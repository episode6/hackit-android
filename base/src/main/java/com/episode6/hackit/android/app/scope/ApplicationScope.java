package com.episode6.hackit.android.app.scope;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.preference.PreferenceManager;

import com.episode6.hackit.android.annotation.ForApplication;

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

  @Provides @Singleton
  Application provideApplication() {
    return mApplication;
  }

  @Provides @Singleton @ForApplication
  Context provideApplicationContext(Application application) {
    return application;
  }

  @Provides @Singleton
  SharedPreferences provideSharedPreferences(Application application) {
    return PreferenceManager.getDefaultSharedPreferences(application);
  }

  @Provides @Singleton
  PackageManager providePackageManager(Application application) {
    return application.getPackageManager();
  }

  @Provides @Singleton
  AudioManager provideAudioManager(Application application) {
    return (AudioManager) application.getSystemService(Context.AUDIO_SERVICE);
  }
}
