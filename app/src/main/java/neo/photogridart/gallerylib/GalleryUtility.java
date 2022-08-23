package neo.photogridart.gallerylib;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.provider.MediaStore.Images.Thumbnails;
import android.util.Log;

public class GalleryUtility {
    public static Bitmap getThumbnailBitmap(Context context, long imageId, int orientation) {
        Bitmap bitmap = null;

        try {
            bitmap = Thumbnails.getThumbnail(context.getContentResolver(), imageId, Thumbnails.FULL_SCREEN_KIND, (BitmapFactory.Options)null);
        } catch (Exception e) {
            Log.v(":::GalleryUtility", "bitmap failed " + e);
        }
        if (bitmap == null) {
            return null;
        }
        Bitmap btm = rotateImage(bitmap, orientation);
        if (btm == null) {
            return bitmap;
        }
        if (btm == bitmap) {
            return btm;
        }
        bitmap.recycle();
        return btm;
    }

    private static Bitmap rotateImage(Bitmap bitmap, int orientation) {
        Matrix matrix = new Matrix();
        if (orientation == 90) {
            matrix.postRotate(90.0f);
        } else if (orientation == 180) {
            matrix.postRotate(180.0f);
        } else if (orientation == 270) {
            matrix.postRotate(270.0f);
        }
        if (orientation == 0) {
            return null;
        }
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }
}
