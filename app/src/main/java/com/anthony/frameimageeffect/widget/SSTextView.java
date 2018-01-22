package com.anthony.frameimageeffect.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import com.anthony.frameimageeffect.R;
import com.anthony.frameimageeffect.util.FontUtil;
import com.anthony.frameimageeffect.util.ValidationUtils;


/**
 * Created by Sandy on 2/22/16.
 */
public class SSTextView extends TextView {
    float defaultTextSize;

    public SSTextView(Context context) {
        super(context);
        init(context,null);
    }

    public SSTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SSTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    public void init(Context ctx, AttributeSet attrs) {
        defaultTextSize = getTextSize();
        String font = null;
        if (attrs != null) {
            TypedArray typedArray = ctx.obtainStyledAttributes(attrs, R.styleable.SSTextView);
            font = typedArray.getString(R.styleable.SSTextView_font);
            typedArray.recycle();
        }
        if (ValidationUtils.isEmpty(font) || "null".equals(font)) {
            font = ctx.getString(R.string.font_worksans_light);
        }

        setFont(ctx, font);
    }
    public void setFont(Context ctx, String font) {
        try {
            Typeface typeface = FontUtil.getFont(ctx, font);
            setTypeface(typeface);
        } catch (Exception ex) {
            Log.v(SSTextView.class.getName(), ex.toString());
        }
    }


    public float getDefaultTextSize() {
        return defaultTextSize;
    }
}
