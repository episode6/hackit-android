package com.episode6.hackit.android.typed.bundle;

import com.episode6.hackit.android.serialize.AbstractMapLikeKey;
import com.episode6.hackit.android.serialize.SerializeKey;
import com.episode6.hackit.android.util.StringFormat;

public class BundleKey<V> extends AbstractMapLikeKey<V> {

  private final String mKeyString;

  BundleKey(
      BundleArgumentNamespace namespace,
      String name,
      SerializeKey<V> serializeKey) {
    super(serializeKey);
    mKeyString = StringFormat.of("%s.%s", namespace.getNamespaceString(), name);
  }

  @Override
  public String getKeyString() {
    return mKeyString;
  }

}
