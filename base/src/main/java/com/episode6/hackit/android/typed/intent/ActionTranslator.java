package com.episode6.hackit.android.typed.intent;

public interface ActionTranslator {
  public <T> T decodeAction(String actionString, Class<T> actionEnumClass);
  public String encodeAction(Object actionObject);
}
