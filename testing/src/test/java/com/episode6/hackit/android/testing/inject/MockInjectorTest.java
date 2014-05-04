package com.episode6.hackit.android.testing.inject;

import com.episode6.hackit.android.testing.AlternateFakeAnnotation;
import com.episode6.hackit.android.testing.DefaultMockitoTest;
import com.episode6.hackit.android.testing.DefaultTestRunner;
import com.episode6.hackit.android.testing.FakeAnnotation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import java.util.Set;

import javax.inject.Inject;
import javax.inject.Provider;

import static junit.framework.Assert.assertTrue;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(DefaultTestRunner.class)
public class MockInjectorTest extends DefaultMockitoTest {

  private static final String PLAIN_MESSAGE = "test message";
  private static final String ANNOTATED_MESSAGE = "annotated_test_message";

  @Mock
  TestClass mMockTestClass;

  @Mock
  @FakeAnnotation
  TestClass mMockAnnotatedTestClass;

  @Test
  public void testMockInjector() {
    MockInjector mockInjector = mockInjector();

    TestClass testClass = mockInjector.getDependency(BindKey.of(TestClass.class));
    assertThat(testClass)
        .isEqualTo(mMockTestClass);


    when(mMockTestClass.getMessage()).thenReturn(PLAIN_MESSAGE);
    when(mMockAnnotatedTestClass.getMessage()).thenReturn(ANNOTATED_MESSAGE);

    TestEntryPoint testEntryPoint = mockInjector().create(TestEntryPoint.class);

    assertThat(testEntryPoint.testClass)
        .isEqualTo(mMockTestClass)
        .isEqualTo(testEntryPoint.testClassProvider.get());
    assertThat(testEntryPoint.annotatedTestClass)
        .isEqualTo(mMockAnnotatedTestClass)
        .isEqualTo(testEntryPoint.annotatedTestClassProvider.get());
    assertThat(testEntryPoint.alternateAnnotatedTestClass)
        .isNotNull();
    assertTrue(MockUtil.isMock(testEntryPoint.alternateAnnotatedTestClass));

    assertThat(testEntryPoint.testClass.getMessage())
        .isEqualTo(PLAIN_MESSAGE);

    assertThat(testEntryPoint.annotatedTestClass.getMessage())
        .isEqualTo(ANNOTATED_MESSAGE);

    assertThat(testEntryPoint.alternateAnnotatedTestClass.getMessage())
        .isNull();

    assertThat(testEntryPoint.setOfClasses)
        .isEmpty();
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

    @Inject
    @AlternateFakeAnnotation
    TestClass alternateAnnotatedTestClass;

    @Inject
    Provider<TestClass> testClassProvider;

    @Inject
    @FakeAnnotation
    Provider<TestClass> annotatedTestClassProvider;

    @Inject
    Set<TestClass> setOfClasses;
  }
}
