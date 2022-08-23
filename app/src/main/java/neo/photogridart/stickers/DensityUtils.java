package neo.photogridart.stickers;

import android.content.Context;
import android.content.res.Resources;
import android.view.WindowManager;

public class DensityUtils {
    public static int dip2px(Context context, float dpValue) {
        return (int) ((dpValue * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        return (int) ((pxValue / context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int getScreenWidth(Context context) {
        return ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getWidth();
    }

    public static int getScreenHeight(Context context) {
        return ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getHeight();
    }

    public static int getStatusBarHeight(Context context) {
        int sbar = 38;
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            return context.getResources().getDimensionPixelSize(Integer.parseInt(c.getField("status_bar_height").get(c.newInstance()).toString()));
        } catch (Exception e1) {
            e1.printStackTrace();
            return sbar;
        }
    }

    public static int getNavigationBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    public static long calculateLength(CharSequence c) {
        double d;
        double len = 0.0d;
        for (int i = 0; i < c.length(); i++) {
            int tmp = c.charAt(i);
            if (tmp <= 0 || tmp >= 127) {
                d = 1.0d;
            } else {
                d = 0.5d;
            }
            len += d;
        }
        return Math.round(len);
    }
}
