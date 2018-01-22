package com.anthony.frameimageeffect.di.module;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.anthony.frameimageeffect.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Sandy on 2/22/16.
 */
@Module
public class FragmentModule {
    final Fragment fragment;

    public FragmentModule(Fragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @FragmentScope
    Fragment fragment() {
        return this.fragment;
    }

    @Provides
    @FragmentScope
    Activity activity() {
        return this.fragment.getActivity();
    }

    @Provides
    public FragmentManager provideFragmentManager() {
        return fragment.getFragmentManager();
    }

}
