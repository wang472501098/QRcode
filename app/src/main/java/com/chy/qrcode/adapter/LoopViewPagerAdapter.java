package com.chy.qrcode.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.chy.qrcode.R;
import com.chy.qrcode.bean.BannerBean;
import com.chy.qrcode.util.LogUtils;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.List;


public class LoopViewPagerAdapter extends BaseLoopPagerAdapter {
    private final List<BannerBean> mBanners;
    private final ViewGroup mIndicators;
    private int mLastPosition;
    private Context context;

    public LoopViewPagerAdapter(Context context, ViewPager viewPager, ViewGroup indicators) {
        super(viewPager);
        this.context = context;
        mIndicators = indicators;
        mBanners = new ArrayList<>();
    }


    public void setList(List<BannerBean> banners) {
        mBanners.clear();
        mBanners.addAll(banners);
        notifyDataSetChanged();
    }

    private void initIndicators() {
        if (mIndicators.getChildCount() != mBanners.size() && mBanners.size() > 1) {
            mIndicators.removeAllViews();
            Resources res = mIndicators.getResources();
            int size = res.getDimensionPixelOffset(R.dimen.height_8dp);
            int margin = res.getDimensionPixelOffset(R.dimen.indicator_margin);
            for (int i = 0; i < getPagerCount(); i++) {
                ImageView indicator = new ImageView(mIndicators.getContext());
                indicator.setAlpha(180);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(size, size);
                lp.setMargins(margin, 0, 0, 0);
                lp.gravity = Gravity.CENTER;
                indicator.setLayoutParams(lp);
                Drawable drawable = res.getDrawable(R.drawable.selector_indicator);
                indicator.setImageDrawable(drawable);
                mIndicators.addView(indicator);
            }
        }
    }

    @Override
    public void notifyDataSetChanged() {
        initIndicators();
        super.notifyDataSetChanged();
    }

    @Override
    public int getPagerCount() {
        return mBanners.size();
    }

    @Override
    public BannerBean getItem(int position) {
        return mBanners.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_detail_vp, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final BannerBean banner = mBanners.get(position);

        // holder.adIv.setImageResource(banner.getImgUrl());
        Picasso.with(context).load(banner.getImgUrl()).into(holder.adIv);
        holder.adIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                Uri content_url = Uri.parse(banner.getDetail());
                intent.setData(content_url);
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    @Override
    public void onPageItemSelected(int position) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            mIndicators.getChildAt(mLastPosition).setActivated(false);
            mIndicators.getChildAt(position).setActivated(true);
        }
        mLastPosition = position;
    }

    public static class ViewHolder {
        ImageView adIv;

        public ViewHolder(View view) {
            adIv = (ImageView) view.findViewById(R.id.iv_banner);
        }
    }
}
