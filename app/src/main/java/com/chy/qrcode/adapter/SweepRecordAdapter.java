package com.chy.qrcode.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.chy.qrcode.R;
import com.chy.qrcode.dao.SWEEP_RECORD;
import com.chy.qrcode.ui.BrowserActivity;
import com.chy.qrcode.view.SweepContextDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by 47250 on 2017/6/23.
 */
public class SweepRecordAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private MyItemClickListener mMyItemClickListener;
    private List<SWEEP_RECORD> mSweepRecordList;

    public SweepRecordAdapter(Context context) {
        this.mContext = context;
        mSweepRecordList = new ArrayList<>();
    }

    public void setmMyItemClickListener(MyItemClickListener mMyItemClickListener) {
        this.mMyItemClickListener = mMyItemClickListener;
    }

    public void setImageViewData(List<SWEEP_RECORD> sweepRecordList) {
        mSweepRecordList.clear();
        mSweepRecordList.addAll(sweepRecordList);
        notifyItemRangeChanged(1, sweepRecordList.size());
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_sweep_record_item, null);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        onBindImageViewHolder((ImageViewHolder) holder, position);
    }

    private void onBindImageViewHolder(ImageViewHolder holder, final int position) {
        if (1 == mSweepRecordList.get(position).getTpye()) {
            holder.tvContext.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
            holder.tvContext.getPaint().setAntiAlias(true);//抗锯齿
            holder.tvContext.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, BrowserActivity.class);
                    intent.putExtra("linkUrl", mSweepRecordList.get(position).getContext());
                    mContext.startActivity(intent);
                }
            });
        } else {
            holder.tvContext.setTextColor(mContext.getResources().getColor(R.color.gray_text));
        }
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                SweepContextDialog sweepContextDialog = new SweepContextDialog(mContext, mSweepRecordList.get(position).getId(), mSweepRecordList.get(position).getContext(),SweepRecordAdapter.this);
                sweepContextDialog.show();
                return true;
            }
        });
        holder.tvContext.setText(mSweepRecordList.get(position).getContext());
        holder.tvTime.setText(mSweepRecordList.get(position).getCreate_time());
    }

    @Override
    public int getItemCount() {
        return mSweepRecordList.size();
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_context)
        TextView tvContext;
        @Bind(R.id.tv_time)
        TextView tvTime;

        public ImageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface MyItemClickListener {
        void onImageItemClick(View view, String path);

    }
}
