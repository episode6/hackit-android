package com.episode6.hackit.android.util;

import android.content.Context;
import android.widget.Toast;

import javax.inject.Inject;

public class ToasterImpl implements Toaster {

  @Inject Context mContext;

  @Override
  public void toastLong(CharSequence message) {
    Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
  }

  @Override
  public void toastShort(CharSequence message) {
    Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
  }

  @Override
  public void toastLong(int messageResId) {
    toastLong(mContext.getString(messageResId));
  }

  @Override
  public void toastShort(int messageResId) {
    toastShort(mContext.getString(messageResId));
  }
}
