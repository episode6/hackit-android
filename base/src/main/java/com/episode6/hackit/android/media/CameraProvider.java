package com.episode6.hackit.android.media;

import android.hardware.Camera;

import com.google.common.base.Optional;

import javax.annotation.Nullable;

public class CameraProvider {

  public Optional<Camera> getRearFacingCamera() {
    return Optional.fromNullable(findCameraFacing(Camera.CameraInfo.CAMERA_FACING_BACK));
  }

  public Optional<Camera> getFrontFacingCamera() {
    return Optional.fromNullable(findCameraFacing(Camera.CameraInfo.CAMERA_FACING_FRONT));
  }

  public Optional<Camera> getAnyCamera() {
    if (Camera.getNumberOfCameras() > 0) {
      return Optional.fromNullable(Camera.open(0));
    }
    return Optional.absent();
  }

  private @Nullable Camera findCameraFacing(int facing) {
    int numberOfCameras = Camera.getNumberOfCameras();
    Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
    for (int i = 0; i < numberOfCameras; i++) {
      Camera.getCameraInfo(i, cameraInfo);
      if (cameraInfo.facing == facing) {
        return Camera.open(i);
      }
    }
    return null;
  }
}
