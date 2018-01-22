package com.anthony.frameimageeffect.di.component;

import android.content.Context;

import com.anthony.frameimageeffect.base.ActionEmptyObject;
import com.anthony.frameimageeffect.di.module.ActionModule;
import com.anthony.frameimageeffect.di.module.AppModule;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Sandy on 2/22/16.
 */

@Singleton
@Component(modules = {AppModule.class, ActionModule.class})
public interface AppComponent {

    EventBus eventBus();

    Context context();

    //TODO inject action
    @Named("emptyAction")
    ActionEmptyObject actionObject();
}
