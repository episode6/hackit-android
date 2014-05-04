package com.episode6.hackit.android.preference;

public interface PrefKey<T> {
  PrefKeyPath getKeyPath();
  Class<T> getObjectType();
}
