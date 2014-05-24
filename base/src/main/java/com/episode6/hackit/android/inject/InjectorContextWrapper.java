package com.episode6.hackit.android.inject;

import android.content.Context;
import android.content.ContextWrapper;

import com.episode6.hackit.inject.HasInjector;
import com.episode6.hackit.inject.HasInjectorScope;
import com.episode6.hackit.inject.Injector;

public class InjectorContextWrapper extends ContextWrapper implements HasInjector {

  public static Context maybeWrapContext(Context baseContext, HasInjector hasInjector) {
    if (hasInjector instanceof HasInjectorScope) {
      return new InjectorContextWrapper(baseContext, hasInjector);
    }
    return baseContext;
  }

  public static Context wrapContext(Context baseContext, HasInjectorScope hasInjector) {
    return new InjectorContextWrapper(baseContext, hasInjector);
  }

  public static Context wrapContext(Context baseContext) {
    return new InjectorContextWrapper(baseContext);
  }

  private HasInjector mHasInjector;

  protected InjectorContextWrapper(Context base) {
    this(base, null);
  }

  protected InjectorContextWrapper(Context base, HasInjector injector) {
    super(base);
    mHasInjector = injector;
  }

  public void setHasInjector(HasInjector injector) {
    if (mHasInjector != null) {
      throw new IllegalArgumentException("Already set mHasInjector on InjectorContextWrapper");
    }
    mHasInjector = injector;
  }

  @Override
  public Injector getInjector() {
    return mHasInjector.getInjector();
  }
}
