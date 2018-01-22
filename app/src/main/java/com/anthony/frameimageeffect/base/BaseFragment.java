package com.anthony.frameimageeffect.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anthony.frameimageeffect.FrameImageApplication;
import com.anthony.frameimageeffect.MainActivity;
import com.anthony.frameimageeffect.di.component.DaggerFragmentComponent;
import com.anthony.frameimageeffect.di.component.FragmentComponent;
import com.anthony.frameimageeffect.di.module.FragmentModule;
import com.anthony.frameimageeffect.ui.Camera2BasicFragment;
import com.anthony.frameimageeffect.ui.EffectsFilterFragment;
import com.anthony.frameimageeffect.ui.TryClothesFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.subscriptions.CompositeSubscription;


/**
 * Created by Sandy on 2/24/16.
 */
public abstract class BaseFragment extends Fragment {
    private FragmentComponent fragmentComponent;
    private BaseActivity activity;
    protected View view;
    protected CompositeSubscription eventSubscription;
    private Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eventSubscription = new CompositeSubscription();
        injectDependency();
        activity = (BaseActivity) getActivity();
        preProcessData(getArguments());

    }

    public void initEvent() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(getLayoutId(), container, false);
        bindView();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        attachPresenter();
        initEvent();
    }

    public void activeEvents(){}
    public void bindView() {
        unbinder = ButterKnife.bind(this, view);
    }

    public void unBindView() {
        unbinder.unbind();
    }

    public abstract int getLayoutId();

    public BaseActivity getBaseActivity() {
        return activity;
    }

    @Override
    public void onResume() {
        super.onResume();
        bindMenu();
//        bindView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (eventSubscription != null) {
            eventSubscription.clear();
        }
        unBindView();
        unBindMenu();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        detachPresenter();
        if (eventSubscription != null) {
            eventSubscription.unsubscribe();
        }
    }

//    protected void showFooter(boolean show) {
//        if (activity instanceof MainActivity) {
//            if (show) {
//                ((MainActivity) activity).showBottomBar();
//            } else {
//                ((MainActivity) activity).hideBottomBar();
//            }
//        }
//    }
//
//    protected void hideMenuToolbar() {
//        if (activity instanceof MainActivity)
//            ((MainActivity) activity).hideToolbarMenu();
//    }

    protected void preProcessData(Bundle data) {
    }

    protected void injectDependency() {
    }

    protected void attachPresenter() {
    }

    protected void detachPresenter() {
    }

    protected void bindMenu() {
        //showFooter(false);
    }

    protected void unBindMenu() {
        //hideMenuToolbar();
        //WidgetUtils.hideSoftKeyboard(getActivity());
    }


    public enum FragmentEnums {
        EFFECT_IMAGE(EffectsFilterFragment.class.getName()),
        CAMERA_ACTION(Camera2BasicFragment.class.getName()),
        TRY_CLOTHES(TryClothesFragment.class.getName());


        private String fragmentName;

        FragmentEnums(String name) {
            this.fragmentName = name;
        }

        public String getFragmentName() {
            return this.fragmentName;
        }
    }

    protected FragmentComponent getFragmentComponent() {
        if (fragmentComponent == null) {
            fragmentComponent = DaggerFragmentComponent.builder()
                    .appComponent(((FrameImageApplication) getActivity().getApplication()).getAppComponent())
                    .fragmentModule(new FragmentModule(this))
                    .build();
        }
        return fragmentComponent;
    }

    public abstract String getFragmentLabel();



    public void showMessage(String message) {
        //DialogUtils.showDialogWithMessage(getActivity(), message);
    }

    public void changeFragment(FragmentEnums tag, boolean addBackstack, Bundle bundle) {
        if (getBaseActivity() instanceof MainActivity) {
            ((MainActivity) getBaseActivity()).changeFragment(tag, addBackstack, bundle);
        }
    }

    public void setTargetTag(Object tag) {
        getBaseActivity().setTag(tag);
    }

    public Object getTargetTag() {
        return getBaseActivity().getTag();
    }
}
