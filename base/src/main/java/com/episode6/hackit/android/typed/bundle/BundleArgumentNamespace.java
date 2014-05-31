package com.episode6.hackit.android.typed.bundle;

import com.episode6.hackit.android.serialize.AbstractMapLikeKeyBuilder;
import com.episode6.hackit.android.serialize.SerializeKey;
import com.episode6.hackit.android.util.StringFormat;
import com.google.gson.reflect.TypeToken;

import javax.annotation.Nullable;

public class BundleArgumentNamespace {

  public static BundleArgumentNamespace anonymous() {
    return new BundleArgumentNamespace("EXTRA");
  }

  public static BundleArgumentNamespace extendFrom(Class<?> clazz) {
    return new BundleArgumentNamespace(StringFormat.of("%s.extra", clazz.getName()));
  }

  private final @Nullable String mNamespace;

  BundleArgumentNamespace(String namespace) {
    mNamespace = namespace;
  }

  public String getNamespaceString() {
    return mNamespace;
  }

  public <T> KeyBuilder<T> newKey(Class<T> clazz) {
    return new KeyBuilder<T>(SerializeKey.newKey(clazz));
  }

  public <T> KeyBuilder<T> newGenericKey(TypeToken<T> token) {
    return new KeyBuilder<T>(SerializeKey.newGenericKey(token));
  }


  public class KeyBuilder<V> extends AbstractMapLikeKeyBuilder<V> {

    KeyBuilder(SerializeKey<V> serializeKey) {
      super(serializeKey);
    }

    public BundleKey<V> named(String name) {
      return new BundleKey<V>(BundleArgumentNamespace.this, name, getSerializeKey());
    }
  }
}
