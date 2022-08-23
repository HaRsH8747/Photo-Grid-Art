package neo.photogridart.imagesavelib;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.media.ExifInterface;
import android.os.Build.VERSION;
import android.os.Debug;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;
import neo.photogridart.R;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class ImageUtility {
    static int SPLASH_TIME_OUT_AFTER_AD_LOADED = 0;
    public static int SPLASH_TIME_OUT_DEFAULT = 0;
    public static int SPLASH_TIME_OUT_LONG = 0;
    static int SPLASH_TIME_OUT_MAX = 0;
    public static int SPLASH_TIME_OUT_SHORT = 0;
    private static final String TAG = "SaveImage Utility";
    static final long interStitialPeriodMin = 6000;
    public static long lastTimeInterstitialShowed = 0;
    public static final int sizeDivider = 100000;

    class C05871 implements Runnable {
        private final String val$finalDirectory;
        private final Context val$mContext;

        C05871(Context context, String str) {
            this.val$mContext = context;
            this.val$finalDirectory = str;
        }

        public void run() {
            Toast msg = Toast.makeText(this.val$mContext, new StringBuilder(String.valueOf(this.val$mContext.getString(R.string.save_image_directory_error_message))).append(this.val$finalDirectory).append(".").toString(), Toast.LENGTH_SHORT);
            msg.setGravity(17, msg.getXOffset() / 2, msg.getYOffset() / 2);
            msg.show();
        }
    }

    static {
        SPLASH_TIME_OUT_LONG = 0;
        SPLASH_TIME_OUT_MAX = 0;
        SPLASH_TIME_OUT_SHORT = 0;
        SPLASH_TIME_OUT_SHORT = 150;
        SPLASH_TIME_OUT_LONG = 800;
        SPLASH_TIME_OUT_MAX = 1300;
    }

    public static boolean getAmazonMarket(Context context) {
        int AMAZON_MARKET = 0;
        try {
            AMAZON_MARKET = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData.getInt("amazon_market");
            if (AMAZON_MARKET < 0) {
                AMAZON_MARKET = 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (AMAZON_MARKET == 1) {
            return true;
        }
        return false;
    }

    public static String getPrefferredDirectoryPathEx(Context mContext) {
        String directory = new StringBuilder(String.valueOf(Environment.getExternalStorageDirectory().getAbsolutePath())).append(mContext.getResources().getString(R.string.foldername)).toString();
        String prefDir = PreferenceManager.getDefaultSharedPreferences(mContext).getString("save_image_directory_custom", null);
        if (prefDir != null) {
            return new StringBuilder(String.valueOf(prefDir)).append(File.separator).toString();
        }
        return directory;
    }

    public static String getPrefferredDirectoryPath(Context mContext, boolean showErrorMessage, boolean getPrefDirectoryNoMatterWhat, boolean isAppCamera) {
        String directory;
        if (isAppCamera) {
            directory = new StringBuilder(String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath())).append(File.separator).append(mContext.getResources().getString(R.string.foldername)).toString();
        } else {
            directory = new StringBuilder(String.valueOf(Environment.getExternalStorageDirectory().getAbsolutePath())).append(mContext.getResources().getString(R.string.foldername)).toString();
        }
        String prefDir = PreferenceManager.getDefaultSharedPreferences(mContext).getString("save_image_directory_custom", null);
        if (prefDir != null) {
            String prefDir2 = new StringBuilder(String.valueOf(prefDir)).append(File.separator).toString();
            if (getPrefDirectoryNoMatterWhat) {
                return prefDir2;
            }
            File dirFile = new File(prefDir2);
            String str = directory;
            if (dirFile.canRead() && dirFile.canWrite() && checkIfEACCES(prefDir2)) {
                directory = prefDir2;
            } else if (showErrorMessage) {
            }
            Log.e(TAG, "prefDir " + prefDir2);
        }
        Log.e(TAG, "getPrefferredDirectoryPathEx " + getPrefferredDirectoryPathEx(mContext));
        Log.e(TAG, "getPrefferredDirectoryPath " + directory);
        return directory;
    }

    public static boolean checkIfEACCES(String dir) {
        Writer writer = null;
        boolean result = false;
        try {
            File f = new File(dir);
            String localPath = new StringBuilder(String.valueOf(dir)).append("mpp").toString();
            f.mkdirs();
            Writer bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(localPath)));
            try {
                bufferedWriter.write("Something");
                result = true;
                bufferedWriter.close();
                String str = TAG;
                StringBuilder append = new StringBuilder().append("f.delete() = ");
                File file = new File(localPath);
                Log.e(str, append.append(file.delete()).toString());
                try {
                    bufferedWriter.close();
                    Writer writer2 = bufferedWriter;
                } catch (Exception e) {
                    Writer writer3 = bufferedWriter;
                }
            } catch (IOException e2) {
                IOException ex = e2;
                writer = bufferedWriter;
                Log.e(TAG, ex.toString());
                try {
                    writer.close();
                } catch (Exception e3) {
                }
                return result;
            } catch (Throwable th2) {
                Throwable th = th2;
                try {
                    writer.close();
                } catch (Exception e4) {
                }
            }
            return result;
        } catch (IOException e5) {
            Log.e(TAG, e5.toString());
            return result;
        }
    }

    private static long getFreeMemory() {
        return Runtime.getRuntime().maxMemory() - Debug.getNativeHeapAllocatedSize();
    }

    public static int maxSizeForDimension() {
        int maxSize = (int) Math.sqrt(((double) getFreeMemory()) / 30.0d);
        Log.e(TAG, "maxSize " + maxSize);
        return Math.min(maxSize, 1624);
    }

    public static int calculateInSampleSize(Options options, int reqWidth, int reqHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        if (height <= reqHeight && width <= reqWidth) {
            return 1;
        }
        if (width < height) {
            return (int) Math.ceil((double) (((float) height) / ((float) reqHeight)));
        }
        return (int) Math.ceil((double) (((float) width) / ((float) reqWidth)));
    }

    public static boolean shouldShowAds(Context mContext) {
        return !mContext.getPackageName().toLowerCase().contains("pro");
    }

    public static double getLeftSizeOfMemory() {
        double totalSize = Double.valueOf((double) Runtime.getRuntime().maxMemory()).doubleValue();
        double heapAllocated = Double.valueOf((double) Runtime.getRuntime().totalMemory()).doubleValue();
        double nativeAllocated = Double.valueOf((double) Debug.getNativeHeapAllocatedSize()).doubleValue();
        double usedMemory = heapAllocated - Double.valueOf((double) Runtime.getRuntime().freeMemory()).doubleValue();
        Log.e("heapAllocated", " " + Runtime.getRuntime().totalMemory());
        Log.e("nativeAllocated", " " + Debug.getNativeHeapAllocatedSize());
        Log.e("getNativeHeapFreeSize", " " + Debug.getNativeHeapFreeSize());
        Log.e("usedMemory", " " + usedMemory);
        Log.e("old free memory ", " " + ((totalSize - heapAllocated) - nativeAllocated));
        return (totalSize - usedMemory) - nativeAllocated;
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
        Bitmap temp = graySourceBtm.copy(Config.ARGB_8888, false);
        if (graySourceBtm == temp) {
            return temp;
        }
        graySourceBtm.recycle();
        return temp;
    }

    public static Bitmap rotateBitmap(Bitmap r9, int r10) {
        throw new UnsupportedOperationException("Method not decompiled: com.prinext.imagesavelib.ImageUtility.rotateBitmap(android.graphics.Bitmap, int):android.graphics.Bitmap");
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

    public static boolean isPackageInstalled(String packagename, Context context) {
        try {
            context.getPackageManager().getPackageInfo(packagename, 1);
            return true;
        } catch (NameNotFoundException e) {
            return false;
        }
    }
}
