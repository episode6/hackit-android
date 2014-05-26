package com.episode6.hackit.android.ui.baseui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.episode6.hackit.android.app.HasContext;
import com.episode6.hackit.android.inject.InjectorContextWrapper;
import com.episode6.hackit.android.inject.Injectors;
import com.episode6.hackit.inject.HasInjector;
import com.episode6.hackit.inject.Injector;

public class BaseFrameLayout extends FrameLayout implements HasInjector, HasContext {

  private Injector mInjector;

  public BaseFrameLayout(Context context) {
    super(InjectorContextWrapper.wrapContext(context));
    initView(context, null, 0);
  }

  public BaseFrameLayout(Context context, AttributeSet attrs) {
    super(InjectorContextWrapper.wrapContext(context), attrs);
    initView(context, attrs, 0);
  }

  public BaseFrameLayout(Context context, AttributeSet attrs, int defStyle) {
    super(InjectorContextWrapper.wrapContext(context), attrs, defStyle);
    initView(context, attrs, defStyle);
  }

  private void initView(Context context, AttributeSet attrs, int defStyle) {
    getWrappedContext().setHasInjector(this);
    mInjector = Injectors.maybePlusFrom(getWrappedContext().getBaseContext(), this);
    mInjector.inject(this);

    onInitView(attrs, defStyle);
  }

  protected void onInitView(AttributeSet attrs, int defStyle) {
    // Override
  }

  protected final InjectorContextWrapper getWrappedContext() {
    return (InjectorContextWrapper) getContext();
  }

  @Override
  public Injector getInjector() {
    return mInjector;
  }
}
