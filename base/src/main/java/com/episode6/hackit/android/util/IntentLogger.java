package com.episode6.hackit.android.util;

import android.content.Intent;
import android.os.Bundle;

public class IntentLogger {

  public static String logIntent(Intent intent) {
    if (intent == null) {
      return "null intent";
    }

    StringBuilder builder = new StringBuilder();
    builder.append("Intent:\nACTION: ")
        .append(nullSafe(intent.getAction()));

    Bundle extras = intent.getExtras();
    builder.append("\nEXTRAS: ");
    if (extras == null) {
      builder.append("null");
    } else {
      for (String key : extras.keySet()) {
        builder.append("\n").append(key).append(": ").append(nullSafe(extras.get(key)));
      }
    }

    return builder.toString();
  }

  private static String nullSafe(Object object) {
    return object == null ? "null" : object.toString();
  }

}
