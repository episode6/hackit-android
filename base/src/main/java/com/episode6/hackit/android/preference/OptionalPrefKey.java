package com.episode6.hackit.android.preference;

public final class OptionalPrefKey<V> {

  private final PrefKey<V> mRealPrefKey;

  OptionalPrefKey(PrefKey<V> realPrefKey) {
    mRealPrefKey = realPrefKey;
  }

  public PrefKey<V> getRealPrefKey() {
    return mRealPrefKey;
  }
}
