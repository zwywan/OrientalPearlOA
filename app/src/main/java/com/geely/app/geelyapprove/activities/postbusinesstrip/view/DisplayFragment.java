package com.geely.app.geelyapprove.activities.postbusinesstrip.view;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.geely.app.geelyapprove.activities.postbusinesstrip.bean.PostBusinessTripEntity;
import com.geely.app.geelyapprove.common.entity.UserPhotoEntity;
import com.geely.app.geelyapprove.common.fragment.CommonFragment;
import com.geely.app.geelyapprove.common.utils.CommonValues;
import com.geely.app.geelyapprove.common.utils.ToastUtil;
import com.geely.app.geelyapprove.common.view.HandInputGroup;
import com.geely.app.geelyapprove.common.view.HandToolbar;
import com.geely.app.geelyapprove.datamanage.HttpManager;
import com.geely.app.orientalpearl.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**查看表单--公出
 * Created by zhy on 2016/9/22.
 */
public class DisplayFragment extends CommonFragment {
    private String SN;
    private HashSet<Uri> paths;
    private String photo;
    private String TAG = "DisplayFragment";

    public DisplayFragment() {
    }

    private PostBusinessTripEntity.RetDataBean entity;

    public static DisplayFragment newInstance(Bundle bundle) {
        DisplayFragment fragment = new DisplayFragment();
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
        list.add(new HandInputGroup.Holder(this.getString(R.string.Expected_Leave_Date), true, false, entity.getDetailData().getPlanLeaveDate(), HandInputGroup.VALUE_TYPE.TEXT));
        list.add(new HandInputGroup.Holder(this.getString(R.string.Expected_Return_Date), true, false, entity.getDetailData().getPlanReturnDate(), HandInputGroup.VALUE_TYPE.TEXT));
        list.add(new HandInputGroup.Holder(this.getString(R.string.Expected_Leave_Days), true, false, entity.getDetailData().getTripDay(), HandInputGroup.VALUE_TYPE.TEXT));
        list.add(new HandInputGroup.Holder(this.getString(R.string.Leave_Budget), true, false, entity.getDetailData().getBudget(), HandInputGroup.VALUE_TYPE.TEXT));
        list.add(new HandInputGroup.Holder(this.getString(R.string.BusinessTrip_Reasons), true, false, entity.getDetailData().getDescr().equals("/" + this.getString(R.string.Please_Fill))?"":entity.getDetailData().getDescr(), HandInputGroup.VALUE_TYPE.TEXT));
        if(!entity.getDetailData().isIsProxy()){
            for (PostBusinessTripEntity.RetDataBean.DetailDataBean.BusinessTripDetailBean bean : entity.getDetailData().getBusinessTripDetail()) {
                list.add(new HandInputGroup.Holder("出发/到达", true, false, bean.getLeaveDate().split(" ")[0] + " " + bean.getFrom()
                        + "\n" + bean.getReturnDate().split(" ")[0] + " " + bean.getTo(), HandInputGroup.VALUE_TYPE.TEXT));
            }
        }
        if (entity.getIsKeyAuditNodeForDisplay().isAttendance()){
            list.add(new HandInputGroup.Holder("实际离开时间", true, false, entity.getDetailData().getActualLeaveDate(), HandInputGroup.VALUE_TYPE.TEXT));
            list.add(new HandInputGroup.Holder("实际回来时间", true, false, entity.getDetailData().getActualReturnDate(), HandInputGroup.VALUE_TYPE.TEXT));
            list.add(new HandInputGroup.Holder("实际公出天数", true, false, entity.getDetailData().getActualLeaveDay(), HandInputGroup.VALUE_TYPE.TEXT).setColor(Color.rgb(229,0,17)));
        }
        Group group = new Group("流程摘要-摘要内容", null, true, null, list).setrl(true).setv1(entity.getEmpData().getNameCN() + "(" + entity.getEmpData().getNameEN() + ")").setv2(entity.getEmpData().getPositionNameCN() + " " + entity.getEmpData().getEid());
        if ( photo != null && !photo.isEmpty()){
            group.setDrawable(photo);
        }
        groups.add(group);
        groups.add(new Group("流程摘要-摘要",null,true,null,null).setValue(entity.getHisData()).setrl(false));
        List<HandInputGroup.Holder> holderList1 = new ArrayList<>();
        holderList1.add(new HandInputGroup.Holder(this.getString(R.string.Summary), true, false, entity.getDetailData().getSummary(), HandInputGroup.VALUE_TYPE.TEXT));
        holderList1.add(new HandInputGroup.Holder("流程编号", true, false, getArguments().getString("barCode"), HandInputGroup.VALUE_TYPE.TEXT));
        holderList1.add(new HandInputGroup.Holder(this.getString(R.string.Company_Name), true, false, entity.getEmpData().getCompNameCN(), HandInputGroup.VALUE_TYPE.TEXT));
        holderList1.add(new HandInputGroup.Holder(this.getString(R.string.Department), true, false, entity.getEmpData().getDeptNameCN(), HandInputGroup.VALUE_TYPE.TEXT));
        holderList1.add(new HandInputGroup.Holder(this.getString(R.string.Employee_ID), true, false, entity.getEmpData().getEid(), HandInputGroup.VALUE_TYPE.TEXT));
        holderList1.add(new HandInputGroup.Holder(this.getString(R.string.Name), true, false, entity.getEmpData().getNameCN(), HandInputGroup.VALUE_TYPE.TEXT));
        holderList1.add(new HandInputGroup.Holder(this.getString(R.string.Position), true, false, entity.getEmpData().getPositionNameCN(), HandInputGroup.VALUE_TYPE.TEXT));
        holderList1.add(new HandInputGroup.Holder(this.getString(R.string.Reimbursement_or_not), true, false, entity.getDetailData().isAllowance()?this.getString(R.string.Yes):this.getString(R.string.No), HandInputGroup.VALUE_TYPE.TEXT));
        holderList1.add(new HandInputGroup.Holder(this.getString(R.string.Leave_Budget), true, false, entity.getDetailData().getBudget(), HandInputGroup.VALUE_TYPE.TEXT));
        holderList1.add(new HandInputGroup.Holder(this.getString(R.string.BusinessTrip_Reasons), true, false, entity.getDetailData().getDescr(), HandInputGroup.VALUE_TYPE.TEXT));
        holderList1.add(new HandInputGroup.Holder(this.getString(R.string.Leave_Type), true, false, entity.getDetailData().getLeaveTypeName(), HandInputGroup.VALUE_TYPE.TEXT));
        holderList1.add(new HandInputGroup.Holder(this.getString(R.string.Expected_Leave_Days), true, false, entity.getDetailData().getTripDay() + "", HandInputGroup.VALUE_TYPE.TEXT).setColor(Color.rgb(229,0,17)));
        holderList1.add(new HandInputGroup.Holder(this.getString(R.string.Expected_Leave_Date), true, false, entity.getDetailData().getPlanLeaveDate().split(" ")[0], HandInputGroup.VALUE_TYPE.TEXT));
        holderList1.add(new HandInputGroup.Holder(this.getString(R.string.Expected_Return_Date), true, false, entity.getDetailData().getPlanReturnDate().split(" ")[0], HandInputGroup.VALUE_TYPE.TEXT));
        groups.add(new Group("详细信息-" + this.getString(R.string.Basic_Information), null, true, null, holderList1).setrl(false));
        List<PostBusinessTripEntity.RetDataBean.DetailDataBean.BusinessTripDetailBean> businessTripDetail = entity.getDetailData().getBusinessTripDetail();
        for (int i = 0; i < businessTripDetail.size(); i++) {
            List<HandInputGroup.Holder> detail = new ArrayList<>();
            detail.add(new HandInputGroup.Holder(this.getString(R.string.Leave_Date), true, false, businessTripDetail.get(i).getLeaveDate().split(" ")[0], HandInputGroup.VALUE_TYPE.TEXT));
            detail.add(new HandInputGroup.Holder(this.getString(R.string.Return_Date), true, false, businessTripDetail.get(i).getReturnDate().split(" ")[0], HandInputGroup.VALUE_TYPE.TEXT));
            detail.add(new HandInputGroup.Holder(this.getString(R.string.From), true, false, businessTripDetail.get(i).getFrom(), HandInputGroup.VALUE_TYPE.TEXT));
            detail.add(new HandInputGroup.Holder(this.getString(R.string.To), true, false, businessTripDetail.get(i).getTo(), HandInputGroup.VALUE_TYPE.TEXT));
            detail.add(new HandInputGroup.Holder(this.getString(R.string.Transport), true, false, businessTripDetail.get(i).getTransportName(), HandInputGroup.VALUE_TYPE.TEXT));
            detail.add(new HandInputGroup.Holder(this.getString(R.string.Air_Ticket), true, false, businessTripDetail.get(i).getTicketCost().equals("") ? "0" : businessTripDetail.get(i).getTicketCost(), HandInputGroup.VALUE_TYPE.TEXT));
            detail.add(new HandInputGroup.Holder(this.getString(R.string.Transport_Cost), true, false, businessTripDetail.get(i).getTransportCost().equals("") ? "0" : businessTripDetail.get(i).getTransportCost(), HandInputGroup.VALUE_TYPE.TEXT));
            detail.add(new HandInputGroup.Holder(this.getString(R.string.Hotel_Cost), true, false, businessTripDetail.get(i).getHotelCost().equals("") ? "0" : businessTripDetail.get(i).getHotelCost(), HandInputGroup.VALUE_TYPE.TEXT));
            detail.add(new HandInputGroup.Holder(this.getString(R.string.Cost_Center), true, false, businessTripDetail.get(i).getGuestsCost().equals("") ? "0" : businessTripDetail.get(i).getGuestsCost(), HandInputGroup.VALUE_TYPE.TEXT));
            detail.add(new HandInputGroup.Holder(this.getString(R.string.Other_Cost), true, false, businessTripDetail.get(i).getOtherCost().equals("") ? "0" : businessTripDetail.get(i).getOtherCost(), HandInputGroup.VALUE_TYPE.TEXT));
            detail.add(new HandInputGroup.Holder(this.getString(R.string.Allowance), true, false, businessTripDetail.get(i).getAllowance().equals("") ? "0" : businessTripDetail.get(i).getAllowance(), HandInputGroup.VALUE_TYPE.TEXT));
            groups.add(new Group("详细信息-" + this.getString(R.string.Details_Information), null, true, null, detail).setrl(false));
        }
        List<HandInputGroup.Holder> holderList3 = new ArrayList<>();
        groups.add(new Group("详细信息-" + this.getString(R.string.Total), null, true, entity.getDetailData().getBudget(), holderList3).setrl(false));
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
        SN = getArguments().getString("SN");
    }

