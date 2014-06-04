package com.episode6.hackit.android.typed.intent;

import android.content.Intent;

import javax.annotation.Nullable;

public interface TypedIntentWrapper {
  TypedIntent newIntent();
  TypedIntent wrapIntent(Intent intent);
  TypedIntent fromNullable(@Nullable Intent intent);
}
