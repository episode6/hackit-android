package com.episode6.hackit.android.preference;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.Set;

/**
 * @Singleton
 */
public class PrefKeyTranslatorSet {

  private final Map<Class<? extends  PrefKey>, PrefKeyTranslator<PrefKey<?>>> mPrefKeyTranslatorMap = Maps.newHashMap();

  public PrefKeyTranslatorSet(Set<PrefKeyTranslator> prefKeyTranslators) {
    for (PrefKeyTranslator translator : prefKeyTranslators) {
      mPrefKeyTranslatorMap.put(translator.getPrefKeyTypeClass(), translator);
    }
  }

  public PrefKeyTranslator getTranslatorForKey(PrefKey<?> key) {
    PrefKeyTranslator translator = mPrefKeyTranslatorMap.get(key.getClass());
    if (translator == null) {
      throw new UnsupportedOperationException();
    }
    return translator;
  }
}
