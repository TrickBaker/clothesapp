package com.anthony.frameimageeffect.widget.spinner_loading;

import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * @author longzma11
 */
public class CallbackAnimation extends Animation {
    public interface TransformationListener {
        void onApplyTrans(float interpolatedTime);
    }

    private TransformationListener mListener;

    public CallbackAnimation(TransformationListener listener) {
        mListener = listener;
        if (listener == null) {
            mListener = listener;
        }
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        mListener.onApplyTrans(interpolatedTime);
    }

    public void setListener(TransformationListener listener) {
        if (listener == null) {
            return;
        }
        mListener = listener;
    }
}
