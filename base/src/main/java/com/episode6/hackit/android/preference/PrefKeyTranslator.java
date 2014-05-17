package com.episode6.hackit.android.preference;

import android.content.SharedPreferences;

import javax.annotation.Nullable;

public interface PrefKeyTranslator<V extends PrefKey> {
  Class<V> getPrefKeyTypeClass();
  void storeObject(V key, Object value, SharedPreferences.Editor editor);
  @Nullable <T> T retrieveObject(V key, SharedPreferences sharedPreferences);
}
