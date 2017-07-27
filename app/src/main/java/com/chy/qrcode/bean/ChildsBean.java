package com.chy.qrcode.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by 47250 on 2017/7/12.
 */
public class ChildsBean implements Serializable {
    private int ID;
    private String Name;
    private String NodeType;
    private int DoneNum;
    private int TestNum;
    private String IsHide;
    private ArrayList<ChildsBean> Childs;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getNodeType() {
        return NodeType;
    }

    public void setNodeType(String nodeType) {
        NodeType = nodeType;
    }

    public int getDoneNum() {
        return DoneNum;
    }

    public void setDoneNum(int doneNum) {
        DoneNum = doneNum;
    }

    public int getTestNum() {
        return TestNum;
    }

    public void setTestNum(int testNum) {
        TestNum = testNum;
    }

    public String getIsHide() {
        return IsHide;
    }

    public void setIsHide(String isHide) {
        IsHide = isHide;
    }

    public ArrayList<ChildsBean> getChilds() {
        return Childs;
    }

    public void setChilds(ArrayList<ChildsBean> childs) {
        Childs = childs;
    }
}
