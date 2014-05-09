package com.episode6.hackit.android.preference;

public class PrefKeyBuilder<V> {

  private final PrefKeyPath mBaseKeyPath;
  private final String mPrefName;
  private final Class<V> mObjectType;

  private V mDefaultInstance = null;

  PrefKeyBuilder(
      PrefKeyPath basePath,
      String name,
      Class<V> objectType) {
    mBaseKeyPath = basePath;
    mPrefName = name;
    mObjectType = objectType;
  }

  public PrefKey<V> defaultTo(V defaultInstance) {
    mDefaultInstance = defaultInstance;
    return build();
  }

  public PrefKey<V> build() {
    PrefKeyPath path = mBaseKeyPath.extend(mPrefName);
    return new SimplePrefKey<V>(path, mObjectType, mDefaultInstance);
  }
}
