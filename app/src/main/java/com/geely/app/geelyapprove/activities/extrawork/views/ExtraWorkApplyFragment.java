package com.geely.app.geelyapprove.activities.extrawork.views;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.geely.app.geelyapprove.activities.HistoryActivity;
import com.geely.app.geelyapprove.activities.extrawork.entity.ExtraWorkDetailEntity;
import com.geely.app.geelyapprove.common.application.GeelyApp;
import com.geely.app.geelyapprove.common.entity.DataListEntity;
import com.geely.app.geelyapprove.common.entity.DictionaryEntity;
import com.geely.app.geelyapprove.common.fragment.CommonFragment;
import com.geely.app.geelyapprove.common.utils.CommonValues;
import com.geely.app.geelyapprove.common.utils.DataUtil;
import com.geely.app.geelyapprove.common.utils.DescripUtil;
import com.geely.app.geelyapprove.common.utils.ToastUtil;
import com.geely.app.geelyapprove.common.view.HandInputGroup;
import com.geely.app.geelyapprove.common.view.HandToolbar;
import com.geely.app.geelyapprove.datamanage.HttpManager;
import com.geely.app.orientalpearl.R;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**加班申请
 * Created by Oliver on 2016/9/21.
 */
public class ExtraWorkApplyFragment extends CommonFragment{

    private boolean IsDeputy = false;
    private List<DataListEntity> OverTimeType;
    private ExtraWorkDetailEntity bean;
    private String uuid;
    private String str;

    public ExtraWorkApplyFragment() {

    }

