package neo.photogridart.stickers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtils {
    private static final String APP_DIR = "Abner";
    private static final String TEMP_DIR = "Abner/.TEMP";
    private static FileUtils instance = null;
    private static Context mContext;

    public static FileUtils getInstance(Context context) {
        if (instance == null) {
            synchronized (FileUtils.class) {
                if (instance == null) {
                    mContext = context.getApplicationContext();
                    instance = new FileUtils();
                }
            }
        }
        return instance;
    }

    public static String saveBitmapToLocal(Bitmap bm, Context context) {
        try {
            File file = getInstance(context).createTempFile("IMG_", ".jpg");
            FileOutputStream fos = new FileOutputStream(file);
            bm.compress(CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            return file.getAbsolutePath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public File createTempFile(String prefix, String extension) throws IOException {
        File file = new File(getAppDirPath() + ".TEMP/" + prefix + System.currentTimeMillis() + extension);
        file.createNewFile();
        return file;
    }

    public String getAppDirPath() {
        if (getLocalPath() != null) {
            return getLocalPath() + APP_DIR + "/";
        }
        return null;
    }

    private static String getLocalPath() {
        return mContext.getFilesDir().getAbsolutePath() + "/";
    }

    public boolean isSDCanWrite() {
        if (!Environment.getExternalStorageState().equals("mounted") || !Environment.getExternalStorageDirectory().canWrite() || !Environment.getExternalStorageDirectory().canRead()) {
            return false;
        }
        return true;
    }

    private FileUtils() {
        if (isSDCanWrite()) {
            creatSDDir(APP_DIR);
            creatSDDir(TEMP_DIR);
        }
    }

    public File creatSDDir(String dirName) {
        File dir = new File(getLocalPath() + dirName);
        dir.mkdirs();
        return dir;
    }
}
