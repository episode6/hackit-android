package com.episode6.hackit.android.app.scope;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.episode6.hackit.android.annotation.ForActivity;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
    library = true,
    includes = {ActivityScope.class})
public class FragmentActivityScope {

  private final FragmentActivity mFragmentActivity;

  public FragmentActivityScope(FragmentActivity fragmentActivity) {
    mFragmentActivity = fragmentActivity;
  }

  @Provides @Singleton
  FragmentActivity provideFragmentActivity() {
    return mFragmentActivity;
  }

  @Provides @Singleton @ForActivity
  FragmentManager provideFragmentManager(FragmentActivity fragmentActivity) {
    return fragmentActivity.getSupportFragmentManager();
  }
}
