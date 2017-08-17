package com.geely.app.geelyapprove.activities.traveloffer.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.geely.app.geelyapprove.activities.traveloffer.bean.TravelOfferDetailBean;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by guiluXu on 2016/10/20.
 */
public class TravelApproveFragment extends TravelOfferDetailFragment{
    private TravelOfferDetailBean.RetDataBean retDataBean;
    private boolean paperReceive;
    private boolean cashier;
    private boolean finance;
    private List<DataListEntity> draftGenerateAccountingVouchersTypes,draftExpenseType,dicTaller,dicExpenditureType;
    private double transportCost;
    private double hotelCost;
    private boolean glAccount;

    public TravelApproveFragment() {
    }

    public static TravelApproveFragment newInstance(Bundle bundle){
        TravelApproveFragment fragment = new TravelApproveFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getBottomTitles();
    }

    @Override
    public List<Group> getGroupList() {
        List<Group> groups = super.getGroupList();
        retDataBean = getEntity();
        if (retDataBean == null) return new ArrayList<>();
        TravelOfferDetailBean.RetDataBean.DetailDataBean detailData = retDataBean.getDetailData();
        paperReceive = retDataBean.getIsKeyAuditNodeForApprove().isPaperReceive();
        finance = retDataBean.getIsKeyAuditNodeForApprove().isFinance();
        glAccount = retDataBean.getIsKeyAuditNodeForApprove().isGLAccount();
        cashier = retDataBean.getIsKeyAuditNodeForApprove().isCashier();
        //单据接收
        List<HandInputGroup.Holder> holders = groups.get(0).getHolders();
        if(paperReceive){
            SimpleDateFormat formatter =  new SimpleDateFormat("yyyy-MM-dd");
            Date  curDate  = new  Date(System.currentTimeMillis());
            String  str  =  formatter.format(curDate);
            holders.add(new HandInputGroup.Holder(this.getString(R.string.Received_Date), true, false,str, HandInputGroup.VALUE_TYPE.DATE));
        }
        //财务审核
        if (finance) {
            loadFinance();
            holders.add(new HandInputGroup.Holder(this.getString(R.string.Audit_Total_Amount), true, false,"/" + retDataBean.getDetailData().getTotalAmount(), HandInputGroup.VALUE_TYPE.DOUBLE).setEditable(false));
            holders.add(new HandInputGroup.Holder(this.getString(R.string.Is_Render_Accountant), true, false, this.getString(R.string.No), HandInputGroup.VALUE_TYPE.BUTTONS));
            holders.add(new HandInputGroup.Holder(this.getString(R.string.Generate_Accounting_Vouchers_Type), false, false, "/" + this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
            holders.add(new HandInputGroup.Holder(this.getString(R.string.Is_Compliance), true, false, retDataBean.getDetailData().isIsCompliance()?this.getString(R.string.Yes):this.getString(R.string.No), HandInputGroup.VALUE_TYPE.BUTTONS).setEditable(!retDataBean.getDetailData().getIsComplianceForApp().equals("0")));
            holders.add(new HandInputGroup.Holder(this.getString(R.string.ExpenseType), true, false, "/" + this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.SELECT));
            double ticketCost = 0;
            transportCost = 0;
            hotelCost = 0;
            double guestsCost = 0;
            double otherCost = 0;
            double allowance = 0;
            List<TravelOfferDetailBean.RetDataBean.DetailDataBean.TravelExpenseDetailBean> travelExpenseDetail = detailData.getTravelExpenseDetail();
            for (TravelOfferDetailBean.RetDataBean.DetailDataBean.TravelExpenseDetailBean travelExpenseDetailBean : travelExpenseDetail) {
                ticketCost += Double.parseDouble(travelExpenseDetailBean.getTicketCost().isEmpty()?"0":travelExpenseDetailBean.getTicketCost());
                transportCost += Double.parseDouble(travelExpenseDetailBean.getTransportCost().isEmpty()?"0":travelExpenseDetailBean.getTransportCost());
                hotelCost += Double.parseDouble(travelExpenseDetailBean.getHotelCost().isEmpty()?"0":travelExpenseDetailBean.getHotelCost());
                guestsCost += Double.parseDouble(travelExpenseDetailBean.getGuestsCost().isEmpty()?"0":travelExpenseDetailBean.getGuestsCost());
                otherCost += Double.parseDouble(travelExpenseDetailBean.getOtherCost().isEmpty()?"0":travelExpenseDetailBean.getOtherCost());
                allowance += Double.parseDouble(travelExpenseDetailBean.getAllowance().isEmpty()?"0":travelExpenseDetailBean.getAllowance());
            }
            List<HandInputGroup.Holder> subMain = new ArrayList<>();
            subMain.add(new HandInputGroup.Holder(this.getString(R.string.Air_Ticket), false, false, "/" + ticketCost, HandInputGroup.VALUE_TYPE.EDIT_DOUBLE).setNumber(ticketCost + ""));
            subMain.add(new HandInputGroup.Holder(this.getString(R.string.Transport_Cost), false, false, "0.00", HandInputGroup.VALUE_TYPE.EDIT_DOUBLE).setNumber(transportCost + "").setEditable(false));
            subMain.add(new HandInputGroup.Holder(this.getString(R.string.Transport_Cost_Rate), false, false, "/" + this.getString(R.string.Please_Fill), HandInputGroup.VALUE_TYPE.EDIT_DOUBLE).setNumber("3%"));
            subMain.add(new HandInputGroup.Holder(" ", false, false, "/" + this.getString(R.string.Please_Fill), HandInputGroup.VALUE_TYPE.EDIT_DOUBLE).setNumber("5%"));
            subMain.add(new HandInputGroup.Holder("  ", false, false, "/" + this.getString(R.string.Please_Fill), HandInputGroup.VALUE_TYPE.EDIT_DOUBLE).setNumber("不可抵扣"));
            subMain.add(new HandInputGroup.Holder(this.getString(R.string.Hotel_Cost), false, false, "0.00", HandInputGroup.VALUE_TYPE.EDIT_DOUBLE).setNumber("" + hotelCost).setEditable(false));
            subMain.add(new HandInputGroup.Holder(this.getString(R.string.Hotel_Cost_Rate), false, true,"/" + this.getString(R.string.Please_Fill), HandInputGroup.VALUE_TYPE.EDIT_DOUBLE).setNumber("3%"));
            subMain.add(new HandInputGroup.Holder("   ", false, true, "/" + this.getString(R.string.Please_Fill), HandInputGroup.VALUE_TYPE.EDIT_DOUBLE).setNumber("5%"));
            subMain.add(new HandInputGroup.Holder("    ", false, true, "/" + this.getString(R.string.Please_Fill), HandInputGroup.VALUE_TYPE.EDIT_DOUBLE).setNumber("不可抵扣"));
            subMain.add(new HandInputGroup.Holder(this.getString(R.string.Guests_Cost), false, false, "/" + this.getString(R.string.Please_Fill), HandInputGroup.VALUE_TYPE.EDIT_DOUBLE).setNumber("" + guestsCost));
            subMain.add(new HandInputGroup.Holder(this.getString(R.string.Other_Cost), false, false, "/" + this.getString(R.string.Please_Fill), HandInputGroup.VALUE_TYPE.EDIT_DOUBLE).setNumber("" + otherCost));
            subMain.add(new HandInputGroup.Holder(this.getString(R.string.Allowance), false, false, "/" + this.getString(R.string.Please_Fill), HandInputGroup.VALUE_TYPE.EDIT_DOUBLE).setNumber("" + allowance));
            subMain.add(new HandInputGroup.Holder(this.getString(R.string.Audit_Amount), false, false, "/" + retDataBean.getDetailData().getTotalAmount(), HandInputGroup.VALUE_TYPE.DOUBLE).setEditable(false));
            Group group = groups.get(groups.size() - 1 - retDataBean.getAttchList().size());
            group.setHolders(subMain);
        }
        //出纳
        if (cashier) {
            loadCashier();
            holders.add(new HandInputGroup.Holder(this.getString(R.string.Audit_Total_Amount), true, false,"/" + retDataBean.getDetailData().getAuditTotalAmount(), HandInputGroup.VALUE_TYPE.TEXT));
            holders.add(new HandInputGroup.Holder(this.getString(R.string.Cash_Pay_Amount), false, false, "/0.00", HandInputGroup.VALUE_TYPE.DOUBLE));
            if (retDataBean.getDetailData().isHasBorrow()){
                holders.add(new HandInputGroup.Holder(this.getString(R.string.Dealings_Pay_Amount), false, false, "/0.00", HandInputGroup.VALUE_TYPE.DOUBLE));
            }
            holders.add(new HandInputGroup.Holder(this.getString(R.string.Capital_Platform_Payment_Amount), false, false,"/" + retDataBean.getDetailData().getAuditTotalAmount(), HandInputGroup.VALUE_TYPE.DOUBLE));
            holders.add(new HandInputGroup.Holder(this.getString(R.string.Expenditure_Type), true, false, "/" + this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.SELECT));
            holders.add(new HandInputGroup.Holder(this.getString(R.string.Cashier), true, false,"/" + this.getString(R.string.Please_Select) , HandInputGroup.VALUE_TYPE.SELECT));
        }
        if (glAccount) {
            holders.add(new HandInputGroup.Holder(this.getString(R.string.Audit_Total_Amount), true, false, retDataBean.getDetailData().getAuditTotalAmount(), HandInputGroup.VALUE_TYPE.TEXT));
            holders.add(new HandInputGroup.Holder(this.getString(R.string.Cash_Pay_Amount), false, false, retDataBean.getDetailData().getCashPayAmount().isEmpty()?"0":retDataBean.getDetailData().getCashPayAmount(), HandInputGroup.VALUE_TYPE.TEXT));
            if (retDataBean.getDetailData().isHasBorrow()){
                holders.add(new HandInputGroup.Holder(this.getString(R.string.Dealings_Pay_Amount), false, false, retDataBean.getDetailData().getDealingsPayAmount().isEmpty()?"0":retDataBean.getDetailData().getDealingsPayAmount(), HandInputGroup.VALUE_TYPE.TEXT));
            }
            holders.add(new HandInputGroup.Holder(this.getString(R.string.Capital_Platform_Payment_Amount), false, false, retDataBean.getDetailData().getPlatPayAmount().isEmpty()?"0":retDataBean.getDetailData().getPlatPayAmount(), HandInputGroup.VALUE_TYPE.TEXT));
            holders.add(new HandInputGroup.Holder(this.getString(R.string.Expenditure_Type), true, false, retDataBean.getDetailData().getExpenditureType(), HandInputGroup.VALUE_TYPE.TEXT));
            holders.add(new HandInputGroup.Holder(this.getString(R.string.Cashier), true, false, retDataBean.getDetailData().getCashierName(), HandInputGroup.VALUE_TYPE.TEXT));
            if (retDataBean.getDetailData().isIsRenderAccountant()){
                holders.add(new HandInputGroup.Holder(this.getString(R.string.Voucher_Number), true, false, retDataBean.getDetailData().getVoucherNumber(), HandInputGroup.VALUE_TYPE.TEXT));
            }
            if (Double.parseDouble(retDataBean.getDetailData().getPlatPayAmount().isEmpty()?"0":retDataBean.getDetailData().getPlatPayAmount()) != 0){
                holders.add(new HandInputGroup.Holder(this.getString(R.string.Pay_Status), false, false, retDataBean.getDetailData().getPayStatus(), HandInputGroup.VALUE_TYPE.TEXT));
                if (retDataBean.getDetailData().getPayStatus().equals("1")){
                holders.add(new HandInputGroup.Holder(this.getString(R.string.Paying_Bank_Account), false, false, retDataBean.getDetailData().getFundReturnBankAccount(), HandInputGroup.VALUE_TYPE.TEXT));
                holders.add(new HandInputGroup.Holder(this.getString(R.string.Paying_Bank_Name), false, false, retDataBean.getDetailData().getFundReturnBankName(), HandInputGroup.VALUE_TYPE.TEXT));
                holders.add(new HandInputGroup.Holder(this.getString(R.string.Payment_Description), false, false, retDataBean.getDetailData().getFundReturnNote(), HandInputGroup.VALUE_TYPE.TEXT));
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
    public void onHolderTextChanged(int main, int index, HandInputGroup.Holder holder) {
        String key = holder.getKey();
        if (holder.getType() == HandInputGroup.VALUE_TYPE.EDIT_DOUBLE){
            Group group = getGroupsByTitle("详细信息-" + this.getString(R.string.Total)).get(0);
            if (key.equals(this.getString(R.string.Air_Ticket))||key.equals(this.getString(R.string.Transport_Cost))||key.equals(this.getString(R.string.Hotel_Cost))||key.equals(this.getString(R.string.Cost_Center))||key.equals(this.getString(R.string.Other_Cost))||key.equals(this.getString(R.string.Allowance))){
                double v = Double.parseDouble(group.getHolders().get(0).getRealValue().isEmpty() ? "0" : group.getHolders().get(0).getRealValue());
                double v1 = Double.parseDouble(group.getHolders().get(1).getRealValue().isEmpty() ? "0" : group.getHolders().get(1).getRealValue());
                double v5 = Double.parseDouble(group.getHolders().get(5).getRealValue().isEmpty() ? "0" : group.getHolders().get(5).getRealValue());
                double v9 = Double.parseDouble(group.getHolders().get(9).getRealValue().isEmpty() ? "0" : group.getHolders().get(9).getRealValue());
                double v10 = Double.parseDouble(group.getHolders().get(10).getRealValue().isEmpty() ? "0" : group.getHolders().get(10).getRealValue());
                double v11 = Double.parseDouble(group.getHolders().get(11).getRealValue().isEmpty() ? "0" : group.getHolders().get(11).getRealValue());
                getDisplayValueByKey(this.getString(R.string.Audit_Total_Amount)).setDispayValue("" + (v + v1 + v5 + v9 + v10 + v11));
                group.getHolders().get(12).setDispayValue("" + (v + v1 + v5 + v9 + v10 + v11));
                notifyGroupChanged(0,1);
            }
            if (key.equals(this.getString(R.string.Transport_Cost_Rate))||key.equals(" ")||key.equals("  ")){
                double v2 = Double.parseDouble(group.getHolders().get(2).getRealValue().isEmpty() ? "0" : group.getHolders().get(2).getRealValue());
                double v3 = Double.parseDouble(group.getHolders().get(3).getRealValue().isEmpty() ? "0" : group.getHolders().get(3).getRealValue());
                double v4 = Double.parseDouble(group.getHolders().get(4).getRealValue().isEmpty() ? "0" : group.getHolders().get(4).getRealValue());
                group.getHolders().get(1).setDispayValue("" + (v2 + v3 + v4));
            }if (key.equals(this.getString(R.string.Hotel_Cost_Rate))||key.equals("   ")||key.equals("    ")){
                double v6 = Double.parseDouble(group.getHolders().get(6).getRealValue().isEmpty() ? "0" : group.getHolders().get(6).getRealValue());
                double v7 = Double.parseDouble(group.getHolders().get(7).getRealValue().isEmpty() ? "0" : group.getHolders().get(7).getRealValue());
                double v8 = Double.parseDouble(group.getHolders().get(8).getRealValue().isEmpty() ? "0" : group.getHolders().get(8).getRealValue());
                group.getHolders().get(5).setDispayValue("" + (v6 + v7 + v8));
            }
        }
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
        if (!title.equals(this.getString(R.string.Reject)) && over != null){
            ToastUtil.showToast(getContext(),(this.getString(R.string.Please_Fill) + over));
            setButtonllEnable(true);
            return;
        }else {
            final Map<String, Object> param = CommonValues.getCommonParams(getActivity());
            TravelOfferDetailBean.RetDataBean.DetailDataBean detailData = getEntity().getDetailData();
            detailData.setUpdateBy(GeelyApp.getLoginEntity().getUserId());
            detailData.setUpdateTime(DataUtil.parseDateNormalString(new Date()));
            List<TravelOfferDetailBean.RetDataBean.DetailDataBean.TravelExpenseDetailBean> expenseDetailBean = getEntity().getDetailData().getTravelExpenseDetail();
            String detail = GsonUtil.getJson(expenseDetailBean);
            if (paperReceive) {
                detailData.setReceivedDate(getDisplayValueByKey(this.getString(R.string.Received_Date)).getDispayValue());
                String main = GsonUtil.getJson(detailData);
                param.put("mainData", main);
                param.put("detailData", detail);
                param.put("SN", getArguments().getString("SN"));
                applyApprove(CommonValues.REQ_TRAVEL_EXPENSE_APPROVE, param, title);
            }else if (finance) {
                if (Double.parseDouble(getDisplayValueByKey(this.getString(R.string.Audit_Total_Amount)).getRealValue().isEmpty()?"0":getDisplayValueByKey(this.getString(R.string.Audit_Total_Amount)).getRealValue()) > Double.parseDouble(retDataBean.getDetailData().getTotalAmount().isEmpty()?"0":retDataBean.getDetailData().getTotalAmount())){
                    ToastUtil.showToast(getContext(),"实报金额不能大于申请金额");
                    setButtonllEnable(true);
                    return;
                }
                if (Double.parseDouble(getDisplayValueByKey(this.getString(R.string.Audit_Total_Amount)).getRealValue().isEmpty()?"0":getDisplayValueByKey(this.getString(R.string.Audit_Total_Amount)).getRealValue()) == 0){
                    ToastUtil.showToast(getContext(),"实报金额不能未0");
                    setButtonllEnable(true);
                    return;
                }
                detailData.setIsRenderAccountant(getDisplayValueByKey(this.getString(R.string.Is_Render_Accountant)).getRealValue().equals(this.getString(R.string.Yes)));
                detailData.setAuditTotalAmount(getDisplayValueByKey(this.getString(R.string.Audit_Total_Amount)).getRealValue());
                if(getDisplayValueByKey(this.getString(R.string.Is_Render_Accountant)).getRealValue().equals(this.getString(R.string.Yes))){
                    detailData.setGenerateAccountingVouchersType(DataUtil.getDicIdByDescr(draftGenerateAccountingVouchersTypes,getDisplayValueByKey(this.getString(R.string.Generate_Accounting_Vouchers_Type)).getRealValue()) + "");
                }else {
                    detailData.setGenerateAccountingVouchersType("");
                }
                detailData.setExpenseType(getDisplayValueByKey(this.getString(R.string.ExpenseType)).getRealValue());
                detailData.setIsCompliance(getDisplayValueByKey(this.getString(R.string.Is_Compliance)).getRealValue().equals(this.getString(R.string.Yes)));
                List<HandInputGroup.Holder> holderList = getGroupsByTitle("详细信息-" + this.getString(R.string.Total)).get(0).getHolders();
                List<TravelOfferDetailBean.RetDataBean.DetailDataBean.TravelAuditDetailBean> travelAuditDetail = detailData.getTravelAuditDetail();
                for (int i = 0; i < travelAuditDetail.size(); i++) {
                    travelAuditDetail.get(i).setAuditAmount(holderList.get(i).getRealValue().isEmpty()?"0":holderList.get(i).getRealValue());
                }
                String main = GsonUtil.getJson(detailData);
                param.put("mainData", main);
                param.put("detailData", detail);
                param.put("SN", getArguments().getString("SN"));
                applyApprove(CommonValues.REQ_TRAVEL_EXPENSE_APPROVE, param, title);
            }else if (cashier) {
                double CashPayAmount = Double.parseDouble(getDisplayValueByKey(this.getString(R.string.Cash_Pay_Amount)).getRealValue().isEmpty()?"0":getDisplayValueByKey(this.getString(R.string.Cash_Pay_Amount)).getRealValue());
                double PlatPayAmount = Double.parseDouble(getDisplayValueByKey(this.getString(R.string.Capital_Platform_Payment_Amount)).getRealValue().isEmpty()?"0":getDisplayValueByKey(this.getString(R.string.Capital_Platform_Payment_Amount)).getRealValue());
                if (retDataBean.getDetailData().isHasBorrow()){
                    double DealingsPayAmount = Double.parseDouble(getDisplayValueByKey(this.getString(R.string.Dealings_Pay_Amount)).getRealValue().isEmpty()?"0":getDisplayValueByKey(this.getString(R.string.Dealings_Pay_Amount)).getRealValue());
                    if((CashPayAmount + PlatPayAmount + DealingsPayAmount != Double.parseDouble(retDataBean.getDetailData().getAuditTotalAmount().isEmpty()?"0":retDataBean.getDetailData().getAuditTotalAmount())) && !title.equals(this.getString(R.string.Reject))){
                        ToastUtil.showToast(getContext(),this.getString(R.string.Cash_Pay_Amount) + " + " + this.getString(R.string.Capital_Platform_Payment_Amount) + " + " + this.getString(R.string.Dealings_Pay_Amount) + "  必须等于实报金额");
                        setButtonllEnable(true);
                    }else {
                        detailData.setCashPayAmount(getDisplayValueByKey(this.getString(R.string.Cash_Pay_Amount)).getRealValue());
                        detailData.setDealingsPayAmount(getDisplayValueByKey(this.getString(R.string.Dealings_Pay_Amount)).getRealValue());
                        detailData.setPlatPayAmount(getDisplayValueByKey(this.getString(R.string.Capital_Platform_Payment_Amount)).getRealValue());
                        detailData.setExpenditureType(getDisplayValueByKey(this.getString(R.string.Expenditure_Type)).getRealValue());
                        detailData.setCashierName(getDisplayValueByKey(this.getString(R.string.Cashier)).getRealValue());
                        detailData.setCashier(DataUtil.getDicodeByDescr(dicTaller,getDisplayValueByKey(this.getString(R.string.Cashier)).getRealValue()));

                        String main = GsonUtil.getJson(detailData);
                        param.put("mainData", main);
                        param.put("detailData", detail);
                        param.put("SN", getArguments().getString("SN"));
                        applyApprove(CommonValues.REQ_TRAVEL_EXPENSE_APPROVE, param, title);
                    }
                }else {
                    if((CashPayAmount + PlatPayAmount != Double.parseDouble(retDataBean.getDetailData().getAuditTotalAmount().isEmpty()?"0":retDataBean.getDetailData().getAuditTotalAmount())) && !title.equals(this.getString(R.string.Reject))){
                        ToastUtil.showToast(getContext(),this.getString(R.string.Cash_Pay_Amount) + " + " + this.getString(R.string.Capital_Platform_Payment_Amount) + "  必须等于实报金额");
                        setButtonllEnable(true);
                    }else {
                        detailData.setCashPayAmount(getDisplayValueByKey(this.getString(R.string.Cash_Pay_Amount)).getRealValue());
                        detailData.setDealingsPayAmount("0");
                        detailData.setPlatPayAmount(getDisplayValueByKey(this.getString(R.string.Capital_Platform_Payment_Amount)).getRealValue());
                        detailData.setExpenditureType(getDisplayValueByKey(this.getString(R.string.Expenditure_Type)).getRealValue());
                        detailData.setCashierName(getDisplayValueByKey(this.getString(R.string.Cashier)).getRealValue());
                        detailData.setCashier(DataUtil.getDicodeByDescr(dicTaller, getDisplayValueByKey(this.getString(R.string.Cashier)).getRealValue()));
                        String main = GsonUtil.getJson(detailData);
                        param.put("mainData", main);
                        param.put("detailData", detail);
                        param.put("SN", getArguments().getString("SN"));
                        applyApprove(CommonValues.REQ_TRAVEL_EXPENSE_APPROVE, param, title);
                    }
                }
            }else {
                String main = GsonUtil.getJson(detailData);
                param.put("mainData", main);
                param.put("detailData", detail);
                param.put("SN", getArguments().getString("SN"));
                applyApprove(CommonValues.REQ_TRAVEL_EXPENSE_APPROVE, param, title);
            }
        }

    }

    private void loadFinance() {
        HttpManager manager = HttpManager.getInstance();
        Map<String, Object> params = CommonValues.getCommonParams(getActivity());
        // 凭证生成类型 4212
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

        //费用归类
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
    public void onClickItemContentSetter(final HandInputGroup.Holder holder) {
        String key = holder.getKey();
        if (holder.getType() == HandInputGroup.VALUE_TYPE.SELECT) {
            if (holder.getKey().equals(this.getString(R.string.Select_Attachments))) {
                AttachmentListEntity entity = (AttachmentListEntity) holder.getValue();
                lookAttachmentInDetailFragment(entity);
            }
        }
        if (finance) {
            if (key.equals(this.getString(R.string.Is_Compliance))) {
                checkedButton(holder);
            } else if (key.equals(this.getString(R.string.Is_Render_Accountant))) {
                checkedButton(holder, new OnSelectedResultCallback() {
                    @Override
                    public void onSelected(Group ownGroup, HandInputGroup.Holder holder, int mainIndex, int itemIndex) {
                        HandInputGroup.Holder displayValueByKey = getDisplayValueByKey(TravelApproveFragment.this.getString(R.string.Generate_Accounting_Vouchers_Type));
                        if (holder.getRealValue().equals(TravelApproveFragment.this.getString(R.string.Yes))){
                            displayValueByKey.setDispayValue("/" + TravelApproveFragment.this.getString(R.string.Please_Select)).setType(HandInputGroup.VALUE_TYPE.SELECT);
                            displayValueByKey.setHasIndicator(true);
                        }else {
                            displayValueByKey.setHasIndicator(false);
                            displayValueByKey.setDispayValue("").setType(HandInputGroup.VALUE_TYPE.TEXTFILED);
                            displayValueByKey.setEditable(false);
                        }
                        notifyDataSetChanged();
                    }
                });
            } else if (key.equals(this.getString(R.string.Generate_Accounting_Vouchers_Type))) {
                showSelector(holder, DataUtil.getDescr(draftGenerateAccountingVouchersTypes));
            } else if (key.equals(this.getString(R.string.ExpenseType))) {
                showSelector(holder, DataUtil.getDescr(draftExpenseType));
            }
        } else if (paperReceive) {
            if(holder.getType() == HandInputGroup.VALUE_TYPE.DATE){
                showDateTimePicker(holder,false);
            }
        } else if (cashier) {
            if (key.equals(this.getString(R.string.Expenditure_Type))) {
                showSelector(holder, DataUtil.getDescr(dicExpenditureType));
            } else if (key.equals(this.getString(R.string.Cashier))) {
                showSelector(holder, DataUtil.getDescr(dicTaller));
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CommonValues.CODE_OA_REQUEST) {
            if (data != null) {
                Log.e("BPM-TRAVEL","keyOfHolder----" + data.getStringExtra("keyOfHolder") + ",empId----" + data.getStringExtra("empid"));
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
                                        bottomString[i] = TravelApproveFragment.this.getString(R.string.Reject);
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
