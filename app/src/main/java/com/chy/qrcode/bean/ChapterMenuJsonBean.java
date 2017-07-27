package com.chy.qrcode.bean;

import java.util.ArrayList;

/**
 * Created by 47250 on 2017/7/12.
 */
public class ChapterMenuJsonBean {

    private int State;
    private int LoginState;
    private String Message;
    private String AppEName;
    private String CreateTime;
    private ArrayList<ChildsBean> Childs;

    public int getState() {
        return State;
    }

    public void setState(int state) {
        State = state;
    }

    public int getLoginState() {
        return LoginState;
    }

    public void setLoginState(int loginState) {
        LoginState = loginState;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getAppEName() {
        return AppEName;
    }

    public void setAppEName(String appEName) {
        AppEName = appEName;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public ArrayList<ChildsBean> getChilds() {
        return Childs;
    }

    public void setChilds(ArrayList<ChildsBean> childs) {
        Childs = childs;
    }
}
