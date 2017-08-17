package com.geely.app.geelyapprove.activities.cost.view;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.widget.Toast;

import com.geely.app.geelyapprove.activities.ItemActivity;
import com.geely.app.geelyapprove.activities.PageConfig;
import com.geely.app.geelyapprove.activities.cost.bean.CostDetailBean;
import com.geely.app.geelyapprove.activities.enterexpense.Entity.EnterExpenseOrdersEntity;
import com.geely.app.geelyapprove.common.application.GeelyApp;
import com.geely.app.geelyapprove.common.entity.AttachmentListEntity;
import com.geely.app.geelyapprove.common.entity.EmpDataEntity;
import com.geely.app.geelyapprove.common.entity.UserPhotoEntity;
import com.geely.app.geelyapprove.common.fragment.CommonFragment;
import com.geely.app.geelyapprove.common.utils.CommonValues;
import com.geely.app.geelyapprove.common.utils.DataUtil;
import com.geely.app.geelyapprove.common.view.HandInputGroup;
import com.geely.app.geelyapprove.common.view.HandToolbar;
import com.geely.app.geelyapprove.common.view.SubListLayout;
import com.geely.app.geelyapprove.datamanage.HttpManager;
import com.geely.app.orientalpearl.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**费用明细
 * Created by guiluXu on 2016/9/30.
 */
public class CostDetailFragment extends CommonFragment {

    private CostDetailBean.RetDataBean entity;
    private CostDetailBean.RetDataBean.DetailDataBean entityDetail;
    private EmpDataEntity entityEmp;
    private ArrayMap<String,Object> enterExpUnion = new ArrayMap<>();
    private String photo;

