package com.episode6.hackit.android.serialize;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class SerializeKeyBuilder<V> {

  private final Type mType;
  private String mPath;

  private SerializeKeyBuilder(Type type) {
    mType = type;
  }

  public SerializeKeyBuilder(Class<V> clazz) {
    this((Type)clazz);
  }

  public SerializeKeyBuilder(TypeToken<V> typeToken) {
    this(typeToken.getType());
  }

  public SerializeKeyBuilder path(String path) {
    mPath = path;
    return this;
  }

  public SerializeKey<V> build() {
    return new AbstractSerializeKey<V>(mPath, mType);
  }

}
