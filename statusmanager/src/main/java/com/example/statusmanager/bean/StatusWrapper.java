package com.example.statusmanager.bean;

import java.util.ArrayList;
import java.util.List;

import com.example.statusmanager.interfaces.IStatusBean;

/**
 * 包裹类
 * Created by Boqin on 2017/3/1.
 * Modified by Boqin
 *
 * @Version
 */
public class StatusWrapper{

    private IStatusBean wrapBean;

    private String statusKey;

    private int statusType;

    /** 在列表显示的ViewHolder位置 */
    private List<Integer> mViewHoldPosition;
    /** 对应的DataSet位置 */
    private List<Integer> mUpdatePosition;

    public StatusWrapper(IStatusBean statusBean){
        setBean(statusBean);
        setStatusKey(statusBean.getStatusKey());
        setStatusType(statusBean.getStatusType());

        mViewHoldPosition = new ArrayList<>();
        mUpdatePosition = new ArrayList<>();

    }

    public IStatusBean getBean(){
        return wrapBean;
    }

    public void setBean(IStatusBean o){
        wrapBean = o;
    }


    public List<Integer> getUpdatePosition() {
        return mUpdatePosition;
    }

    public void addUpdatePosition(int updatePosition) {
        mUpdatePosition.add(updatePosition);
    }

    public List<Integer> getViewHoldPosition() {
        return mViewHoldPosition;
    }

    public void addViewHoldPosition(int updateViewHoldPosition) {
        mViewHoldPosition.add(updateViewHoldPosition);
    }

    public String getStatusKey() {
        return statusKey;
    }

    public void setStatusKey(String statusKey) {
        this.statusKey = statusKey;
    }

    public int getStatusType() {
        return statusType;
    }

    public void setStatusType(int statusType) {
        this.statusType = statusType;
    }
}
