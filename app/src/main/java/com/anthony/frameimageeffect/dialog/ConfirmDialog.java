package com.anthony.frameimageeffect.dialog;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import com.anthony.frameimageeffect.R;
import com.anthony.frameimageeffect.widget.SSButton;
import com.anthony.frameimageeffect.widget.SSTextView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by HarryLe on 4/11/16.
 */
public class ConfirmDialog extends SpringDialog {

    @BindView(R.id.message_text)
    SSTextView messageText;
    @BindView(R.id.btnYes)
    FrameLayout btnYes;
    @BindView(R.id.btnNo)
    FrameLayout btnNo;
    @BindView(R.id.dialogContainer)
    FrameLayout dialogContainer;
    @BindView(R.id.btnSubYes)
    SSButton btnSubYes;
    @BindView(R.id.btnSubNo)
    SSButton btnSubNo;
    @BindView(R.id.title_text)
    SSTextView titleText;

    private String yesTitle;
    private String noTitle;
    private OnActionConfirm onActionConfirm;
    private String message;
    private String title;

    public ConfirmDialog(Context context, String message) {
        super(context);
        this.message = message;
        this.title = "";
    }

    public ConfirmDialog(Context context, String message, String yesTitle, String noTitle) {
        super(context);
        this.message = message;
        this.yesTitle = yesTitle;
        this.noTitle = noTitle;
        this.title = "";
    }

    public ConfirmDialog(Context context, String title,String message, String yesTitle,String noTitle){
        super(context);
        this.message = message;
        this.yesTitle = yesTitle;
        this.noTitle = noTitle;
        this.title = title;
    }
    @Override
    protected int getLayoutContent() {
        return R.layout.dialog_confirm;
    }

    @Override
    protected void doLayout() {
        if(title.equals("")){
            titleText.setVisibility(View.GONE);
        }
        if (yesTitle != null) {
            btnSubYes.setText(yesTitle);
        }
        if (noTitle != null) {
            btnSubNo.setText(noTitle);
        }
        messageText.setText(message);
        titleText.setText(title);
    }

    @OnClick({R.id.btnYes, R.id.btnNo, R.id.dialogContainer})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnYes:
                if (onActionConfirm != null) {
                    onActionConfirm.onYesClick();
                }
                dismissAnimation();
                break;
            case R.id.btnNo:
                if (onActionConfirm != null) {
                    onActionConfirm.onNoClick();
                }
                dismissAnimation();
                break;
        }
    }

    public void setOnActionConfirm(OnActionConfirm onActionConfirm) {
        this.onActionConfirm = onActionConfirm;
    }

    public interface OnActionConfirm {
        void onYesClick();

        void onNoClick();
    }
}
