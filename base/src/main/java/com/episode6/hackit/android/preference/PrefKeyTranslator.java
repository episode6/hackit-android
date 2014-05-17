package com.episode6.hackit.android.preference;

import android.content.SharedPreferences;

import javax.annotation.Nullable;

public interface PrefKeyTranslator<V extends PrefKey> {
  Class<V> getPrefKeyTypeClass();
  void storeObject(V key, Object value, SharedPreferences.Editor editor);

  /**
   * As far as I can tell, java does not have a way to define a generic type AS a generic type.
   * Because of this we cannot define the V key variable as we want to which is V<T>
   *
   * However, since the only call-site of this method should be the PreferencesManager itself
   * we'll consider such casting as safe.
   */
  @Nullable Object retrieveObject(V key, SharedPreferences sharedPreferences);
}
