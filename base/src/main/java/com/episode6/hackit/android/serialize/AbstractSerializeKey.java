package com.episode6.hackit.android.serialize;

import java.lang.reflect.Type;

public class AbstractSerializeKey<V> implements SerializeKey<V> {
  private final String mPath;
  private final Type mType;

  AbstractSerializeKey(String path, Type typeOf) {
    mPath = path;
    mType = typeOf;
  }

  @Override
  public String getPath() {
    return mPath;
  }

  @Override
  public Type getObjectType() {
    return mType;
  }
}
