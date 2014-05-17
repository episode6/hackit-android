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

    if (mObjectType == Boolean.class) {
      return (PrefKey<V>) new BasicPrefKeys.BoolPrefKey(keyPath, (Provider<Boolean>) mDefaultInstanceProvider);
    } else if (mObjectType == Integer.class) {
      return (PrefKey<V>) new BasicPrefKeys.IntPrefKey(keyPath, (Provider<Integer>) mDefaultInstanceProvider);
    } else if (mObjectType == Long.class) {
      return (PrefKey<V>) new BasicPrefKeys.LongPrefKey(keyPath, (Provider<Long>) mDefaultInstanceProvider);
    } else if (mObjectType == Float.class) {
      return (PrefKey<V>) new BasicPrefKeys.FloatPrefKey(keyPath, (Provider<Float>) mDefaultInstanceProvider);
    } else if (mObjectType == String.class) {
      return (PrefKey<V>) new BasicPrefKeys.StringPrefKey(keyPath, (Provider<String>) mDefaultInstanceProvider);
    }

    return new GsonPrefKey.Key<V>(keyPath, mObjectType, mDefaultInstanceProvider);
  }
}
