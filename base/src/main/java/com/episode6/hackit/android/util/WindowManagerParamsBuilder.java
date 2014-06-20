package com.episode6.hackit.android.util;

import android.graphics.PixelFormat;
import android.view.WindowManager;

public class WindowManagerParamsBuilder {

  private Integer mXPos = null;
  private Integer mYPos = null;
  private int mWidth = WindowManager.LayoutParams.MATCH_PARENT;
  private int mHeight = WindowManager.LayoutParams.MATCH_PARENT;
  private int mType = WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
  private int mFlags = 0;
  private int mPixelFormat = PixelFormat.TRANSPARENT;

  public WindowManagerParamsBuilder matchParent() {
    mWidth = WindowManager.LayoutParams.MATCH_PARENT;
    mHeight = WindowManager.LayoutParams.MATCH_PARENT;
    return this;
  }

  public WindowManagerParamsBuilder wrapContent() {
    mWidth = WindowManager.LayoutParams.WRAP_CONTENT;
    mHeight = WindowManager.LayoutParams.WRAP_CONTENT;
    return this;
  }

  public WindowManagerParamsBuilder width(int width) {
    mWidth = width;
    return this;
  }

  public WindowManagerParamsBuilder height(int height) {
    mHeight = height;
    return this;
  }

  public WindowManagerParamsBuilder xPosition(int xPosition) {
    mXPos = xPosition;
    return this;
  }

  public WindowManagerParamsBuilder yPosition(int yPosition) {
    mYPos = yPosition;
    return this;
  }

  public WindowManagerParamsBuilder typePhone() {
    mType = WindowManager.LayoutParams.TYPE_PHONE;
    return this;
  }

  public WindowManagerParamsBuilder typeSystemAlert() {
    mType = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
    return this;
  }

  public WindowManagerParamsBuilder typeSystemOverlay() {
    mType = WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
    return this;
  }

  public WindowManagerParamsBuilder allowLockWhileScreenOn() {
    mFlags |= WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON;
    return this;
  }

  public WindowManagerParamsBuilder dimBehind() {
    mFlags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
    return this;
  }

  public WindowManagerParamsBuilder notFocusable() {
    mFlags |= WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
    return this;
  }

  public WindowManagerParamsBuilder notTouchable() {
    mFlags |= WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
    return this;
  }

  public WindowManagerParamsBuilder notTouchModal() {
    mFlags |= WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
    return this;
  }

  public WindowManagerParamsBuilder touchableWhenWaking() {
    mFlags |= WindowManager.LayoutParams.FLAG_TOUCHABLE_WHEN_WAKING;
    return this;
  }

  public WindowManagerParamsBuilder keepScreenOn() {
    mFlags |= WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
    return this;
  }

  public WindowManagerParamsBuilder layoutInScreen() {
    mFlags |= WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
    return this;
  }

  public WindowManagerParamsBuilder layoutNoLimits() {
    mFlags |= WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
    return this;
  }

  public WindowManagerParamsBuilder fullscreen() {
    mFlags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
    return this;
  }

  public WindowManagerParamsBuilder forceNotFullscreen() {
    mFlags |= WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN;
    return this;
  }

  public WindowManagerParamsBuilder secure() {
    mFlags |= WindowManager.LayoutParams.FLAG_SECURE;
    return this;
  }

  public WindowManagerParamsBuilder scaled() {
    mFlags |= WindowManager.LayoutParams.FLAG_SCALED;
    return this;
  }

  public WindowManagerParamsBuilder ignoreCheekPresses() {
    mFlags |= WindowManager.LayoutParams.FLAG_IGNORE_CHEEK_PRESSES;
    return this;
  }

  public WindowManagerParamsBuilder layoutInsetDecor() {
    mFlags |= WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR;
    return this;
  }

  public WindowManagerParamsBuilder altFocusableInputMethod() {
    mFlags |= WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM;
    return this;
  }

  public WindowManagerParamsBuilder watchOutsideTouch() {
    mFlags |= WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
    return this;
  }

  public WindowManagerParamsBuilder showWhenLocked() {
    mFlags |= WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;
    return this;
  }

  public WindowManagerParamsBuilder showWallpaper() {
    mFlags |= WindowManager.LayoutParams.FLAG_SHOW_WALLPAPER;
    return this;
  }

  public WindowManagerParamsBuilder turnScreenOn() {
    mFlags |= WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON;
    return this;
  }

  public WindowManagerParamsBuilder dismissKeyguard() {
    mFlags |= WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD;
    return this;
  }

  public WindowManagerParamsBuilder splitTouch() {
    mFlags |= WindowManager.LayoutParams.FLAG_SPLIT_TOUCH;
    return this;
  }

  public WindowManagerParamsBuilder hardwareAccelerated() {
    mFlags |= WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED;
    return this;
  }

  public WindowManagerParamsBuilder layoutInOverscan() {
    mFlags |= WindowManager.LayoutParams.FLAG_LAYOUT_IN_OVERSCAN;
    return this;
  }

  public WindowManagerParamsBuilder translucentStatus() {
    mFlags |= WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
    return this;
  }

  public WindowManagerParamsBuilder translucentNavigation() {
    mFlags |= WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
    return this;
  }

  public WindowManagerParamsBuilder localFocusMode() {
    mFlags |= WindowManager.LayoutParams.FLAG_LOCAL_FOCUS_MODE;
    return this;
  }

  public WindowManagerParamsBuilder pixelFormatTranslucent() {
    mPixelFormat = PixelFormat.TRANSLUCENT;
    return this;
  }

  public WindowManagerParamsBuilder pixelFormatTransparent() {
    mPixelFormat = PixelFormat.TRANSPARENT;
    return this;
  }

  public WindowManagerParamsBuilder pixelFormatOpaque() {
    mPixelFormat = PixelFormat.OPAQUE;
    return this;
  }

  public WindowManager.LayoutParams build() {
    if (mXPos != null && mYPos != null) {
      return new WindowManager.LayoutParams(mWidth, mHeight, mXPos, mYPos, mType, mFlags, mPixelFormat);
    }
    return new WindowManager.LayoutParams(mWidth, mHeight, mType, mFlags, mPixelFormat);
  }
}
