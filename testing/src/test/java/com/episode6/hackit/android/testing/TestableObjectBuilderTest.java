package com.episode6.hackit.android.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import javax.inject.Inject;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(DefaultTestRunner.class)
public class TestableObjectBuilderTest extends DefaultMockitoTest {

  private static final String PLAIN_MESSAGE = "test message";
  private static final String ANNOTATED_MESSAGE = "annotated_test_message";

  @Mock
  TestClass mMockTestClass;

  @Mock
  @FakeAnnotation
  TestClass mMockAnnotatedTestClass;

  @Test
  public void testMockInjectorGet() {

    when(mMockTestClass.getMessage()).thenReturn(PLAIN_MESSAGE);
    when(mMockAnnotatedTestClass.getMessage()).thenReturn(ANNOTATED_MESSAGE);

    TestEntryPoint testEntryPoint = injectObjectWithMocksAndCreate(TestEntryPoint.class);

    assertThat(testEntryPoint)
        .isNotNull();
    assertThat(testEntryPoint.testClass)
        .isNotNull()
        .isEqualTo(mMockTestClass);
    assertThat(testEntryPoint.annotatedTestClass)
        .isNotNull()
        .isEqualTo(mMockAnnotatedTestClass);

    assertThat(testEntryPoint.testClass.getMessage())
        .isNotNull()
        .isEqualTo(PLAIN_MESSAGE);

    assertThat(testEntryPoint.annotatedTestClass.getMessage())
        .isNotNull()
        .isEqualTo(ANNOTATED_MESSAGE);
  }

  public static class TestClass {
    public String getMessage() {
      throw new RuntimeException("This is the \"real\" implementation, it should not have been called");
    }
  }

  public static class TestEntryPoint {

    @Inject
    TestClass testClass;

    @Inject
    @FakeAnnotation
    TestClass annotatedTestClass;
  }
}
