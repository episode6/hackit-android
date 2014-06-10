package com.episode6.hackit.android.typed.intent;

import com.google.gson.Gson;

import javax.inject.Inject;

public class ActionTranslatorImpl implements ActionTranslator {

  @Inject Gson mGson;

  @Override
  public <T extends Enum> T decodeAction(String actionString, Class<T> actionEnumClass) {
    return null;
  }

  @Override
  public String encodeAction(Enum actionObject) {
    return null;
  }
}
