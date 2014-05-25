package com.episode6.hackit.android.util;


import android.os.Build;

public class DeviceInfo {

  public final int sdkVersion;

  DeviceInfo(int sdkVersion) {
    this.sdkVersion = sdkVersion;
  }

  public boolean isHoneycombUp() {
    return sdkVersion >= Build.VERSION_CODES.HONEYCOMB;
  }

  public boolean isJellyBeanUp() {
    return sdkVersion >= Build.VERSION_CODES.JELLY_BEAN;
  }
}
