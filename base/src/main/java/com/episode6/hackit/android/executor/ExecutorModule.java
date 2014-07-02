package com.episode6.hackit.android.executor;

import android.os.Handler;
import android.os.Looper;

import com.episode6.hackit.android.annotation.ForUiThread;
import com.episode6.hackit.android.app.scope.ApplicationScope;

import java.util.concurrent.Executor;

import dagger.Module;
import dagger.Provides;

@Module(
    library = true,
    includes = {
        ApplicationScope.class})
public class ExecutorModule {

  @Provides @ForUiThread
  Handler provideUiHandler() {
    return new Handler(Looper.getMainLooper());
  }

  @Provides @ForUiThread
  Executor provideUiThreadExecutor(final @ForUiThread Handler handler) {
    return new Executor() {
      @Override
      public void execute(Runnable command) {
        handler.post(command);
      }
    };
  }
}
