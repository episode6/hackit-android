package com.episode6.hackit.android.preference;

import com.google.common.collect.Maps;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PrefCache {

  private Map<PrefKey<?>, Object> mCache = Maps.newHashMap();

  @Inject
  public PrefCache() {

  }

  public boolean contains(PrefKey<?> key) {
    return mCache.containsKey(key);
  }

  public Object get(PrefKey<?> key) {
    return mCache.get(key);
  }

  public void put(PrefKey<?> key, Object value) {
    mCache.put(key, value);
  }

}
