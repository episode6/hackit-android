package com.episode6.hackit.android.preference;

import android.content.SharedPreferences;

import com.episode6.hackit.android.app.scope.ApplicationScope;
import com.episode6.hackit.android.app.scope.ContextScope;
import com.episode6.hackit.android.extmodule.GsonModule;
import com.google.common.collect.ImmutableSet;

import java.util.Set;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
    library = true,
    includes = {
        ApplicationScope.class,
        ContextScope.class,
        GsonModule.class})
public class PreferencesModule {

  @Provides @Singleton
  PreferencesManager providePrefManager(PreferencesManagerImpl preferencesManager) {
    return preferencesManager;
  }

  @Provides(type = Provides.Type.SET_VALUES)
  Set<PrefKeyTranslator> provideTranslators(
      BasicPrefKey.Translator basicTranslator,
      GsonPrefKey.Translator gsonTranslator) {

    return ImmutableSet.<PrefKeyTranslator>of(
        basicTranslator,
        gsonTranslator);
  }

  @Provides @Singleton
  PrefKeyTranslatorSet providePrefKeyTranslatorSet(Set<PrefKeyTranslator> prefKeyTranslators) {
    return new PrefKeyTranslatorSet(prefKeyTranslators);
  }
}
