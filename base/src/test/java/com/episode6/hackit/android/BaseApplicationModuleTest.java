package com.episode6.hackit.android;

import com.episode6.hackit.android.annotation.TestAnnotation;
import com.episode6.hackit.android.baseapp.module.BaseApplicationModule;
import com.episode6.hackit.android.app.scope.ApplicationScope;
import com.episode6.hackit.android.app.scope.ContextScope;
import com.episode6.hackit.android.testing.DefaultMockitoTest;
import com.episode6.hackit.android.testing.DefaultTestRunner;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;

import javax.inject.Inject;

import dagger.Module;
import dagger.ObjectGraph;

import static org.fest.assertions.api.Assertions.assertThat;

@RunWith(DefaultTestRunner.class)
public class BaseApplicationModuleTest extends DefaultMockitoTest {

  @Test
  public void testTomethingrandom() {
    HacKitTestClass testClass = injectObjectWithMocks(HacKitTestClass.class)
        .withInstance(TestAnnotation.class, String.class, "mock string")
        .create();

    assertThat(testClass.getHelloWorldMessage())
        .isEqualTo("mock string");
  }
  @Module(
      addsTo = BaseApplicationModule.class,
      injects = {TestEntryPoint.class}
  )
  static class TestModule {

  }

  static class TestEntryPoint {

    @Inject
    @TestAnnotation
    String testString;
  }

  @Test
  public void testHacKitModule() {
    ObjectGraph objectGraph = ObjectGraph.create(
        new ContextScope(Robolectric.application),
        new ApplicationScope(Robolectric.application),
        new BaseApplicationModule(),
        new TestModule());
    objectGraph.validate();

    TestEntryPoint testEntryPoint = objectGraph.get(TestEntryPoint.class);
    assertThat(testEntryPoint)
        .isNotNull();

    assertThat(testEntryPoint.testString)
        .isNotNull();
  }
}
