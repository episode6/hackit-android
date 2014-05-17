package com.episode6.hackit.android.preference;

import android.content.SharedPreferences;

import com.google.common.base.Optional;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PreferencesManager {

  public static final PrefKeyPath ROOT_KEY_PATH = new PrefKeyPath("/");

  private final SharedPreferences mSharedPreferences;
  private final GsonPrefKey.Translator mSimpleTranslator;

  @Inject
  public PreferencesManager(
      SharedPreferences sharedPreferences,
      GsonPrefKey.Translator simpleTranslator) {
    mSharedPreferences = sharedPreferences;
    mSimpleTranslator = simpleTranslator;
  }

  public @Nullable <V> V load(PrefKey<V> key) {
    if (!mSimpleTranslator.getPrefKeyTypeClass().isInstance(key)) {
      throw new UnsupportedOperationException();
    }

    return mSimpleTranslator.retrieveObject((GsonPrefKey.Key) key, mSharedPreferences);
  }

  public <V> Optional<V> loadOptional(PrefKey<V> key) {
    return Optional.fromNullable(load(key));
  }

  public boolean isPrefNull(PrefKey key) {
    return mSharedPreferences.getString(key.getKeyPath().getPath(), null) == null;
  }

  public boolean isPrefPresent(PrefKey key) {
    return mSharedPreferences.contains(key.getKeyPath().getPath());
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
      if (!mSimpleTranslator.getPrefKeyTypeClass().isInstance(key)) {
        throw new UnsupportedOperationException();
      }
      mSimpleTranslator.storeObject((GsonPrefKey.Key) key, value, mEditor);
      return this;
    }

    public void commit() {
      mEditor.commit();
    }
  }
}
