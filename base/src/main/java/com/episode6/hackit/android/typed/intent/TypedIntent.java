package com.episode6.hackit.android.typed.intent;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;

import com.episode6.hackit.android.typed.bundle.BundleKey;
import com.episode6.hackit.android.typed.bundle.TypedBundle;

import java.util.Set;

import javax.annotation.Nullable;

public interface TypedIntent {
  boolean hasAction();
  <T> T getAction(Class<T> actionEnumClass);
  String getActionString();
  TypedIntent setAction(Object action);

  Set<String> getCategories();
  TypedIntent addCategory(String category);

  ComponentName getComponent();

  TypedBundle getExtras();
  TypedIntent putExtras(TypedBundle bundle);
  TypedIntent putExtras(Bundle bundle);
  TypedIntent putExtras(TypedIntent intent);
  TypedIntent putExtras(Intent intent);
  <T> T getExtra(BundleKey<T> key);
  <T> TypedIntent putExtra(BundleKey<T> key, @Nullable T value);

  Intent getIntent();
}
