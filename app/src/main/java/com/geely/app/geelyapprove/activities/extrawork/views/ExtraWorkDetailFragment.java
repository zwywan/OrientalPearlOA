package com.geely.app.geelyapprove.activities.extrawork.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.geely.app.geelyapprove.activities.extrawork.entity.ExtraWorkDetailEntity;
import com.geely.app.geelyapprove.common.entity.UserPhotoEntity;
import com.geely.app.geelyapprove.common.fragment.CommonFragment;
import com.geely.app.geelyapprove.common.utils.CommonValues;
import com.geely.app.geelyapprove.common.view.HandInputGroup;
import com.geely.app.geelyapprove.common.view.HandToolbar;
import com.geely.app.geelyapprove.datamanage.HttpManager;
import com.geely.app.orientalpearl.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Oliver on 2016/9/29.
 */
public class ExtraWorkDetailFragment extends CommonFragment {

    private String photo;
    private String TAG = "ExtraWorkDetailFragment";

    public ExtraWorkDetailFragment(){
    }

    private ExtraWorkDetailEntity.RetDataBean entity = null;

    public static ExtraWorkDetailFragment newInstance(Bundle bundle) {
        ExtraWorkDetailFragment fragment = new ExtraWorkDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public List<Group> getGroupList() {
        if (entity == null) return new ArrayList<>();
        List<Group> groups = new ArrayList<>();
        List<HandInputGroup.Holder> list = new ArrayList<>();
        list.add(new HandInputGroup.Holder(this.getString(R.string.Summary), true, false, entity.getDetailData().getSummary(), HandInputGroup.VALUE_TYPE.TEXT));
        list.add(new HandInputGroup.Holder("流程编号", true, false, getArguments().getString("barCode"), HandInputGroup.VALUE_TYPE.TEXT));
        List<ExtraWorkDetailEntity.RetDataBean.DetailDataBean.OverTimeDetailBean> overTimeDetail = entity.getDetailData().getOverTimeDetail();
        if(overTimeDetail != null && overTimeDetail.size() > 0){
            for (ExtraWorkDetailEntity.RetDataBean.DetailDataBean.OverTimeDetailBean bean : overTimeDetail) {
                String OTTypeName = bean.getOTTypeName();
                list.add(new HandInputGroup.Holder(OTTypeName, true, false, bean.getStartTime() + "\n" + bean.getEndTime(), HandInputGroup.VALUE_TYPE.TEXT));
            }
        }
        list.add(new HandInputGroup.Holder(this.getString(R.string.Total), true, false, entity.getDetailData().getTotalOverTime(), HandInputGroup.VALUE_TYPE.TEXT));
        Group group = new Group("流程摘要-摘要内容", null, true, null, list).setrl(true).setv1(entity.getEmpData().getNameCN() + "(" + entity.getEmpData().getNameEN() + ")").setv2(entity.getEmpData().getPositionNameCN() + " " + entity.getEmpData().getEid());
        if (photo != null && !photo.isEmpty()) {
            group.setDrawable(photo);
        }
        groups.add(group);
        groups.add(new Group("流程摘要-摘要",null,true,null,null).setValue(entity.getHisData()).setrl(false));
        List<HandInputGroup.Holder> subHolder = new ArrayList<>();
        subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Summary), true, false, entity.getDetailData().getSummary(), HandInputGroup.VALUE_TYPE.TEXT));
        subHolder.add(new HandInputGroup.Holder("流程编号", true, false, getArguments().getString("barCode"), HandInputGroup.VALUE_TYPE.TEXT));
        subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Company_Name), true, false, entity.getEmpData().getCompNameCN(), HandInputGroup.VALUE_TYPE.TEXT));
        subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Department), true, false, entity.getEmpData().getDeptNameCN(), HandInputGroup.VALUE_TYPE.TEXT));
        subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Position), true, false, entity.getEmpData().getPositionNameCN(), HandInputGroup.VALUE_TYPE.TEXT));
        subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Employee_ID), true, false, entity.getEmpData().getEid(), HandInputGroup.VALUE_TYPE.TEXT));
        subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Name), true, false, entity.getEmpData().getNameCN(), HandInputGroup.VALUE_TYPE.TEXT));
        groups.add(new Group("详细信息-" + this.getString(R.string.Basic_Information), null, true, null, subHolder).setrl(false));
        if(overTimeDetail!=null&&overTimeDetail.size()>0){
            for (int i = 0; i < overTimeDetail.size(); i++) {
                List<HandInputGroup.Holder> subDetail = new ArrayList<>();
                ExtraWorkDetailEntity.RetDataBean.DetailDataBean.OverTimeDetailBean bean = overTimeDetail.get(i);
                subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Overtime_category), true, false, bean.getOTTypeName(), HandInputGroup.VALUE_TYPE.TEXT));
                subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Starting_Time), true, false, bean.getStartTime(), HandInputGroup.VALUE_TYPE.TEXT));
                subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Ending_Time), true, false, bean.getEndTime(), HandInputGroup.VALUE_TYPE.TEXT));
                subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Overtime_Hours), true, false, bean.getHours() + "小时", HandInputGroup.VALUE_TYPE.TEXT));
                subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Overtime_Reason), true, false, bean.getReason(), HandInputGroup.VALUE_TYPE.TEXT));
                if (entity.getIsKeyAuditNodeForDisplay().isHRBP()){
                    subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Overtime_Type), true, false, bean.getOTTypesName(), HandInputGroup.VALUE_TYPE.TEXT));
                }
                groups.add(new Group("详细信息-" + this.getString(R.string.Details_Information), null, true, null, subDetail).setrl(false));
            }
            List<HandInputGroup.Holder> holderTotal = new ArrayList<>();
            groups.add(new Group("详细信息-" + this.getString(R.string.Total), null, true, entity.getDetailData().getTotalOverTime(), holderTotal).setrl(false));
        }
        return groups;
    }

    public void setToolbar(HandToolbar toolbar) {
        toolbar.setTitle(getArguments().getString("ProcessNameCN"));
        toolbar.setDisplayHomeAsUpEnabled(true, getActivity());
    }

    @Override
    public String[] getBottomButtonsTitles() {
        return null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadData();
    }

    private void loadData() {
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
                            setEntity(entity.getRetData());
                            setGroup(getGroupList());
                            setPb(false);
                            setButtonllEnable(true);
                            setDisplayTabs(true);
                            notifyDataSetChanged();
                            return;
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
        param.put("uid",getArguments().getString("SubmitBy"));
        HttpManager.getInstance().requestResultForm(CommonValues.GET_USER_PHOTO, param, UserPhotoEntity.class, new HttpManager.ResultCallback<UserPhotoEntity>() {
            @Override
            public void onSuccess(String content, final UserPhotoEntity entity) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (entity != null && entity.getRetData() != null) {
                            if (entity.getCode().equals("100")) {
                                photo = entity.getRetData();
                                if (getGroup().size() > 0){
                                    getGroup().get(0).setDrawable(photo);
                                    notifyGroupChanged(0,1);
                                }
                                return;
                            }
                        }
                    }

                });

            }

            @Override
            public void onFailure(String content) {

            }

        });

    }

    public ExtraWorkDetailEntity.RetDataBean getEntity() {
        return entity;
    }

    public void setEntity(ExtraWorkDetailEntity.RetDataBean entity) {
        this.entity = entity;
    }

}
