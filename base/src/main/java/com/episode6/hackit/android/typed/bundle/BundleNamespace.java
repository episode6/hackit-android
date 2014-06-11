package com.episode6.hackit.android.typed.bundle;

import com.episode6.hackit.android.serialize.AbstractMapLikeKeyBuilder;
import com.episode6.hackit.android.serialize.Namespace;
import com.episode6.hackit.android.serialize.SerializeKey;
import com.google.gson.reflect.TypeToken;

public final class BundleNamespace extends Namespace {

  private static final String DELINEATER = ".";

  public static final BundleNamespace ANONYMOUS = new BundleNamespace();

  public static final BundleNamespace fromClass(Class<?> classNamespace) {
    String nameSpaceName = classNamespace.getName().replace("$", DELINEATER);
    return ANONYMOUS.extend(nameSpaceName);
  }

  BundleNamespace() {
    super(DELINEATER);
  }

  BundleNamespace(BundleNamespace parentNamespace, String name) {
    super(parentNamespace, name);
  }

  public BundleNamespace extend(String name) {
    return new BundleNamespace(this, name);
  }

  public <T> KeyBuilder<T> newKey(Class<T> clazz) {
    return new KeyBuilder<T>(SerializeKey.key(clazz));
  }

  public <T> KeyBuilder<T> newGenericKey(TypeToken<T> token) {
    return new KeyBuilder<T>(SerializeKey.genericKey(token));
  }

  public class KeyBuilder<V> extends AbstractMapLikeKeyBuilder<V> {

    protected KeyBuilder(SerializeKey<V> serializeKey) {
      super(serializeKey);
    }

    public BundleKey<V> named(String name) {
      return new BundleKey<V>(buildKey(name), getSerializeKey());
    }
  }
}
