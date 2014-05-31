package com.episode6.hackit.android.serialize;

import javax.annotation.Nullable;

public interface MapLike<T> {

  T getOriginal();

  public interface Getter<V> extends MapLike<V> {
    boolean containsKey(String key);
    @Nullable String getString(String key);
  }

  public interface Setter<V> extends MapLike<V> {
    void putString(String key, String value);
    void removeKey(String key);
  }
}
