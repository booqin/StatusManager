package com.boqin.statusmanager;

import com.boqin.statusmanager.bean.FollowBean;
import com.example.statusmanager.bean.StatusWrapper;
import com.example.statusmanager.impl.RecyclerViewStatusManager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * 状态相关详情页
 * Created by Boqin on 2017/3/2.
 * Modified by Boqin
 *
 * @Version
 */
public class StatusDetailActivity extends AppCompatActivity {

    private Boolean mChecked;
    private String mId;

    private FollowBean mFollowBean;

    private TextView mDetailTV;
    private Button mButton;

    @Override
    protected void onCreate(
            @Nullable
                    Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_status);
        mChecked = getIntent().getBooleanExtra("BQ-1",false);
        mId = getIntent().getStringExtra("BQ-0");

        mDetailTV = (TextView) findViewById(R.id.detail_tv);
        mButton = (Button) findViewById(R.id.follow_bt);

        mFollowBean = new FollowBean();
        mFollowBean.setUserId(mId);
        mFollowBean.setChecked(mChecked);

        mDetailTV.setText(mId);

        mButton.setText(mChecked?"已关注":"关注");

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFollowBean.setChecked(!mFollowBean.isChecked());
                StatusWrapper statusWrapper = new StatusWrapper(mFollowBean);
                RecyclerViewStatusManager.post(statusWrapper);
                mButton.setText(mFollowBean.isChecked()?"已关注":"关注");
            }
        });

    }

}
