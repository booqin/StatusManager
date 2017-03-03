
# 前言
　　StatusManager用于关注，赞等状态的同步管理，从Miss项目的开发中总结出来的，进行了模块的解耦。

# 效果先行
![StatusManager](https://github.com/booqin/StatusManager/raw/master/capture/status_manager.gif)

# 关于StatusManager
　　在社交类的App，点赞，关注等操作是一个很重要的元素。而在Miss项目的开放过程中，我们也加入了对应功能，设计过程中发现，通过RecyclerView来展示列表帖子，当触发关注操作时，你需要自己维护本地的dataset和界面的更新，并且存在多个子页面的情况下，必须保持多个页面间状态的同步。在实际开放过程中，总结如下：  
　　在RecyclerView来实现的列表展示模块中，当一个状态更新时，可分两部分，即Adapter中的Dataset和RecyclerView中显示的ViewHolder，你可以通过notifyDataSetChanged等类notify方法直接来更新界面，但这样可能会带来当前显示列表出现闪屏等现象。所以需要通过两种方式去更新。另外，不同的页面下进行状态相关操作，涉及到不同线程间的状态同步。这时就需要通过EventBus来进行数据通信，同时使用Rxjava来多个线程切换处理事件，降低主线程的占用率。

# 模块的设计
todo

# 如何使用
　　StatusManager提供了多个接口，对于RecyclerView，可以参考Demo，我已经实现了RecyclerViewStatusManager类，结合RecyclerView，我们需要通过以下几步来完成该模块的部署。
- 实现IStatusBean，对应状态Bean
　　该类对于于操作的元数据，即赞，关注，评论，浏览量等状态数据。你需要自己声明一个Bean类，并且实现IStatusBean，该接口提供了匹配状态key和状态类型字段。

- 实现IStatusAdapter，对应RecyclerView中的Adapter
　　需要通过Recycler中的Adapter实现该接口，通过该接口提供dataset的相关数据，以及需要更新时的回调。

- 实现IStatusHolder，对应RecyclerView中的ViewHolder
　　提供了key，以及更新界面的回调，直接更新ViewHolder的某一view，提高效率。

- 使用RecyclerViewStatusManager  
　　传入RecyclerView实例化RecyclerViewStatusManager，在需要使用是调用mStatusManager.register()进行注册，在退出时调用mStatusManager.unregister()进行取消，避免内存泄露。
```java

    @Override
    protected void onCreate(
            @Nullable
                    Bundle savedInstanceState) {
        ……

        mRecyclerView = (RecyclerView) findViewById(R.id.rv);
        ……
        mStatusManager = new RecyclerViewStatusManager(mRecyclerView);
        mStatusManager.setAdapter(statusRVAdapter);
        mStatusManager.register();
    }

    @Override
    protected void onDestroy() {
    	……
        mStatusManager.unregister();
    }
```
- 发起更新  
　　直接通过RecyclerViewStatusManager.post(statusWrapper)发送包装好的Bean。

```java
    StatusWrapper statusWrapper = new StatusWrapper(mFollowBean);
    RecyclerViewStatusManager.post(statusWrapper);
```
