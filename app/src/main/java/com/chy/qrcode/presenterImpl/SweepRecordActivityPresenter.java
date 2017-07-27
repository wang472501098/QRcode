package com.chy.qrcode.presenterImpl;

import android.content.Context;

import com.chy.qrcode.Mapp;
import com.chy.qrcode.dao.SWEEP_RECORD;
import com.chy.qrcode.ui.BuildRecordActivity;
import com.chy.qrcode.ui.SweepRecordActivity;
import com.chy.qrcode.util.GetImageListUtil;

import java.util.List;

/**
 * Created by 47250 on 2017/6/24.
 */
public class SweepRecordActivityPresenter {
    private Context mContext;
    private SweepRecordActivity mSweepRecordActivity;

    public SweepRecordActivityPresenter(Context context, SweepRecordActivity sweepRecordActivity) {
        this.mContext = context;
        this.mSweepRecordActivity = sweepRecordActivity;
        getImageViewData();
    }

    private void getImageViewData() {
        List<SWEEP_RECORD> mSweepRecordList = Mapp.getInstances().getDaoSession().getSWEEP_RECORDDao().loadAll();
        if (mSweepRecordList.size() > 0) {
            mSweepRecordActivity.showImageViewData(mSweepRecordList);
        } else {
            mSweepRecordActivity.showNullData();
        }
    }
}
