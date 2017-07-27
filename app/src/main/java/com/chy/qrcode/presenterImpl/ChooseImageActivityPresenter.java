package com.chy.qrcode.presenterImpl;

import android.content.Context;

import com.chy.qrcode.ui.ChooseImageActivity;
import com.chy.qrcode.util.GetImageListUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 47250 on 2017/6/23.
 */
public class ChooseImageActivityPresenter {
    private Context mContext;
    private ChooseImageActivity mChooseImageActivity;

    public ChooseImageActivityPresenter(Context context, ChooseImageActivity chooseImageActivity) {
        this.mContext = context;
        this.mChooseImageActivity = chooseImageActivity;
        getImageViewData();
    }

    private void getImageViewData() {
        List<String> mNoticeList = GetImageListUtil.getAllImage();
        mChooseImageActivity.showImageViewData(mNoticeList);
    }
}

