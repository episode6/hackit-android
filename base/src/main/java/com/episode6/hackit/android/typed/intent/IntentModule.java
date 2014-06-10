package com.episode6.hackit.android.typed.intent;

import com.episode6.hackit.android.app.scope.ApplicationScope;
import com.episode6.hackit.android.extmodule.GsonModule;
import com.episode6.hackit.android.serialize.SerializeModule;
import com.episode6.hackit.android.typed.bundle.BundleModule;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapterFactory;

import dagger.Module;
import dagger.Provides;

@Module(
    library = true,
    includes = {
        ApplicationScope.class,

        BundleModule.class,
        SerializeModule.class,
        GsonModule.class})
public class IntentModule {

  @Provides @ForIntentActionTranslator
  TypeAdapterFactory provideEnumTypeAdapterFactory(ActionEnumTypeAdapterFactory typeAdapterFactory) {
    return typeAdapterFactory;
  }

  @Provides @ForIntentActionTranslator
  Gson provideGsonForActions(@ForIntentActionTranslator TypeAdapterFactory typeAdapterFactory) {
    return new GsonBuilder()
        .registerTypeAdapterFactory(typeAdapterFactory)
        .create();
  }

  @Provides
  TypedIntentWrapper provideTypedIntentWrapper(TypedIntentWrapperImpl typedIntentWrapper) {
    return typedIntentWrapper;
  }

  @Provides
  ActionTranslator provideActionTranslator(ActionTranslatorImpl actionTranslator) {
    return actionTranslator;
  }
}
