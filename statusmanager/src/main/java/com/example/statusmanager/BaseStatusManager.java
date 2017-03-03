package com.example.statusmanager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import com.example.statusmanager.bean.StatusWrapper;
import com.example.statusmanager.interfaces.IStatusManager;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * 状态管理器基类
 * Created by Boqin on 2017/2/28.
 * Modified by Boqin
 *
 * @Version
 */
public abstract class BaseStatusManager implements IStatusManager {

    /** 复合订阅，可取消订阅 */
    protected CompositeSubscription mSubscriptions;

    public BaseStatusManager(){
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void register() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void unregister() {
        EventBus.getDefault().unregister(this);
        mSubscriptions.unsubscribe();
    }

    /**
     * 发起更新请求
     * @param statusWrapper 包裹类
     */
    public static void post(StatusWrapper statusWrapper) {
        EventBus.getDefault().post(statusWrapper);
    }

    /**
     * 监听状态更新入口
     */
    @Subscribe
    public void onEventMainThread(StatusWrapper statusWrapper) {
        Subscription subscribe;
        subscribe = doSubscribe(statusWrapper);
        if (subscribe == null) {
            return;
        }
        mSubscriptions.add(subscribe);
    }

    /**
     * 订阅事件，处理数据
     */
    public abstract Subscription doSubscribe(StatusWrapper statusWrapper);

}
