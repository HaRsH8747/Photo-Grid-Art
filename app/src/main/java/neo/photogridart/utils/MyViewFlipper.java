package neo.photogridart.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ViewFlipper;

public class MyViewFlipper extends ViewFlipper {
    public MyViewFlipper(Context context) {
        super(context);
    }

    public MyViewFlipper(Context context, AttributeSet attributeset) {
        super(context, attributeset);
    }

    public void onDetachedFromWindow() {
        try {
            super.onDetachedFromWindow();
        } catch (IllegalArgumentException e) {
            stopFlipping();
        }
    }
}
