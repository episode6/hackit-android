package com.episode6.hackit.android.preference;

public class SimplePrefKey<T> implements PrefKey<T> {

  private final PrefKeyPath mKeyPath;
  private final Class<T> mObjectClass;

  SimplePrefKey(PrefKeyPath keyPath, Class<T> objectClass) {
    mKeyPath = keyPath;
    mObjectClass = objectClass;
  }

  @Override
  public PrefKeyPath getKeyPath() {
    return mKeyPath;
  }

  @Override
  public Class<T> getObjectType() {
    return mObjectClass;
  }
}
