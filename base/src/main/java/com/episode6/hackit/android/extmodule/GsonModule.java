package com.episode6.hackit.android.extmodule;

import com.episode6.hackit.android.app.scope.ApplicationScope;
import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
    library = true,
    includes = {ApplicationScope.class})
public class GsonModule {

  @Provides @Singleton
  Gson provideGson() {
    return new Gson();
  }
}
