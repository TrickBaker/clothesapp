package com.anthony.frameimageeffect.ui.event;

import android.content.Intent;
import android.os.Bundle;

import com.anthony.frameimageeffect.Constant;
import com.anthony.frameimageeffect.MainActivity;
import com.anthony.frameimageeffect.base.ActionEmptyObject;
import com.anthony.frameimageeffect.base.BaseFragment;
import com.anthony.frameimageeffect.base.BaseObject;

/**
 * Created by Sandy on 3/24/16.
 */
public class EventHandler {

    private static EventHandler eventHandler = null;
    private MainActivity mainActivity;
    private BaseObject event;

    public static EventHandler getInstance() {
        if (eventHandler == null) {
            eventHandler = new EventHandler();
        }
        return eventHandler;
    }

    public EventHandler setObjectEvent(BaseObject event) {
        this.event = event;
        return this;
    }

    public EventHandler setTarget(MainActivity activity) {
        this.mainActivity = activity;
        return this;
    }

    public void excute() {
        Bundle bundle = new Bundle();
        Intent intent;
        switch (event.getObjectType()) {
            case Constant.ObjectType.Effect:
                mainActivity.changeFragment(BaseFragment.FragmentEnums.EFFECT_IMAGE, true, null);
                break;
        }
    }

}
