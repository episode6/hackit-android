package com.episode6.hackit.android.preference;

import android.content.SharedPreferences;
import android.util.Pair;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * Singleton provided by prefs module
 */
public class PreferencesManager {

  public static final PrefKeyPath ROOT_KEY_PATH = new PrefKeyPath("/");
  private final SharedPreferences mSharedPreferences;
  private final PrefKeyTranslatorSet mPrefKeyTranslatorSet;
  private final PrefCache mPrefCache;

  public PreferencesManager(
      SharedPreferences sharedPreferences,
      PrefKeyTranslatorSet prefKeyTranslatorSet,
      PrefCache prefCache) {
    mSharedPreferences = sharedPreferences;
    mPrefKeyTranslatorSet = prefKeyTranslatorSet;
    mPrefCache = prefCache;
  }

  /**
   * Technically this method can return a null object. However we protect against this by not
   * allowing users to build a PrefKey with a null default instance unless they are building an
   * OptionalPrefKey (which would use the other load method).
   */
  public <V> V load(PrefKey<V> key) {
    synchronized (mPrefCache) {
      if (key.shouldCache() && isCached(key)) {
        return (V) mPrefCache.get(key);
      }

      if (!isPrefPresent(key)) {
        return key.createDefaultObject();
      }

      V object = (V) getTranslator(key).retrieveObject(key, mSharedPreferences, key.getKeyPath().getPath());
      return object;
    }
  }

  public <V> Optional<V> load(OptionalPrefKey<V> key) {
    return Optional.fromNullable(load(key.getRealPrefKey()));
  }

  public boolean isPrefPresent(PrefKey key) {
    synchronized (mPrefCache) {
      return mSharedPreferences.contains(key.getKeyPath().getPath());
    }
  }

  public boolean isPrefPresent(OptionalPrefKey key) {
    return isPrefPresent(key.getRealPrefKey());
  }

  public Editor edit() {
    return new Editor(mSharedPreferences.edit());
  }

  public class Editor {

    private final SharedPreferences.Editor mEditor;
    private final List<Pair<PrefKey<?>, Object>> mCachedPuts = Lists.newLinkedList();

    Editor(SharedPreferences.Editor editor) {
      mEditor = editor;
    }

    public <V> Editor put(PrefKey<V> key, V value) {
      String sharedPrefKey = key.getKeyPath().getPath();
      if (key.shouldCache()) {
        mCachedPuts.add(new Pair<PrefKey<?>, Object>(key, value));
      }

      if (value == null) {
        mEditor.remove(sharedPrefKey);
        return this;
      }

      getTranslator(key).storeObject(key, value, mEditor, sharedPrefKey);
      return this;
    }

    public <V> Editor put(OptionalPrefKey<V> key, V value) {
      return put(key.getRealPrefKey(), value);
    }

    public void commit() {
      synchronized (mPrefCache) {
        for (Pair<PrefKey<?>, Object> pair : mCachedPuts) {
          mPrefCache.put(pair.first, pair.second);
        }
        mEditor.commit();
      }
    }
  }

  private PrefKeyTranslator getTranslator(PrefKey<?> key) {
    return mPrefKeyTranslatorSet.getTranslatorForKey(key);
  }

  private boolean isCached(PrefKey<?> key) {
    return mPrefCache.contains(key);
  }
}
