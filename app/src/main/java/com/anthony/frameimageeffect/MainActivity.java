package com.anthony.frameimageeffect;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.anthony.frameimageeffect.base.ActionEmptyObject;
import com.anthony.frameimageeffect.base.BaseActivity;
import com.anthony.frameimageeffect.base.BaseFragment;
import com.anthony.frameimageeffect.base.BaseObject;
import com.anthony.frameimageeffect.ui.Camera2BasicFragment;
import com.anthony.frameimageeffect.ui.EffectsFilterFragment;
import com.anthony.frameimageeffect.ui.TryClothesFragment;
import com.anthony.frameimageeffect.ui.event.EventHandler;
import com.anthony.frameimageeffect.util.VideoServer;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {


    @Inject
    EventBus eventBus;

    @Inject
    @Named("emptyAction")
    ActionEmptyObject emptyObject;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.frame_container)
    FrameLayout frameContainer;

    private BaseFragment fragment;
    private VideoServer videoServer;
    private List<String> activeTitleList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        try {
            ButterKnife.bind(this);
            toolbar.setContentInsetsAbsolute(0, 0);
            setSupportActionBar(toolbar);
            getActivityComponent().inject(this);

            eventBus.register(this);

            if (null == savedInstanceState) {
                changeFragment(BaseFragment.FragmentEnums.CAMERA_ACTION, true, null);
            }

            //videoServer = new VideoServer(this);
            //frameContainer.addView(videoServer);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void changeFragment(BaseFragment.FragmentEnums tag, boolean addBackStack, Bundle bundle) {
        switch (tag) {
            case EFFECT_IMAGE:
                fragment = EffectsFilterFragment.newInstance(bundle);
                break;
            case CAMERA_ACTION:
                fragment = Camera2BasicFragment.newInstance(bundle);
                break;
            case TRY_CLOTHES:
                fragment = TryClothesFragment.newInstance(bundle);
                break;
        }
        if (fragment != null) {
            switchContent(fragment, addBackStack, tag.getFragmentName());
        }
    }

    @OnClick(R.id.sub_footer_profile)
    public void onClickEffect() {
        if(Camera2BasicFragment.getImageByteArray() != null) {
            Bundle bundle = new Bundle();
            bundle.putByteArray(Constant.DataType.Image, Camera2BasicFragment.getImageByteArray());
            bundle.putInt(Constant.Parameter.Height, Camera2BasicFragment.getImageHeight());
            bundle.putInt(Constant.Parameter.Width, Camera2BasicFragment.getImageWidth());
            changeFragment(BaseFragment.FragmentEnums.EFFECT_IMAGE, true, bundle);
        }
        else {
            changeFragment(BaseFragment.FragmentEnums.EFFECT_IMAGE, true, null);
        }
    }

    @OnClick(R.id.sub_footer_notification)
    public void onClickMain() {
        super.onBackPressed();
    }

    @OnClick(R.id.sub_footer_message)
    public void onClickChangeClothes() {
        if(Camera2BasicFragment.getImageByteArray() != null) {
            Bundle bundle = new Bundle();
            bundle.putByteArray(Constant.DataType.Image, Camera2BasicFragment.getImageByteArray());
            bundle.putInt(Constant.Parameter.Height, Camera2BasicFragment.getImageHeight());
            bundle.putInt(Constant.Parameter.Width, Camera2BasicFragment.getImageWidth());
            changeFragment(BaseFragment.FragmentEnums.TRY_CLOTHES, true, bundle);
        }
        else {
            changeFragment(BaseFragment.FragmentEnums.TRY_CLOTHES, true, null);
        }
    }

    @Subscribe
    public void onEvent(BaseObject event) {
        EventHandler.getInstance()
                .setTarget(this)
                .setObjectEvent(event)
                .excute();
    }

    protected void switchContent(BaseFragment fragment, boolean addBackStack, String name) {
        if (fragment != null) {
            activeTitleList.add(fragment.getFragmentLabel());
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//            transaction.setCustomAnimations(
//                    R.anim.fade_in,
//                    R.anim.fade_out
//            );
            if (addBackStack) {
                transaction.addToBackStack(name);
            }
            transaction.replace(R.id.frame_container, fragment, name);
            transaction.commitAllowingStateLoss();

            setActiveTitle(fragment.getFragmentLabel(), false);
        }
    }

    public void setActiveTitle(String title, boolean shouldShowSubTitle) {
//        if (Utils.isEmpty(title))
//            return;
//        if (tvTitleBar != null) {
//            tvTitleBar.setText(Html.fromHtml(title));
//        }
//        if (tvSubTitle != null) {
//            if (shouldShowSubTitle) {
//                tvSubTitle.setVisibility(View.VISIBLE);
//            } else {
//                tvSubTitle.setVisibility(View.GONE);
//            }
//        }
    }
}
