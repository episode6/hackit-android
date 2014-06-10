// Copied and modified from Gson source: https://code.google.com/p/google-gson/source/browse/trunk/gson/src/main/java/com/google/gson/internal/bind/TypeAdapters.java#717
// Attribution below

/*
 * Copyright (C) 2011 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.episode6.hackit.android.typed.intent;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

public class ActionEnumTypeAdapterFactory implements TypeAdapterFactory {

  @Inject
  public ActionEnumTypeAdapterFactory() {

  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  @Override
  public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
    Class<? super T> rawType = typeToken.getRawType();
    if (!Enum.class.isAssignableFrom(rawType) || rawType == Enum.class) {
      return null;
    }
    if (!rawType.isEnum()) {
      rawType = rawType.getSuperclass(); // handle anonymous subclasses
    }
    return (TypeAdapter<T>) new EnumTypeAdapter(rawType);

  }

  private static final class EnumTypeAdapter<T extends Enum<T>> extends TypeAdapter<T> {
    private final Map<String, T> nameToConstant = new HashMap<String, T>();
    private final Map<T, String> constantToName = new HashMap<T, String>();

    public EnumTypeAdapter(Class<T> classOfT) {
      try {
        String classPrefix = classOfT.getName().replace("$", ".") + ".";
        for (T constant : classOfT.getEnumConstants()) {
          String name = constant.name();
          SerializedName annotation = classOfT.getField(name).getAnnotation(SerializedName.class);
          if (annotation == null) {
            name = classPrefix + name;
          } else {
            name = annotation.value();
          }
          nameToConstant.put(name, constant);
          constantToName.put(constant, name);
        }
      } catch (NoSuchFieldException e) {
        throw new AssertionError();
      }
    }
    public T read(JsonReader in) throws IOException {
      if (in.peek() == JsonToken.NULL) {
        in.nextNull();
        return null;
      }
      return nameToConstant.get(in.nextString());
    }

    public void write(JsonWriter out, T value) throws IOException {
      out.value(value == null ? null : constantToName.get(value));
    }
  }
}
