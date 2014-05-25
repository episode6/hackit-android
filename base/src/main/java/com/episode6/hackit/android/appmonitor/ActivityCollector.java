package com.episode6.hackit.android.appmonitor;

import android.app.Activity;

import com.episode6.hackit.android.util.Clock;

import java.util.Map;

public interface ActivityCollector {
  public Activity getTopActivity();
  public Map<Activity, ActivityLifespanInfo> getCreatedActivities();
  public Map<Activity, ActivityLifespanInfo> getResumedActivities();
  public <T extends Activity> void loopCreatedActivities(Class<T> activityClass, Looper<T> looper);
  public <T extends Activity> void loopResumedActivities(Class<T> activityClass, Looper<T> looper);

  public static interface Looper<V extends Activity> {

    /**
     * Used to loop through a set of activities, casting them as desired
     *
     * @param activity the activity
     * @return true to break the loop, false to continue
     */
    boolean loop(V activity);
  }

  public static class ActivityLifespanInfo {
    private final Clock mClock;

    private long mCreatedAt = -1;
    private long mResumedAt = -1;
    private long mPausedAt = -1;

    ActivityLifespanInfo(Clock clock) {
      mClock = clock;
      onCreate();
    }

    private long time() {
      return mClock.getElapsedRealTime();
    }

    void onCreate() {
      mCreatedAt = time();
    }

    void onResume() {
      mResumedAt = time();
    }

    void onPause() {
      mPausedAt = time();
    }

    public long getCreatedAt() {
      return mCreatedAt;
    }

    public long getResumedAt() {
      return mResumedAt;
    }

    public long getPausedAt() {
      return mPausedAt;
    }
  }
}
