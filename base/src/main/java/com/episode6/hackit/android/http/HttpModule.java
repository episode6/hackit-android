package com.episode6.hackit.android.http;

import android.app.Application;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import com.squareup.okhttp.OkHttpClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * This module is optional. It has a non-empty constructor to ensure it only gets added at the
 * application level so it can be a true singleton and can initialize eager singletons
 */
@Module(
    library = true)
public class HttpModule {

  private CookieSyncManager mCookieSyncManager;

  public HttpModule(Application application) {
    mCookieSyncManager = CookieSyncManager.createInstance(application);
  }

  @Provides @Singleton
  CookieSyncManager provideDefaultCookieSyncManager() {
    return mCookieSyncManager;
  }

  @Provides
  CookieManager provideDefaultCookieManager() {
    return CookieManager.getInstance();
  }

  @Provides
  OkHttpClient provideDefaultOkHttpClient() {
    return new OkHttpClient();
  }
}
