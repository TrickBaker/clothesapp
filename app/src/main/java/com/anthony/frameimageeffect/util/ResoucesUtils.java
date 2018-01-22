package com.anthony.frameimageeffect.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;

/**
 * Created by longzma11 on 3/5/16.
 */
public class ResoucesUtils {

    public static String getStringResource(Context context, int id) {
        return context.getResources().getString(id);
    }

    public static float getDimenResource(Context context, int dimen) {
        return context.getResources().getDimension(dimen);
    }

    public static int getColorResource(Context context, int color){
        return context.getResources().getColor(color);
    }

    public static Drawable getDrawableResource(Context context, int drawable){
        return context.getResources().getDrawable(drawable);
    }

    public static int getSizeToolbar(Context context){
        // Calculate ActionBar height
        TypedValue tv = new TypedValue();
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))
        {
           return TypedValue.complexToDimensionPixelSize(tv.data,context.getResources().getDisplayMetrics());
        }
        return 0;
    }
}
