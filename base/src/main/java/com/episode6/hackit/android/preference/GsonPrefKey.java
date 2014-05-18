package com.episode6.hackit.android.preference;

import android.content.SharedPreferences;

import com.google.gson.Gson;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

public class GsonPrefKey {
  public static class Key<T> extends AbstractPrefKey<T> {
    Key(PrefKeyPath keyPath, Class<T> objectClass, @Nullable Provider<T> defaultInstanceProvider) {
      super(keyPath, objectClass, defaultInstanceProvider);
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
    public void storeObject(Key key, Object value, SharedPreferences.Editor editor, String sharedPrefKey) {
      String jsonValue = mGson.toJson(value);
      editor.putString(sharedPrefKey, jsonValue);
    }

    @Nullable
    @Override
    public Object retrieveObject(Key key, SharedPreferences sharedPreferences, String sharedPrefKey) {
      String rawValue = sharedPreferences.getString(sharedPrefKey, null);
      return mGson.fromJson(rawValue, key.getObjectType());
    }
  }
}
