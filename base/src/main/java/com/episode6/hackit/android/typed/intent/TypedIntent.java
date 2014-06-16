package com.episode6.hackit.android.typed.intent;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;

import com.episode6.hackit.android.typed.bundle.BundleKey;
import com.episode6.hackit.android.typed.bundle.TypedBundle;
import com.google.common.base.Optional;

import java.util.Set;

import javax.annotation.Nullable;

public interface TypedIntent {
  <T extends Enum> Optional<T> getAction(Class<T> actionEnumClass);
  Optional<String> getActionString();
  TypedIntent setAction(Enum action);
  TypedIntent setAction(String actionString);

  boolean hasCategory(String category);
  Set<String> getCategories();
  TypedIntent addCategory(String category);
  TypedIntent removeCategory(String category);

  Optional<ComponentName> getComponent();
  ComponentName resolveComponent();
  TypedIntent setClass(Context packageContext, Class<?> cls);
  TypedIntent setClassName(Context packageContext, String className);
  TypedIntent setClassName(String packageName, String className);
  TypedIntent setComponent(ComponentName component);

  Optional<Uri> getData();
  Optional<String> getDataString();
  TypedIntent setData(Uri data);

  int getFlags();
  TypedIntent addFlags(int flags);
  TypedIntent setFlags(int flags);

  Optional<String> getPackage();
  TypedIntent setPackage(String packageString);

  Optional<String> getScheme();

  Optional<Rect> getSourceBounds();
  TypedIntent setSourceBounds(Rect sourceBounds);

  Optional<String> getType();
  String resolveType();
  TypedIntent setType(String type);

  boolean hasFileDescriptors();

  TypedIntent setExtrasClassLoader(ClassLoader classLoader);

  boolean hasExtra(BundleKey key);
  TypedBundle getExtras();
  TypedIntent putExtras(TypedBundle bundle);
  TypedIntent putExtras(Bundle bundle);
  TypedIntent putExtras(TypedIntent intent);
  TypedIntent putExtras(Intent intent);
  TypedIntent replaceExtras(TypedBundle bundle);
  TypedIntent replaceExtras(Bundle bundle);
  TypedIntent replaceExtras(TypedIntent intent);
  TypedIntent replaceExtras(Intent intent);
  <T> Optional<T> getExtra(BundleKey<T> key);
  <T> TypedIntent putExtra(BundleKey<T> key, @Nullable T value);
  TypedIntent removeExtra(BundleKey key);

  Intent getIntent();
}