    public CostDetailFragment() {
    }
    public static CostDetailFragment newInstance(Bundle bundle) {
        CostDetailFragment fragment = new CostDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public List<Group> getGroupList() {
        if (entity == null)return new ArrayList<>();
        List<Group> groups = new ArrayList<>();
        boolean finance = entity.getIsKeyAuditNodeForDisplay().isFinance();
        boolean paperReceive = entity.getIsKeyAuditNodeForDisplay().isPaperReceive();
        boolean glAccount = entity.getIsKeyAuditNodeForDisplay().isGLAccount();
        boolean cashier = entity.getIsKeyAuditNodeForDisplay().isCashier();
        entityEmp = entity.getEmpData();
        entityDetail = entity.getDetailData();
        List<HandInputGroup.Holder> list = new ArrayList<>();
        list.add(new HandInputGroup.Holder(this.getString(R.string.Summary), true, false, entity.getDetailData().getSummary(), HandInputGroup.VALUE_TYPE.TEXT));
        list.add(new HandInputGroup.Holder("流程编号", true, false, getArguments().getString("barCode"), HandInputGroup.VALUE_TYPE.TEXT));
        list.add(new HandInputGroup.Holder(this.getString(R.string.Company_Name), true, false, entityEmp.getCompNameCN(), HandInputGroup.VALUE_TYPE.TEXT));
        list.add(new HandInputGroup.Holder(this.getString(R.string.Cost_Center), true, false, entityDetail.getCostCenterName(), HandInputGroup.VALUE_TYPE.TEXT));
        list.add(new HandInputGroup.Holder(this.getString(R.string.Expense_Cost), true, false, entity.getDetailData().getTotalAmount(), HandInputGroup.VALUE_TYPE.TEXT));
        if(paperReceive){
            if (!entity.getIsKeyAuditNodeForApprove().isPaperReceive()) {
                list.add(new HandInputGroup.Holder(this.getString(R.string.Received_Date), true, false, entity.getDetailData().getBillPaperReceiveDate().split(" ")[0], HandInputGroup.VALUE_TYPE.TEXT));
            }
        }
        if(finance){
            if (!entity.getIsKeyAuditNodeForApprove().isFinance()) {
                list.add(new HandInputGroup.Holder(this.getString(R.string.Audit_Total_Amount), false, false, entity.getDetailData().getAuditTotalAmount(), HandInputGroup.VALUE_TYPE.TEXT));
                list.add(new HandInputGroup.Holder(this.getString(R.string.Is_Render_Accountant), true, false, entity.getDetailData().isIsGenerateAccountingVouchers() ? this.getString(R.string.Yes) : this.getString(R.string.No), HandInputGroup.VALUE_TYPE.TEXT));
                if (entity.getDetailData().isIsGenerateAccountingVouchers()) {
                    list.add(new HandInputGroup.Holder(this.getString(R.string.Generate_Accounting_Vouchers_Type), true, false, entity.getDetailData().getGenerateAccountingVouchersTypeName(), HandInputGroup.VALUE_TYPE.TEXT));
                }
                list.add(new HandInputGroup.Holder(this.getString(R.string.ExpenseType), true, false, entity.getDetailData().getExpenseType(), HandInputGroup.VALUE_TYPE.TEXT));
            }
        }
        if (cashier){
            if (!entity.getIsKeyAuditNodeForApprove().isCashier()) {
                list.add(new HandInputGroup.Holder(this.getString(R.string.Cash_Pay_Amount), true, false, entity.getDetailData().getCashPayAmount().isEmpty()?"0":entity.getDetailData().getCashPayAmount(), HandInputGroup.VALUE_TYPE.TEXT));
                if (entity.getDetailData().isIsOffsetLoan()) {
                    list.add(new HandInputGroup.Holder(this.getString(R.string.Dealings_Pay_Amount), true, false, entity.getDetailData().getDealingsPayAmount().isEmpty()?"0":entity.getDetailData().getDealingsPayAmount(), HandInputGroup.VALUE_TYPE.TEXT));
                }
                list.add(new HandInputGroup.Holder(this.getString(R.string.Capital_Platform_Payment_Amount), true, false, entity.getDetailData().getPlatPayAmount().isEmpty()?"0":entity.getDetailData().getPlatPayAmount(), HandInputGroup.VALUE_TYPE.TEXT));
                list.add(new HandInputGroup.Holder(this.getString(R.string.Expenditure_Type), true, false, entity.getDetailData().getExpenditureType(), HandInputGroup.VALUE_TYPE.TEXT));
                list.add(new HandInputGroup.Holder(this.getString(R.string.Cashier), true, false, entity.getDetailData().getCashierName(), HandInputGroup.VALUE_TYPE.TEXT));
            }
        }
        if (glAccount){
            list.add(new HandInputGroup.Holder(this.getString(R.string.Cash_Pay_Amount), true, false, entity.getDetailData().getCashPayAmount().isEmpty()?"0":entity.getDetailData().getCashPayAmount(), HandInputGroup.VALUE_TYPE.TEXT));
            if (entity.getDetailData().isIsOffsetLoan()){
                list.add(new HandInputGroup.Holder(this.getString(R.string.Dealings_Pay_Amount), true, false, entity.getDetailData().getDealingsPayAmount().isEmpty()?"0":entity.getDetailData().getDealingsPayAmount(), HandInputGroup.VALUE_TYPE.TEXT));
            }
            list.add(new HandInputGroup.Holder(this.getString(R.string.Capital_Platform_Payment_Amount), true, false, entity.getDetailData().getPlatPayAmount().isEmpty()?"0":entity.getDetailData().getPlatPayAmount(), HandInputGroup.VALUE_TYPE.TEXT));
            list.add(new HandInputGroup.Holder(this.getString(R.string.Expenditure_Type), true, false, entity.getDetailData().getExpenditureType(), HandInputGroup.VALUE_TYPE.TEXT));
            list.add(new HandInputGroup.Holder(this.getString(R.string.Cashier), true, false, entity.getDetailData().getCashierName(), HandInputGroup.VALUE_TYPE.TEXT));
            if (entity.getDetailData().isIsGenerateAccountingVouchers()){
                list.add(new HandInputGroup.Holder(this.getString(R.string.Voucher_Number), true, false, entity.getDetailData().getVoucherNumber(), HandInputGroup.VALUE_TYPE.TEXT));
            }
            if (Double.parseDouble(entity.getDetailData().getPlatPayAmount().isEmpty()?"0":entity.getDetailData().getPlatPayAmount()) != 0){
                list.add(new HandInputGroup.Holder(this.getString(R.string.Pay_Status), true, false, entity.getDetailData().getPayStatusName(), HandInputGroup.VALUE_TYPE.TEXT));
                if (entity.getDetailData().getPayStatus().equals("1")) {
                    list.add(new HandInputGroup.Holder(this.getString(R.string.Paying_Bank_Account), true, false, entity.getDetailData().getFundReturnBankAccount(), HandInputGroup.VALUE_TYPE.TEXT));
                    list.add(new HandInputGroup.Holder(this.getString(R.string.Paying_Bank_Name), true, false, entity.getDetailData().getFundReturnBankName(), HandInputGroup.VALUE_TYPE.TEXT));
                    list.add(new HandInputGroup.Holder(this.getString(R.string.Payment_Description), true, false, entity.getDetailData().getFundReturnNote(), HandInputGroup.VALUE_TYPE.TEXT));
                }
            }
        }
        Group group = new Group("流程摘要-摘要内容", null, true, null, list).setrl(true).setv1(entity.getEmpData().getNameCN() + "(" + entity.getEmpData().getNameEN() + ")").setv2(entity.getEmpData().getPositionNameCN() + " " + entity.getEmpData().getEid());
        if ( photo != null && !photo.isEmpty()){
            group.setDrawable(photo);
        }
        groups.add(group);
        groups.add(new Group("流程摘要-摘要",null,true,null,null).setValue(entity.getHisData()).setrl(false));
        List<HandInputGroup.Holder> subHolder = new ArrayList<>();
        subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Summary), true, false, entity.getDetailData().getSummary(), HandInputGroup.VALUE_TYPE.TEXT));
        subHolder.add(new HandInputGroup.Holder("流程编号", true, false, getArguments().getString("barCode"), HandInputGroup.VALUE_TYPE.TEXT));
        subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Reimbursement_Person), true, false, entityEmp.getNameCN(), HandInputGroup.VALUE_TYPE.TEXT));
        subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Employee_ID), true, false, entityEmp.getEid(), HandInputGroup.VALUE_TYPE.TEXT));
        subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Grade), true, false, entityEmp.getGrade().trim(), HandInputGroup.VALUE_TYPE.TEXT));
        subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Position), true, false, entityEmp.getPositionNameCN(), HandInputGroup.VALUE_TYPE.TEXT));
        subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Company_Name), true, true, entityEmp.getCompNameCN(), HandInputGroup.VALUE_TYPE.TEXT));
        subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Cost_Center), true, true, entityDetail.getCostCenterName(), HandInputGroup.VALUE_TYPE.TEXT));
        subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Employee_ID), true, true, entityDetail.getPayeeId(), HandInputGroup.VALUE_TYPE.TEXT));
        subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Name), true, true, entityDetail.getPayeeName(), HandInputGroup.VALUE_TYPE.TEXT));
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
        subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Bank_Adress), true, false, entityDetail.getPayeeBankProvinceName() + " " + entityDetail.getPayeeBankCityName(), HandInputGroup.VALUE_TYPE.TEXT));
        subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Branch_Bank), true, true, entityDetail.getPayeeBranchBank(), HandInputGroup.VALUE_TYPE.TEXT));
        subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Has_Borrow), true, false, entityDetail.isIsOffsetLoan() ? this.getString(R.string.Yes) : this.getString(R.string.No), HandInputGroup.VALUE_TYPE.TEXT));
        subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Has_Buget), true, false, entityDetail.isHasBuget() ? this.getString(R.string.Yes) : this.getString(R.string.No), HandInputGroup.VALUE_TYPE.TEXT));
        String relateHospitalitOrder = entityDetail.getRelatedEntertainmentOrders();
        if (!relateHospitalitOrder.isEmpty()) {
            String[] split = relateHospitalitOrder.split(";");
            if (split.length > 1) {
                for (int i = 0; i < split.length; i++) {
                    for (CostDetailBean.RetDataBean.DetailDataBean.ExpenseDetailBean travelExpenseDetailBean : entityDetail.getExpenseDetail()) {
                        if (travelExpenseDetailBean.getEntertainmentBarCode().equals(split[i])) {
                            EnterExpenseOrdersEntity.RetDataBean.EntertainmentExpenseOrdersBean ent = new EnterExpenseOrdersEntity.RetDataBean.EntertainmentExpenseOrdersBean();
                            ent.setBarCode(travelExpenseDetailBean.getEntertainmentBarCode());
                            ent.setAmount(travelExpenseDetailBean.getExpenseCost());
                            enterExpUnion.put(split[i], ent);
                        }
                    }

                }
            } else if (split.length == 1) {
                for (CostDetailBean.RetDataBean.DetailDataBean.ExpenseDetailBean travelExpenseDetailBean : entityDetail.getExpenseDetail()) {
                    if (travelExpenseDetailBean.getEntertainmentBarCode().equals(split[0])) {
                        EnterExpenseOrdersEntity.RetDataBean.EntertainmentExpenseOrdersBean ent = new EnterExpenseOrdersEntity.RetDataBean.EntertainmentExpenseOrdersBean();
                        ent.setBarCode(travelExpenseDetailBean.getEntertainmentBarCode());
                        ent.setAmount(travelExpenseDetailBean.getExpenseCost());
                        enterExpUnion.put(split[0], ent);
                    }
                }
            }
        }
        subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Relate_Hospitalit_Order), false, true, "", HandInputGroup.VALUE_TYPE.SUB_LIST).setEditable(false).setValue(enterExpUnion.keySet()));
        subHolder.add(new HandInputGroup.Holder("报销说明", true, false, entityDetail.getRemark(), HandInputGroup.VALUE_TYPE.TEXT));
        groups.add(new Group("详细信息-" + this.getString(R.string.Basic_Information), null, true, null, subHolder));
        for (CostDetailBean.RetDataBean.DetailDataBean.ExpenseDetailBean bean : entityDetail.getExpenseDetail()) {
            List<HandInputGroup.Holder> subDetail = new ArrayList<>();
            subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Expense_Type), true, false, bean.getExpenseTypeName(), HandInputGroup.VALUE_TYPE.TEXT));
            subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Expense_Cost), true, false, bean.getExpenseCost().isEmpty() ? "0" : bean.getExpenseCost(), HandInputGroup.VALUE_TYPE.TEXT));
            if (finance){
                subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Audit_Amount), true, false, bean.getAuditAmount().isEmpty()? "0" : bean.getAuditAmount(), HandInputGroup.VALUE_TYPE.TEXT).setColor(Color.rgb(54,106,160)));
                subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Deductible_Or_Not), true, false, bean.isIsOffset()?this.getString(R.string.Yes):this.getString(R.string.No), HandInputGroup.VALUE_TYPE.TEXT));
                if (bean.isIsOffset()){
                    subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Tax_Rate), true, false,bean.getTaxRateIdName().isEmpty()?"0%":bean.getTaxRateIdName(), HandInputGroup.VALUE_TYPE.TEXT));
                    subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Amount_Without_Tax), true, false, bean.getAmountWithoutTax().isEmpty()?"0.00":bean.getAmountWithoutTax(), HandInputGroup.VALUE_TYPE.TEXT).setColor(Color.rgb(214,16,24)));
                    subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Amount_of_Tax), true, false, bean.getAmountWithTax().isEmpty()?"0.00":bean.getAmountWithTax(), HandInputGroup.VALUE_TYPE.TEXT).setColor(Color.rgb(214,16,24)));
                }else {
                    subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Tax_Rate), true, false,"", HandInputGroup.VALUE_TYPE.TEXT));
                    subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Amount_Without_Tax), true, false, "", HandInputGroup.VALUE_TYPE.TEXT).setColor(Color.rgb(214,16,24)));
                    subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Amount_of_Tax), true, false, "", HandInputGroup.VALUE_TYPE.TEXT).setColor(Color.rgb(214,16,24)));
                }

            }
            groups.add(new Group("详细信息-" + this.getString(R.string.Details_Information), null, true, null, subDetail));
        }
        List<HandInputGroup.Holder> subDetailTotal = new ArrayList<>();
        groups.add(new Group("详细信息-" + this.getString(R.string.Total), null, true, entity.getDetailData().getTotalAmount(), subDetailTotal));
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

    private void loadData() {
        Map<String, Object> param = CommonValues.getCommonParams(getActivity());
        param.put("userId",getArguments().getString("userId"));
        param.put("barCode", getArguments().getString("barCode"));
        param.put("workflowType", getArguments().getString("workflowType"));
        HttpManager.getInstance().requestResultForm(CommonValues.REQ_EXPENSE_APPROVE, param, CostDetailBean.class, new HttpManager.ResultCallback<CostDetailBean>() {
            @Override
            public void onSuccess(String content, final CostDetailBean entity) {
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
                            } else {
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

    public CostDetailBean.RetDataBean getEntity() {
        return entity;
    }

    public void setEntity(CostDetailBean.RetDataBean entity) {
        this.entity = entity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadData();
    }

    @Override
    public void onBarCodeChanged(int main, int index, HandInputGroup.Holder holder, String oldBarcode, SubListLayout.ActionType type, int id) {

        if(type == SubListLayout.ActionType.CLICK){
            DisplayItem(oldBarcode);
        }
    }

    private void DisplayItem(String barcode) {
        Intent intent = new Intent(getActivity(), ItemActivity.class);
        intent.putExtra(PageConfig.PAGE_CODE, PageConfig.PAGE_DISPLAY_EXPENSE_OFFER);
        Bundle bundle = new Bundle();
        bundle.putString("userId", GeelyApp.getLoginEntity().getUserId());
        bundle.putString("barCode", barcode);
        bundle.putString("workflowType", "EntertainmentExpenseRequest");
        intent.putExtra("data", bundle);
        startActivity(intent);
    }

}
