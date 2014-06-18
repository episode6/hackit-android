package com.episode6.hackit.android.media;

import android.hardware.Camera;

import com.google.common.base.Optional;

import javax.annotation.Nullable;

public class CameraProvider {

  public Optional<CameraAndInfo> getRearFacingCamera() {
    return Optional.fromNullable(findCameraFacing(Camera.CameraInfo.CAMERA_FACING_BACK));
  }

  public Optional<CameraAndInfo> getFrontFacingCamera() {
    return Optional.fromNullable(findCameraFacing(Camera.CameraInfo.CAMERA_FACING_FRONT));
  }

  public Optional<CameraAndInfo> getAnyCamera() {
    int numberOfCameras = Camera.getNumberOfCameras();
    if (numberOfCameras <= 0) {
      return Optional.absent();
    }

    Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
    Camera.getCameraInfo(0, cameraInfo);
    Camera camera = Camera.open(0);
    return Optional.of(new CameraAndInfo(camera, cameraInfo));
  }

  private @Nullable CameraAndInfo findCameraFacing(int facing) {
    int numberOfCameras = Camera.getNumberOfCameras();
    Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
    for (int i = 0; i < numberOfCameras; i++) {
      Camera.getCameraInfo(i, cameraInfo);
      if (cameraInfo.facing == facing) {
        return new CameraAndInfo(Camera.open(i), cameraInfo);
      }
    }
    return null;
  }

  public static class CameraAndInfo {
    private final Camera mCamera;
    private final Camera.CameraInfo mCameraInfo;

    CameraAndInfo(Camera camera, Camera.CameraInfo info) {
      mCamera = camera;
      mCameraInfo = info;
    }

    public Camera getCamera() {
      return mCamera;
    }

    public Camera.CameraInfo getCameraInfo() {
      return mCameraInfo;
    }
  }
}
