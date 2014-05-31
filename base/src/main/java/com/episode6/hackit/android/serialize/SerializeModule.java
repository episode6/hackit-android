package com.episode6.hackit.android.serialize;

import com.episode6.hackit.android.app.scope.ApplicationScope;
import com.episode6.hackit.android.extmodule.GsonModule;

import dagger.Module;
import dagger.Provides;

@Module(
    library = true,
    includes = {
        ApplicationScope.class,
        GsonModule.class})
public class SerializeModule {

  @Provides
  Serializer providerSerializer(GsonSerializer serializer) {
    return serializer;
  }

  @Provides
  MapLikeTranslator provideMapLikeTranslator(MapLikeTranslatorImpl mapLikeTranslator) {
    return mapLikeTranslator;
  }
}
