package com.episode6.hackit.android.serialize;

public abstract class AbstractMapLikeKeyBuilder<V> {

  private final SerializeKey<V> mSerializeKey;

  protected AbstractMapLikeKeyBuilder(SerializeKey<V> serializeKey) {
    mSerializeKey = serializeKey;
  }

  public SerializeKey<V> getSerializeKey() {
    return mSerializeKey;
  }
}
