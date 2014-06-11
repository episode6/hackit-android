package com.episode6.hackit.android.serialize;

import com.episode6.hackit.android.testing.DefaultMockitoTest;
import com.episode6.hackit.android.testing.DefaultTestRunner;
import com.episode6.hackit.chop.Chop;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

/**
 * just futzing with stuff, ignore this
 */
@RunWith(DefaultTestRunner.class)
public class SerializeTest extends DefaultMockitoTest {

  private Serializer mSerializer;

  @Before
  public void setup() {
    mSerializer = injectObjectWithMocks(GsonSerializer.class)
        .withInstance(Gson.class, new Gson()) // real gson
        .create();
  }

  @Test
  public void testKeyBuilder() {
    SerializeKey<Integer> intKey = SerializeKey.key(Integer.class);
    SerializeKey<List<Integer>> listKey = SerializeKey.genericKey(new TypeToken<List<Integer>>() {});

    String serialized = mSerializer.serialize(intKey, 10);
    Chop.d("Serialized Int: %s", serialized);
    int realVal = mSerializer.deserialize(intKey, serialized);
    Chop.d("Deserialized int: %d", realVal);

    List<Integer> intList = Lists.newArrayList(6, 5, 4, 3, 2, 1);
    serialized = mSerializer.serialize(listKey, intList);
    Chop.d("Serialized IntList: %s", serialized);

    List<Integer> deserializedList = mSerializer.deserialize(listKey, serialized);
    Chop.d("Deserialized int list: %s", Arrays.toString(deserializedList.toArray()));
  }
}
