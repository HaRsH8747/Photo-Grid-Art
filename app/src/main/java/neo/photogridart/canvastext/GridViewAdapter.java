package neo.photogridart.canvastext;

import android.content.ClipData.Item;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import neo.photogridart.R;

public class GridViewAdapter extends ArrayAdapter<Item> {
    public Context context;
    public String[] fontPathList;
    public int layoutResourceId;
    public Typeface[] typeFaceArray;
    Typeface font;

    public GridViewAdapter(Context context2, int layoutResourceId2, String[] data) {
        super(context2, layoutResourceId2);
        this.layoutResourceId = layoutResourceId2;
        this.context = context2;
        this.fontPathList = data;
        int length = this.fontPathList.length;
        this.typeFaceArray = new Typeface[length];


        for (int i = 0; i < length; i++) {
            this.typeFaceArray[i] = FontCache.get(context2, this.fontPathList[i]);
        }
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            row = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.row_grid, parent, false);
        }

         font=Typeface.createFromAsset(context.getAssets(), this.fontPathList[position]);

        ((TextView) row.findViewById(R.id.item_text)).setTypeface(font);
        return row;
    }

    public int getCount() {
        return this.fontPathList.length;
    }
}
