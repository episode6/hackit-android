package com.episode6.hackit.android.testing.inject;

import com.episode6.hackit.chop.Chop;
import com.google.common.collect.Maps;

import org.mockito.Mockito;

import java.util.HashSet;
import java.util.Map;

import javax.inject.Provider;

import dagger.Lazy;

public class MockDependencyMap {

  private Map<BindKey, Object> mInstanceMap = Maps.newHashMap();

  public void set(BindKey key, Object instance) {
    if (containsKey(key)) {
      throw new IllegalArgumentException("Already added BindKey to dependency map: " + key.toString());
    }
    mInstanceMap.put(key, instance);
  }

  public Object get(BindKey key) {
    if (!containsKey(key)) {
      try {
        set(key, createObject(key));
      } catch (Throwable t) {
        throw new RuntimeException("Error creating object for key: " + key.toString());
      }
    }
    return mInstanceMap.get(key);
  }

  public boolean containsKey(BindKey key) {
    return mInstanceMap.containsKey(key);
  }

  protected Object createDefaultObject(BindKey key) {
    Chop.d("createDefaultObject: %s", key);
    return Mockito.mock(key.objectClass);
  }

  private Object createObject(final BindKey key) {
    if (key.isProvider()) {
      return new Provider() {
        @Override
        public Object get() {
          return MockDependencyMap.this.get(key.normalVersion());
        }
      };
    } else if (key.isLazy()) {
      return new Lazy() {
        @Override
        public Object get() {
          return MockDependencyMap.this.get(key.normalVersion());
        }
      };
    } else if (key.isSet()) {
      return new HashSet();
    } else if (key.isOfType(Integer.class)) {
      return 0;
    } else if (key.isOfType(Float.class)) {
      return Float.valueOf(0f);
    } else if (key.isOfType(Long.class)) {
      return Long.valueOf(0L);
    } else if (key.isOfType(Double.class)) {
      return Double.valueOf(0.0);
    } else if (key.isOfType(Short.class)) {
      return Short.valueOf((short) 0);
    } else if (key.isOfType(Byte.class)) {
      return Byte.valueOf((byte) 0);
    } else if (key.isOfType(Character.class)) {
      return Character.valueOf((char) 0);
    } else if (key.isOfType(String.class)) {
      return "";
    }
    return createDefaultObject(key);
  }
}
