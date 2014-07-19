package com.episode6.hackit.android.media.camera;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.view.SurfaceHolder;

import java.io.IOException;

public interface CameraLike {
  public Camera getRawCamera();

  public void addCallbackBuffer(byte[] callbackBuffer);
  public void autoFocus(Camera.AutoFocusCallback cb);
  public void cancelAutoFocus();
  public boolean enableShutterSound(boolean enabled);
  public Camera.Parameters getParameters();
  public void lock();
  public void reconnect() throws IOException;
  public void release();
  public void setAutoFocusMoveCallback(Camera.AutoFocusMoveCallback cb);
  public void setDisplayOrientation(int degrees);
  public void setErrorCallback(Camera.ErrorCallback cb);
  public void setFaceDetectionListener(Camera.FaceDetectionListener listener);
  public void setOneShotPreviewCallback(Camera.PreviewCallback cb);
  public void setParameters(Camera.Parameters params);
  public void setPreviewCallback(Camera.PreviewCallback cb);
  public void setPreviewCallbackWithBuffer(Camera.PreviewCallback cb);
  public void setPreviewDisplay(SurfaceHolder holder) throws IOException;
  public void setPreviewTexture(SurfaceTexture surfaceTexture) throws IOException;
  public void setZoomChangeListener(Camera.OnZoomChangeListener listener);
  public void startFaceDetection();
  public void startPreview();
  public void startSmoothZoom(int value);
  public void stopFaceDetection();
  public void stopPreview();
  public void stopSmoothZoom();
  public void takePicture(Camera.ShutterCallback shutter, Camera.PictureCallback raw, Camera.PictureCallback jpeg);
  public void takePicture(Camera.ShutterCallback shutter, Camera.PictureCallback raw, Camera.PictureCallback postview, Camera.PictureCallback jpeg);
  public void unlock();
}
