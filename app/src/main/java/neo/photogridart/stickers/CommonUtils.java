package neo.photogridart.stickers;

public class CommonUtils {
    private static final int KEY_PREVENT_TS = -20000;
    private static String TAG = "CommonUtils";
    private static long lastClickTime;
    private static int lastClickViewId;

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
