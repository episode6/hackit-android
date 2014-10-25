package com.episode6.hackit.android.ui.baseui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.episode6.hackit.android.R;

import butterknife.ButterKnife;

public abstract class BaseListAdapter extends BaseAdapter {

  public interface ItemViewBinder<V> {
    void bind(int position, V item);
  }

  protected abstract ItemViewBinder createItemViewBinder(int position, View view);
  protected abstract View createView(int position, ViewGroup parent);

  @SuppressWarnings("unchecked")
  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    ItemViewBinder itemViewBinder;
    if (convertView == null) {
      convertView = createView(position, parent);
      itemViewBinder = createItemViewBinder(position, convertView);
      ButterKnife.inject(itemViewBinder, convertView);
      convertView.setTag(R.id.view_binder_tag_key, itemViewBinder);
    } else {
      itemViewBinder = (ItemViewBinder) convertView.getTag(R.id.view_binder_tag_key);
    }
    itemViewBinder.bind(position, getItem(position));
    return convertView;
  }
}
