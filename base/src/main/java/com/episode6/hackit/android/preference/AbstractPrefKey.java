package com.episode6.hackit.android.preference;

import javax.annotation.Nullable;
import javax.inject.Provider;

public class AbstractPrefKey<T> implements PrefKey<T> {

  private final PrefKeyPath mKeyPath;
  private final Class<T> mObjectClass;
  private final @Nullable Provider<T> mDefaultInstanceProvider;

  AbstractPrefKey(PrefKeyPath keyPath, Class<T> objectClass, @Nullable Provider<T> defaultInstanceProvider) {
    mKeyPath = keyPath;
    mObjectClass = objectClass;
    mDefaultInstanceProvider = defaultInstanceProvider;
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
  public T createDefaultObject() {
    return mDefaultInstanceProvider == null ? null : mDefaultInstanceProvider.get();
  }
}
