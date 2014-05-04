package com.episode6.hackit.android.inject;

import android.content.Context;
import android.content.ContextWrapper;

import com.episode6.hackit.inject.HasInjector;
import com.episode6.hackit.inject.HasInjectorScope;
import com.episode6.hackit.inject.Injector;

public class InjectorContextWrapper extends ContextWrapper implements HasInjector {

  public static Context maybeWrapContext(Context baseContext, HasInjector hasInjector) {
    if (hasInjector instanceof HasInjectorScope) {
      return new InjectorContextWrapper(baseContext, hasInjector.getInjector());
    }
    return baseContext;
  }

  public static Context wrapContext(Context baseContext, HasInjectorScope hasInjector) {
    return new InjectorContextWrapper(baseContext, hasInjector.getInjector());
  }

  private final Injector mInjector;

  protected InjectorContextWrapper(Context base, Injector injector) {
    super(base);
    if (injector == null) {
      throw new NullPointerException("Null injector passed to InjectorContextWrapper");
    }
    mInjector = injector;
  }

  @Override
  public Injector getInjector() {
    return mInjector;
  }
}
