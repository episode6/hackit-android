package com.episode6.hackit.android.typed.bundle;

import android.os.Bundle;

import com.episode6.hackit.android.serialize.MapLikeTranslator;
import com.google.common.base.Optional;

import javax.annotation.Nullable;
import javax.inject.Inject;

public class TypedBundleWrapperImpl implements TypedBundleWrapper {

  @Inject MapLikeTranslator mMapLikeTranslator;

  @Override
  public TypedBundle newBundle() {
    return wrapBundle(new Bundle());
  }

  @Override
  public TypedBundle wrapBundle(Bundle bundle) {
    return new TypedBundleImpl(bundle);
  }

  public class TypedBundleImpl implements TypedBundle {

    private final BundleMapLikeWrapper mBundle;

    private TypedBundleImpl(Bundle bundle) {
      mBundle = new BundleMapLikeWrapper(bundle);
    }

    @Override
    public <T> Optional<T> get(BundleKey<T> key) {
      return Optional.fromNullable(mMapLikeTranslator.get(mBundle, key));
    }

    @Override
    public <T> TypedBundle set(BundleKey<T> key, @Nullable T value) {
      mMapLikeTranslator.set(mBundle, key, value);
      return this;
    }

    @Override
    public Bundle getBundle() {
      return mBundle.getOriginal();
    }
  }
}
