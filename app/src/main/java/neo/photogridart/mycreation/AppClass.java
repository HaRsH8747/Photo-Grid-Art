package neo.photogridart.mycreation;

import android.content.ContentResolver;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import neo.photogridart.R;

import java.io.File;
import java.io.IOException;

public class AppClass {
    public static String path;

    public static String getOutputPath(Context context) {
        StringBuilder stringBuilder = new StringBuilder();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            path = context.getExternalFilesDir("/").toString();
        } else {
            path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
        }
        stringBuilder.append(path + "/" + context.getResources().getString(R.string.foldername));
        String stringBuilder2 = stringBuilder.toString();
        File file = new File(stringBuilder2);
        if (!file.exists()) {
            file.mkdirs();
        }
        return stringBuilder2;
    }

    public static boolean isNetworkAvailable(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            return (networkInfo != null && networkInfo.isConnected());
        } catch (Exception ex) {
            return false;
        }
    }

    public static void showSnackbarforinternet(View view) {
        final Snackbar snackBar = Snackbar.make(view, "No Internet Connection!!", Snackbar.LENGTH_LONG);

        snackBar.show();
    }

    public static void deleteFileFromMediaStore(Context context, final ContentResolver contentResolver, final File file) {
        String canonicalPath;
        try {
            canonicalPath = file.getCanonicalPath();
        } catch (IOException e) {
            canonicalPath = file.getAbsolutePath();
        }
        final Uri uri = MediaStore.Files.getContentUri("external");
        final int result = contentResolver.delete(uri,
                MediaStore.Files.FileColumns.DATA + "=?", new String[]{canonicalPath});
        if (result == 0) {
            final String absolutePath = file.getAbsolutePath();
            if (!absolutePath.equals(canonicalPath)) {
                contentResolver.delete(uri,
                        MediaStore.Files.FileColumns.DATA + "=?", new String[]{absolutePath});
            }
        }
    }


}
