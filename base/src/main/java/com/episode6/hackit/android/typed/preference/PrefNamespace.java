package com.episode6.hackit.android.typed.preference;

import com.episode6.hackit.android.serialize.AbstractMapLikeKeyBuilder;
import com.episode6.hackit.android.serialize.Namespace;
import com.episode6.hackit.android.serialize.NamespacedKey;
import com.episode6.hackit.android.serialize.SerializeKey;
import com.episode6.hackit.chop.Chop;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Collections2;
import com.google.common.collect.Sets;
import com.google.gson.reflect.TypeToken;

import java.util.Collection;
import java.util.Set;

import javax.annotation.Nullable;

public final class PrefNamespace extends Namespace {

  private static final String DELINEATOR = "/";

  public static final PrefNamespace ANONYMOUS = new PrefNamespace();
  public static final PrefNamespace ROOT = ANONYMOUS.extend(DELINEATOR);

  private final Set<PrefNamespace> mSubNamespaces = Sets.newHashSet();
  private final Set<PrefKey<?>> mPrefKeys = Sets.newHashSet();

  PrefNamespace() {
    super(DELINEATOR);
  }

  PrefNamespace(PrefNamespace parentNamespace, String name) {
    super(parentNamespace, name);
  }

  public PrefNamespace extend(String newSegment) {
    PrefNamespace newSpace = new PrefNamespace(this, newSegment);
    mSubNamespaces.add(newSpace);
    return newSpace;
  }

  public <V> PrefKeyBuilder<V> key(Class<V> type) {
    return new PrefKeyBuilder<V>(SerializeKey.key(type));
  }

  public <V> PrefKeyBuilder<V> key(TypeToken<V> typeToken) {
    return new PrefKeyBuilder<V>(SerializeKey.genericKey(typeToken));
  }

  public PrefKeyBuilder<Boolean> boolKey(String name) {
    return key(Boolean.class).named(name);
  }

  public PrefKeyBuilder<Integer> intKey(String name) {
    return key(Integer.class).named(name);
  }

  public PrefKeyBuilder<Long> longKey(String name) {
    return key(Long.class).named(name);
  }

  public PrefKeyBuilder<Float> floatKey(String name) {
    return key(Float.class).named(name);
  }

  public PrefKeyBuilder<String> stringKey(String name) {
    return key(String.class).named(name);
  }

  /**
   * Only call this in debug builds
   */
  public void validate() {
    Set<NamespacedKey> uniqueNames = validateInternal();
    Chop.d("PrefNamespace validation successful, found %d keys...", uniqueNames.size());
    for (NamespacedKey prefName : uniqueNames) {
      Chop.d(prefName.toString());
    }
  }

  private Set<NamespacedKey> validateInternal() {
    Chop.d("Validating Preference path %s", this);
    Set<NamespacedKey> knownUniqueNames = Sets.newHashSet();

    Collection<NamespacedKey> childPrefKeyNames =
        Collections2.transform(mPrefKeys, new Function<PrefKey<?>, NamespacedKey>() {

          @Override
          public @Nullable NamespacedKey apply(PrefKey<?> input) {
            return input.getNameKey();
          }
        });

    validateMoreKeys(knownUniqueNames, childPrefKeyNames);

    for (PrefNamespace namespace : mSubNamespaces) {
      validateMoreKeys(knownUniqueNames, namespace.validateInternal());
    }
    return knownUniqueNames;
  }

  private void validateMoreKeys(Set<NamespacedKey> knownUniques, Collection<NamespacedKey> unknowns) {
    for (NamespacedKey key : unknowns) {
      Preconditions.checkArgument(knownUniques.add(key), "Two keys found with the same name: " + key);
    }
  }

  public class PrefKeyBuilder<V> extends AbstractMapLikeKeyBuilder<V> {

    private String mName = null;
    private PrefValueProvider<V> mDefaultInstanceProvider = null;
    private boolean mShouldCache = false;

    protected PrefKeyBuilder(SerializeKey<V> serializeKey) {
      super(serializeKey);
    }

    public PrefKeyBuilder<V> named(String name) {
      mName = name;
      return this;
    }

    public PrefKeyBuilder<V> defaultTo(final V defaultInstance) {
      mDefaultInstanceProvider = defaultInstance == null ?
          null :
          new SimpleDefaultValueProvider<V>(defaultInstance);
      return this;
    }

    public PrefKeyBuilder<V> defaultTo(PrefValueProvider<V> defaultInstanceProvider) {
      mDefaultInstanceProvider = defaultInstanceProvider;
      return this;
    }

    public PrefKeyBuilder<V> cache(boolean shouldCache) {
      mShouldCache = shouldCache;
      return this;
    }

    public PrefKey<V> build() {
      return buildInternal(false);
    }

    public OptionalPrefKey<V> buildOptional() {
      return new OptionalPrefKey<V>(buildInternal(true));
    }

    private PrefKey<V> buildInternal(boolean nullSafe) {
      Preconditions.checkNotNull(mName, "Cannot create PrefKey with null name");
      if (!nullSafe) {
        Preconditions.checkNotNull(
            mDefaultInstanceProvider,
            "Cannot build a PrefKey with a null default instance unless using buildOptional()");
      }

      PrefKey<V> newKey = new PrefKey<V>(
          buildKey(mName),
          getSerializeKey(),
          mDefaultInstanceProvider,
          mShouldCache);
      mPrefKeys.add(newKey);

      return newKey;
    }
  }

  private static class SimpleDefaultValueProvider<V> implements PrefValueProvider<V> {

    private final V mInstance;

    public SimpleDefaultValueProvider(V instance) {
      mInstance = instance;
    }

    @Override
    public V createDefaultValue(PrefManager prefManager) {
      return mInstance;
    }
  }
}
