package com.episode6.hackit.android.serialize;

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

    String serializedValue = mSerializer.serialize(key.getSerializeKey(), value);
    mapSetter.putString(keyString, serializedValue);
  }
}
