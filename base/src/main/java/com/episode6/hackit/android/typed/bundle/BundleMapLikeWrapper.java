package com.episode6.hackit.android.typed.bundle;

import android.os.Bundle;

import com.episode6.hackit.android.serialize.MapLike;
import com.google.common.base.Preconditions;

import javax.annotation.Nullable;

public class BundleMapLikeWrapper implements MapLike.Getter<Bundle>, MapLike.Setter<Bundle> {

  private final Bundle mBundle;

  public BundleMapLikeWrapper(Bundle bundle) {
    mBundle = Preconditions.checkNotNull(bundle, "Created BundleMapLikeWrapper with null bundle");
  }

  @Override
  public boolean containsKey(String key) {
    return mBundle.containsKey(key);
  }

  @Override
  public int getInt(String key) {
    return mBundle.getInt(key);
  }

  @Override
  public float getFloat(String key) {
    return mBundle.getFloat(key);
  }

  @Override
  public boolean getBool(String key) {
    return mBundle.getBoolean(key);
  }

  @Override
  public long getLong(String key) {
    return mBundle.getLong(key);
  }

  @Nullable
  @Override
  public String getString(String key) {
    return mBundle.getString(key);
  }

  @Override
  public void putInt(String key, int value) {
    mBundle.putInt(key, value);
  }

  @Override
  public void putFloat(String key, float value) {
    mBundle.putFloat(key, value);
  }

  @Override
  public void putBool(String key, boolean value) {
    mBundle.putBoolean(key, value);
  }

  @Override
  public void putLong(String key, long value) {
    mBundle.putLong(key, value);
  }

  @Override
  public void putString(String key, String value) {
    mBundle.putString(key, value);
  }

  @Override
  public void removeKey(String key) {
    mBundle.remove(key);
  }

  @Override
  public Bundle getOriginal() {
    return mBundle;
  }
}
