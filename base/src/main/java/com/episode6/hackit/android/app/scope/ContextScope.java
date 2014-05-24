package com.episode6.hackit.android.app.scope;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import android.view.LayoutInflater;
import android.view.WindowManager;

import com.episode6.hackit.android.app.HasContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(library = true)
public class ContextScope {

  private final HasContext mContext;

  public ContextScope(final Context context) {
    mContext = new HasContext() {
      @Override
      public Context getContext() {
        return context;
      }
    };
  }

  public ContextScope(HasContext context) {
    mContext = context;
  }

  @Provides @Singleton
  Context provideContext() {
    return mContext.getContext();
  }

  @Provides @Singleton
  LayoutInflater provideLayoutInflater(Context context) {
    if (context instanceof Application) {
      return LayoutInflater.from(context);
    } else if (context instanceof Activity) {
      return ((Activity)context).getLayoutInflater();
    }
    return (LayoutInflater.from(context).cloneInContext(context));
  }

  @Provides
  AssetManager provideAssetManager(Context context) {
    return context.getAssets();
  }

  @Provides
  WindowManager provideWindowManager(Context context) {
    return (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
  }
}
