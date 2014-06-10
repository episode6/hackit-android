package com.episode6.hackit.android.typed.intent;

public interface ActionTranslator {
  public <T extends Enum> T decodeAction(String actionString, Class<T> actionEnumClass);
  public String encodeAction(Enum actionObject);
}
