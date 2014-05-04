package com.episode6.hackit.android.app.events;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Otto Events for fragment lifecycle
 */
public class FragmentEvents {
  private static abstract class AbstractFragmentLifecycleEvent {
    public final Fragment fragment;

    public AbstractFragmentLifecycleEvent(Fragment fragment) {
      this.fragment = fragment;
    }
  }

  public static class OnCreate extends AbstractFragmentLifecycleEvent {
    public final Bundle savedInstanceState;

    public OnCreate(Fragment fragment, Bundle savedInstanceState) {
      super(fragment);
      this.savedInstanceState = savedInstanceState;
    }
  }
}
