package neo.photogridart;

import android.app.Activity;
import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;
import neo.photogridart.utils.AdUtils;

import static androidx.lifecycle.Lifecycle.Event.ON_START;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AppOpenManager implements LifecycleObserver, Application.ActivityLifecycleCallbacks {
    private static final String LOG_TAG = "AppOpenManager";
    private AppOpenAd appOpenAd = null;
    private AppOpenAd.AppOpenAdLoadCallback loadCallback;
    private final AppCheck myApplication;
    private Activity currentActivity;
    private static boolean isShowingAd = false;

    class RequestApi extends AsyncTask<Void,Void,String> {
        @Override
        protected String doInBackground(Void... strings) {
            StringBuilder builder = new StringBuilder();
            try {
                String link = AdUtils.BASE_AD_URL;
                URL url = new URL(link);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while (true)
                {
                    String data = reader.readLine();
                    if (data == null){
                        break;
                    }
                    builder.append(data);
                }
            }catch (Exception e){
                e.printStackTrace();
                Log.d("CLEAR",e.getMessage());
                return "Error! " + e.getMessage();
            }
            return builder.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                int status = jsonObject.getInt("status");
                AdUtils.isAdEnabled = status == 1;
                if (AdUtils.isAdEnabled){
                    AdUtils.appOpenAdId =jsonObject.getString("app_open");
                    AdUtils.interstitialAdId =jsonObject.getString("interstitial");
                    AdUtils.bannerAdId =jsonObject.getString("banner");
                    AdUtils.applicationId =jsonObject.getString("app_id");
                    AdUtils.nativeAdvanceId =jsonObject.getString("native_advanced");
                    try {
                        ApplicationInfo ai = myApplication.getPackageManager().getApplicationInfo(myApplication.getPackageName(), PackageManager.GET_META_DATA);
                        Bundle bundle = ai.metaData;
                        String myApiKey = bundle.getString("com.google.android.gms.ads.APPLICATION_ID");
                        Log.d("CLEAR", "Name Found: " + myApiKey);
                        ai.metaData.putString("com.google.android.gms.ads.APPLICATION_ID", AdUtils.applicationId);//you can replace your key APPLICATION_ID here
                        String ApiKey = bundle.getString("com.google.android.gms.ads.APPLICATION_ID");
                        Log.d("CLEAR", "ReNamed Found: " + ApiKey);
                    } catch (PackageManager.NameNotFoundException e) {
                        Log.e("CLEAR", "Failed to load meta-data, NameNotFound: " + e.getMessage());
                    } catch (NullPointerException e) {
                        Log.e("CLEAR", "Failed to load meta-data, NullPointer: " + e.getMessage());
                    }
                    fetchAd();
                }
            }
            catch (Exception e){
                e.printStackTrace();
                Log.d("CLEAR","ggg " +e.getMessage());
            }
        }
    }

    public void showAdIfAvailable() {
        if (!isShowingAd && isAdAvailable()) {
            Log.d(LOG_TAG, "Will show ad.");
            FullScreenContentCallback fullScreenContentCallback = new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    // Set the reference to null so isAdAvailable() returns false.
                    AppOpenManager.this.appOpenAd = null;
                    isShowingAd = false;
                }

                @Override
                public void onAdFailedToShowFullScreenContent(AdError adError) {
                }

                @Override
                public void onAdShowedFullScreenContent() {
                    isShowingAd = true;
                }
            };
            appOpenAd.setFullScreenContentCallback(fullScreenContentCallback);
            appOpenAd.show(currentActivity);
        } else {
            Log.d(LOG_TAG, "Can not show ad.");
            fetchAd();
        }
    }

    public AppOpenManager(AppCheck myApplication) {
        this.myApplication = myApplication;
        this.myApplication.registerActivityLifecycleCallbacks(this);
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
    }

    public void fetchAd() {
        if (isAdAvailable()) {
            return;
        }
        loadCallback =
                new AppOpenAd.AppOpenAdLoadCallback() {
                    @Override
                    public void onAdLoaded(AppOpenAd ad) {
                        AppOpenManager.this.appOpenAd = ad;
                        showAdIfAvailable();
                    }

                    @Override
                    public void onAdFailedToLoad(LoadAdError loadAdError) {
                    }

                };
        AdRequest request = getAdRequest();
        AppOpenAd.load(
                myApplication, AdUtils.appOpenAdId, request,
                AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, loadCallback);

    }

    private AdRequest getAdRequest() {
        return new AdRequest.Builder().build();
    }

    public boolean isAdAvailable() {
        return appOpenAd != null;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
    }

    @Override
    public void onActivityStarted(Activity activity) {
        currentActivity = activity;
    }

    @Override
    public void onActivityResumed(Activity activity) {
        currentActivity = activity;
    }

    @Override
    public void onActivityStopped(Activity activity) {
    }

    @Override
    public void onActivityPaused(Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        currentActivity = null;
    }

    @OnLifecycleEvent(ON_START)
    public void onStart() {
        new RequestApi().execute();
//        showAdIfAvailable();
        Log.d(LOG_TAG, "onStart");
    }

}
