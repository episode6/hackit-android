/**
 * Some of the contents of this file have been taken from DroidKit here:
 * https://github.com/droidkit/droidkit/blob/master/src/org/droidkit/util/tricks/TextTricks.java
 * and modified.
 *
 * DroidKit License: https://github.com/droidkit/droidkit/blob/master/NOTICE
 */
package com.episode6.hackit.android.util;

import com.episode6.hackit.chop.Chop;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

public class StringFormat {

  private static final ThreadLocal<MessageDigest> MD5_MESSAGE_DIGEST = new ThreadLocal<MessageDigest>() {
    @Override
    protected MessageDigest initialValue() {
      try {
        return MessageDigest.getInstance("MD5");
      } catch (NoSuchAlgorithmException e) {
        Chop.e(e, "Could not create MessageDigest");
        throw new RuntimeException(e);
      }
    }
  };

  public static String of(String format, Object... args) {
    return String.format(Locale.US, format, args);
  }

  public static String sanitizeFileName(String url) {
    String fileName = url.replaceAll("[:\\/\\?\\&\\|]+", "-");
    if (fileName.length() > 128) {
      fileName = md5Hash(fileName);
    }
    return fileName;
  }

  public static String md5Hash(String value) {
    MessageDigest messageDigest = MD5_MESSAGE_DIGEST.get();

    messageDigest.reset();
    messageDigest.update(value.getBytes(), 0, value.length());

    String hash = new BigInteger(1, messageDigest.digest()).toString(16);
    while (hash.length() < 32)
      hash = "0" + hash;
    return hash;
  }
}
