package com.episode6.hackit.android.baseapp.component;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.episode6.hackit.android.util.DeviceInfo;

import javax.annotation.Nullable;
import javax.inject.Inject;

public class BaseWebViewFragment extends BaseFragment {

  @Inject DeviceInfo mDeviceInfo;

  private WebView mWebView;

  @Nullable
  @Override
  public View onCreateContentView(Context context, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    mWebView = new WebView(context);
    WebSettings settings = mWebView.getSettings();
    settings.setJavaScriptEnabled(true);
    mWebView.setWebChromeClient(new WebChromeClient());
    mWebView.setWebViewClient(new WebViewClient());
    return mWebView;
  }

  @Override
  public void onResume() {
    super.onResume();
    if (mDeviceInfo.isHoneycombUp()) {
      mWebView.onResume();
    } else {
      mWebView.resumeTimers();
    }
  }

  @Override
  public void onPause() {
    if (mDeviceInfo.isHoneycombUp()) {
      mWebView.onPause();
    } else {
      mWebView.pauseTimers();
    }
    super.onPause();
  }

  @Override
  public void onDestroyView() {
    mWebView.destroy();
    super.onDestroyView();
  }

  public WebView getWebView() {
    return mWebView;
  }

  public void loadUrl(String url) {
    mWebView.loadUrl(url);
  }

  @Override
  public boolean handleBackPress() {
    if (mWebView.canGoBack()) {
      mWebView.goBack();
      return true;
    }
    return false;
  }
}
