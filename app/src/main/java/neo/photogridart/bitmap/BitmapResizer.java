package neo.photogridart.bitmap;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.graphics.Point;
import android.media.ExifInterface;
import android.os.Build.VERSION;
import android.os.Debug;
import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class BitmapResizer {
    public static Bitmap decodeFile(File f, int requiredSize) {
        try {
            Options o = new Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);
            int REQUIRED_SIZE = requiredSize;
            int width_tmp = o.outWidth;
            int height_tmp = o.outHeight;
            int scale = 1;
            while (width_tmp / 2 >= REQUIRED_SIZE && height_tmp / 2 >= REQUIRED_SIZE) {
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }
            Options o2 = new Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    public static int maxSizeForDimension(Context context) {
        return (int) Math.sqrt(((double) getFreeMemory(context)) / 80.0d);
    }

    public static long getFreeMemory(Context context) {
        return ((long) (((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass() * 1048576)) - Debug.getNativeHeapAllocatedSize();
    }

    public static Bitmap decodeX(String selectedImagePath, int requiredSize, int[] scaler, int[] rotation) {
        String o1 = "";
        try {
            o1 = new ExifInterface(selectedImagePath).getAttribute("Orientation");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (o1 == null) {
            o1 = "";
        }
        File f = new File(selectedImagePath);
        if (o1.contentEquals("6")) {
            rotation[0] = 90;
            Bitmap localBitmap = decodeFile(f, requiredSize, scaler);
            Matrix localMatrix = new Matrix();
            localMatrix.postRotate(90.0f);
            Bitmap result = Bitmap.createBitmap(localBitmap, 0, 0, localBitmap.getWidth(), localBitmap.getHeight(), localMatrix, false);
            localBitmap.recycle();
            return result;
        } else if (o1.contentEquals("8")) {
            rotation[0] = 270;
            Bitmap localBitmap2 = decodeFile(f, requiredSize, scaler);
            Matrix localMatrix2 = new Matrix();
            localMatrix2.postRotate(270.0f);
            Bitmap result2 = Bitmap.createBitmap(localBitmap2, 0, 0, localBitmap2.getWidth(), localBitmap2.getHeight(), localMatrix2, false);
            localBitmap2.recycle();
            return result2;
        } else if (!o1.contentEquals("3")) {
            return decodeFile(f, requiredSize, scaler);
        } else {
            rotation[0] = 180;
            Bitmap localBitmap3 = decodeFile(f, requiredSize, scaler);
            Matrix localMatrix3 = new Matrix();
            localMatrix3.postRotate(180.0f);
            Bitmap result3 = Bitmap.createBitmap(localBitmap3, 0, 0, localBitmap3.getWidth(), localBitmap3.getHeight(), localMatrix3, false);
            localBitmap3.recycle();
            return result3;
        }
    }

    public static Bitmap decodeFile(File f, int requiredSize, int[] scaler) {
        try {
            Options o = new Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);
            int REQUIRED_SIZE = requiredSize;
            int scale = 1;
            int max = Math.max(o.outWidth, o.outHeight);
            while (max / 2 >= REQUIRED_SIZE && max / 2 >= REQUIRED_SIZE) {
                max /= 2;
                scale *= 2;
            }
            Options o2 = new Options();
            o2.inSampleSize = scale;
            scaler[0] = scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    public static Point decodeFileSize(File f, int requiredSize) {
        try {
            Options o = new Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);
            int REQUIRED_SIZE = requiredSize;
            int width_tmp = o.outWidth;
            int height_tmp = o.outHeight;
            int scale = 1;
            while (Math.max(width_tmp, height_tmp) / 2 > REQUIRED_SIZE) {
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }
            if (scale == 1) {
                return new Point(-1, -1);
            }
            return new Point(width_tmp, height_tmp);
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    public static Point getFileSize(File f, int requiredSize) {
        try {
            Options o = new Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);
            return new Point(o.outWidth, o.outHeight);
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    public static Bitmap decodeBitmapFromFile(String selectedImagePath, int MAX_SIZE) {
        try {
            int orientation = new ExifInterface(selectedImagePath).getAttributeInt("Orientation", 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap localBitmap = decodeFile(selectedImagePath, MAX_SIZE);
        if (localBitmap == null) {
            return null;
        }
        Bitmap graySourceBtm = localBitmap;
        if (graySourceBtm == null || VERSION.SDK_INT >= 13) {
            return graySourceBtm;
        }
        Bitmap temp = graySourceBtm.copy(Config.ARGB_8888, true);
        if (graySourceBtm == temp) {
            return temp;
        }
        graySourceBtm.recycle();
        return temp;
    }

    public static Bitmap rotateBitmap(Bitmap r9, int r10) {
        throw new UnsupportedOperationException("Method not decompiled: BitmapResizer.rotateBitmap(android.graphics.Bitmap, int):android.graphics.Bitmap");
    }

    private static Bitmap decodeFile(String selectedImagePath, int MAX_SIZE) {
        Options o = new Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(selectedImagePath, o);
        int scale = 1;
        int width_tmp = o.outWidth;
        int height_tmp = o.outHeight;
        while (Math.max(width_tmp, height_tmp) / 2 > MAX_SIZE) {
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }
        Options o2 = new Options();
        o2.inSampleSize = scale;
        Bitmap b = BitmapFactory.decodeFile(selectedImagePath, o2);
        if (b != null) {
            Log.e("decoded file height", String.valueOf(b.getHeight()));
            Log.e("decoded file width", String.valueOf(b.getWidth()));
        }
        return b;
    }
}
