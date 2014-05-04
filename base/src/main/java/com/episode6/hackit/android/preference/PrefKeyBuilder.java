package com.episode6.hackit.android.preference;

public class PrefKeyBuilder<V> {

  private final PrefKeyPath mBaseKeyPath;

  private String mPrefName;
  private Class<V> mObjectType;
  private V mDefaultInstance = null;

  PrefKeyBuilder(PrefKeyPath basePath) {
    mBaseKeyPath = basePath;
  }

  public PrefKeyBuilder<V> named(String name) {
    mPrefName = name;
    return this;
  }

  public PrefKeyBuilder<V> ofType(Class<V> objectType) {
    mObjectType = objectType;
    return this;
  }

  public PrefKeyBuilder<V> defaultTo(V defaultInstance) {
    mDefaultInstance = defaultInstance;
    return this;
  }

  public PrefKey<V> build() {
    PrefKeyPath path = mBaseKeyPath.extend(mPrefName);
    return new SimplePrefKey<V>(path, mObjectType, mDefaultInstance);
  }
}
