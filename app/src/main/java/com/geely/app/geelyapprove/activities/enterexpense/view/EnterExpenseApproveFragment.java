package com.geely.app.geelyapprove.activities.enterexpense.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.geely.app.geelyapprove.activities.enterexpense.Entity.EnterExpenseDetailEntity;
import com.geely.app.geelyapprove.common.application.GeelyApp;
import com.geely.app.geelyapprove.common.entity.AttachmentListEntity;
import com.geely.app.geelyapprove.common.entity.DataListEntity;
import com.geely.app.geelyapprove.common.entity.DictionaryEntity;
import com.geely.app.geelyapprove.common.entity.ExpenseDraftListEntity;
import com.geely.app.geelyapprove.common.entity.ProcessActionEmtity;
import com.geely.app.geelyapprove.common.utils.CommonValues;
import com.geely.app.geelyapprove.common.utils.DataUtil;
import com.geely.app.geelyapprove.common.utils.GsonUtil;
import com.geely.app.geelyapprove.common.utils.ToastUtil;
import com.geely.app.geelyapprove.common.view.HandInputGroup;
import com.geely.app.geelyapprove.datamanage.HttpManager;
import com.geely.app.orientalpearl.R;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Oliver on 2016/11/18.
 */
public class EnterExpenseApproveFragment extends EnterExpenseDetailFragment {

    private boolean paperReceive;
    private boolean finance;
    private boolean cashier;
    private EnterExpenseDetailEntity.RetDataBean retDataBean;
    private List<DataListEntity> draftGenerateAccountingVouchersTypes,draftExpenseType,dicExpenditureType,dicTaller;
    private boolean glAccount;
    private Map<String, Object> param;
    private DecimalFormat df;

    public EnterExpenseApproveFragment() {
        super();
    }

