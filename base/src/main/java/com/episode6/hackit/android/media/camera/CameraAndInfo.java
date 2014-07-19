package com.episode6.hackit.android.media.camera;

import android.hardware.Camera;

import com.google.common.base.Objects;

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

  @Override
  public int hashCode() {
    return Objects.hashCode(mCamera);
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof Camera) {
      return mCamera.equals(o);
    }
    if (o instanceof CameraAndInfo) {
      return mCamera.equals(((CameraAndInfo)o).getCamera());
    }
    return false;
  }
}
