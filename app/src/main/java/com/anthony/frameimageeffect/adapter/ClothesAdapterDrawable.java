package com.anthony.frameimageeffect.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.anthony.frameimageeffect.R;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by hamin on 11/4/2017.
 */

public class ClothesAdapterDrawable extends RecyclerView.Adapter<ClothesAdapterDrawable.ClothesHolder> {

    private Context mContext;
    private String itemData[] = {
            "simg_1",
            "simg_2",
            "simg_3",
            "simg_4",
            "simg_5"};

    public ClothesAdapterDrawable(Context mContext) {
        super();
        this.mContext = mContext;
    }

    @Override
    public ClothesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.imf_filter_item, parent, false);
        ClothesHolder viewHolder = new ClothesHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ClothesHolder holder, int position) {
        String val = itemData[position];
        // load image
        try {
            // get input stream
            InputStream ims = mContext.getAssets().open(val);
            // load image as Drawable
            Drawable d = Drawable.createFromStream(ims, null);
            // set image to ImageView
            holder.imFilter.setImageDrawable(d);
        }
        catch(IOException ex) {
            return;
        }
    }

    @Override
    public int getItemCount() {
        return itemData.length;
    }


    public class ClothesHolder extends RecyclerView.ViewHolder {
        public ImageView imFilter;

        public ClothesHolder(View itemView) {
            super(itemView);
            imFilter = (ImageView) itemView.findViewById(R.id.effectsviewimage_item);
        }
    }
}
