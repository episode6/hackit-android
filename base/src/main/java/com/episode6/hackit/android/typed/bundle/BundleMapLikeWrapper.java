package com.episode6.hackit.android.typed.bundle;

import android.os.Bundle;

import com.episode6.hackit.android.serialize.MapLike;

import javax.annotation.Nullable;

public class BundleMapLikeWrapper implements MapLike.Getter<Bundle>, MapLike.Setter<Bundle> {

  private final Bundle mBundle;

  public BundleMapLikeWrapper(Bundle bundle) {
    mBundle = bundle;
  }

  @Override
  public boolean containsKey(String key) {
    return mBundle.containsKey(key);
  }

  @Nullable
  @Override
  public String getString(String key) {
    return mBundle.getString(key);
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
