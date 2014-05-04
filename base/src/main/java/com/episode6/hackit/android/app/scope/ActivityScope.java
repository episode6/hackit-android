package com.episode6.hackit.android.app.scope;

import android.app.Activity;
import android.content.Context;

import com.episode6.hackit.android.annotation.ForActivity;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
    library = true,
    addsTo = ApplicationScope.class,
    includes = {
        ContextScope.class})
public class ActivityScope {

  private final Activity mActivity;

  public ActivityScope(Activity activity) {
    mActivity = activity;
  }

  @Provides @Singleton
  Activity provideActivity() {
    return mActivity;
  }

  @Provides @Singleton @ForActivity
  Context provideActivityContext(Activity activity) {
    return activity;
  }
}
