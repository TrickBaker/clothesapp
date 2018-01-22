package com.anthony.frameimageeffect.widget.fixedhorizontallistview;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by mb on 3/17/16
 */
public abstract class FixedHorizontalListViewAdapter implements ViewAdapter {

    private FixedHorizontalListView fixedHorizontalListView;

    public View getLastMoreView(ViewGroup parent) {
        return null;
    }

    public interface OnItemClickListener {
        void onItemClick(ViewAdapter adapter, int position, View view, boolean isLastView);
    }

    public interface OnLongItemClickListener {
        void onLongItemClick(ViewAdapter adapter, int position, View view, boolean isLastView);
    }

    protected void setListView(FixedHorizontalListView ls) {
        fixedHorizontalListView = ls;
    }

    public void notifyDataSetChanged() {
        if (fixedHorizontalListView != null) {
            fixedHorizontalListView.notifyDataChanged();
        }
    }

}

