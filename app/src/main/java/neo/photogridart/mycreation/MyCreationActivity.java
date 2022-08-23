package neo.photogridart.mycreation;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import neo.photogridart.activities.CreateActivity;
import neo.photogridart.activities.MainActivity;
import neo.photogridart.R;
import neo.photogridart.utils.AdUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class MyCreationActivity extends AppCompatActivity {

    private ImageAdapter adapter;
    private ProgressBar progressBar;
    public static RecyclerView recyclerView;
    public static LinearLayout tvError;
    ImageView back;
    private ArrayList<ImageModel> videos = new ArrayList();
    public static TextView create_video;


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_my_creation);
        if (AdUtils.isAdEnabled){
            BannerIDCardAds();
        }

        this.recyclerView = findViewById(R.id.recycler_view);
        tvError = findViewById(R.id.tv_error);
        this.progressBar = findViewById(R.id.progress_bar);
        this.back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        create_video = findViewById(R.id.create_video);
        create_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyCreationActivity.this, MainActivity.class));
                finish();
            }
        });

        new LoadPhotos().execute();

    }

    private class LoadPhotos extends AsyncTask<Void, Void, ArrayList> {

        protected void onPreExecute() {
            super.onPreExecute();
            videos.clear();
            MyCreationActivity.this.progressBar.setVisibility(View.VISIBLE);
        }

        protected ArrayList doInBackground(Void... voidArr) {
            AppClass.getOutputPath(MyCreationActivity.this);
            ArrayList<ImageModel> video = MyCreationActivity.this.getVideoList();
            Log.v(":::videosizeincrea", video.size() + "");
            MyCreationActivity.this.videos = video;
            return video;

        }

        protected void onPostExecute(ArrayList videolist) {
            super.onPostExecute(videolist);
            if (videolist.size() > 0) {

                recyclerView.setVisibility(View.VISIBLE);

                videos = videolist;

                Log.v(":::videolissize", videos.size() + "");
                Collections.reverse(MyCreationActivity.this.videos);


                MyCreationActivity.this.progressBar.setVisibility(View.GONE);

                recyclerView.setHasFixedSize(true);
                recyclerView.setItemViewCacheSize(30);
                recyclerView.setLayoutManager(new GridLayoutManager(MyCreationActivity.this, 1));
                recyclerView.addItemDecoration(new RVGridSpacing(2, 15, true));
                adapter = new ImageAdapter(MyCreationActivity.this, MyCreationActivity.this.videos);
                recyclerView.setAdapter(MyCreationActivity.this.adapter);
                adapter.notifyDataSetChanged();

            } else {
                tvError.setVisibility(View.VISIBLE);
                MyCreationActivity.this.recyclerView.setVisibility(View.GONE);
                MyCreationActivity.this.progressBar.setVisibility(View.GONE);
            }


        }
    }

    private ArrayList<ImageModel> getVideoList() {
        ArrayList<ImageModel> arrayList = new ArrayList();
        String outputPath = AppClass.getOutputPath(MyCreationActivity.this);
        ArrayList loadFiles = FileHelper.loadFiles(outputPath);
        for (int i = 0; i < loadFiles.size(); i++) {
            String str = (String) loadFiles.get(i);
            String substring = str.substring(0, str.lastIndexOf("."));
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(new File(str).length());
            stringBuilder.append("");
            arrayList.add(new ImageModel(substring, str, stringBuilder.toString()));
        }

        Collections.sort(arrayList, new CustomComparator());

        return arrayList;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(MyCreationActivity.this, CreateActivity.class));
        finish();
    }
}
