package com.anthony.frameimageeffect.widget.fixedhorizontallistview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;


/**
 * Created by mb on 3/17/16
 */
public class FixedHorizontalListView extends LinearLayout implements View.OnClickListener, View.OnLongClickListener {
    private FixedHorizontalListViewAdapter fixedHorizontalListViewAdapter;
    private int width;
    private int shownItemCount;
    private FixedHorizontalListViewAdapter.OnItemClickListener onItemClickListener;
    private FixedHorizontalListViewAdapter.OnLongItemClickListener onLongItemClickListener;
    private boolean hasLastView;

    public FixedHorizontalListView(Context context) {
        super(context);
    }

    public FixedHorizontalListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FixedHorizontalListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initView() {
        if (width == 0) {
            getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    width = getWidth();
                    if (width != 0) {
                        initView();
                    }
                }
            });
        }
        if (fixedHorizontalListViewAdapter == null || width == 0 || fixedHorizontalListViewAdapter.getCount() == 0) {
            return;
        }

        int size = fixedHorizontalListViewAdapter.getSize();
        width = width - getPaddingLeft() - getPaddingRight();
        if (size != 0) {
            shownItemCount = width / size;
            if (shownItemCount != 0 && width % size != 0) {
                size += (width % size) / shownItemCount;
            }
        } else {
            shownItemCount = fixedHorizontalListViewAdapter.getCount();
        }

        View view;
        View lastView = null;

        int count = shownItemCount < fixedHorizontalListViewAdapter.getCount() ? shownItemCount : fixedHorizontalListViewAdapter.getCount();

        for (int i = 0; i < count; i++) {

            if (i == (shownItemCount - 1) && count < fixedHorizontalListViewAdapter.getCount()) {
                lastView = fixedHorizontalListViewAdapter.getLastMoreView(this);
            }

            if (lastView != null) {
                hasLastView = true;
                view = lastView;
            } else {
                view = fixedHorizontalListViewAdapter.getView(i, this);
            }

            if (view != null && !view.isShown()) {
                view.setOnClickListener(this);
                view.setOnLongClickListener(this);
                view.setTag(i);
                if (size != 0) {
                    addView(view, size, size);
                } else {
                    addView(view);
                }
            }
        }
    }

    public void setAdapter(FixedHorizontalListViewAdapter fixedHorizontalListViewAdapter) {
        this.fixedHorizontalListViewAdapter = fixedHorizontalListViewAdapter;
        if (fixedHorizontalListViewAdapter != null) {
            fixedHorizontalListViewAdapter.setListView(this);
        }
        notifyDataChanged();
    }


    public void setOnItemClickListener(FixedHorizontalListViewAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnLongItemClickListener(FixedHorizontalListViewAdapter.OnLongItemClickListener onLongItemClickListener) {
        this.onLongItemClickListener = onLongItemClickListener;
    }

    @Override
    public void onClick(View v) {
        if (onItemClickListener != null) {
            int position = (int) v.getTag();
            onItemClickListener.onItemClick(fixedHorizontalListViewAdapter, position, v, position == (shownItemCount - 1) && hasLastView);
        }
    }

    public int getShownItemCount() {
        return shownItemCount;
    }

    protected void notifyDataChanged() {
        removeAllViews();
        initView();
    }

    @Override
    public boolean onLongClick(View v) {
        if (onLongItemClickListener != null) {
            int position = (int) v.getTag();
            onLongItemClickListener.onLongItemClick(fixedHorizontalListViewAdapter, position, v, position == (shownItemCount - 1) && hasLastView);
            return true;
        }
        return false;
    }
}
