package neo.photogridart.activities;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import neo.photogridart.R;
import neo.photogridart.bitmap.BitmapLoader;
import neo.photogridart.mycreation.AppClass;
import neo.photogridart.mycreation.MyCreationActivity;
import neo.photogridart.utils.AdUtils;

import java.io.File;
import java.util.Iterator;
import java.util.List;

public class SaveImageActivity extends AppCompatActivity implements View.OnClickListener {

    private Bundle bundle;

    public String imagePath;
    Bitmap previewBitmap;
    ProgressDialog progressDialog;
    public ImageView shareImageview;
    private ImageView back;
    ImageView mycreation;
    RelativeLayout facebook, instagram, multipleshare, delete;

    private class BitmapWorkerTask extends AsyncTask<Void, Void, Bitmap> {
        BitmapLoader bitmapLoader = new BitmapLoader();
        DisplayMetrics metrics;

        public BitmapWorkerTask() {
            Log.v(":::savedimage1", imagePath + "");
            new File(SaveImageActivity.this.imagePath);
            this.metrics = SaveImageActivity.this.getResources().getDisplayMetrics();
        }

        public void onPreExecute() {
            super.onPreExecute();
        }

        public Bitmap doInBackground(Void... arg0) {

            try {
                Log.v(":::savedimage2", imagePath + "");
                return this.bitmapLoader.load(SaveImageActivity.this, new int[]{this.metrics.widthPixels, this.metrics.heightPixels}, SaveImageActivity.this.imagePath);
            } catch (Exception e) {
                Log.v(":::saveexception", e + "");
                return null;
            }

        }

