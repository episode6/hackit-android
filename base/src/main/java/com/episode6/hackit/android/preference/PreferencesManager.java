package com.episode6.hackit.android.preference;

import android.content.SharedPreferences;

import com.google.common.base.Optional;
import com.google.common.collect.Maps;
import com.google.gson.Gson;

import java.util.Map;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PreferencesManager {

  public static final PrefKeyPath ROOT_KEY_PATH = new PrefKeyPath("/");

  private final SharedPreferences mSharedPreferences;
  private final Gson mGson;

  @Inject
  public PreferencesManager(
      SharedPreferences sharedPreferences,
      Gson gson) {
    mSharedPreferences = sharedPreferences;
    mGson = gson;
  }

  public @Nullable <V> V load(PrefKey<V> key) {
    Class<V> objectType = key.getObjectType();
    PrefKeyPath keyPath = key.getKeyPath();

    String rawValue = mSharedPreferences.getString(keyPath.getPath(), null);
    if (rawValue == null) {
      return null;
    }

    return mGson.fromJson(rawValue, objectType);
  }

  public <V> Optional<V> loadOptional(PrefKey<V> key) {
    return Optional.fromNullable(load(key));
  }

  public Editor edit() {
    return new Editor();
  }

  public class Editor {

    private final Map<PrefKey, Object> mPendingPuts = Maps.newHashMap();

    public <V> Editor put(PrefKey<V> key, V value) {
      mPendingPuts.put(key, value);
      return this;
    }

    public void commit() {
      SharedPreferences.Editor editor = mSharedPreferences.edit();
      for (PrefKey key : mPendingPuts.keySet()) {
        Object value = mPendingPuts.get(key);
        String rawValue = mGson.toJson(value);
        editor.putString(key.getKeyPath().getPath(), rawValue);
      }
      editor.commit();
    }
  }
}
