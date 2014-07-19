package com.episode6.hackit.android.typed.bundle;

public final class DefaultableBundleKey<V> {

  private final BundleKey<V> mRealKey;
  private final V mDefaultValue;

  DefaultableBundleKey(BundleKey<V> key, V defaultValue) {
    mRealKey = key;
    mDefaultValue = defaultValue;
  }

  public BundleKey<V> getRealKey() {
    return mRealKey;
  }

  public V getDefaultValue() {
    return mDefaultValue;
  }

}
