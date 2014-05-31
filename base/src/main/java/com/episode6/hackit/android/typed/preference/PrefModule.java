package com.episode6.hackit.android.typed.preference;

import android.content.SharedPreferences;

import com.episode6.hackit.android.app.scope.ApplicationScope;
import com.episode6.hackit.android.serialize.SerializeModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
    library = true,
    includes = {
        ApplicationScope.class,
        SerializeModule.class})
public class PrefModule {

  @Provides @Singleton
  SharedPrefsMapLikeWrapper providePrefWrapper(SharedPreferences sharedPreferences) {
    return new SharedPrefsMapLikeWrapper(sharedPreferences);
  }

  @Provides @Singleton
  PrefManager providePrefManager(PrefManagerImpl preferencesManager) {
    return preferencesManager;
  }
}
