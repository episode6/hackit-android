package com.episode6.hackit.android.appmonitor;

import android.app.Activity;

import com.episode6.hackit.android.util.functions.BoolFunctions;
import com.google.common.base.Function;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ActivityAssassin {

  private final ActivityCollector mActivityCollector;

  @Inject
  public ActivityAssassin(ActivityCollector activityCollector) {
    mActivityCollector = activityCollector;
  }

  public void finishActivities(Function<Activity, Boolean> shouldFinish) {
    for (Activity activity : mActivityCollector.getCreatedActivities().keySet()) {
      if (activity != null &&
          !activity.isFinishing() &&
          shouldFinish.apply(activity)) {
        activity.finish();
      }
    }
  }

  public void finishAllActivities() {
    finishActivities(BoolFunctions.<Activity>alwaysTrue());
  }

  public void finishAllActivitiesExcept(final Activity instance) {
    finishActivities(BoolFunctions.isNotSame(instance));
  }

  public void finishAllActivitiesExcept(final Class<?> activityClass) {
    finishActivities(BoolFunctions.<Activity>isNotInstanceOf(activityClass));
  }
}
