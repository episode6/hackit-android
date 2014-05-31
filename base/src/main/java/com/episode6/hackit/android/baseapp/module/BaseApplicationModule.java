package com.episode6.hackit.android.baseapp.module;

import com.episode6.hackit.android.annotation.TestAnnotation;
import com.episode6.hackit.android.app.scope.ApplicationScope;
import com.episode6.hackit.android.app.scope.ContextScope;
import com.episode6.hackit.android.appmonitor.AppMonitorModule;
import com.episode6.hackit.android.baseapp.component.BaseApplication;
import com.episode6.hackit.android.baseapp.component.BaseBroadcastReceiver;
import com.episode6.hackit.android.executor.ExecutorModule;
import com.episode6.hackit.android.extmodule.GsonModule;
import com.episode6.hackit.android.extmodule.OttoBusModule;
import com.episode6.hackit.android.typed.preference.PrefModule;
import com.episode6.hackit.android.util.UtilModule;
import com.episode6.hackit.android.wakelock.WakeLockModule;

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

        GsonModule.class,
        OttoBusModule.class,
        UtilModule.class,
        AppMonitorModule.class,
        ExecutorModule.class,
        PrefModule.class,
        WakeLockModule.class})
public class BaseApplicationModule {

  @Provides
  @TestAnnotation
  @Singleton
  String provideTestString() {
    return "Sup fucker";
  }

}