    private void loadData() {
        Map<String, Object> param = CommonValues.getCommonParams(getActivity());
        param.put("userId",getArguments().getString("userId"));
        param.put("barCode", getArguments().getString("barCode"));
        param.put("workflowType", getArguments().getString("workflowType"));
        HttpManager.getInstance().requestResultForm(CommonValues.REQ_BLEAVE_DETAIL_DISPLAY, param, PostBusinessTripEntity.class, new HttpManager.ResultCallback<PostBusinessTripEntity>() {
            @Override
            public void onSuccess(String content, final PostBusinessTripEntity entity) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (entity != null && entity.getRetData() != null) {
                            if (entity.getCode().equals("100")) {
                                setEntity(entity.getRetData());
                                setGroup(getGroupList());
                                setPb(false);
                                setButtonllEnable(true);
                                setDisplayTabs(true);
                                notifyDataSetChanged();
                                return;
                            }else{
                                Toast.makeText(getActivity(), entity.getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

            }

            @Override
            public void onFailure(String content) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast(getContext(),"请检查网络");
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

    public PostBusinessTripEntity.RetDataBean getEntity() {
        return entity;
    }

    public void setEntity(PostBusinessTripEntity.RetDataBean entity) {
        this.entity = entity;
    }

    public String getSN(){
        return SN;
    }


}
