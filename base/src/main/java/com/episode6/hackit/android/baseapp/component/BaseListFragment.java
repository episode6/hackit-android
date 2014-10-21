package com.episode6.hackit.android.baseapp.component;

import android.content.Context;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.episode6.hackit.android.R;

import javax.annotation.Nullable;

public class BaseListFragment extends BaseFragment {

  private ListView mListView;
  private View mLoadingView;
  private View mEmptyView;
  private TextView mEmptyText;

  private AdapterObserver mAdapterObserver;

  private boolean mFinishedLoading = false;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mAdapterObserver = new AdapterObserver();
  }

  /**
   * If you override this method you should probably also over ride all the findXXView() methods
   */
  @Override
  public @Nullable View onCreateContentView(
      Context context,
      LayoutInflater inflater,
      ViewGroup container,
      Bundle savedInstanceState) {
    FrameLayout rootView =
        (FrameLayout) inflater.inflate(R.layout.hackit_list_fragment, container, false);
    ListView listView = onCreateListView(context, inflater, rootView, savedInstanceState);
    listView.setId(R.id.list);
    rootView.addView(listView, 0);
    return rootView;
  }

  /**
   * Override just this method to use a custom ListView class without needing your own xml
   * NOTE: it's id will be set to @id/list
   */
  protected ListView onCreateListView(
      Context context,
      LayoutInflater inflater,
      ViewGroup container,
      Bundle savedInstanceState) {
    ListView listView = new ListView(context);
    listView.setLayoutParams(container.generateLayoutParams(null));
    return listView;
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mListView = findListView();
    mLoadingView = findLoadingView();
    mEmptyView = findEmptyView();
    mEmptyText = findEmptyTextView();
    setLoading();
  }

  @Override
  public void onDestroyView() {
    ListAdapter adapter = getListAdapter();
    if (adapter != null) {
      adapter.unregisterDataSetObserver(mAdapterObserver);
    }
    super.onDestroyView();
  }

  public ListView getListView() {
    return mListView;
  }

  public ListAdapter getListAdapter() {
    return mListView.getAdapter();
  }

  public void setListAdapter(ListAdapter adapter) {
    ListAdapter oldAdapter = getListAdapter();
    if (adapter == oldAdapter) {
      return;
    }

    if (oldAdapter != null) {
      oldAdapter.unregisterDataSetObserver(mAdapterObserver);
    }

    adapter.registerDataSetObserver(mAdapterObserver);
    mListView.setAdapter(adapter);
    updateListVisibility();
  }

  public void setOnListItemClickListener(AdapterView.OnItemClickListener listener) {
    mListView.setOnItemClickListener(listener);
  }

  public boolean isListEmpty() {
    ListAdapter adapter = mListView.getAdapter();
    return adapter == null || adapter.getCount() == 0;
  }

  public void setFinishedLoading(String reason) {
    mEmptyText.setText(reason);
    mFinishedLoading = true;
    updateListVisibility();
  }

  public boolean isFinishedLoading() {
    return mFinishedLoading;
  }

  protected ListView findListView() {
    return getView(R.id.list);
  }

  protected View findLoadingView() {
    return getView(R.id.loading);
  }

  protected View findEmptyView() {
    return getView(R.id.empty);
  }

  protected TextView findEmptyTextView() {
    return getView(R.id.empty_text);
  }

  protected void onListDataChanged() {
    updateListVisibility();
  }

  private void updateListVisibility() {
    if (!isListEmpty()) {
      setListVisible();
      return;
    }

    if (mFinishedLoading) {
      setEmpty();
      return;
    }

    setLoading();
  }

  private void setListVisible() {
    mListView.setVisibility(View.VISIBLE);
    mLoadingView.setVisibility(View.GONE);
    mEmptyView.setVisibility(View.GONE);
  }

  private void setLoading() {
    mListView.setVisibility(View.GONE);
    mLoadingView.setVisibility(View.VISIBLE);
    mEmptyView.setVisibility(View.GONE);
  }

  private void setEmpty() {
    mListView.setVisibility(View.GONE);
    mLoadingView.setVisibility(View.GONE);
    mEmptyView.setVisibility(View.VISIBLE);
  }

  private class AdapterObserver extends DataSetObserver {

    @Override
    public void onChanged() {
      onListDataChanged();
    }

    @Override
    public void onInvalidated() {
      onListDataChanged();
    }
  }
}
