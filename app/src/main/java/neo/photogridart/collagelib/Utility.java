package neo.photogridart.collagelib;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Debug;
import android.provider.MediaStore.Images.Media;
import android.util.Log;

import neo.photogridart.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Utility {
    public static final int ICON_SIZE = 160;
    private static final String TAG = Utility.class.getSimpleName();
    private static final float limitDivider = 30.0f;
    private static final int[][] r0 = new int[12][];
    private static final float limitDividerGinger = 160.0f;
    public static final int[] patternResIdList = {R.drawable.no_pattern, R.drawable.color_picker, R.drawable.pattern_01, R.drawable.pattern_02, R.drawable.pattern_03, R.drawable.pattern_04, R.drawable.pattern_05, R.drawable.pattern_06, R.drawable.pattern_07, R.drawable.pattern_08, R.drawable.pattern_09, R.drawable.pattern_10, R.drawable.pattern_11, R.drawable.pattern_12, R.drawable.pattern_13, R.drawable.pattern_14, R.drawable.pattern_15, R.drawable.pattern_16, R.drawable.pattern_17, R.drawable.pattern_18, R.drawable.pattern_19, R.drawable.pattern_20, R.drawable.pattern_21, R.drawable.pattern_22, R.drawable.pattern_23, R.drawable.pattern_24, R.drawable.pattern_25, R.drawable.pattern_26, R.drawable.pattern_27, R.drawable.pattern_28, R.drawable.pattern_29, R.drawable.pattern_30, R.drawable.pattern_31, R.drawable.pattern_32, R.drawable.pattern_33, R.drawable.pattern_34, R.drawable.pattern_35, R.drawable.pattern_36, R.drawable.pattern_37, R.drawable.pattern_38, R.drawable.pattern_39, R.drawable.pattern_40, R.drawable.pattern_41, R.drawable.pattern_42, R.drawable.pattern_43, R.drawable.pattern_44, R.drawable.pattern_45, R.drawable.pattern_46, R.drawable.pattern_47, R.drawable.pattern_48, R.drawable.pattern_49, R.drawable.pattern_50, R.drawable.pattern_51, R.drawable.pattern_52, R.drawable.pattern_53, R.drawable.pattern_54, R.drawable.pattern_55, R.drawable.pattern_56, R.drawable.pattern_57};
    public static final int[][] patternResIdList2 = r0;
    public static final int[] patternResIdList3 = {R.drawable.no_pattern, R.drawable.color_picker, R.drawable.pattern_icon_08, R.drawable.pattern_icon_09, R.drawable.pattern_icon_06, R.drawable.pattern_icon_07, R.drawable.pattern_icon_10, R.drawable.pattern_icon_11, R.drawable.pattern_icon_12, R.drawable.pattern_icon_05, R.drawable.pattern_icon_01, R.drawable.pattern_icon_02, R.drawable.pattern_icon_03, R.drawable.pattern_icon_04};
    public static final int[][] stickerResIdList = {new int[]{R.drawable.flag_ball_1, R.drawable.flag_ball_2, R.drawable.flag_ball_3, R.drawable.flag_ball_4, R.drawable.flag_ball_5, R.drawable.flag_ball_6, R.drawable.flag_ball_7, R.drawable.flag_ball_8, R.drawable.flag_ball_9, R.drawable.flag_ball_10, R.drawable.flag_ball_11, R.drawable.flag_ball_12, R.drawable.flag_ball_13, R.drawable.flag_ball_14, R.drawable.flag_ball_15, R.drawable.flag_ball_16, R.drawable.flag_ball_17, R.drawable.flag_ball_18, R.drawable.flag_ball_19, R.drawable.flag_ball_20, R.drawable.flag_ball_21, R.drawable.flag_ball_22, R.drawable.flag_ball_22, R.drawable.flag_ball_23, R.drawable.flag_ball_24, R.drawable.flag_ball_25, R.drawable.flag_ball_26, R.drawable.flag_ball_27, R.drawable.flag_ball_28, R.drawable.flag_ball_29}, new int[]{R.drawable.country_love_1, R.drawable.country_love_2, R.drawable.country_love_3, R.drawable.country_love_4, R.drawable.country_love_5, R.drawable.country_love_6, R.drawable.country_love_7, R.drawable.country_love_8, R.drawable.country_love_9, R.drawable.country_love_10, R.drawable.country_love_11, R.drawable.country_love_12, R.drawable.country_love_13, R.drawable.country_love_14, R.drawable.country_love_15, R.drawable.country_love_16, R.drawable.country_love_17, R.drawable.country_love_18, R.drawable.country_love_19, R.drawable.country_love_20}, new int[]{R.drawable.text_regular_1, R.drawable.text_regular_2, R.drawable.text_regular_3, R.drawable.text_regular_4, R.drawable.text_regular_5, R.drawable.text_regular_6, R.drawable.text_regular_7, R.drawable.text_regular_8, R.drawable.text_regular_9, R.drawable.text_regular_10, R.drawable.text_regular_11, R.drawable.text_regular_12, R.drawable.text_regular_13, R.drawable.text_regular_14}, new int[]{R.drawable.mask_1, R.drawable.mask_2, R.drawable.mask_3, R.drawable.mask_4, R.drawable.mask_5, R.drawable.mask_6, R.drawable.mask_7, R.drawable.mask_8, R.drawable.mask_9}, new int[]{R.drawable.animal_face_1, R.drawable.animal_face_2, R.drawable.animal_face_3, R.drawable.animal_face_4, R.drawable.animal_face_5, R.drawable.animal_face_6, R.drawable.animal_face_7, R.drawable.animal_face_8, R.drawable.animal_face_9, R.drawable.animal_face_10, R.drawable.animal_face_11, R.drawable.animal_face_12, R.drawable.animal_face_13, R.drawable.animal_face_14, R.drawable.animal_face_15}, new int[]{R.drawable.love_regular_1, R.drawable.love_regular_2, R.drawable.love_regular_3, R.drawable.love_regular_4, R.drawable.love_regular_5, R.drawable.love_regular_6, R.drawable.love_regular_7, R.drawable.love_regular_8, R.drawable.love_regular_9, R.drawable.love_regular_10, R.drawable.love_regular_11, R.drawable.love_regular_12}, new int[]{R.drawable.love_special_1, R.drawable.love_special_2, R.drawable.love_special_3, R.drawable.love_special_4, R.drawable.love_special_5, R.drawable.love_special_6, R.drawable.love_special_7, R.drawable.love_special_8, R.drawable.love_special_9, R.drawable.love_special_10, R.drawable.love_special_11, R.drawable.love_special_12}, new int[]{R.drawable.couple_1, R.drawable.couple_2, R.drawable.couple_3, R.drawable.couple_4, R.drawable.couple_5, R.drawable.couple_6, R.drawable.couple_7, R.drawable.couple_8, R.drawable.couple_9}, new int[]{R.drawable.birthday_1, R.drawable.birthday_2, R.drawable.birthday_3, R.drawable.birthday_4, R.drawable.birthday_5, R.drawable.birthday_6, R.drawable.birthday_7, R.drawable.birthday_8, R.drawable.birthday_9, R.drawable.birthday_10, R.drawable.birthday_11, R.drawable.birthday_12}, new int[]{R.drawable.food_regular_1, R.drawable.food_regular_2, R.drawable.food_regular_3, R.drawable.food_regular_4, R.drawable.food_regular_5, R.drawable.food_regular_6, R.drawable.food_regular_7, R.drawable.food_regular_8, R.drawable.food_regular_9, R.drawable.food_regular_10, R.drawable.food_regular_11, R.drawable.food_regular_12, R.drawable.food_regular_13, R.drawable.food_regular_14, R.drawable.food_regular_15, R.drawable.food_regular_16, R.drawable.food_regular_17}, new int[]{R.drawable.food_special_1, R.drawable.food_special_2, R.drawable.food_special_3, R.drawable.food_special_4, R.drawable.food_special_5, R.drawable.food_special_6, R.drawable.food_special_7, R.drawable.food_special_8, R.drawable.food_special_9, R.drawable.food_special_10, R.drawable.food_special_11, R.drawable.food_special_12, R.drawable.food_special_13}};
    public static final int[] stickerResIdList2 = {R.drawable.flag_ball_1, R.drawable.country_love_1, R.drawable.text_regular_1, R.drawable.mask_1, R.drawable.animal_face_1, R.drawable.love_regular_1, R.drawable.love_special_1, R.drawable.couple_1, R.drawable.birthday_1, R.drawable.food_regular_1, R.drawable.food_special_1};
    public static final int[] stickerResIdList3 = {R.drawable.text_regular_1, R.drawable.text_regular_2, R.drawable.text_regular_3, R.drawable.text_regular_4, R.drawable.text_regular_5, R.drawable.text_regular_6, R.drawable.text_regular_7, R.drawable.text_regular_8, R.drawable.text_regular_9, R.drawable.text_regular_10, R.drawable.text_regular_11, R.drawable.text_regular_12, R.drawable.text_regular_13, R.drawable.text_regular_14};

    static {
        r0[0] = new int[]{R.drawable.pattern_085, R.drawable.pattern_086, R.drawable.pattern_087, R.drawable.pattern_088, R.drawable.pattern_089, R.drawable.pattern_090, R.drawable.pattern_091, R.drawable.pattern_092, R.drawable.pattern_093, R.drawable.pattern_094, R.drawable.pattern_095, R.drawable.pattern_096};
        r0[1] = new int[]{R.drawable.pattern_097, R.drawable.pattern_098, R.drawable.pattern_099, R.drawable.pattern_100, R.drawable.pattern_101, R.drawable.pattern_102, R.drawable.pattern_103, R.drawable.pattern_104, R.drawable.pattern_105, R.drawable.pattern_106, R.drawable.pattern_107, R.drawable.pattern_108};
        r0[2] = new int[]{R.drawable.pattern_061, R.drawable.pattern_062, R.drawable.pattern_063, R.drawable.pattern_064, R.drawable.pattern_065, R.drawable.pattern_066, R.drawable.pattern_067, R.drawable.pattern_068, R.drawable.pattern_069, R.drawable.pattern_070, R.drawable.pattern_071, R.drawable.pattern_072};
        r0[3] = new int[]{R.drawable.pattern_073, R.drawable.pattern_074, R.drawable.pattern_075, R.drawable.pattern_076, R.drawable.pattern_077, R.drawable.pattern_078, R.drawable.pattern_079, R.drawable.pattern_080, R.drawable.pattern_081, R.drawable.pattern_082, R.drawable.pattern_083, R.drawable.pattern_084};
        r0[4] = new int[]{R.drawable.pattern_109, R.drawable.pattern_110, R.drawable.pattern_111, R.drawable.pattern_112, R.drawable.pattern_113, R.drawable.pattern_114, R.drawable.pattern_115, R.drawable.pattern_116, R.drawable.pattern_117, R.drawable.pattern_118, R.drawable.pattern_119, R.drawable.pattern_120};
        r0[5] = new int[]{R.drawable.pattern_121, R.drawable.pattern_122, R.drawable.pattern_123, R.drawable.pattern_124, R.drawable.pattern_125, R.drawable.pattern_126, R.drawable.pattern_127, R.drawable.pattern_128, R.drawable.pattern_129, R.drawable.pattern_130, R.drawable.pattern_131};
        r0[6] = new int[]{R.drawable.pattern_132, R.drawable.pattern_133, R.drawable.pattern_134, R.drawable.pattern_135, R.drawable.pattern_136, R.drawable.pattern_137, R.drawable.pattern_138, R.drawable.pattern_139, R.drawable.pattern_140, R.drawable.pattern_141, R.drawable.pattern_142};
        r0[7] = new int[]{R.drawable.pattern_49, R.drawable.pattern_50, R.drawable.pattern_51, R.drawable.pattern_52, R.drawable.pattern_53, R.drawable.pattern_54, R.drawable.pattern_55, R.drawable.pattern_56, R.drawable.pattern_57, R.drawable.pattern_058, R.drawable.pattern_059, R.drawable.pattern_060};
        r0[8] = new int[]{R.drawable.pattern_01, R.drawable.pattern_02, R.drawable.pattern_03, R.drawable.pattern_04, R.drawable.pattern_05, R.drawable.pattern_06, R.drawable.pattern_07, R.drawable.pattern_08, R.drawable.pattern_09, R.drawable.pattern_10, R.drawable.pattern_11, R.drawable.pattern_12};
        r0[9] = new int[]{R.drawable.pattern_13, R.drawable.pattern_14, R.drawable.pattern_15, R.drawable.pattern_16, R.drawable.pattern_17, R.drawable.pattern_18, R.drawable.pattern_19, R.drawable.pattern_20, R.drawable.pattern_21, R.drawable.pattern_22, R.drawable.pattern_23, R.drawable.pattern_24};
        r0[10] = new int[]{R.drawable.pattern_25, R.drawable.pattern_26, R.drawable.pattern_27, R.drawable.pattern_28, R.drawable.pattern_29, R.drawable.pattern_30, R.drawable.pattern_31, R.drawable.pattern_32, R.drawable.pattern_33, R.drawable.pattern_34, R.drawable.pattern_35, R.drawable.pattern_36};
        r0[11] = new int[]{R.drawable.pattern_37, R.drawable.pattern_38, R.drawable.pattern_39, R.drawable.pattern_40, R.drawable.pattern_41, R.drawable.pattern_42, R.drawable.pattern_43, R.drawable.pattern_44, R.drawable.pattern_45, R.drawable.pattern_46, R.drawable.pattern_47, R.drawable.pattern_48};
    }

    @SuppressLint({"NewApi"})
    public static Bitmap getScaledBitmapFromId(Context context, long imageID, int orientation, int requiredSize, boolean isScrapBook) {
        Uri uri = Uri.withAppendedPath(Media.EXTERNAL_CONTENT_URI, Long.toString(imageID));
        Options boundsOption = new Options();
        boundsOption.inJustDecodeBounds = true;
        AssetFileDescriptor fileDescriptor = null;
        try {
            fileDescriptor = context.getContentResolver().openAssetFileDescriptor(uri, "r");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (fileDescriptor == null) {
            return null;
        }
        BitmapFactory.decodeFileDescriptor(fileDescriptor.getFileDescriptor(), null, boundsOption);
        Options options = new Options();
        options.inSampleSize = calculateInSampleSize(boundsOption, requiredSize, requiredSize);
        if (VERSION.SDK_INT >= 11 && isScrapBook) {
            options.inMutable = true;
        }
        Bitmap actuallyUsableBitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor.getFileDescriptor(), null, options);
        if (actuallyUsableBitmap == null) {
            return null;
        }
        Bitmap bitmap = rotateImage(actuallyUsableBitmap, orientation);
        if (!(bitmap == null || actuallyUsableBitmap == bitmap)) {
            actuallyUsableBitmap.recycle();
        }
        if (bitmap.isMutable() || !isScrapBook) {
            return bitmap;
        }
        Log.e(TAG, "bitmap is not mutable");
        Bitmap mutableBitmap = bitmap.copy(Config.ARGB_8888, true);
        if (mutableBitmap == bitmap) {
            return mutableBitmap;
        }
        bitmap.recycle();
        return mutableBitmap;
    }

    @SuppressLint({"NewApi"})
    public static Bitmap decodeFile(String path, int requiredSize, boolean isScrapBook) {
        try {
            File f = new File(path);
            Options boundsOption = new Options();
            boundsOption.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, boundsOption);
            Options o2 = new Options();
            if (VERSION.SDK_INT >= 11 && isScrapBook) {
                o2.inMutable = true;
            }
            o2.inSampleSize = calculateInSampleSize(boundsOption, requiredSize, requiredSize);
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
            ExifInterface exif = null;
            try {
                exif = new ExifInterface(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Bitmap bitmap2 = rotateImage(bitmap, exif.getAttributeInt("Orientation", 0));
            if (bitmap2.isMutable()) {
                return bitmap2;
            }
            Bitmap mutableBitmap = bitmap2.copy(Config.ARGB_8888, true);
            if (mutableBitmap != bitmap2) {
                bitmap2.recycle();
            }
            return mutableBitmap;
        } catch (FileNotFoundException e2) {
            return null;
        }
    }

    private static Bitmap rotateImage(Bitmap bitmap, int orientation) {
        Bitmap resultBitmap = bitmap;
        Matrix matrix = new Matrix();
        if (orientation == 90) {
            matrix.postRotate(90.0f);
        } else if (orientation == 180) {
            matrix.postRotate(180.0f);
        } else if (orientation == 270) {
            matrix.postRotate(270.0f);
        }
        if (orientation == 0) {
            return resultBitmap;
        }
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public static int calculateInSampleSize(Options options, int reqWidth, int reqHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            int halfHeight = height / 2;
            int halfWidth = width / 2;
            while (true) {
                if (halfHeight / inSampleSize <= reqHeight && halfWidth / inSampleSize <= reqWidth) {
                    break;
                }
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    public static double getLeftSizeOfMemory() {
        return (Double.valueOf((double) Runtime.getRuntime().maxMemory()).doubleValue() - (Double.valueOf((double) Runtime.getRuntime().totalMemory()).doubleValue() - Double.valueOf((double) Runtime.getRuntime().freeMemory()).doubleValue())) - Double.valueOf((double) Debug.getNativeHeapAllocatedSize()).doubleValue();
    }

    public static int maxSizeForDimension(Context context, int count, float upperLimit) {
        float divider = limitDivider;
        if (VERSION.SDK_INT <= 11) {
            divider = limitDividerGinger;
        }
        Log.e(TAG, "divider = " + divider);
        int maxSize = (int) Math.sqrt(getLeftSizeOfMemory() / ((double) (((float) count) * divider)));
        if (maxSize <= 0) {
            maxSize = getDefaultLimit(count, upperLimit);
        }
        return Math.min(maxSize, getDefaultLimit(count, upperLimit));
    }

    public static int maxSizeForSave(Context context, float upperLimit) {
        float divider = limitDivider;
        if (VERSION.SDK_INT <= 11) {
            divider = limitDividerGinger;
        }
        Log.e(TAG, "divider = " + divider);
        int maxSize = (int) Math.sqrt(getLeftSizeOfMemory() / ((double) divider));
        if (maxSize > 0) {
            return (int) Math.min((float) maxSize, upperLimit);
        }
        return (int) upperLimit;
    }

    private static int getDefaultLimit(int count, float upperLimit) {
        int limit = (int) (((double) upperLimit) / Math.sqrt((double) count));
        Log.e(TAG, "limit = " + limit);
        return limit;
    }

    public static float pointToAngle(float x, float y, float centerX, float centerY) {
        float degree = 0.0f;
        if (x >= centerX && y < centerY) {
            degree = (float) (270.0d + Math.toDegrees(Math.atan(((double) (x - centerX)) / ((double) (centerY - y)))));
        } else if (x > centerX && y >= centerY) {
            degree = (float) Math.toDegrees(Math.atan(((double) (y - centerY)) / ((double) (x - centerX))));
        } else if (x <= centerX && y > centerY) {
            degree = (float) (90.0d + Math.toDegrees(Math.atan(((double) (centerX - x)) / ((double) (y - centerY)))));
        } else if (x < centerX && y <= centerY) {
            degree = (float) (((int) Math.toDegrees(Math.atan(((double) (centerY - y)) / ((double) (centerX - x))))) + 180);
        }
        if (degree < -180.0f) {
            degree += 360.0f;
        }
        if (degree > 180.0f) {
            return degree - 360.0f;
        }
        return degree;
    }

    public static int maxSizeForSave() {
        int maxSize = (int) Math.sqrt(getLeftSizeOfMemory() / 40.0d);
        if (maxSize > 1080) {
            return 1080;
        }
        return maxSize;
    }
}
