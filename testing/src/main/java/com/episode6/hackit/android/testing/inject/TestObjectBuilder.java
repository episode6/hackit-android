package com.episode6.hackit.android.testing.inject;

import com.episode6.hackit.chop.Chop;

import java.lang.annotation.Annotation;

/**
* Continence object that build objects using the mock injector
*/
public class TestObjectBuilder<T> {
  private final Class<T> mClass;

  public TestObjectBuilder(Class<T> clazz) {
    mClass = clazz;
  }

  public TestObjectBuilder<T> withInstance(BindKey bindKey, Object instance) {
    Chop.d("withInstance key: %s instance: %s", bindKey, instance);
    MockInjector.get().setInstance(bindKey, instance);
    return this;
  }

  public TestObjectBuilder<T> withInstance(Class<?> objectClass, Object instance) {
    return withInstance(
        BindKey.of(objectClass),
        instance);
  }

  public TestObjectBuilder<T> withInstance(
      Class<? extends Annotation> annotationClass,
      Class<?> objectClass,
      Object instance) {

    return withInstance(
        BindKey.of(annotationClass, objectClass),
        instance);
  }

  public T create() {
    return MockInjector.get().create(mClass);
  }
}
