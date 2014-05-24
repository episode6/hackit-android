package com.episode6.hackit.android.wakelock;

import com.episode6.hackit.android.app.scope.ApplicationScope;
import com.episode6.hackit.android.wakelock.impl.DefaultWakeLockManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
    library = true,
    includes = ApplicationScope.class)
public class WakeLockModule {

  @Provides @Singleton
  WakeLockManager provideWakeLockManager(DefaultWakeLockManager defaultWakeLockManager) {
    return defaultWakeLockManager;
  }
}
