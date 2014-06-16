package com.episode6.hackit.android.bus;

import android.os.Handler;

import com.episode6.hackit.android.annotation.ForUiThread;
import com.squareup.otto.Bus;

import javax.inject.Inject;

public class MainThreadBusPoster implements BusPoster {

  @Inject @ForUiThread Bus mBus;
  @Inject @ForUiThread Handler mHandler;

  @Override
  public void post(Object object) {
    mHandler.post(new PosterRunnable(object));
  }

  private final class PosterRunnable implements Runnable {
    private final Object mMessage;

    PosterRunnable(Object message) {
      mMessage = message;
    }

    @Override
    public void run() {
      mBus.post(mMessage);
    }
  }
}
