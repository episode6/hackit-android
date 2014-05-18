package com.episode6.hackit.android.baseapp.component;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.episode6.hackit.android.app.HasContext;
import com.episode6.hackit.android.app.events.FragmentEvents;
import com.episode6.hackit.android.inject.InjectorContextWrapper;
import com.episode6.hackit.android.inject.Injectors;
import com.episode6.hackit.inject.HasInjector;
import com.episode6.hackit.inject.Injector;
import com.squareup.otto.Bus;

import javax.annotation.Nullable;
import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * Base Fragment, supports injection.
 *
 * This class replaces onCreateView with onCreateContentView to support ButterKnife View Injection
 */
public abstract class BaseFragment extends Fragment implements HasInjector, HasContext, HandlesBackPress {

  private Injector mInjector;
  private Context mContext;

  @Inject protected LayoutInflater mLayoutInflater;
  @Inject protected Bus mBus;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    mInjector = Injectors.maybePlusFrom(getActivity(), this);
    mContext = InjectorContextWrapper.maybeWrapContext(getActivity(), this);
    getInjector().inject(this);

    super.onCreate(savedInstanceState);

    mBus.post(new FragmentEvents.OnCreate(this, savedInstanceState));
  }

  @Override
  public void onDestroy() {
    mInjector = null;
    mContext = null;
    super.onDestroy();
  }

  @Override
  public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View returnValue = onCreateContentView(
        getContext(),
        inflater,
        container,
        savedInstanceState);

    if (returnValue != null) {
      ButterKnife.inject(this, returnValue);
    }
    return returnValue;
  }

  public @Nullable View onCreateContentView(
      Context context,
      LayoutInflater inflater,
      ViewGroup container,
      Bundle savedInstanceState) {
    return null;
  }

  @Override
  public Context getContext() {
    return mContext;
  }

  @Override
  public Injector getInjector() {
    return mInjector;
  }

  @Override
  public LayoutInflater getLayoutInflater(Bundle savedInstanceState) {
    return getLayoutInflater();
  }

  public LayoutInflater getLayoutInflater() {
    return mLayoutInflater;
  }

  @Override
  public boolean handleBackPress() {
    return false;
  }
}