        public void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                SaveImageActivity.this.shareImageview.setImageBitmap(bitmap);
                SaveImageActivity.this.previewBitmap = bitmap.copy(Config.ARGB_8888, false);
                return;
            }
            Toast.makeText(SaveImageActivity.this, SaveImageActivity.this.getString(R.string.error_img_not_found), Toast.LENGTH_SHORT).show();
        }
    }

    Animation blink;


    LinearLayout adContainerView;
    AdView adViewone;

    public void BannerIDCardAds() {
        adContainerView = findViewById(R.id.adsmultyViews);
        adViewone = new AdView(getApplicationContext());
        adViewone.setAdUnitId(AdUtils.bannerAdId);
        adContainerView.addView(adViewone);
        BannerLoad();
        adViewone.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {

            }
        });
    }

    private void BannerLoad() {
        AdRequest adRequest = new AdRequest.Builder().build();
        AdSize adSize = BannerGetSize();
        adViewone.setAdSize(adSize);
        adViewone.loadAd(adRequest);
    }

    private AdSize BannerGetSize() {
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        float widthPixels = outMetrics.widthPixels;
        float density = outMetrics.density;
        int adWidth = (int) (widthPixels / density);
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(1024, 1024);
        setContentView((int) R.layout.activity_save_image);
        blink = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
        blink.setRepeatCount(Animation.INFINITE);
        if (AdUtils.isAdEnabled){
            BannerIDCardAds();
        }

        shareImageview = (ImageView) findViewById(R.id.share_imageView);
        this.facebook = findViewById(R.id.facebook);
        this.instagram = findViewById(R.id.instagram);
        this.multipleshare = findViewById(R.id.share_more);
        this.mycreation = findViewById(R.id.mycreation);
        mycreation.startAnimation(blink);

        blink.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                mycreation.startAnimation(blink);
            }

            public void onAnimationRepeat(Animation animation) {
            }
        });
        this.back = findViewById(R.id.back);
        this.delete = findViewById(R.id.delete);

        this.back.setOnClickListener(this);
        this.mycreation.setOnClickListener(this);
        facebook.setOnClickListener(this);
        instagram.setOnClickListener(this);
        multipleshare.setOnClickListener(this);

        this.bundle = getIntent().getExtras();
        if (this.bundle != null) {
            this.imagePath = this.bundle.getString("imagePath");
        }
        Log.v(":::savedimage0", imagePath + "");

        if (imagePath.isEmpty()) {
            Toast.makeText(this, "failed to load image", Toast.LENGTH_SHORT).show();
        } else {
            new BitmapWorkerTask().execute(new Void[0]);
        }

        this.progressDialog = new ProgressDialog(this);
        this.progressDialog.setIndeterminate(true);
        this.progressDialog.setMessage("Loading...");

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(SaveImageActivity.this);
                dialog.getWindow().requestFeature(1);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setContentView(R.layout.dialog_delete);
                Button no = (Button) dialog.findViewById(R.id.iv_no);
                Button yes = (Button) dialog.findViewById(R.id.iv_yes);

                no.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                yes.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        imagePath = imagePath.replaceAll("file://", "");
                        Log.v(":::deletingpath", imagePath + "");

                        AppClass.deleteFileFromMediaStore(SaveImageActivity.this, getContentResolver(), new File(Uri.parse(imagePath).getPath()));

                        Toast.makeText(SaveImageActivity.this, "Image Deleted Successfully", Toast.LENGTH_SHORT).show();

                        dialog.dismiss();
                        Intent i = new Intent(SaveImageActivity.this, CreateActivity.class);
                        startActivity(i);
                        finish();

                    }
                });
                dialog.show();
            }
        });

    }

    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.back:
                startActivity(new Intent(SaveImageActivity.this, CreateActivity.class));
                finish();
                break;
            case R.id.mycreation:
                startActivity(new Intent(SaveImageActivity.this, MyCreationActivity.class));
                SaveImageActivity.this.finish();
                break;

            case R.id.facebook:
                if (appInstalledOrNot("com.facebook.katana")) {

                    Intent share = new Intent(Intent.ACTION_SEND);
                    share.setType("image/*");
                    share.setPackage("com.facebook.katana");
                    share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                    try {
                        if (android.os.Build.VERSION.SDK_INT >= 23) {
                            imagePath = imagePath.replaceAll("file://", "");
                            Log.v(":::shareurl", imagePath + "");
                            File imageFileToShare = new File(imagePath);
                            Uri uri = FileProvider.getUriForFile(SaveImageActivity.this, "com.photocollagegridpicmaker.provider", imageFileToShare);
                            share.putExtra(Intent.EXTRA_STREAM, uri);

                            startActivity(Intent.createChooser(share, "Share to Facebook"));
                        } else {
                            try {
                                share.putExtra(Intent.EXTRA_STREAM, Uri.parse(imagePath));
                                SaveImageActivity.this.startActivity(Intent.createChooser(share, "Share to Facebook"));
                            } catch (Exception unused) {
                                Toast.makeText(SaveImageActivity.this, "You can not share image to this app..", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(SaveImageActivity.this, "Facebook App is not installed", Toast.LENGTH_LONG).show();
                    return;
                }
                break;

            case R.id.instagram:

                if (appInstalledOrNot("com.instagram.android")) {
                    try {
                        Intent share = new Intent(Intent.ACTION_SEND);
                        share.setType("image/*");
                        share.setPackage("com.instagram.android");
                        share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                        if (android.os.Build.VERSION.SDK_INT >= 23) {
                            imagePath = imagePath.replaceAll("file://", "");
                            Log.v(":::shareurl", imagePath + "");
                            File imageFileToShare = new File(imagePath);
                            Uri uri = FileProvider.getUriForFile(SaveImageActivity.this, "com.photocollagegridpicmaker.provider", imageFileToShare);
                            share.putExtra(Intent.EXTRA_STREAM, uri);
                            startActivity(Intent.createChooser(share, "Share to Instagram"));
                        } else {
                            try {
                                share.putExtra(Intent.EXTRA_STREAM, Uri.parse(imagePath));
                                SaveImageActivity.this.startActivity(Intent.createChooser(share, "Share to Instagram"));
                            } catch (Exception unused) {
                                Toast.makeText(SaveImageActivity.this, "You can not share image to this app..", Toast.LENGTH_SHORT).show();
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(SaveImageActivity.this, "Instagram App is not installed", Toast.LENGTH_LONG).show();
                }

                break;

            case R.id.share_more:

                try {

                    Intent share = new Intent(Intent.ACTION_SEND);
                    share.setType("image/*");
                    share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                    if (android.os.Build.VERSION.SDK_INT >= 23) {
                        imagePath = imagePath.replaceAll("file://", "");
                        Log.v(":::shareurl", imagePath + "");
                        File imageFileToShare = new File(imagePath);
                        Uri uri = FileProvider.getUriForFile(SaveImageActivity.this, "com.photocollagegridpicmaker.provider", imageFileToShare);
                        share.putExtra(Intent.EXTRA_STREAM, uri);
                        startActivity(Intent.createChooser(share, "Share to"));
                    } else {
                        try {
                            share.putExtra(Intent.EXTRA_STREAM, Uri.parse(imagePath));
                            SaveImageActivity.this.startActivity(Intent.createChooser(share, "Share to"));
                        } catch (Exception unused) {
                            Toast.makeText(SaveImageActivity.this, "You can not share image to this app..", Toast.LENGTH_SHORT).show();
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                return;
        }
    }

    private boolean appInstalledOrNot(String uri) {
        try {
            getPackageManager().getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }


    public void onActivityResult(int requestCode, int responseCode, Intent data) {
        super.onActivityResult(requestCode, responseCode, data);
        this.progressDialog.hide();
    }


    private void initShareIntent(String type) {
        ResolveInfo info = null;
        boolean found = false;
        Intent share = new Intent("android.intent.action.SEND");
        share.setType("image/jpeg");
        List<ResolveInfo> resInfo = getPackageManager().queryIntentActivities(share, 0);
        if (!resInfo.isEmpty()) {
            Iterator it = resInfo.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                info = (ResolveInfo) it.next();
                if (!info.activityInfo.packageName.toLowerCase().contains(type)) {
                    if (info.activityInfo.name.toLowerCase().contains(type)) {
                        break;
                    }
                } else {
                    break;
                }
            }
            share.putExtra("android.intent.extra.SUBJECT", "Created With #Photo Collage Editor App");
            share.putExtra("android.intent.extra.TEXT", "Created With #Photo Collage Editor App");
            share.putExtra("android.intent.extra.STREAM", Uri.fromFile(new File(this.imagePath)));
            share.setPackage(info.activityInfo.packageName);
            found = true;
            if (!found) {
                Toast.makeText(this, getString(R.string.no_facebook_app), Toast.LENGTH_SHORT).show();
            } else {
                startActivity(Intent.createChooser(share, "Select"));
            }
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(SaveImageActivity.this, CreateActivity.class));
        finish();
    }
}
