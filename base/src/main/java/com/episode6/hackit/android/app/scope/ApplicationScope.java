package com.episode6.hackit.android.app.scope;

import android.app.Application;
import android.content.Context;

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
}
