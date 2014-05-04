package com.episode6.hackit.android.testing.inject;

import com.episode6.hackit.android.testing.DefaultMockitoTest;
import com.episode6.hackit.android.testing.DefaultTestRunner;
import com.episode6.hackit.android.testing.FakeAnnotation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.MockUtil;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.fest.assertions.api.Assertions.assertThat;

@RunWith(DefaultTestRunner.class)
public class MockDependencyMapTest extends DefaultMockitoTest {

  MockUtil mMockUtil = new MockUtil();

  @Test
  public void testConsistency() {
    MockDependencyMap mockDependencyMap = new MockDependencyMap();

    assertFalse(mockDependencyMap.containsKey(BindKey.of(Integer.class)));
    assertFalse(mockDependencyMap.containsKey(BindKey.of(FakeAnnotation.class, String.class)));

    String testString = "testString";
    Integer testInt = 12;
    String annotatedTestString = "annotatedTestString";
    Integer annotatedInt = 17;

    mockDependencyMap.set(BindKey.of(String.class), testString);
    mockDependencyMap.set(BindKey.of(Integer.class), testInt);
    mockDependencyMap.set(BindKey.of(FakeAnnotation.class, String.class), annotatedTestString);
    mockDependencyMap.set(BindKey.of(FakeAnnotation.class, Integer.class), annotatedInt);

    assertTrue(mockDependencyMap.containsKey(BindKey.of(String.class)));
    assertTrue(mockDependencyMap.containsKey(BindKey.of(FakeAnnotation.class, String.class)));

    assertThat(mockDependencyMap.get(BindKey.of(String.class)))
        .isEqualTo(testString);
    assertThat(mockDependencyMap.get(BindKey.of(Integer.class)))
        .isEqualTo(testInt);
    assertThat(mockDependencyMap.get(BindKey.of(FakeAnnotation.class, String.class)))
        .isEqualTo(annotatedTestString);
    assertThat(mockDependencyMap.get(BindKey.of(FakeAnnotation.class, Integer.class)))
        .isEqualTo(annotatedInt);

    Object testMock = mockDependencyMap.get(BindKey.of(Object.class));
    assertThat(testMock)
        .isInstanceOf(Object.class);
    assertTrue(mMockUtil.isMock(testMock));



  }
}
