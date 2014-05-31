package com.episode6.hackit.android.typed.preference;

public interface PrefValueProvider<T> {
  public T createDefaultValue(PrefManager prefManager);
}
