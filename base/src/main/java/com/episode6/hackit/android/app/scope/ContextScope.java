package com.episode6.hackit.android.app.scope;

import android.accounts.AccountManager;
import android.app.Activity;
import android.app.Application;
import android.app.KeyguardManager;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.os.PowerManager;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.episode6.hackit.android.annotation.ForApplication;
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

  @Provides @Singleton
  AssetManager provideAssetManager(Context context) {
    return context.getAssets();
  }

  @Provides @Singleton
  WindowManager provideWindowManager(Context context) {
    return getSystemService(context, Context.WINDOW_SERVICE);
  }

  @Provides @Singleton
  AudioManager provideAudioManager(Context context) {
    return getSystemService(context, Context.AUDIO_SERVICE);
  }

  @Provides @Singleton
  TelephonyManager provideTelephonyManager(Context context) {
    return getSystemService(context, Context.TELEPHONY_SERVICE);
  }

  @Provides @Singleton
  InputMethodManager provideInputMethodManager(Context context) {
    return getSystemService(context, Context.INPUT_METHOD_SERVICE);
  }

  @Provides @Singleton
  NotificationManager provideNotificationManager(Context context) {
    return getSystemService(context, Context.NOTIFICATION_SERVICE);
  }

  @Provides @Singleton
  AccountManager provideAccountManager(Context context) {
    return getSystemService(context, Context.ACCOUNT_SERVICE);
  }

  @Provides @Singleton
  PowerManager providePowerManager(Context context) {
    return getSystemService(context, Context.POWER_SERVICE);
  }

  @Provides @Singleton
  KeyguardManager provideKeyguardManager(Context context) {
    return getSystemService(context, Context.KEYGUARD_SERVICE);
  }

  @Provides @Singleton
  ClassLoader provideClassLoader(Context context) {
    return context.getClassLoader();
  }

  @SuppressWarnings("unchecked")
  private static <T> T getSystemService(Context context, String serviceConstant) {
    return (T) context.getSystemService(serviceConstant);
  }
}
