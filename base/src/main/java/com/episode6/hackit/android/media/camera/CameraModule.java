package com.episode6.hackit.android.media.camera;

import com.episode6.hackit.android.app.scope.ApplicationScope;

import dagger.Module;
import dagger.Provides;

@Module(
    library = true,
    includes = {
        ApplicationScope.class})
public class CameraModule {

  @Provides
  CameraManager provideCameraProvider(CameraManagerImpl impl) {
    return impl;
  }
}
