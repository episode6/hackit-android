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

    Type type = key.getSerializeKey().getObjectType();
    if (isPrimitive(type)) {
      return (T) getPrimitive(mapGetter, type, keyString);
    }

    String value = mapGetter.getString(keyString);
    return mSerializer.deserialize(key.getSerializeKey(), value);
  }

  @Override
  public <T> void set(MapLike.Setter mapSetter, MapLikeKey<T> key, @Nullable T value) {
    String keyString = key.getKeyString();
    if (value == null) {
      mapSetter.removeKey(keyString);
      return;
    }

    Type type = key.getSerializeKey().getObjectType();
    if (isPrimitive(type)) {
      setPrimitive(mapSetter, type, keyString, value);
      return;
    }

    String serializedValue = mSerializer.serialize(key.getSerializeKey(), value);
    mapSetter.putString(keyString, serializedValue);
  }

  private boolean isPrimitive(Type type) {
    return type == Integer.class ||
        type == Boolean.class ||
        type == Float.class ||
        type == Long.class ||
        type == String.class;
  }

  private Object getPrimitive(MapLike.Getter getter, Type type, String key) {
    if (type == Integer.class) {
      return getter.getInt(key);
    } else if (type == Boolean.class) {
      return getter.getBool(key);
    } else if (type == Float.class) {
      return getter.getFloat(key);
    } else if (type == Long.class) {
      return getter.getLong(key);
    } else if (type == String.class) {
      return getter.getString(key);
    }
    return null;
  }

  private void setPrimitive(MapLike.Setter setter, Type type, String key, Object value) {
    if (type == Integer.class) {
      setter.putInt(key, (Integer) value);
    } else if (type == Boolean.class) {
      setter.putBool(key, (Boolean) value);
    } else if (type == Float.class) {
      setter.putFloat(key, (Float) value);
    } else if (type == Long.class) {
      setter.putLong(key, (Long) value);
    } else if (type == String.class) {
      setter.putString(key, (String) value);
    }
  }
}
