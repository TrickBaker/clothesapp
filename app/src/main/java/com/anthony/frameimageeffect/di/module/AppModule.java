package com.anthony.frameimageeffect.di.module;

import android.content.Context;

import com.anthony.frameimageeffect.FrameImageApplication;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Sandy on 2/22/16.
 */
@Module
public class AppModule {
    private final FrameImageApplication app;

    public AppModule(FrameImageApplication application) {
        app = application;
    }

    @Provides
    Context provideContext() {
        return app;
    }

    @Provides
    @Singleton
    public EventBus eventBus() {
        return new EventBus();
    }
}
