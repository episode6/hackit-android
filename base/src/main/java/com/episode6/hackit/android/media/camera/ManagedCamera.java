package com.episode6.hackit.android.media.camera;

import android.hardware.Camera;

public class ManagedCamera extends ForwardingCameraLike {

  private final CameraManager mCameraManager;

  protected ManagedCamera(Camera camera, CameraManager cameraManager) {
    super(camera);
    mCameraManager = cameraManager;
  }

  @Override
  public void release() {
    super.release();
    mCameraManager.markReleased(getRawCamera());
  }
}
