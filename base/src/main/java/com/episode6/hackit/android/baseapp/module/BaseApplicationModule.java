package com.episode6.hackit.android.baseapp.module;

import com.episode6.hackit.android.annotation.TestAnnotation;
import com.episode6.hackit.android.appmonitor.AppMonitorModule;
import com.episode6.hackit.android.baseapp.component.BaseApplication;
import com.episode6.hackit.android.baseapp.component.BaseBroadcastReceiver;
import com.episode6.hackit.android.extmodule.AndroidModule;
import com.episode6.hackit.android.extmodule.GsonModule;
import com.episode6.hackit.android.extmodule.OttoBusModule;
import com.episode6.hackit.android.app.scope.ApplicationScope;
import com.episode6.hackit.android.app.scope.ContextScope;
import com.episode6.hackit.android.preference.PrefModule;
import com.episode6.hackit.android.util.UtilModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Catch-all base module
 */
@Module(
    library = true,
    injects = {
        BaseApplication.class,
        BaseBroadcastReceiver.class},
    includes = {
        ContextScope.class,
        ApplicationScope.class,

        AndroidModule.class,
        GsonModule.class,
        OttoBusModule.class,
        UtilModule.class,
        AppMonitorModule.class,
        PrefModule.class})
public class BaseApplicationModule {

  @Provides
  @TestAnnotation
  @Singleton
  String provideTestString() {
    return "Sup fucker";
  }

}
