package com.anthony.frameimageeffect.widget.fixedhorizontallistview;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by mb on 3/17/16
 */
public interface ViewAdapter {
    Object getItem(int position);

    int getCount();

    View getView(int position, ViewGroup parent);

    int getSize();
}
