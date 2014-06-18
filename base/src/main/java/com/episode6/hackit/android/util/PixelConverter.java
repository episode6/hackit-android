package com.episode6.hackit.android.util;

import android.util.DisplayMetrics;

import javax.inject.Inject;

public class PixelConverter {

  @Inject DisplayMetrics mDisplayMetrics;

  public int convertDpToPixels(int sizeDp) {
    final float scale = mDisplayMetrics.density;
    return (int) ((float)sizeDp * scale + 0.5f);
  }
}
