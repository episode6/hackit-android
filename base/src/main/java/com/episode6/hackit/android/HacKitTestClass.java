package com.episode6.hackit.android;

import com.episode6.hackit.android.annotation.TestAnnotation;

import javax.inject.Inject;

public class HacKitTestClass {

  private String mTestMessage;

  @Inject
  public HacKitTestClass(@TestAnnotation String testMessage) {
    mTestMessage = testMessage;
  }

  public String getHelloWorldMessage() {
    return mTestMessage;
  }
}
