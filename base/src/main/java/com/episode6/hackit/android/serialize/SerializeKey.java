package com.episode6.hackit.android.serialize;

import com.episode6.hackit.android.util.StringFormat;
import com.google.common.base.Objects;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public final class SerializeKey<V> {

  public static <T> SerializeKey<T> key(Class<T> clazz) {
    return new SerializeKey<T>(clazz);
  }

  public static <T> SerializeKey<T> genericKey(TypeToken<T> typeToken) {
    return new SerializeKey<T>(typeToken);
  }

  public enum KeyType {
    OBJECT,
    GENERIC,
    PRIMITIVE
  }

  public enum PrimitiveType {
    BOOL,
    INT,
    LONG,
    FLOAT,
    STRING,
    NOT_PRIMITIVE
  }

  private static KeyType getKeyType(Type type, Class<?> rawType) {
    if (type != rawType) {
      return KeyType.GENERIC;
    }

    if (type == Integer.class ||
        type == Boolean.class ||
        type == Float.class ||
        type == Long.class ||
        type == String.class) {
      return KeyType.PRIMITIVE;
    }
    return KeyType.OBJECT;
  }

  private static PrimitiveType getPrimitiveType(Type type) {
    if (type == Integer.class) {
      return PrimitiveType.INT;
    }
    if (type == Boolean.class) {
      return PrimitiveType.BOOL;
    }
    if (type == Float.class) {
      return PrimitiveType.FLOAT;
    }
    if (type == Long.class) {
      return PrimitiveType.LONG;
    }
    if (type == String.class) {
      return PrimitiveType.STRING;
    }
    return PrimitiveType.NOT_PRIMITIVE;
  }

  private final Type mType;
  private final Class<? super V> mRawType;
  private final KeyType mKeyType;
  private final PrimitiveType mPrimitiveType;

  private SerializeKey(Type type, Class<? super V> rawType) {
    mType = type;
    mRawType = rawType;
    mKeyType = getKeyType(type, rawType);
    mPrimitiveType = getPrimitiveType(type);
  }

  SerializeKey(Class<V> clazz) {
    this((Type)clazz, clazz);
  }

  SerializeKey(TypeToken<V> typeToken) {
    this(typeToken.getType(), typeToken.getRawType());
  }

  public Type getObjectType() {
    return mType;
  }

  public Class<? super V> getRawType() {
    return mRawType;
  }

  public boolean isPrimitive() {
    return mKeyType == KeyType.PRIMITIVE;
  }

  public KeyType getKeyType() {
    return mKeyType;
  }

  public PrimitiveType getPrimitiveType() {
    return mPrimitiveType;
  }

  @SuppressWarnings("unchecked")
  public V castSafely(Object instance) {
    if (mRawType.isInstance(instance)) {
      return (V) instance;
    }
    return null;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(
        mType,
        mRawType,
        mKeyType,
        mPrimitiveType);
  }

  @Override
  public boolean equals(Object o) {
    return o != null && hashCode() == o.hashCode();
  }

  @Override
  public String toString() {
    return StringFormat.of("SerializeKey of Type: %s", mType);
  }
}
