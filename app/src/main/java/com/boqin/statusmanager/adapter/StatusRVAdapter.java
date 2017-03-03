package com.boqin.statusmanager.adapter;

import java.util.List;

import com.boqin.statusmanager.R;
import com.boqin.statusmanager.StatusDetailActivity;
import com.boqin.statusmanager.bean.FollowBean;
import com.example.statusmanager.bean.StatusWrapper;
import com.example.statusmanager.impl.RecyclerViewStatusManager;
import com.example.statusmanager.interfaces.IStatusAdapter;
import com.example.statusmanager.interfaces.IStatusHolder;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * RV的自定义适配器
 * Created by Boqin on 2017/3/1.
 * Modified by Boqin
 *
 * @Version
 */
public class StatusRVAdapter extends RecyclerView.Adapter implements IStatusAdapter {

    private List<FollowBean> mFollowBeanList;

    public StatusRVAdapter(List<FollowBean> list){
        mFollowBeanList = list;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_status, parent, false);
        return new StatusViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((StatusViewHolder) holder).render(mFollowBeanList.get(position));
    }

    @Override
    public int getItemCount() {
        return mFollowBeanList.size();
    }

    @Override
    public String getStatusKey(int position, int statusType) {
        return ""+mFollowBeanList.get(position).getUserId();
    }

    @Override
    public int getHeaderCount() {
        return 0;
    }

    @Override
    public void onUpdate(int position, StatusWrapper statusWrapper) {
        if (statusWrapper.getBean() instanceof FollowBean) {
            mFollowBeanList.get(position).setChecked(((FollowBean) statusWrapper.getBean()).isChecked());
        }
    }

    @Override
    public void notifyItemViewChanged(int position) {
        notifyItemChanged(position);
    }

    public class StatusViewHolder extends RecyclerView.ViewHolder implements IStatusHolder {


        private Button mFollowButton;
        private TextView mDetail;
        private Button mDetailBT;

        private FollowBean mFollowBean;

        public StatusViewHolder(View itemView) {
            super(itemView);
            mFollowButton = (Button) itemView.findViewById(R.id.follow);
            mDetail = (TextView) itemView.findViewById(R.id.detail);
            mDetailBT = (Button) itemView.findViewById(R.id.detail_bt);

            mFollowButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mFollowBean.setChecked(!mFollowBean.isChecked());
                    StatusWrapper statusWrapper = new StatusWrapper(mFollowBean);
                    RecyclerViewStatusManager.post(statusWrapper);

                }
            });

            mDetailBT.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), StatusDetailActivity.class);
                    intent.putExtra("BQ-0", mFollowBean.getUserId());
                    intent.putExtra("BQ-1", mFollowBean.isChecked());
                    v.getContext().startActivity(intent);
                }
            });
        }

        public void render(FollowBean followBean){
            mFollowBean = followBean;
            mFollowButton.setBackgroundResource(mFollowBean.isChecked()? R.color.colorAccent:R.color.colorPrimaryDark);
            mDetail.setText(""+"userId:"+mFollowBean.getUserId());
        }

        @Override
        public String getStatusKey(int statusType) {
            return ""+mFollowBean.getUserId();
        }


        @Override
        public void onUpdateStatus(StatusWrapper statusWrapper) {
            if (statusWrapper.getBean() instanceof FollowBean) {
                FollowBean followBean = (FollowBean) statusWrapper.getBean();
                mFollowBean.setChecked(followBean.isChecked());
                mFollowButton.setBackgroundResource(followBean.isChecked()?R.color.colorAccent:R.color.colorPrimaryDark);

            }
        }

    }
}
