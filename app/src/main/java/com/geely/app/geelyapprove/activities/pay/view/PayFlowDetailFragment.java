package com.geely.app.geelyapprove.activities.pay.view;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.geely.app.geelyapprove.activities.pay.bean.PayFlowDetailBean;
import com.geely.app.geelyapprove.common.application.GeelyApp;
import com.geely.app.geelyapprove.common.entity.AttachmentListEntity;
import com.geely.app.geelyapprove.common.entity.EmpDataEntity;
import com.geely.app.geelyapprove.common.entity.UserPhotoEntity;
import com.geely.app.geelyapprove.common.fragment.CommonFragment;
import com.geely.app.geelyapprove.common.utils.CommonValues;
import com.geely.app.geelyapprove.common.utils.DataUtil;
import com.geely.app.geelyapprove.common.utils.ToastUtil;
import com.geely.app.geelyapprove.common.view.HandInputGroup;
import com.geely.app.geelyapprove.common.view.HandToolbar;
import com.geely.app.geelyapprove.datamanage.HttpManager;
import com.geely.app.orientalpearl.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Created by guiluXu on 2016/9/30.
 */
public class PayFlowDetailFragment extends CommonFragment {

    private String photo;
    private String TAG = "PayFlowDetailFragment";

    public PayFlowDetailFragment(){
    }

    private PayFlowDetailBean.RetDataBean entity = null;
    private PayFlowDetailBean.RetDataBean.DetailDataBean entityDetail = null;
    private EmpDataEntity entityEmp = null;

