package com.episode6.hackit.android.util;

public interface Toaster {
  void toastLong(CharSequence message);
  void toastShort(CharSequence message);
  void toastLong(int messageResId);
  void toastShort(int messageResId);
}
