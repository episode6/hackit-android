package com.episode6.hackit.android.media.camera;

import com.google.common.base.Optional;

public interface CameraManager {

  public Optional<CameraAndInfo> getRearFacingCamera();
  public Optional<CameraAndInfo> getFrontFacingCamera();
  public Optional<CameraAndInfo> getAnyCamera();
}
