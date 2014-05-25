package com.episode6.hackit.android.wakelock.impl;

import android.os.PowerManager;

import com.episode6.hackit.android.wakelock.WakeLockKey;
import com.episode6.hackit.android.wakelock.WakeLockManager;
import com.episode6.hackit.chop.Chop;
import com.google.common.collect.Maps;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DefaultWakeLockManager implements WakeLockManager {

  private final PowerManager mPowerManager;

  private final Map<WakeLockKey, PowerManager.WakeLock> mAcquiredWakeLocks = Maps.newHashMap();

  @Inject
  public DefaultWakeLockManager(PowerManager powerManager) {
    mPowerManager = powerManager;
  }

  @Override
  public boolean acquire(WakeLockKey key) {
    Chop.d("Trying to acquire key: %s", key);
    if (isAcquired(key)) {
      return false;
    }

    PowerManager.WakeLock wakeLock =
        mPowerManager.newWakeLock(key.getFlags(), key.getTag());
    wakeLock.acquire();
    mAcquiredWakeLocks.put(key, wakeLock);
    return true;
  }

  @Override
  public boolean release(WakeLockKey key) {
    Chop.d("Trying to release key: %s", key);
    if (!isAcquired(key)) {
      return false;
    }

    PowerManager.WakeLock wakeLock = mAcquiredWakeLocks.remove(key);
    wakeLock.release();
    return true;
  }

  @Override
  public boolean isAcquired(WakeLockKey key) {
    boolean rslt = mAcquiredWakeLocks.containsKey(key);
    Chop.d("key: %s isAcquired: %s", key, String.valueOf(rslt));
    return rslt;
  }
}
