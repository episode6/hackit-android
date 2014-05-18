package com.episode6.hackit.android.preference;

import android.content.SharedPreferences;
import android.util.Pair;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;

import java.util.List;

public interface PrefManager {
  public static final PrefKeyPath ROOT_KEY_PATH = new PrefKeyPath("/");

  public <V> V load(PrefKey<V> key);
  public <V> Optional<V> load(OptionalPrefKey<V> key);
  public boolean isPrefPresent(PrefKey key);
  public boolean isPrefPresent(OptionalPrefKey key);
  public Editor edit();

  public interface Editor {
    public <V> Editor put(PrefKey<V> key, V value);
    public <V> Editor put(OptionalPrefKey<V> key, V value);
    public void commit();
  }
}
