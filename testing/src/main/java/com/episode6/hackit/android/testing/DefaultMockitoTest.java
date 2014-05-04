package com.episode6.hackit.android.testing;

import com.episode6.hackit.android.testing.inject.MockInjector;
import com.episode6.hackit.android.testing.inject.TestObjectBuilder;

/**
 * Base class for mockito tests with Auto Mock
 */
public class DefaultMockitoTest {

  public MockInjector mockInjector() {
    return MockInjector.get();
  }

  public <T> TestObjectBuilder<T> injectObjectWithMocks(Class<T> classToTest) {
    return new TestObjectBuilder<T>(classToTest);
  }

  public <T> T injectObjectWithMocksAndCreate(Class<T> classToTest) {
    return injectObjectWithMocks(classToTest).create();
  }

  public static void log(String message) {
    System.out.println(message);
  }

  public static void log(String format, Object... args) {
    System.out.printf(format + "\n", args);
  }

  public static void elog(String message) {
    System.err.println(message);
  }

  public static void elog(String format, Object... args) {
    System.err.printf(format + "\n", args);
  }
}
