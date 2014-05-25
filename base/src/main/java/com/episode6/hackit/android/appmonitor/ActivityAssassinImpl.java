package com.episode6.hackit.android.appmonitor;

import android.app.Activity;

import com.episode6.hackit.android.util.functions.BoolFunctions;
import com.google.common.base.Function;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ActivityAssassinImpl implements ActivityAssassin {
  private final ActivityCollector mActivityCollector;

  @Inject
  public ActivityAssassinImpl(ActivityCollector activityCollector) {
    mActivityCollector = activityCollector;
  }

  @Override
  public void finishActivities(final Function<Activity, Boolean> shouldFinish) {
    mActivityCollector.loopCreatedActivities(
        Activity.class,
        new ActivityCollector.Looper<Activity>() {
          @Override
          public boolean loop(Activity activity) {
            if (activity != null &&
                !activity.isFinishing() &&
                shouldFinish.apply(activity)) {
              activity.finish();
            }
            return false;
          }
        });
  }

  @Override
  public void finishAllActivities() {
    finishActivities(BoolFunctions.<Activity>alwaysTrue());
  }

  @Override
  public void finishAllActivitiesExcept(final Activity instance) {
    finishActivities(BoolFunctions.isNotSame(instance));
  }

  @Override
  public void finishAllActivitiesExcept(final Class<?> activityClass) {
    finishActivities(BoolFunctions.<Activity>isNotInstanceOf(activityClass));
  }
}
