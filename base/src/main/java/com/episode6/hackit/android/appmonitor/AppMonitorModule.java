package com.episode6.hackit.android.appmonitor;

import com.episode6.hackit.android.extmodule.OttoBusModule;
import com.episode6.hackit.android.app.scope.ApplicationScope;
import com.episode6.hackit.android.util.Clock;
import com.episode6.hackit.android.util.UtilModule;
import com.squareup.otto.Bus;

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
  ActivityCollector provideActivityCollection(Bus ottoBus, Clock clock) {
    return new ActivityCollector(ottoBus, clock);
  }

  @Provides @Singleton
  ActivityAssassin provideActivityAssassin(ActivityCollector activityCollector) {
    return new ActivityAssassin(activityCollector);
  }
}
