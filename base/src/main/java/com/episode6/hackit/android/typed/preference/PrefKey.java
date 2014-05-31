package com.episode6.hackit.android.typed.preference;

import com.episode6.hackit.android.serialize.AbstractMapLikeKey;
import com.episode6.hackit.android.serialize.SerializeKey;

import javax.annotation.Nullable;

public final class PrefKey<T> extends AbstractMapLikeKey<T> {

  private final PrefKeyPath mKeyPath;
  private final @Nullable PrefValueProvider<T> mDefaultInstanceProvider;
  private final boolean mShouldCache;

  PrefKey(
      SerializeKey<T> serializeKey,
      PrefKeyPath path,
      @Nullable PrefValueProvider<T> defaultInstanceProvider,
      boolean shouldCache) {
    super(serializeKey);

    mKeyPath = path;
    mDefaultInstanceProvider = defaultInstanceProvider;
    mShouldCache = shouldCache;
  }

  public @Nullable T createDefaultObject(PrefManager prefManager) {
    return mDefaultInstanceProvider == null ? null : mDefaultInstanceProvider.createDefaultValue(prefManager);
  }

  public boolean shouldCache() {
    return mShouldCache;
  }

  @Override
  public String getKeyString() {
    return mKeyPath.getPath();
  }
}
