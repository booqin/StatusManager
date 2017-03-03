package com.example.statusmanager.interfaces;

import com.example.statusmanager.bean.StatusWrapper;

/**
 * 关注，赞，收藏状态操作接口，优化更新效率，对应RecyclerView中的ViewHolder
 * Created by Boqin on 2016/11/30.
 * Modified by Boqin
 *
 * @Version
 */
public interface IStatusHolder {

    /**
     * 帖子Id
     *
     * @description: Created by Boqin on 2016/12/7 12:55
     * @param statusType 状态类型
     */
    String getStatusKey(int statusType);

    /**
     * 更新关注操作
     *
     * @description: Created by Boqin on 2016/12/7 12:56
     */
    void onUpdateStatus(StatusWrapper statusWrapper);

}
