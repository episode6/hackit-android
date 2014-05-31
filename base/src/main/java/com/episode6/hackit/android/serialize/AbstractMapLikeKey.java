package com.episode6.hackit.android.serialize;

public abstract class AbstractMapLikeKey<V> implements MapLikeKey<V> {

  private final SerializeKey<V> mSerializeKey;

  public AbstractMapLikeKey(SerializeKey<V> serializeKey) {
    mSerializeKey = serializeKey;
  }

  @Override
  public SerializeKey<V> getSerializeKey() {
    return mSerializeKey;
  }
}
