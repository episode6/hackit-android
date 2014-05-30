package com.episode6.hackit.android.serialize;

import java.lang.reflect.Type;

public interface SerializeKey<V> {
  String getPath();
  Type getObjectType();
}
