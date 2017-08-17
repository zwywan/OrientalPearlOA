package com.geely.app.geelyapprove.activities.enterexpense.view;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.geely.app.geelyapprove.activities.enterexpense.Entity.EnterExpenseDetailEntity;
import com.geely.app.geelyapprove.common.application.GeelyApp;
import com.geely.app.geelyapprove.common.entity.AttachmentListEntity;
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
 * Created by zhy on 2016/11/17.
 */

public class EnterExpenseDetailFragment extends CommonFragment {

    private String SN;
    private HashSet<Uri> paths;
    private EnterExpenseDetailEntity.RetDataBean entity;
    private boolean isDirectExpense;
    private boolean hasBuget;
    private EnterExpenseDetailEntity.RetDataBean.DetailDataBean detailData;
    protected String json;
    private String photo;
    private String TAG = "EnterExpenseDetailFragment";

    public EnterExpenseDetailFragment() {
    }

    public static EnterExpenseDetailFragment newInstance(Bundle bundle) {
        EnterExpenseDetailFragment fragment = new EnterExpenseDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadData();
    }


    @Override
    public String[] getBottomButtonsTitles() {
        return super.getBottomButtonsTitles();
    }


    @Override
    public List<Group> getGroupList() {
        if (entity == null) return new ArrayList<>();
        List<Group> groups = new ArrayList<>();
        detailData = entity.getDetailData();
        EnterExpenseDetailEntity.RetDataBean.IsKeyAuditNodeBean isKeyAuditNodeForDisplay = entity.getIsKeyAuditNodeForDisplay();
        EnterExpenseDetailEntity.RetDataBean.IsKeyAuditNodeBean isKeyAuditNodeForApprove = entity.getIsKeyAuditNodeForApprove();
        boolean finance = isKeyAuditNodeForDisplay.isFinance();
        boolean cashier = isKeyAuditNodeForDisplay.isCashier();
        boolean glAccount = isKeyAuditNodeForDisplay.isGLAccount();
        boolean paperReceive = isKeyAuditNodeForDisplay.isPaperReceive();
        isDirectExpense = detailData.isIsDirectExpense();
        hasBuget = detailData.isHasBuget();
        List<HandInputGroup.Holder> list = new ArrayList<>();
        list.add(new HandInputGroup.Holder(this.getString(R.string.Summary), true, false, entity.getDetailData().getSummary(), HandInputGroup.VALUE_TYPE.TEXT));
        list.add(new HandInputGroup.Holder("流程编号", true, false, getArguments().getString("barCode"), HandInputGroup.VALUE_TYPE.TEXT));
        list.add(new HandInputGroup.Holder(this.getString(R.string.Hosting_expense), true, false, detailData.getAmount(), HandInputGroup.VALUE_TYPE.TEXT));
        if(isDirectExpense){
            list.add(new HandInputGroup.Holder(this.getString(R.string.Company_Name), true, false, detailData.getCompanyName(), HandInputGroup.VALUE_TYPE.TEXT));
            list.add(new HandInputGroup.Holder(this.getString(R.string.Cost_Center), true, false, detailData.getCostCenterName(), HandInputGroup.VALUE_TYPE.TEXT));
        }
        if(glAccount){
            list.add(new HandInputGroup.Holder(this.getString(R.string.Cash_Pay_Amount), true, false, entity.getDetailData().getCashPayAmount(), HandInputGroup.VALUE_TYPE.TEXT));
            if (entity.getDetailData().isIsOffsetLoan()){
                list.add(new HandInputGroup.Holder(this.getString(R.string.Dealings_Pay_Amount), true, false, entity.getDetailData().getDealingsPayAmount(), HandInputGroup.VALUE_TYPE.TEXT));
            }
            list.add(new HandInputGroup.Holder(this.getString(R.string.Capital_Platform_Payment_Amount), true, false, entity.getDetailData().getPlatPayAmount(), HandInputGroup.VALUE_TYPE.TEXT));
            list.add(new HandInputGroup.Holder(this.getString(R.string.Expenditure_Type), true, false, entity.getDetailData().getExpenditureType(), HandInputGroup.VALUE_TYPE.TEXT));
            list.add(new HandInputGroup.Holder(this.getString(R.string.Cashier), true, false, entity.getDetailData().getCashierName(), HandInputGroup.VALUE_TYPE.TEXT));
            if (entity.getDetailData().isIsGenerateAccountingVouchers()){
                list.add(new HandInputGroup.Holder(this.getString(R.string.Voucher_Number), true, false, entity.getDetailData().getVoucherNumber(), HandInputGroup.VALUE_TYPE.TEXT));
            }
            if (Double.parseDouble(entity.getDetailData().getPlatPayAmount().isEmpty()?"0":entity.getDetailData().getPlatPayAmount())!=0){
                list.add(new HandInputGroup.Holder(this.getString(R.string.Pay_Status), true, false, entity.getDetailData().getPayStatusName(), HandInputGroup.VALUE_TYPE.TEXT));
                if (entity.getDetailData().getPayStatus().equals("1")) {
                list.add(new HandInputGroup.Holder(this.getString(R.string.Paying_Bank_Account), true, false, entity.getDetailData().getFundReturnBankAccount(), HandInputGroup.VALUE_TYPE.TEXT));
                list.add(new HandInputGroup.Holder(this.getString(R.string.Paying_Bank_Name), true, false, entity.getDetailData().getFundReturnBankName(), HandInputGroup.VALUE_TYPE.TEXT));
                list.add(new HandInputGroup.Holder(this.getString(R.string.Payment_Description), true, false, entity.getDetailData().getFundReturnNote(), HandInputGroup.VALUE_TYPE.TEXT));
                }
            }
        }
        if (cashier){
            if(!isKeyAuditNodeForApprove.isCashier()){
                list.add(new HandInputGroup.Holder(this.getString(R.string.Cash_Pay_Amount), true, false, entity.getDetailData().getCashPayAmount().isEmpty()?"0":entity.getDetailData().getCashPayAmount(), HandInputGroup.VALUE_TYPE.TEXT));
                if (entity.getDetailData().isIsOffsetLoan()){
                    list.add(new HandInputGroup.Holder(this.getString(R.string.Dealings_Pay_Amount), true, false, entity.getDetailData().getDealingsPayAmount().isEmpty()?"0":entity.getDetailData().getDealingsPayAmount(), HandInputGroup.VALUE_TYPE.TEXT));
                }
                list.add(new HandInputGroup.Holder(this.getString(R.string.Capital_Platform_Payment_Amount), true, false, entity.getDetailData().getPlatPayAmount().isEmpty()?"0":entity.getDetailData().getPlatPayAmount(), HandInputGroup.VALUE_TYPE.TEXT));
                list.add(new HandInputGroup.Holder(this.getString(R.string.Expenditure_Type), true, false, entity.getDetailData().getExpenditureType(), HandInputGroup.VALUE_TYPE.TEXT));
                list.add(new HandInputGroup.Holder(this.getString(R.string.Cashier), true, false, entity.getDetailData().getCashierName(), HandInputGroup.VALUE_TYPE.TEXT));
            }
        }
        if(finance){
            if (!isKeyAuditNodeForApprove.isFinance()){
                list.add(new HandInputGroup.Holder(this.getString(R.string.Audit_Total_Amount), true, false, entity.getDetailData().getAuditTotalAmount(), HandInputGroup.VALUE_TYPE.TEXT));
                list.add(new HandInputGroup.Holder(this.getString(R.string.Is_Render_Accountant), true, false, entity.getDetailData().isIsGenerateAccountingVouchers()?this.getString(R.string.Yes):this.getString(R.string.No), HandInputGroup.VALUE_TYPE.TEXT));
                if (entity.getDetailData().isIsGenerateAccountingVouchers()){
                    list.add(new HandInputGroup.Holder(this.getString(R.string.Generate_Accounting_Vouchers_Type), true, false, entity.getDetailData().getGenerateAccountingVouchersTypeName(), HandInputGroup.VALUE_TYPE.TEXT));
                }
                list.add(new HandInputGroup.Holder(this.getString(R.string.Is_Compliance), true, false, entity.getDetailData().isIsCompliance()?this.getString(R.string.Yes):this.getString(R.string.No), HandInputGroup.VALUE_TYPE.TEXT));
                list.add(new HandInputGroup.Holder(this.getString(R.string.ExpenseType), true, false, entity.getDetailData().getExpenseType(), HandInputGroup.VALUE_TYPE.TEXT));
            }
        }
        if(paperReceive){
            if (!isKeyAuditNodeForApprove.isPaperReceive()) {
                list.add(new HandInputGroup.Holder(this.getString(R.string.Received_Date), true, false, entity.getDetailData().getBillPaperReceiveDate().split(" ")[0], HandInputGroup.VALUE_TYPE.TEXT));
            }
        }
        Group group = new Group("流程摘要-摘要内容", null, true, null, list).setrl(true).setv1(entity.getEmpData().getNameCN() + "(" + entity.getEmpData().getNameEN() + ")").setv2(entity.getEmpData().getPositionNameCN() + " " + entity.getEmpData().getEid());
        if ( photo!= null && !photo.isEmpty()){
            group.setDrawable(photo);
        }
        groups.add(group);
        groups.add(new Group("流程摘要-摘要",null,true,null,null).setValue(entity.getHisData()).setrl(false));
        List<HandInputGroup.Holder> holderList1 = new ArrayList<>();
        holderList1.add(new HandInputGroup.Holder(this.getString(R.string.Summary), true, false, detailData.getSummary(), HandInputGroup.VALUE_TYPE.TEXT));
        holderList1.add(new HandInputGroup.Holder("流程编号", true, false, getArguments().getString("barCode"), HandInputGroup.VALUE_TYPE.TEXT));
        holderList1.add(new HandInputGroup.Holder(this.getString(R.string.Submitter), true, false, detailData.getSubmitBy(), HandInputGroup.VALUE_TYPE.TEXT));
        holderList1.add(new HandInputGroup.Holder(this.getString(R.string.Number_of_Guests), true, false, detailData.getPeopleNumber(), HandInputGroup.VALUE_TYPE.TEXT));
        holderList1.add(new HandInputGroup.Holder(this.getString(R.string.Hosting_expense), true, false, detailData.getAmount(), HandInputGroup.VALUE_TYPE.TEXT));
        holderList1.add(new HandInputGroup.Holder(this.getString(R.string.Direct_Reimbursement), true, false, isDirectExpense ? this.getString(R.string.Yes) : this.getString(R.string.No), HandInputGroup.VALUE_TYPE.TEXT));
        holderList1.add(new HandInputGroup.Holder(this.getString(R.string.Has_Buget), true, false, hasBuget ? this.getString(R.string.Yes) : this.getString(R.string.No), HandInputGroup.VALUE_TYPE.TEXT));
        holderList1.add(new HandInputGroup.Holder(this.getString(R.string.Place_Of_Entertainment), true, false, detailData.getPlaceOfEntertainment(), HandInputGroup.VALUE_TYPE.TEXT));
        holderList1.add(new HandInputGroup.Holder(this.getString(R.string.Our_Participant_hosts), true, false, detailData.getOurReceptionPeople(), HandInputGroup.VALUE_TYPE.TEXT));
        holderList1.add(new HandInputGroup.Holder(this.getString(R.string.Entertainment_Content), true, false, detailData.getRemark(), HandInputGroup.VALUE_TYPE.TEXT));
        groups.add(new CommonFragment.Group("详细信息-" + this.getString(R.string.Basic_Information), null, true, null, holderList1).setrl(false));
        if (isDirectExpense) {
            List<HandInputGroup.Holder> subHolder1 = new ArrayList<>();
            subHolder1.add(new HandInputGroup.Holder(this.getString(R.string.Company_Name), true, false, detailData.getCompanyName(), HandInputGroup.VALUE_TYPE.TEXT));
            subHolder1.add(new HandInputGroup.Holder(this.getString(R.string.Cost_Center), true, false, detailData.getCostCenterName(), HandInputGroup.VALUE_TYPE.TEXT));
            subHolder1.add(new HandInputGroup.Holder(this.getString(R.string.Reimbursement_Employee_ID), true, false, entity.getEmpData().getEid(), HandInputGroup.VALUE_TYPE.TEXT));
            subHolder1.add(new HandInputGroup.Holder(this.getString(R.string.Reimbursement_Person), true, false, entity.getEmpData().getNameCN(), HandInputGroup.VALUE_TYPE.TEXT));
            subHolder1.add(new HandInputGroup.Holder(this.getString(R.string.Grade), true, false, entity.getEmpData().getGrade().trim(), HandInputGroup.VALUE_TYPE.TEXT));
            subHolder1.add(new HandInputGroup.Holder(this.getString(R.string.Position), true, false, entity.getEmpData().getPositionNameCN(), HandInputGroup.VALUE_TYPE.TEXT));
            subHolder1.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Employee_ID), true, false, detailData.getPayeeId(), HandInputGroup.VALUE_TYPE.TEXT));
            subHolder1.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Name), true, false, detailData.getPayeeName(), HandInputGroup.VALUE_TYPE.TEXT));
            if (entity.getIsKeyAuditNodeForApprove().isCashier() || entity.getIsKeyAuditNodeForApprove().isFinance() || finance || cashier || entity.getEmpData().getUserId().equals(GeelyApp.getLoginEntity().getRetData().getUserInfo().getUserId())){
                subHolder1.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Bank_Account), false, false, detailData.getPayeeBankAccount(), HandInputGroup.VALUE_TYPE.TEXT));
            }else {
                if (detailData.getPayeeBankAccount().length() > 15){
                    subHolder1.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Bank_Account), false, false, "********" + detailData.getPayeeBankAccount().substring(detailData.getPayeeBankAccount().length() - 4,detailData.getPayeeBankAccount().length()), HandInputGroup.VALUE_TYPE.TEXT));
                }else{
                    subHolder1.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Bank_Account), false, false, detailData.getPayeeBankAccount(), HandInputGroup.VALUE_TYPE.TEXT));
                }
            }
            subHolder1.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Bank_Adress), true, false, detailData.getPayeeBankProvinceName() + " " + detailData.getPayeeBankCityName(), HandInputGroup.VALUE_TYPE.TEXT));
            subHolder1.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Branch_Bank), true, false, detailData.getPayeeBranchBank(), HandInputGroup.VALUE_TYPE.TEXT));
            subHolder1.add(new HandInputGroup.Holder(this.getString(R.string.Has_Borrow), true, false, detailData.isIsOffsetLoan() ? this.getString(R.string.Yes) : this.getString(R.string.No), HandInputGroup.VALUE_TYPE.TEXT));
            groups.add(new CommonFragment.Group("详细信息-" + this.getString(R.string.Reimbursement_Info), null, true, null, subHolder1).setrl(false));
        }

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

    @Override
    public void onClickItemContentSetter(HandInputGroup.Holder holder) {
        if (holder.getType() == HandInputGroup.VALUE_TYPE.SELECT) {
            if (holder.getKey().equals(this.getString(R.string.Select_Attachments))) {
                AttachmentListEntity entity = (AttachmentListEntity) holder.getValue();
                lookAttachmentInDetailFragment(entity);
            }
        }
    }


    public void setToolbar(HandToolbar toolbar) {
        toolbar.setDisplayHomeAsUpEnabled(true, getActivity());
        toolbar.setTitle(getArguments().getString("ProcessNameCN"));
    }


    public void loadData() {
        Map<String, Object> param = CommonValues.getCommonParams(getActivity());
        param.put("userId",getArguments().getString("userId"));
        param.put("barCode", getArguments().getString("barCode"));
        param.put("workflowType", getArguments().getString("workflowType"));
        HttpManager.getInstance().requestResultForm(CommonValues.REQ_EXPENSEREQUEST_DETAIL, param, EnterExpenseDetailEntity.class, new HttpManager.ResultCallback<EnterExpenseDetailEntity>() {
            @Override
            public void onSuccess(final String json, final EnterExpenseDetailEntity entity) {
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
            public void onFailure(String msg) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "请检查网络", Toast.LENGTH_SHORT).show();
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

    public EnterExpenseDetailEntity.RetDataBean getEntity() {
        return entity;
    }

    public void setEntity(EnterExpenseDetailEntity.RetDataBean entity) {
        this.entity = entity;
    }

    public String getSN() {
        return SN;
    }
}
