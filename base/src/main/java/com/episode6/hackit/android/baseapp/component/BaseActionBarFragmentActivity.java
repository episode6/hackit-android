package com.episode6.hackit.android.baseapp.component;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;

import com.episode6.hackit.android.app.events.ActivityEvents;
import com.episode6.hackit.android.app.scope.FragmentActivityScope;
import com.episode6.hackit.android.baseapp.module.BaseActivityModule;
import com.episode6.hackit.inject.HasInjectorScope;
import com.episode6.hackit.inject.Injector;
import com.episode6.hackit.android.inject.Injectors;
import com.episode6.hackit.android.app.scope.ActivityScope;
import com.episode6.hackit.android.app.scope.ContextScope;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.squareup.otto.Bus;

import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * Base activity class, supports injection
 */
public abstract class BaseActionBarFragmentActivity extends ActionBarActivity
    implements HasInjectorScope {

  private Injector mInjector;

  @Inject
  protected Bus mBus;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    mInjector = Injectors.plusFrom(getApplicationContext(), this);
    getInjector().inject(this);

    super.onCreate(savedInstanceState);

    mBus.post(new ActivityEvents.OnCreate(this, savedInstanceState));
  }

  @Override
  public Injector getInjector() {
    return mInjector;
  }

  @Override
  public List<Object> getModules() {
    return ImmutableList.of(
        new ContextScope(this),
        new ActivityScope(this),
        new FragmentActivityScope(this),
        new BaseActivityModule());
  }

  @Override
  public void onBackPressed() {
    List<Fragment> fragments = getSupportFragmentManager().getFragments();
    if (fragments != null && !fragments.isEmpty()) {
      for (Fragment fragment : fragments) {
        if (fragment != null &&
            fragment.isResumed() &&
            fragment instanceof HandlesBackPress &&
            ((HandlesBackPress) fragment).handleBackPress()) {
          return;
        }
      }
    }
    super.onBackPressed();
  }

  @Override
  protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    mBus.post(new ActivityEvents.OnPostCreate(this, savedInstanceState));
  }

  @Override
  protected void onRestart() {
    super.onRestart();
    mBus.post(new ActivityEvents.OnRestart(this));
  }

  @Override
  protected void onStart() {
    super.onStart();
    mBus.post(new ActivityEvents.OnStart(this));
  }

  @Override
  protected void onResume() {
    super.onResume();
    mBus.post(new ActivityEvents.OnResume(this));
  }

  @Override
  protected void onPostResume() {
    super.onPostResume();
    mBus.post(new ActivityEvents.OnPostResume(this));
  }

  @Override
  protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);
    mBus.post(new ActivityEvents.OnNewIntent(this, intent));
  }

  @Override
  protected void onPause() {
    mBus.post(new ActivityEvents.OnPause(this));
    super.onPause();
  }

  @Override
  protected void onStop() {
    mBus.post(new ActivityEvents.OnStop(this));
    super.onStop();
  }

  @Override
  protected void onDestroy() {
    mBus.post(new ActivityEvents.OnDestroy(this));
    mInjector = null;
    super.onDestroy();
  }

  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    mBus.post(new ActivityEvents.OnConfigurationChanged(this, newConfig));
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    mBus.post(new ActivityEvents.OnSaveInstanceState(this, outState));
    super.onSaveInstanceState(outState);
  }

  @Override
  public void setContentView(int layoutResID) {
    super.setContentView(layoutResID);

    ButterKnife.inject(this);
  }

  @Override
  public void setContentView(View view) {
    super.setContentView(view);

    ButterKnife.inject(this);
  }

  @Override
  public void setContentView(View view, ViewGroup.LayoutParams params) {
    super.setContentView(view, params);

    ButterKnife.inject(this);
  }

  public <T extends View> T getView(int viewId) {
    T view = getViewInternal(viewId);
    if (view == null) {
      throw new NullPointerException("Could not find view!");
    }
    return view;
  }

  @SuppressWarnings("unchecked")
  public <T extends View> Optional<T> getOptionalView(int viewId) {
    return Optional.fromNullable((T)getViewInternal(viewId));
  }

  @SuppressWarnings("unchecked")
  private @Nullable <T extends View> T getViewInternal(int viewId) {
    return (T) findViewById(viewId);
  }
}
