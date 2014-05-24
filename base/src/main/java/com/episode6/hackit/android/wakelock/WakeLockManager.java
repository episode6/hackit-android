package com.episode6.hackit.android.wakelock;

public interface WakeLockManager {

  /**
   * acquire a wake lock. does nothing if wake lock is already acquired for key
   *
   * @param key the key to acquire a wake lock for
   * @return true if acquired a new wakelock, false if already acquired
   */
  boolean acquire(WakeLockKey key);

  /**
   * release the wakelock
   *
   * @param key the key to release the wake lock for
   * @return true if the wakelock was release, false otherwise
   */
  boolean release(WakeLockKey key);

  boolean isAcquired(WakeLockKey key);
}
