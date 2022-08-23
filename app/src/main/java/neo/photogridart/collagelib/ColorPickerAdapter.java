package neo.photogridart.collagelib;

import android.annotation.SuppressLint;
import android.graphics.Color;
import androidx.recyclerview.widget.RecyclerView;

import android.renderscript.ScriptIntrinsicBLAS;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import neo.photogridart.R;
import neo.photogridart.adapters.MyRecyclerViewAdapter;
import neo.photogridart.collagelib.MyAdapter.CurrentCollageIndexChangedListener;
import java.util.ArrayList;
import java.util.List;

@SuppressLint({"InflateParams"})
public class ColorPickerAdapter extends MyRecylceAdapterBase<MyRecyclerViewAdapter.ViewHolder> implements OnClickListener {
    private static final String TAG = "Adapter";
    int colorCount = 60;
    int colorDefault;
    private List<Integer> colorList = new ArrayList();
    int colorSelected;
    String[] colors;
    CurrentCollageIndexChangedListener listener;
    RecyclerView recylceView;
    View selectedListItem;
    int selectedPosition;

    public static class ViewHolder extends MyRecyclerViewAdapter.ViewHolder {
        private static final String TAG = "ViewHolder";
        public View colorPickerView;
        private int item;
        CurrentCollageIndexChangedListener viewHolderListener;

        public void setCurrentCollageIndexChangedListener(CurrentCollageIndexChangedListener l) {
            this.viewHolderListener = l;
        }

