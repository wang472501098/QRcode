package com.chy.qrcode.adapter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Html;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.chy.qrcode.R;
import com.chy.qrcode.bean.BannerBean;
import com.chy.qrcode.bean.NotificationBean;
import com.chy.qrcode.bean.ToolItemBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 47250 on 2017/6/19.
 */
public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_BANNER = 101;
    private static final int TYPE_NOTICE = 102;
    private static final int TYPE_TOOLITEM = 103;
    private Context mContext;

    private List<ToolItemBean> mToolItemList;
    private List<BannerBean> mBannerList;
    private List<NotificationBean> mNoticeList;
    private LoopViewPagerAdapter mPagerAdapter;

    private MyItemClickListener mMyItemClickListener;

    public void setmMyItemClickListener(MyItemClickListener mMyItemClickListener) {
        this.mMyItemClickListener = mMyItemClickListener;
    }

    public MainAdapter(Context context) {
        this.mContext = context;
        mToolItemList = new ArrayList<>();
        mBannerList = new ArrayList<>();
        mNoticeList = new ArrayList<>();
    }

    public void setToolData(List<ToolItemBean> toolData) {
        mToolItemList.clear();
        mToolItemList.addAll(toolData);
        notifyItemRangeChanged(2, toolData.size() + 1);
    }

    public void setNoticeData(List<NotificationBean> noticeData) {
        mNoticeList.clear();
        mNoticeList.addAll(noticeData);
        notifyItemChanged(1);
    }

    public void setBannerData(List<BannerBean> bannerData) {
        mBannerList.clear();
        mBannerList.addAll(bannerData);
        notifyItemChanged(0);
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        int position = holder.getLayoutPosition();
        int type = getItemViewType(position);
        if (isFullSpanType(type)) {
            ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
            if (layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
                StaggeredGridLayoutManager.LayoutParams lp = (StaggeredGridLayoutManager.LayoutParams) layoutParams;
                lp.setFullSpan(true);
            }
        }
    }

    @Override
    public void onAttachedToRecyclerView(final RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    RecyclerView.Adapter adapter = recyclerView.getAdapter();
                    if (isFullSpanType(adapter.getItemViewType(position))) {
                        return gridLayoutManager.getSpanCount();
                    }
                    return 1;
                }
            });
        }
    }

    /**
     * 设置当前view占满列数，这样就可以占据两列实现头部了
     *
     * @param type
     * @return
     */
    private boolean isFullSpanType(int type) {
        return type == TYPE_BANNER || type == TYPE_NOTICE;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return TYPE_BANNER;
            case 1:
                return TYPE_NOTICE;
            default:
                return TYPE_TOOLITEM;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_BANNER) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_main_banner, null);
            return new HeadViewHolder(view);
        } else if (viewType == TYPE_NOTICE) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.layout_main_notice, null);
            return new NoticeViewHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_main_tool_item, null);

            return new ToolItemViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_BANNER) {
            onBindBannerHolder((HeadViewHolder) holder);
        } else if (getItemViewType(position) == TYPE_TOOLITEM) {
            onBindToolItemViewHolder((ToolItemViewHolder) holder, position);
        } else if (getItemViewType(position) == TYPE_NOTICE) {
            onBindNoticeHolder((NoticeViewHolder) holder);
        }
    }

    private void onBindNoticeHolder(NoticeViewHolder holder) {
        holder.mTvNotice.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.slide_in_bottom));
        holder.mTvNotice.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.slide_out_top));
        holder.mTvNotice.startFlipping();
        timeClock(holder);
    }

    /**
     * 公告定时轮播
     *
     * @param holder
     */
    private void timeClock(NoticeViewHolder holder) {
        if (null != mNoticeList && mNoticeList.size() > 0) {
            holder.mLayout.setVisibility(View.VISIBLE);
        } else {
            holder.mLayout.setVisibility(View.GONE);
        }
        for (NotificationBean notice : mNoticeList) {
            TextView textView = new TextView(mContext);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setSingleLine(true);
            textView.setEllipsize(TextUtils.TruncateAt.MIDDLE);
            textView.setText(Html.fromHtml(notice.getDetail()));
            holder.mTvNotice.addView(textView, lp);
        }
    }


    private void onBindToolItemViewHolder(final ToolItemViewHolder holder, final int position) {

        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        ViewGroup.LayoutParams lp;
        lp = holder.mLlItem.getLayoutParams();
        lp.width = width / 3;
        lp.height = width / 3;
        holder.mLlItem.setLayoutParams(lp);
        holder.mIvToolIcon.setImageResource(mToolItemList.get(position - 2).getImgPath());
        holder.mTvToolText.setText(mToolItemList.get(position - 2).getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMyItemClickListener.onToolItemClick(holder.itemView,position);
            }
        });
    }

    private void onBindBannerHolder(HeadViewHolder holder) {

        if (holder.mVpBanner.getAdapter() == null) {
            mPagerAdapter = new LoopViewPagerAdapter(mContext, holder.mVpBanner, holder.mIndicators);
            mPagerAdapter.setList(mBannerList);
            holder.mVpBanner.setAdapter(mPagerAdapter);
            mPagerAdapter.notifyDataSetChanged();
            holder.mVpBanner.addOnPageChangeListener(mPagerAdapter);
            if (null != mBannerList && mBannerList.size() > 0) {
                holder.mReLayBanner.setVisibility(View.VISIBLE);
            } else {
                holder.mReLayBanner.setVisibility(View.GONE);
            }
        } else {
            mPagerAdapter.setList(mBannerList);
            if (null != mBannerList && mBannerList.size() > 0) {
                holder.mReLayBanner.setVisibility(View.VISIBLE);
            } else {
                holder.mReLayBanner.setVisibility(View.GONE);
            }
        }
    }


    @Override
    public int getItemCount() {
        return mToolItemList.size() + 2;
    }


    public class ToolItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_tool_icon)
        ImageView mIvToolIcon;
        @Bind(R.id.tv_tool_text)
        TextView mTvToolText;
        @Bind(R.id.ll_itme)
        LinearLayout mLlItem;

        public ToolItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class HeadViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.vp_banner)
        ViewPager mVpBanner;
        @Bind(R.id.indicators)
        LinearLayout mIndicators;
        @Bind(R.id.reLay_Banner)
        RelativeLayout mReLayBanner;

        public HeadViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class NoticeViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.vf_notice)
        ViewFlipper mTvNotice;
        @Bind(R.id.notice_tag)
        LinearLayout mLayout;

        public NoticeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface MyItemClickListener {
        void onToolItemClick(View view, int postion);
    }

}
