package com.episode6.hackit.android.typed.preference;

import com.episode6.hackit.android.serialize.AbstractMapLikeKeyBuilder;
import com.episode6.hackit.android.serialize.SerializeKey;

public class PrefKeyBuilder<V> extends AbstractMapLikeKeyBuilder<V> {

  private final PrefKeyPath mBaseKeyPath;
  private final String mPrefName;

  private PrefValueProvider<V> mDefaultInstanceProvider = null;
  private boolean mShouldCache = false;

  PrefKeyBuilder(
      SerializeKey<V> serializeKey,
      PrefKeyPath basePath,
      String name) {
    super(serializeKey);

    mBaseKeyPath = basePath;
    mPrefName = name;
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
    if (mDefaultInstanceProvider == null && !nullSafe) {
      throw new NullPointerException("Cannot build a PrefKey with a null default instance unless using buildOptional()");
    }

    PrefKeyPath keyPath = mBaseKeyPath.extendWithoutTracking(mPrefName);
    return mBaseKeyPath.addChildKey(
        new PrefKey<V>(
            getSerializeKey(),
            keyPath,
            mDefaultInstanceProvider,
            mShouldCache));
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
