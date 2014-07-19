package com.episode6.hackit.android.media.camera;

import android.annotation.TargetApi;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Build;
import android.view.SurfaceHolder;

import java.io.IOException;

public class ForwardingCameraLike implements CameraLike {

  private final Camera mCamera;

  protected ForwardingCameraLike(Camera camera) {
    mCamera = camera;
  }

  @Override
  public Camera getRawCamera() {
    return mCamera;
  }

  @Override
  public void addCallbackBuffer(byte[] callbackBuffer) {
    mCamera.addCallbackBuffer(callbackBuffer);
  }

  @Override
  public void autoFocus(Camera.AutoFocusCallback cb) {
    mCamera.autoFocus(cb);
  }

  @Override
  public void cancelAutoFocus() {
    mCamera.cancelAutoFocus();
  }

  @Override
  @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
  public boolean enableShutterSound(boolean enabled) {
    if (!isDeviceVersionGreaterOrEqualTo(Build.VERSION_CODES.JELLY_BEAN_MR1)) {
      return false;
    }
    return mCamera.enableShutterSound(enabled);
  }

  @Override
  public Camera.Parameters getParameters() {
    return mCamera.getParameters();
  }

  @Override
  public void lock() {
    mCamera.lock();
  }

  @Override
  public void reconnect() throws IOException {
    mCamera.reconnect();
  }

  @Override
  public void release() {
    mCamera.release();
  }

  @Override
  @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
  public void setAutoFocusMoveCallback(Camera.AutoFocusMoveCallback cb) {
    if (!isDeviceVersionGreaterOrEqualTo(Build.VERSION_CODES.JELLY_BEAN)) {
      return;
    }
    mCamera.setAutoFocusMoveCallback(cb);
  }

  @Override
  public void setDisplayOrientation(int degrees) {
    mCamera.setDisplayOrientation(degrees);
  }

  @Override
  public void setErrorCallback(Camera.ErrorCallback cb) {
    mCamera.setErrorCallback(cb);
  }

  @Override
  @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
  public void setFaceDetectionListener(Camera.FaceDetectionListener listener) {
    if (!isDeviceVersionGreaterOrEqualTo(Build.VERSION_CODES.ICE_CREAM_SANDWICH)) {
      return;
    }
    mCamera.setFaceDetectionListener(listener);
  }

  @Override
  public void setOneShotPreviewCallback(Camera.PreviewCallback cb) {
    mCamera.setOneShotPreviewCallback(cb);
  }

  @Override
  public void setParameters(Camera.Parameters params) {
    mCamera.setParameters(params);
  }

  @Override
  public void setPreviewCallback(Camera.PreviewCallback cb) {
    mCamera.setPreviewCallback(cb);
  }

  @Override
  public void setPreviewCallbackWithBuffer(Camera.PreviewCallback cb) {
    mCamera.setPreviewCallbackWithBuffer(cb);
  }

  @Override
  public void setPreviewDisplay(SurfaceHolder holder) throws IOException {
    mCamera.setPreviewDisplay(holder);
  }

  @Override
  @TargetApi(Build.VERSION_CODES.HONEYCOMB)
  public void setPreviewTexture(SurfaceTexture surfaceTexture) throws IOException {
    if (!isDeviceVersionGreaterOrEqualTo(Build.VERSION_CODES.HONEYCOMB)) {
      return;
    }
    mCamera.setPreviewTexture(surfaceTexture);
  }

  @Override
  public void setZoomChangeListener(Camera.OnZoomChangeListener listener) {
    mCamera.setZoomChangeListener(listener);
  }

  @Override
  @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
  public void startFaceDetection() {
    if (!isDeviceVersionGreaterOrEqualTo(Build.VERSION_CODES.ICE_CREAM_SANDWICH)) {
      return;
    }
    mCamera.startFaceDetection();
  }

  @Override
  public void startPreview() {
    mCamera.startPreview();
  }

  @Override
  public void startSmoothZoom(int value) {
    mCamera.startSmoothZoom(value);
  }

  @Override
  @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
  public void stopFaceDetection() {
    if (!isDeviceVersionGreaterOrEqualTo(Build.VERSION_CODES.ICE_CREAM_SANDWICH)) {
      return;
    }
    mCamera.stopFaceDetection();
  }

  @Override
  public void stopPreview() {
    mCamera.stopPreview();
  }

  @Override
  public void stopSmoothZoom() {
    mCamera.stopSmoothZoom();
  }

  @Override
  public void takePicture(Camera.ShutterCallback shutter, Camera.PictureCallback raw, Camera.PictureCallback jpeg) {
    mCamera.takePicture(shutter, raw, jpeg);
  }

  @Override
  public void takePicture(Camera.ShutterCallback shutter, Camera.PictureCallback raw, Camera.PictureCallback postview, Camera.PictureCallback jpeg) {
    mCamera.takePicture(shutter, raw, postview, jpeg);
  }

  @Override
  public void unlock() {
    mCamera.unlock();
  }

  private boolean isDeviceVersionGreaterOrEqualTo(int targetVersion) {
    return Build.VERSION.SDK_INT >= targetVersion;
  }
}
