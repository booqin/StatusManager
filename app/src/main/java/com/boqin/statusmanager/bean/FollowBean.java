package com.boqin.statusmanager.bean;

import com.example.statusmanager.interfaces.IStatusBean;

/**
 * 关注，需要传递的Bean
 * Created by Boqin on 2017/3/1.
 * Modified by Boqin
 *
 * @Version
 */
public class FollowBean implements IStatusBean {

    private String userId;

    private boolean checked;


    @Override
    public String getStatusKey() {
        return userId;
    }

    @Override
    public int getStatusType() {
        return FOLLOW;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
