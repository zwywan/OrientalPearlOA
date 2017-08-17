package com.geely.app.geelyapprove.activities.postbusinesstrip.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.geely.app.geelyapprove.activities.postbusinesstrip.bean.PostBusinessTripEntity;
import com.geely.app.geelyapprove.common.activity.InputActivity;
import com.geely.app.geelyapprove.common.activity.InputHolder;
import com.geely.app.geelyapprove.common.entity.CommonDataEntity;
import com.geely.app.geelyapprove.common.utils.CommonValues;
import com.geely.app.geelyapprove.common.utils.ToastUtil;
import com.geely.app.geelyapprove.common.view.HandInputGroup;
import com.geely.app.geelyapprove.datamanage.HttpManager;
import com.geely.app.orientalpearl.R;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Oliver on 2016/9/29.
 */

public class BTripApproveFragment extends DisplayFragment {

    HandInputGroup.Holder mholder;
    private boolean attendance;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getBottomTitles();
    }

    public static BTripApproveFragment newInstance(Bundle args) {
        BTripApproveFragment fragment = new BTripApproveFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public List<Group> getGroupList() {
        List<Group> groupList = super.getGroupList();
        PostBusinessTripEntity.RetDataBean entity = getEntity();
        attendance = entity.getIsKeyAuditNodeForApprove().isAttendance();
        if(attendance){
            List<HandInputGroup.Holder> holderList1 = groupList.get(0).getHolders();
            holderList1.add(new HandInputGroup.Holder("实际离开时间", true, false, "/" + this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.DATE));
            holderList1.add(new HandInputGroup.Holder("实际回来时间", true, false, "/" + this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.DATE));
            holderList1.add(new HandInputGroup.Holder("实际公出天数", true, false, "0", HandInputGroup.VALUE_TYPE.DOUBLE).setEditable(false).setColor(Color.rgb(229,0,17)));
            setButtonsTitles(new String[]{"提交"});
        }
        return groupList;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBottomButtonsClick(final String title, final List<Group> groups) {
        setButtonllEnable(false);
        if (title.equals("转办")){
            requestForPerson(title);
            return;
        }
        String over = isOver(groups);
        if (!title.equals("驳回") && over != null){
            ToastUtil.showToast(getContext(),(this.getString(R.string.Please_Fill) + over));
            setButtonllEnable(true);
            return;
        }else {
            PostBusinessTripEntity.RetDataBean entity = getEntity();
            final Map<String, Object> body = CommonValues.getCommonParams(getActivity());
            PostBusinessTripEntity.RetDataBean.DetailDataBean data = entity.getDetailData();
            Map<String, Object> mainData = new HashMap<String, Object>();
            mainData.put("Id", entity.getDetailData().getId());
            mainData.put("SubmitBy", data.getSubmitBy());
            mainData.put("IsProxy", data.isIsProxy());
            mainData.put("Allowance", data.isAllowance());
            mainData.put("Budget", data.getBudget());
            mainData.put("Descr", data.getDescr());
            mainData.put("LeaveType", data.getLeaveType());
            mainData.put("PlanLeaveDate", data.getPlanLeaveDate());
            mainData.put("ActualLeaveDate", data.getActualLeaveDate());
            mainData.put("TripDay", data.getTripDay());
            mainData.put("BarCode", data.getBarCode());
            mainData.put("BusinessTripDetail", null);
            mainData.put("LeaveTypeName", data.getLeaveTypeName());
            mainData.put("ApproverDetail", null);
            mainData.put("PlanReturnDate", data.getPlanReturnDate());
            mainData.put("Identifier", data.getIdentifier());
            mainData.put("Summary", data.getSummary());
            mainData.put("PlanLeaveDate", data.getPlanLeaveDate());
            if (attendance) {
                mainData.put("ActualLeaveDay", getDisplayValueByKey("实际公出天数").getRealValue());
                mainData.put("ActualLeaveDate", getDisplayValueByKey("实际离开时间").getRealValue());
                mainData.put("ActualReturnDate", getDisplayValueByKey("实际回来时间").getRealValue());
            }
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date curDate = new Date();//获取当前时间
            String str = formatter.format(curDate);
            mainData.put("UpdateTime", str);
            mainData.put("CreateTime", data.getCreateTime());
            String main = new Gson().toJson(mainData);
            body.put("mainData", main);
            List<PostBusinessTripEntity.RetDataBean.DetailDataBean.BusinessTripDetailBean> details = entity.getDetailData().getBusinessTripDetail();
            String detail = new Gson().toJson(details);
            body.put("detailData", detail);
            String sn = getSN();
            body.put("SN", sn);
            if (attendance) {
                InputHolder holder = new InputHolder("审批意见", "确定", "请输入" + title + "内容", title.equals("同意") ? "同意" : "", title.equals("驳回"));
                InputActivity.showBottomInputTextField(getActivity(),
                        holder, new InputActivity.Callback() {
                            @Override
                            public void onResult(String value) {
                                body.put("actionType", "Submit");
                                body.put("comment", value);
                                HttpManager.getInstance().requestResultForm(CommonValues.REQ_BLEAVE_APPROVE_POST, body, CommonDataEntity.class, new HttpManager.ResultCallback<CommonDataEntity>() {
                                    @Override
                                    public void onSuccess(String json, final CommonDataEntity commonDataEntity) {
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                if (commonDataEntity != null && commonDataEntity.getCode().equals("100")) {
                                                    Toast.makeText(getActivity(), title + "成功", Toast.LENGTH_SHORT).show();
                                                    getActivity().onBackPressed();
                                                } else {
                                                    Toast.makeText(getActivity(), title + "失败，" + commonDataEntity.getMsg() + "请重新提交", Toast.LENGTH_SHORT).show();
                                                }
                                                setButtonllEnable(true);
                                            }
                                        });
                                    }

                                    @Override
                                    public void onFailure(final String msg) {
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(getActivity(), title + "失败，" + msg + "请重新提交", Toast.LENGTH_SHORT).show();
                                                setButtonllEnable(true);
                                            }
                                        });
                                    }
                                });
                            }
                        }
                );

            } else {
                applyApprove(CommonValues.REQ_BLEAVE_APPROVE_POST, body, title);
            }

        }

    }
    public void onClickItemContentSetter(final HandInputGroup.Holder holder) {
        mholder = holder;
        if (mholder.getType() == HandInputGroup.VALUE_TYPE.DATE) {
            showDateTimePicker(holder,true);
        }
    }

    @Override
    public void onDataChanged(HandInputGroup.Holder holder) {
        if (holder.getKey().equals("实际回来时间")) {
            if (!getDisplayValueByKey("实际离开时间").getRealValue().isEmpty()) {
                String returnt = holder.getRealValue();
                String leave = getDisplayValueByKey("实际离开时间").getRealValue();
                int getday = getday(leave, returnt);
                if (getday == -1 ) {
                    holder.setDispayValue("/" + this.getString(R.string.Please_Select));
                    getDisplayValueByKey("实际公出天数").setDispayValue("0");
                    ToastUtil.showToast(getContext(),"请正确填写实际回来时间");
                } else {
                    if (getday%24 == 0){
                        getDisplayValueByKey("实际公出天数").setDispayValue(getday/24 + "");
                    }else {
                        getDisplayValueByKey("实际公出天数").setDispayValue(getday/24 + 1 + "");
                    }
                }
            }
        } else if (holder.getKey().equals("实际离开时间")) {
            if (!getDisplayValueByKey("实际回来时间").getRealValue().isEmpty()) {
                String leave = holder.getRealValue();
                String returnt = getDisplayValueByKey("实际回来时间").getRealValue();
                int getday = getday(leave, returnt);
                if (getday == -1) {
                    holder.setDispayValue("/" + this.getString(R.string.Please_Select));
                    getDisplayValueByKey("实际公出天数").setDispayValue("0");
                    ToastUtil.showToast(getContext(),"请正确填写实际回来时间");
                } else {
                    if (getday%24 == 0){
                        getDisplayValueByKey("实际公出天数").setDispayValue(getday/24 + "");
                    }else {
                        getDisplayValueByKey("实际公出天数").setDispayValue(getday/24 + 1 + "");
                    }
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CommonValues.CODE_OA_REQUEST) {
            if (data != null) {
                String empid = data.getStringExtra("empId");//eid
                if (empid == null || empid.equals("")) {
                    Toast.makeText(getActivity(), "读取转办人信息失败", Toast.LENGTH_SHORT).show();
                    setButtonllEnable(true);
                    return;
                }
                if (empid != null) {
                    Map<String, Object> params = CommonValues.getCommonParams(getActivity());
                    params.put("barCode",getArguments().getString("barCode"));
                    params.put("sn",getArguments().getString("SN"));
                    params.put("approver",empid);
                    applyApprove(CommonValues.FOR_WARD_TASK_PROCESS, params, "转办");
                }
            } else {
                Toast.makeText(getActivity(), "读取人员信息失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
