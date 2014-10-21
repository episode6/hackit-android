package com.episode6.hackit.android.baseapp.component;

import android.content.ContentProvider;

import com.episode6.hackit.android.app.HasContext;
import com.episode6.hackit.android.inject.Injectors;
import com.episode6.hackit.inject.HasInjector;
import com.episode6.hackit.inject.Injector;

/**
 * WARNING: I DO NOT KNOW IF THIS IS SAFE FOR USE AS A BASE CLASS! USE AT YOUR OWN RISK
 */
public abstract class BaseContentProvider extends ContentProvider implements HasContext, HasInjector {

  private Injector mInjector;

  protected abstract boolean onCreateProvider();

  @Override
  public final boolean onCreate() {
    if (getContext() == null) {
      return false;
    }
    mInjector = Injectors.maybePlusFrom(getContext(), this);
    getInjector().inject(this);
    return onCreateProvider();
  }

  @Override
  public Injector getInjector() {
    return mInjector;
  }
}
