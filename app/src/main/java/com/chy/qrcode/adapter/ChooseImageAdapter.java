package com.chy.qrcode.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ViewFlipper;

import com.chy.qrcode.R;
import com.chy.qrcode.bean.ToolItemBean;
import com.chy.qrcode.util.LogUtils;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by 47250 on 2017/6/23.
 */
public class ChooseImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_CAMERA = 201;
    private static final int TYPE_IMAGE = 202;
    private Context mContext;
    private MyItemClickListener mMyItemClickListener;
    private List<String> mImageViewList;

    public ChooseImageAdapter(Context context) {
        this.mContext = context;
        mImageViewList = new ArrayList<>();
    }

    public void setmMyItemClickListener(MyItemClickListener mMyItemClickListener) {
        this.mMyItemClickListener = mMyItemClickListener;
    }

    public void setImageViewData(List<String> imageViewData) {
        mImageViewList.clear();
        mImageViewList.addAll(imageViewData);
        notifyItemRangeChanged(1, imageViewData.size() + 1);
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return TYPE_CAMERA;
            default:
                return TYPE_IMAGE;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_CAMERA) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_chooseimage_head, null);
            return new HeadViewHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_chooseimage_imageitem, null);
            return new ImageViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_CAMERA) {
            onBindHeadHolder((HeadViewHolder) holder);
        } else if (getItemViewType(position) == TYPE_IMAGE) {
            onBindImageViewHolder((ImageViewHolder) holder, position);
        }
    }

    private void onBindHeadHolder(HeadViewHolder holder) {
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        ViewGroup.LayoutParams lp;
        lp = holder.mLlHeadItem.getLayoutParams();
        lp.width = width / 3;
        lp.height = width / 3;
        holder.mLlHeadItem.setLayoutParams(lp);
        holder.mLlHeadItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMyItemClickListener.onHeadtemClick(v);
            }
        });
    }

    private void onBindImageViewHolder(ImageViewHolder holder, final int position) {
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        ViewGroup.LayoutParams lp;
        lp = holder.mLlImageItem.getLayoutParams();
        lp.width = width / 3;
        lp.height = width / 3;
        holder.mLlImageItem.setLayoutParams(lp);
        Picasso.with(mContext).load("file://" + mImageViewList.get(position - 1))
                .config(Bitmap.Config.RGB_565)
                .resize(150, 150)
                .into(holder.mIvImageImage);

        holder.mLlImageItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMyItemClickListener.onImageItemClick(v, mImageViewList.get(position - 1));
            }
        });

    }

    @Override
    public int getItemCount() {
        return mImageViewList.size() + 1;
    }

    public class HeadViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.ll_head_itme)
        LinearLayout mLlHeadItem;
        @Bind(R.id.iv_head_image)
        ImageView mIvHeadImage;

        public HeadViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
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
        void onImageItemClick(View view, String path);

        void onHeadtemClick(View view);
    }
}
