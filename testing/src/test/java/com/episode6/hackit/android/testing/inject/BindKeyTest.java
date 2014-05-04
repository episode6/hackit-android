package com.episode6.hackit.android.testing.inject;

import com.episode6.hackit.android.testing.DefaultTestRunner;
import com.episode6.hackit.android.testing.FakeAnnotation;
import com.google.common.collect.Sets;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Set;

import static org.fest.assertions.api.Assertions.assertThat;

@RunWith(DefaultTestRunner.class)
public class BindKeyTest {

  @Test
  public void testInHashSet() {

    // Create initial hash set
    Set<BindKey> keySet = Sets.newHashSet(
        BindKey.of(String.class),
        BindKey.of(Integer.class),
        BindKey.of(FakeAnnotation.class, String.class),
        BindKey.of(FakeAnnotation.class, Integer.class));

    // Try to add new instances of the same BindKeys, ensure they can't be added
    assertThat(keySet.add(BindKey.of(String.class))).isFalse();
    assertThat(keySet.add(BindKey.of(Integer.class))).isFalse();
    assertThat(keySet.add(BindKey.of(FakeAnnotation.class, String.class))).isFalse();
    assertThat(keySet.add(BindKey.of(FakeAnnotation.class, Integer.class))).isFalse();

    // Final sanity check assert
    assertThat(keySet)
        .containsOnly(
            BindKey.of(String.class),
            BindKey.of(Integer.class),
            BindKey.of(FakeAnnotation.class, String.class),
            BindKey.of(FakeAnnotation.class, Integer.class));
  }
}
