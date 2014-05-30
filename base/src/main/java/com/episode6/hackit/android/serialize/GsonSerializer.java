package com.episode6.hackit.android.serialize;

import com.google.gson.Gson;

import javax.inject.Inject;

public class GsonSerializer implements Serializer {

  @Inject Gson mGson;

  @Override
  public <T> T deserialize(SerializeKey<T> key, String serializedValue) {
    return mGson.fromJson(serializedValue, key.getObjectType());
  }

  @Override
  public <T> String serialize(SerializeKey<T> key, T value) {
    return mGson.toJson(value, key.getObjectType());
  }
}
