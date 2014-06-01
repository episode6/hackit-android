package com.episode6.hackit.android.typed.bundle;

import android.os.Bundle;

import javax.annotation.Nullable;

public interface TypedBundleWrapper {
  TypedBundle newBundle();
  TypedBundle wrapBundle(Bundle bundle);
  TypedBundle fromNullable(@Nullable Bundle bundle);
}
