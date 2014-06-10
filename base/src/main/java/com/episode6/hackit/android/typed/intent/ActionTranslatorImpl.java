package com.episode6.hackit.android.typed.intent;

import com.google.gson.Gson;

import javax.inject.Inject;

public class ActionTranslatorImpl implements ActionTranslator {

  @Inject @ForIntentActionTranslator Gson mGson;

  @Override
  public <T extends Enum> T decodeAction(String actionString, Class<T> actionEnumClass) {
    if (actionString == null) {
      return null;
    }

    return mGson.fromJson(actionString, actionEnumClass);
  }

  @Override
  public String encodeAction(Enum actionObject) {
    if (actionObject == null) {
      return null;
    }

    String serialized = mGson.toJson(actionObject);
    return serialized.substring(1, serialized.length()-1); // remove quotes
  }
}
