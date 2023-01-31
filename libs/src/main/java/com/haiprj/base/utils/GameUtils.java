package com.haiprj.base.utils;

import android.content.Context;

public class GameUtils {

    public static float dpFromPx(final Context context, final float px) {
        return px / context.getResources().getDisplayMetrics().density;
    }

    public static float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

    public static int getDp(Context context,int i) {
        return (int) dpFromPx(context, i);
    }
}
