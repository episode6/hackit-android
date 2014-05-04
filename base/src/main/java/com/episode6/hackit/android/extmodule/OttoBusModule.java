package com.episode6.hackit.android.extmodule;

import android.os.Looper;

import com.episode6.hackit.android.annotation.ForBackgroundThread;
import com.episode6.hackit.android.annotation.ForUiThread;
import com.episode6.hackit.android.app.scope.ApplicationScope;
import com.episode6.hackit.chop.Chop;
import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
    library = true,
    includes = {ApplicationScope.class})
public class OttoBusModule {

  @Provides @Singleton
  Bus provideOttoBus() {
    Chop.e("Created new OttoBus!");
    return new Bus(ThreadEnforcer.MAIN);
  }

  @Provides @ForUiThread @Singleton
  Bus provideUiThreadOttoBus(Bus defaultOttoBus) {
    return defaultOttoBus;
  }

  @Provides @ForBackgroundThread @Singleton
  Bus provideBackgroundThreadOttoBus() {
    return new Bus(new ThreadEnforcer() {
      @Override
      public void enforce(Bus bus) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
          throw new IllegalStateException("Background thread event bus " + bus + " accessed from main thread " + Looper.myLooper());
        }
      }
    });
  }
}
