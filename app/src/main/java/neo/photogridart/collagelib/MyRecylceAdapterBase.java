package neo.photogridart.collagelib;


import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import neo.photogridart.adapters.MyRecyclerViewAdapter;

public class MyRecylceAdapterBase<VH extends MyRecyclerViewAdapter.ViewHolder> extends RecyclerView.Adapter<VH> {
    public int getItemCount() {
        return 0;
    }

    public void onBindViewHolder(VH vh, int arg1) {
    }

    public VH onCreateViewHolder(ViewGroup arg0, int arg1) {
        return null;
    }

    public void setSelectedPositinVoid() {
    }
}
