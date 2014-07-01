package com.episode6.hackit.android.baseapp.component;

import android.app.IntentService;

import com.episode6.hackit.android.app.scope.ContextScope;
import com.episode6.hackit.android.app.scope.ServiceScope;
import com.episode6.hackit.android.baseapp.module.BaseServiceModule;
import com.episode6.hackit.android.inject.Injectors;
import com.episode6.hackit.inject.HasInjectorScope;
import com.episode6.hackit.inject.Injector;
import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 * Base service that supports injection and scoping
 */
public abstract class BaseIntentService extends IntentService implements HasInjectorScope {

  private Injector mInjector;

  /**
   * Creates an IntentService.  Invoked by your subclass's constructor.
   *
   * @param name Used to name the worker thread, important only for debugging.
   */
  public BaseIntentService(String name) {
    super(name);
  }

  @Override
  public void onCreate() {
    mInjector = Injectors.plusFrom(getApplicationContext(), this);
    getInjector().inject(this);
    super.onCreate();
  }

  @Override
  public void onDestroy() {
    mInjector = null;
    super.onDestroy();
  }

  @Override
  public List<Object> getModules() {
    return ImmutableList.of(
        new ContextScope(this),
        new ServiceScope(this),
        new BaseServiceModule());
  }

  @Override
  public Injector getInjector() {
    return mInjector;
  }
}
