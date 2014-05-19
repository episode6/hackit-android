package com.episode6.hackit.android.preference;

import javax.inject.Provider;

public class PrefKeyBuilder<V> {

  private final PrefKeyPath mBaseKeyPath;
  private final String mPrefName;
  private final Class<V> mObjectType;

  private Provider<V> mDefaultInstanceProvider = null;
  private boolean mShouldCache = false;

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

  public PrefKeyBuilder<V> cache(boolean shouldCache) {
    mShouldCache = shouldCache;
    return this;
  }

  public PrefKey<V> build() {
    return buildInternal(false);
  }

  public OptionalPrefKey<V> buildOptional() {
    return new OptionalPrefKey<V>(buildInternal(true));
  }

  private PrefKey<V> buildInternal(boolean nullSafe) {
    if (mDefaultInstanceProvider == null && !nullSafe) {
      throw new NullPointerException("Cannot build a PrefKey with a null default instance unless using buildOptional()");
    }

    PrefKeyPath keyPath = mBaseKeyPath.extendWithoutTracking(mPrefName);

    if (BasicPrefKey.supportsObject(mObjectType)) {
      return mBaseKeyPath.addChildKey(new BasicPrefKey.Key<V>(
          keyPath,
          mObjectType,
          mDefaultInstanceProvider,
          mShouldCache));
    }

    return mBaseKeyPath.addChildKey(new GsonPrefKey.Key<V>(
        keyPath,
        mObjectType,
        mDefaultInstanceProvider,
        mShouldCache));
  }
}
