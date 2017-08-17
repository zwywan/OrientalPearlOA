package com.geely.app.geelyapprove.activities.home.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.ListView;

import com.geely.app.geelyapprove.common.adapter.ListAdapter;
import com.geely.app.geelyapprove.common.entity.HisDataBean;
import com.geely.app.geelyapprove.common.utils.SingletonManager;
import com.geely.app.orientalpearl.R;

import java.util.ArrayList;

public class HistoryFlowActivity extends Fragment {
    private ListView listContainer;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static HistoryFlowActivity newInstance() {
        HistoryFlowActivity history = new HistoryFlowActivity();
        return history;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (listContainer == null) {
            listContainer = new ListView(getActivity());
            listContainer.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            listContainer.setAdapter(historyAdapter);
            listContainer.setDivider(null);
            historyAdapter.notifyDataSetChanged();
            setListViewHeightBasedOnChildren(listContainer);
        }
        return listContainer;
    }

    private ListAdapter<HisDataBean.AuditNodeBean> historyAdapter = new ListAdapter<HisDataBean.AuditNodeBean>((ArrayList<HisDataBean.AuditNodeBean>) SingletonManager.getService("history"), R.layout.layout_hand_history_flow_list_item) {

        @Override
        public void bindView(ViewHolder holder, HisDataBean.AuditNodeBean obj) {
            if (holder.getItemPosition() == 0) {
                holder.setVisibility(R.id.tv_vertical_line, View.INVISIBLE);
            }
            if (historyAdapter.getCount() == holder.getItemPosition() + 1) {
                holder.setVisibility(R.id.tv_vertical_line_bottom, View.INVISIBLE);
            }
            String i = obj.getStatus();
            String prefix = "";
            ImageView ivStatus = holder.getView(R.id.iv_status);
            if (i.equals("0")) {
                //未开始
                ivStatus.setImageResource(R.drawable.ic_flow_future);
                prefix = "到达时间：";
            } else if (i.equals("1")) {
                //已完成
                ivStatus.setImageResource(R.drawable.ic_pass);
                if (holder.getItemPosition() == 0) {
                    prefix = "发起时间：";
                } else {
                    prefix = "审批时间：";
                }
            } else if (i.equals("2 ")) {
                //当前
                ivStatus.setImageResource(R.drawable.ic_flow_now);
                ivStatus.setScaleY(0.5F);
                ivStatus.setScaleX(0.5F);
                prefix = "审批时间：";
            }
            holder.setText(R.id.tv_main, obj.getNameCN());
            holder.setText(R.id.tv_create_time, prefix + obj.getCreateTime());
            holder.setText(R.id.tv_operator, obj.getApprovers());
            holder.setText(R.id.tv_action, obj.getAction());
            holder.setText(R.id.tv_comment, obj.getComment());
            holder.getView(R.id.relative_layout).animate().rotationX(360f).setStartDelay(holder.getItemPosition() * 100).setInterpolator(new OvershootInterpolator()).setDuration(1000).start();
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        SingletonManager.unregisterService("history");
    }

    protected void setListViewHeightBasedOnChildren(ListView listView) {
        if (listView == null) return;
        android.widget.ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

}
