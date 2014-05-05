package com.episode6.hackit.android.baseapp.component;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.episode6.hackit.android.app.HasContext;
import com.episode6.hackit.android.inject.Injectors;
import com.episode6.hackit.inject.HasInjectorScope;
import com.episode6.hackit.inject.Injector;

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
  public List<Object> getModules() {
    return null;
  }

  @Override
  public Injector getInjector() {
    return null;
  }

  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }
}
