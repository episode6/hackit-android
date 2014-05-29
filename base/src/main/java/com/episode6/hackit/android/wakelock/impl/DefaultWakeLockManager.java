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
      Chop.d("Already acquired wakelock for key: %s", key);
      return false;
    }

    PowerManager.WakeLock wakeLock =
        mPowerManager.newWakeLock(key.getFlags(), key.getTag());
    wakeLock.acquire();
    mAcquiredWakeLocks.put(key, wakeLock);
    Chop.d("WakeLock acquired: %s", key);
    return true;
  }

  @Override
  public boolean release(WakeLockKey key) {
    Chop.d("Trying to release key: %s", key);
    if (!isAcquired(key)) {
      Chop.d("Can't release wakelock cause we don't have key: %s", key);
      return false;
    }

    PowerManager.WakeLock wakeLock = mAcquiredWakeLocks.remove(key);
    wakeLock.release();
    Chop.d("Wake lock released: %s", key);
    return true;
  }

  @Override
  public boolean isAcquired(WakeLockKey key) {
    return mAcquiredWakeLocks.containsKey(key);
  }
}
