package neo.photogridart.activities;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.MediaScannerConnectionClient;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;
import android.widget.ViewFlipper;

import neo.photogridart.R;
import neo.photogridart.adapters.MyRecyclerViewAdapter.RecyclerAdapterIndexChangedListener;
import neo.photogridart.bitmap.BitmapResizer;
import neo.photogridart.canvastext.ApplyTextInterface;
import neo.photogridart.canvastext.CustomRelativeLayout;
import neo.photogridart.canvastext.SingleTap;
import neo.photogridart.canvastext.TextData;
import neo.photogridart.collagelib.Utility;
import neo.photogridart.fragments.EffectFragment;
import neo.photogridart.fragments.EffectFragment.BitmapReady;
import neo.photogridart.fragments.FontFragment;
import neo.photogridart.fragments.FontFragment.FontChoosedListener;
import neo.photogridart.utils.LibUtility;
import neo.photogridart.utils.MirrorMode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class MirrorNewActivity extends AppCompatActivity {


    private static final String TAG = "MirrorNewActivity";
    int D3_BUTTON_SIZE = 24;
    int MIRROR_BUTTON_SIZE = 15;
    int RATIO_BUTTON_SIZE = 11;
    CustomRelativeLayout canvasText;
    int currentSelectedTabIndex = -1;
    ImageView[] d3ButtonArray;
    private int[] d3resList = {R.drawable.mirror_3d_14, R.drawable.mirror_3d_14, R.drawable.mirror_3d_10, R.drawable.mirror_3d_10, R.drawable.mirror_3d_11, R.drawable.mirror_3d_11, R.drawable.mirror_3d_4, R.drawable.mirror_3d_4, R.drawable.mirror_3d_3, R.drawable.mirror_3d_3, R.drawable.mirror_3d_1, R.drawable.mirror_3d_1, R.drawable.mirror_3d_6, R.drawable.mirror_3d_6, R.drawable.mirror_3d_13, R.drawable.mirror_3d_13, R.drawable.mirror_3d_15, R.drawable.mirror_3d_15, R.drawable.mirror_3d_15, R.drawable.mirror_3d_15, R.drawable.mirror_3d_16, R.drawable.mirror_3d_16, R.drawable.mirror_3d_16, R.drawable.mirror_3d_16};
    EffectFragment effectFragment;
    Bitmap filterBitmap;
    FontChoosedListener fontChoosedListener = new FontChoosedListener() {
        public void onOk(TextData textData) {
            MirrorNewActivity.this.canvasText.addTextView(textData);
            MirrorNewActivity.this.getSupportFragmentManager().beginTransaction().remove(MirrorNewActivity.this.fontFragment).commit();
        }
    };
    FontFragment fontFragment;
    int initialYPos = 0;
    RelativeLayout mainLayout;
    Matrix matrixMirror1 = new Matrix();
    Matrix matrixMirror2 = new Matrix();
    Matrix matrixMirror3 = new Matrix();
    Matrix matrixMirror4 = new Matrix();
    ImageView[] mirrorButtonArray;
    MirrorView mirrorView;
    float mulX = 16.0f;
    float mulY = 16.0f;
    Button[] ratioButtonArray;
    AlertDialog saveImageAlert;
    int screenHeightPixels;
    int screenWidthPixels;
    boolean showText = false;
    private Animation slideLeftIn;
    private Animation slideLeftOut;
    private Animation slideRightIn;
    private Animation slideRightOut;
    Bitmap sourceBitmap;
    View[] tabButtonList;
    ArrayList<TextData> textDataList = new ArrayList<>();
    ViewFlipper viewFlipper;
    ProgressBar pbar;
    TextView downloading, textview2;
    ImageView cancel;
    int count = 0;
    Dialog savedialog;


    class MirrorView extends View {
        int currentModeIndex = 0;
        Bitmap d3Bitmap;
        boolean d3Mode = false;
        RectF destRect1;
        RectF destRect1X;
        RectF destRect1Y;
        RectF destRect2;
        RectF destRect2X;
        RectF destRect2Y;
        RectF destRect3;
        RectF destRect4;
        boolean drawSavedImage = false;
        RectF dstRectPaper1;
        RectF dstRectPaper2;
        RectF dstRectPaper3;
        RectF dstRectPaper4;
        final Matrix f510I = new Matrix();
        Bitmap frameBitmap;
        Paint framePaint = new Paint();
        int height;
        boolean isTouchStartedLeft;
        boolean isTouchStartedTop;
        boolean isVerticle = false;
        Matrix m1 = new Matrix();
        Matrix m2 = new Matrix();
        Matrix m3 = new Matrix();
        MirrorMode[] mirrorModeList = new MirrorMode[20];
        MirrorMode modeX;
        MirrorMode modeX10;
        MirrorMode modeX11;
        MirrorMode modeX12;
        MirrorMode modeX13;
        MirrorMode modeX14;
        MirrorMode modeX15;
        MirrorMode modeX16;
        MirrorMode modeX17;
        MirrorMode modeX18;
        MirrorMode modeX19;
        MirrorMode modeX2;
        MirrorMode modeX20;
        MirrorMode modeX3;
        MirrorMode modeX4;
        MirrorMode modeX5;
        MirrorMode modeX6;
        MirrorMode modeX7;
        MirrorMode modeX8;
        MirrorMode modeX9;
        float oldX;
        float oldY;
        RectF srcRect1;
        RectF srcRect2;
        RectF srcRect3;
        RectF srcRectPaper;
        int tMode1;
        int tMode2;
        int tMode3;
        Matrix textMatrix = new Matrix();
        Paint textRectPaint = new Paint(1);
        RectF totalArea1;
        RectF totalArea2;
        RectF totalArea3;
        int width;

        public MirrorView(Context context, int screenWidth, int screenHeight) {
            super(context);
            this.width = MirrorNewActivity.this.sourceBitmap.getWidth();
            this.height = MirrorNewActivity.this.sourceBitmap.getHeight();
            int widthPixels = screenWidth;
            int heightPixels = screenHeight;
            createMatrix(widthPixels, heightPixels);
            createRectX(widthPixels, heightPixels);
            createRectY(widthPixels, heightPixels);
            createRectXY(widthPixels, heightPixels);
            createModes();
            this.framePaint.setAntiAlias(true);
            this.framePaint.setFilterBitmap(true);
            this.framePaint.setDither(true);
        }


        public void reset(int widthPixels, int heightPixels, boolean invalidate) {
            createMatrix(widthPixels, heightPixels);
            createRectX(widthPixels, heightPixels);
            createRectY(widthPixels, heightPixels);
            createRectXY(widthPixels, heightPixels);
            createModes();
            if (invalidate) {
                postInvalidate();
            }
        }


        public String saveBitmap(boolean saveToFile, int widthPixel, int heightPixel) {
            float upperScale = (float) Utility.maxSizeForSave();
            float scale = upperScale / ((float) Math.min(widthPixel, heightPixel));
            Log.e(MirrorNewActivity.TAG, "upperScale" + upperScale);
            Log.e(MirrorNewActivity.TAG, "scale" + scale);
            if (MirrorNewActivity.this.mulY > MirrorNewActivity.this.mulX) {
                float f = MirrorNewActivity.this.mulX;
                scale = (1.0f * scale) / MirrorNewActivity.this.mulY;
            }
            if (scale <= 0.0f) {
                scale = 1.0f;
            }
            Log.e(MirrorNewActivity.TAG, "scale" + scale);
            int wP = Math.round(((float) widthPixel) * scale);
            int wH = Math.round(((float) heightPixel) * scale);
            RectF srcRect = this.mirrorModeList[this.currentModeIndex].getSrcRect();
            reset(wP, wH, false);
            int btmWidth = Math.round(MirrorNewActivity.this.mirrorView.getCurrentMirrorMode().rectTotalArea.width());
            int btmHeight = Math.round(MirrorNewActivity.this.mirrorView.getCurrentMirrorMode().rectTotalArea.height());
            if (btmWidth % 2 == 1) {
                btmWidth--;
            }
            if (btmHeight % 2 == 1) {
                btmHeight--;
            }
            Bitmap savedBitmap = Bitmap.createBitmap(btmWidth, btmHeight, Config.ARGB_8888);
            Canvas bitmapCanvas = new Canvas(savedBitmap);
            Matrix matrix = new Matrix();
            matrix.reset();
            Log.e(MirrorNewActivity.TAG, "btmWidth " + btmWidth);
            Log.e(MirrorNewActivity.TAG, "btmHeight " + btmHeight);
            matrix.postTranslate(((float) (-(wP - btmWidth))) / 2.0f, ((float) (-(wH - btmHeight))) / 2.0f);
            MirrorMode saveMode = this.mirrorModeList[this.currentModeIndex];
            saveMode.setSrcRect(srcRect);
            if (MirrorNewActivity.this.filterBitmap == null) {
                drawMode(bitmapCanvas, MirrorNewActivity.this.sourceBitmap, saveMode, matrix);
            } else {
                drawMode(bitmapCanvas, MirrorNewActivity.this.filterBitmap, saveMode, matrix);
            }
            if (this.d3Mode && this.d3Bitmap != null && !this.d3Bitmap.isRecycled()) {
                bitmapCanvas.setMatrix(matrix);
                bitmapCanvas.drawBitmap(this.d3Bitmap, null, this.mirrorModeList[this.currentModeIndex].rectTotalArea, this.framePaint);
            }
            if (MirrorNewActivity.this.textDataList != null) {
                for (int i = 0; i < MirrorNewActivity.this.textDataList.size(); i++) {
                    Matrix mat = new Matrix();
                    mat.set(((TextData) MirrorNewActivity.this.textDataList.get(i)).imageSaveMatrix);
                    mat.postScale(scale, scale);
                    mat.postTranslate(((float) (-(wP - btmWidth))) / 2.0f, ((float) (-(wH - btmHeight))) / 2.0f);
                    bitmapCanvas.setMatrix(mat);
                    bitmapCanvas.drawText(((TextData) MirrorNewActivity.this.textDataList.get(i)).message, ((TextData) MirrorNewActivity.this.textDataList.get(i)).xPos, ((TextData) MirrorNewActivity.this.textDataList.get(i)).yPos, ((TextData) MirrorNewActivity.this.textDataList.get(i)).textPaint);
                }
            }
            if (this.frameBitmap != null && !this.frameBitmap.isRecycled()) {
                bitmapCanvas.setMatrix(matrix);
                bitmapCanvas.drawBitmap(this.frameBitmap, null, this.mirrorModeList[this.currentModeIndex].rectTotalArea, this.framePaint);
            }
            String resultPath = null;
            if (saveToFile) {
                String path;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    path = getContext().getExternalFilesDir("/").toString();
                } else {
                    path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath();
                }
                resultPath = new StringBuilder(path).append(MirrorNewActivity.this.getResources().getString(R.string.foldername)).append(String.valueOf(System.currentTimeMillis())).append(".jpg").toString();
                File file = new File(resultPath);
                file.getParentFile().mkdirs();
                try {
                    FileOutputStream out = new FileOutputStream(resultPath);
                    savedBitmap.compress(CompressFormat.JPEG, 90, out);
                    out.flush();
                    out.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
            savedBitmap.recycle();
            reset(widthPixel, heightPixel, false);
            this.mirrorModeList[this.currentModeIndex].setSrcRect(srcRect);
            return resultPath;
        }


        public void setCurrentMode(int index) {
            this.currentModeIndex = index;
        }

        public MirrorMode getCurrentMirrorMode() {
            return this.mirrorModeList[this.currentModeIndex];
        }

        private void createModes() {
            this.modeX = new MirrorMode(4, this.srcRect3, this.destRect1, this.destRect1, this.destRect3, this.destRect3, MirrorNewActivity.this.matrixMirror1, this.f510I, MirrorNewActivity.this.matrixMirror1, this.tMode3, this.totalArea3);
            this.modeX2 = new MirrorMode(4, this.srcRect3, this.destRect1, this.destRect4, this.destRect1, this.destRect4, MirrorNewActivity.this.matrixMirror1, MirrorNewActivity.this.matrixMirror1, this.f510I, this.tMode3, this.totalArea3);
            this.modeX3 = new MirrorMode(4, this.srcRect3, this.destRect3, this.destRect2, this.destRect3, this.destRect2, MirrorNewActivity.this.matrixMirror1, MirrorNewActivity.this.matrixMirror1, this.f510I, this.tMode3, this.totalArea3);
            this.modeX8 = new MirrorMode(4, this.srcRect3, this.destRect1, this.destRect1, this.destRect1, this.destRect1, MirrorNewActivity.this.matrixMirror1, MirrorNewActivity.this.matrixMirror2, MirrorNewActivity.this.matrixMirror3, this.tMode3, this.totalArea3);
            int m9TouchMode = 4;
            if (this.tMode3 == 0) {
                m9TouchMode = 0;
            }
            this.modeX9 = new MirrorMode(4, this.srcRect3, this.destRect2, this.destRect2, this.destRect2, this.destRect2, MirrorNewActivity.this.matrixMirror1, MirrorNewActivity.this.matrixMirror2, MirrorNewActivity.this.matrixMirror3, m9TouchMode, this.totalArea3);
            int m10TouchMode = 3;
            if (this.tMode3 == 1) {
                m10TouchMode = 1;
            }
            this.modeX10 = new MirrorMode(4, this.srcRect3, this.destRect3, this.destRect3, this.destRect3, this.destRect3, MirrorNewActivity.this.matrixMirror1, MirrorNewActivity.this.matrixMirror2, MirrorNewActivity.this.matrixMirror3, m10TouchMode, this.totalArea3);
            int m11TouchMode = 4;
            if (this.tMode3 == 0) {
                m11TouchMode = 3;
            }
            this.modeX11 = new MirrorMode(4, this.srcRect3, this.destRect4, this.destRect4, this.destRect4, this.destRect4, MirrorNewActivity.this.matrixMirror1, MirrorNewActivity.this.matrixMirror2, MirrorNewActivity.this.matrixMirror3, m11TouchMode, this.totalArea3);
            this.modeX4 = new MirrorMode(2, this.srcRect1, this.destRect1X, this.destRect1X, MirrorNewActivity.this.matrixMirror1, this.tMode1, this.totalArea1);
            int m5TouchMode = 4;
            if (this.tMode1 == 0) {
                m5TouchMode = 0;
            } else if (this.tMode1 == 5) {
                m5TouchMode = 5;
            }
            this.modeX5 = new MirrorMode(2, this.srcRect1, this.destRect2X, this.destRect2X, MirrorNewActivity.this.matrixMirror1, m5TouchMode, this.totalArea1);
            this.modeX6 = new MirrorMode(2, this.srcRect2, this.destRect1Y, this.destRect1Y, MirrorNewActivity.this.matrixMirror2, this.tMode2, this.totalArea2);
            int m7TouchMode = 3;
            if (this.tMode2 == 1) {
                m7TouchMode = 1;
            } else if (this.tMode2 == 6) {
                m7TouchMode = 6;
            }
            this.modeX7 = new MirrorMode(2, this.srcRect2, this.destRect2Y, this.destRect2Y, MirrorNewActivity.this.matrixMirror2, m7TouchMode, this.totalArea2);
            this.modeX12 = new MirrorMode(2, this.srcRect1, this.destRect1X, this.destRect2X, MirrorNewActivity.this.matrixMirror4, this.tMode1, this.totalArea1);
            this.modeX13 = new MirrorMode(2, this.srcRect2, this.destRect1Y, this.destRect2Y, MirrorNewActivity.this.matrixMirror4, this.tMode2, this.totalArea2);
            this.modeX14 = new MirrorMode(2, this.srcRect1, this.destRect1X, this.destRect1X, MirrorNewActivity.this.matrixMirror3, this.tMode1, this.totalArea1);
            this.modeX15 = new MirrorMode(2, this.srcRect2, this.destRect1Y, this.destRect1Y, MirrorNewActivity.this.matrixMirror3, this.tMode2, this.totalArea2);
            this.modeX16 = new MirrorMode(4, this.srcRectPaper, this.dstRectPaper1, this.dstRectPaper2, this.dstRectPaper3, this.dstRectPaper4, MirrorNewActivity.this.matrixMirror1, MirrorNewActivity.this.matrixMirror1, this.f510I, this.tMode1, this.totalArea1);
            this.modeX17 = new MirrorMode(4, this.srcRectPaper, this.dstRectPaper1, this.dstRectPaper3, this.dstRectPaper3, this.dstRectPaper1, this.f510I, MirrorNewActivity.this.matrixMirror1, MirrorNewActivity.this.matrixMirror1, this.tMode1, this.totalArea1);
            this.modeX18 = new MirrorMode(4, this.srcRectPaper, this.dstRectPaper2, this.dstRectPaper4, this.dstRectPaper2, this.dstRectPaper4, this.f510I, MirrorNewActivity.this.matrixMirror1, MirrorNewActivity.this.matrixMirror1, this.tMode1, this.totalArea1);
            this.modeX19 = new MirrorMode(4, this.srcRectPaper, this.dstRectPaper1, this.dstRectPaper2, this.dstRectPaper2, this.dstRectPaper1, this.f510I, MirrorNewActivity.this.matrixMirror1, MirrorNewActivity.this.matrixMirror1, this.tMode1, this.totalArea1);
            this.modeX20 = new MirrorMode(4, this.srcRectPaper, this.dstRectPaper4, this.dstRectPaper3, this.dstRectPaper3, this.dstRectPaper4, this.f510I, MirrorNewActivity.this.matrixMirror1, MirrorNewActivity.this.matrixMirror1, this.tMode1, this.totalArea1);
            this.mirrorModeList[0] = this.modeX4;
            this.mirrorModeList[1] = this.modeX5;
            this.mirrorModeList[2] = this.modeX6;
            this.mirrorModeList[3] = this.modeX7;
            this.mirrorModeList[4] = this.modeX8;
            this.mirrorModeList[5] = this.modeX9;
            this.mirrorModeList[6] = this.modeX10;
            this.mirrorModeList[7] = this.modeX11;
            this.mirrorModeList[8] = this.modeX12;
            this.mirrorModeList[9] = this.modeX13;
            this.mirrorModeList[10] = this.modeX14;
            this.mirrorModeList[11] = this.modeX15;
            this.mirrorModeList[12] = this.modeX;
            this.mirrorModeList[13] = this.modeX2;
            this.mirrorModeList[14] = this.modeX3;
            this.mirrorModeList[15] = this.modeX7;
            this.mirrorModeList[16] = this.modeX17;
            this.mirrorModeList[17] = this.modeX18;
            this.mirrorModeList[18] = this.modeX19;
            this.mirrorModeList[19] = this.modeX20;
        }

        public Bitmap getBitmap() {
            setDrawingCacheEnabled(true);
            buildDrawingCache();
            Bitmap bmp = Bitmap.createBitmap(getDrawingCache());
            setDrawingCacheEnabled(false);
            return bmp;
        }

        public void setFrame(int index) {
            if (this.frameBitmap != null && !this.frameBitmap.isRecycled()) {
                this.frameBitmap.recycle();
                this.frameBitmap = null;
            }
            if (index == 0) {
                postInvalidate();
                return;
            }
            this.frameBitmap = BitmapFactory.decodeResource(getResources(), LibUtility.borderRes[index]);
            postInvalidate();
        }

        private void createMatrix(int widthPixels, int heightPixels) {
            this.f510I.reset();
            MirrorNewActivity.this.matrixMirror1.reset();
            MirrorNewActivity.this.matrixMirror1.postScale(-1.0f, 1.0f);
            MirrorNewActivity.this.matrixMirror1.postTranslate((float) widthPixels, 0.0f);
            MirrorNewActivity.this.matrixMirror2.reset();
            MirrorNewActivity.this.matrixMirror2.postScale(1.0f, -1.0f);
            MirrorNewActivity.this.matrixMirror2.postTranslate(0.0f, (float) heightPixels);
            MirrorNewActivity.this.matrixMirror3.reset();
            MirrorNewActivity.this.matrixMirror3.postScale(-1.0f, -1.0f);
            MirrorNewActivity.this.matrixMirror3.postTranslate((float) widthPixels, (float) heightPixels);
        }

        private void createRectX(int widthPixels, int heightPixels) {
            float destH = ((float) widthPixels) * (MirrorNewActivity.this.mulY / MirrorNewActivity.this.mulX);
            float destW = ((float) widthPixels) / 2.0f;
            float destX = 0.0f;
            float f = (float) MirrorNewActivity.this.initialYPos;
            if (destH > ((float) heightPixels)) {
                destH = (float) heightPixels;
                destW = ((MirrorNewActivity.this.mulX / MirrorNewActivity.this.mulY) * destH) / 2.0f;
                destX = (((float) widthPixels) / 2.0f) - destW;
            }
            float destY = ((float) MirrorNewActivity.this.initialYPos) + ((((float) heightPixels) - destH) / 2.0f);
            float srcX = 0.0f;
            float srcY = 0.0f;
            float srcX2 = (float) this.width;
            float srcY2 = (float) this.height;
            this.destRect1X = new RectF(destX, destY, destW + destX, destH + destY);
            float destXX = destX + destW;
            this.destRect2X = new RectF(destXX, destY, destW + destXX, destH + destY);
            this.totalArea1 = new RectF(destX, destY, destW + destXX, destH + destY);
            this.tMode1 = 1;
            if (MirrorNewActivity.this.mulX * ((float) this.height) <= MirrorNewActivity.this.mulY * 2.0f * ((float) this.width)) {
                srcX = (((float) this.width) - (((MirrorNewActivity.this.mulX / MirrorNewActivity.this.mulY) * ((float) this.height)) / 2.0f)) / 2.0f;
                srcX2 = srcX + (((MirrorNewActivity.this.mulX / MirrorNewActivity.this.mulY) * ((float) this.height)) / 2.0f);
            } else {
                srcY = (((float) this.height) - (((float) (this.width * 2)) * (MirrorNewActivity.this.mulY / MirrorNewActivity.this.mulX))) / 2.0f;
                srcY2 = srcY + (((float) (this.width * 2)) * (MirrorNewActivity.this.mulY / MirrorNewActivity.this.mulX));
                this.tMode1 = 5;
            }
            this.srcRect1 = new RectF(srcX, srcY, srcX2, srcY2);
            this.srcRectPaper = new RectF(srcX, srcY, ((srcX2 - srcX) / 2.0f) + srcX, srcY2);
            float destWPapar = destW / 2.0f;
            this.dstRectPaper1 = new RectF(destX, destY, destWPapar + destX, destH + destY);
            float dextXP = destX + destWPapar;
            this.dstRectPaper2 = new RectF(dextXP, destY, destWPapar + dextXP, destH + destY);
            float dextXP2 = dextXP + destWPapar;
            this.dstRectPaper3 = new RectF(dextXP2, destY, destWPapar + dextXP2, destH + destY);
            float dextXP3 = dextXP2 + destWPapar;
            this.dstRectPaper4 = new RectF(dextXP3, destY, destWPapar + dextXP3, destH + destY);
        }

        private void createRectY(int widthPixels, int heightPixels) {
            float destH = (((float) widthPixels) * (MirrorNewActivity.this.mulY / MirrorNewActivity.this.mulX)) / 2.0f;
            float destW = (float) widthPixels;
            float destX = 0.0f;
            float f = (float) MirrorNewActivity.this.initialYPos;
            if (destH > ((float) heightPixels)) {
                destH = (float) heightPixels;
                destW = ((MirrorNewActivity.this.mulX / MirrorNewActivity.this.mulY) * destH) / 2.0f;
                destX = (((float) widthPixels) / 2.0f) - destW;
            }
            float destY = ((float) MirrorNewActivity.this.initialYPos) + ((((float) heightPixels) - (2.0f * destH)) / 2.0f);
            this.destRect1Y = new RectF(destX, destY, destW + destX, destH + destY);
            float destYY = destY + destH;
            this.destRect2Y = new RectF(destX, destYY, destW + destX, destH + destYY);
            this.totalArea2 = new RectF(destX, destY, destW + destX, destH + destYY);
            float srcX = 0.0f;
            float srcY = 0.0f;
            float srcX2 = (float) this.width;
            float srcY2 = (float) this.height;
            this.tMode2 = 0;
            if (MirrorNewActivity.this.mulX * 2.0f * ((float) this.height) > MirrorNewActivity.this.mulY * ((float) this.width)) {
                srcY = (((float) this.height) - (((MirrorNewActivity.this.mulY / MirrorNewActivity.this.mulX) * ((float) this.width)) / 2.0f)) / 2.0f;
                srcY2 = srcY + (((MirrorNewActivity.this.mulY / MirrorNewActivity.this.mulX) * ((float) this.width)) / 2.0f);
            } else {
                srcX = (((float) this.width) - (((float) (this.height * 2)) * (MirrorNewActivity.this.mulX / MirrorNewActivity.this.mulY))) / 2.0f;
                srcX2 = srcX + (((float) (this.height * 2)) * (MirrorNewActivity.this.mulX / MirrorNewActivity.this.mulY));
                this.tMode2 = 6;
            }
            this.srcRect2 = new RectF(srcX, srcY, srcX2, srcY2);
        }

        private void createRectXY(int widthPixels, int heightPixels) {
            float destH = (((float) widthPixels) * (MirrorNewActivity.this.mulY / MirrorNewActivity.this.mulX)) / 2.0f;
            float destW = ((float) widthPixels) / 2.0f;
            float destX = 0.0f;
            float f = (float) MirrorNewActivity.this.initialYPos;
            if (destH > ((float) heightPixels)) {
                destH = (float) heightPixels;
                destW = ((MirrorNewActivity.this.mulX / MirrorNewActivity.this.mulY) * destH) / 2.0f;
                destX = (((float) widthPixels) / 2.0f) - destW;
            }
            float destY = ((float) MirrorNewActivity.this.initialYPos) + ((((float) heightPixels) - (2.0f * destH)) / 2.0f);
            float srcX = 0.0f;
            float srcY = 0.0f;
            float srcX2 = (float) this.width;
            float srcY2 = (float) this.height;
            this.destRect1 = new RectF(destX, destY, destW + destX, destH + destY);
            float destX2 = destX + destW;
            this.destRect2 = new RectF(destX2, destY, destW + destX2, destH + destY);
            float destY2 = destY + destH;
            this.destRect3 = new RectF(destX, destY2, destW + destX, destH + destY2);
            this.destRect4 = new RectF(destX2, destY2, destW + destX2, destH + destY2);
            this.totalArea3 = new RectF(destX, destY, destW + destX2, destH + destY2);
            if (MirrorNewActivity.this.mulX * ((float) this.height) <= MirrorNewActivity.this.mulY * ((float) this.width)) {
                srcX = (((float) this.width) - ((MirrorNewActivity.this.mulX / MirrorNewActivity.this.mulY) * ((float) this.height))) / 2.0f;
                srcX2 = srcX + ((MirrorNewActivity.this.mulX / MirrorNewActivity.this.mulY) * ((float) this.height));
                this.tMode3 = 1;
            } else {
                srcY = (((float) this.height) - (((float) this.width) * (MirrorNewActivity.this.mulY / MirrorNewActivity.this.mulX))) / 2.0f;
                srcY2 = srcY + (((float) this.width) * (MirrorNewActivity.this.mulY / MirrorNewActivity.this.mulX));
                this.tMode3 = 0;
            }
            this.srcRect3 = new RectF(srcX, srcY, srcX2, srcY2);
        }

        public void onDraw(Canvas canvas) {
            if (MirrorNewActivity.this.filterBitmap == null) {
                drawMode(canvas, MirrorNewActivity.this.sourceBitmap, this.mirrorModeList[this.currentModeIndex], this.f510I);
            } else {
                drawMode(canvas, MirrorNewActivity.this.filterBitmap, this.mirrorModeList[this.currentModeIndex], this.f510I);
            }
            if (this.d3Mode && this.d3Bitmap != null && !this.d3Bitmap.isRecycled()) {
                canvas.setMatrix(this.f510I);
                canvas.drawBitmap(this.d3Bitmap, null, this.mirrorModeList[this.currentModeIndex].rectTotalArea, this.framePaint);
            }
            if (MirrorNewActivity.this.showText) {
                for (int i = 0; i < MirrorNewActivity.this.textDataList.size(); i++) {
                    this.textMatrix.set(((TextData) MirrorNewActivity.this.textDataList.get(i)).imageSaveMatrix);
                    this.textMatrix.postConcat(this.f510I);
                    canvas.setMatrix(this.textMatrix);
                    canvas.drawText(((TextData) MirrorNewActivity.this.textDataList.get(i)).message, ((TextData) MirrorNewActivity.this.textDataList.get(i)).xPos, ((TextData) MirrorNewActivity.this.textDataList.get(i)).yPos, ((TextData) MirrorNewActivity.this.textDataList.get(i)).textPaint);
                    canvas.setMatrix(this.f510I);
                    canvas.drawRect(0.0f, 0.0f, this.mirrorModeList[this.currentModeIndex].rectTotalArea.left, (float) MirrorNewActivity.this.screenHeightPixels, this.textRectPaint);
                    canvas.drawRect(0.0f, 0.0f, (float) MirrorNewActivity.this.screenWidthPixels, this.mirrorModeList[this.currentModeIndex].rectTotalArea.top, this.textRectPaint);
                    canvas.drawRect(this.mirrorModeList[this.currentModeIndex].rectTotalArea.right, 0.0f, (float) MirrorNewActivity.this.screenWidthPixels, (float) MirrorNewActivity.this.screenHeightPixels, this.textRectPaint);
                    canvas.drawRect(0.0f, this.mirrorModeList[this.currentModeIndex].rectTotalArea.bottom, (float) MirrorNewActivity.this.screenWidthPixels, (float) MirrorNewActivity.this.screenHeightPixels, this.textRectPaint);
                }
            }
            if (this.frameBitmap != null && !this.frameBitmap.isRecycled()) {
                canvas.setMatrix(this.f510I);
                canvas.drawBitmap(this.frameBitmap, null, this.mirrorModeList[this.currentModeIndex].rectTotalArea, this.framePaint);
            }
            super.onDraw(canvas);
        }

        private void drawMode(Canvas canvas, Bitmap bitmap, MirrorMode mirrorMode, Matrix matrix) {
            canvas.setMatrix(matrix);
            canvas.drawBitmap(bitmap, mirrorMode.getDrawBitmapSrc(), mirrorMode.rect1, this.framePaint);
            this.m1.set(mirrorMode.matrix1);
            this.m1.postConcat(matrix);
            canvas.setMatrix(this.m1);
            if (bitmap != null && !bitmap.isRecycled()) {
                canvas.drawBitmap(bitmap, mirrorMode.getDrawBitmapSrc(), mirrorMode.rect2, this.framePaint);
            }
            if (mirrorMode.count == 4) {
                this.m2.set(mirrorMode.matrix2);
                this.m2.postConcat(matrix);
                canvas.setMatrix(this.m2);
                if (bitmap != null && !bitmap.isRecycled()) {
                    canvas.drawBitmap(bitmap, mirrorMode.getDrawBitmapSrc(), mirrorMode.rect3, this.framePaint);
                }
                this.m3.set(mirrorMode.matrix3);
                this.m3.postConcat(matrix);
                canvas.setMatrix(this.m3);
                if (bitmap != null && !bitmap.isRecycled()) {
                    canvas.drawBitmap(bitmap, mirrorMode.getDrawBitmapSrc(), mirrorMode.rect4, this.framePaint);
                }
            }
        }

        public boolean onTouchEvent(MotionEvent event) {
            float x = event.getX();
            float y = event.getY();
            switch (event.getAction()) {
                case 0:
                    if (x < ((float) (MirrorNewActivity.this.screenWidthPixels / 2))) {
                        this.isTouchStartedLeft = true;
                    } else {
                        this.isTouchStartedLeft = false;
                    }
                    if (y < ((float) (MirrorNewActivity.this.screenHeightPixels / 2))) {
                        this.isTouchStartedTop = true;
                    } else {
                        this.isTouchStartedTop = false;
                    }
                    this.oldX = x;
                    this.oldY = y;
                    break;
                case 2:
                    moveGrid(this.mirrorModeList[this.currentModeIndex].getSrcRect(), x - this.oldX, y - this.oldY);
                    this.mirrorModeList[this.currentModeIndex].updateBitmapSrc();
                    this.oldX = x;
                    this.oldY = y;
                    break;
            }
            postInvalidate();
            return true;
        }


        public void moveGrid(RectF srcRect, float x, float y) {
            if (this.mirrorModeList[this.currentModeIndex].touchMode == 1 || this.mirrorModeList[this.currentModeIndex].touchMode == 4 || this.mirrorModeList[this.currentModeIndex].touchMode == 6) {
                if (this.mirrorModeList[this.currentModeIndex].touchMode == 4) {
                    x *= -1.0f;
                }
                if (this.isTouchStartedLeft && this.mirrorModeList[this.currentModeIndex].touchMode != 6) {
                    x *= -1.0f;
                }
                if (srcRect.left + x < 0.0f) {
                    x = -srcRect.left;
                }
                if (srcRect.right + x >= ((float) this.width)) {
                    x = ((float) this.width) - srcRect.right;
                }
                srcRect.left += x;
                srcRect.right += x;
            } else if (this.mirrorModeList[this.currentModeIndex].touchMode == 0 || this.mirrorModeList[this.currentModeIndex].touchMode == 3 || this.mirrorModeList[this.currentModeIndex].touchMode == 5) {
                if (this.mirrorModeList[this.currentModeIndex].touchMode == 3) {
                    y *= -1.0f;
                }
                if (this.isTouchStartedTop && this.mirrorModeList[this.currentModeIndex].touchMode != 5) {
                    y *= -1.0f;
                }
                if (srcRect.top + y < 0.0f) {
                    y = -srcRect.top;
                }
                if (srcRect.bottom + y >= ((float) this.height)) {
                    y = ((float) this.height) - srcRect.bottom;
                }
                srcRect.top += y;
                srcRect.bottom += y;
            }
        }
    }

    final class MyMediaScannerConnectionClient implements MediaScannerConnectionClient {
        private MediaScannerConnection mConn;
        private String mFilename;
        private String mMimetype;

        public MyMediaScannerConnectionClient(Context ctx, File file, String mimetype) {
            this.mFilename = file.getAbsolutePath();
            this.mConn = new MediaScannerConnection(ctx, this);
            this.mConn.connect();
        }

        public void onMediaScannerConnected() {
            this.mConn.scanFile(this.mFilename, this.mMimetype);
        }

        public void onScanCompleted(String path, Uri uri) {
            this.mConn.disconnect();
        }
    }

    private class SaveImageTask extends AsyncTask<Object, Object, Object> {
        String resultPath;

        private SaveImageTask() {
            this.resultPath = null;
        }


        public Object doInBackground(Object... arg0) {
            this.resultPath = MirrorNewActivity.this.mirrorView.saveBitmap(true, MirrorNewActivity.this.screenWidthPixels, MirrorNewActivity.this.screenHeightPixels);
            return null;
        }


        public void onPreExecute() {

            savedialog = new Dialog(MirrorNewActivity.this);
            savedialog.setContentView(R.layout.dialog_save);
            savedialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            savedialog.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.WRAP_CONTENT);
            savedialog.setCanceledOnTouchOutside(false);
            savedialog.setCancelable(false);

            pbar = savedialog.findViewById(R.id.pbar);
            downloading = savedialog.findViewById(R.id.downloading);
            textview2 = savedialog.findViewById(R.id.textview2);
            cancel = savedialog.findViewById(R.id.cancel);
            textview2.setText("Saving Image...");

            pbar.getProgressDrawable().setColorFilter(MirrorNewActivity.this.getResources().getColor(R.color.start_color), android.graphics.PorterDuff.Mode.SRC_IN);
            savedialog.show();
        }


        public void onPostExecute(Object result) {
            super.onPostExecute(result);

            new CountDownTimer(2000, 500) {
                @Override
                public void onTick(long millisUntilFinished) {
                    count = count + 25;
                    downloading.setText(count + "%");
                    pbar.setProgress(count);
                    Log.v(":::count", count + "");
                }

                @Override
                public void onFinish() {
                    Toast.makeText(MirrorNewActivity.this, "Image is Saved ", Toast.LENGTH_SHORT).show();
                    downloading.setText("100%");
                    pbar.setProgress(100);
                }
            }.start();

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (savedialog.isShowing()) {
                        savedialog.dismiss();
                    }
                    if (resultPath != null) {
                        Intent intent = new Intent(MirrorNewActivity.this, SaveImageActivity.class);
                        intent.putExtra("imagePath", resultPath);
                        MirrorNewActivity.this.startActivity(intent);
                    }
                }
            });

            new MyMediaScannerConnectionClient(MirrorNewActivity.this.getApplicationContext(), new File(this.resultPath), null);
        }
    }




    @SuppressLint({"NewApi"})
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(1024, 1024);
        Bundle extras = getIntent().getExtras();
        this.sourceBitmap = BitmapResizer.decodeBitmapFromFile(extras.getString("selectedImagePath"), extras.getInt("MAX_SIZE"));
        if (this.sourceBitmap == null) {
            Toast msg = Toast.makeText(this, "Could not load the photo, please use another GALLERY app!", Toast.LENGTH_LONG);
            msg.setGravity(17, msg.getXOffset() / 2, msg.getYOffset() / 2);
            msg.show();
            finish();
            return;
        }
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        this.screenHeightPixels = metrics.heightPixels;
        this.screenWidthPixels = metrics.widthPixels;
        Display display = getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();
        if (this.screenWidthPixels <= 0) {
            this.screenWidthPixels = width;
        }
        if (this.screenHeightPixels <= 0) {
            this.screenHeightPixels = height;
        }
        this.mirrorView = new MirrorView(this, this.screenWidthPixels, this.screenHeightPixels);
        setContentView((int) R.layout.activity_mirror_new);

        this.mainLayout = (RelativeLayout) findViewById(R.id.layout_mirror_activity);
        this.mainLayout.addView(this.mirrorView);
        this.viewFlipper = (ViewFlipper) findViewById(R.id.mirror_view_flipper);
        this.viewFlipper.bringToFront();
        findViewById(R.id.mirror_footer).bringToFront();
        this.slideLeftIn = AnimationUtils.loadAnimation(this, R.anim.slide_in_left);
        this.slideLeftOut = AnimationUtils.loadAnimation(this, R.anim.slide_out_left);
        this.slideRightIn = AnimationUtils.loadAnimation(this, R.anim.slide_in_right);
        this.slideRightOut = AnimationUtils.loadAnimation(this, R.anim.slide_out_right);
        findViewById(R.id.mirror_header).bringToFront();
        addEffectFragment();
        setSelectedTab(0);
    }

    public void onPause() {
        super.onPause();
    }

    public void addEffectFragment() {
        if (this.effectFragment == null) {
            this.effectFragment = (EffectFragment) getSupportFragmentManager().findFragmentByTag("MY_EFFECT_FRAGMENT");
            if (this.effectFragment == null) {
                this.effectFragment = new EffectFragment();
                Log.e(TAG, "EffectFragment == null");
                this.effectFragment.setBitmap(this.sourceBitmap);
                this.effectFragment.setArguments(getIntent().getExtras());
                getSupportFragmentManager().beginTransaction().add(R.id.mirror_effect_fragment_container, this.effectFragment, "MY_EFFECT_FRAGMENT").commit();
            } else {
                this.effectFragment.setBitmap(this.sourceBitmap);
                this.effectFragment.setSelectedTabIndex(0);
            }
            this.effectFragment.setBitmapReadyListener(new BitmapReady() {
                public void onBitmapReady(Bitmap bitmap) {
                    MirrorNewActivity.this.filterBitmap = bitmap;
                    MirrorNewActivity.this.mirrorView.postInvalidate();
                }
            });
            this.effectFragment.setBorderIndexChangedListener(new RecyclerAdapterIndexChangedListener() {
                public void onIndexChanged(int i) {
                    MirrorNewActivity.this.mirrorView.setFrame(i);
                }
            });
        }
    }

    public void onResume() {
        super.onResume();
    }

    public void onDestroy() {
        super.onDestroy();
        if (this.sourceBitmap != null) {
            this.sourceBitmap.recycle();
        }
        if (this.filterBitmap != null) {
            this.filterBitmap.recycle();
        }
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean("show_text", this.showText);
        savedInstanceState.putSerializable("text_data", this.textDataList);
        if (this.fontFragment != null && this.fontFragment.isVisible()) {
            getSupportFragmentManager().beginTransaction().remove(this.fontFragment).commit();
        }
        super.onSaveInstanceState(savedInstanceState);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        this.showText = savedInstanceState.getBoolean("show_text");
        this.textDataList = (ArrayList) savedInstanceState.getSerializable("text_data");
        if (this.textDataList == null) {
            this.textDataList = new ArrayList<>();
        }
    }

    public void myClickHandler(View view) {
        int id = view.getId();
        this.mirrorView.drawSavedImage = false;
        if (id == R.id.button_save_mirror_image) {
            new SaveImageTask().execute(new Object[0]);
        } else if (id == R.id.closeScreen) {
            backButtonAlertBuilder();
        } else if (id == R.id.button_mirror) {
            setSelectedTab(0);
        } else if (id == R.id.button_mirror_frame) {
            setSelectedTab(4);
        } else if (id == R.id.button_mirror_ratio) {
            setSelectedTab(2);
        } else if (id == R.id.button_mirror_effect) {
            setSelectedTab(3);
        } else if (id == R.id.button_mirror_adj) {
            setSelectedTab(5);
        } else if (id == R.id.button_mirror_3d) {
            setSelectedTab(1);
        } else if (id == R.id.button_3d_1) {
            set3dMode(0);
        } else if (id == R.id.button_3d_2) {
            set3dMode(1);
        } else if (id == R.id.button_3d_3) {
            set3dMode(2);
        } else if (id == R.id.button_3d_4) {
            set3dMode(3);
        } else if (id == R.id.button_3d_5) {
            set3dMode(4);
        } else if (id == R.id.button_3d_6) {
            set3dMode(5);
        } else if (id == R.id.button_3d_7) {
            set3dMode(6);
        } else if (id == R.id.button_3d_8) {
            set3dMode(7);
        } else if (id == R.id.button_3d_9) {
            set3dMode(8);
        } else if (id == R.id.button_3d_10) {
            set3dMode(9);
        } else if (id == R.id.button_3d_11) {
            set3dMode(10);
        } else if (id == R.id.button_3d_12) {
            set3dMode(11);
        } else if (id == R.id.button_3d_13) {
            set3dMode(12);
        } else if (id == R.id.button_3d_14) {
            set3dMode(13);
        } else if (id == R.id.button_3d_15) {
            set3dMode(14);
        } else if (id == R.id.button_3d_16) {
            set3dMode(15);
        } else if (id == R.id.button_3d_17) {
            set3dMode(16);
        } else if (id == R.id.button_3d_18) {
            set3dMode(17);
        } else if (id == R.id.button_3d_19) {
            set3dMode(18);
        } else if (id == R.id.button_3d_20) {
            set3dMode(19);
        } else if (id == R.id.button_3d_21) {
            set3dMode(20);
        } else if (id == R.id.button_3d_22) {
            set3dMode(21);
        } else if (id == R.id.button_3d_23) {
            set3dMode(22);
        } else if (id == R.id.button_3d_24) {
            set3dMode(23);
        } else if (id == R.id.button11) {
            this.mulX = 1.0f;
            this.mulY = 1.0f;
            this.mirrorView.reset(this.screenWidthPixels, this.screenHeightPixels, true);
            setRatioButtonBg(0);
        } else if (id == R.id.button21) {
            this.mulX = 2.0f;
            this.mulY = 1.0f;
            this.mirrorView.reset(this.screenWidthPixels, this.screenHeightPixels, true);
            setRatioButtonBg(1);
        } else if (id == R.id.button12) {
            this.mulX = 1.0f;
            this.mulY = 2.0f;
            this.mirrorView.reset(this.screenWidthPixels, this.screenHeightPixels, true);
            setRatioButtonBg(2);
        } else if (id == R.id.button32) {
            this.mulX = 3.0f;
            this.mulY = 2.0f;
            this.mirrorView.reset(this.screenWidthPixels, this.screenHeightPixels, true);
            setRatioButtonBg(3);
        } else if (id == R.id.button23) {
            this.mulX = 2.0f;
            this.mulY = 3.0f;
            this.mirrorView.reset(this.screenWidthPixels, this.screenHeightPixels, true);
            setRatioButtonBg(4);
        } else if (id == R.id.button43) {
            this.mulX = 4.0f;
            this.mulY = 3.0f;
            this.mirrorView.reset(this.screenWidthPixels, this.screenHeightPixels, true);
            setRatioButtonBg(5);
        } else if (id == R.id.button34) {
            this.mulX = 3.0f;
            this.mulY = 4.0f;
            this.mirrorView.reset(this.screenWidthPixels, this.screenHeightPixels, true);
            setRatioButtonBg(6);
        } else if (id == R.id.button45) {
            this.mulX = 4.0f;
            this.mulY = 5.0f;
            this.mirrorView.reset(this.screenWidthPixels, this.screenHeightPixels, true);
            setRatioButtonBg(7);
        } else if (id == R.id.button57) {
            this.mulX = 5.0f;
            this.mulY = 7.0f;
            this.mirrorView.reset(this.screenWidthPixels, this.screenHeightPixels, true);
            setRatioButtonBg(8);
        } else if (id == R.id.button169) {
            this.mulX = 16.0f;
            this.mulY = 9.0f;
            this.mirrorView.reset(this.screenWidthPixels, this.screenHeightPixels, true);
            setRatioButtonBg(9);
        } else if (id == R.id.button916) {
            this.mulX = 9.0f;
            this.mulY = 16.0f;
            this.mirrorView.reset(this.screenWidthPixels, this.screenHeightPixels, true);
            setRatioButtonBg(10);
        } else if (id == R.id.button_m1) {
            this.mirrorView.setCurrentMode(0);
            this.mirrorView.d3Mode = false;
            this.mirrorView.reset(this.screenWidthPixels, this.screenHeightPixels, true);
            setMirrorButtonBg(0);
        } else if (id == R.id.button_m2) {
            this.mirrorView.setCurrentMode(1);
            this.mirrorView.d3Mode = false;
            this.mirrorView.reset(this.screenWidthPixels, this.screenHeightPixels, true);
            setMirrorButtonBg(1);
        } else if (id == R.id.button_m3) {
            this.mirrorView.setCurrentMode(2);
            this.mirrorView.d3Mode = false;
            this.mirrorView.reset(this.screenWidthPixels, this.screenHeightPixels, true);
            setMirrorButtonBg(2);
        } else if (id == R.id.button_m4) {
            this.mirrorView.setCurrentMode(3);
            this.mirrorView.d3Mode = false;
            this.mirrorView.reset(this.screenWidthPixels, this.screenHeightPixels, true);
            setMirrorButtonBg(3);
        } else if (id == R.id.button_m5) {
            this.mirrorView.setCurrentMode(4);
            this.mirrorView.d3Mode = false;
            this.mirrorView.reset(this.screenWidthPixels, this.screenHeightPixels, true);
            setMirrorButtonBg(4);
        } else if (id == R.id.button_m6) {
            this.mirrorView.setCurrentMode(5);
            this.mirrorView.d3Mode = false;
            this.mirrorView.reset(this.screenWidthPixels, this.screenHeightPixels, true);
            setMirrorButtonBg(5);
        } else if (id == R.id.button_m7) {
            this.mirrorView.setCurrentMode(6);
            this.mirrorView.d3Mode = false;
            this.mirrorView.reset(this.screenWidthPixels, this.screenHeightPixels, true);
            setMirrorButtonBg(6);
        } else if (id == R.id.button_m8) {
            this.mirrorView.setCurrentMode(7);
            this.mirrorView.d3Mode = false;
            this.mirrorView.reset(this.screenWidthPixels, this.screenHeightPixels, true);
            setMirrorButtonBg(7);
        } else if (id == R.id.button_m9) {
            this.mirrorView.setCurrentMode(8);
            this.mirrorView.d3Mode = false;
            this.mirrorView.reset(this.screenWidthPixels, this.screenHeightPixels, true);
            setMirrorButtonBg(8);
        } else if (id == R.id.button_m10) {
            this.mirrorView.setCurrentMode(9);
            this.mirrorView.d3Mode = false;
            this.mirrorView.reset(this.screenWidthPixels, this.screenHeightPixels, true);
            setMirrorButtonBg(9);
        } else if (id == R.id.button_m11) {
            this.mirrorView.setCurrentMode(10);
            this.mirrorView.d3Mode = false;
            this.mirrorView.reset(this.screenWidthPixels, this.screenHeightPixels, true);
            setMirrorButtonBg(10);
        } else if (id == R.id.button_m12) {
            this.mirrorView.setCurrentMode(11);
            this.mirrorView.d3Mode = false;
            this.mirrorView.reset(this.screenWidthPixels, this.screenHeightPixels, true);
            setMirrorButtonBg(11);
        } else if (id == R.id.button_m13) {
            this.mirrorView.setCurrentMode(12);
            this.mirrorView.d3Mode = false;
            this.mirrorView.reset(this.screenWidthPixels, this.screenHeightPixels, true);
            setMirrorButtonBg(12);
        } else if (id == R.id.button_m14) {
            this.mirrorView.setCurrentMode(13);
            this.mirrorView.d3Mode = false;
            this.mirrorView.reset(this.screenWidthPixels, this.screenHeightPixels, true);
            setMirrorButtonBg(13);
        } else if (id == R.id.button_m15) {
            this.mirrorView.setCurrentMode(14);
            this.mirrorView.d3Mode = false;
            this.mirrorView.reset(this.screenWidthPixels, this.screenHeightPixels, true);
            setMirrorButtonBg(14);
        } else if (id == R.id.button_mirror_text) {
            addCanvasTextView();
            clearViewFlipper();
        } else if (id != R.id.button_mirror_sticker) {
            this.effectFragment.myClickHandler(id);
            if (id == R.id.button_lib_cancel || id == R.id.button_lib_ok) {
                clearFxAndFrame();
            }
        }
    }

    private void clearFxAndFrame() {
        int selectedTabIndex = this.effectFragment.getSelectedTabIndex();
        if (this.currentSelectedTabIndex != 3 && this.currentSelectedTabIndex != 4) {
            return;
        }
        if (selectedTabIndex == 0 || selectedTabIndex == 1) {
            clearViewFlipper();
        }
    }


    public void addCanvasTextView() {
        this.canvasText = new CustomRelativeLayout(this, this.textDataList, this.mirrorView.f510I, new SingleTap() {
            public void onSingleTap(TextData textData) {
                MirrorNewActivity.this.fontFragment = new FontFragment();
                Bundle arguments = new Bundle();
                arguments.putSerializable("text_data", textData);
                MirrorNewActivity.this.fontFragment.setArguments(arguments);
                MirrorNewActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.text_view_fragment_container, MirrorNewActivity.this.fontFragment, "FONT_FRAGMENT").commit();
                Log.e(MirrorNewActivity.TAG, "replace fragment");
                MirrorNewActivity.this.fontFragment.setFontChoosedListener(MirrorNewActivity.this.fontChoosedListener);
            }
        });
        this.canvasText.setApplyTextListener(new ApplyTextInterface() {
            public void onCancel() {
                MirrorNewActivity.this.showText = true;
                MirrorNewActivity.this.mainLayout.removeView(MirrorNewActivity.this.canvasText);
                MirrorNewActivity.this.mirrorView.postInvalidate();
            }

            public void onOk(ArrayList<TextData> arrayList) {
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    ((TextData) it.next()).setImageSaveMatrix(MirrorNewActivity.this.mirrorView.f510I);
                }
                MirrorNewActivity.this.textDataList = arrayList;
                MirrorNewActivity.this.showText = true;
                if (MirrorNewActivity.this.mainLayout == null) {
                    MirrorNewActivity.this.mainLayout = (RelativeLayout) MirrorNewActivity.this.findViewById(R.id.layout_mirror_activity);
                }
                MirrorNewActivity.this.mainLayout.removeView(MirrorNewActivity.this.canvasText);
                MirrorNewActivity.this.mirrorView.postInvalidate();
            }
        });
        this.showText = false;
        this.mirrorView.invalidate();
        this.mainLayout.addView(this.canvasText);
        findViewById(R.id.text_view_fragment_container).bringToFront();
        this.fontFragment = new FontFragment();
        this.fontFragment.setArguments(new Bundle());
        getSupportFragmentManager().beginTransaction().add(R.id.text_view_fragment_container, this.fontFragment, "FONT_FRAGMENT").commit();
        Log.e(TAG, "add fragment");
        this.fontFragment.setFontChoosedListener(this.fontChoosedListener);
    }

    private void set3dMode(int index) {
        this.mirrorView.d3Mode = true;
        if (index > 15 && index < 20) {
            this.mirrorView.setCurrentMode(index);
        } else if (index > 19) {
            this.mirrorView.setCurrentMode(index - 4);
        } else if (index % 2 == 0) {
            this.mirrorView.setCurrentMode(0);
        } else {
            this.mirrorView.setCurrentMode(1);
        }
        this.mirrorView.reset(this.screenWidthPixels, this.screenHeightPixels, false);
        if (VERSION.SDK_INT < 11) {
            if (this.mirrorView.d3Bitmap != null && !this.mirrorView.d3Bitmap.isRecycled()) {
                this.mirrorView.d3Bitmap.recycle();
            }
            this.mirrorView.d3Bitmap = BitmapFactory.decodeResource(getResources(), this.d3resList[index]);
        } else {
            loadInBitmap(this.d3resList[index]);
        }
        this.mirrorView.postInvalidate();
        setD3ButtonBg(index);
    }

    @SuppressLint({"NewApi"})
    private void loadInBitmap(int resId) {
        Log.e(TAG, "loadInBitmap");
        Options options = new Options();
        if (this.mirrorView.d3Bitmap == null || this.mirrorView.d3Bitmap.isRecycled()) {
            options.inJustDecodeBounds = true;
            options.inMutable = true;
            BitmapFactory.decodeResource(getResources(), resId, options);
            this.mirrorView.d3Bitmap = Bitmap.createBitmap(options.outWidth, options.outHeight, Config.ARGB_8888);
        }
        options.inJustDecodeBounds = false;
        options.inSampleSize = 1;
        options.inBitmap = this.mirrorView.d3Bitmap;
        try {
            this.mirrorView.d3Bitmap = BitmapFactory.decodeResource(getResources(), resId, options);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            if (this.mirrorView.d3Bitmap != null && !this.mirrorView.d3Bitmap.isRecycled()) {
                this.mirrorView.d3Bitmap.recycle();
            }
            this.mirrorView.d3Bitmap = BitmapFactory.decodeResource(getResources(), resId);
        }
    }

    private void setD3ButtonBg(int index) {
        if (this.d3ButtonArray == null) {
            this.d3ButtonArray = new ImageView[this.D3_BUTTON_SIZE];
            this.d3ButtonArray[0] = (ImageView) findViewById(R.id.button_3d_1);
            this.d3ButtonArray[1] = (ImageView) findViewById(R.id.button_3d_2);
            this.d3ButtonArray[2] = (ImageView) findViewById(R.id.button_3d_3);
            this.d3ButtonArray[3] = (ImageView) findViewById(R.id.button_3d_4);
            this.d3ButtonArray[4] = (ImageView) findViewById(R.id.button_3d_5);
            this.d3ButtonArray[5] = (ImageView) findViewById(R.id.button_3d_6);
            this.d3ButtonArray[6] = (ImageView) findViewById(R.id.button_3d_7);
            this.d3ButtonArray[7] = (ImageView) findViewById(R.id.button_3d_8);
            this.d3ButtonArray[8] = (ImageView) findViewById(R.id.button_3d_9);
            this.d3ButtonArray[9] = (ImageView) findViewById(R.id.button_3d_10);
            this.d3ButtonArray[10] = (ImageView) findViewById(R.id.button_3d_11);
            this.d3ButtonArray[11] = (ImageView) findViewById(R.id.button_3d_12);
            this.d3ButtonArray[12] = (ImageView) findViewById(R.id.button_3d_13);
            this.d3ButtonArray[13] = (ImageView) findViewById(R.id.button_3d_14);
            this.d3ButtonArray[14] = (ImageView) findViewById(R.id.button_3d_15);
            this.d3ButtonArray[15] = (ImageView) findViewById(R.id.button_3d_16);
            this.d3ButtonArray[16] = (ImageView) findViewById(R.id.button_3d_17);
            this.d3ButtonArray[17] = (ImageView) findViewById(R.id.button_3d_18);
            this.d3ButtonArray[18] = (ImageView) findViewById(R.id.button_3d_19);
            this.d3ButtonArray[19] = (ImageView) findViewById(R.id.button_3d_20);
            this.d3ButtonArray[20] = (ImageView) findViewById(R.id.button_3d_21);
            this.d3ButtonArray[21] = (ImageView) findViewById(R.id.button_3d_22);
            this.d3ButtonArray[22] = (ImageView) findViewById(R.id.button_3d_23);
            this.d3ButtonArray[23] = (ImageView) findViewById(R.id.button_3d_24);
        }
        for (int i = 0; i < this.D3_BUTTON_SIZE; i++) {
            this.d3ButtonArray[i].setBackgroundColor(getResources().getColor(R.color.blacktransparent));
        }
        this.d3ButtonArray[index].setBackgroundColor(getResources().getColor(R.color.footer_button_color_pressed));
    }

    private void setMirrorButtonBg(int index) {
        if (this.mirrorButtonArray == null) {
            this.mirrorButtonArray = new ImageView[this.MIRROR_BUTTON_SIZE];
            this.mirrorButtonArray[0] = (ImageView) findViewById(R.id.button_m1);
            this.mirrorButtonArray[1] = (ImageView) findViewById(R.id.button_m2);
            this.mirrorButtonArray[2] = (ImageView) findViewById(R.id.button_m3);
            this.mirrorButtonArray[3] = (ImageView) findViewById(R.id.button_m4);
            this.mirrorButtonArray[4] = (ImageView) findViewById(R.id.button_m5);
            this.mirrorButtonArray[5] = (ImageView) findViewById(R.id.button_m6);
            this.mirrorButtonArray[6] = (ImageView) findViewById(R.id.button_m7);
            this.mirrorButtonArray[7] = (ImageView) findViewById(R.id.button_m8);
            this.mirrorButtonArray[8] = (ImageView) findViewById(R.id.button_m9);
            this.mirrorButtonArray[9] = (ImageView) findViewById(R.id.button_m10);
            this.mirrorButtonArray[10] = (ImageView) findViewById(R.id.button_m11);
            this.mirrorButtonArray[11] = (ImageView) findViewById(R.id.button_m12);
            this.mirrorButtonArray[12] = (ImageView) findViewById(R.id.button_m13);
            this.mirrorButtonArray[13] = (ImageView) findViewById(R.id.button_m14);
            this.mirrorButtonArray[14] = (ImageView) findViewById(R.id.button_m15);
        }
        for (int i = 0; i < this.MIRROR_BUTTON_SIZE; i++) {
            this.mirrorButtonArray[i].setBackgroundResource(R.color.blacktransparent);
        }
        this.mirrorButtonArray[index].setBackgroundResource(R.color.footer_button_color_pressed);
    }

    private void setRatioButtonBg(int index) {
        if (this.ratioButtonArray == null) {
            this.ratioButtonArray = new Button[this.RATIO_BUTTON_SIZE];
            this.ratioButtonArray[0] = (Button) findViewById(R.id.button11);
            this.ratioButtonArray[1] = (Button) findViewById(R.id.button21);
            this.ratioButtonArray[2] = (Button) findViewById(R.id.button12);
            this.ratioButtonArray[3] = (Button) findViewById(R.id.button32);
            this.ratioButtonArray[4] = (Button) findViewById(R.id.button23);
            this.ratioButtonArray[5] = (Button) findViewById(R.id.button43);
            this.ratioButtonArray[6] = (Button) findViewById(R.id.button34);
            this.ratioButtonArray[7] = (Button) findViewById(R.id.button45);
            this.ratioButtonArray[8] = (Button) findViewById(R.id.button57);
            this.ratioButtonArray[9] = (Button) findViewById(R.id.button169);
            this.ratioButtonArray[10] = (Button) findViewById(R.id.button916);
        }
        for (int i = 0; i < this.RATIO_BUTTON_SIZE; i++) {
            this.ratioButtonArray[i].setBackgroundResource(R.drawable.selector_collage_ratio_button);
        }
        this.ratioButtonArray[index].setBackgroundResource(R.drawable.collage_ratio_bg_pressed);
    }

    public void setSelectedTab(int index) {
        setTabBg(0);
        int displayedChild = this.viewFlipper.getDisplayedChild();
        if (index == 0) {
            if (displayedChild != 0) {
                this.viewFlipper.setInAnimation(this.slideLeftIn);
                this.viewFlipper.setOutAnimation(this.slideRightOut);
                this.viewFlipper.setDisplayedChild(0);
            } else {
                return;
            }
        }
        if (index == 1) {
            setTabBg(1);
            if (displayedChild != 1) {
                if (displayedChild == 0) {
                    this.viewFlipper.setInAnimation(this.slideRightIn);
                    this.viewFlipper.setOutAnimation(this.slideLeftOut);
                } else {
                    this.viewFlipper.setInAnimation(this.slideLeftIn);
                    this.viewFlipper.setOutAnimation(this.slideRightOut);
                }
                this.viewFlipper.setDisplayedChild(1);
            } else {
                return;
            }
        }
        if (index == 2) {
            setTabBg(2);
            if (displayedChild != 2) {
                if (displayedChild == 0) {
                    this.viewFlipper.setInAnimation(this.slideRightIn);
                    this.viewFlipper.setOutAnimation(this.slideLeftOut);
                } else {
                    this.viewFlipper.setInAnimation(this.slideLeftIn);
                    this.viewFlipper.setOutAnimation(this.slideRightOut);
                }
                this.viewFlipper.setDisplayedChild(2);
            } else {
                return;
            }
        }
        if (index == 3) {
            setTabBg(3);
            this.effectFragment.setSelectedTabIndex(0);
            if (displayedChild != 3) {
                if (displayedChild == 0 || displayedChild == 2) {
                    this.viewFlipper.setInAnimation(this.slideRightIn);
                    this.viewFlipper.setOutAnimation(this.slideLeftOut);
                } else {
                    this.viewFlipper.setInAnimation(this.slideLeftIn);
                    this.viewFlipper.setOutAnimation(this.slideRightOut);
                }
                this.viewFlipper.setDisplayedChild(3);
            } else {
                return;
            }
        }
        if (index == 4) {
            setTabBg(4);
            this.effectFragment.setSelectedTabIndex(1);
            if (displayedChild != 3) {
                if (displayedChild == 5) {
                    this.viewFlipper.setInAnimation(this.slideLeftIn);
                    this.viewFlipper.setOutAnimation(this.slideRightOut);
                } else {
                    this.viewFlipper.setInAnimation(this.slideRightIn);
                    this.viewFlipper.setOutAnimation(this.slideLeftOut);
                }
                this.viewFlipper.setDisplayedChild(3);
            } else {
                return;
            }
        }
        if (index == 5) {
            setTabBg(5);
            this.effectFragment.showToolBar();
            if (displayedChild != 3) {
                this.viewFlipper.setInAnimation(this.slideRightIn);
                this.viewFlipper.setOutAnimation(this.slideLeftOut);
                this.viewFlipper.setDisplayedChild(3);
            } else {
                return;
            }
        }
        if (index == 7) {
            setTabBg(-1);
            if (displayedChild != 4) {
                this.viewFlipper.setInAnimation(this.slideRightIn);
                this.viewFlipper.setOutAnimation(this.slideLeftOut);
                this.viewFlipper.setDisplayedChild(4);
            }
        }
    }

    private void setTabBg(int index) {
        this.currentSelectedTabIndex = index;
        if (this.tabButtonList == null) {
            this.tabButtonList = new View[6];
            this.tabButtonList[0] = findViewById(R.id.button_mirror);
            this.tabButtonList[1] = findViewById(R.id.button_mirror_3d);
            this.tabButtonList[3] = findViewById(R.id.button_mirror_effect);
            this.tabButtonList[2] = findViewById(R.id.button_mirror_ratio);
            this.tabButtonList[4] = findViewById(R.id.button_mirror_frame);
            this.tabButtonList[5] = findViewById(R.id.button_mirror_adj);
        }
        for (View backgroundResource : this.tabButtonList) {
            // backgroundResource.setBackgroundResource(R.drawable.collage_footer_button);
            backgroundResource.setBackgroundResource(R.color.end_color);
        }
        if (index >= 0) {
            this.tabButtonList[index].setBackgroundResource(R.color.footer_button_color_pressed);
        }
    }


    public void clearViewFlipper() {
        this.viewFlipper.setInAnimation(null);
        this.viewFlipper.setOutAnimation(null);
        this.viewFlipper.setDisplayedChild(4);
        setTabBg(-1);
    }

    public void onBackPressed() {
        if (this.fontFragment != null && this.fontFragment.isVisible()) {
            getSupportFragmentManager().beginTransaction().remove(this.fontFragment).commit();
        } else if (this.viewFlipper.getDisplayedChild() == 3) {
            clearFxAndFrame();
            clearViewFlipper();
        } else if (!this.showText && this.canvasText != null) {
            this.showText = true;
            this.mainLayout.removeView(this.canvasText);
            this.mirrorView.postInvalidate();
            this.canvasText = null;
            Log.e(TAG, "replace fragment");
        } else if (this.viewFlipper.getDisplayedChild() != 4) {
            clearViewFlipper();
        } else {
            backButtonAlertBuilder();
        }
    }

    private void backButtonAlertBuilder() {
        final Dialog dialog = new Dialog(MirrorNewActivity.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().setLayout(-2, -2);
        dialog.setContentView(R.layout.dialog_back);
        TextView No = dialog.findViewById(R.id.dialogButton_no);
        TextView yes = dialog.findViewById(R.id.dialogButtonyes);

        yes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        No.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                dialog.cancel();
            }
        });

        dialog.show();
    }
}
