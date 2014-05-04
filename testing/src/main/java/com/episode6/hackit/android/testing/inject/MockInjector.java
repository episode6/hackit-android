package com.episode6.hackit.android.testing.inject;

import com.episode6.hackit.android.testing.reflection.ReflectionUtil;
import com.episode6.hackit.chop.Chop;
import com.episode6.hackit.inject.Injector;
import com.google.common.collect.Lists;

import org.mockito.Mock;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;

import javax.inject.Inject;

/**
 * Injector that creates objects injected with mocks
 *
 * This object is statically accessible, however a new static instance
 * is initialized for each test (via the {@link com.episode6.hackit.android.testing.DefaultTestRunner}
 */
public class MockInjector implements Injector {

  private static MockInjector sInstance = null;

  public static MockInjector init() {
    sInstance = new MockInjector();
    return sInstance;
  }

  public static MockInjector get() {
    return sInstance;
  }

  private final MockDependencyMap mDependencyMap = new MockDependencyMap();

  private MockInjector() {
    Chop.d("init");
  }

  public MockInjector withMocksFrom(Object testWithMocks) {
    Chop.d("withMocksFrom: %s", testWithMocks.getClass().getSimpleName());
    List<Field> mockedFields =
        ReflectionUtil.getFieldsWithAnnotation(testWithMocks.getClass(), Mock.class);
    for (Field field : mockedFields) {
      setInstance(
          BindKey.of(field),
          ReflectionUtil.getValueOfField(testWithMocks, field));
    }
    return this;
  }

  public void setInstance(BindKey bindKey, Object instance) {
    mDependencyMap.set(bindKey, instance);
  }

  @Override
  public Injector plus(Object... modules) {
    return this;
  }

  @Override
  public <T> T inject(T instance) {
    Chop.d("inject: %s", instance.getClass().getSimpleName());
    Class<?> clazz = instance.getClass();
    List<Field> injectableFields = ReflectionUtil.getFieldsWithAnnotation(clazz, Inject.class);
    for (Field field : injectableFields) {
      ReflectionUtil.setValueOfField(
          instance,
          field,
          getDependency(BindKey.of(field)));
    }
    return instance;
  }

  @Override
  public <T> T get(Class<T> type) {
    return create(type);
  }

  @SuppressWarnings("unchecked")
  public <T> T create(Class<T> type) {
    Chop.d("create: %s", type.getSimpleName());
    Constructor<?> constructor = ReflectionUtil.getAnnotatedConstructor(type, Inject.class);
    if (constructor == null) {
      constructor = ReflectionUtil.getEmptyConstructor(type);
    }
    if (constructor == null) {
      throw new IllegalArgumentException("Could not find a valid constructor to inject class " + type.getName());
    }

    try {
      List<Object> paramList = getParamListForConstructor(constructor);
      return inject((T) constructor.newInstance(paramList.toArray()));
    } catch (Exception e) {
      throw new RuntimeException("Failed to construct object: " + type.getName(), e);
    }
  }

  @Override
  public void validate() {

  }

  public <T> T getDependency(BindKey bindKey) {
    Chop.d("getDependency: %s", bindKey);
    return (T) mDependencyMap.get(bindKey);
  }

  private List<Object> getParamListForConstructor(Constructor<?> constructor) throws Exception {
    List<BindKey> constructorParamKeys = BindKey.constructorParams(constructor);
    List<Object> paramList = Lists.newArrayListWithCapacity(constructorParamKeys.size());
    for (BindKey key : constructorParamKeys) {
      paramList.add(getDependency(key));
    }
    return paramList;
  }

}
