package com.episode6.hackit.inject;

import java.util.List;

public interface HasInjectorScope extends HasInjector {
  public List<Object> getModules();
}
