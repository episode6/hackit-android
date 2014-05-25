package com.episode6.hackit.android.preference;

import android.content.SharedPreferences;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

public class BasicPrefKey {

  public static boolean supportsObject(Class<?> objectType) {
    return objectType == Boolean.class ||
        objectType == Integer.class ||
        objectType == Long.class ||
        objectType == Float.class ||
        objectType == String.class;
  }

  public static class Key<T> extends AbstractPrefKey<T> {
    Key(PrefKeyPath keyPath, Class<T> objectClass, @Nullable PrefValueProvider<T> defaultInstanceProvider, boolean shouldCache) {
      super(keyPath, objectClass, defaultInstanceProvider, shouldCache);
    }
  }

  @Singleton
  public static class Translator implements PrefKeyTranslator<Key> {

    @Inject
    public Translator() {}

    @Override
    public Class<Key> getPrefKeyTypeClass() {
      return Key.class;
    }

    @Override
    public void storeObject(Key key, Object value, SharedPreferences.Editor editor, String sharedPrefKey) {
      Class<?> objectType = key.getObjectType();
      if (objectType == Boolean.class) {
        editor.putBoolean(sharedPrefKey, (Boolean) value);
      } else if (objectType == Integer.class) {
        editor.putInt(sharedPrefKey, (Integer) value);
      } else if (objectType == Long.class) {
        editor.putLong(sharedPrefKey, (Long) value);
      } else if (objectType == Float.class) {
        editor.putFloat(sharedPrefKey, (Float) value);
      } else if (objectType == String.class) {
        editor.putString(sharedPrefKey, (String) value);
      }
    }

    @Nullable
    @Override
    public Object retrieveObject(Key key, SharedPreferences sharedPreferences, String sharedPrefKey) {
      Class<?> objectType = key.getObjectType();
      if (objectType == Boolean.class) {
        return sharedPreferences.getBoolean(sharedPrefKey, false);
      } else if (objectType == Integer.class) {
        return sharedPreferences.getInt(sharedPrefKey, -1);
      } else if (objectType == Long.class) {
        return sharedPreferences.getLong(sharedPrefKey, -1);
      } else if (objectType == Float.class) {
        return sharedPreferences.getFloat(sharedPrefKey, -1f);
      } else if (objectType == String.class) {
        return sharedPreferences.getString(sharedPrefKey, null);
      }

      return null;
    }
  }
}
