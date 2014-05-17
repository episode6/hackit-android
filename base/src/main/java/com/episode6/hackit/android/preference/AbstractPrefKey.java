package com.episode6.hackit.android.preference;

import javax.annotation.Nullable;
import javax.inject.Provider;

public class AbstractPrefKey<T> implements PrefKey<T> {

  private final PrefKeyPath mKeyPath;
  private final @Nullable Provider<T> mDefaultInstanceProvider;

  AbstractPrefKey(PrefKeyPath keyPath, @Nullable Provider<T> defaultInstanceProvider) {
    mKeyPath = keyPath;
    mDefaultInstanceProvider = defaultInstanceProvider;
  }

  @Override
  public PrefKeyPath getKeyPath() {
    return mKeyPath;
  }

  @Nullable
  @Override
  public T createDefaultObject() {
    return mDefaultInstanceProvider == null ? null : mDefaultInstanceProvider.get();
  }
}
