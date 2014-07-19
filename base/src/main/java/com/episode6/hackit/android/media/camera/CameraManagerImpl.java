package com.episode6.hackit.android.media.camera;

import android.hardware.Camera;

import com.episode6.hackit.chop.Chop;
import com.google.common.base.Optional;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;

import java.util.Iterator;
import java.util.Set;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CameraManagerImpl implements CameraManager {

  private final Set<CameraAndInfo> mOpenCameras = Sets.newHashSet();

  @Inject DeferredCameraReleaseManager mDeferredCameraReleaseManager;

  private Optional<CameraAndInfo> wrapAndTrack(CameraAndInfo cameraAndInfo) {
    if (cameraAndInfo != null) {
      mOpenCameras.add(cameraAndInfo);
    }
    return Optional.fromNullable(cameraAndInfo);
  }

  @Override
  public Optional<CameraAndInfo> getRearFacingCamera() {
    return wrapAndTrack(findCameraFacing(Camera.CameraInfo.CAMERA_FACING_BACK));
  }

  @Override
  public Optional<CameraAndInfo> getFrontFacingCamera() {
    return wrapAndTrack(findCameraFacing(Camera.CameraInfo.CAMERA_FACING_FRONT));
  }

  @Override
  public Optional<CameraAndInfo> getAnyCamera() {
    if (!mOpenCameras.isEmpty()) {
      return Optional.fromNullable(Iterables.getFirst(mOpenCameras, null));
    }

    int numberOfCameras = Camera.getNumberOfCameras();
    if (numberOfCameras <= 0) {
      return Optional.absent();
    }

    Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
    Camera.getCameraInfo(0, cameraInfo);
    try {
      Camera camera = Camera.open(0);
      return wrapAndTrack(new CameraAndInfo(camera, cameraInfo, this));
    } catch (Throwable t) {
      Chop.e(t, "Failed to open camera");
      return Optional.absent();
    }
  }

  @Override
  public void maybeRelease(Camera camera) {
    Chop.d("Maybe release camera: %s", camera);
    if (removeCamera(camera)) {
      Chop.d("Found camera in open cameras cache");
    } else {
      Chop.e("Could not find camera! WTF!");
    }

    mDeferredCameraReleaseManager.deferCameraRelease(camera);

    if (mOpenCameras.isEmpty()) {
      mDeferredCameraReleaseManager.releaseAllNow();
    }
  }

  private @Nullable CameraAndInfo findCameraFacing(int facing) {
    for (CameraAndInfo cameraAndInfo : mOpenCameras) {
      if (cameraAndInfo.isFacing(facing)) {
        return cameraAndInfo;
      }
    }


    int numberOfCameras = Camera.getNumberOfCameras();
    Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
    for (int i = 0; i < numberOfCameras; i++) {
      Camera.getCameraInfo(i, cameraInfo);
      if (cameraInfo.facing == facing) {
        try {
          return new CameraAndInfo(Camera.open(i), cameraInfo, this);
        } catch (Throwable t) {
          Chop.e(t, "Failed to open camera");
        }
      }
    }

    return null;
  }

  private boolean removeCamera(Camera camera) {
    Iterator<CameraAndInfo> iterator = mOpenCameras.iterator();
    while (iterator.hasNext()) {
      CameraAndInfo cameraAndInfo = iterator.next();
      if (cameraAndInfo.getCamera().getRawCamera() == camera) {
        iterator.remove();
        return true;
      }
    }
    return false;
  }
}
