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
public class ActivityCollectorImpl implements ActivityCollector {

  private final Bus mBus;
  private final Clock mClock;

  private final EventHandler mEventHandler =
      new EventHandler();
  private final WeakHashMap<Activity, ActivityLifespanInfo> mCreatedActivities =
      new WeakHashMap<Activity, ActivityLifespanInfo>();
  private final WeakHashMap<Activity, ActivityLifespanInfo> mResumedActivities =
      new WeakHashMap<Activity, ActivityLifespanInfo>();

  private
  @Nullable
  WeakReference<Activity> mTopActivity;

  @Inject
  public ActivityCollectorImpl(Bus bus, Clock clock) {
    mBus = bus;
    mClock = clock;
    bus.register(mEventHandler);
  }

  @Override
  public Activity getTopActivity() {
    if (mTopActivity != null) {
      return mTopActivity.get();
    }
    return null;
  }

  @Override
  public Map<Activity, ActivityLifespanInfo> getCreatedActivities() {
    return ImmutableMap.copyOf(mCreatedActivities);
  }

  @Override
  public Map<Activity, ActivityLifespanInfo> getResumedActivities() {
    return ImmutableMap.copyOf(mResumedActivities);
  }

  @Override
  public <T extends Activity> void loopCreatedActivities(Class<T> activityClass, Looper<T> looper) {
    for (Activity activity : getCreatedActivities().keySet()) {
      if (activityClass.isInstance(activity)) {
        looper.loop((T) activity);
      }
    }
  }

  @Override
  public <T extends Activity> void loopResumedActivities(Class<T> activityClass, Looper<T> looper) {
    for (Activity activity : getResumedActivities().keySet()) {
      if (activityClass.isInstance(activity)) {
        looper.loop((T) activity);
      }
    }
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
}
