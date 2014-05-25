package com.episode6.hackit.android.preference;

public interface PrefValueProvider<T> {
  public T createDefaultValue(PrefManager prefManager);
}
