package com.anthony.frameimageeffect.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.anthony.frameimageeffect.R;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by hamin on 11/4/2017.
 */

public class ClothesAdapterFactory extends RecyclerView.Adapter<ClothesAdapterFactory.ClothesHolder> {


    private Context mContext;
    private String itemData[] = {
            "simg_1.jpg",
            "simg_2.jpg",
            "simg_3.jpg",
            "simg_4.jpg",
            "simg_5.jpg"};

    private String item[] = {
            "img_1.png",
            "img_2.png",
            "img_3.png",
            "img_4.png",
            "img_5.png"};

    public ClothesAdapterFactory(Context mContext) {
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

    public String[] getItemData()
    {
        return item;
    }

    @Override
    public void onBindViewHolder(ClothesHolder holder, int position) {
        String val = itemData[position];
        // load image
        try {
            // get input stream
            InputStream ims = mContext.getAssets().open("image/" + val);
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
