package com.episode6.hackit.android.typed.intent;

import android.content.Intent;

import com.episode6.hackit.android.serialize.MapLike;
import com.google.common.base.Preconditions;

import javax.annotation.Nullable;

public class IntentMapLikeWrapper implements MapLike.Getter<Intent>, MapLike.Setter<Intent> {

  private final Intent mIntent;

  public IntentMapLikeWrapper(Intent intent) {
    mIntent = Preconditions.checkNotNull(intent, "Can't create IntentMapLikeWrapper with null intent");
  }

  @Override
  public boolean containsKey(String key) {
    return mIntent.hasExtra(key);
  }

  @Override
  public int getInt(String key) {
    return mIntent.getIntExtra(key, -1);
  }

  @Override
  public float getFloat(String key) {
    return mIntent.getFloatExtra(key, -1f);
  }

  @Override
  public boolean getBool(String key) {
    return mIntent.getBooleanExtra(key, false);
  }

  @Override
  public long getLong(String key) {
    return mIntent.getLongExtra(key, -1L);
  }

  @Nullable
  @Override
  public String getString(String key) {
    return mIntent.getStringExtra(key);
  }

  @Override
  public void putInt(String key, int value) {
    mIntent.putExtra(key, value);
  }

  @Override
  public void putFloat(String key, float value) {
    mIntent.putExtra(key, value);
  }

  @Override
  public void putBool(String key, boolean value) {
    mIntent.putExtra(key, value);
  }

  @Override
  public void putLong(String key, long value) {
    mIntent.putExtra(key, value);
  }

  @Override
  public void putString(String key, String value) {
    mIntent.putExtra(key, value);
  }

  @Override
  public void removeKey(String key) {
    mIntent.removeExtra(key);
  }

  @Override
  public Intent getOriginal() {
    return mIntent;
  }
}
