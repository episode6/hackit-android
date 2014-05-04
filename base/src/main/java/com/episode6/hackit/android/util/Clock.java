package com.episode6.hackit.android.util;

import android.os.SystemClock;

import javax.inject.Inject;

public class Clock {

  @Inject
  public Clock() {

  }

  public long getWallTime() {
    return System.currentTimeMillis();
  }

  public long getElapsedRealTime() {
    return SystemClock.elapsedRealtime();
  }

  public long getElapsedRealTimeNanos() {
    return System.nanoTime();
  }
}
