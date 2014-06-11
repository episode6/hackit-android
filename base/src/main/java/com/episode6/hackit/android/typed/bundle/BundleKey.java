package com.episode6.hackit.android.typed.bundle;

import com.episode6.hackit.android.serialize.AbstractMapLikeKey;
import com.episode6.hackit.android.serialize.NamespacedKey;
import com.episode6.hackit.android.serialize.SerializeKey;
import com.episode6.hackit.android.util.StringFormat;

public class BundleKey<V> extends AbstractMapLikeKey<V> {

  public BundleKey(NamespacedKey nameKey, SerializeKey<V> serializeKey) {
    super(nameKey, serializeKey);
  }
}
