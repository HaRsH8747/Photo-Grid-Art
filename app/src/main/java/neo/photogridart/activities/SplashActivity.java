package neo.photogridart.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import neo.photogridart.R;
import neo.photogridart.utils.AdUtils;


public class SplashActivity extends AppCompatActivity {

    InterstitialAd mMobInterstitialAds;

    public void InterstitialLoad() {
        AdRequest adRequest = new AdRequest.Builder().build();
//        RequestConfiguration configuration = new RequestConfiguration.Builder().setTestDeviceIds(Arrays.asList("1ADAD30F02CD84CDE72190C2ABE5EB5E")).build();
//        MobileAds.setRequestConfiguration(configuration);
        InterstitialAd.load(getApplicationContext(), AdUtils.interstitialAdId, adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                SplashActivity.this.mMobInterstitialAds = interstitialAd;
                interstitialAd.setFullScreenContentCallback(
                        new FullScreenContentCallback() {
                            @Override
                            public void onAdDismissedFullScreenContent() {

                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {

                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                            }
                        });
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
            }
        });
    }

    private void ShowFunUAds() {
        if (this.mMobInterstitialAds != null) {
            this.mMobInterstitialAds.show(SplashActivity.this);
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(1024, 124);
        setContentView((int) R.layout.activity_splash);
        if (AdUtils.isAdEnabled){
            InterstitialLoad();
        }
        new Handler().postDelayed(new Runnable() {
            public void run() {
                if (isNetworkConnected()) {
                    startActivity(new Intent(SplashActivity.this, StartActivity.class));
                    ShowFunUAds();
                    finish();
                } else {
                    Toast.makeText(SplashActivity.this, "Please Check your connectivity", Toast.LENGTH_SHORT).show();
                }
            }
        }, 2000);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }


}
