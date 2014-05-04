package com.episode6.hackit.android.testing.inject;

public class MockUtil {

  private static org.mockito.internal.util.MockUtil mMockUtil = new org.mockito.internal.util.MockUtil();

  public static boolean isMock(Object mock) {
    return mMockUtil.isMock(mock);
  }

  public static boolean isSpy(Object mock) {
    return mMockUtil.isSpy(mock);
  }
}
