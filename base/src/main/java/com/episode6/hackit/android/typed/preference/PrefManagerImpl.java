package com.episode6.hackit.android.typed.preference;

import android.content.SharedPreferences;
import android.util.Pair;

import com.episode6.hackit.android.serialize.MapLikeTranslator;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;

import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PrefManagerImpl implements PrefManager {

  private final SharedPrefsMapLikeWrapper mSharedPrefsWrapper;
  private final MapLikeTranslator mMapLikeTranslator;
  private final PrefCache mPrefCache;

  @Inject
  public PrefManagerImpl(
      SharedPrefsMapLikeWrapper sharedPrefsMapLikeWrapper,
      MapLikeTranslator mapLikeTranslator,
      PrefCache prefCache) {

    mSharedPrefsWrapper = sharedPrefsMapLikeWrapper;
    mMapLikeTranslator = mapLikeTranslator;
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

  @SuppressWarnings("unchecked")
  public @Nullable <V> V loadInternal(PrefKey<V> key) {
    synchronized (mPrefCache) {
      if (key.shouldCache() && isCached(key)) {
        return (V) mPrefCache.get(key);
      }

      if (!isPrefPresent(key)) {
        V obj = key.createDefaultObject(this);
        if (key.shouldCache()) {
          mPrefCache.put(key, obj);
        }
        return obj;
      }

      V obj = mMapLikeTranslator.get(mSharedPrefsWrapper, key);
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
      return mMapLikeTranslator.containsKey(mSharedPrefsWrapper, key);
    }
  }

  @Override
  public boolean isPrefPresent(OptionalPrefKey key) {
    return isPrefPresent(key.getRealPrefKey());
  }

  @Override
  public Editor edit() {
    return new Editor(mSharedPrefsWrapper.getOriginal().edit());
  }

  public class Editor implements PrefManager.Editor {

    private final SharedPrefsMapLikeWrapper.EditorMapLikeWrapper mEditor;
    private final List<Pair<PrefKey<?>, Object>> mCachedPuts = Lists.newLinkedList();

    Editor(SharedPreferences.Editor editor) {
      mEditor = new SharedPrefsMapLikeWrapper.EditorMapLikeWrapper(editor);
    }

    @Override
    public <V> Editor put(PrefKey<V> key, @Nullable V value) {
      if (key.shouldCache()) {
        mCachedPuts.add(new Pair<PrefKey<?>, Object>(key, value));
      }
      mMapLikeTranslator.set(mEditor, key, value);
      return this;
    }

    @Override
    public <V> Editor put(OptionalPrefKey<V> key, @Nullable V value) {
      return put(key.getRealPrefKey(), value);
    }

    @Override
    public void commit() {
      synchronized (mPrefCache) {
        for (Pair<PrefKey<?>, Object> pair : mCachedPuts) {
          mPrefCache.put(pair.first, pair.second);
        }
        mEditor.getOriginal().commit();
      }
    }
  }

  private boolean isCached(PrefKey<?> key) {
    return mPrefCache.contains(key);
  }
}
