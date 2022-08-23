package neo.photogridart.activities;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdView;
import neo.photogridart.R;
import neo.photogridart.bitmap.BitmapResizer;
import neo.photogridart.collagelib.CollageActivity;
import neo.photogridart.collagelib.CollageHelper;
import neo.photogridart.gallerylib.GalleryFragment;
import neo.photogridart.imagesavelib.ImageLoader;
import neo.photogridart.imagesavelib.ImageLoader.ImageLoaded;
import neo.photogridart.utils.AdUtils;
import neo.photogridart.utils.Utility;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements OnClickListener {
    private static Context ctx;
    int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;

    int MEDIA_TYPE_IMAGE = 1;
    int PERMISSION_CAMERA_EDITOR = 44;
    int PERMISSION_COLLAGE_EDITOR = 11;
    int PERMISSION_MIRROR_EDITOR = 55;
    int PERMISSION_SCRAPBOOK_EDITOR = 33;
    int PERMISSION_SINGLE_EDITOR = 22;
    int REQUEST_MIRROR = 3;
    final Activity activity = this;
    Uri fileUri;
    GalleryFragment galleryFragment;
    ImageLoader imageLoader;
    ConstraintLayout mMainLayout;
    CardView mCameraLayout, mCollegeLayout, mMirrorLayout, mScrapbookLayout, mSingleEditorLayout;
    List<String> productList;


    private NativeAd mobNativeView;

    private void NativeBinding(NativeAd nativeAd, NativeAdView adView) {
        MediaView mediaView = adView.findViewById(R.id.ad_media);
        adView.setMediaView(mediaView);
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
        if (nativeAd.getBody() == null) {
            adView.getBodyView().setVisibility(View.INVISIBLE);
        } else {
            adView.getBodyView().setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }
        if (nativeAd.getCallToAction() == null) {
            adView.getCallToActionView().setVisibility(View.INVISIBLE);
        } else {
            adView.getCallToActionView().setVisibility(View.VISIBLE);
            ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }
        if (nativeAd.getIcon() == null) {
            adView.getIconView().setVisibility(View.GONE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(
                    nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }
        adView.setNativeAd(nativeAd);
    }

    public void NativeShow(final FrameLayout frameLayout) {
        AdLoader.Builder builder = new AdLoader.Builder(getApplication(), AdUtils.nativeAdvanceId);

        builder.forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
            @Override
            public void onNativeAdLoaded(NativeAd nativeAd) {

                boolean isDestroyed = false;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    isDestroyed = isDestroyed();
                }
                if (isDestroyed || isFinishing() || isChangingConfigurations()) {
                    nativeAd.destroy();
                    return;
                }
                if (MainActivity.this.mobNativeView != null) {
                    MainActivity.this.mobNativeView.destroy();
                }
                MainActivity.this.mobNativeView = nativeAd;
                NativeAdView adView = (NativeAdView) getLayoutInflater().inflate(R.layout.mobnative, null);
                NativeBinding(nativeAd, adView);
                frameLayout.removeAllViews();
                frameLayout.addView(adView);
            }
        });
        VideoOptions videoOptions = new VideoOptions.Builder().build();
        com.google.android.gms.ads.nativead.NativeAdOptions adOptions = new com.google.android.gms.ads.nativead.NativeAdOptions.Builder().setVideoOptions(videoOptions).build();
        builder.withNativeAdOptions(adOptions);
        AdLoader adLoader = builder.withAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {


            }
        }).build();
        adLoader.loadAd(new AdRequest.Builder().build());


    }

    public void NativeLoad() {
        NativeShow((FrameLayout) findViewById(R.id.mobadslayout));
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(1024, 1024);
        setContentView((int) R.layout.activity_main);
        if (AdUtils.isAdEnabled){
            NativeLoad();
        }

        GalleryFragment localGalleryFragment = CollageHelper.getGalleryFragment(this);
        if (localGalleryFragment != null && localGalleryFragment.isVisible()) {
            Log.v(":::Mainactivity", "fragment back");
            localGalleryFragment.onBackPressed();
        }


        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        ctx = this;

        findViewbyIds();


        if (Build.VERSION.SDK_INT >= 23) {
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            String str = "Read Storage";
            if (!addPermission(arrayList2, "android.permission.READ_EXTERNAL_STORAGE")) {
                arrayList.add(str);
            }
            if (!addPermission(arrayList2, "android.permission.WRITE_EXTERNAL_STORAGE")) {
                arrayList.add(str);
            }
            if (!addPermission(arrayList2, "android.permission.CAMERA")) {
                arrayList.add("Camera");
            }

            if (arrayList2.size() > 0) {
                requestPermissions((String[]) arrayList2.toArray(new String[arrayList2.size()]), 2);
            }
        }


        this.imageLoader = new ImageLoader(this);
        this.imageLoader.setListener(new ImageLoaded() {
            public void callFileSizeAlertDialogBuilder() {
                MainActivity.this.fileSizeAlertDialogBuilder();
            }
        });
    }

    @SuppressLint("WrongConstant")
    private boolean addPermission(List<String> list, String str) {
        if (Build.VERSION.SDK_INT >= 23 && checkSelfPermission(str) != 0) {
            list.add(str);
            if (!shouldShowRequestPermissionRationale(str)) {
                return false;
            }
        }
        return true;
    }

    public void onPause() {
        super.onPause();
    }

    public void onResume() {
        super.onResume();
    }

    private void findViewbyIds() {
        this.mMainLayout = (ConstraintLayout) findViewById(R.id.mainLayout);
        this.mSingleEditorLayout = findViewById(R.id.layout_single_editor);
        this.mCameraLayout = findViewById(R.id.layout_camera);
        this.mCollegeLayout = findViewById(R.id.layout_college);
        this.mMirrorLayout = findViewById(R.id.layout_mirror);
        this.mScrapbookLayout = findViewById(R.id.layout_scrapbook);

        this.mSingleEditorLayout.setOnClickListener(this);
        this.mCameraLayout.setOnClickListener(this);
        this.mCollegeLayout.setOnClickListener(this);
        this.mMirrorLayout.setOnClickListener(this);
        this.mScrapbookLayout.setOnClickListener(this);
    }


    public void fileSizeAlertDialogBuilder() {
        Point p = BitmapResizer.decodeFileSize(new File(this.imageLoader.selectedImagePath), Utility.maxSizeForDimension(this, 1, 1500.0f));
        if (p == null || p.x != -1) {
            startShaderActivity();
        } else {
            startShaderActivity();
        }
    }

    private void startShaderActivity() {
        int maxSize = Utility.maxSizeForDimension(this, 1, 1500.0f);
        Intent shaderIntent = new Intent(getApplicationContext(), MirrorNewActivity.class);
        shaderIntent.putExtra("selectedImagePath", this.imageLoader.selectedImagePath);
        shaderIntent.putExtra("isSession", false);
        shaderIntent.putExtra("MAX_SIZE", maxSize);
        Utility.logFreeMemory(this);
        startActivity(shaderIntent);


    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == this.PERMISSION_COLLAGE_EDITOR) {
            if (ActivityCompat.checkSelfPermission(this, permissions[0]) == 0) {
                openCollage(false, false, false);
            }
        } else if (requestCode == this.PERMISSION_SINGLE_EDITOR) {
            if (ActivityCompat.checkSelfPermission(this, permissions[0]) == 0) {
                openCollage(true, false, false);
            }
        } else if (requestCode == this.PERMISSION_SCRAPBOOK_EDITOR) {
            if (ActivityCompat.checkSelfPermission(this, permissions[0]) == 0) {
                openCollage(false, true, false);
            }
        } else if (requestCode == this.PERMISSION_CAMERA_EDITOR) {
            if (ActivityCompat.checkSelfPermission(this, permissions[0]) == 0) {
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                this.fileUri = getOutputMediaFileUri(this.MEDIA_TYPE_IMAGE);
                intent.putExtra("output", this.fileUri);
                startActivityForResult(intent, this.CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
            }
        } else if (requestCode == this.PERMISSION_MIRROR_EDITOR && ActivityCompat.checkSelfPermission(this, permissions[0]) == 0) {
            Intent galleryIntent = new Intent();
            galleryIntent.setType("image/*");
            galleryIntent.setAction("android.intent.action.GET_CONTENT");
            startActivityForResult(Intent.createChooser(galleryIntent, "Select Picture"), this.REQUEST_MIRROR);
        }
    }

    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("file_uri", this.fileUri);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        this.fileUri = (Uri) savedInstanceState.getParcelable("file_uri");
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if (requestCode == this.CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
                if (resultCode == -1) {
                    Intent localIntent = new Intent(this, CollageActivity.class);
                    System.out.println("CAMERA IMAGE PATH" + this.fileUri.getPath());
                    localIntent.putExtra("selected_image_path", this.fileUri.getPath());
                    startActivity(localIntent);

                    MainActivity.this.finish();
                } else if (resultCode == 0) {
                    Toast.makeText(getApplicationContext(), "User cancelled image capture", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Sorry! Failed to capture image", Toast.LENGTH_SHORT).show();
                }
            } else if (resultCode == -1 && requestCode == this.REQUEST_MIRROR) {
                try {
                    this.imageLoader.getImageFromIntent(data);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, "" + getString(R.string.error_img_not_found), Toast.LENGTH_SHORT).show();
                }
            }


        } catch (NullPointerException e2) {
            e2.printStackTrace();
        }
    }

    public void onClick(View v) {
        if (this.mCollegeLayout == v) {
            if (VERSION.SDK_INT < 23) {
                openCollage(false, false, false);
            } else if (checkAndRequestCollagePermissions()) {
                openCollage(false, false, false);
            }
        }

        if (this.mSingleEditorLayout == v) {
            if (VERSION.SDK_INT < 23) {
                openCollage(true, false, false);
            } else if (checkAndRequestSinglePermissions()) {
                openCollage(true, false, false);
            }
        }

        if (this.mScrapbookLayout == v) {
            if (VERSION.SDK_INT < 23) {
                openCollage(false, true, false);
            } else if (checkAndRequestScrapbookPermissions()) {
                openCollage(false, true, false);
            }
        }

        if (this.mCameraLayout == v) {
            if (VERSION.SDK_INT < 23) {
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                this.fileUri = getOutputMediaFileUri(this.MEDIA_TYPE_IMAGE);
                intent.putExtra("output", this.fileUri);
                startActivityForResult(intent, this.CAMERA_CAPTURE_IMAGE_REQUEST_CODE);

            } else if (checkAndRequestCameraPermissions()) {
                Intent intent2 = new Intent("android.media.action.IMAGE_CAPTURE");
                this.fileUri = getOutputMediaFileUri(this.MEDIA_TYPE_IMAGE);
                intent2.putExtra("output", this.fileUri);
                startActivityForResult(intent2, this.CAMERA_CAPTURE_IMAGE_REQUEST_CODE);

            }
        }
        if (this.mMirrorLayout == v) {
            if (VERSION.SDK_INT < 23) {
                Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction("android.intent.action.GET_CONTENT");
                startActivityForResult(Intent.createChooser(galleryIntent, "Select Picture"), this.REQUEST_MIRROR);
            } else if (checkAndRequestMirrorPermissions()) {
                Intent galleryIntent2 = new Intent();
                galleryIntent2.setType("image/*");
                galleryIntent2.setAction("android.intent.action.GET_CONTENT");
                startActivityForResult(Intent.createChooser(galleryIntent2, "Select Picture"), this.REQUEST_MIRROR);
            }
        }
    }

    public void openCollage(boolean isblur, boolean isScrapBook, boolean isShape) {
        this.galleryFragment = CollageHelper.addGalleryFragment(this, R.id.gallery_fragment_container, true, null);
        this.galleryFragment.setCollageSingleMode(isblur);
        this.galleryFragment.setIsScrapbook(isScrapBook);
        this.galleryFragment.setIsShape(isShape);
        if (!isScrapBook) {
            this.galleryFragment.setLimitMax(GalleryFragment.MAX_COLLAGE);
        }
    }

    public void onBackPressed() {
        GalleryFragment localGalleryFragment = CollageHelper.getGalleryFragment(this);
        if (localGalleryFragment != null && localGalleryFragment.isVisible()) {
            Log.v(":::Mainactivity", "fragment back");
            localGalleryFragment.onBackPressed();
        } else {
            Log.v(":::Mainactivity", "checkforrate");
            startActivity(new Intent(MainActivity.this, CreateActivity.class));
            finish();
        }

    }

    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    public File getOutputMediaFile(int type) {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), getResources().getString(R.string.foldername));
        if (mediaStorageDir.exists() || mediaStorageDir.mkdirs()) {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
            if (type == this.MEDIA_TYPE_IMAGE) {
                return new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
            }
            return null;
        }
        return null;
    }

    private boolean checkAndRequestCollagePermissions() {
        int permissionCAMERA = ContextCompat.checkSelfPermission(this, "android.permission.CAMERA");
        int storagePermission = ContextCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE");
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (storagePermission != 0) {
            listPermissionsNeeded.add("android.permission.READ_EXTERNAL_STORAGE");
        }
        if (permissionCAMERA != 0) {
            listPermissionsNeeded.add("android.permission.CAMERA");
        }
        if (listPermissionsNeeded.isEmpty()) {
            return true;
        }
        ActivityCompat.requestPermissions(this, (String[]) listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), this.PERMISSION_COLLAGE_EDITOR);
        return false;
    }

    private boolean checkAndRequestSinglePermissions() {
        int permissionCAMERA = ContextCompat.checkSelfPermission(this, "android.permission.CAMERA");
        int storagePermission = ContextCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE");
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (storagePermission != 0) {
            listPermissionsNeeded.add("android.permission.READ_EXTERNAL_STORAGE");
        }
        if (permissionCAMERA != 0) {
            listPermissionsNeeded.add("android.permission.CAMERA");
        }
        if (listPermissionsNeeded.isEmpty()) {
            return true;
        }
        ActivityCompat.requestPermissions(this, (String[]) listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), this.PERMISSION_SINGLE_EDITOR);
        return false;
    }

    private boolean checkAndRequestScrapbookPermissions() {
        int permissionCAMERA = ContextCompat.checkSelfPermission(this, "android.permission.CAMERA");
        int storagePermission = ContextCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE");
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (storagePermission != 0) {
            listPermissionsNeeded.add("android.permission.READ_EXTERNAL_STORAGE");
        }
        if (permissionCAMERA != 0) {
            listPermissionsNeeded.add("android.permission.CAMERA");
        }
        if (listPermissionsNeeded.isEmpty()) {
            return true;
        }
        ActivityCompat.requestPermissions(this, (String[]) listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), this.PERMISSION_SCRAPBOOK_EDITOR);
        return false;
    }

    private boolean checkAndRequestCameraPermissions() {
        int permissionCAMERA = ContextCompat.checkSelfPermission(this, "android.permission.CAMERA");
        int storagePermission = ContextCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE");
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (storagePermission != 0) {
            listPermissionsNeeded.add("android.permission.READ_EXTERNAL_STORAGE");
        }
        if (permissionCAMERA != 0) {
            listPermissionsNeeded.add("android.permission.CAMERA");
        }
        if (listPermissionsNeeded.isEmpty()) {
            return true;
        }
        ActivityCompat.requestPermissions(this, (String[]) listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), this.PERMISSION_CAMERA_EDITOR);
        return false;
    }

    private boolean checkAndRequestMirrorPermissions() {
        int permissionCAMERA = ContextCompat.checkSelfPermission(this, "android.permission.CAMERA");
        int storagePermission = ContextCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE");
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (storagePermission != 0) {
            listPermissionsNeeded.add("android.permission.READ_EXTERNAL_STORAGE");
        }
        if (permissionCAMERA != 0) {
            listPermissionsNeeded.add("android.permission.CAMERA");
        }
        if (listPermissionsNeeded.isEmpty()) {
            return true;
        }
        ActivityCompat.requestPermissions(this, (String[]) listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), this.PERMISSION_MIRROR_EDITOR);
        return false;
    }
}
