package com.anthony.frameimageeffect.di.component;


import com.anthony.frameimageeffect.MainActivity;
import com.anthony.frameimageeffect.di.module.ActivityModule;
import com.anthony.frameimageeffect.di.scope.ActivityScope;

import dagger.Component;

/**
 * Created by Sandy on 2/22/16.
 */
@ActivityScope
@Component(dependencies = {AppComponent.class}, modules = {ActivityModule.class})
public interface ActivityComponent {
    void inject(MainActivity mainActivity);

}
