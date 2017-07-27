package com.chy.qrcode.presenterImpl;

import android.content.Context;

import com.chy.qrcode.ui.BuildRecordActivity;
import com.chy.qrcode.ui.ChooseImageActivity;
import com.chy.qrcode.util.GetImageListUtil;

import java.util.List;

/**
 * Created by 47250 on 2017/6/24.
 */
public class BuildRecordActivityPresenter {
    private Context mContext;
    private BuildRecordActivity mBuildRecordActivity;

    public BuildRecordActivityPresenter(Context context, BuildRecordActivity buildRecordActivity) {
        this.mContext = context;
        this.mBuildRecordActivity = buildRecordActivity;
        getImageViewData();
    }

    private void getImageViewData() {
        List<String> mNoticeList = GetImageListUtil.getBuildRecordImage();
        if (mNoticeList.size() > 0) {
            mBuildRecordActivity.showImageViewData(mNoticeList);
        } else {
            mBuildRecordActivity.showNullData();
        }

    }
}
