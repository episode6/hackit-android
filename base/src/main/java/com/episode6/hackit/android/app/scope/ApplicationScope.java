package com.episode6.hackit.android.app.scope;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;

import com.episode6.hackit.android.annotation.ForApplication;
import com.episode6.hackit.android.app.AppInfo;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
    library = true,
    includes = {
        ContextScope.class})
public class ApplicationScope {

  private final Application mApplication;

  public ApplicationScope(Application application) {
    mApplication = application;
  }

  /**
   * Override this in your own debug application module if you want
   */
  @Provides @Singleton
  AppInfo provideDefaultAppInfo() {
    return new AppInfo() {
      @Override
      public AppType getAppType() {
        return AppType.RELEASE;
      }
    };
  }

  @Provides @Singleton
  AppInfo.AppType provideAppType(AppInfo appInfo) {
    return appInfo.getAppType();
  }

  @Provides @Singleton
  Application provideApplication() {
    return mApplication;
  }

  @Provides @Singleton @ForApplication
  Context provideApplicationContext(Application application) {
    return application;
  }

  @Provides @Singleton
  SharedPreferences provideSharedPreferences(@ForApplication Context context) {
    return PreferenceManager.getDefaultSharedPreferences(context);
  }

  @Provides @Singleton
  PackageManager providePackageManager(@ForApplication Context context) {
    return context.getPackageManager();
  }

  @Provides @Singleton
  PackageInfo providePackageInfo(@ForApplication Context context, PackageManager packageManager) {
    try {
      return packageManager.getPackageInfo(context.getPackageName(), 0);
    } catch (PackageManager.NameNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  @Provides @Singleton
  ApplicationInfo provideApplicationInfo(@ForApplication Context context) {
    return context.getApplicationInfo();
  }
}
