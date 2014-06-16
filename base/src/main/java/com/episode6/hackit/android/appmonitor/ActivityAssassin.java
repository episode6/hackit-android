package com.episode6.hackit.android.appmonitor;

import android.app.Activity;

import com.google.common.base.Function;

public interface ActivityAssassin {

  /**
   * @param shouldFinish
   * @return true if any activities are finished, false otherwise
   */
  public boolean finishActivities(Function<Activity, Boolean> shouldFinish);
  public boolean finishAllActivities();
  public boolean finishAllActivitiesExcept(final Activity instance);
  public boolean finishAllActivitiesExcept(final Class<?> activityClass);
}
