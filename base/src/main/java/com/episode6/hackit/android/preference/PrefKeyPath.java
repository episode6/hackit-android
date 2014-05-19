package com.episode6.hackit.android.preference;

import com.episode6.hackit.android.util.StringFormat;
import com.episode6.hackit.chop.Chop;
import com.google.common.collect.Sets;

import java.util.Set;

public class PrefKeyPath {

  private static final String EXTENSION_FORMAT = "%s/%s";

  private final String mPath;
  private final Set<PrefKeyPath> mChildPaths = Sets.newHashSet();
  private final Set<PrefKey<?>> mChildKeys = Sets.newHashSet();

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
    PrefKeyPath prefKeyPath = extendWithoutTracking(newPathSegment);
    mChildPaths.add(prefKeyPath);
    return prefKeyPath;
  }

  /**
   * Only call this in debug builds
   */
  public void validate() {
    for (String prefPath : validateInternal()) {
      Chop.d(prefPath);
    }
  }

  private Set<String> validateInternal() {
    Chop.d("Validating Preference path %s", mPath);
    Set<String> uniquePaths = Sets.newHashSet();
    for (PrefKey<?> key : mChildKeys) {
      if (!uniquePaths.add(key.getKeyPath().getPath())) {
        throw new IllegalArgumentException("Two keys with the same path: " + key.getKeyPath().getPath());
      }
    }

    for (PrefKeyPath path : mChildPaths) {
      for (String pathString : path.validateInternal()) {
        if (!uniquePaths.add(pathString)) {
          throw new IllegalArgumentException("Two keys with the same path: " + pathString);
        }
      }
    }
    return uniquePaths;
  }

  public <V> PrefKeyBuilder<V> key(String name, Class<V> type) {
    return new PrefKeyBuilder<V>(this, name, type);
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

  PrefKeyPath extendWithoutTracking(String newPathSegment) {
    return new PrefKeyPath(mPath, newPathSegment);
  }

  <V> PrefKey<V> addChildKey(PrefKey<V> key) {
    mChildKeys.add(key);
    return key;
  }
}
