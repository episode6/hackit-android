package com.episode6.hackit.android.typed.intent;

import com.episode6.hackit.android.app.scope.ApplicationScope;
import com.episode6.hackit.android.extmodule.GsonModule;
import com.episode6.hackit.android.serialize.SerializeModule;
import com.episode6.hackit.android.typed.bundle.BundleModule;

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

  @Provides
  TypedIntentWrapper provideTypedIntentWrapper(TypedIntentWrapperImpl typedIntentWrapper) {
    return typedIntentWrapper;
  }

  @Provides
  ActionTranslator provideActionTranslator(ActionTranslatorImpl actionTranslator) {
    return actionTranslator;
  }
}