    public static EnterExpenseApproveFragment newInstance(Bundle bundle) {
        EnterExpenseApproveFragment fragment = new EnterExpenseApproveFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public List<Group> getGroupList() {
        List<Group> groups = super.getGroupList();
        retDataBean = getEntity();
        if (this.retDataBean == null) return new ArrayList<>();
        paperReceive = retDataBean.getIsKeyAuditNodeForApprove().isPaperReceive();//单据接收
        finance = retDataBean.getIsKeyAuditNodeForApprove().isFinance(); //财务审核
        glAccount = retDataBean.getIsKeyAuditNodeForApprove().isGLAccount();
        cashier = retDataBean.getIsKeyAuditNodeForApprove().isCashier(); //出纳
        List<HandInputGroup.Holder> holders = groups.get(0).getHolders();
        if (finance) {
            loadFinance();
            holders.add(new HandInputGroup.Holder(this.getString(R.string.Audit_Total_Amount), true, false,"/" + retDataBean.getDetailData().getAmount(), HandInputGroup.VALUE_TYPE.DOUBLE));
            holders.add(new HandInputGroup.Holder(this.getString(R.string.Is_Render_Accountant), true, false, this.getString(R.string.No), HandInputGroup.VALUE_TYPE.BUTTONS));
            holders.add(new HandInputGroup.Holder(this.getString(R.string.Generate_Accounting_Vouchers_Type), false, false, "", HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
            holders.add(new HandInputGroup.Holder(this.getString(R.string.Is_Compliance), true, false, retDataBean.getDetailData().isIsCompliance()?this.getString(R.string.Yes):this.getString(R.string.No), HandInputGroup.VALUE_TYPE.BUTTONS).setEditable(!retDataBean.getDetailData().getIsComplianceForApp().equals("0")));
            holders.add(new HandInputGroup.Holder(this.getString(R.string.ExpenseType), true, false, "/" + this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.SELECT));
        }
        if(paperReceive){
            SimpleDateFormat formatter =  new SimpleDateFormat("yyyy-MM-dd");
            Date  curDate  = new  Date(System.currentTimeMillis());
            String  str  =  formatter.format(curDate);
            holders.add(new HandInputGroup.Holder(this.getString(R.string.Received_Date), true, true,str, HandInputGroup.VALUE_TYPE.DATE));
        }
        if (cashier) {
            loadCashier();
            holders.add(new HandInputGroup.Holder(this.getString(R.string.Cash_Pay_Amount), false, false, "/0.00", HandInputGroup.VALUE_TYPE.DOUBLE));
            if (retDataBean.getDetailData().isIsOffsetLoan()){
                holders.add(new HandInputGroup.Holder(this.getString(R.string.Dealings_Pay_Amount), false, false, "/0.00", HandInputGroup.VALUE_TYPE.DOUBLE));
            }
            holders.add(new HandInputGroup.Holder(this.getString(R.string.Capital_Platform_Payment_Amount), false, false, "/" + retDataBean.getDetailData().getAuditTotalAmount(), HandInputGroup.VALUE_TYPE.DOUBLE));
            holders.add(new HandInputGroup.Holder(this.getString(R.string.Expenditure_Type), true, false, "/" + this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.SELECT));
            holders.add(new HandInputGroup.Holder(this.getString(R.string.Cashier), true, false, "/" + this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.SELECT));
        }
        if (glAccount) {
            holders.add(new HandInputGroup.Holder(this.getString(R.string.Cash_Pay_Amount), true, false, retDataBean.getDetailData().getCashPayAmount().isEmpty()?"0":retDataBean.getDetailData().getCashPayAmount(), HandInputGroup.VALUE_TYPE.TEXT));
            if (retDataBean.getDetailData().isIsOffsetLoan()){
                holders.add(new HandInputGroup.Holder(this.getString(R.string.Dealings_Pay_Amount), false, false, "/0.00", HandInputGroup.VALUE_TYPE.DOUBLE));
            }
            holders.add(new HandInputGroup.Holder(this.getString(R.string.Capital_Platform_Payment_Amount), true, false, retDataBean.getDetailData().getPlatPayAmount().isEmpty()?"0":retDataBean.getDetailData().getPlatPayAmount(), HandInputGroup.VALUE_TYPE.TEXT));
            holders.add(new HandInputGroup.Holder(this.getString(R.string.Expenditure_Type), true, false, retDataBean.getDetailData().getExpenditureType(), HandInputGroup.VALUE_TYPE.TEXT));
            holders.add(new HandInputGroup.Holder(this.getString(R.string.Cashier), true, false, retDataBean.getDetailData().getCashierName(), HandInputGroup.VALUE_TYPE.TEXT));
            if (retDataBean.getDetailData().isIsGenerateAccountingVouchers()){
                holders.add(new HandInputGroup.Holder(this.getString(R.string.Voucher_Number), true, false, retDataBean.getDetailData().getVoucherNumber(), HandInputGroup.VALUE_TYPE.TEXT));
            }
            if (Double.parseDouble(retDataBean.getDetailData().getPlatPayAmount().isEmpty()?"0":retDataBean.getDetailData().getPlatPayAmount())!=0){
                holders.add(new HandInputGroup.Holder(this.getString(R.string.Pay_Status), true, false, retDataBean.getDetailData().getPayStatusName(), HandInputGroup.VALUE_TYPE.TEXT));
                if (retDataBean.getDetailData().getPayStatus().equals("1")){
                holders.add(new HandInputGroup.Holder(this.getString(R.string.Paying_Bank_Account), true, false, retDataBean.getDetailData().getFundReturnBankAccount(), HandInputGroup.VALUE_TYPE.TEXT));
                holders.add(new HandInputGroup.Holder(this.getString(R.string.Paying_Bank_Name), true, false, retDataBean.getDetailData().getFundReturnBankName(), HandInputGroup.VALUE_TYPE.TEXT));
                holders.add(new HandInputGroup.Holder(this.getString(R.string.Payment_Description), true, false, retDataBean.getDetailData().getFundReturnNote(), HandInputGroup.VALUE_TYPE.TEXT));
                }
            }

            //根据资金平台付款状态改按钮
            if (retDataBean.getDetailData().getPlatPayAmount().isEmpty() || Double.parseDouble(retDataBean.getDetailData().getPlatPayAmount()) == 0){
                setButtonsTitles(new String[]{"同意"});
            }else {
                if (retDataBean.getDetailData().getPayStatus().equals("1")){
                    setButtonsTitles(new String[]{"同意"});
                }else if (retDataBean.getDetailData().getPayStatus().equals("2")){
                    setButtonsTitles(new String[]{this.getString(R.string.Reject)});
                }
            }
        }
        return groups;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        df = new DecimalFormat("######0.00");
        getBottomTitles();
    }


    @Override
    public void onClickItemContentSetter(HandInputGroup.Holder holder) {
        if (holder.getType() == HandInputGroup.VALUE_TYPE.SELECT) {
            if (holder.getKey().equals(this.getString(R.string.Select_Attachments))) {
                AttachmentListEntity entity = (AttachmentListEntity) holder.getValue();
                lookAttachmentInDetailFragment(entity);
            }
        }
        if (holder.getType() == HandInputGroup.VALUE_TYPE.DATE) {
            showDateTimePicker(holder,false);
        }else if(holder.getKey().equals(this.getString(R.string.Is_Render_Accountant))){
            checkedButton(holder, new OnSelectedResultCallback() {
                @Override
                public void onSelected(Group ownGroup, HandInputGroup.Holder holder, int mainIndex, int itemIndex) {
                    HandInputGroup.Holder holder1 = getDisplayValueByKey(EnterExpenseApproveFragment.this.getString(R.string.Generate_Accounting_Vouchers_Type));
                    if (holder.getRealValue().equals(EnterExpenseApproveFragment.this.getString(R.string.No))) {
                        holder1.setHasIndicator(false);
                        holder1.setDispayValue("").setType(HandInputGroup.VALUE_TYPE.TEXTFILED);
                        holder1.setEditable(false);
                    } else if (holder.getRealValue().equals(EnterExpenseApproveFragment.this.getString(R.string.Yes))){
                        holder1.setHasIndicator(true);
                        holder1.setDispayValue("/" + EnterExpenseApproveFragment.this.getString(R.string.Please_Select)).setEditable(true).setType(HandInputGroup.VALUE_TYPE.SELECT);
                    }
                    notifyDataSetChanged();
                }
            });

        }else if (holder.getKey().equals(this.getString(R.string.Generate_Accounting_Vouchers_Type))) {
            showSelector(holder, DataUtil.getDescr(draftGenerateAccountingVouchersTypes));
        } else if (holder.getKey().equals(this.getString(R.string.ExpenseType))) {
            showSelector(holder, DataUtil.getDescr(draftExpenseType));
        }else if(holder.getKey().equals(this.getString(R.string.Is_Compliance))){
            checkedButton(holder);
        }else if (holder.getKey().equals(this.getString(R.string.Expenditure_Type))) {
            showSelector(holder, DataUtil.getDescr(dicExpenditureType));
        } else if (holder.getKey().equals(this.getString(R.string.Cashier))) {
            showSelector(holder, DataUtil.getDescr(dicTaller));
        }
    }

    @Override
    public void onBottomButtonsClick(final String title, List<Group> groups) {
        setButtonllEnable(false);
        if (title.equals("转办")){
            requestForPerson(title);
            return;
        }
        String over = isOver(groups);
        if (title.equals("同意") && over != null){
            ToastUtil.showToast(getContext(),(this.getString(R.string.Please_Fill) + over));
            setButtonllEnable(true);
            return;
        }else {
            param = CommonValues.getCommonParams(getActivity());
            EnterExpenseDetailEntity.RetDataBean.DetailDataBean detailData = getEntity().getDetailData();
            if (paperReceive) {
                detailData.setBillPaperReceiveDate(getDisplayValueByKey(this.getString(R.string.Received_Date)).getRealValue());
                detailData.setUpdateBy(GeelyApp.getLoginEntity().getUserId());
                detailData.setUpdateTime(DataUtil.parseDateNormalString(new Date()));
                String mainData = GsonUtil.getJson(detailData);
                param.put("mainData", mainData);
                param.put("detailData", "");
                param.put("SN", getArguments().getString("SN"));
            } else if (finance) {
                if (Double.parseDouble(getDisplayValueByKey(this.getString(R.string.Audit_Total_Amount)).getRealValue().isEmpty()?"0":getDisplayValueByKey(this.getString(R.string.Audit_Total_Amount)).getRealValue()) > Double.parseDouble(retDataBean.getDetailData().getAmount().isEmpty()?"0":retDataBean.getDetailData().getAmount())){
                    ToastUtil.showToast(getContext(),"实报金额不能大于招待金额");
                    setButtonllEnable(true);
                    return;
                }
                detailData.setAuditTotalAmount(getDisplayValueByKey(this.getString(R.string.Audit_Total_Amount)).getRealValue().equals("") ? "0" : getDisplayValueByKey(this.getString(R.string.Audit_Total_Amount)).getRealValue());
                detailData.setIsGenerateAccountingVouchers(getDisplayValueByKey(this.getString(R.string.Is_Render_Accountant)).getRealValue().equals(this.getString(R.string.Yes)));
                detailData.setGenerateAccountingVouchersType(DataUtil.getDicIdByDescr(draftGenerateAccountingVouchersTypes, getDisplayValueByKey(this.getString(R.string.Generate_Accounting_Vouchers_Type)).getRealValue()));
                detailData.setIsCompliance(getDisplayValueByKey(this.getString(R.string.Is_Compliance)).getRealValue().equals(this.getString(R.string.Yes)));
                detailData.setExpenseType(getDisplayValueByKey(this.getString(R.string.ExpenseType)).getRealValue());
                detailData.setUpdateBy(GeelyApp.getLoginEntity().getUserId());
                detailData.setUpdateTime(DataUtil.parseDateNormalString(new Date()));
                String mainData = GsonUtil.getJson(detailData);
                param.put("mainData", mainData);
                param.put("detailData", "");
                param.put("SN", getArguments().getString("SN"));
            } else if (cashier) {
                if (retDataBean.getDetailData().isIsOffsetLoan()){
                    double CashPayAmount = Double.parseDouble(getDisplayValueByKey(this.getString(R.string.Cash_Pay_Amount)).getRealValue().isEmpty() ? "0" : getDisplayValueByKey(this.getString(R.string.Cash_Pay_Amount)).getRealValue());
                    double DealingsPayAmount = Double.parseDouble(getDisplayValueByKey(this.getString(R.string.Dealings_Pay_Amount)).getRealValue().isEmpty() ? "0" : getDisplayValueByKey(this.getString(R.string.Dealings_Pay_Amount)).getRealValue());
                    double PlatPayAmount = Double.parseDouble(getDisplayValueByKey(this.getString(R.string.Capital_Platform_Payment_Amount)).getRealValue().equals("") ? "0" : getDisplayValueByKey(this.getString(R.string.Capital_Platform_Payment_Amount)).getRealValue());
                    if (CashPayAmount + PlatPayAmount + DealingsPayAmount != Double.parseDouble(retDataBean.getDetailData().getAuditTotalAmount().isEmpty()?"0":retDataBean.getDetailData().getAuditTotalAmount())) {
                        if (!title.equals(this.getString(R.string.Reject))){
                            ToastUtil.showToast(getContext(), this.getString(R.string.Cash_Pay_Amount) + " + " + this.getString(R.string.Capital_Platform_Payment_Amount) + " + " + this.getString(R.string.Dealings_Pay_Amount) + "  必须等于实报金额");
                            setButtonllEnable(true);
                            return;
                        }
                    }
                }else {
                    double CashPayAmount = Double.parseDouble(getDisplayValueByKey(this.getString(R.string.Cash_Pay_Amount)).getRealValue().isEmpty() ? "0" : getDisplayValueByKey(this.getString(R.string.Cash_Pay_Amount)).getRealValue());
                    double PlatPayAmount = Double.parseDouble(getDisplayValueByKey(this.getString(R.string.Capital_Platform_Payment_Amount)).getRealValue().equals("") ? "0" : getDisplayValueByKey(this.getString(R.string.Capital_Platform_Payment_Amount)).getRealValue());
                    if (CashPayAmount + PlatPayAmount != Double.parseDouble(retDataBean.getDetailData().getAuditTotalAmount().isEmpty()?"0":retDataBean.getDetailData().getAuditTotalAmount())) {
                        if (!title.equals(this.getString(R.string.Reject))){
                            ToastUtil.showToast(getContext(), this.getString(R.string.Cash_Pay_Amount) + " + " + this.getString(R.string.Capital_Platform_Payment_Amount) + "  必须等于实报金额");
                            setButtonllEnable(true);
                            return;
                        }
                    }
                }
                if (retDataBean.getDetailData().isIsOffsetLoan()){
                    detailData.setDealingsPayAmount(getDisplayValueByKey(this.getString(R.string.Dealings_Pay_Amount)).getRealValue().isEmpty()?"0":getDisplayValueByKey(this.getString(R.string.Dealings_Pay_Amount)).getRealValue());
                }
                detailData.setCashPayAmount(getDisplayValueByKey(this.getString(R.string.Cash_Pay_Amount)).getRealValue().isEmpty()?"0":getDisplayValueByKey(this.getString(R.string.Cash_Pay_Amount)).getRealValue());
                detailData.setPlatPayAmount(getDisplayValueByKey(this.getString(R.string.Capital_Platform_Payment_Amount)).getRealValue().equals("") ? "0": getDisplayValueByKey(this.getString(R.string.Capital_Platform_Payment_Amount)).getRealValue());
                detailData.setExpenditureType(getDisplayValueByKey(this.getString(R.string.Expenditure_Type)).getRealValue());
                detailData.setCashierName(getDisplayValueByKey(this.getString(R.string.Cashier)).getRealValue());
                detailData.setCashier(DataUtil.getDicodeByDescr(dicTaller, getDisplayValueByKey(this.getString(R.string.Cashier)).getRealValue()));
                detailData.setUpdateBy(GeelyApp.getLoginEntity().getUserId());
                detailData.setUpdateTime(DataUtil.parseDateNormalString(new Date()));
                String mainData = GsonUtil.getJson(detailData);
                param.put("mainData", mainData);
                param.put("detailData", "");
                param.put("SN", getArguments().getString("SN"));
            } else {
                detailData.setUpdateBy(GeelyApp.getLoginEntity().getUserId());
                detailData.setUpdateTime(DataUtil.parseDateNormalString(new Date()));
                String mainData = GsonUtil.getJson(detailData);
                param.put("mainData", mainData);
                param.put("detailData", "");
                param.put("SN", getArguments().getString("SN"));
            }
            applyApprove(CommonValues.REQ_EXPENSEREQUEST_APPROVE, param, title);

        }

    }
    private void loadFinance() {
        HttpManager manager = HttpManager.getInstance();
        Map<String, Object> params = CommonValues.getCommonParams(getActivity());

        // 费用归类 4212
        params.put("code", 4240);
        manager.requestResultForm(CommonValues.GET_DICTIONARY_LIST, params, DictionaryEntity.class, new HttpManager.ResultCallback<DictionaryEntity>() {
            @Override
            public void onSuccess(String content, DictionaryEntity expenseTypeEntity) {
                draftExpenseType = expenseTypeEntity.getRetData().get(0).getDataList();
            }

            @Override
            public void onFailure(String content) {

            }
        });

        //凭证生成类型 2144
        params.put("code", 4212);
        manager.requestResultForm(CommonValues.GET_DICTIONARY_LIST, params, DictionaryEntity.class, new HttpManager.ResultCallback<DictionaryEntity>() {
            @Override
            public void onSuccess(String content, DictionaryEntity expenseTypeEntity) {
                draftGenerateAccountingVouchersTypes = expenseTypeEntity.getRetData().get(0).getDataList();
            }

            @Override
            public void onFailure(String content) {

            }
        });

    }
    private void loadCashier() {
        HttpManager manager = HttpManager.getInstance();
        Map<String, Object> params = CommonValues.getCommonParams(getActivity());
        //出纳人
        manager.requestResultForm(CommonValues.DICTIONARY_TALLER, params, ExpenseDraftListEntity.class, new HttpManager.ResultCallback<ExpenseDraftListEntity>() {
            @Override
            public void onSuccess(String content, ExpenseDraftListEntity expenseTypeEntity) {
                dicTaller = expenseTypeEntity.getRetData();
            }

            @Override
            public void onFailure(String content) {

            }
        });

        //支出类别
        manager.requestResultForm(CommonValues.DICTIONARY_EXPENDITURE_TYPE, params, ExpenseDraftListEntity.class, new HttpManager.ResultCallback<ExpenseDraftListEntity>() {
            @Override
            public void onSuccess(String content, ExpenseDraftListEntity expenseTypeEntity) {
                dicExpenditureType = expenseTypeEntity.getRetData();
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
                Log.e("BPM-ENTEREXPENSE","keyOfHolder----" + data.getStringExtra("keyOfHolder") + ",empId----" + data.getStringExtra("empid"));
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

    public void getBottomTitles() {
        Map<String, Object> param = CommonValues.getCommonParams(getActivity());
        param.put("barCode", getArguments().getString("barCode"));
        param.put("sn", getArguments().getString("SN"));
        param.put("identifier",getArguments().getString("WorkflowIdentifier"));
        HttpManager.getInstance().requestResultForm(CommonValues.GET_PROCESS_ACTION, param, ProcessActionEmtity.class, new HttpManager.ResultCallback<ProcessActionEmtity>() {
            @Override
            public void onSuccess(String content, final ProcessActionEmtity entity) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (entity != null && entity.getRetData() != null) {
                            if (entity.getCode().equals("100")) {
                                List<String> retData = entity.getRetData();
                                String[] bottomString = new String[retData.size()];
                                for (int i = 0; i < retData.size(); i++) {
                                    if (retData.get(i).equals("拒绝")){
                                        bottomString[i] = EnterExpenseApproveFragment.this.getString(R.string.Reject);
                                    }else if (retData.get(i).equals("转签")){
                                        bottomString[i] = "转办";
                                    }else {
                                        bottomString[i] = retData.get(i);
                                    }
                                }
                                if (retDataBean == null || !retDataBean.getIsKeyAuditNodeForApprove().isGLAccount()){
                                    setButtonsTitles(bottomString);
                                    notifyDataSetChanged();
                                }
                                return;
                            }
                        } else {
                            Toast.makeText(getActivity(), entity.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void onFailure(String content) {
            }
        });
    }
}
