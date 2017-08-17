package com.geely.app.geelyapprove.common.fragment;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.geely.app.geelyapprove.common.entity.HisDataBean;
import com.geely.app.orientalpearl.R;

import java.util.List;

/**
 * Created by Oliver on 2016/12/1.
 */
public class HistortFlowAdapter extends RecyclerView.Adapter<HistortFlowAdapter.HistoryHolder>{

    private HisDataBean data;
    private Context context;
    private final List<HisDataBean.AuditNodeBean> auditNode;

    public HistortFlowAdapter(Context context, HisDataBean data){
        this.data = data;
        auditNode = this.data.getAuditNode();
//        for (int i = 0; i < auditNode.size(); i++) {
//            if (auditNode.get(i).getStatus().equals("1")){
//                auditNode.remove(i);
//                i--;
//            }else if (auditNode.get(i).getStatus().equals("2") && auditNode.get(i).getNameCN().equals("资金平台")){
//                auditNode.remove(i);
//                auditHis.remove(auditHis.size()-1);
//            }
//        }
//        if (auditNode.size() > 0){
//            for (HisDataBean.AuditNodeBean auditNodeBean : auditNode) {
//                HisDataBean.AuditHisBean auditHisBean = new HisDataBean.AuditHisBean();
//                auditHisBean.setCreateTime(auditNodeBean.getCreateTime());
//                auditHisBean.setOperatorName(auditNodeBean.getApprovers());
//                String status = auditNodeBean.getStatus();
//                if (status.equals("0")){
//                    auditHisBean.setAction("未开始");
//                }else if (status.equals("2")){
//                    auditHisBean.setAction("待审核");
//                }
//                auditHisBean.setActivityNameCN(auditNodeBean.getNameCN());
//                auditHis.add(auditHisBean);
//            }
//        }
        this.context = context;
    }

    @Override
    public HistortFlowAdapter.HistoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout inflate = (LinearLayout) View.inflate(context, R.layout.layout_hand_history_flow_list_item, null);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
        inflate.setLayoutParams(params);
        inflate.setOrientation(LinearLayout.HORIZONTAL);
        return new HistoryHolder(inflate);
    }

    @Override
    public void onBindViewHolder(HistortFlowAdapter.HistoryHolder holder, int position) {
        HisDataBean.AuditNodeBean auditNodeBean = auditNode.get(position);
        String status = auditNodeBean.getStatus();
        if (status.equals("1")){
            holder.ivStatus.setImageResource(R.drawable.ic_pass);
            holder.tvAction.setTextColor(0xff6ABB66);
        }else if (status.equals("2")){
            holder.ivStatus.setImageResource(R.drawable.ic_flow_now);
            holder.ivStatus.setScaleY(0.5F);
            holder.ivStatus.setScaleX(0.5F);
        }else if (status.equals("0")){
            holder.ivStatus.setImageResource(R.drawable.ic_flow_future);
        }else if (status.equals("3") ){
            holder.ivStatus.setImageResource(R.drawable.ic_flow_reject);
            holder.tvAction.setTextColor(Color.RED);
        }
        if (position == 0) {
            holder.viewVerticalLine.setVisibility(View.INVISIBLE);
        }else {
            holder.viewVerticalLine.setVisibility(View.VISIBLE);
        }
        if (getItemCount() == position+1) {
            holder.viewVerticalLineBottom.setVisibility(View.INVISIBLE);
        }else {
            holder.viewVerticalLineBottom.setVisibility(View.VISIBLE);
        }
        holder.tvOperator.setText( auditNodeBean.getApprovers());
        holder.tvMain.setText( auditNodeBean.getNameCN());
        holder.tvCreateTime.setText(auditNodeBean.getCreateTime());
        holder.tvAction.setText( auditNodeBean.getAction());
        holder.tvComment.setText( auditNodeBean.getComment());
    }

    @Override
    public int getItemCount() {
        return auditNode.size();
    }


    static class HistoryHolder extends RecyclerView.ViewHolder{
        ImageView ivStatus;
        TextView tvMain,tvCreateTime,tvOperator,tvAction,tvComment;
        View viewVerticalLine,viewVerticalLineBottom;
        LinearLayout rlContainer;
        public HistoryHolder(View itemView) {
            super(itemView);
            ivStatus = (ImageView) itemView.findViewById(R.id.iv_status);
            tvMain = (TextView) itemView.findViewById(R.id.tv_main);
            tvCreateTime = (TextView) itemView.findViewById(R.id.tv_create_time);
            tvOperator = (TextView) itemView.findViewById(R.id.tv_operator);
            tvAction = (TextView) itemView.findViewById(R.id.tv_action);
            tvComment = (TextView) itemView.findViewById(R.id.tv_comment);
            viewVerticalLine = itemView.findViewById(R.id.tv_vertical_line);
            viewVerticalLineBottom = itemView.findViewById(R.id.tv_vertical_line_bottom);
            rlContainer = (LinearLayout) itemView.findViewById(R.id.relative_layout);
        }
    }
}
