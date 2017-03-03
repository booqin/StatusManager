package com.example.statusmanager.interfaces;

import com.example.statusmanager.bean.StatusWrapper;

/**
 * 用于适配器，获取一些基础数据以及更新数据源
 * Created by Boqin on 2017/2/28.
 * Modified by Boqin
 *
 * @Version
 */
public interface IStatusAdapter {

    /**
     * 获取item数量
     */
    int getItemCount();

    /**
     * 获取对应ID，用于判断是否更新
     */
    String getStatusKey(int position, int statusType);

    /**
     * 获取头部数量
     */
    int getHeaderCount();

    /**
     * 更新数据
     * @param position 在数据集中的位置
     */
    void onUpdate(int position, StatusWrapper statusWrapper);

    /**
     * 更新列表View，用于某些不再当前界面显示列表View
     * @param position 更新的列表位置
     */
    void notifyItemViewChanged(int position);
}
