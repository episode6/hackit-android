package com.episode6.hackit.android.media.camera;

import android.hardware.Camera;

public class CameraAndInfo {
  private final CameraLike mCamera;
  private final Camera.CameraInfo mCameraInfo;

  public CameraAndInfo(Camera camera, Camera.CameraInfo info) {
    mCamera = new ForwardingCameraLike(camera);
    mCameraInfo = info;
  }

  public CameraLike getCamera() {
    return mCamera;
  }

  public Camera.CameraInfo getCameraInfo() {
    return mCameraInfo;
  }
}
