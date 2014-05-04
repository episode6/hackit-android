package com.episode6.hackit.android.util;

import java.util.Locale;

public class StringFormat {

  public static String of(String format, Object... args) {
    return String.format(Locale.US, format, args);
  }
}
