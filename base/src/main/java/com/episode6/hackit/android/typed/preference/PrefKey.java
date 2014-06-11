package com.episode6.hackit.android.typed.preference;

import com.episode6.hackit.android.serialize.AbstractMapLikeKey;
import com.episode6.hackit.android.serialize.NamespacedKey;
import com.episode6.hackit.android.serialize.SerializeKey;

import javax.annotation.Nullable;

public final class PrefKey<T> extends AbstractMapLikeKey<T> {

  private final @Nullable PrefValueProvider<T> mDefaultInstanceProvider;
  private final boolean mShouldCache;

  PrefKey(
      NamespacedKey nameKey,
      SerializeKey<T> serializeKey,
      @Nullable PrefValueProvider<T> defaultInstanceProvider,
      boolean shouldCache) {
    super(nameKey, serializeKey);

    mDefaultInstanceProvider = defaultInstanceProvider;
    mShouldCache = shouldCache;
  }

  public @Nullable T createDefaultObject(PrefManager prefManager) {
    return mDefaultInstanceProvider == null ? null : mDefaultInstanceProvider.createDefaultValue(prefManager);
  }

  public boolean shouldCache() {
    return mShouldCache;
  }

}
