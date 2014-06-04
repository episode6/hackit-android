package com.episode6.hackit.android.serialize;

import java.lang.reflect.Type;

import javax.annotation.Nullable;
import javax.inject.Inject;

public class MapLikeTranslatorImpl implements MapLikeTranslator {

  @Inject Serializer mSerializer;

  @Override
  public <T> boolean containsKey(MapLike.Getter mapGetter, MapLikeKey<T> key) {
    return mapGetter.containsKey(key.getKeyString());
  }

  @Override
  public <T> T get(MapLike.Getter mapGetter, MapLikeKey<T> key) {
    String keyString = key.getKeyString();
    if (!mapGetter.containsKey(keyString)) {
      return null;
    }

    SerializeKey<T> serializeKey = key.getSerializeKey();
    if (serializeKey.isPrimitive()) {
      return serializeKey.castSafely(
          getPrimitive(mapGetter, serializeKey, keyString));
    }

    String value = mapGetter.getString(keyString);
    return mSerializer.deserialize(serializeKey, value);
  }

  @Override
  public <T> void set(MapLike.Setter mapSetter, MapLikeKey<T> key, @Nullable T value) {
    String keyString = key.getKeyString();
    if (value == null) {
      mapSetter.removeKey(keyString);
      return;
    }

    SerializeKey<T> serializeKey = key.getSerializeKey();
    if (serializeKey.isPrimitive()) {
      setPrimitive(mapSetter, serializeKey, keyString, value);
      return;
    }

    String serializedValue = mSerializer.serialize(serializeKey, value);
    mapSetter.putString(keyString, serializedValue);
  }

  private Object getPrimitive(MapLike.Getter getter, SerializeKey serializeKey, String key) {
    switch (serializeKey.getPrimitiveType()) {
      case BOOL:
        return getter.getBool(key);
      case INT:
        return getter.getInt(key);
      case LONG:
        return getter.getLong(key);
      case FLOAT:
        return getter.getFloat(key);
      case STRING:
        return getter.getString(key);
    }
    return null;
  }

  private void setPrimitive(MapLike.Setter setter, SerializeKey serializeKey, String key, Object value) {
    switch (serializeKey.getPrimitiveType()) {
      case BOOL:
        setter.putBool(key, (Boolean) value);
        break;
      case INT:
        setter.putInt(key, (Integer) value);
        break;
      case LONG:
        setter.putLong(key, (Long) value);
        break;
      case FLOAT:
        setter.putFloat(key, (Float) value);
        break;
      case STRING:
        setter.putString(key, (String) value);
        break;
    }
  }
}
