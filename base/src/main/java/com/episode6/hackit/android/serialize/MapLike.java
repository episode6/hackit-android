package com.episode6.hackit.android.serialize;

import javax.annotation.Nullable;

public interface MapLike<T> {

  T getOriginal();

  public interface Getter<V> extends MapLike<V> {
    boolean containsKey(String key);
    int getInt(String key);
    float getFloat(String key);
    boolean getBool(String key);
    long getLong(String key);
    @Nullable String getString(String key);
  }

  public interface Setter<V> extends MapLike<V> {
    void putInt(String key, int value);
    void putFloat(String key, float value);
    void putBool(String key, boolean value);
    void putLong(String key, long value);
    void putString(String key, String value);
    void removeKey(String key);
  }
}
