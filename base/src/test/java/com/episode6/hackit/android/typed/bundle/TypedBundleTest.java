package com.episode6.hackit.android.typed.bundle;

import android.os.Bundle;

import com.episode6.hackit.android.serialize.GsonSerializer;
import com.episode6.hackit.android.serialize.MapLikeTranslator;
import com.episode6.hackit.android.serialize.MapLikeTranslatorImpl;
import com.episode6.hackit.android.serialize.Serializer;
import com.episode6.hackit.android.testing.DefaultMockitoTest;
import com.episode6.hackit.android.testing.DefaultTestRunner;
import com.episode6.hackit.chop.Chop;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;

@RunWith(DefaultTestRunner.class)
public class TypedBundleTest extends DefaultMockitoTest {

  private static final BundleNamespace NAMESPACE = BundleNamespace.fromClass(TypedBundleTest.class);

  public static final BundleKey<String> STRING_BUNDLE_KEY = NAMESPACE
      .newKey(String.class)
      .named("test_string_key");

  public static final BundleKey<Integer> INT_KEY = NAMESPACE
      .newKey(Integer.class)
      .named("int_key");

  public static final BundleKey<List<Integer>> INT_LIST_KEY = NAMESPACE
      .newGenericKey(new TypeToken<List<Integer>>(){})
      .named("int_list");

  private TypedBundleWrapper mTypedBundleWrapper;

  @Before
  public void setup() {
    mTypedBundleWrapper = createWrapper();
  }

  @Test
  public void testKey() {
    Chop.d("String key path: %s", STRING_BUNDLE_KEY.getNameKey().toString());

    Bundle realBundle = mTypedBundleWrapper.newBundle()
        .set(STRING_BUNDLE_KEY, "Test String")
        .getBundle();

    TypedBundle newWrapper = mTypedBundleWrapper.wrapBundle(realBundle);
    String myString = newWrapper.get(STRING_BUNDLE_KEY).get();

    assertThat(myString)
        .isEqualTo("Test String");
  }

  @Test
  public void testKeyFromWrongSource() {
    Chop.d("Int key path: %s", INT_KEY.getNameKey().toString());
    Bundle bundle = new Bundle();

    bundle.putInt(INT_KEY.getNameKey().toString(), 12);

    TypedBundle bundle1 = mTypedBundleWrapper.wrapBundle(bundle);

    Optional<Integer> outputInt = bundle1.get(INT_KEY);

    assertThat(outputInt.isPresent()).isTrue();
    assertThat(outputInt.get())
        .isEqualTo(12);
  }

  @Test
  public void testGeneric() {
    List<Integer> integerList = Lists.newArrayList(6, 5, 4, 3, 2, 1);
    TypedBundle bundle = mTypedBundleWrapper.newBundle()
        .set(INT_LIST_KEY, integerList);

    Bundle rawBundle = bundle.getBundle();

    TypedBundle outputBundle = mTypedBundleWrapper.wrapBundle(rawBundle);
    Optional<List<Integer>> outputList = outputBundle.get(INT_LIST_KEY);

    assertThat(outputList.isPresent()).isTrue();
    assertThat(outputList.get())
        .containsExactly(6, 5, 4, 3, 2, 1);
  }

  private TypedBundleWrapper createWrapper() {
    Serializer serializer = injectObjectWithMocks(GsonSerializer.class)
        .withInstance(Gson.class, new Gson())
        .create();

    MapLikeTranslator translator = injectObjectWithMocks(MapLikeTranslatorImpl.class)
        .withInstance(Serializer.class, serializer)
        .create();

    return injectObjectWithMocks(TypedBundleWrapperImpl.class)
        .withInstance(MapLikeTranslator.class, translator)
        .create();
  }
}
