package com.episode6.hackit.android.preference;

import android.content.SharedPreferences;

import com.google.gson.Gson;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

public class GsonPrefKey {
  public static class Key<T> extends AbstractPrefKey<T> {
    private final Class<T> mObjectClass;

    Key(PrefKeyPath keyPath, Class<T> objectClass, @Nullable Provider<T> defaultInstanceProvider) {
      super(keyPath, defaultInstanceProvider);
      mObjectClass = objectClass;
    }

    public Class<T> getObjectType() {
      return mObjectClass;
    }
  }

  @Singleton
  public static class Translator implements PrefKeyTranslator<Key> {

    private final Gson mGson;

    @Inject
    public Translator(Gson gson) {
      mGson = gson;
    }

    @Override
    public Class<Key> getPrefKeyTypeClass() {
      return Key.class;
    }

    @Override
    public void storeObject(Key key, Object value, SharedPreferences.Editor editor) {
      String path = key.getKeyPath().getPath();
      String jsonValue = mGson.toJson(value);
      editor.putString(path, jsonValue);
    }

    @Nullable
    @Override
    public Object retrieveObject(Key key, SharedPreferences sharedPreferences) {
      String rawValue = sharedPreferences.getString(key.getKeyPath().getPath(), null);
      return mGson.fromJson(rawValue, key.getObjectType());
    }
  }
}
