package com.episode6.hackit.android.media.camera;

import android.hardware.Camera;

public class CameraAndInfo {
  private final Camera mCamera;
  private final Camera.CameraInfo mCameraInfo;

  public CameraAndInfo(Camera camera, Camera.CameraInfo info) {
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
