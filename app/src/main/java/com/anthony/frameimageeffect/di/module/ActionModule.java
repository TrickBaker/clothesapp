package com.anthony.frameimageeffect.di.module;


import com.anthony.frameimageeffect.base.ActionEmptyObject;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Harry on 7/9/16.
 */
@Module
public class ActionModule {
    @Provides
    @Singleton
    @Named("emptyAction")
    ActionEmptyObject getActionObject(){
        return new ActionEmptyObject();
    }

}
