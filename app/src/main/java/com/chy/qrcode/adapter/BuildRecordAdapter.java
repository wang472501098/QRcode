package com.chy.qrcode.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chy.qrcode.R;
import com.chy.qrcode.util.GetImageListUtil;
import com.chy.qrcode.view.BuildContextDialog;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by 47250 on 2017/6/23.
 */
public class BuildRecordAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private MyItemClickListener mMyItemClickListener;
    private List<String> mImageViewList;

    public BuildRecordAdapter(Context context) {
        this.mContext = context;
        mImageViewList = new ArrayList<>();
    }

    public void setmMyItemClickListener(MyItemClickListener mMyItemClickListener) {
        this.mMyItemClickListener = mMyItemClickListener;
    }

    public void setImageViewData(List<String> imageViewData) {
        mImageViewList.clear();
        mImageViewList.addAll(imageViewData);
        notifyItemRangeChanged(1, imageViewData.size());
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_chooseimage_imageitem, null);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        onBindImageViewHolder((ImageViewHolder) holder, position);
    }

    private void onBindImageViewHolder(ImageViewHolder holder, final int position) {
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        ViewGroup.LayoutParams lp;
        lp = holder.mLlImageItem.getLayoutParams();
        lp.width = width / 3;
        lp.height = width / 3;
        holder.mLlImageItem.setLayoutParams(lp);
        Picasso.with(mContext).load("file://" + mImageViewList.get(position))
                .config(Bitmap.Config.RGB_565)
                .resize(150, 150)
                .into(holder.mIvImageImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMyItemClickListener.onImageItemClick(v, position);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                BuildContextDialog buildContextDialog = new BuildContextDialog(mContext,
                        mImageViewList.get(position), BuildRecordAdapter.this);
                buildContextDialog.show();
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return mImageViewList.size();
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.ll_image_itme)
        LinearLayout mLlImageItem;
        @Bind(R.id.iv_image_image)
        ImageView mIvImageImage;

        public ImageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface MyItemClickListener {
        void onImageItemClick(View view, int path);

    }
}
