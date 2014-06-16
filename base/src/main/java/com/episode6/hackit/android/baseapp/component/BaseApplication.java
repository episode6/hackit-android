package com.episode6.hackit.android.baseapp.component;

import android.app.Application;
import android.app.Instrumentation;
import android.content.Context;

import com.episode6.hackit.android.app.scope.ApplicationScope;
import com.episode6.hackit.android.app.scope.ContextScope;
import com.episode6.hackit.android.appmonitor.ActivityCollector;
import com.episode6.hackit.android.baseapp.module.BaseApplicationModule;
import com.episode6.hackit.android.inject.Injectors;
import com.episode6.hackit.android.typed.preference.PrefManager;
import com.episode6.hackit.inject.HasInjectorScope;
import com.episode6.hackit.inject.Injector;
import com.google.common.collect.ImmutableList;

import java.util.List;

import javax.inject.Inject;

public class BaseApplication extends Application implements HasInjectorScope {

  /* Eager Singletons */
  @Inject ActivityCollector mActivityCollector;
  @Inject PrefManager mPrefManager;

  private Injector mInjector;

  public BaseApplication() {
    super();
  }

  public BaseApplication(final Context context) {
    this();
    attachBaseContext(context);
  }

  public BaseApplication(final Instrumentation instrumentation) {
    this();
    attachBaseContext(instrumentation.getTargetContext());
  }

  @Override
  public Injector getInjector() {
    return mInjector;
  }

  @Override
  public void onCreate() {
    mInjector = Injectors.createFrom(this);
    getInjector().inject(this);

    super.onCreate();
  }

  @Override
  public List<Object> getModules() {
    return ImmutableList.<Object>of(
        new ContextScope(this),
        new ApplicationScope(this),
        new BaseApplicationModule());
  }
}
