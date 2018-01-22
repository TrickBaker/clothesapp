package com.anthony.frameimageeffect.di.module;

import android.app.Activity;

import com.anthony.frameimageeffect.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Sandy on 2/22/16.
 */
@Module
public class ActivityModule {
    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    Activity activity() {
        return activity;
    }

}
