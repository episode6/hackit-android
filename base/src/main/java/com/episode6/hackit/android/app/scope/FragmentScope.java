package com.episode6.hackit.android.app.scope;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.episode6.hackit.android.annotation.ForFragment;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
    library = true,
    addsTo = ActivityScope.class,
    includes = {
        ContextScope.class})
public class FragmentScope {

  private final Fragment mFragment;

  public FragmentScope(Fragment fragment) {
    mFragment = fragment;
  }

  @Provides @Singleton
  Fragment provideFragment() {
    return mFragment;
  }

  @Provides @Singleton @ForFragment
  Context provideContext(Context context) {
    return context;
  }

  @Provides @Singleton @ForFragment
  FragmentManager provideChildFragmentManager(Fragment fragment) {
    return fragment.getChildFragmentManager();
  }
}
