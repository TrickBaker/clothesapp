package com.anthony.frameimageeffect.ui;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.anthony.frameimageeffect.Constant;
import com.anthony.frameimageeffect.R;
import com.anthony.frameimageeffect.adapter.ClothesAdapterFactory;
import com.anthony.frameimageeffect.base.BaseFragment;
import com.anthony.frameimageeffect.util.MoveGestureDetector;
import com.anthony.frameimageeffect.util.RecyclerItemClickListener;
import com.anthony.frameimageeffect.util.Utils;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import butterknife.BindView;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by hamin on 11/4/2017.
 */

public class TryClothesFragment extends BaseFragment {


    @Inject
    EventBus eventBus;


    private float mScaleSpan = 1.0f;
    private float mScaleFactor = .4f;
    private float mFocusX = 0.f;
    private float mFocusY = 0.f;
    private Matrix mMatrix = new Matrix();
    private MoveGestureDetector mMoveDetector;


    @BindView(R.id.rc_clothes)
    RecyclerView recList;

    @BindView(R.id.myImage)
    ImageView mMyImage;

    @BindView(R.id.clothesImage)
    ImageView mClothesImage;

    @BindView(R.id.myLayout)
    RelativeLayout myLayout;


    int mCurrentClothes;
    private int mImageWidth;
    private int mImageHeight;
    private boolean mInitialized = false;
    private volatile boolean saveFrame;
    private Bitmap bitmap;
    byte[] bytes;

    int layoutWidth;
    int layoutHeight;

    private ScaleGestureDetector mScaleDetector;
    private RelativeLayout.LayoutParams layoutParams ;

    public void setCurrentClothes(int clothes) {
        mCurrentClothes = clothes;
    }

    @Override
    protected void injectDependency() {
        super.injectDependency();
        getFragmentComponent().inject(this);
    }

    @Override
    protected void preProcessData(Bundle data) {
        super.preProcessData(data);
        if (getArguments() != null) {
            bytes = getArguments().getByteArray(Constant.DataType.Image);
            mImageWidth = getArguments().getInt(Constant.Parameter.Width);
            mImageHeight = getArguments().getInt(Constant.Parameter.Height);
            bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            bitmap = Bitmap.createScaledBitmap(
                    bitmap, 1096, 1324, false);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMoveDetector = new MoveGestureDetector(getContext(), new MoveListener());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mCurrentClothes = 0;
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        recList.setHasFixedSize(true);
        recList.setLayoutManager(layoutManager);

        ClothesAdapterFactory  clothesAdapter = new ClothesAdapterFactory(getActivity());
        recList.setAdapter(clothesAdapter);


        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        mFocusX = display.getWidth()/2f;
        mFocusY = display.getHeight()/2f;

        recList.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                setCurrentClothes(position);
                mClothesImage.setImageDrawable(Utils.loadImageDrawable(getContext(),clothesAdapter.getItemData(),position));
                mClothesImage.setBackground(null);
                layoutWidth = myLayout.getWidth();
                layoutHeight = myLayout.getHeight();
                mMatrix.postScale(mScaleFactor, mScaleFactor);
                mClothesImage.setOnTouchListener(new View.OnTouchListener() {

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        //LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mClothesImage.getLayoutParams();
                        mMoveDetector.onTouchEvent(event);
                        mMatrix.reset();
                        float scaledImageCenterX = (mClothesImage.getWidth() * mScaleFactor)/2;
                        float scaledImageCenterY = (mClothesImage.getHeight() * mScaleFactor)/2;
                        mMatrix.postTranslate(mFocusX - scaledImageCenterX, mFocusY - scaledImageCenterY);
                        ImageView view = (ImageView) v;
                        view.setImageMatrix(mMatrix);
                        return true;
                    }
                });
            }
        }));

        mMyImage.setImageBitmap(bitmap);
    }

    public static TryClothesFragment newInstance(Bundle bundle) {
        TryClothesFragment fragment = new TryClothesFragment();
        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.try_clothes_layout;
    }

    @Override
    public String getFragmentLabel() {
        return null;
    }



    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleSpan = detector.getCurrentSpan(); // average distance between fingers
            return true;
        }
    }

    private class MoveListener extends MoveGestureDetector.SimpleOnMoveGestureListener {
        @Override
        public boolean onMove(MoveGestureDetector detector) {
            PointF d = detector.getFocusDelta();
            mFocusX += d.x;
            mFocusY += d.y;

            // mFocusX = detector.getFocusX();
            // mFocusY = detector.getFocusY();
            return true;
        }
    }

}
