package com.episode6.hackit.android.media.camera;

import android.hardware.Camera;

public class CameraAndInfo {
  private final CameraLike mCamera;
  private final Camera.CameraInfo mCameraInfo;

  CameraAndInfo(Camera camera, Camera.CameraInfo info, CameraManager cameraManager) {
    mCamera = new ManagedCamera(camera, cameraManager);
    mCameraInfo = info;
  }

  public CameraLike getCamera() {
    return mCamera;
  }

  public Camera.CameraInfo getCameraInfo() {
    return mCameraInfo;
  }

  public boolean isFrontFacing() {
    return isFacing(Camera.CameraInfo.CAMERA_FACING_FRONT);
  }

  public boolean isRearFacing() {
    return isFacing(Camera.CameraInfo.CAMERA_FACING_BACK);
  }

  public boolean isFacing(int facing) {
    return mCameraInfo.facing == facing;
  }
}
