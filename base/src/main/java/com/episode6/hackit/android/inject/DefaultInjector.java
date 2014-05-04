package com.episode6.hackit.android.inject;

import com.episode6.hackit.inject.Injector;

import dagger.ObjectGraph;

public class DefaultInjector implements Injector {

  private final ObjectGraph mObjectGraph;

  DefaultInjector(ObjectGraph objectGraph) {
    mObjectGraph = objectGraph;
    mObjectGraph.injectStatics();
  }

  @Override
  public DefaultInjector plus(Object... modules) {
    return new DefaultInjector(mObjectGraph.plus(modules));
  }

  @Override
  public <T> T inject(T instance) {
    return mObjectGraph.inject(instance);
  }

  @Override
  public <T> T get(Class<T> type) {
    return mObjectGraph.get(type);
  }

  @Override
  public void validate() {
    mObjectGraph.validate();
  }
}
