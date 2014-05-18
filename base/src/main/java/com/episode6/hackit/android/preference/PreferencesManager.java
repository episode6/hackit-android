package com.episode6.hackit.android.preference;

import android.content.SharedPreferences;

import com.google.common.base.Optional;

import javax.annotation.Nullable;

/**
 * Singleton provided by prefs module
 */
public class PreferencesManager {

  public static final PrefKeyPath ROOT_KEY_PATH = new PrefKeyPath("/");
  private final SharedPreferences mSharedPreferences;
  private final PrefKeyTranslatorSet mPrefKeyTranslatorSet;

  public PreferencesManager(
      SharedPreferences sharedPreferences,
      PrefKeyTranslatorSet prefKeyTranslatorSet) {
    mSharedPreferences = sharedPreferences;
    mPrefKeyTranslatorSet = prefKeyTranslatorSet;
  }

  public @Nullable <V> V load(PrefKey<V> key) {
    if (!isPrefPresent(key)) {
      return key.createDefaultObject();
    }
    V object = (V) getTranslator(key).retrieveObject(key, mSharedPreferences, key.getKeyPath().getPath());
    return object;
  }

  public <V> Optional<V> load(OptionalPrefKey<V> key) {
    return Optional.fromNullable(load(key.getRealPrefKey()));
  }

  public boolean isPrefPresent(PrefKey key) {
    return mSharedPreferences.contains(key.getKeyPath().getPath());
  }

  public boolean isPrefPresent(OptionalPrefKey key) {
    return isPrefPresent(key.getRealPrefKey());
  }

  public Editor edit() {
    return new Editor(mSharedPreferences.edit());
  }

  public class Editor {

    private final SharedPreferences.Editor mEditor;

    Editor(SharedPreferences.Editor editor) {
      mEditor = editor;
    }

    public <V> Editor put(PrefKey<V> key, V value) {
      String sharedPrefKey = key.getKeyPath().getPath();
      if (value == null) {
        mEditor.remove(key.getKeyPath().getPath());
        return this;
      }

      getTranslator(key).storeObject(key, value, mEditor, sharedPrefKey);
      return this;
    }

    public <V> Editor put(OptionalPrefKey<V> key, V value) {
      return put(key.getRealPrefKey(), value);
    }

    public void commit() {
      mEditor.commit();
    }
  }

  private PrefKeyTranslator getTranslator(PrefKey<?> key) {
    return mPrefKeyTranslatorSet.getTranslatorForKey(key);
  }
}
