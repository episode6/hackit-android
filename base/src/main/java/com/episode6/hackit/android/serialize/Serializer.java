package com.episode6.hackit.android.serialize;

public interface Serializer {
  <T> T deserialize(SerializeKey<T> key, String serializedValue);
  <T> String serialize(SerializeKey<T> key, T value);
}