    public static PayFlowDetailFragment newInstance(Bundle bundle) {
        PayFlowDetailFragment fragment = new PayFlowDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onClickItemContentSetter(HandInputGroup.Holder holder) {
        if (holder.getType() == HandInputGroup.VALUE_TYPE.SELECT) {
            if (holder.getKey().equals(this.getString(R.string.Select_Attachments))) {
                AttachmentListEntity entity = (AttachmentListEntity) holder.getValue();
                lookAttachmentInDetailFragment(entity);
            }
        }
    }

    @Override
    public List<CommonFragment.Group> getGroupList() {
        if (entity == null) return new ArrayList<>();
        entityEmp = entity.getEmpData();
        entityDetail = entity.getDetailData();
        boolean paperReceive = entity.getIsKeyAuditNodeForDisplay().isPaperReceive();//单据接收
        boolean finance = entity.getIsKeyAuditNodeForDisplay().isFinance();//财务审核
        boolean glAccount = entity.getIsKeyAuditNodeForDisplay().isGLAccount();
        boolean cashier = entity.getIsKeyAuditNodeForDisplay().isCashier();//出纳
        List<Group> groups = new ArrayList<>();
        List<HandInputGroup.Holder> list = new ArrayList<>();
        list.add(new HandInputGroup.Holder(this.getString(R.string.Summary), true, false, entity.getDetailData().getSummary(), HandInputGroup.VALUE_TYPE.TEXT));
        list.add(new HandInputGroup.Holder("流程编号", true, false, getArguments().getString("barCode"), HandInputGroup.VALUE_TYPE.TEXT));
        list.add(new HandInputGroup.Holder(this.getString(R.string.Company_Name), true, false, entityEmp.getCompNameCN(), HandInputGroup.VALUE_TYPE.TEXT));
        list.add(new HandInputGroup.Holder(this.getString(R.string.Cost_Center), true, false, entityDetail.getCostCenterName(), HandInputGroup.VALUE_TYPE.TEXT));
        list.add(new HandInputGroup.Holder(this.getString(R.string.Payment_Amount), true, false, entityDetail.getPaymentAmount().isEmpty()? "0" : entityDetail.getPaymentAmount(), HandInputGroup.VALUE_TYPE.TEXT));
        list.add(new HandInputGroup.Holder(this.getString(R.string.Payment_Type), true, false, entityDetail.getPaymentTypeName(), HandInputGroup.VALUE_TYPE.TEXT));
        if (paperReceive){
            if (!entity.getIsKeyAuditNodeForApprove().isPaperReceive()) {
                list.add(new HandInputGroup.Holder(this.getString(R.string.Received_Date), true, false, entity.getDetailData().getBillPaperReceiveDate().split(" ")[0], HandInputGroup.VALUE_TYPE.TEXT));
            }
        }
        if (finance){
            if (!entity.getIsKeyAuditNodeForApprove().isFinance()) {
                list.add(new HandInputGroup.Holder(this.getString(R.string.Audit_Total_Amount), true, false, entity.getDetailData().getAuditPaymentAmount(), HandInputGroup.VALUE_TYPE.TEXT));
            }
        }
        if (cashier){
            if (!entity.getIsKeyAuditNodeForApprove().isCashier()) {
                list.add(new HandInputGroup.Holder(this.getString(R.string.Payment_Amount_of_other_ways), true, false, entity.getDetailData().getCashPayAmount().isEmpty()?"0":entity.getDetailData().getCashPayAmount(), HandInputGroup.VALUE_TYPE.TEXT));
                list.add(new HandInputGroup.Holder(this.getString(R.string.Capital_Platform_Payment_Amount), true, false, entity.getDetailData().getPlatPayAmount().isEmpty()?"0":entity.getDetailData().getPlatPayAmount(), HandInputGroup.VALUE_TYPE.TEXT));
                list.add(new HandInputGroup.Holder(this.getString(R.string.Expenditure_Type), true, false, entity.getDetailData().getExpenditureType(), HandInputGroup.VALUE_TYPE.TEXT));
                list.add(new HandInputGroup.Holder(this.getString(R.string.Cashier), true, false, entity.getDetailData().getCashierName(), HandInputGroup.VALUE_TYPE.TEXT));
            }
        }
        if (glAccount){
            list.add(new HandInputGroup.Holder(this.getString(R.string.Payment_Amount_of_other_ways), true, false, entity.getDetailData().getCashPayAmount().isEmpty()?"0":entity.getDetailData().getCashPayAmount(), HandInputGroup.VALUE_TYPE.TEXT));
            list.add(new HandInputGroup.Holder(this.getString(R.string.Capital_Platform_Payment_Amount), true, false, entity.getDetailData().getPlatPayAmount().isEmpty()?"0":entity.getDetailData().getPlatPayAmount(), HandInputGroup.VALUE_TYPE.TEXT));
            list.add(new HandInputGroup.Holder(this.getString(R.string.Expenditure_Type), true, false, entity.getDetailData().getExpenditureType(), HandInputGroup.VALUE_TYPE.TEXT));
            list.add(new HandInputGroup.Holder(this.getString(R.string.Cashier), true, false, entity.getDetailData().getCashierName(), HandInputGroup.VALUE_TYPE.TEXT));
            if (Double.parseDouble(entity.getDetailData().getPlatPayAmount().isEmpty()?"0":entity.getDetailData().getPlatPayAmount())!=0){
                list.add(new HandInputGroup.Holder(this.getString(R.string.Pay_Status), true, false, entity.getDetailData().getPayStatusName(), HandInputGroup.VALUE_TYPE.TEXT));
                if (entity.getDetailData().getPayStatus().equals("1")) {
                    list.add(new HandInputGroup.Holder(this.getString(R.string.Paying_Bank_Account), true, false, entity.getDetailData().getFundReturnBankAccount(), HandInputGroup.VALUE_TYPE.TEXT));
                    list.add(new HandInputGroup.Holder(this.getString(R.string.Paying_Bank_Name), true, false, entity.getDetailData().getFundReturnBankName(), HandInputGroup.VALUE_TYPE.TEXT));
                    list.add(new HandInputGroup.Holder(this.getString(R.string.Payment_Description), true, false, entity.getDetailData().getFundReturnNote(), HandInputGroup.VALUE_TYPE.TEXT));
                }
            }
        }
        Group group = new Group("流程摘要-摘要内容", null, true, null, list).setrl(true).setv1(entity.getEmpData().getNameCN() + "(" + entity.getEmpData().getNameEN() + ")").setv2(entity.getEmpData().getPositionNameCN() + " " + entity.getEmpData().getEid());
        if ( photo != null && !photo.isEmpty()) {
            group.setDrawable(photo);
        }
        groups.add(group);
        groups.add(new Group("流程摘要-摘要",null,true,null,null).setValue(entity.getHisData()).setrl(false));
        List<HandInputGroup.Holder> subHolder = new ArrayList<>();
        subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Summary), true, false, entity.getDetailData().getSummary(), HandInputGroup.VALUE_TYPE.TEXT));
        subHolder.add(new HandInputGroup.Holder("流程编号", true, false, getArguments().getString("barCode"), HandInputGroup.VALUE_TYPE.TEXT));
        subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Company_Name), true, false, entityEmp.getCompNameCN(), HandInputGroup.VALUE_TYPE.TEXT));
        subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Cost_Center), true, true, entityDetail.getCostCenterName(), HandInputGroup.VALUE_TYPE.TEXT));
        subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Operator), true, false, entityDetail.getRequestorName(), HandInputGroup.VALUE_TYPE.TEXT));
        subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Payment_Type), true, false, entityDetail.getPaymentTypeName(), HandInputGroup.VALUE_TYPE.TEXT));
        subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Payment_Content), true, false, entityDetail.getPaymentContent(), HandInputGroup.VALUE_TYPE.TEXT));
        subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Name_of_Payee), true, false, entityDetail.getPayeeId(), HandInputGroup.VALUE_TYPE.TEXT));
        subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Bank), true, false, entityDetail.getPayeeBankName(), HandInputGroup.VALUE_TYPE.TEXT));
        if (entity.getIsKeyAuditNodeForApprove().isCashier() || entity.getIsKeyAuditNodeForApprove().isFinance() || finance || cashier || entity.getEmpData().getUserId().equals(GeelyApp.getLoginEntity().getRetData().getUserInfo().getUserId())){
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Bank_Account), true, false, entityDetail.getPayeeBankAccount(), HandInputGroup.VALUE_TYPE.TEXT));
        }else {
            if (entityDetail.getPayeeBankAccount().length() > 15){
                subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Bank_Account), true, false, "********" + entityDetail.getPayeeBankAccount().substring(entityDetail.getPayeeBankAccount().length() - 4,entityDetail.getPayeeBankAccount().length()), HandInputGroup.VALUE_TYPE.TEXT));
            }else{
                subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Bank_Account), true, false, entityDetail.getPayeeBankAccount(), HandInputGroup.VALUE_TYPE.TEXT));
            }
        }
        subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Bank_Adress), true, false, entityDetail.getPayeeBankProvinceName() + entityDetail.getPayeeBankCityName(), HandInputGroup.VALUE_TYPE.TEXT));
        subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Branch_Bank), true, true, entityDetail.getPayeeBranchBank(), HandInputGroup.VALUE_TYPE.TEXT));
        subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Has_Invoice), true, false, entityDetail.isIsInvoice()? this.getString(R.string.Yes) : this.getString(R.string.No), HandInputGroup.VALUE_TYPE.TEXT));
        subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Invoice_Number), true, true, entityDetail.getInvoiceNumber(), HandInputGroup.VALUE_TYPE.TEXT));
        subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Invoice_Amount), true, false, entityDetail.getInvoiceAmount(), HandInputGroup.VALUE_TYPE.TEXT));
        subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Payment_Amount), true, false, entityDetail.getPaymentAmount().isEmpty()? "0" : entityDetail.getPaymentAmount(), HandInputGroup.VALUE_TYPE.TEXT));
        subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Has_Buget), true, true, entityDetail.isHasBuget()?this.getString(R.string.Yes):this.getString(R.string.No), HandInputGroup.VALUE_TYPE.TEXT));
        groups.add(new Group("详细信息-" + this.getString(R.string.Basic_Information), null, true, null, subHolder).setrl(false));
        List<AttachmentListEntity> attachList = entity.getAttchList();
        if (attachList != null && attachList.size() > 0) {
            for  ( int  i  =   0 ; i  <  attachList.size()  -   1 ; i ++ )   {
                for  ( int  j  =  attachList.size()  -   1 ; j  >  i; j -- )   {
                    if  (attachList.get(j).getFileName().equals(attachList.get(i).getFileName()))   {
                        attachList.remove(j);
                        notifyDataSetChanged();
                    }
                }
            }
            for (AttachmentListEntity entity : attachList) {
                List<HandInputGroup.Holder> temp = new ArrayList<>();
                temp.add(new HandInputGroup.Holder(this.getString(R.string.Attachment_Type), false, false, entity.getFileGroupName(), HandInputGroup.VALUE_TYPE.TEXT));
                temp.add(new HandInputGroup.Holder<HashSet<Uri>>(this.getString(R.string.Select_Attachments), false, false, entity.getFileName() + DataUtil.getCurrentDate() +entity.getFileExtension().substring(entity.getFileExtension().indexOf(".")), HandInputGroup.VALUE_TYPE.SELECT)
                        .setColor(Color.BLUE).setDrawableRight(-1).setValue(entity));
                groups.add(new Group("详细信息-" + this.getString(R.string.Attachment_Info), null, false, null, temp).setrl(false));
            }

        }

        return groups;
    }

    public void setToolbar(HandToolbar toolbar) {
        toolbar.setDisplayHomeAsUpEnabled(true, getActivity());
        toolbar.setTitle(getArguments().getString("ProcessNameCN"));
    }

    /**
     * 返回底部按钮设置
     *
     * @return 返回一个字符串数组，为空则不显示
     */
    @Override
    public String[] getBottomButtonsTitles() {
        return null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadData();
    }

    protected void loadData() {
        Map<String, Object> param = CommonValues.getCommonParams(getActivity());
        param.put("userId",getArguments().getString("userId"));
        param.put("barCode", getArguments().getString("barCode"));
        param.put("workflowType", getArguments().getString("workflowType"));
        HttpManager.getInstance().requestResultForm(CommonValues.REQ_PAYMENT_DETAIL, param, PayFlowDetailBean.class, new HttpManager.ResultCallback<PayFlowDetailBean>() {
            @Override
            public void onSuccess(String content, final PayFlowDetailBean entity) {
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
                            }else {
                                ToastUtil.showToast(getContext(),entity.getMsg());
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

    public PayFlowDetailBean.RetDataBean getEntity() {
        return entity;
    }
    public void setEntity(PayFlowDetailBean.RetDataBean entity) {
        this.entity = entity;
    }


}
