package com.episode6.hackit.android.baseapp.component;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.episode6.hackit.android.app.HasContext;
import com.episode6.hackit.android.inject.InjectorContextWrapper;
import com.episode6.hackit.android.inject.Injectors;
import com.episode6.hackit.inject.HasInjector;
import com.episode6.hackit.inject.Injector;

public abstract class BaseBroadcastReceiver extends BroadcastReceiver implements HasInjector, HasContext {

  private Injector mInjector;
  private Context mContext;

  protected abstract void onReceive(Intent intent);

  @Override
  public final void onReceive(Context context, Intent intent) {
    mInjector = Injectors.maybePlusFrom(context, this);
    mContext = InjectorContextWrapper.maybeWrapContext(context, this);
    getInjector().inject(this);
    onReceive(intent);
  }

  @Override
  public Context getContext() {
    return mContext;
  }

  @Override
  public Injector getInjector() {
    return mInjector;
  }
}
