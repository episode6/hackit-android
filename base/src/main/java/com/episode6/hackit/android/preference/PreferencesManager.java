package com.episode6.hackit.android.preference;

import android.content.SharedPreferences;

import com.google.common.base.Optional;
import com.google.common.collect.Maps;

import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Singleton provided by prefs module
 */
public class PreferencesManager {

  public static final PrefKeyPath ROOT_KEY_PATH = new PrefKeyPath("/");

  private final Map<Class<? extends  PrefKey>, PrefKeyTranslator<PrefKey<?>>> mPrefKeyTranslatorMap = Maps.newHashMap();
  private final SharedPreferences mSharedPreferences;

  public PreferencesManager(
      SharedPreferences sharedPreferences,
      Set<PrefKeyTranslator> prefKeyTranslators) {
    mSharedPreferences = sharedPreferences;

    for (PrefKeyTranslator translator : prefKeyTranslators) {
      mPrefKeyTranslatorMap.put(translator.getPrefKeyTypeClass(), translator);
    }
  }

  public @Nullable <V> V load(PrefKey<V> key) {
    if (!isPrefPresent(key)) {
      return key.createDefaultObject();
    }
    V object = (V) getTranslator(key).retrieveObject(key, mSharedPreferences, key.getKeyPath().getPath());
    return object;
  }

  public <V> Optional<V> loadOptional(PrefKey<V> key) {
    return Optional.fromNullable(load(key));
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
      String sharedPrefKey = key.getKeyPath().getPath();
      if (value == null) {
        mEditor.remove(key.getKeyPath().getPath());
        return this;
      }

      getTranslator(key).storeObject(key, value, mEditor, sharedPrefKey);
      return this;
    }

    public void commit() {
      mEditor.commit();
    }
  }

  private PrefKeyTranslator getTranslator(PrefKey<?> key) {
    PrefKeyTranslator translator = mPrefKeyTranslatorMap.get(key.getClass());
    if (translator == null) {
      throw new UnsupportedOperationException();
    }
    return translator;
  }
}
