package com.anthony.frameimageeffect.dialog;

import android.content.Context;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;


import com.anthony.frameimageeffect.R;
import com.anthony.frameimageeffect.util.ResoucesUtils;
import com.anthony.frameimageeffect.widget.SSTextView;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by Jerry  on 02/03/2016.
 */
public class MenuClickDialog extends SpringDialog {

    @BindView(R.id.layout_background)
    LinearLayout frameLayout;

    @BindView(R.id.dialog_badges_detail_iv_avatar)
    CircleImageView imAvatar;

    @BindView(R.id.dialog_badges_detail_tv_des)
    SSTextView tvDes;

    @BindView(R.id.dialog_badges_detail_tv_obtained)
    SSTextView tvObtained;

    @BindView(R.id.dialog_badges_detail_tv_title)
    SSTextView tvTitle;

    //Badges badgesDetail;

    public MenuClickDialog(Context context) {
        super(context);
        //this.badgesDetail = badges;
    }


    @Override
    protected int getLayoutContent() {
        return R.layout.dialog_badges_detail;
    }

    @Override
    protected void doLayout() {
//        if (!Utils.isEmpty(badgesDetail.getTitle())) {
//            tvTitle.setText(badgesDetail.getTitle());
//        }
//        if (!Utils.isEmpty(badgesDetail.getDes())) {
//            tvDes.setText(badgesDetail.getDes());
//        }
//        if (!Utils.isEmpty(badgesDetail.getCreatedAt())) {
//            tvObtained.setText(badgesDetail.getCreatedAt());
//        }
//        PiccasoUtils.getInstance(imAvatar.getContext())
//                .loadImage(badgesDetail.getIcon(), imAvatar);
    }

    @Override
    protected void adjustingLayout(WindowManager.LayoutParams lp) {

        //TODO adjusting layout
        float ratio_margin_vertical = 15.0f;
        int sizeToolbar = ResoucesUtils.getSizeToolbar(getContext());
        int sizeMarginHorizontal = sizeToolbar * 2;

        int sizeMarginVertical = (int) (getWindow().getAttributes().width / ratio_margin_vertical);
        ((FrameLayout.LayoutParams) dialogContent.getLayoutParams()).setMargins(sizeMarginVertical, sizeMarginHorizontal, sizeMarginVertical, sizeMarginHorizontal);


//        int hContent = lp.height - (sizeMarginHorizontal * 2);
//        int hRecyclerView = hContent - sizeToolbar;
//        RelativeLayout.LayoutParams lpRecyclerView = (RelativeLayout.LayoutParams) frameLayout.getLayoutParams();
//        lpRecyclerView.height = hRecyclerView;
//        frameLayout.setLayoutParams(lpRecyclerView);
    }

    @OnClick({R.id.dialog_badges_detail_tv_exit,R.id.dialogContainer})
    void OnClickExit() {
        dismissAnimation();
    }

}