    public static ExtraWorkApplyFragment newInstance() {
        ExtraWorkApplyFragment fragment = new ExtraWorkApplyFragment();
        return fragment;
    }
    public static ExtraWorkApplyFragment newInstance(Bundle bundle) {
        ExtraWorkApplyFragment fragment = new ExtraWorkApplyFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public List<Group> getGroupList() {
        List<Group> groups = new ArrayList<>();
        if(bean == null){
            List<HandInputGroup.Holder> subHolder = new ArrayList<>();
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Company_Name), false, false, GeelyApp.getLoginEntity().getRetData().getUserInfo().getCompNameCN(), HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Department), false, false, GeelyApp.getLoginEntity().getRetData().getUserInfo().getDeptNameCN(), HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Employee_ID), false, false, GeelyApp.getLoginEntity().getRetData().getUserInfo().getEid(), HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Name), false, false, GeelyApp.getLoginEntity().getRetData().getUserInfo().getNameCN(), HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Position), false, false, GeelyApp.getLoginEntity().getRetData().getUserInfo().getPositionNameCN(), HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
            groups.add(new Group(this.getString(R.string.Basic_Information), null, true, null, subHolder));
            List<HandInputGroup.Holder> subHolder1 = new ArrayList<>();
            subHolder1.add(new HandInputGroup.Holder(this.getString(R.string.Overtime_category), true, false, "/" + this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.SELECT));
            subHolder1.add(new HandInputGroup.Holder(this.getString(R.string.Starting_Time), true, false, "/" + this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.DATE));
            subHolder1.add(new HandInputGroup.Holder(this.getString(R.string.Ending_Time), true, false, "/" + this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.DATE));
            subHolder1.add(new HandInputGroup.Holder(this.getString(R.string.Overtime_Hours), true, false, "/小时", HandInputGroup.VALUE_TYPE.DOUBLE).setColor(Color.rgb(229,0,17)));
            subHolder1.add(new HandInputGroup.Holder(this.getString(R.string.Overtime_Reason), false, false, "/" + this.getString(R.string.Please_Fill), HandInputGroup.VALUE_TYPE.TEXTFILED));
            groups.add(new Group(this.getString(R.string.Details_Information), R.mipmap.add_detail3x, true, null, subHolder1).sethasDelete(true));
            List<HandInputGroup.Holder> subHolder2 = new ArrayList<>();
            groups.add(new Group(this.getString(R.string.Total), null, true, "0小时", subHolder2));
        }else {
            ExtraWorkDetailEntity.RetDataBean.DetailDataBean detailData = bean.getRetData().getDetailData();
            List<ExtraWorkDetailEntity.RetDataBean.DetailDataBean.OverTimeDetailBean> overTimeDetail = detailData.getOverTimeDetail();
            IsDeputy = detailData.isIsDeputy();
            List<HandInputGroup.Holder> subHolder = new ArrayList<>();
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Company_Name), false, false, GeelyApp.getLoginEntity().getRetData().getUserInfo().getCompNameCN(), HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Department), false, false, GeelyApp.getLoginEntity().getRetData().getUserInfo().getDeptNameCN(), HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Employee_ID), false, false, GeelyApp.getLoginEntity().getUserId(), HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Name), false, false, GeelyApp.getLoginEntity().getRetData().getUserInfo().getNameCN(), HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Position), false, false, GeelyApp.getLoginEntity().getRetData().getUserInfo().getPositionNameCN(), HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
            groups.add(new Group(this.getString(R.string.Basic_Information), null, true, null, subHolder));
            for (int i = 0; i < overTimeDetail.size(); i++) {
                List<HandInputGroup.Holder> subHolder1 = new ArrayList<>();
                subHolder1.add(new HandInputGroup.Holder(this.getString(R.string.Overtime_category), true, false, overTimeDetail.get(i).getOTTypeName(), HandInputGroup.VALUE_TYPE.SELECT));
                subHolder1.add(new HandInputGroup.Holder(this.getString(R.string.Starting_Time), true, false, overTimeDetail.get(i).getStartTime(), HandInputGroup.VALUE_TYPE.DATE));
                subHolder1.add(new HandInputGroup.Holder(this.getString(R.string.Ending_Time), true, false, overTimeDetail.get(i).getEndTime(), HandInputGroup.VALUE_TYPE.DATE));
                subHolder1.add(new HandInputGroup.Holder(this.getString(R.string.Overtime_Hours), true, false, overTimeDetail.get(i).getHours(), HandInputGroup.VALUE_TYPE.DOUBLE).setColor(Color.rgb(229,0,17)));
                subHolder1.add(new HandInputGroup.Holder(this.getString(R.string.Overtime_Reason), false, false, overTimeDetail.get(i).getReason(), HandInputGroup.VALUE_TYPE.TEXTFILED));
                if (i == overTimeDetail.size() - 1) {
                    groups.add(new Group(this.getString(R.string.Details_Information), R.mipmap.add_detail3x, true, null, subHolder1).sethasDelete(true));
                } else {
                    groups.add(new Group(this.getString(R.string.Details_Information), null, true, null, subHolder1).sethasDelete(true));
                }
            }
            List<HandInputGroup.Holder> subHolder2 = new ArrayList<>();
            groups.add(new Group(this.getString(R.string.Total), null, true, detailData.getTotalOverTime(), subHolder2));

        }
        return groups;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadData();
    }

    private void loadData() {
        if (getArguments() != null){
            Map<String, Object> param = CommonValues.getCommonParams(getActivity());
            param.put("userId",getArguments().getString("userId"));
            param.put("barCode", getArguments().getString("barCode"));
            param.put("workflowType", getArguments().getString("workflowType"));
            HttpManager.getInstance().requestResultForm(CommonValues.REQ_EXTRAWORK_DETAIL, param, ExtraWorkDetailEntity.class, new HttpManager.ResultCallback<ExtraWorkDetailEntity>() {
                @Override
                public void onSuccess(String content, final ExtraWorkDetailEntity entity) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (entity != null && entity.getCode().equals("100")) {
                                bean = entity;
                                setGroup(getGroupList());
                                if (getArguments().getBoolean("Remak")){
                                    settoolbar();
                                    setButtonsTitles(new String[]{"重新提交"});
                                }
                                setPb(false);
                                setButtonllEnable(true);
                                notifyDataSetChanged();
                            } else {
                                Toast.makeText(getActivity(), entity.getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }

                @Override
                public void onFailure(String content) {

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "请检查网络", Toast.LENGTH_SHORT).show();
                            getActivity().finish();
                        }
                    });
                }
            });
        }

    }

    private void settoolbar() {
        HandToolbar toolbar = getToolbar();
        final View his = toolbar.findViewById(R.id.tv_history);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                his.setVisibility(View.VISIBLE);
                his.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HistoryActivity.startActivity(getActivity(),bean.getRetData().getHisData());
                    }
                });
            }
        });
    }

    @Override
    public void setToolbar(HandToolbar toolbar) {
        toolbar.setTitle("加班审批");
        toolbar.setDisplayHomeAsUpEnabled(true, getActivity());
        loadDraftListData();
    }

    @Override
    public String[] getBottomButtonsTitles() {
        return new String[]{"提交", "保存"};
    }


    /**
     * @param title  传出的点击的参数
     * @param groups 传出的所有数据
     */
    @SuppressWarnings("unchecked")
    @Override
    public void onBottomButtonsClick(String title, final List<Group> groups) {
        setButtonllEnable(false);
        if (title.equals("保存")){
            ButtonClick(title,groups);
        }else {
            String over = isOver(groups);
            if (over != null){
                ToastUtil.showToast(getContext(),this.getString(R.string.Please_Fill) + over);
                setButtonllEnable(true);
                return;
            }else {
                ButtonClick(title,groups);
            }
        }
    }

    private void ButtonClick(String title, List<Group> groups) {
        List<Group> groupsByTitle = getGroupsByTitle(this.getString(R.string.Details_Information));
        for (Group group : groupsByTitle) {
            long times1 = getTimes(group.getHolders().get(1).getRealValue().isEmpty()?"0":group.getHolders().get(1).getRealValue());
            long times2 = getTimes(group.getHolders().get(2).getRealValue().isEmpty()?"0":group.getHolders().get(2).getRealValue());
            if(!title.equals("保存") && (Double.parseDouble(group.getHolders().get(3).getRealValue().isEmpty()?"0":group.getHolders().get(3).getRealValue())%4 != 0)){
                ToastUtil.showToast(getContext(),"加班时长必须是4的整数倍！");
                setButtonllEnable(true);
                return;
            }
            if (!title.equals("保存") && (times2-times1)/1000/3600<Double.parseDouble(group.getHolders().get(3).getRealValue().isEmpty()?"0":group.getHolders().get(3).getRealValue())){
                ToastUtil.showToast(getContext(),"填写小时数不能超过所选时间！");
                setButtonllEnable(true);
                return;
            }
        }
        Map<String, Object> body = CommonValues.getCommonParams(getActivity());
        Map<String, Object> mainData = new HashMap<String, Object>();
        if (bean == null) {
            uuid = UUID.randomUUID().toString();
            str = DescripUtil.formatDate(new Date());
            body.put("SN", "");
        } else {
            uuid = bean.getRetData().getDetailData().getIdentifier();
            str = bean.getRetData().getDetailData().getCreateTime();
            mainData.put("BarCode", bean.getRetData().getDetailData().getBarCode());
            mainData.put("Id", bean.getRetData().getDetailData().getId());
            body.put("SN", getArguments().getString("SN"));
        }
        mainData.put("Identifier", uuid);
        mainData.put("SubmitBy", GeelyApp.getLoginEntity().getUserId());
        mainData.put("UpdateBy",GeelyApp.getLoginEntity().getUserId());
        mainData.put("CreateTime", str);
        mainData.put("UpdateTime", DescripUtil.formatDate(new Date()));
        String topRightTitle = null;
        if(!IsDeputy){
            topRightTitle = getGroupsByTitle(this.getString(R.string.Total)).get(0).getGroupTopRightTitle();
        }else{
            topRightTitle = null;
        }
        String s = str.split(" ")[0];
        if (topRightTitle != null) {
            if (topRightTitle.endsWith("小时")) {
                if (topRightTitle.substring(0, topRightTitle.length() - 2).isEmpty() || Double.parseDouble(topRightTitle.substring(0, topRightTitle.length() - 2)) == 0) {
                    mainData.put("TotalOverTime", "0");
                } else {
                    mainData.put("TotalOverTime", topRightTitle.substring(0, topRightTitle.length() - 4));//10
                }
                mainData.put("Summary", s.split("-")[0] + s.split("-")[1] + s.split("-")[2] + "-" + GeelyApp.getLoginEntity().getRetData().getUserInfo().getDeptNameCN() + GeelyApp.getLoginEntity().getRetData().getUserInfo().getNameCN()
                        + "-" + topRightTitle + "加班");//14
            }else{
                if (Double.parseDouble(topRightTitle) == 0) {
                    mainData.put("TotalOverTime", "0");
                } else {
                    mainData.put("TotalOverTime", topRightTitle);//10
                }
                mainData.put("Summary", s.split("-")[0] + s.split("-")[1] + s.split("-")[2] + "-" + GeelyApp.getLoginEntity().getRetData().getUserInfo().getDeptNameCN() + GeelyApp.getLoginEntity().getRetData().getUserInfo().getNameCN()
                        + "-" + topRightTitle + "加班");//14
            }
        }else{
            mainData.put("Summary", s.split("-")[0] + s.split("-")[1] + s.split("-")[2] + "-" + GeelyApp.getLoginEntity().getRetData().getUserInfo().getDeptNameCN() + GeelyApp.getLoginEntity().getRetData().getUserInfo().getNameCN()
                    + "-0小时加班");//14
        }
        mainData.put("Applicant", GeelyApp.getLoginEntity().getRetData().getUserInfo().getNameCN());
        mainData.put("IsDeputy", IsDeputy);
        mainData.put("UserID", GeelyApp.getLoginEntity().getUserId());
        String j = new Gson().toJson(mainData);
        body.put("mainData", j);
        List<Object> objects = new ArrayList<Object>();
        if (!IsDeputy) {
            for (int i = 1; i < groups.size() - 1; i++) {
                String startTime = groups.get(i).getHolders().get(1).getRealValue();
                String endTime = groups.get(i).getHolders().get(2).getRealValue();
                Map<String, Object> detailData = new HashMap<String, Object>();
                detailData.put("WorkflowIdentifier", uuid);
                detailData.put("Id",0);
                detailData.put("StartTime", startTime);
                detailData.put("EndTime", endTime);
                String OTType = groups.get(i).getHolders().get(0).getRealValue();
                String dicodeByDescr = DataUtil.getDicodeByDescr(OverTimeType, OTType);
                detailData.put("OTType", dicodeByDescr);
                detailData.put("OTTypeName", OTType);
                detailData.put("Reason", groups.get(i).getHolders().get(4).getRealValue());
                detailData.put("Hours", groups.get(i).getHolders().get(3).getRealValue());
                objects.add(detailData);
            }
        }
        String json = new Gson().toJson(objects);
        applySaveOrStart(CommonValues.REQ_EXTRAWORK_APPLY, body, title);
    }

    @Override
    public void onHolderTextChanged(int main, int index, HandInputGroup.Holder holder) {
        final String key = holder.getKey();
        if (key.equals(this.getString(R.string.Overtime_Hours))){
            double sum = 0;
            List<Group> groupsByTitle = getGroupsByTitle(this.getString(R.string.Details_Information));
            for (Group group : groupsByTitle) {
                String realValue = group.getHolders().get(3).getRealValue();
                String str = realValue.isEmpty()?"0":realValue;
                double v = Double.parseDouble(str);
                sum +=v;
            }
            getGroupsByTitle(this.getString(R.string.Total)).get(0).setGroupTopRightTitle(sum + "小时");
            notifyGroupChanged(getGroup().size()-1, 1);
        }

    }

    @Override
    public void onDataChanged(HandInputGroup.Holder holder) throws ParseException {
        Group group = getGroup().get(getitemnum(holder));
        if (holder.getKey().equals(this.getString(R.string.Starting_Time))){
            HandInputGroup.Holder holder1 = group.getHolders().get(2);
            if (!holder1.getRealValue().isEmpty()){
                String starttime = holder.getRealValue();
                String overtime = holder1.getRealValue();
                int getday = getday(starttime, overtime);
                if (getday==-1){
                    holder.setDispayValue("/请填入时间");
                    ToastUtil.showToast(getContext(),"请正确选择\n第" + getitemnum(holder) + "条明细信息中的开始、结束时间");
                }else{

                }
            }
        }else if (holder.getKey().equals(this.getString(R.string.Ending_Time))){
            HandInputGroup.Holder holder1 = group.getHolders().get(1);
            if (!holder1.getRealValue().isEmpty()){
                String starttime = holder1.getRealValue();
                String overtime = holder.getRealValue();
                int getday = getday(starttime, overtime);
                if (getday==-1){
                    holder.setDispayValue("/请填入时间");
                    ToastUtil.showToast(getContext(),"请正确选择\n第" + getitemnum(holder) + "条明细信息中的开始、结束时间");
                }else{

                }
            }
        }
    }

    @Override
    public void onClickItemContentSetter(final HandInputGroup.Holder holder) {
        if (holder.getType() == HandInputGroup.VALUE_TYPE.DATE) {
            showDateTimePicker(holder,true);
        }else if(holder.getKey().equals(this.getString(R.string.Overtime_category))){
            showSelector(holder, DataUtil.getDescr(OverTimeType));
        }
    }

    @Override
    public void onOneItemBottomDrawableResClick(int index) {
        List<HandInputGroup.Holder> subHolder1 = new ArrayList<>();
        subHolder1.add(new HandInputGroup.Holder(this.getString(R.string.Overtime_category), true, false, "/" + this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.SELECT));
        subHolder1.add(new HandInputGroup.Holder(this.getString(R.string.Starting_Time), true, false, "/" + this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.DATE));
        subHolder1.add(new HandInputGroup.Holder(this.getString(R.string.Ending_Time), true, false, "/" + this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.DATE));
        subHolder1.add(new HandInputGroup.Holder(this.getString(R.string.Overtime_Hours), true, false, "/小时", HandInputGroup.VALUE_TYPE.DOUBLE).setColor(Color.rgb(229,0,17)));
        subHolder1.add(new HandInputGroup.Holder(this.getString(R.string.Overtime_Reason), false, false, "/" + this.getString(R.string.Please_Fill), HandInputGroup.VALUE_TYPE.TEXTFILED));
        addGroup(index + 1, new Group(this.getString(R.string.Details_Information), R.mipmap.add_detail3x, true, null, subHolder1).sethasDelete(true));
        getGroup().get(index).setDrawableRes(null);
        notifyDataSetChanged();
    }

    private void loadDraftListData() {
        HttpManager manager = HttpManager.getInstance();
        Map<String, Object> params = CommonValues.getCommonParams(getActivity());
        //加班类别
        params.put("code",7284);
        manager.requestResultForm(CommonValues.GET_DICTIONARY_LIST, params, DictionaryEntity.class, new HttpManager.ResultCallback<DictionaryEntity>() {
            @Override
            public void onSuccess(String content,DictionaryEntity expenseTypeEntity) {
                OverTimeType = expenseTypeEntity.getRetData().get(0).getDataList();
            }

            @Override
            public void onFailure(String content) {

            }
        });

    }
    public int getday(String leave, String returnt) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        long LeaveTime = 0;
        long ReturnTime = 0;
        try {
            LeaveTime = sdf.parse(leave).getTime();
            ReturnTime = sdf.parse(returnt).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (ReturnTime <= LeaveTime) {
            return -1;
        } else {
            int day = (int) ((ReturnTime - LeaveTime) / 1000 / 60 / 60 / 24) + 1;
            return day;
        }

    }

}
