package com.episode6.hackit.android.typed.intent;

import android.content.ComponentName;
import android.content.Intent;
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
      getIntent().setAction(mActionTranslator.encodeAction(action));
      return this;
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
    public ComponentName getComponent() {
      return getIntent().getComponent();
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
    public <T> T getExtra(BundleKey<T> key) {
      return mMapLikeTranslator.get(mIntent, key);
    }

    @Override
    public <T> TypedIntent putExtra(BundleKey<T> key, @Nullable T value) {
      mMapLikeTranslator.set(mIntent, key, value);
      return this;
    }

    @Override
    public final Intent getIntent() {
      return mIntent.getOriginal();
    }
  }
}
