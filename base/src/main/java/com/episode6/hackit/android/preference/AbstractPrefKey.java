package com.episode6.hackit.android.preference;

import com.episode6.hackit.android.util.StringFormat;

import javax.annotation.Nullable;
import javax.inject.Provider;

public class AbstractPrefKey<T> implements PrefKey<T> {

  private final PrefKeyPath mKeyPath;
  private final Class<T> mObjectClass;
  private final @Nullable PrefValueProvider<T> mDefaultInstanceProvider;
  private final boolean mShouldCache;

  AbstractPrefKey(
      PrefKeyPath keyPath,
      Class<T> objectClass,
      @Nullable PrefValueProvider<T> defaultInstanceProvider,
      boolean shouldCache) {
    mKeyPath = keyPath;
    mObjectClass = objectClass;
    mDefaultInstanceProvider = defaultInstanceProvider;
    mShouldCache = shouldCache;
  }

  @Override
  public PrefKeyPath getKeyPath() {
    return mKeyPath;
  }

  @Override
  public Class<T> getObjectType() {
    return mObjectClass;
  }

  @Nullable
  @Override
  public T createDefaultObject(PrefManager prefManager) {
    return mDefaultInstanceProvider == null ? null : mDefaultInstanceProvider.createDefaultValue(prefManager);
  }

  @Override
  public String toString() {
    return StringFormat.of("PrefKey of type: %s, path: %s", mObjectClass.getSimpleName(), mKeyPath.getPath());
  }

  @Override
  public boolean shouldCache() {
    return mShouldCache;
  }
}
