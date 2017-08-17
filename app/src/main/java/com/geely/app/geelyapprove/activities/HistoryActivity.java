package com.geely.app.geelyapprove.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.geely.app.geelyapprove.common.entity.HisDataBean;
import com.geely.app.geelyapprove.common.view.HandToolbar;
import com.geely.app.orientalpearl.R;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhy on 2017/1/17.
 */

public class HistoryActivity extends AppCompatActivity {

    public static final String HISTORY = "HISTORY";
    private HandToolbar toobar;
    private ListView list;
    private List<HisDataBean.AuditNodeBean> auditNodeBeen;
    private HisDataBean his;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_activity);
        initView();
        initContent();
        list.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return auditNodeBeen.size();
            }

            @Override
            public Object getItem(int position) {
                return auditNodeBeen.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                //getView核心代码
                ViewHolder viewHolder;
                if(convertView == null){
                    convertView = View.inflate(getApplicationContext(),R.layout.layout_hand_history_flow_list_item, null);
                    viewHolder = new ViewHolder(convertView);
                    viewHolder.ivStatus = (ImageView) convertView.findViewById(R.id.iv_status);
                    viewHolder.tvMain = (TextView) convertView.findViewById(R.id.tv_main);
                    viewHolder.tvCreateTime = (TextView) convertView.findViewById(R.id.tv_create_time);
                    viewHolder.tvOperator = (TextView) convertView.findViewById(R.id.tv_operator);
                    viewHolder.tvAction = (TextView) convertView.findViewById(R.id.tv_action);
                    viewHolder.tvComment = (TextView) convertView.findViewById(R.id.tv_comment);
                    convertView.setTag(viewHolder);
                }else{
                    viewHolder = (ViewHolder) convertView.getTag();
                }
                if (position == 0) {
                    viewHolder.viewVerticalLine.setVisibility(View.INVISIBLE);
                }else {
                    viewHolder.viewVerticalLine.setVisibility(View.VISIBLE);
                }
                if (getCount() == position+1) {
                    viewHolder.viewVerticalLineBottom.setVisibility(View.INVISIBLE);
                }else {
                    viewHolder.viewVerticalLineBottom.setVisibility(View.VISIBLE);
                }
                String i = auditNodeBeen.get(position).getStatus();
                if (i.equals("1")){
                    viewHolder.ivStatus.setImageResource(R.drawable.ic_pass);
                    viewHolder.tvAction.setTextColor(0xff6ABB66);
                }else if (i.equals("2")){
                    viewHolder.ivStatus.setImageResource(R.drawable.ic_flow_now);
                    viewHolder.ivStatus.setScaleY(0.5F);
                    viewHolder.ivStatus.setScaleX(0.5F);
                }else if (i.equals("0")){
                    viewHolder.ivStatus.setImageResource(R.drawable.ic_flow_future);
                }else if (i.equals("3")){
                    viewHolder.ivStatus.setImageResource(R.drawable.ic_flow_reject);
                    viewHolder.tvAction.setTextColor(Color.RED);
                }

                viewHolder.tvOperator.setText( auditNodeBeen.get(position).getApprovers());
                viewHolder.tvMain.setText( auditNodeBeen.get(position).getNameCN());
                viewHolder.tvCreateTime.setText(auditNodeBeen.get(position).getCreateTime());
                viewHolder.tvAction.setText( auditNodeBeen.get(position).getAction());
                viewHolder.tvComment.setText( auditNodeBeen.get(position).getComment());
                return convertView;
            }
            class ViewHolder {
                ImageView ivStatus;
                TextView tvMain, tvCreateTime, tvOperator, tvAction, tvComment;
                View viewVerticalLine, viewVerticalLineBottom;
                LinearLayout rlContainer;

                public ViewHolder(View itemView) {
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
        });


    }

    private void initContent() {
        Intent intent = getIntent();
        his = (HisDataBean) intent.getSerializableExtra(HISTORY);
        auditNodeBeen = his.getAuditNode();
    }

    private void initView() {
        toobar = (HandToolbar) findViewById(R.id.toolbar);
        toobar.setTitle("审批历史");
        toobar.setDisplayHomeAsUpEnabled(true, this);
        toobar.setBackHome(true,this);
        list = (ListView) findViewById(R.id.fl_container);
    }

    public static void startActivity(Activity content, HisDataBean his) {
        Intent intent = new Intent();
        intent.setClass(content,HistoryActivity.class);
        intent.putExtra(HISTORY,(Serializable)his);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        content.startActivity(intent);
    }
}
