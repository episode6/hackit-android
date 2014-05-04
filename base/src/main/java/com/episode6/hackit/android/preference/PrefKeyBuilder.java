package com.episode6.hackit.android.preference;

public class PrefKeyBuilder<V> {

  private final PrefKeyPath mBaseKeyPath;

  private String mPrefName;
  private Class<V> mObjectType;

  PrefKeyBuilder(PrefKeyPath basePath) {
    mBaseKeyPath = basePath;
  }

  public PrefKeyBuilder<V> name(String name) {
    mPrefName = name;
    return this;
  }

  public PrefKeyBuilder<V> name(Class<V> objectType) {
    mObjectType = objectType;
    return this;
  }

  public PrefKey<V> build() {
    PrefKeyPath path = mBaseKeyPath.extend(mPrefName);
    return new SimplePrefKey<V>(path, mObjectType);
  }
}
