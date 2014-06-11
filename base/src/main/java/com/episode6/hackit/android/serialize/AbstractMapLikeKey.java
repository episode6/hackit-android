package com.episode6.hackit.android.serialize;

public abstract class AbstractMapLikeKey<V> implements MapLikeKey<V> {

  private final NamespacedKey mNameKey;
  private final SerializeKey<V> mSerializeKey;

  public AbstractMapLikeKey(NamespacedKey nameKey, SerializeKey<V> serializeKey) {
    mNameKey = nameKey;
    mSerializeKey = serializeKey;
  }

  @Override
  public SerializeKey<V> getSerializeKey() {
    return mSerializeKey;
  }

  @Override
  public NamespacedKey getNameKey() {
    return mNameKey;
  }
}
