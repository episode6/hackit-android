package com.episode6.hackit.android.serialize;

import com.episode6.hackit.android.testing.DefaultMockitoTest;
import com.episode6.hackit.android.testing.DefaultTestRunner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(DefaultTestRunner.class)
public class MapLikeTranslatorTest extends DefaultMockitoTest {

  private static final MapLikeKey<Boolean> BOOL_KEY = new FakeMapLikeKey<Boolean>("bool", Boolean.class);
  private static final MapLikeKey<Integer> INT_KEY = new FakeMapLikeKey<Integer>("int", Integer.class);
  private static final MapLikeKey<Float> FLOAT_KEY = new FakeMapLikeKey<Float>("float", Float.class);
  private static final MapLikeKey<Long> LONG_KEY = new FakeMapLikeKey<Long>("long", Long.class);
  private static final MapLikeKey<String> STRING_KEY = new FakeMapLikeKey<String>("string", String.class);

  private static final MapLikeKey<TestClassToSerialize> OBJECT_KEY =
      new FakeMapLikeKey<TestClassToSerialize>("obj", TestClassToSerialize.class);

  @Mock Serializer mSerializer;
  @Mock MapLike.Getter mGetter;
  @Mock MapLike.Setter mSetter;

  MapLikeTranslator mMapLikeTranslator;

  @Before
  public void setup() {
    mMapLikeTranslator = injectObjectWithMocks(MapLikeTranslatorImpl.class).create();
  }

  @Test
  public void testSetObject() {
    TestClassToSerialize testObject = new TestClassToSerialize();
    when(mSerializer.serialize(any(SerializeKey.class), any())).thenReturn("serialize_result");

    mMapLikeTranslator.set(mSetter, OBJECT_KEY, testObject);

    InOrder inOrder = inOrder(mSerializer, mSetter);
    inOrder.verify(mSerializer).serialize(OBJECT_KEY.getSerializeKey(), testObject);
    inOrder.verify(mSetter).putString("obj", "serialize_result");
    inOrder.verifyNoMoreInteractions();
  }

  @Test
  public void testSetPrimitives() {
    mMapLikeTranslator.set(mSetter, BOOL_KEY, true);
    mMapLikeTranslator.set(mSetter, INT_KEY, 10);
    mMapLikeTranslator.set(mSetter, FLOAT_KEY, 11f);
    mMapLikeTranslator.set(mSetter, LONG_KEY, 12L);
    mMapLikeTranslator.set(mSetter, STRING_KEY, "13");

    InOrder inOrder = inOrder(mSerializer, mSetter);
    inOrder.verify(mSetter).putBool("bool", true);
    inOrder.verify(mSetter).putInt("int", 10);
    inOrder.verify(mSetter).putFloat("float", 11f);
    inOrder.verify(mSetter).putLong("long", 12L);
    inOrder.verify(mSetter).putString("string", "13");
    inOrder.verifyNoMoreInteractions();
  }

  @Test
  public void testGetObject() {
    TestClassToSerialize testObject = new TestClassToSerialize();
    when(mGetter.containsKey(anyString())).thenReturn(true);
    when(mGetter.getString(anyString())).thenReturn("deserialize_result");
    when(mSerializer.deserialize(any(SerializeKey.class), anyString())).thenReturn(testObject);

    TestClassToSerialize testResult = mMapLikeTranslator.get(mGetter, OBJECT_KEY);

    assertThat(testResult)
        .isEqualTo(testObject);

    InOrder inOrder = inOrder(mGetter, mSerializer);
    inOrder.verify(mGetter).containsKey("obj");
    inOrder.verify(mGetter).getString("obj");
    inOrder.verify(mSerializer).deserialize(OBJECT_KEY.getSerializeKey(), "deserialize_result");
    inOrder.verifyNoMoreInteractions();
  }

  @Test
  public void testGetPrimitives() {
    when(mGetter.containsKey(anyString())).thenReturn(true);

    mMapLikeTranslator.get(mGetter, BOOL_KEY);
    mMapLikeTranslator.get(mGetter, INT_KEY);
    mMapLikeTranslator.get(mGetter, FLOAT_KEY);
    mMapLikeTranslator.get(mGetter, LONG_KEY);
    mMapLikeTranslator.get(mGetter, STRING_KEY);

    verify(mGetter, times(5)).containsKey(anyString());

    InOrder inOrder = inOrder(mGetter, mSerializer);
    inOrder.verify(mGetter).getBool("bool");
    inOrder.verify(mGetter).getInt("int");
    inOrder.verify(mGetter).getFloat("float");
    inOrder.verify(mGetter).getLong("long");
    inOrder.verify(mGetter).getString("string");
    inOrder.verifyNoMoreInteractions();
  }

  @Test
  public void testGetNonExistentPrimitives() {
    when(mGetter.containsKey(anyString())).thenReturn(false);

    mMapLikeTranslator.get(mGetter, BOOL_KEY);
    mMapLikeTranslator.get(mGetter, INT_KEY);
    mMapLikeTranslator.get(mGetter, FLOAT_KEY);
    mMapLikeTranslator.get(mGetter, LONG_KEY);
    mMapLikeTranslator.get(mGetter, STRING_KEY);

    InOrder inOrder = inOrder(mSerializer, mGetter);
    inOrder.verify(mGetter).containsKey("bool");
    inOrder.verify(mGetter).containsKey("int");
    inOrder.verify(mGetter).containsKey("float");
    inOrder.verify(mGetter).containsKey("long");
    inOrder.verify(mGetter).containsKey("string");
    inOrder.verifyNoMoreInteractions();
  }

  public static class TestClassToSerialize {

  }
}
