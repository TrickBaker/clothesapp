package com.anthony.frameimageeffect.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.anthony.frameimageeffect.FrameImageApplication;
import com.anthony.frameimageeffect.di.component.ActivityComponent;
import com.anthony.frameimageeffect.di.component.DaggerActivityComponent;
import com.anthony.frameimageeffect.di.module.ActivityModule;


/**
 * Created by Sandy on 2/23/16.
 */
public class BaseActivity extends AppCompatActivity {

    private ActivityComponent activityComponent;
    private Object tag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected ActivityComponent getActivityComponent() {
        if (activityComponent == null) {
            activityComponent = DaggerActivityComponent.builder()
                    .appComponent(((FrameImageApplication) getApplication()).getAppComponent())
                    .activityModule(new ActivityModule(this))
                    .build();
        }
        return activityComponent;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }
    public Object getTag(){
        return tag;
    }
}

