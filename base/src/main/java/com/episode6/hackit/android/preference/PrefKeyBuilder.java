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

  public PrefKeyBuilder<V> defaultTo(final V defaultInstance) {
    mDefaultInstanceProvider = defaultInstance == null ? null :
        new Provider<V>() {
          @Override
          public V get() {
            return defaultInstance;
          }
        };
    return this;
  }

  public PrefKeyBuilder<V> defaultTo(Provider<V> defaultInstanceProvider) {
    mDefaultInstanceProvider = defaultInstanceProvider;
    return this;
  }

  public PrefKey<V> build() {
    PrefKeyPath keyPath = mBaseKeyPath.extend(mPrefName);

    if (BasicPrefKey.supportsObject(mObjectType)) {
      return new BasicPrefKey.Key<V>(keyPath, mObjectType, mDefaultInstanceProvider);
    }

    return new GsonPrefKey.Key<V>(keyPath, mObjectType, mDefaultInstanceProvider);
  }

  public OptionalPrefKey<V> buildOptional() {
    return new OptionalPrefKey<V>(build());
  }
}
