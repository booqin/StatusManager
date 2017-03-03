package com.example.statusmanager.impl;

import java.util.List;

import com.example.statusmanager.BaseStatusManager;
import com.example.statusmanager.bean.StatusWrapper;
import com.example.statusmanager.interfaces.IStatusAdapter;
import com.example.statusmanager.interfaces.IStatusHolder;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 适用于RecyclerView的状态管理器
 * Created by Boqin on 2017/2/28.
 * Modified by Boqin
 *
 * @Version
 */
public class RecyclerViewStatusManager extends BaseStatusManager{

    private RecyclerView mRecyclerView;

    private IStatusAdapter mAdapter;

    public RecyclerViewStatusManager(RecyclerView recyclerView){
        super();
        mRecyclerView = recyclerView;
    }

    public void setAdapter(IStatusAdapter adapter){
        mAdapter = adapter;
    }

    @Override
    public Subscription doSubscribe(StatusWrapper stateBean) {
        if (mAdapter == null ) {
            return null;
        }
        Subscription subscribe = getSubscription(stateBean);
        return subscribe;
    }

    /**
     * 设置关注事件
     *
     * @description: Created by Boqin on 2016/12/16 21:46
     */
    private Subscription getSubscription(StatusWrapper bean) {
        return Observable.just(bean)
                .observeOn(Schedulers.newThread())
                .map(new Func1<StatusWrapper, StatusWrapper>() {
                    @Override
                    public StatusWrapper call(StatusWrapper likeFollowBean) {
                        String key = likeFollowBean.getStatusKey();
                        if (mRecyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                            //1.遍历适配器中的所有数据，根据id判断是否需要更新，把需要更新的位置记录到UpdatePosition中（更新dataset）
                            for (int i = 0; i < mAdapter.getItemCount(); i++) {
                                if (mAdapter.getStatusKey(i, likeFollowBean.getStatusType())
                                        .equals(key)) {
                                    likeFollowBean.addUpdatePosition(i);
                                }
                            }
                            //2.遍历窗口可见的ViewHolder，根据id更新界面（更新viewholder）
                            for (int i = linearLayoutManager.findFirstVisibleItemPosition(); i <= linearLayoutManager.findLastVisibleItemPosition();
                                    i++) {
                                if (mRecyclerView.findViewHolderForAdapterPosition(i) instanceof IStatusHolder) {
                                    IStatusHolder statusOperation = (IStatusHolder) mRecyclerView.findViewHolderForAdapterPosition(i);
                                    if (statusOperation != null && key != null && key.equals(statusOperation.getStatusKey(likeFollowBean.getStatusType()))) {
                                        //因为存在banner
                                        //移除需要更新的ItemView的位置，直接通过VH更新的位置添加
                                        likeFollowBean.addViewHoldPosition(i - mAdapter.getHeaderCount());
                                        List list = likeFollowBean.getUpdatePosition();
                                        list.remove(list.indexOf(i - mAdapter.getHeaderCount()));
                                    }
                                }
                            }
                        }
                        return likeFollowBean;
                    }
                }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<StatusWrapper>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(StatusWrapper likeFollowBean) {
                        if (likeFollowBean == null) {
                            return;
                        }
                        IStatusHolder statusOperation;
                        for (Integer integer : (List<Integer>) likeFollowBean.getViewHoldPosition()) {
                            statusOperation = (IStatusHolder) mRecyclerView.findViewHolderForAdapterPosition(integer + mAdapter.getHeaderCount());
                            if (statusOperation != null) {
                                //3.更新ViewHolder的视图
                                statusOperation.onUpdateStatus(likeFollowBean);
                                //更新dataset
                                mAdapter.onUpdate(integer, likeFollowBean);
                            }
                        }
                        //4.更新其他同用户id的dataset并通知更新
                        for (int i = 0; i < likeFollowBean.getUpdatePosition().size(); i++) {
                            //更新dataset
                            mAdapter.onUpdate((Integer) likeFollowBean.getUpdatePosition().get(i), likeFollowBean);
                            //通知更新ItemView，有些自定义的RV更新列表的方式不一样，所以提供这个入口。
                            mAdapter.notifyItemViewChanged((Integer)likeFollowBean.getUpdatePosition().get(i) + mAdapter.getHeaderCount());
                        }

                    }
                });
    }

}
