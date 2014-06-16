package com.episode6.hackit.android.appmonitor;

import com.episode6.hackit.android.app.scope.ApplicationScope;
import com.episode6.hackit.android.bus.OttoBusModule;
import com.episode6.hackit.android.util.UtilModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
    library = true,
    includes = {
        ApplicationScope.class,
        OttoBusModule.class,
        UtilModule.class})
public class AppMonitorModule {

  @Provides @Singleton
  ActivityCollector provideActivityCollection(ActivityCollectorImpl activityCollector) {
    return activityCollector;
  }

  @Provides @Singleton
  ActivityAssassin provideActivityAssassin(ActivityAssassinImpl activityAssassin) {
    return activityAssassin;
  }
}
