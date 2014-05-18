package com.episode6.hackit.android.baseapp.component;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.episode6.hackit.android.R;
import com.episode6.hackit.android.util.DeviceInfo;

import javax.annotation.Nullable;
import javax.inject.Inject;

public class BaseWebViewFragment extends BaseFragment {

  private static final String ARG_URL = "url";

  @Inject DeviceInfo mDeviceInfo;

  private WebView mWebView;
  private ProgressBar mProgressBar;

  public void setUrl(String url) {
    if (getArguments() == null) {
      setArguments(new Bundle());
    }
    getArguments().putString(ARG_URL, url);
  }

  public String getUrl() {
    return getArguments().getString(ARG_URL);
  }

  @Nullable
  @Override
  public View onCreateContentView(Context context, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    return inflater.inflate(R.layout.hackit_web_fragment, container, false);
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mWebView = getView(R.id.hackit_webview);
    mProgressBar = getView(R.id.hackit_progress);

    setUpWebView();
  }

  private void setUpWebView() {
    WebSettings settings = mWebView.getSettings();
    settings.setJavaScriptEnabled(true);
    mWebView.setWebChromeClient(new WebChromeClient() {
      @Override
      public void onProgressChanged(WebView view, int newProgress) {
        if (mProgressBar.isIndeterminate()) {
          mProgressBar.setIndeterminate(false);
        }
        mProgressBar.setProgress(newProgress);
      }
    });
    mWebView.setWebViewClient(new WebViewClient() {
      @Override
      public void onPageStarted(WebView view, String url, Bitmap favicon) {
        mProgressBar.setVisibility(View.VISIBLE);
        mProgressBar.setIndeterminate(true);
        mProgressBar.setProgress(0);
      }

      @Override
      public void onPageFinished(WebView view, String url) {
        mProgressBar.setVisibility(View.GONE);
      }
    });

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
