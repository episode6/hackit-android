package com.episode6.hackit.android.util;

import com.episode6.hackit.android.app.scope.ApplicationScope;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
    library = true,
    includes = {ApplicationScope.class})
public class UtilModule {

  @Provides @Singleton
  Clock provideClock() {
    return new Clock();
  }
}
