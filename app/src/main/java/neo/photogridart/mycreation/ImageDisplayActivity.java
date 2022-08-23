package neo.photogridart.mycreation;


import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import neo.photogridart.BuildConfig;
import neo.photogridart.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ImageDisplayActivity extends AppCompatActivity {

    Bitmap imageBitmap;
    RelativeLayout facebook, instagram, multipleshare, delete;
    ImageView finalimg, mycreation, back;
    Uri imageuri;

    LinearLayout adContainerView;
    AdView adViewone;

    public void BannerIDCardAds() {
        adContainerView = findViewById(R.id.adsmultyViews);
        adViewone = new AdView(getApplicationContext());
        adViewone.setAdUnitId(getString(R.string.Admob_Banner));
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_share);
        BannerIDCardAds();

        this.finalimg = findViewById(R.id.finalimg);
        this.facebook = findViewById(R.id.iv_facebook);
        this.instagram = findViewById(R.id.iv_instragram);
        this.multipleshare = findViewById(R.id.iv_more);
        mycreation = findViewById(R.id.mycreation);
        delete = findViewById(R.id.delete);
        back = findViewById(R.id.back);
        mycreation.setVisibility(View.GONE);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ImageDisplayActivity.this, MyCreationActivity.class));
                finish();
            }
        });

        imageuri = Uri.parse(getIntent().getStringExtra("imageToShare-uri"));
        finalimg.setImageURI(imageuri);
        InputStream inputStream = null;
        try {
            inputStream = getContentResolver().openInputStream(imageuri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.imageBitmap = BitmapFactory.decodeStream(inputStream);
        this.multipleshare.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ImageDisplayActivity.this.ShareImagetoanotherapp(v);
            }
        });

        this.instagram.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ImageDisplayActivity.this.Instagram(v);
            }
        });

        this.facebook.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ImageDisplayActivity.this.Facebook(v);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(ImageDisplayActivity.this);
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

                        /*File file = new File(String.valueOf(imageuri));
                        if (file.exists()) {
                            file.delete();
                        }*/

                        AppClass.deleteFileFromMediaStore(ImageDisplayActivity.this, getContentResolver(), new File(String.valueOf(imageuri)));

                        Toast.makeText(ImageDisplayActivity.this, "Image Deleted Successfully", Toast.LENGTH_SHORT).show();

                        dialog.dismiss();
                        Intent i = new Intent(ImageDisplayActivity.this, MyCreationActivity.class);
                        startActivity(i);
                        finish();

                    }
                });
                dialog.show();
            }
        });


    }

    public void ShareImagetoanotherapp(View view) {

        try {
            if (Build.VERSION.SDK_INT >= 23) {

                Log.v(":::shareuri", Uri.parse(getIntent().getStringExtra("imageToShare-uri")) + "");
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("image/*");
                File imageFileToShare = new File(String.valueOf(Uri.parse(getIntent().getStringExtra("imageToShare-uri"))));
                Uri uri = FileProvider.getUriForFile(ImageDisplayActivity.this, ImageDisplayActivity.this.getApplicationContext().getPackageName() + ".provider", imageFileToShare);
                share.putExtra(Intent.EXTRA_STREAM, uri);
                share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(Intent.createChooser(share, "Share to"));

            } else {
                try {
                    Intent intent = new Intent("android.intent.action.SEND");
                    intent.setType("image/*");
                    intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(getIntent().getStringExtra("imageToShare-uri")));
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    ImageDisplayActivity.this.startActivity(Intent.createChooser(intent, "Share to"));
                } catch (Exception unused) {
                    Toast.makeText(ImageDisplayActivity.this, "You can not share image to this app..", Toast.LENGTH_SHORT).show();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Facebook(View view) {

        if (appInstalledOrNot("com.facebook.katana")) {

            try {
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("image/*");
                share.setPackage("com.facebook.katana");
                share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                if (Build.VERSION.SDK_INT >= 23) {
                    Log.v(":::shareuri", getIntent().getStringExtra("imageToShare-uri") + "");
                    File imageFileToShare = new File(getIntent().getStringExtra("imageToShare-uri"));
                    Uri uri = FileProvider.getUriForFile(ImageDisplayActivity.this, BuildConfig.APPLICATION_ID + ".provider", imageFileToShare);
                    share.setPackage("com.facebook.katana");
                    share.putExtra(Intent.EXTRA_STREAM, uri);
                    startActivity(Intent.createChooser(share, "Share to Facebook"));
                } else {
                    try {
                        share.putExtra(Intent.EXTRA_STREAM, Uri.parse(String.valueOf(Uri.parse(getIntent().getStringExtra("imageToShare-uri")))));
                        ImageDisplayActivity.this.startActivity(Intent.createChooser(share, "Share to Facebook"));
                    } catch (Exception unused) {
                        Toast.makeText(ImageDisplayActivity.this, "You can not share image to this app..", Toast.LENGTH_SHORT).show();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            Toast.makeText(ImageDisplayActivity.this, "Facebook App is not installed", Toast.LENGTH_LONG).show();
            return;
        }

    }

    public void Instagram(View view) {

        if (appInstalledOrNot("com.instagram.android")) {

            try {
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setPackage("com.instagram.android");
                share.setType("image/*");
                share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                if (Build.VERSION.SDK_INT >= 23) {
                    Log.v(":::shareuri", Uri.parse(getIntent().getStringExtra("imageToShare-uri")) + "");
                    File imageFileToShare = new File(String.valueOf(Uri.parse(getIntent().getStringExtra("imageToShare-uri"))));
                    Uri uri = FileProvider.getUriForFile(ImageDisplayActivity.this, BuildConfig.APPLICATION_ID + ".provider", imageFileToShare);
                    share.putExtra(Intent.EXTRA_STREAM, uri);
                    startActivity(Intent.createChooser(share, "Share to Instagram"));
                } else {
                    try {
                        share.putExtra(Intent.EXTRA_STREAM, Uri.parse(getIntent().getStringExtra("imageToShare-uri")));
                        ImageDisplayActivity.this.startActivity(Intent.createChooser(share, "Share to Instagram"));
                    } catch (Exception unused) {
                        Toast.makeText(ImageDisplayActivity.this, "You can not share image to this app..", Toast.LENGTH_SHORT).show();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(ImageDisplayActivity.this, "Instagram App is not installed", Toast.LENGTH_LONG).show();
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

    public void onDestroy() {
        super.onDestroy();
    }

}
