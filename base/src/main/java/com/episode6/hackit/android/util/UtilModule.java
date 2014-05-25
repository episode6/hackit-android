package com.episode6.hackit.android.util;

import android.os.Build;

import com.episode6.hackit.android.app.scope.ApplicationScope;

import java.util.Random;

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

  @Provides @Singleton
  DeviceInfo provideDeviceInfo() {
    return new DeviceInfo(Build.VERSION.SDK_INT);
  }

  @Provides
  Random provideRandom() {
    return new Random();
  }
}
