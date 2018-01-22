package com.anthony.frameimageeffect;

import android.app.Application;
import android.content.Context;

import com.anthony.frameimageeffect.di.component.AppComponent;
import com.anthony.frameimageeffect.di.component.DaggerAppComponent;
import com.anthony.frameimageeffect.di.module.ActionModule;
import com.anthony.frameimageeffect.di.module.AppModule;

/**
 * Created by Anthony Tricker on 11/14/2016.
 */

public class FrameImageApplication extends Application {
    private static FrameImageApplication frameImageApplication;
    private AppComponent appComponent;

    public static FrameImageApplication getFrameImageApplication() {
        return frameImageApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        frameImageApplication = this;
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .actionModule(new ActionModule())
                .build();
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
