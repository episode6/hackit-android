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
  public boolean finishActivities(final Function<Activity, Boolean> shouldFinish) {
    final BoolTracker boolTracker = new BoolTracker(false);

    mActivityCollector.loopCreatedActivities(
        Activity.class,
        new ActivityCollector.Looper<Activity>() {
          @Override
          public boolean loop(Activity activity) {
            if (activity != null &&
                !activity.isFinishing() &&
                shouldFinish.apply(activity)) {
              activity.finish();
              boolTracker.mBoolean = true;
            }
            return false;
          }
        });
    return boolTracker.mBoolean;
  }

  @Override
  public boolean finishAllActivities() {
    return finishActivities(BoolFunctions.<Activity>alwaysTrue());
  }

  @Override
  public boolean finishAllActivitiesExcept(final Activity instance) {
    return finishActivities(BoolFunctions.isNotSame(instance));
  }

  @Override
  public boolean finishAllActivitiesExcept(final Class<?> activityClass) {
    return finishActivities(BoolFunctions.<Activity>isNotInstanceOf(activityClass));
  }

  private static class BoolTracker {
    boolean mBoolean;

    BoolTracker(boolean startValue) {
      mBoolean = startValue;
    }
  }
}
