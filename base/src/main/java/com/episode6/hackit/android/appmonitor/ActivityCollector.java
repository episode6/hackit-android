package com.episode6.hackit.android.appmonitor;

import android.app.Activity;

import com.episode6.hackit.android.app.events.ActivityEvents;
import com.episode6.hackit.android.util.Clock;
import com.google.common.collect.ImmutableMap;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.WeakHashMap;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ActivityCollector {

  private final Bus mBus;
  private final Clock mClock;

  private final EventHandler mEventHandler =
      new EventHandler();
  private final WeakHashMap<Activity, ActivityLifespanInfo> mCreatedActivities =
      new WeakHashMap<Activity, ActivityLifespanInfo>();
  private final WeakHashMap<Activity, ActivityLifespanInfo> mResumedActivities =
      new WeakHashMap<Activity, ActivityLifespanInfo>();

  private @Nullable WeakReference<Activity> mTopActivity;

  @Inject
  ActivityCollector(Bus bus, Clock clock) {
    mBus = bus;
    mClock = clock;
    bus.register(mEventHandler);
  }

  public Activity getTopActivity() {
    if (mTopActivity != null) {
      return mTopActivity.get();
    }
    return null;
  }

  public Map<Activity, ActivityLifespanInfo> getCreatedActivities() {
    return ImmutableMap.copyOf(mCreatedActivities);
  }

  public Map<Activity, ActivityLifespanInfo> getResumedActivities() {
    return ImmutableMap.copyOf(mResumedActivities);
  }

  private class EventHandler {

    @Subscribe
    public void onActivityCreated(ActivityEvents.OnCreate onCreateEvent) {
      mCreatedActivities.put(onCreateEvent.activity, new ActivityLifespanInfo(mClock));
    }

    @Subscribe
    public void onActivityResumed(ActivityEvents.OnResume onResumeEvent) {
      ActivityLifespanInfo activityLifespanInfo = mCreatedActivities.get(onResumeEvent.activity);
      activityLifespanInfo.onResume();
      mResumedActivities.put(onResumeEvent.activity, activityLifespanInfo);
      mTopActivity = new WeakReference<Activity>(onResumeEvent.activity);
    }

    @Subscribe
    public void onActivityPaused(ActivityEvents.OnPause onPauseEvent) {
      ActivityLifespanInfo activityLifespanInfo = mResumedActivities.remove(onPauseEvent.activity);
      activityLifespanInfo.onPause();
      if (mTopActivity != null && mTopActivity.get() == onPauseEvent.activity) {
        mTopActivity = null;
      }
    }

    @Subscribe
    public void onActivityDestroyed(ActivityEvents.OnDestroy onDestroyEvent) {
      mCreatedActivities.remove(onDestroyEvent.activity);
    }
  }

  public static class ActivityLifespanInfo {
    private final Clock mClock;

    private long mCreatedAt = -1;
    private long mResumedAt = -1;
    private long mPausedAt = -1;

    private ActivityLifespanInfo(Clock clock) {
      mClock = clock;
      onCreate();
    }

    private long time() {
      return mClock.getElapsedRealTime();
    }

    private void onCreate() {
      mCreatedAt = time();
    }

    private void onResume() {
      mResumedAt = time();
    }

    private void onPause() {
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
