package com.episode6.hackit.android.typed.bundle;

import com.episode6.hackit.android.app.scope.ApplicationScope;
import com.episode6.hackit.android.serialize.SerializeModule;

import dagger.Module;
import dagger.Provides;

@Module(
    library = true,
    includes = {
        ApplicationScope.class,

        SerializeModule.class})
public class BundleModule {

  @Provides
  TypedBundleWrapper provideTypedBundleWrapper(TypedBundleWrapperImpl typedBundleWrapper) {
    return typedBundleWrapper;
  }
}
