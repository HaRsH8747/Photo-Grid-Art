package neo.photogridart;

import android.app.Application;
import android.content.Context;

import com.google.android.gms.ads.MobileAds;
//import com.firebase.client.Firebase;
//import com.google.firebase.FirebaseApp;

public class AppCheck extends Application {

    private static AppOpenManager appOpenManager;
    private static final String TAG = AppCheck.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();

//        Firebase.setAndroidContext(this);
//        FirebaseApp.initializeApp(this);
        MobileAds.initialize(
                this,
                initializationStatus -> {});
        appOpenManager = new AppOpenManager(this);
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

    }
}