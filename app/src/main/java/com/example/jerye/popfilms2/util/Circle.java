package com.example.jerye.popfilms2.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by jerye on 8/23/2017.
 */

/**
 *  @https://stackoverflow.com/questions/29381474/how-to-draw-a-circle-with-animation-in-android-with-circle-size-based-on-a-value
 */

public class Circle extends View {

    private static final int START_ANGLE_POINT = -90;

    private final Paint paint;
    private final RectF rect;

    private float angle;

    public Circle(Context context, AttributeSet attrs) {
        super(context, attrs);

        final int strokeWidth = 40;

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);
        //Circle color
        paint.setColor(Color.RED);

        //size 200x200 example
        rect = new RectF(strokeWidth, strokeWidth, 300  + strokeWidth, 300 + strokeWidth);

        //Initial Angle (optional, it can be zero)
        angle = 0;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(rect, START_ANGLE_POINT, angle, false, paint);
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }
}
