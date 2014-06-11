package com.episode6.hackit.android.serialize;


import com.google.gson.reflect.TypeToken;

public class FakeMapLikeKey<V> extends AbstractMapLikeKey<V> {

  public static final Namespace NAMESPACE = new Namespace(".") {

    @Override
    public NamespacedKey buildKey(String newNameSegment) {
      return super.buildKey(newNameSegment);
    }
  };

  public FakeMapLikeKey(String name, Class<V> typeOf) {
    this(name, SerializeKey.key(typeOf));
  }

  public FakeMapLikeKey(String name, TypeToken<V> typeToken) {
    this(name, SerializeKey.genericKey(typeToken));
  }

  public FakeMapLikeKey(String name, SerializeKey<V> serializeKey) {
    super(NAMESPACE.buildKey(name), serializeKey);
  }
}
