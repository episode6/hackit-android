package com.episode6.hackit.android.serialize;

import com.episode6.hackit.android.serialize.SerializeKey;

public interface MapLikeKey<V> {
  NamespacedKey getNameKey();
  SerializeKey<V> getSerializeKey();
}
