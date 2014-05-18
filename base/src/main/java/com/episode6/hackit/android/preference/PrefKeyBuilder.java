package com.episode6.hackit.android.preference;

import javax.inject.Provider;

public class PrefKeyBuilder<V> {

  private final PrefKeyPath mBaseKeyPath;
  private final String mPrefName;
  private final Class<V> mObjectType;

  private Provider<V> mDefaultInstanceProvider = null;

  PrefKeyBuilder(
      PrefKeyPath basePath,
      String name,
      Class<V> objectType) {
    mBaseKeyPath = basePath;
    mPrefName = name;
    mObjectType = objectType;
  }

  public PrefKey<V> defaultTo(final V defaultInstance) {
    mDefaultInstanceProvider = defaultInstance == null ? null :
        new Provider<V>() {
          @Override
          public V get() {
            return defaultInstance;
          }
        };
    return build();
  }

  public PrefKey<V> defaultTo(Provider<V> defaultInstanceProvider) {
    mDefaultInstanceProvider = defaultInstanceProvider;
    return build();
  }

  public PrefKey<V> build() {
    PrefKeyPath keyPath = mBaseKeyPath.extend(mPrefName);

    if (mObjectType == Boolean.class ||
        mObjectType == Integer.class ||
        mObjectType == Long.class ||
        mObjectType == Float.class ||
        mObjectType == String.class) {
      return new BasicPrefKey.Key<V>(keyPath, mObjectType, mDefaultInstanceProvider);
    }

    return new GsonPrefKey.Key<V>(keyPath, mObjectType, mDefaultInstanceProvider);
  }
}
