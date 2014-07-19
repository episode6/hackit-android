package com.episode6.hackit.android.media.camera;


import android.hardware.Camera;

import com.episode6.hackit.chop.Chop;
import com.google.common.collect.Sets;

import java.util.Set;

import javax.inject.Inject;

public class DeferredCameraReleaseManager {

  @Inject DeferredCameraReleaseManager() {}

  private final Set<Camera> mCamerasToRelease = Sets.newHashSet();

  public void deferCameraRelease(Camera camera) {
    Chop.d("Trying to add camera to deferred releases: %s", camera);
    if (mCamerasToRelease.add(camera)) {
      Chop.d("success!");
    } else {
      Chop.d("Fail!");
    }
  }

  public void releaseAllNow() {
    Chop.d("Trying to release all cameras");
    for (Camera camera : mCamerasToRelease) {
//      try {
//        camera.stopPreview();
//      } catch (Throwable t) {
//        Chop.e(t, "Failed to stop preview for %s", camera);
//      }
      try {
        camera.release();
      } catch (Throwable t) {
        Chop.e(t, "Failed to release camera: %s", camera);
      }
    }
    mCamerasToRelease.clear();
  }
}
