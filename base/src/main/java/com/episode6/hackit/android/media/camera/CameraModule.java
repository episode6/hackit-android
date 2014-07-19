package com.episode6.hackit.android.media.camera;

import com.episode6.hackit.android.app.scope.ApplicationScope;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
    library = true,
    includes = {
        ApplicationScope.class})
public class CameraModule {

  @Provides @Singleton
  CameraManager provideCameraProvider(CameraManagerImpl impl) {
    return impl;
  }
}
