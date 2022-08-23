package neo.photogridart.mycreation;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore.Audio;
import android.provider.MediaStore.Images.Media;
import android.provider.MediaStore.Video;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class FileHelper {
    private static String TAG;

    static {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("JNP__");
        stringBuilder.append(FileHelper.class.getSimpleName());
        TAG = stringBuilder.toString();
    }

    @SuppressLint({"DefaultLocale"})
    public static String formatFileSize(long j) {
        if (j < 1024) {
            return String.format("%d B", Long.valueOf(j));
        } else if (j < 1048576) {
            return String.format("%.1f KB", Float.valueOf(((float) j) / 1024.0f));
        } else if (j < 1073741824) {
            return String.format("%.1f MB", Float.valueOf((((float) j) / 1024.0f) / 1024.0f));
        } else if (j < 0) {
            return String.format("%.1f GB", Float.valueOf(((((float) j) / 1024.0f) / 1024.0f) / 1024.0f));
        } else {
            return String.format("%.1f TB", Float.valueOf((((((float) j) / 1024.0f) / 1024.0f) / 1024.0f) / 1024.0f));
        }
    }

    public static boolean isExtension(String str, String str2) {
        return str2.equalsIgnoreCase(str.substring(str.lastIndexOf(".") + 1));
    }

    public static ArrayList<String> loadFiles(String str) {
        ArrayList<String> arrayList = new ArrayList();
        File file = new File(str);
        String[] list = file.exists() ? file.list() : null;
        if (list != null) {
            for (String append : list) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(file.getPath());
                stringBuilder.append("/");
                stringBuilder.append(append);
                arrayList.add(stringBuilder.toString());
            }
        }
        return arrayList;
    }

    public static boolean delete(String str) {
        return new File(str).delete();
    }

    public static boolean delete(Context context, Uri uri) {
        return getPath(context, uri).delete();
    }

    public static void copyFile(@NonNull String str, @NonNull String str2) {
        if (!str.equalsIgnoreCase(str2)) {
            String fileName = getFileName(str);
            try {
                File file = new File(str2);
                if (!file.exists()) {
                    file.mkdirs();
                }
                InputStream fileInputStream = new FileInputStream(str);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(str2);
                stringBuilder.append(File.separator);
                stringBuilder.append(fileName);
                OutputStream fileOutputStream = new FileOutputStream(stringBuilder.toString());
                byte[] bArr = new byte[1024];
                while (true) {
                    int read = fileInputStream.read(bArr);
                    if (read == -1) {
                        break;
                    }
                    fileOutputStream.write(bArr, 0, read);
                }
                fileInputStream.close();
                fileOutputStream.flush();
                fileOutputStream.close();
            } catch (FileNotFoundException e) {
                Log.e(TAG, e.getMessage());
            } catch (Exception e2) {
                Log.e(TAG, e2.getMessage());
            }
        }
    }

    public static String getFileName(String str) {
        return str.substring(str.lastIndexOf("/") + 1);
    }


    @SuppressLint({"NewApi"})
    public static File getPath(final Context context, final Uri uri) {
        String path = null;
        // DocumentProvider
        if (DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    path = Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(uri)) {
                // DownloadsProvider
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris
                        .withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                path = getDataColumn(context, contentUri, null, null);
            } else if (isMediaDocument(uri)) {
                // MediaProvider
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };

                path = getDataColumn(context, contentUri, selection, selectionArgs);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // MediaStore (and general)
            path = getDataColumn(context, uri, null, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // File
            path = uri.getPath();
        }

        if (path != null) {
            return new File(path);
        }
        return null;
    }

    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    private static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }

}
