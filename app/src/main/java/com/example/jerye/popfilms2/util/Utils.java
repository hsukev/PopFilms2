package com.example.jerye.popfilms2.util;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by jerye on 8/19/2017.
 */

public class Utils {
    private static int SCREEN_HEIGHT = 0;
    private static int SCREEN_WIDTH = 0;

    public static int getScreenHeight(Context context) {
        if (SCREEN_HEIGHT == 0) {
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            SCREEN_HEIGHT = size.y;
        }
        return SCREEN_HEIGHT;
    }

    public static int getScreenWidth(Context context) {
        if (SCREEN_WIDTH == 0) {
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            SCREEN_WIDTH = size.y;
        }
        return SCREEN_WIDTH;
    }
}
