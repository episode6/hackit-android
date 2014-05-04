package com.episode6.hackit.inject;

public interface Injector {
  public Injector plus(Object... modules);
  public <T> T inject(T instance);
  public <T> T get(Class<T> type);
  public void validate();
}
