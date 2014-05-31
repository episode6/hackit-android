package com.episode6.hackit.android.typed.bundle;

import android.os.Bundle;

import com.google.common.base.Optional;

import javax.annotation.Nullable;

public interface TypedBundle {
  <T> Optional<T> get(BundleKey<T> key);
  <T> void set(BundleKey<T> key, @Nullable T value);
  Bundle getBundle();
}
