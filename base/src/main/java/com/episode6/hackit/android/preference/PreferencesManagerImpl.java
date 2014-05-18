package com.episode6.hackit.android.preference;

import android.content.SharedPreferences;
import android.util.Pair;

import com.episode6.hackit.chop.Chop;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;

import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PreferencesManagerImpl implements PreferencesManager {
  private final SharedPreferences mSharedPreferences;
  private final PrefKeyTranslatorSet mPrefKeyTranslatorSet;
  private final PrefCache mPrefCache;

  @Inject
  public PreferencesManagerImpl(
      SharedPreferences sharedPreferences,
      PrefKeyTranslatorSet prefKeyTranslatorSet,
      PrefCache prefCache) {
    mSharedPreferences = sharedPreferences;
    mPrefKeyTranslatorSet = prefKeyTranslatorSet;
    mPrefCache = prefCache;
  }

  @Override
  public <V> V load(PrefKey<V> key) {
    V obj = loadInternal(key);
    if (obj == null) {
      throw new NullPointerException("Unexpected null preference loaded: " + key.toString());
    }
    return obj;
  }

  @Override
  public <V> Optional<V> load(OptionalPrefKey<V> key) {
    return Optional.fromNullable(loadInternal(key.getRealPrefKey()));
  }

  public @Nullable <V> V loadInternal(PrefKey<V> key) {
    synchronized (mPrefCache) {
      if (key.shouldCache() && isCached(key)) {
        Chop.d("Cache hit for: %s", key);
        return (V) mPrefCache.get(key);
      }

      if (!isPrefPresent(key)) {
        V obj = key.createDefaultObject();
        if (key.shouldCache()) {
          mPrefCache.put(key, obj);
        }
        return obj;
      }

      V obj = (V) getTranslator(key).retrieveObject(key, mSharedPreferences, key.getKeyPath().getPath());
      if (key.shouldCache()) {
        mPrefCache.put(key, obj);
      }
      return obj;
    }
  }

  @Override
  public boolean isPrefPresent(PrefKey key) {
    synchronized (mPrefCache) {
      if (key.shouldCache() && mPrefCache.contains(key)) {
        return true;
      }
      return mSharedPreferences.contains(key.getKeyPath().getPath());
    }
  }

  @Override
  public boolean isPrefPresent(OptionalPrefKey key) {
    return isPrefPresent(key.getRealPrefKey());
  }

  @Override
  public Editor edit() {
    return new Editor(mSharedPreferences.edit());
  }

  public class Editor implements PreferencesManager.Editor {

    private final SharedPreferences.Editor mEditor;
    private final List<Pair<PrefKey<?>, Object>> mCachedPuts = Lists.newLinkedList();

    Editor(SharedPreferences.Editor editor) {
      mEditor = editor;
    }

    @Override
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

    @Override
    public <V> Editor put(OptionalPrefKey<V> key, V value) {
      return put(key.getRealPrefKey(), value);
    }

    @Override
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
