package com.episode6.hackit.android.typed.bundle;

import android.os.Bundle;

import com.google.common.base.Optional;

import javax.annotation.Nullable;

public interface TypedBundle {
  <T> Optional<T> get(BundleKey<T> key);
  <T> TypedBundle set(BundleKey<T> key, @Nullable T value);
  <T> T get(DefaultableBundleKey<T> key);
  <T> TypedBundle set(DefaultableBundleKey<T> key, @Nullable T value);
  Bundle getBundle();
}
