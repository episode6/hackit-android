package com.episode6.hackit.android.extmodule;

import android.accounts.AccountManager;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.view.inputmethod.InputMethodManager;

import com.episode6.hackit.android.annotation.ForApplication;
import com.episode6.hackit.android.app.scope.ApplicationScope;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
    library = true,
    includes = {ApplicationScope.class})
public class AndroidModule {

  @Provides
  SharedPreferences provideDefaultSharedPreferences(@ForApplication Context context) {
    return PreferenceManager.getDefaultSharedPreferences(context);
  }

  @Provides
  @Singleton
  PackageManager providePackageManager(@ForApplication Context context) {
    return context.getPackageManager();
  }

  @Provides
  PackageInfo providePackageInfo(@ForApplication Context context, PackageManager packageManager) {
    try {
      return packageManager.getPackageInfo(context.getPackageName(), 0);
    } catch (PackageManager.NameNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  @Provides
  TelephonyManager provideTelephonyManager(@ForApplication Context context) {
    return getSystemService(context, Context.TELEPHONY_SERVICE);
  }

  @Provides
  InputMethodManager provideInputMethodManager(@ForApplication Context context) {
    return getSystemService(context, Context.INPUT_METHOD_SERVICE);
  }

  @Provides
  ApplicationInfo provideApplicationInfo(@ForApplication Context context) {
    return context.getApplicationInfo();
  }

  @Provides
  AccountManager provideAccountManager(@ForApplication Context context) {
    return AccountManager.get(context);
  }

  @Provides
  ClassLoader provideClassLoader(@ForApplication Context context) {
    return context.getClassLoader();
  }

  @Provides
  NotificationManager provideNotificationManager(@ForApplication Context context) {
    return getSystemService(context, Context.NOTIFICATION_SERVICE);
  }

  @SuppressWarnings("unchecked")
  private static <T> T getSystemService(Context context, String serviceConstant) {
    return (T) context.getSystemService(serviceConstant);
  }
}
