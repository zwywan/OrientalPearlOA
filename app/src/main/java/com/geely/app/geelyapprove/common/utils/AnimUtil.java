package com.geely.app.geelyapprove.common.utils;

import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.ScaleAnimation;

/**
 * Created by Oliver on 2016/9/15.
 */
public class AnimUtil {
    public static Animation scaleAnimation(long duration, long delayTime, Interpolator interpolator, boolean isReversed) {

        Animation animation = isReversed ? new ScaleAnimation(1, 0f, 1, 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
                : new ScaleAnimation(0, 1f, 0, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setInterpolator(interpolator);
        animation.setStartOffset(delayTime);
        animation.setDuration(duration);
        return animation;
    }
}
