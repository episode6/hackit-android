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
import com.google.gson.Gson;

import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static org.fest.assertions.api.Assertions.assertThat;

@RunWith(DefaultTestRunner.class)
public class TypedBundleTest extends DefaultMockitoTest {


  private static final BundleArgumentNamespace NAMESPACE
      = BundleArgumentNamespace.extendFrom(TypedBundleTest.class);

  public static final BundleKey<String> STRING_BUNDLE_KEY
      = NAMESPACE.newKey(String.class).named("test_string_key");

  public static final BundleKey<Integer> INT_KEY
      = NAMESPACE.newKey(Integer.class).named("int_key");

  @Test
  public void testKey() {
    Chop.d("String key path: %s", STRING_BUNDLE_KEY.getKeyString());

    TypedBundleWrapper wrapper = createBundleWrapper();

    TypedBundle bundle = wrapper.newBundle();
    bundle.set(STRING_BUNDLE_KEY, "Test String");

    Bundle realBundle = bundle.getBundle();

    TypedBundle newWrapper = wrapper.wrapBundle(realBundle);
    String myString = newWrapper.get(STRING_BUNDLE_KEY).get();

    assertThat(myString)
        .isEqualTo("Test String");
  }

  @Test
  public void testKeyFromWrongSource() {
    Chop.d("Int key path: %s", INT_KEY.getKeyString());
    Bundle bundle = new Bundle();

    bundle.putInt(INT_KEY.getKeyString(), 12);

    TypedBundleWrapper wrapper = createBundleWrapper();
    TypedBundle bundle1 = wrapper.wrapBundle(bundle);

    Optional<Integer> outputInt = bundle1.get(INT_KEY);

    assertThat(outputInt.isPresent()).isTrue();
    assertThat(outputInt.get())
        .isEqualTo(12);
  }

  private TypedBundleWrapper createBundleWrapper() {
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
