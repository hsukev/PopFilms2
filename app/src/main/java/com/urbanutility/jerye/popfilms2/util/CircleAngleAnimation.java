package com.urbanutility.jerye.popfilms2.util;

import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * @https://stackoverflow.com/questions/29381474/how-to-draw-a-circle-with-animation-in-android-with-circle-size-based-on-a-value
 */

public class CircleAngleAnimation extends Animation {

    private Circle circle;

    private float oldAngle;
    private float newAngle;

    public CircleAngleAnimation(Circle circle, float newAngle) {
        this.oldAngle = circle.getAngle();
        this.newAngle = newAngle;
        this.circle = circle;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation transformation) {
        float angle = oldAngle + ((newAngle - oldAngle) * interpolatedTime);

        circle.setAngle(angle);
        circle.requestLayout();
    }
}
