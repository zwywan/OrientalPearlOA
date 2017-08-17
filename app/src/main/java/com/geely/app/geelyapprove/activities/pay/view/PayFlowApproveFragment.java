package com.geely.app.geelyapprove.activities.pay.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.geely.app.geelyapprove.activities.pay.bean.PayFlowDetailBean;
import com.geely.app.geelyapprove.common.application.GeelyApp;
import com.geely.app.geelyapprove.common.entity.AttachmentListEntity;
import com.geely.app.geelyapprove.common.entity.DataListEntity;
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
 * Created by guiluXu on 2016/9/27.
 */
public class PayFlowApproveFragment extends PayFlowDetailFragment {

    private List<DataListEntity> ExpenditureTyoeList;
    private List<DataListEntity> TallerList;
    private PayFlowDetailBean.RetDataBean retDataBean;
    private boolean paperReceive;
    private boolean finance;
    private boolean cashier;
    private boolean glAccount;
    private DecimalFormat df;

    public PayFlowApproveFragment() {
    }

    public static PayFlowApproveFragment newInstance(Bundle bundle) {
        PayFlowApproveFragment fragment = new PayFlowApproveFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        df = new DecimalFormat("######0.00");
        getBottomTitles();
    }

    @Override
    public List<Group> getGroupList() {
        List<Group> groups = super.getGroupList();
        retDataBean = getEntity();
        if (retDataBean == null) return new ArrayList<>();
        //单据接收
        paperReceive = retDataBean.getIsKeyAuditNodeForApprove().isPaperReceive();
        List<HandInputGroup.Holder> sHolder = groups.get(0).getHolders();
        if(paperReceive){
            SimpleDateFormat formatter =  new SimpleDateFormat("yyyy-MM-dd");
            Date  curDate  = new  Date(System.currentTimeMillis());
            String  str  =  formatter.format(curDate);
            sHolder.add(new HandInputGroup.Holder(this.getString(R.string.Received_Date), true, false,str, HandInputGroup.VALUE_TYPE.DATE));
        }
        //财务审核
        finance = retDataBean.getIsKeyAuditNodeForApprove().isFinance();
        if (finance){
            sHolder.add(new HandInputGroup.Holder(this.getString(R.string.Audit_Total_Amount), true, false, "/" + retDataBean.getDetailData().getAuditPaymentAmount(), HandInputGroup.VALUE_TYPE.DOUBLE));
        }
        //总账
        glAccount = retDataBean.getIsKeyAuditNodeForApprove().isGLAccount();
        //出纳
        cashier = retDataBean.getIsKeyAuditNodeForApprove().isCashier();
        if(cashier){
            sHolder.add(new HandInputGroup.Holder(this.getString(R.string.Payment_Amount_of_other_ways), true, false, "0", HandInputGroup.VALUE_TYPE.DOUBLE));
            sHolder.add(new HandInputGroup.Holder(this.getString(R.string.Capital_Platform_Payment_Amount), true, false,"/" + retDataBean.getDetailData().getAuditPaymentAmount(), HandInputGroup.VALUE_TYPE.DOUBLE));
            sHolder.add(new HandInputGroup.Holder(this.getString(R.string.Expenditure_Type), true, false, "/" + this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.SELECT));
            sHolder.add(new HandInputGroup.Holder(this.getString(R.string.Cashier), true, false,"/" + this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.SELECT));
            loadFinance();
        }
        if (glAccount) {
            sHolder.add(new HandInputGroup.Holder(this.getString(R.string.Payment_Amount_of_other_ways), true, false, retDataBean.getDetailData().getCashPayAmount().isEmpty()?"0":retDataBean.getDetailData().getCashPayAmount(), HandInputGroup.VALUE_TYPE.TEXT));
            sHolder.add(new HandInputGroup.Holder(this.getString(R.string.Capital_Platform_Payment_Amount), true, false, retDataBean.getDetailData().getPlatPayAmount().isEmpty()?"0":retDataBean.getDetailData().getPlatPayAmount(), HandInputGroup.VALUE_TYPE.TEXT));
            sHolder.add(new HandInputGroup.Holder(this.getString(R.string.Expenditure_Type), true, false, retDataBean.getDetailData().getExpenditureType(), HandInputGroup.VALUE_TYPE.TEXT));
            sHolder.add(new HandInputGroup.Holder(this.getString(R.string.Cashier), true, false, retDataBean.getDetailData().getCashierName(), HandInputGroup.VALUE_TYPE.TEXT));
            if (Double.parseDouble(retDataBean.getDetailData().getPlatPayAmount().isEmpty()?"0":retDataBean.getDetailData().getPlatPayAmount())!=0){
                sHolder.add(new HandInputGroup.Holder(this.getString(R.string.Pay_Status), true, false, retDataBean.getDetailData().getPayStatusName(), HandInputGroup.VALUE_TYPE.TEXT));
                if (retDataBean.getDetailData().getPayStatus().equals("1")){
                sHolder.add(new HandInputGroup.Holder(this.getString(R.string.Paying_Bank_Account), true, false, retDataBean.getDetailData().getFundReturnBankAccount(), HandInputGroup.VALUE_TYPE.TEXT));
                sHolder.add(new HandInputGroup.Holder(this.getString(R.string.Paying_Bank_Name), true, false, retDataBean.getDetailData().getFundReturnBankName(), HandInputGroup.VALUE_TYPE.TEXT));
                sHolder.add(new HandInputGroup.Holder(this.getString(R.string.Payment_Description), true, false, retDataBean.getDetailData().getFundReturnNote(), HandInputGroup.VALUE_TYPE.TEXT));
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
    public void onClickItemContentSetter(HandInputGroup.Holder holder) {
        if (holder.getType() == HandInputGroup.VALUE_TYPE.DATE) {
            showDateTimePicker(holder,false);
        }else if(holder.getKey().equals(this.getString(R.string.Expenditure_Type))){
            showSelector(holder,DataUtil.getDescr(ExpenditureTyoeList));
        }else if(holder.getKey().equals(this.getString(R.string.Cashier))){
            showSelector(holder,DataUtil.getDescr(TallerList));
        }
        if (holder.getType() == HandInputGroup.VALUE_TYPE.SELECT) {
            if (holder.getKey().equals(this.getString(R.string.Select_Attachments))) {
                AttachmentListEntity entity = (AttachmentListEntity) holder.getValue();
                lookAttachmentInDetailFragment(entity);
            }
        }
    }

    @Override
    public void onBottomButtonsClick(String title, List<Group> groups) {
        setButtonllEnable(false);
        if (title.equals("转办")){
            requestForPerson(title);
            return;
        }
        String over = isOver(groups);
        if (!title.equals(this.getString(R.string.Reject)) && over != null){
            ToastUtil.showToast(getContext(),(this.getString(R.string.Please_Fill) + over));
            setButtonllEnable(true);
            return;
        }else {
            Map<String, Object> param = CommonValues.getCommonParams(getActivity());
            PayFlowDetailBean.RetDataBean.DetailDataBean detailData = getEntity().getDetailData();
            if(paperReceive){
                detailData.setBillPaperReceiveDate(getDisplayValueByKey(this.getString(R.string.Received_Date)).getRealValue());
                detailData.setUpdateBy(GeelyApp.getLoginEntity().getUserId());
                detailData.setUpdateTime(DataUtil.parseDateNormalString(new Date()));
                String mainData = GsonUtil.getJson(detailData);
                param.put("mainData", mainData);
                param.put("detailData", "");
                param.put("SN", getArguments().getString("SN"));
                applyApprove(CommonValues.REQ_PAYMENT_APPROVE, param, title);
            }else if(finance){
                if (Double.parseDouble(getDisplayValueByKey(this.getString(R.string.Audit_Total_Amount)).getRealValue().isEmpty()?"0":getDisplayValueByKey(this.getString(R.string.Audit_Total_Amount)).getRealValue()) > Double.parseDouble(retDataBean.getDetailData().getPaymentAmount().isEmpty()? "0" : retDataBean.getDetailData().getPaymentAmount())){
                    ToastUtil.showToast(getContext(),"实报金额不能大于付款金额");
                    setButtonllEnable(true);
                    return;
                }
                detailData.setAuditPaymentAmount(getDisplayValueByKey(this.getString(R.string.Audit_Total_Amount)).getRealValue().isEmpty()?"0":getDisplayValueByKey(this.getString(R.string.Audit_Total_Amount)).getRealValue());
                detailData.setUpdateBy(GeelyApp.getLoginEntity().getUserId());
                detailData.setUpdateTime(DataUtil.parseDateNormalString(new Date()));
                String mainData = GsonUtil.toJson(detailData, PayFlowDetailBean.RetDataBean.DetailDataBean.class);
                param.put("mainData", mainData);
                param.put("detailData", "");
                param.put("SN", getArguments().getString("SN"));
                applyApprove(CommonValues.REQ_PAYMENT_APPROVE, param, title);
            }else if(cashier){
                double CashPayAmount = Double.parseDouble(getDisplayValueByKey(this.getString(R.string.Payment_Amount_of_other_ways)).getRealValue().isEmpty()?"0":getDisplayValueByKey(this.getString(R.string.Payment_Amount_of_other_ways)).getRealValue());
                double PlatPayAmount = Double.parseDouble(getDisplayValueByKey(this.getString(R.string.Capital_Platform_Payment_Amount)).getRealValue().isEmpty()?"0":getDisplayValueByKey(this.getString(R.string.Capital_Platform_Payment_Amount)).getRealValue());
                if((CashPayAmount + PlatPayAmount != Double.parseDouble(retDataBean.getDetailData().getAuditPaymentAmount().isEmpty()?"0":retDataBean.getDetailData().getAuditPaymentAmount())) && !title.equals(this.getString(R.string.Reject))){
                    ToastUtil.showToast(getContext(),this.getString(R.string.Payment_Amount_of_other_ways) + " + " + this.getString(R.string.Capital_Platform_Payment_Amount) + "  必须等于实报金额");
                    setButtonllEnable(true);
                    return;
                }else {
                    detailData.setCashPayAmount(getDisplayValueByKey(this.getString(R.string.Payment_Amount_of_other_ways)).getRealValue());
                    detailData.setPlatPayAmount(getDisplayValueByKey(this.getString(R.string.Capital_Platform_Payment_Amount)).getRealValue().equals("") ? retDataBean.getDetailData().getAuditPaymentAmount() : getDisplayValueByKey(this.getString(R.string.Capital_Platform_Payment_Amount)).getRealValue());
                    detailData.setExpenditureType(getDisplayValueByKey(this.getString(R.string.Expenditure_Type)).getRealValue());
                    detailData.setCashierName(getDisplayValueByKey(this.getString(R.string.Cashier)).getRealValue());
                    detailData.setCashier(DataUtil.getDicodeByDescr(TallerList,getDisplayValueByKey(this.getString(R.string.Cashier)).getRealValue()));
                    detailData.setUpdateBy(GeelyApp.getLoginEntity().getUserId());
                    detailData.setUpdateTime(DataUtil.parseDateNormalString(new Date()));
                    String mainData = GsonUtil.toJson(detailData, PayFlowDetailBean.RetDataBean.DetailDataBean.class);
                    param.put("mainData", mainData);
                    param.put("detailData", "");
                    param.put("SN", getArguments().getString("SN"));
                    applyApprove(CommonValues.REQ_PAYMENT_APPROVE, param, title);
                }
            }else {
                detailData.setUpdateBy(GeelyApp.getLoginEntity().getUserId());
                detailData.setUpdateTime(DataUtil.parseDateNormalString(new Date()));
                String mainData = GsonUtil.toJson(detailData, PayFlowDetailBean.RetDataBean.DetailDataBean.class);
                param.put("mainData", mainData);
                param.put("detailData", "");
                param.put("SN", getArguments().getString("SN"));
                applyApprove(CommonValues.REQ_PAYMENT_APPROVE, param, title);
            }
        }

    }
    private void loadFinance() {
        HttpManager manager = HttpManager.getInstance();
        Map<String, Object> params = CommonValues.getCommonParams(getActivity());

        // 支出类别
        manager.requestResultForm(CommonValues.GET_EXPENDITURETYPE_LIST, params, ExpenseDraftListEntity.class, new HttpManager.ResultCallback<ExpenseDraftListEntity>() {
            @Override
            public void onSuccess(String content, ExpenseDraftListEntity expenseTypeEntity) {
                ExpenditureTyoeList = expenseTypeEntity.getRetData();
            }

            @Override
            public void onFailure(String content) {

            }
        });

        //出纳
        manager.requestResultForm(CommonValues.GET_TALLER_LIST, params, ExpenseDraftListEntity.class, new HttpManager.ResultCallback<ExpenseDraftListEntity>() {
            @Override
            public void onSuccess(String content, ExpenseDraftListEntity expenseTypeEntity) {
                TallerList = expenseTypeEntity.getRetData();
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
                                        bottomString[i] = PayFlowApproveFragment.this.getString(R.string.Reject);
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
