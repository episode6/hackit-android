package com.episode6.hackit.android.preference;

import android.content.SharedPreferences;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

public class BasicPrefKey {

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

    @Inject
    public Translator() {}

    @Override
    public Class<Key> getPrefKeyTypeClass() {
      return Key.class;
    }

    @Override
    public void storeObject(Key key, Object value, SharedPreferences.Editor editor) {
      String prefKey = key.getKeyPath().getPath();

      if (value == null) {
        editor.remove(prefKey);
      } else if (key.getObjectType() == Boolean.class) {
        editor.putBoolean(prefKey, (Boolean) value);
      } else if (key.getObjectType() == Integer.class) {
        editor.putInt(prefKey, (Integer) value);
      } else if (key.getObjectType() == Long.class) {
        editor.putLong(prefKey, (Long) value);
      } else if (key.getObjectType() == Float.class) {
        editor.putFloat(prefKey, (Float) value);
      } else if (key.getObjectType() == String.class) {
        editor.putString(prefKey, (String) value);
      }
    }

    @Nullable
    @Override
    public Object retrieveObject(Key key, SharedPreferences sharedPreferences) {
      String prefKey = key.getKeyPath().getPath();

      if (!sharedPreferences.contains(prefKey)) {
        return  null;
      }

      if (key.getObjectType() == Boolean.class) {
        return sharedPreferences.getBoolean(prefKey, false);
      } else if (key.getObjectType() == Integer.class) {
        return sharedPreferences.getInt(prefKey, -1);
      } else if (key.getObjectType() == Long.class) {
        return sharedPreferences.getLong(prefKey, -1);
      } else if (key.getObjectType() == Float.class) {
        return sharedPreferences.getFloat(prefKey, -1f);
      } else if (key.getObjectType() == String.class) {
        return sharedPreferences.getString(prefKey, null);
      }

      return null;
    }
  }
}
