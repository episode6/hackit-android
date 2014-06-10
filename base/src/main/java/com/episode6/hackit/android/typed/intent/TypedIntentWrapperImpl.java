package com.episode6.hackit.android.typed.intent;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import com.episode6.hackit.android.serialize.MapLikeTranslator;
import com.episode6.hackit.android.typed.bundle.BundleKey;
import com.episode6.hackit.android.typed.bundle.TypedBundle;
import com.episode6.hackit.android.typed.bundle.TypedBundleWrapper;

import java.util.Set;

import javax.annotation.Nullable;
import javax.inject.Inject;

public class TypedIntentWrapperImpl implements TypedIntentWrapper {

  @Inject ContentResolver mContentResolver;
  @Inject PackageManager mPackageManager;
  @Inject MapLikeTranslator mMapLikeTranslator;
  @Inject TypedBundleWrapper mTypedBundleWrapper;
  @Inject ActionTranslator mActionTranslator;


  @Override
  public TypedIntent newIntent() {
    return wrapIntent(new Intent());
  }

  @Override
  public TypedIntent wrapIntent(Intent intent) {
    return new TypedIntentImpl(intent);
  }

  @Override
  public TypedIntent fromNullable(@Nullable Intent intent) {
    return intent == null ? newIntent() : wrapIntent(intent);
  }

  public class TypedIntentImpl implements TypedIntent {

    private final IntentMapLikeWrapper mIntent;

    TypedIntentImpl(Intent intent) {
      mIntent = new IntentMapLikeWrapper(intent);
    }

    @Override
    public boolean hasAction() {
      return !TextUtils.isEmpty(getActionString());
    }

    @Override
    public <T extends Enum> T getAction(Class<T> actionEnumClass) {
      return mActionTranslator.decodeAction(getActionString(), actionEnumClass);
    }

    @Override
    public String getActionString() {
      return getIntent().getAction();
    }

    @Override
    public TypedIntent setAction(Enum action) {
      return setAction(mActionTranslator.encodeAction(action));
    }

    @Override
    public TypedIntent setAction(String actionString) {
      getIntent().setAction(actionString);
      return this;
    }

    @Override
    public boolean hasCategory(String category) {
      return getIntent().hasCategory(category);
    }

    @Override
    public Set<String> getCategories() {
      return getIntent().getCategories();
    }

    @Override
    public TypedIntent addCategory(String category) {
      getIntent().addCategory(category);
      return this;
    }

    @Override
    public TypedIntent removeCategory(String category) {
      getIntent().removeCategory(category);
      return this;
    }

    @Override
    public ComponentName getComponent() {
      return getIntent().getComponent();
    }

    @Override
    public ComponentName resolveComponent() {
      return getIntent().resolveActivity(mPackageManager);
    }

    @Override
    public TypedIntent setClass(Context packageContext, Class<?> cls) {
      getIntent().setClass(packageContext, cls);
      return this;
    }

    @Override
    public TypedIntent setClassName(Context packageContext, String className) {
      getIntent().setClassName(packageContext, className);
      return this;
    }

    @Override
    public TypedIntent setClassName(String packageName, String className) {
      getIntent().setClassName(packageName, className);
      return this;
    }

    @Override
    public TypedIntent setComponent(ComponentName component) {
      getIntent().setComponent(component);
      return this;
    }

    @Override
    public boolean hasData() {
      return getData() != null;
    }

    @Override
    public Uri getData() {
      return getIntent().getData();
    }

    @Override
    public String getDataString() {
      return getIntent().getDataString();
    }

    @Override
    public TypedIntent setData(Uri data) {
      getIntent().setData(data);
      return this;
    }

    @Override
    public int getFlags() {
      return getIntent().getFlags();
    }

    @Override
    public TypedIntent addFlags(int flags) {
      getIntent().addFlags(flags);
      return this;
    }

    @Override
    public TypedIntent setFlags(int flags) {
      getIntent().setFlags(flags);
      return this;
    }

    @Override
    public String getPackage() {
      return getIntent().getPackage();
    }

    @Override
    public TypedIntent setPackage(String packageString) {
      getIntent().setPackage(packageString);
      return this;
    }

    @Override
    public String getScheme() {
      return getIntent().getScheme();
    }

    @Override
    public Rect getSourceBounds() {
      return getIntent().getSourceBounds();
    }

    @Override
    public TypedIntent setSourceBounds(Rect sourceBounds) {
      getIntent().setSourceBounds(sourceBounds);
      return this;
    }

    @Override
    public String getType() {
      return getIntent().getType();
    }

    @Override
    public String resolveType() {
      return getIntent().resolveType(mContentResolver);
    }

    @Override
    public TypedIntent setType(String type) {
      getIntent().setType(type);
      return this;
    }

    @Override
    public boolean hasFileDescriptors() {
      return getIntent().hasFileDescriptors();
    }

    @Override
    public TypedIntent setExtrasClassLoader(ClassLoader classLoader) {
      getIntent().setExtrasClassLoader(classLoader);
      return this;
    }

    @Override
    public boolean hasExtra(BundleKey key) {
      return getIntent().hasExtra(key.getKeyString());
    }

    @Override
    public TypedBundle getExtras() {
      return mTypedBundleWrapper.wrapBundle(getIntent().getExtras());
    }

    @Override
    public TypedIntent putExtras(TypedBundle bundle) {
      return putExtras(bundle.getBundle());
    }

    @Override
    public TypedIntent putExtras(Bundle bundle) {
      getIntent().putExtras(bundle);
      return this;
    }

    @Override
    public TypedIntent putExtras(TypedIntent intent) {
      return putExtras(intent.getIntent());
    }

    @Override
    public TypedIntent putExtras(Intent intent) {
      getIntent().putExtras(intent);
      return this;
    }

    @Override
    public TypedIntent replaceExtras(TypedBundle bundle) {
      return replaceExtras(bundle.getBundle());
    }

    @Override
    public TypedIntent replaceExtras(Bundle bundle) {
      getIntent().replaceExtras(bundle);
      return this;
    }

    @Override
    public TypedIntent replaceExtras(TypedIntent intent) {
      return replaceExtras(intent.getIntent());
    }

    @Override
    public TypedIntent replaceExtras(Intent intent) {
      getIntent().replaceExtras(intent);
      return this;
    }

    @Override
    public <T> T getExtra(BundleKey<T> key) {
      return mMapLikeTranslator.get(mIntent, key);
    }

    @Override
    public <T> TypedIntent putExtra(BundleKey<T> key, @Nullable T value) {
      mMapLikeTranslator.set(mIntent, key, value);
      return this;
    }

    @Override
    public TypedIntent removeExtra(BundleKey key) {
      getIntent().removeExtra(key.getKeyString());
      return this;
    }

    @Override
    public final Intent getIntent() {
      return mIntent.getOriginal();
    }
  }
}
