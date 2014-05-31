package com.episode6.hackit.android.serialize;

import javax.annotation.Nullable;

public interface MapLikeTranslator {
  <T> boolean containsKey(MapLike.Getter mapGetter, MapLikeKey<T> key);
  <T> T get(MapLike.Getter mapGetter, MapLikeKey<T> key);
  <T> void set(MapLike.Setter mapSetter, MapLikeKey<T> key, @Nullable T value);
}
