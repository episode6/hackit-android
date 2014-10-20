package com.episode6.hackit.android.executor;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

import com.episode6.hackit.android.annotation.ForBackgroundThread;
import com.episode6.hackit.android.annotation.ForUiThread;
import com.episode6.hackit.android.app.scope.ApplicationScope;

import java.util.concurrent.Executor;

import javax.inject.Singleton;

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

  @Provides @Singleton @ForBackgroundThread
  HandlerThread provideWorkerThread() {
    HandlerThread thread = new HandlerThread("worker_thread", Thread.MIN_PRIORITY);
    thread.start();
    return thread;
  }

  @Provides @Singleton @ForBackgroundThread
  Handler providerWorkerHandler(@ForBackgroundThread HandlerThread workerThread) {
    return new Handler(workerThread.getLooper());
  }

  @Provides @Singleton @ForBackgroundThread
  Executor provideBackgroundExecutor(final @ForBackgroundThread Handler handler) {
    return new Executor() {
      @Override
      public void execute(Runnable command) {
        handler.post(command);
      }
    };
  }
}
