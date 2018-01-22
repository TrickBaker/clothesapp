package com.anthony.frameimageeffect.util;

import android.content.Context;
import android.graphics.Typeface;

import java.util.Hashtable;

/**
 * Created by mb on 3/22/16
 */
public class FontUtil {
    private static final Hashtable<String, Typeface> fonts = new Hashtable<>();

    public static Typeface getFont(Context context, String font) {
        Typeface typeface = fonts.get(font);
        if (typeface == null) {
            typeface = Typeface.createFromAsset(context.getAssets(), font);
            fonts.put(font, typeface);
        }
        return typeface;
    }
}
