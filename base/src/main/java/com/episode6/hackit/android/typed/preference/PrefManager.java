package com.episode6.hackit.android.typed.preference;

import com.google.common.base.Optional;

import javax.annotation.Nullable;

public interface PrefManager {
  public static final PrefKeyPath ROOT_KEY_PATH = new PrefKeyPath("/");

  public <V> V load(PrefKey<V> key);
  public <V> Optional<V> load(OptionalPrefKey<V> key);
  public boolean isPrefPresent(PrefKey key);
  public boolean isPrefPresent(OptionalPrefKey key);
  public Editor edit();

  public interface Editor {
    public <V> Editor put(PrefKey<V> key, @Nullable V value);
    public <V> Editor put(OptionalPrefKey<V> key, @Nullable V value);
    public void commit();
  }
}
