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

  public <V> PrefKey<V> addKey(String name, Class<V> type) {
    return new PrefKeyBuilder<V>(this)
        .named(name)
        .ofType(type)
        .build();
  }
}
