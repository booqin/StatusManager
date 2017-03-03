package com.boqin.statusmanager;

import java.util.ArrayList;
import java.util.List;

import com.boqin.statusmanager.adapter.StatusRVAdapter;
import com.boqin.statusmanager.bean.FollowBean;
import com.example.statusmanager.impl.RecyclerViewStatusManager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * 状态列表页
 * Created by Boqin on 2017/3/1.
 * Modified by Boqin
 *
 * @Version
 */
public class StatusActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    private List<FollowBean> mDataset;

    private RecyclerViewStatusManager mStatusManager;

    @Override
    protected void onCreate(
            @Nullable
                    Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        initDataset();

        StatusRVAdapter statusRVAdapter = new StatusRVAdapter(mDataset);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv);
        mRecyclerView.setAdapter(statusRVAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mStatusManager = new RecyclerViewStatusManager(mRecyclerView);
        mStatusManager.setAdapter(statusRVAdapter);
        mStatusManager.register();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mStatusManager.unregister();
    }

    private void initDataset() {
        mDataset = new ArrayList<>();
        for (int i=0; i<20; i++){
            FollowBean followBean = new FollowBean();
            followBean.setUserId("" + ((int) (Math.random() * 10)));
            followBean.setChecked(false);
            mDataset.add(followBean);
        }
    }

}
