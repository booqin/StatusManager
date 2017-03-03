package com.example.statusmanager.interfaces;

/**
 * 状态Bean接口,所有需要传递的Bean都要实现该接口
 * Created by Boqin on 2017/3/1.
 * Modified by Boqin
 *
 * @Version
 */
public interface IStatusBean {

    /** 关注类型 */
    int FOLLOW = 0;
    /** 赞类型 */
    int LIKE = 1;
    /** 评论类型 */
    int COMMENT = 2;
    /** 浏览量类型 */
    int BROWSE = 3;

    /**
     * 获取状态键值，用于匹配到ViewHolder或者dataset
     */
    String getStatusKey();

    /**
     * 获取状态类型，用于在同一个dataset中更新不同的状态
     */
    int getStatusType();
}
