package com.geely.app.geelyapprove.activities.extrawork.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.geely.app.geelyapprove.activities.extrawork.entity.ExtraWorkDetailEntity;
import com.geely.app.geelyapprove.common.entity.DataListEntity;
import com.geely.app.geelyapprove.common.entity.DictionaryEntity;
import com.geely.app.geelyapprove.common.utils.CommonValues;
import com.geely.app.geelyapprove.common.utils.DataUtil;
import com.geely.app.geelyapprove.common.utils.GsonUtil;
import com.geely.app.geelyapprove.common.utils.ToastUtil;
import com.geely.app.geelyapprove.common.view.HandInputGroup;
import com.geely.app.geelyapprove.datamanage.HttpManager;
import com.geely.app.orientalpearl.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Oliver on 2016/9/20.
 */
public class ExtraWorkApproveFragment extends ExtraWorkDetailFragment {

    private List<DataListEntity> OverTimeType;
    private ExtraWorkDetailEntity.RetDataBean entity;

    public ExtraWorkApproveFragment() {

    }

    public static ExtraWorkApproveFragment newInstance(Bundle bundle) {
        ExtraWorkApproveFragment fragment = new ExtraWorkApproveFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getBottomTitles();
        loadDraftListData();

    }


    @Override
    public List<Group> getGroupList() {
        List<Group> groups = super.getGroupList();
        entity = getEntity();
        if (entity == null){
            return new ArrayList<>();
        }
        if (entity.getIsKeyAuditNodeForApprove().isHRBP()){
            for (Group group : groups) {
                if (group.getTitle().equals("详细信息-" + this.getString(R.string.Details_Information))){
                    List<HandInputGroup.Holder> holders = group.getHolders();
                    holders.add(new HandInputGroup.Holder(this.getString(R.string.Overtime_Type), true, false, "/" + this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.SELECT));
                }
            }
        }

        return groups;
    }


    @Override
    public void onBottomButtonsClick(final String title, final List<Group> groups) {
        setButtonllEnable(false);
        if (title.equals("转办")){
            requestForPerson(title);
            return;
        }
        String over = isOver(groups);
        if (!title.equals(this.getString(R.string.Reject)) && over != null){
            ToastUtil.showToast(getContext(),(this.getString(R.string.Please_Fill)+ over));
            setButtonllEnable(true);
            return;
        }else {
            final Map<String, Object> param = CommonValues.getCommonParams(getActivity());
            ExtraWorkDetailEntity.RetDataBean.DetailDataBean mainData = getEntity().getDetailData();
            List<Object> details = new ArrayList<Object>();
            for (int i = 3; i < groups.size()-1 - entity.getAttchList().size(); i++) {
                Map<String, Object> ddata = new HashMap<String, Object>();
                if (entity.getIsKeyAuditNodeForApprove().isHRBP()){
                    ddata.put("OTTypesName",groups.get(i).getHolders().get(5).getRealValue());
                    ddata.put("OTTypes",DataUtil.getDicodeByDescr(OverTimeType,groups.get(i).getHolders().get(5).getRealValue()));
                }else {
                    ddata.put("OTTypesName",mainData.getOverTimeDetail().get(i-3).getOTTypesName());
                    ddata.put("OTTypes",mainData.getOverTimeDetail().get(i-3).getOTTypes());
                }
                ddata.put("WorkflowIdentifier", mainData.getOverTimeDetail().get(i-3).getWorkflowIdentifier());
                ddata.put("OTTypeName",mainData.getOverTimeDetail().get(i-3).getOTTypeName());
                ddata.put("StartTime",mainData.getOverTimeDetail().get(i-3).getStartTime());
                ddata.put("EndTime",mainData.getOverTimeDetail().get(i-3).getEndTime());
                ddata.put("OTType",mainData.getOverTimeDetail().get(i-3).getOTType());
                ddata.put("Reason",mainData.getOverTimeDetail().get(i-3).getReason());
                ddata.put("Hours",mainData.getOverTimeDetail().get(i-3).getHours());
                ddata.put("IsPostToPs",mainData.getOverTimeDetail().get(i-3).isIsPostToPs());
                details.add(ddata);
            }
            String json = GsonUtil.getJson(details);
            mainData.setUpdateTime(DataUtil.parseDateNormalString(new Date()));
            String main = GsonUtil.getJson(mainData);

            param.put("mainData", main);
            param.put("detailData",json);
            param.put("SN", getArguments().getString("SN"));
            applyApprove(CommonValues.REQ_EXTRAWORK_APPROVE, param, title);
        }

    }
    @Override
    public void onClickItemContentSetter(final HandInputGroup.Holder holder) {
        if(holder.getKey().equals(this.getString(R.string.Overtime_Type))){
            showSelector(holder, DataUtil.getDescr(OverTimeType));
        }
    }

    private void loadDraftListData() {
        HttpManager manager = HttpManager.getInstance();
        Map<String, Object> params = CommonValues.getCommonParams(getActivity());
        //加班类型
        params.put("code", 7285);
        manager.requestResultForm(CommonValues.GET_DICTIONARY_LIST, params, DictionaryEntity.class, new HttpManager.ResultCallback<DictionaryEntity>() {
            @Override
            public void onSuccess(String content, DictionaryEntity expenseTypeEntity) {
                ExtraWorkApproveFragment.this.OverTimeType = expenseTypeEntity.getRetData().get(0).getDataList();
            }

            @Override
            public void onFailure(String content) {

            }
        });
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
