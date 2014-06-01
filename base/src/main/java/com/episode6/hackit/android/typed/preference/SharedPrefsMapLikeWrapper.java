package com.episode6.hackit.android.typed.preference;

import android.content.SharedPreferences;

import com.episode6.hackit.android.serialize.MapLike;

import javax.annotation.Nullable;

public class SharedPrefsMapLikeWrapper implements MapLike.Getter<SharedPreferences> {

  private final SharedPreferences mSharedPreferences;

  public SharedPrefsMapLikeWrapper(SharedPreferences sharedPreferences) {
    mSharedPreferences = sharedPreferences;
  }

  @Override
  public boolean containsKey(String key) {
    return mSharedPreferences.contains(key);
  }

  @Override
  public int getInt(String key) {
    return mSharedPreferences.getInt(key, -1);
  }

  @Override
  public float getFloat(String key) {
    return mSharedPreferences.getFloat(key, -1f);
  }

  @Override
  public boolean getBool(String key) {
    return mSharedPreferences.getBoolean(key, false);
  }

  @Override
  public long getLong(String key) {
    return mSharedPreferences.getLong(key, -1L);
  }

  @Nullable
  @Override
  public String getString(String key) {
    return mSharedPreferences.getString(key, null);
  }

  @Override
  public SharedPreferences getOriginal() {
    return mSharedPreferences;
  }

  public static class EditorMapLikeWrapper implements Setter<SharedPreferences.Editor> {

    private final SharedPreferences.Editor mEditor;

    public EditorMapLikeWrapper(SharedPreferences.Editor editor) {
      mEditor = editor;
    }

    @Override
    public void putInt(String key, int value) {
      mEditor.putInt(key, value);
    }

    @Override
    public void putFloat(String key, float value) {
      mEditor.putFloat(key, value);
    }

    @Override
    public void putBool(String key, boolean value) {
      mEditor.putBoolean(key, value);
    }

    @Override
    public void putLong(String key, long value) {
      mEditor.putLong(key, value);
    }

    @Override
    public void putString(String key, String value) {
      mEditor.putString(key, value);
    }

    @Override
    public void removeKey(String key) {
      mEditor.remove(key);
    }

    @Override
    public SharedPreferences.Editor getOriginal() {
      return mEditor;
    }
  }
}
