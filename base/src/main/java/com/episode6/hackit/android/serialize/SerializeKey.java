package com.episode6.hackit.android.serialize;

import com.episode6.hackit.android.util.StringFormat;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public final class SerializeKey<V> {

  public static <T> SerializeKey<T> newKey(Class<T> clazz) {
    return new SerializeKey<T>(clazz);
  }

  public static <T> SerializeKey<T> newGenericKey(TypeToken<T> typeToken) {
    return new SerializeKey<T>(typeToken);
  }

  private final Type mType;

  private SerializeKey(Type type) {
    mType = type;
  }

  SerializeKey(Class<V> clazz) {
    this((Type)clazz);
  }

  SerializeKey(TypeToken<V> typeToken) {
    this(typeToken.getType());
  }

  public Type getObjectType() {
    return mType;
  }

  @Override
  public String toString() {
    return StringFormat.of("SerializeKey of Type: %s", mType);
  }
}
