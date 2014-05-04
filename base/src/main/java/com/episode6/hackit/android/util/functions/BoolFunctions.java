package com.episode6.hackit.android.util.functions;

import com.google.common.base.Function;

import javax.annotation.Nullable;

public class BoolFunctions {

  public static <F> Function<F, Boolean> alwaysTrue() {
    return new Function<F, Boolean>() {

      @Nullable
      @Override
      public Boolean apply(@Nullable F input) {
        return true;
      }
    };
  }

  public static <F> Function<F, Boolean> alwaysFalse() {
    return new Function<F, Boolean>() {

      @Nullable
      @Override
      public Boolean apply(@Nullable F input) {
        return false;
      }
    };
  }

  public static <F> Function<F, Boolean> isSame(final F instance) {
    return new Function<F, Boolean>() {

      @Nullable
      @Override
      public Boolean apply(@Nullable F input) {
        return input == instance;
      }
    };
  }

  public static <F> Function<F, Boolean> isNotSame(final F instance) {
    return new Function<F, Boolean>() {

      @Nullable
      @Override
      public Boolean apply(@Nullable F input) {
        return input != instance;
      }
    };
  }

  public static <F> Function<F, Boolean> isEquals(final F instance) {
    return new Function<F, Boolean>() {

      @Nullable
      @Override
      public Boolean apply(@Nullable F input) {
        return ((Object)input).equals(instance);
      }
    };
  }

  public static <F> Function<F, Boolean> isNotEquals(final F instance) {
    return new Function<F, Boolean>() {

      @Nullable
      @Override
      public Boolean apply(@Nullable F input) {
        return !((Object)input).equals(instance);
      }
    };
  }

  public static <F> Function<F, Boolean> isInstanceOf(final Class<?> clazz) {
    return new Function<F, Boolean>() {

      @Nullable
      @Override
      public Boolean apply(@Nullable F input) {
        return clazz.isInstance(input);
      }
    };
  }

  public static <F> Function<F, Boolean> isNotInstanceOf(final Class<?> clazz) {
    return new Function<F, Boolean>() {

      @Nullable
      @Override
      public Boolean apply(@Nullable F input) {
        return !clazz.isInstance(input);
      }
    };
  }
}
