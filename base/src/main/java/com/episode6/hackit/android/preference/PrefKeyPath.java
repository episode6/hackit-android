package com.episode6.hackit.android.preference;

import com.episode6.hackit.android.util.StringFormat;

public class PrefKeyPath {

  private static final String EXTENSION_FORMAT = "%s/%s";

  private final String mPath;

  PrefKeyPath(String path) {
    mPath = path;
  }

  PrefKeyPath(String pathBase, String lastPathSegment) {
    this(StringFormat.of(EXTENSION_FORMAT, lastPathSegment));
  }

  public String getPath() {
    return mPath;
  }

  public PrefKeyPath extend(String newPathSegment) {
    return new PrefKeyPath(mPath, newPathSegment);
  }
}
