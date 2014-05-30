package com.episode6.hackit.android.serialize;

import com.episode6.hackit.android.testing.DefaultMockitoTest;
import com.episode6.hackit.android.testing.DefaultTestRunner;
import com.episode6.hackit.chop.Chop;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

/**
 * just futzing with stuff, ignore this
 */
@RunWith(DefaultTestRunner.class)
public class SerializeTest extends DefaultMockitoTest {

  @Test
  public void testKeyBuilder() {
    SerializeKey<Integer> intKey = buildKey(Integer.class).build();

    SerializeKey<List<Integer>> listKey = buildKey(new TypeToken<List<Integer>>() {}).build();

    GsonSerializer serializer = new GsonSerializer();
    serializer.mGson = new Gson();

    String serialized = serializer.serialize(intKey, 10);
    Chop.d("Serialized Int: %s", serialized);
    int realVal = serializer.deserialize(intKey, serialized);
    Chop.d("Deserialized int: %d", realVal);

    List<Integer> intList = Lists.newArrayList(6, 5, 4, 3, 2, 1);
    serialized = serializer.serialize(listKey, intList);
    Chop.d("Serialized IntList: %s", serialized);

    List<Integer> deserializedList = serializer.deserialize(listKey, serialized);
    Chop.d("Deserialized int list: %s", Arrays.toString(deserializedList.toArray()));
  }

  private <T> SerializeKeyBuilder<T> buildKey(Class<T> clazz) {
    return new SerializeKeyBuilder<T>(clazz);
  }

  private <T> SerializeKeyBuilder<T> buildKey(TypeToken<T> typeToke) {
    return new SerializeKeyBuilder<T>(typeToke);
  }


}
