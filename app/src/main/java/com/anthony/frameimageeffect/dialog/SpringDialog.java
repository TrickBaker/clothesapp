package com.anthony.frameimageeffect.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;


import com.anthony.frameimageeffect.R;
import com.anthony.frameimageeffect.util.DialogUtils;
import com.anthony.frameimageeffect.util.ResoucesUtils;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringListener;
import com.facebook.rebound.SpringSystem;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by longzma11 on 3/9/16.
 */
public abstract class SpringDialog extends Dialog {
    protected int TIME_START_ANIMATION = 400;
    protected int TIME_END_ANIMATION = 400;

    protected double TENSION = 800;
    protected double DAMPER = 40;

    private SpringSystem springSystem;
    private Spring mSpring;

    private Handler handler = new Handler();

    @BindView(R.id.dialogContent)
    RelativeLayout dialogContent;

    public RelativeLayout getDialogContent() {
        return dialogContent;
    }

    public SpringDialog(Context context) {
        super(context, R.style.SSDialog_Dialog);

        springSystem = SpringSystem.create();
        mSpring = springSystem.createSpring();

        SpringConfig config = new SpringConfig(TENSION, DAMPER);
        mSpring.setSpringConfig(config);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.dimAmount=0.5f;
        getWindow().setAttributes(lp);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(getLayoutContent());

        ButterKnife.bind(this);
        doLayout();
    }

    protected abstract int getLayoutContent();

    protected abstract void doLayout();
    protected void adjustingLayout(WindowManager.LayoutParams lp){
        float ratio_margin_vertical = 15.0f;
        int sizeToolbar = ResoucesUtils.getSizeToolbar(getContext());
        int sizeMarginHorizontal = sizeToolbar * 2;
        int sizeMarginVertical = (int) (getWindow().getAttributes().width / ratio_margin_vertical);
        ((FrameLayout.LayoutParams) dialogContent.getLayoutParams()).setMargins(sizeMarginVertical, sizeMarginHorizontal, sizeMarginVertical, sizeMarginHorizontal);
    };

    protected void dismissAnimation() {
        final View v = this.findViewById(R.id.dialogContainer);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSpring.setEndValue(-v.getHeight() * 1.1);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dismiss();
                    }
                }, TIME_END_ANIMATION);
            }
        }, 400);

    }
    @Override
    public void show() {
        super.show();

        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(getWindow().getAttributes());
        lp.width = displayMetrics.widthPixels;
        lp.height = displayMetrics.heightPixels;
        getWindow().setAttributes(lp);
        adjustingLayout(lp);
        getWindow().setGravity(Gravity.CENTER);
        mSpring.setEndValue(-dialogContent.getHeight() * 1.1);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialogContent.setVisibility(View.VISIBLE);
                mSpring.setEndValue(dialogContent.getY());
            }
        }, TIME_START_ANIMATION);

        mSpring.addListener(new SpringListener() {
            @Override
            public void onSpringUpdate(Spring spring) {
                float value = (float) spring.getCurrentValue();
                dialogContent.setY(value);
            }

            @Override
            public void onSpringAtRest(Spring spring) {

            }

            @Override
            public void onSpringActivate(Spring spring) {

            }

            @Override
            public void onSpringEndStateChange(Spring spring) {

            }
        });
    }

    public void showMessage(String message) {
        DialogUtils.showDialogWithMessage(getContext(), message);
    }
}