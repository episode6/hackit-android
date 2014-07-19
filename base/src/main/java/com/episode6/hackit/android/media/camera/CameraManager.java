package com.episode6.hackit.android.media.camera;

import android.hardware.Camera;

import com.google.common.base.Optional;

public interface CameraManager {

  public Optional<CameraAndInfo> getRearFacingCamera();
  public Optional<CameraAndInfo> getFrontFacingCamera();
  public Optional<CameraAndInfo> getAnyCamera();

  public void markReleased(CameraAndInfo cameraAndInfo);
  public void markReleased(Camera camera);
}
