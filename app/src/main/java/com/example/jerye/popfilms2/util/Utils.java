package com.example.jerye.popfilms2.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by jerye on 8/19/2017.
 */

public class Utils {
    private static int SCREEN_HEIGHT = 0;
    private static int SCREEN_WIDTH = 0;
    private static String youTubeWebUrl = "https://www.youtube.com/watch?v=%s";
    private static String youTubeAppUrl = "vnd.youtube:%s";
    private static String youTubeVideoThumbnail = "http://img.youtube.com/vi/%s/hqdefault.jpg";




    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

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

    public static String buildYouTubeWebUri(String videoKey){
        return String.format(youTubeWebUrl, videoKey);
    }
    public static String buildYouTubeAppUri(String videoKey){
        return String.format(youTubeAppUrl, videoKey);
    }
    public static String buildThumbnailUri(String thumbnailKey) {
        return String.format(youTubeVideoThumbnail, thumbnailKey);
    }
}
