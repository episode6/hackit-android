package com.episode6.hackit.android.app.scope;

import android.app.Service;
import android.content.Context;

import com.episode6.hackit.android.annotation.ForService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
    library = true,
    addsTo = ApplicationScope.class,
    includes = {
        ContextScope.class})
public class ServiceScope {

  private final Service mService;

  public ServiceScope(Service service) {
    mService = service;
  }

  @Provides @Singleton
  Service provideService() {
    return mService;
  }

  @Provides @Singleton @ForService
  Context provideServiceContext() {
    return mService;
  }
}
