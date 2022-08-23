package neo.photogridart.mycreation;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import neo.photogridart.R;

import java.io.File;
import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<ImageModel> videoList;

    public ImageAdapter(Context context, ArrayList<ImageModel> arrayList) {
        Log.v(":::arrayinadapter",arrayList.size()+"");
        this.context = context;
        this.videoList = arrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.card_image, viewGroup, false);
        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder myViewHolder, @SuppressLint("RecyclerView") final int i) {
        final String path = this.videoList.get(i).getPath();
        CharSequence title = this.videoList.get(i).getTitle();
        String size = this.videoList.get(i).getSize();


        Log.d(":::RESPONE", path+ "   "+title +"   "+size);

        Glide.with(context).load(path).centerCrop().placeholder(R.color.black).into(myViewHolder.thumbnail);
        myViewHolder.thumbnail.setScaleType(ImageView.ScaleType.FIT_XY);

        myViewHolder.tv_title.setText(FileHelper.getFileName(path));
        myViewHolder.tv_size.setText(FileHelper.formatFileSize(Long.parseLong(size)));

        myViewHolder.card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, ImageDisplayActivity.class);
                i.putExtra("imageToShare-uri", path);
                context.startActivity(i);

            }
        });

        myViewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.getWindow().requestFeature(1);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                dialog.setContentView(R.layout.dialog_delete);
                Button no = dialog.findViewById(R.id.iv_no);
                Button yes = dialog.findViewById(R.id.iv_yes);

                no.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                yes.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {

                        AppClass.deleteFileFromMediaStore(context,context.getContentResolver(),new File(path));

                        Toast.makeText(context, "Image Deleted Successfully..", Toast.LENGTH_SHORT).show();
                        videoList.remove(i);
                        notifyItemRemoved(i);
                        notifyItemRangeChanged(i, videoList.size());

                        if (videoList.isEmpty())
                        {
                            MyCreationActivity.tvError.setVisibility(View.VISIBLE);
                            MyCreationActivity.recyclerView.setVisibility(View.GONE);
                        }
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        myViewHolder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    if (android.os.Build.VERSION.SDK_INT >= 23) {

                        Intent share = new Intent(Intent.ACTION_SEND);
                        share.setType("image/*");
                        File imageFileToShare = new File(path);
                        Uri uri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", imageFileToShare);
                        share.putExtra(Intent.EXTRA_STREAM, uri);
                        share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        context.startActivity(Intent.createChooser(share, "Share to"));

                    } else {
                        try {
                            Intent intent = new Intent("android.intent.action.SEND");
                            intent.setType("image/*");
                            intent.putExtra(Intent.EXTRA_STREAM, path);
                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            context.startActivity(Intent.createChooser(intent, "Share to"));
                        } catch (Exception unused) {
                            Toast.makeText(context, "You can not share image to this app..", Toast.LENGTH_SHORT).show();
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.videoList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout card;
        RelativeLayout card2;
        ImageView thumbnail,delete,share;
        TextView tv_size;
        TextView tv_title;

        public MyViewHolder(@NonNull View view) {
            super(view);
            thumbnail = view.findViewById(R.id.img_smoke);
            delete = view.findViewById(R.id.delete);
            share = view.findViewById(R.id.share);
            card = view.findViewById(R.id.card);
            card2 = view.findViewById(R.id.card2);
            tv_title = view.findViewById(R.id.tv_title);
            tv_title.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            tv_title.setSingleLine(true);
            tv_title.setMarqueeRepeatLimit(-1);
            tv_title.setSelected(true);
            tv_size = view.findViewById(R.id.tv_size);
        }

    }
}
