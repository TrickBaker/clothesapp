package com.anthony.frameimageeffect.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.androidadvance.topsnackbar.TSnackbar;
import com.anthony.frameimageeffect.FrameImageApplication;
import com.anthony.frameimageeffect.R;
import com.anthony.frameimageeffect.dialog.ConfirmDialog;

/**
 * Created by Sandy on 3/9/16.
 */
public class DialogUtils {
    private static TSnackbar tSnackbar = null;
    private static OnClickDialog onNegativeClick;
    private static OnClickDialog onPositiveClick;
    public static void showDialogWithMessage(Activity activity, String message) {
        if (activity == null)
            return;
        MaterialDialog.Builder builder = new MaterialDialog.Builder(activity)
                .content(message)
                .contentColor(Color.BLACK)
                .positiveText(R.string.okCLick)
                .positiveColor(activity.getResources().getColor(R.color.colorPrimary))
                .backgroundColor(Color.WHITE);
        builder.show();
    }

    public static void showDialogWithMessage(Context context, String message) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(context)
                .content(message)
                .contentColor(Color.BLACK)
                .positiveText(R.string.okCLick)
                .positiveColor(context.getResources().getColor(R.color.colorPrimary))
                .backgroundColor(Color.WHITE);
        builder.show();
    }


    public static void showDialogWithMessage(Context context, String message,String textBtn,ConfirmDialog.OnActionConfirm onActionConfirm) {
        checkAndInitEvent();
        MaterialDialog.Builder builder = new MaterialDialog.Builder(context)
                .content(message)
                .contentColor(Color.BLACK)
                .positiveText(textBtn)
                .positiveColor(context.getResources().getColor(R.color.colorPrimary))
                .backgroundColor(Color.WHITE);
        builder.onPositive(onPositiveClick.setOnActionConfirm(onActionConfirm));
        builder.show();
    }

    private static class OnClickDialog implements MaterialDialog.SingleButtonCallback {

        private ConfirmDialog.OnActionConfirm onActionConfirm = null;
        private boolean isNegatived;

        @Override
        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
            if (onActionConfirm != null) {
                if (!isNegatived) {
                    onActionConfirm.onNoClick();
                } else {
                    onActionConfirm.onYesClick();
                }
            }
        }

        public OnClickDialog(boolean isNegatived) {
            super();
            this.isNegatived = isNegatived;
        }

        public OnClickDialog setOnActionConfirm(ConfirmDialog.OnActionConfirm onActionConfirm) {
            this.onActionConfirm = onActionConfirm;
            return this;
        }
    }
    private static void checkAndInitEvent(){
        if(onPositiveClick == null){
            onPositiveClick = new OnClickDialog(true);
        }

        if(onNegativeClick == null){
            onNegativeClick = new OnClickDialog(false);
        }
    }
    public static void showConfirmDialog(Context context, String message, final ConfirmDialog.OnActionConfirm onActionConfirm) {
        checkAndInitEvent();
        MaterialDialog.Builder builder = new MaterialDialog.Builder(context)
                .content(message)
                .contentColor(Color.BLACK)
                .positiveText(R.string.yesClick)
                .onPositive(onPositiveClick.setOnActionConfirm(onActionConfirm))
                .positiveColor(context.getResources().getColor(R.color.colorPrimary))
                .negativeText(R.string.noClick)
                .onNegative(onNegativeClick.setOnActionConfirm(onActionConfirm))
                .negativeColor(context.getResources().getColor(R.color.colorPrimary))
                .backgroundColor(Color.WHITE);
        builder.show();
    }

    public static void showConfirmDialog(Context context, String message, String yesTitle, String noTitle, ConfirmDialog.OnActionConfirm onActionConfirm) {
        checkAndInitEvent();
        MaterialDialog.Builder builder = new MaterialDialog.Builder(context)
                .content(message)
                .positiveText(yesTitle)
                .negativeText(noTitle)
                .contentColor(Color.BLACK)
                .onPositive(onPositiveClick.setOnActionConfirm(onActionConfirm))
                .positiveColor(context.getResources().getColor(R.color.colorPrimary))
                .onNegative(onNegativeClick.setOnActionConfirm(onActionConfirm))
                .negativeColor(context.getResources().getColor(R.color.colorPrimary))
                .backgroundColor(Color.WHITE);
        builder.show();
    }

    public static void showConfirmDialog(Context context, String title, String message, String yesTitle, String noTitle, ConfirmDialog.OnActionConfirm onActionConfirm) {

        checkAndInitEvent();
        MaterialDialog.Builder builder = new MaterialDialog.Builder(context)
                .content(message)
                .positiveText(yesTitle)
                .negativeText(noTitle)
                .contentColor(Color.BLACK)
                .onPositive(onPositiveClick.setOnActionConfirm(onActionConfirm))
                .positiveColor(context.getResources().getColor(R.color.colorPrimary))
                .onNegative(onNegativeClick.setOnActionConfirm(onActionConfirm))
                .negativeColor(context.getResources().getColor(R.color.colorPrimary))
                .backgroundColor(Color.WHITE);
        builder.show();
    }

    public static void showSnackbarMessage(View view, String message) {
        if (tSnackbar != null) {
            tSnackbar.dismiss();
        }
        tSnackbar = TSnackbar.make(view, message, TSnackbar.LENGTH_LONG);
        tSnackbar.getView().setBackgroundColor(Color.parseColor("#007ac2"));
        TextView textView = (TextView) tSnackbar.getView().findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
        textView.setTypeface(FontUtil.getFont(FrameImageApplication.getFrameImageApplication().getAppComponent().context(), FrameImageApplication.getFrameImageApplication().getAppComponent().context().getString(R.string.font_worksans_light)));
        textView.setTextColor(Color.WHITE);
        tSnackbar.show();

    }

    public static void showSnackbarBottomMessage(View view, String message){
        Snackbar.make(view,message,Snackbar.LENGTH_SHORT).show();
    }
}
