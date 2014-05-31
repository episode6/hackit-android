package com.episode6.hackit.android.typed.bundle;

import android.os.Bundle;

public interface TypedBundleWrapper {
  TypedBundle newBundle();
  TypedBundle wrapBundle(Bundle bundle);
}
