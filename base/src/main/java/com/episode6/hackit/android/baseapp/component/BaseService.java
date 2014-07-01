package com.episode6.hackit.android.baseapp.component;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.episode6.hackit.android.app.HasContext;
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
public class BaseService extends Service implements HasInjectorScope {

  private Injector mInjector;

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

  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }
}
