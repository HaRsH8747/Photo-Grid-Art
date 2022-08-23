package neo.photogridart.utils;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.interstitial.InterstitialAd;

public class AdUtils {

    public static String BASE_AD_URL = "https://invisionicl.com/mobileapp/wp-json/photogrid/v1/ads";
    public static InterstitialAd mInterstitialAd = null;
    public static String applicationId = "";
    public static String interstitialAdId = "";
    public static String appOpenAdId = "";
    public static String  bannerAdId = "";
    public static String  nativeAdvanceId = "";
    public static AdRequest adRequest = null;
    public static Boolean isAdEnabled = false;
    public static boolean showedAdRecently = false;
    public static int currentCount = 0;
    public static int finalCount = 5;
}