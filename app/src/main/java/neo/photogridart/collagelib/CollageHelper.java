package neo.photogridart.collagelib;

import android.content.Intent;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.View;
import neo.photogridart.gallerylib.GalleryFragment;

public class CollageHelper {
    protected static final String TAG = "CollageHelper";

    public static GalleryFragment getGalleryFragment(FragmentActivity activity) {
        return (GalleryFragment) activity.getSupportFragmentManager().findFragmentByTag("myFragmentTag");
    }

    public static GalleryFragment addGalleryFragment(FragmentActivity activity, int gallery_fragment_container, boolean showInter, View view) {
        FragmentManager fm = activity.getSupportFragmentManager();
        GalleryFragment galleryFragment = (GalleryFragment) fm.findFragmentByTag("myFragmentTag");
        if (galleryFragment == null) {
            GalleryFragment galleryFragment2 = new GalleryFragment();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(gallery_fragment_container, galleryFragment2, "myFragmentTag");
            ft.commitAllowingStateLoss();
            galleryFragment2.setGalleryListener(createGalleryListener(activity, galleryFragment2, showInter, view));
            activity.findViewById(gallery_fragment_container).bringToFront();
            return galleryFragment2;
        }
        activity.getSupportFragmentManager().beginTransaction().show(galleryFragment).commitAllowingStateLoss();
        return galleryFragment;
    }

    public static GalleryFragment.GalleryListener createGalleryListener(FragmentActivity activity, GalleryFragment galleryFragment, boolean showInter, View view) {
        final View view2 = view;
        final FragmentActivity fragmentActivity = activity;
        final GalleryFragment galleryFragment2 = galleryFragment;
        final boolean z = showInter;
        return new GalleryFragment.GalleryListener() {
            public void onGalleryCancel() {
                if (!(view2 == null || view2.getVisibility() == View.VISIBLE)) {
                    view2.setVisibility(View.VISIBLE);
                }
                fragmentActivity.getSupportFragmentManager().beginTransaction().hide(galleryFragment2).commitAllowingStateLoss();

            }

            public void onGalleryOkImageArray(long[] jArr, int[] iArr, boolean x, boolean y) {
                if (!(view2 == null || view2.getVisibility() == View.VISIBLE)) {
                    view2.setVisibility(View.VISIBLE);
                }
                Intent localIntent = new Intent(fragmentActivity, CollageActivity.class);
                localIntent.putExtra("photo_id_list", jArr);
                localIntent.putExtra("photo_orientation_list", iArr);
                localIntent.putExtra("is_scrap_book", x);
                localIntent.putExtra("is_shape", y);
                fragmentActivity.startActivity(localIntent);
            }

            public void onGalleryOkImageArrayRemoveFragment(long[] jArt, int[] iArr, boolean x, boolean y) {
            }

            public void onGalleryOkSingleImage(long j, int i, boolean x, boolean y) {
            }
        };
    }
}
