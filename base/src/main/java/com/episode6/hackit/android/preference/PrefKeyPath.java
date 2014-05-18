package com.episode6.hackit.android.preference;

import com.episode6.hackit.android.util.StringFormat;

public class PrefKeyPath {

  private static final String EXTENSION_FORMAT = "%s/%s";

  private final String mPath;

  PrefKeyPath(String path) {
    mPath = path;
  }

  PrefKeyPath(String pathBase, String lastPathSegment) {
    this(StringFormat.of(EXTENSION_FORMAT, pathBase, lastPathSegment));
  }

  public String getPath() {
    return mPath;
  }

  public PrefKeyPath extend(String newPathSegment) {
    return new PrefKeyPath(mPath, newPathSegment);
  }

  public <V> PrefKeyBuilder<V> key(String name, Class<V> type) {
    return new PrefKeyBuilder<V>(this, name, type);
  }

  public <V> PrefKey<V> nullKey(String name, Class<V> type) {
    return new PrefKeyBuilder<V>(this, name, type).build();
  }

  public PrefKeyBuilder<Boolean> boolKey(String name) {
    return key(name, Boolean.class);
  }

  public PrefKeyBuilder<Integer> intKey(String name) {
    return key(name, Integer.class);
  }

  public PrefKeyBuilder<Long> longKey(String name) {
    return key(name, Long.class);
  }

  public PrefKeyBuilder<Float> floatKey(String name) {
    return key(name, Float.class);
  }

  public PrefKeyBuilder<String> stringKey(String name) {
    return key(name, String.class);
  }
}
