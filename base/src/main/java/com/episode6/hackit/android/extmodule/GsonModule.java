package com.episode6.hackit.android.extmodule;

import com.episode6.hackit.android.app.scope.ApplicationScope;
import com.google.common.collect.ImmutableSet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapterFactory;

import java.util.Set;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
    library = true,
    includes = {ApplicationScope.class})
public class GsonModule {

  @Provides(type = Provides.Type.SET_VALUES) @Singleton
  Set<TypeAdapterFactory> provideDefaultTypeAdapterFactorySet() {
    return ImmutableSet.of();
  }

  @Provides @Singleton
  Gson provideGson(Set<TypeAdapterFactory> typeAdapterFactories) {
    GsonBuilder builder = new GsonBuilder();
    for (TypeAdapterFactory factory : typeAdapterFactories) {
      builder.registerTypeAdapterFactory(factory);
    }
    return builder.create();
  }
}
