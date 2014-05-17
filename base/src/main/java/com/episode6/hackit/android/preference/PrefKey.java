package com.episode6.hackit.android.preference;

import javax.annotation.Nullable;

public interface PrefKey<T> {
  PrefKeyPath getKeyPath();
  @Nullable T createDefaultObject();
}
