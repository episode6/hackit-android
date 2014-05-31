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
