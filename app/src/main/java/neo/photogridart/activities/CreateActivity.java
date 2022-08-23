package neo.photogridart.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdView;
import neo.photogridart.R;
import neo.photogridart.mycreation.MyCreationActivity;
import neo.photogridart.utils.AdUtils;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class CreateActivity extends AppCompatActivity {

    CardView mycreation, create;

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
                if (CreateActivity.this.mobNativeView != null) {
                    CreateActivity.this.mobNativeView.destroy();
                }
                CreateActivity.this.mobNativeView = nativeAd;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_create);
        if (AdUtils.isAdEnabled){
            NativeLoad();
        }

        mycreation = findViewById(R.id.mycreation);
        create = findViewById(R.id.create);

        mycreation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 23) {
                    String str = "android.permission.READ_EXTERNAL_STORAGE";
                    String str2 = WRITE_EXTERNAL_STORAGE;
                    String camera = "android.permission.CAMERA";
                    if (checkPermission(CreateActivity.this)) {
                        startActivity(new Intent(CreateActivity.this, MyCreationActivity.class));
                        finish();
                    } else if (checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{str, str2, camera}, 101);
                    }
                } else {
                    startActivity(new Intent(CreateActivity.this, MyCreationActivity.class));
                    finish();
                }

            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 23) {
                    String str = "android.permission.READ_EXTERNAL_STORAGE";
                    String str2 = WRITE_EXTERNAL_STORAGE;
                    String str3 = "android.permission.CAMERA";
                    if (checkPermission(CreateActivity.this)) {
                        startActivity(new Intent(CreateActivity.this, MainActivity.class));
                        finish();
                    } else if (checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{str, str2, str3}, 101);
                    }
                } else {
                    startActivity(new Intent(CreateActivity.this, MainActivity.class));
                    finish();
                }

            }
        });
    }

    public static boolean checkPermission(Context context) {


        int result1 = ContextCompat.checkSelfPermission(context, WRITE_EXTERNAL_STORAGE);
        int result2 = ContextCompat.checkSelfPermission(context, READ_EXTERNAL_STORAGE);
        int result3 = ContextCompat.checkSelfPermission(context, CAMERA);

        return result1 == PackageManager.PERMISSION_GRANTED &&
                result2 == PackageManager.PERMISSION_GRANTED &&
                result3 == PackageManager.PERMISSION_GRANTED;

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(CreateActivity.this, StartActivity.class));
        finish();
    }
}
