package com.episode6.hackit.android.preference;

import javax.annotation.Nullable;

public class SimplePrefKey<T> implements PrefKey<T> {

  private final PrefKeyPath mKeyPath;
  private final Class<T> mObjectClass;
  private final @Nullable T mDefaultInstance;

  SimplePrefKey(PrefKeyPath keyPath, Class<T> objectClass, @Nullable T defaultInstance) {
    mKeyPath = keyPath;
    mObjectClass = objectClass;
    mDefaultInstance = defaultInstance;
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
    return mDefaultInstance;
  }
}
