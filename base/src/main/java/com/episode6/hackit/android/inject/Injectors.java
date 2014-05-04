package com.episode6.hackit.android.inject;

import android.content.Context;
import android.content.ContextWrapper;

import com.episode6.hackit.inject.HasInjector;
import com.episode6.hackit.inject.HasInjectorScope;
import com.episode6.hackit.inject.Injector;

import java.util.List;

import dagger.ObjectGraph;

public class Injectors {

  public static Injector createFrom(HasInjectorScope rootScope) {
    List<Object> rootModules = rootScope.getModules();
    ObjectGraph graph = rootModules.isEmpty() ?
        ObjectGraph.create() :
        ObjectGraph.create(rootModules.toArray());
    return new DefaultInjector(graph);
  }

  public static Injector plusFrom(Injector baseGraph, HasInjectorScope newScope) {
    List<Object> scopeModules = newScope.getModules();

    if (scopeModules.isEmpty()) {
      return baseGraph.plus();
    }

    return baseGraph.plus(scopeModules.toArray());
  }

  public static Injector plusFrom(HasInjector hasBaseGraph, HasInjectorScope newScope) {
    return plusFrom(hasBaseGraph.getInjector(), newScope);
  }

  public static Injector plusFrom(Context baseContext, HasInjectorScope newScope) {
    return plusFrom(findInjector(baseContext), newScope);
  }

  public static Injector maybePlusFrom(Injector baseGraph, HasInjector newInjector) {
    if (newInjector instanceof  HasInjectorScope) {
      return plusFrom(baseGraph, (HasInjectorScope)newInjector);
    }
    return baseGraph;
  }

  public static Injector maybePlusFrom(HasInjector hasBaseGraph, HasInjector newInjector) {
    return maybePlusFrom(hasBaseGraph.getInjector(), newInjector);
  }

  public static Injector maybePlusFrom(Context baseContext, HasInjector newInjector) {
    return maybePlusFrom(findInjector(baseContext), newInjector);
  }

  private static HasInjector findInjector(Context context) {
    if (context instanceof HasInjector) {
      return (HasInjector)context;
    }
    if (context instanceof ContextWrapper) {
      return findInjector(((ContextWrapper)context).getBaseContext());
    }
    return (HasInjector)context.getApplicationContext();
  }
}
