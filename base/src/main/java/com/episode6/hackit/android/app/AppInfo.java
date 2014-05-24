package com.episode6.hackit.android.app;

public interface AppInfo {
  public enum AppType {
    DEBUG,
    RELEASE;

    public boolean isDebug() {
      return this == DEBUG;
    }

    public boolean isRelease() {
      return this == RELEASE;
    }
  }

  public AppType getAppType();
}