        public void setItem(int items) {
            this.item = items;
            this.colorPickerView.setBackgroundColor(this.item);
        }

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            this.colorPickerView = itemLayoutView.findViewById(R.id.color_picker_view);
        }
    }

    public ColorPickerAdapter(CurrentCollageIndexChangedListener l, int cDefault, int cSelected) {
        String[] strArr = new String[ScriptIntrinsicBLAS.LOWER];
        strArr[0] = "#FFFFFF";
        strArr[1] = "#EFDECD";
        strArr[2] = "#CD4A4A";
        strArr[3] = "#CC6666";
        strArr[4] = "#BC5D58";
        strArr[5] = "#FF5349";
        strArr[6] = "#FD5E53";
        strArr[7] = "#FD7C6E";
        strArr[8] = "#FDBCB4";
        strArr[9] = "#FF6E4A";
        strArr[10] = "#FFA089";
        strArr[11] = "#EA7E5D";
        strArr[12] = "#B4674D";
        strArr[13] = "#A5694F";
        strArr[14] = "#FF7538";
        strArr[15] = "#FF7F49";
        strArr[16] = "#DD9475";
        strArr[17] = "#FF8243";
        strArr[18] = "#FFA474";
        strArr[19] = "#9F8170";
        strArr[20] = "#CD9575";
        strArr[21] = "#EFCDB8";
        strArr[22] = "#D68A59";
        strArr[23] = "#DEAA88";
        strArr[24] = "#FAA76C";
        strArr[25] = "#FFCFAB";
        strArr[26] = "#FFBD88";
        strArr[27] = "#FDD9B5";
        strArr[28] = "#FFA343";
        strArr[29] = "#EFDBC5";
        strArr[30] = "#FFB653";
        strArr[31] = "#E7C697";
        strArr[32] = "#8A795D";
        strArr[33] = "#FAE7B5";
        strArr[34] = "#FFCF48";
        strArr[35] = "#FCD975";
        strArr[36] = "#FDDB6D";
        strArr[37] = "#FCE883";
        strArr[38] = "#F0E891";
        strArr[39] = "#ECEABE";
        strArr[40] = "#BAB86C";
        strArr[41] = "#FDFC74";
        strArr[42] = "#FDFC74";
        strArr[43] = "#FFFF99";
        strArr[44] = "#C5E384";
        strArr[45] = "#B2EC5D";
        strArr[46] = "#87A96B";
        strArr[47] = "#A8E4A0";
        strArr[48] = "#1DF914";
        strArr[49] = "#76FF7A";
        strArr[50] = "#71BC78";
        strArr[51] = "#6DAE81";
        strArr[52] = "#9FE2BF";
        strArr[53] = "#1CAC78";
        strArr[54] = "#30BA8F";
        strArr[55] = "#45CEA2";
        strArr[56] = "#3BB08F";
        strArr[57] = "#1CD3A2";
        strArr[58] = "#17806D";
        strArr[59] = "#158078";
        strArr[60] = "#1FCECB";
        strArr[61] = "#78DBE2";
        strArr[62] = "#77DDE7";
        strArr[63] = "#80DAEB";
        strArr[64] = "#414A4C";
        strArr[65] = "#199EBD";
        strArr[66] = "#1CA9C9";
        strArr[67] = "#1DACD6";
        strArr[68] = "#9ACEEB";
        strArr[69] = "#1A4876";
        strArr[70] = "#1974D2";
        strArr[71] = "#2B6CC4";
        strArr[72] = "#1F75FE";
        strArr[73] = "#C5D0E6";
        strArr[74] = "#B0B7C6";
        strArr[75] = "#5D76CB";
        strArr[76] = "#A2ADD0";
        strArr[77] = "#979AAA";
        strArr[78] = "#ADADD6";
        strArr[79] = "#7366BD";
        strArr[80] = "#7442C8";
        strArr[81] = "#7851A9";
        strArr[82] = "#9D81BA";
        strArr[83] = "#926EAE";
        strArr[84] = "#CDA4DE";
        strArr[85] = "#8F509D";
        strArr[86] = "#C364C5";
        strArr[87] = "#FB7EFD";
        strArr[88] = "#FC74FD";
        strArr[89] = "#8E4585";
        strArr[90] = "#FF1DCE";
        strArr[91] = "#FF1DCE";
        strArr[92] = "#FF48D0";
        strArr[93] = "#E6A8D7";
        strArr[94] = "#C0448F";
        strArr[95] = "#6E5160";
        strArr[96] = "#DD4492";
        strArr[97] = "#FF43A4";
        strArr[98] = "#F664AF";
        strArr[99] = "#FCB4D5";
        strArr[100] = "#FFBCD9";
        strArr[101] = "#F75394";
        strArr[102] = "#FFAACC";
        strArr[103] = "#E3256B";
        strArr[104] = "#FDD7E4";
        strArr[105] = "#CA3767";
        strArr[106] = "#DE5D83";
        strArr[107] = "#FC89AC";
        strArr[108] = "#F780A1";
        strArr[109] = "#C8385A";
        strArr[110] = "#EE204D";
        strArr[111] = "#FF496C";
        strArr[112] = "#EF98AA";
        strArr[113] = "#FC6C85";
        strArr[114] = "#FC2847";
        strArr[115] = "#FF9BAA";
        strArr[116] = "#CB4154";
        strArr[117] = "#EDEDED";
        strArr[118] = "#DBD7D2";
        strArr[119] = "#CDC5C2";
        strArr[120] = "#95918C";
        strArr[121] = "#232323";
        this.colors = strArr;
        this.listener = l;
        this.colorDefault = cDefault;
        this.colorSelected = cSelected;
        createColorList();
    }

    private void createColorList() {
        for (String parseColor : this.colors) {
            this.colorList.add(Integer.valueOf(Color.parseColor(parseColor)));
        }
    }

    public void setSelectedPositinVoid() {
        this.selectedListItem = null;
        this.selectedPosition = -1;
    }

    public MyRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.color_recycler_view_item, null);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        viewHolder.setCurrentCollageIndexChangedListener(this.listener);
        itemLayoutView.setOnClickListener(this);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(MyRecyclerViewAdapter.ViewHolder viewHolder, int position) {
        viewHolder.setItem(((Integer) this.colorList.get(position)).intValue());
        if (this.selectedPosition == position) {
            viewHolder.itemView.setBackgroundColor(this.colorSelected);
        } else {
            viewHolder.itemView.setBackgroundColor(this.colorDefault);
        }
    }

   /* public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.setItem(((Integer) this.colorList.get(position)).intValue());
        if (this.selectedPosition == position) {
            viewHolder.itemView.setBackgroundColor(this.colorSelected);
        } else {
            viewHolder.itemView.setBackgroundColor(this.colorDefault);
        }
    }*/

    public void onAttachedToRecyclerView(RecyclerView recylceView2) {
        this.recylceView = recylceView2;
    }

    public void onClick(View view) {
        int position = this.recylceView.getChildPosition(view);
        androidx.recyclerview.widget.RecyclerView.ViewHolder oldViewHolder = this.recylceView.findViewHolderForPosition(this.selectedPosition);
        if (oldViewHolder != null) {
            View oldView = oldViewHolder.itemView;
            if (oldView != null) {
                oldView.setBackgroundColor(this.colorDefault);
            }
        }
        if (this.selectedListItem != null) {
            Log.d(TAG, "selectedListItem " + position);
        }
        Log.d(TAG, "onClick " + position);
        this.listener.onIndexChanged(((Integer) this.colorList.get(position)).intValue());
        this.selectedPosition = position;
        view.setBackgroundColor(this.colorSelected);
        this.selectedListItem = view;
    }

    public int getItemCount() {
        return this.colorList.size();
    }
}
