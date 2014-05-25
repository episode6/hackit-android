package com.episode6.hackit.android.appmonitor;

import android.app.Activity;

import com.google.common.base.Function;

public interface ActivityAssassin {
  public void finishActivities(Function<Activity, Boolean> shouldFinish);
  public void finishAllActivities();
  public void finishAllActivitiesExcept(final Activity instance);
  public void finishAllActivitiesExcept(final Class<?> activityClass);
}
