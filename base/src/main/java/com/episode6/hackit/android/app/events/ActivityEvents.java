package com.episode6.hackit.android.app.events;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

/**
 * Otto Events for activity lifecycle
 */
public class ActivityEvents {
  private static abstract class AbstractActivityLifecycleEvent {
    public final Activity activity;

    public AbstractActivityLifecycleEvent(Activity activity) {
      this.activity = activity;
    }
  }

  public static class OnCreate extends AbstractActivityLifecycleEvent {
    public final Bundle savedInstanceState;

    public OnCreate(Activity activity, Bundle savedInstanceState) {
      super(activity);
      this.savedInstanceState = savedInstanceState;
    }
  }

  public static class OnPostCreate extends AbstractActivityLifecycleEvent {
    public final Bundle savedInstanceState;

    public OnPostCreate(Activity activity, Bundle savedInstanceState) {
      super(activity);
      this.savedInstanceState = savedInstanceState;
    }
  }

  public static class OnRestart extends AbstractActivityLifecycleEvent {
    public OnRestart(Activity activity) {
      super(activity);
    }
  }

  public static class OnStart extends AbstractActivityLifecycleEvent {
    public OnStart(Activity activity) {
      super(activity);
    }
  }

  public static class OnResume extends AbstractActivityLifecycleEvent {
    public OnResume(Activity activity) {
      super(activity);
    }
  }

  public static class OnPostResume extends AbstractActivityLifecycleEvent {
    public OnPostResume(Activity activity) {
      super(activity);
    }
  }

  public static class OnNewIntent extends AbstractActivityLifecycleEvent {
    public final Intent newIntent;

    public OnNewIntent(Activity activity, Intent newIntent) {
      super(activity);
      this.newIntent = newIntent;
    }
  }

  public static class OnConfigurationChanged extends AbstractActivityLifecycleEvent {
    public final Configuration newConfig;

    public OnConfigurationChanged(Activity activity, Configuration newConfig) {
      super(activity);
      this.newConfig = newConfig;
    }
  }

  public static class OnSaveInstanceState extends AbstractActivityLifecycleEvent {
    public final Bundle outState;

    public OnSaveInstanceState(Activity activity, Bundle outState) {
      super(activity);
      this.outState = outState;
    }
  }

  public static class OnPause extends AbstractActivityLifecycleEvent {
    public OnPause(Activity activity) {
      super(activity);
    }
  }

  public static class OnStop extends AbstractActivityLifecycleEvent {
    public OnStop(Activity activity) {
      super(activity);
    }
  }

  public static class OnDestroy extends AbstractActivityLifecycleEvent {
    public OnDestroy(Activity activity) {
      super(activity);
    }
  }
}
