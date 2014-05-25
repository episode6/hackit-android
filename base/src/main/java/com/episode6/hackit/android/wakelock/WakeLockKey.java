package com.episode6.hackit.android.wakelock;

import com.episode6.hackit.android.util.StringFormat;

import java.util.UUID;

import javax.annotation.Nullable;

public class WakeLockKey {

  public static WakeLockKey key(int flags) {
    return new WakeLockKey(flags, null);
  }

  public static WakeLockKey key(int flags, Class<?> tag) {
    return new WakeLockKey(flags, tag.getSimpleName());
  }

  private final int mFlags;
  private final String mTag;

  WakeLockKey(int flags, @Nullable String tag) {
    mFlags = flags;
    mTag = tag != null ? tag : UUID.randomUUID().toString();
  }

  public int getFlags() {
    return mFlags;
  }

  public String getTag() {
    return mTag;
  }

  @Override
  public String toString() {
    return StringFormat.of("WakeLockKey: %s", getTag());
  }
}
