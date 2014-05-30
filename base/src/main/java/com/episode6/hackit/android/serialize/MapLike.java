package com.episode6.hackit.android.serialize;

import javax.annotation.Nullable;

public final class MapLike {

  public interface Getter {
    boolean containsKey(String key);
    @Nullable String getString(String key);
  }

  public interface Setter {
    void putString(String key, @Nullable String value);
  }
}
