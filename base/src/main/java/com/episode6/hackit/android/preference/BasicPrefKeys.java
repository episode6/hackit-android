package com.episode6.hackit.android.preference;

import android.content.SharedPreferences;

import com.google.common.collect.ImmutableList;

import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Provider;

public class BasicPrefKeys {

  public static List<PrefKeyTranslator> getTranslators() {
    return ImmutableList.<PrefKeyTranslator>of(
        new AbstractTranslator<BoolPrefKey>() {

          @Override
          public Class<BoolPrefKey> getPrefKeyTypeClass() {
            return BoolPrefKey.class;
          }
        },
        new AbstractTranslator<IntPrefKey>() {

          @Override
          public Class<IntPrefKey> getPrefKeyTypeClass() {
            return IntPrefKey.class;
          }
        },
        new AbstractTranslator<LongPrefKey>() {

          @Override
          public Class<LongPrefKey> getPrefKeyTypeClass() {
            return LongPrefKey.class;
          }
        },
        new AbstractTranslator<FloatPrefKey>() {

          @Override
          public Class<FloatPrefKey> getPrefKeyTypeClass() {
            return FloatPrefKey.class;
          }
        },
        new AbstractTranslator<StringPrefKey>() {

          @Override
          public Class<StringPrefKey> getPrefKeyTypeClass() {
            return StringPrefKey.class;
          }
        }
    );
  }

  public static class BoolPrefKey extends AbstractPrefKey<Boolean> {
    BoolPrefKey(PrefKeyPath keyPath, @Nullable Provider<Boolean> defaultInstanceProvider) {
      super(keyPath, defaultInstanceProvider);
    }
  }

  public static class IntPrefKey extends AbstractPrefKey<Integer> {
    IntPrefKey(PrefKeyPath keyPath, @Nullable Provider<Integer> defaultInstanceProvider) {
      super(keyPath, defaultInstanceProvider);
    }
  }

  public static class LongPrefKey extends AbstractPrefKey<Long> {
    LongPrefKey(PrefKeyPath keyPath, @Nullable Provider<Long> defaultInstanceProvider) {
      super(keyPath, defaultInstanceProvider);
    }
  }

  public static class FloatPrefKey extends AbstractPrefKey<Float> {
    FloatPrefKey(PrefKeyPath keyPath, @Nullable Provider<Float> defaultInstanceProvider) {
      super(keyPath, defaultInstanceProvider);
    }
  }

  public static class StringPrefKey extends AbstractPrefKey<String> {
    StringPrefKey(PrefKeyPath keyPath, @Nullable Provider<String> defaultInstanceProvider) {
      super(keyPath, defaultInstanceProvider);
    }
  }

  private static abstract class AbstractTranslator<V extends PrefKey> implements PrefKeyTranslator<V> {
    @Override
    public void storeObject(V key, Object value, SharedPreferences.Editor editor) {
      String prefKey = key.getKeyPath().getPath();

      if (value == null) {
        editor.remove(prefKey);
      } else if (key instanceof BoolPrefKey) {
        editor.putBoolean(prefKey, (Boolean) value);
      } else if (key instanceof IntPrefKey) {
        editor.putInt(prefKey, (Integer) value);
      } else if (key instanceof LongPrefKey) {
        editor.putLong(prefKey, (Long) value);
      } else if (key instanceof FloatPrefKey) {
        editor.putFloat(prefKey, (Float) value);
      } else if (key instanceof StringPrefKey) {
        editor.putString(prefKey, (String) value);
      }
    }

    @Nullable
    @Override
    public Object retrieveObject(V key, SharedPreferences sharedPreferences) {
      String prefKey = key.getKeyPath().getPath();

      if (!sharedPreferences.contains(prefKey)) {
        return  null;
      }

      if (key instanceof BoolPrefKey) {
        return sharedPreferences.getBoolean(prefKey, false);
      } else if (key instanceof IntPrefKey) {
        return sharedPreferences.getInt(prefKey, -1);
      } else if (key instanceof LongPrefKey) {
        return sharedPreferences.getLong(prefKey, -1);
      } else if (key instanceof FloatPrefKey) {
        return sharedPreferences.getFloat(prefKey, -1f);
      } else if (key instanceof StringPrefKey) {
        return sharedPreferences.getString(prefKey, null);
      }

      return null;
    }
  }
}
