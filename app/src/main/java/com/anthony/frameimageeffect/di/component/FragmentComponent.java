package com.anthony.frameimageeffect.di.component;

import com.anthony.frameimageeffect.di.module.FragmentModule;
import com.anthony.frameimageeffect.di.scope.FragmentScope;
import com.anthony.frameimageeffect.ui.Camera2BasicFragment;
import com.anthony.frameimageeffect.ui.EffectsFilterFragment;
import com.anthony.frameimageeffect.ui.TryClothesFragment;

import dagger.Component;

/**
 * Created by Sandy on 2/22/16.
 */
@FragmentScope
@Component(dependencies = {AppComponent.class}, modules = {FragmentModule.class})
public interface FragmentComponent {

    void inject(EffectsFilterFragment effectsFilterFragment);
    void inject(Camera2BasicFragment camera2BasicFragment);
    void inject(TryClothesFragment tryClothesFragment);
}
