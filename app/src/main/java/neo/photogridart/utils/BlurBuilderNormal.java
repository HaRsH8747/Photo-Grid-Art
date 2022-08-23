package neo.photogridart.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Build.VERSION;

public class BlurBuilderNormal {
    private static final float BITMAP_SCALE = 0.4f;
    public static final int BLUR_RADIUS_DEFAULT = 14;
    public static final int BLUR_RADIUS_MAX = 25;
    public static final int BLUR_RADIUS_SENTINEL = -1;
    private static final String TAG = BlurBuilderNormal.class.getName();
    Bitmap inputBitmap;
    int lastBlurRadius = -1;
    Matrix matrixBlur = new Matrix();
    Bitmap outputBitmap;
    Paint paintBlur = new Paint(2);

    public static Bitmap createCroppedBitmap(Bitmap bitmap, int i, int j, int k, int l, boolean flag) {
        Bitmap bitmap1 = Bitmap.createBitmap(k, l, Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap1);
        Paint paint = new Paint();
        paint.setFilterBitmap(flag);
        canvas.drawBitmap(bitmap, (float) (-i), (float) (-j), paint);
        return bitmap1;
    }

    public static Bitmap createScaledBitmap(Bitmap bitmap, int i, int j, boolean flag) {
        Matrix matrix = new Matrix();
        matrix.setScale(((float) i) / ((float) bitmap.getWidth()), ((float) j) / ((float) bitmap.getHeight()));
        Bitmap bitmap1 = Bitmap.createBitmap(i, j, Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap1);
        Paint paint = new Paint();
        paint.setFilterBitmap(flag);
        canvas.drawBitmap(bitmap, matrix, paint);
        return bitmap1;
    }

    public Bitmap createBlurBitmapNDK(Bitmap bitmap, int i) {
        int j = i;
        if (i <= 2) {
            j = 2;
        }
        if (this.lastBlurRadius == j && this.outputBitmap != null) {
            return this.outputBitmap;
        }
        if (this.inputBitmap == null) {
            int k = Math.round(((float) bitmap.getWidth()) * BITMAP_SCALE);
            int l = Math.round(((float) bitmap.getHeight()) * BITMAP_SCALE);
            int i2 = k;
            if (k % 2 == 1) {
                i2 = k + 1;
            }
            int k2 = l;
            if (l % 2 == 1) {
                k2 = l + 1;
            }
            if (VERSION.SDK_INT < 12) {
                Options options = new Options();
                options.inDither = true;
                options.inScaled = false;
                options.inPreferredConfig = Config.ARGB_8888;
                options.inPurgeable = true;
                this.inputBitmap = createScaledBitmap(bitmap, i2, k2, false);
            } else {
                this.inputBitmap = Bitmap.createScaledBitmap(bitmap, i2, k2, false);
            }
        }
        if (this.outputBitmap == null) {
            this.outputBitmap = this.inputBitmap.copy(Config.ARGB_8888, true);
        } else {
            new Canvas(this.outputBitmap).drawBitmap(this.inputBitmap, 0.0f, 0.0f, this.paintBlur);
        }
        this.lastBlurRadius = j;
        return this.outputBitmap;
    }

    public void destroy() {
        this.outputBitmap.recycle();
        this.outputBitmap = null;
        this.inputBitmap.recycle();
        this.inputBitmap = null;
    }

    public int getBlur() {
        return this.lastBlurRadius;
    }
}
