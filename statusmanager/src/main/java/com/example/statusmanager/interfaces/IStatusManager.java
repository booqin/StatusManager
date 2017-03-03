package com.example.statusmanager.interfaces;

/**
 * 状态管理工具，<p>用于赞，关注等需要同步的状态 <p>
 * Created by Boqin on 2017/2/28.
 * Modified by Boqin
 *
 * @Version
 */
public interface IStatusManager {

    /**
     * 注册事件
     */
    void register();

    /**
     * 取消注册，避免内存泄露
     */
    void unregister();

}
