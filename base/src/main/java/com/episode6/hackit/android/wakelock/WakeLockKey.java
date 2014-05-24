package com.episode6.hackit.android.wakelock;

import com.episode6.hackit.android.util.StringFormat;

import java.util.UUID;

public class WakeLockKey {

  public static WakeLockKey key(int flags) {
    return new WakeLockKey(flags);
  }

  private final int mFlags;
  private final String mTag;

  WakeLockKey(int flags) {
    mFlags = flags;
    mTag = UUID.randomUUID().toString();
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
