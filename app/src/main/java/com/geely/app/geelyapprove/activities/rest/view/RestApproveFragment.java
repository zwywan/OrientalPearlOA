package com.geely.app.geelyapprove.activities.rest.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.geely.app.geelyapprove.activities.rest.bean.RestDetailBean;
import com.geely.app.geelyapprove.common.application.GeelyApp;
import com.geely.app.geelyapprove.common.entity.AttachmentListEntity;
import com.geely.app.geelyapprove.common.utils.CommonValues;
import com.geely.app.geelyapprove.common.utils.DescripUtil;
import com.geely.app.geelyapprove.common.utils.ToastUtil;
import com.geely.app.geelyapprove.common.view.HandInputGroup;
import com.geely.app.orientalpearl.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guiluXu on 2016/9/22.
 */
public class RestApproveFragment extends RestDetailFragment{

    private RestDetailBean.RetDataBean.DetailDataBean entitiy;
    private HandInputGroup.Holder mholder;

    public RestApproveFragment() {
    }

    public static RestApproveFragment newInstance(Bundle bundle) {
        RestApproveFragment fragment = new RestApproveFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getBottomTitles();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBottomButtonsClick(final String title, final List<Group> groups) {
        setButtonllEnable(false);
        if (title.equals("转办")){
            requestForPerson(title);
            return;
        }
        entitiy = getEntity().getDetailData();
        String over = isOver(groups);
        if (!title.equals("驳回") && over != null){
            ToastUtil.showToast(getContext(),(this.getString(R.string.Please_Fill) + over));
            setButtonllEnable(true);
            return;
        }else {
            Map<String, Object> body = CommonValues.getCommonParams(getActivity());
            Map<String, Object> mainData = new HashMap<String, Object>();
            mainData.put("Identifier",entitiy.getIdentifier());
            mainData.put("BarCode",entitiy.getBarCode());
            mainData.put("Company",entitiy.getCompany());
            mainData.put("Position",entitiy.getPosition());
            mainData.put("EmployeeID",entitiy.getEmployeeID());
            mainData.put("Department",entitiy.getDepartment());
            mainData.put("SubmitBy",entitiy.getSubmitBy());
            mainData.put("TotalLeaveTime",entitiy.getTotalLeaveTime());
            mainData.put("Applicant",entitiy.getApplicant());
            mainData.put("UpdateTime", DescripUtil.formatDate(new Date()));
            mainData.put("CreateTime",entitiy.getCreateTime());
            mainData.put("Summary",entitiy.getSummary());
            mainData.put("UpdateBy", GeelyApp.getLoginEntity().getUserId());
            mainData.put("IsSelf",entitiy.isIsSelf());
            mainData.put("Id",entitiy.getId());
            if (entitiy.isIsSelf()){
                mainData.put("LeaveDays",entitiy.getLeaveDays());
            }else {
                mainData.put("laveYearDays", entitiy.getLaveYearDays());//年假剩余天数//18
                mainData.put("laveHours", entitiy.getLaveHours());//调休剩余小时数//19
                mainData.put("totalNumber", entitiy.getTotalNumber());//累积产前检查次数//20
                mainData.put("totalDays", entitiy.getTotalDays());//累计事假天数//21
            }
            String main = new Gson().toJson(mainData);
            body.put("mainData",main);
            List<Object> objects = new ArrayList<>();
            List<RestDetailBean.RetDataBean.DetailDataBean.LeaveRequestDetailBean> details = entitiy.getLeaveRequestDetail();
            for (RestDetailBean.RetDataBean.DetailDataBean.LeaveRequestDetailBean detail : details) {
                Map<String,Object> detailData = new HashMap<>();
                detailData.put("Id",detail.getId());
                detailData.put("LeaveTpyeName",detail.getLeaveTpyeName());
                detailData.put("WorkflowIdentifier",detail.getWorkflowIdentifier());
                detailData.put("LeaveType",detail.getLeaveType());
                detailData.put("StartTime",detail.getStartTime());
                detailData.put("EndTime",detail.getEndTime());
                detailData.put("LeaveTime",detail.getLeaveTime());
                detailData.put("LeaveCauses",detail.getLeaveCauses());
                detailData.put("Remark",detail.getRemark());
                objects.add(detailData);
            }
            String detail = new Gson().toJson(objects);
            body.put("detailData",detail);
            body.put("sn",getArguments().getString("SN"));
            applyApprove(CommonValues.REQ_REST_APPROVE, body, title);
        }

    }


    @Override
    public void onClickItemContentSetter(HandInputGroup.Holder holder) {
        mholder = holder;
        if (mholder.getType() == HandInputGroup.VALUE_TYPE.SELECT) {
            if (holder.getKey().equals(this.getString(R.string.Select_Attachments))) {
                AttachmentListEntity entity = (AttachmentListEntity) holder.getValue();
                lookAttachmentInDetailFragment(entity);
            }
        }
        if (mholder.getType() == HandInputGroup.VALUE_TYPE.DATE) {
            showDateTimePicker(mholder,false);
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
