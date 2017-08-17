package com.geely.app.geelyapprove.activities.cost.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.geely.app.geelyapprove.activities.cost.bean.CostDetailBean;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.geely.app.geelyapprove.common.utils.DataUtil.getDescr;
import static com.geely.app.geelyapprove.common.utils.DataUtil.getDicodeByDescr;
import static java.lang.Double.parseDouble;

/**费用审批
 * Created by guiluXu on 2016/9/28.
 */
public class CostApproveFragment extends CostDetailFragment {
    private CostDetailBean.RetDataBean retDataBean;
    private boolean paperReceive;
    private boolean finance;
    private boolean cashier;
    private List<DataListEntity> dicTaller,TaxRateList, dicExpenditureType, draftExpense,draftExpenseType, draftGenerateAccountingVouchersTypes;
    private boolean glAccount;
    private DecimalFormat df;


    public CostApproveFragment() {
        super();
    }

    public static CostApproveFragment newInstance(Bundle bundle) {
        CostApproveFragment fragment = new CostApproveFragment();
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
        retDataBean = getEntity();
        if (retDataBean == null) return new ArrayList<>();
        List<Group> groups = super.getGroupList();
        paperReceive = retDataBean.getIsKeyAuditNodeForApprove().isPaperReceive();//单据接收
        finance = retDataBean.getIsKeyAuditNodeForApprove().isFinance(); //财务审核
        glAccount = retDataBean.getIsKeyAuditNodeForApprove().isGLAccount();
        cashier = retDataBean.getIsKeyAuditNodeForApprove().isCashier(); //出纳
        List<HandInputGroup.Holder> holders = groups.get(0).getHolders();
        if(paperReceive){
            SimpleDateFormat formatter =  new SimpleDateFormat("yyyy-MM-dd");
            Date  curDate  = new  Date(System.currentTimeMillis());
            String  str  =  formatter.format(curDate);
            holders.add(new HandInputGroup.Holder(this.getString(R.string.Received_Date), true, false, str, HandInputGroup.VALUE_TYPE.DATE));
        }
        if (finance) {
            loadFinance();
            List<HandInputGroup.Holder> holders1 = groups.get(0).getHolders();
            holders1.add(new HandInputGroup.Holder(this.getString(R.string.Audit_Total_Amount), true, false, "/" + retDataBean.getDetailData().getTotalAmount() , HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
            holders1.add(new HandInputGroup.Holder(this.getString(R.string.Is_Render_Accountant), true, false, this.getString(R.string.No), HandInputGroup.VALUE_TYPE.BUTTONS));
            holders1.add(new HandInputGroup.Holder(this.getString(R.string.Generate_Accounting_Vouchers_Type), false, false, "", HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
            holders1.add(new HandInputGroup.Holder(this.getString(R.string.ExpenseType), true, false, "/" + this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.SELECT));
            for (int i = 3; i < groups.size() - 1 - retDataBean.getAttchList().size(); i++) {
                List<HandInputGroup.Holder> holders2 = groups.get(i).getHolders();
                holders2.add(new HandInputGroup.Holder(this.getString(R.string.Amount_Without_Tax), false, false, "0", HandInputGroup.VALUE_TYPE.TEXT).setColor(Color.rgb(214,16,24)));
                holders2.add(new HandInputGroup.Holder(this.getString(R.string.Amount_of_Tax), false, false, retDataBean.getDetailData().getExpenseDetail().get(i - 3).getAmountWithTax(), HandInputGroup.VALUE_TYPE.TEXT).setColor(Color.rgb(214,16,24)));
                holders2.add(new HandInputGroup.Holder(this.getString(R.string.Audit_Amount), true, false, "/" + (retDataBean.getDetailData().getExpenseDetail().get(i - 3).getAuditAmount().isEmpty()? "0" : retDataBean.getDetailData().getExpenseDetail().get(i - 3).getAuditAmount()), HandInputGroup.VALUE_TYPE.DOUBLE).setColor(Color.rgb(54,106,160)));
                holders2.add(new HandInputGroup.Holder(this.getString(R.string.Deductible_Or_Not), false, false, this.getString(R.string.No), HandInputGroup.VALUE_TYPE.BUTTONS));
                holders2.add(new HandInputGroup.Holder(this.getString(R.string.Tax_Rate), false, false, "0%", HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
            }
            groups.get(groups.size() -2-retDataBean.getAttchList().size()).setDrawableRes(R.mipmap.add_detail3x);
        }

        if (cashier) {
            loadCashier();
            holders.add(new HandInputGroup.Holder(this.getString(R.string.Cash_Pay_Amount), false, false, "", HandInputGroup.VALUE_TYPE.DOUBLE));
            if (retDataBean.getDetailData().isIsOffsetLoan()){
                holders.add(new HandInputGroup.Holder(this.getString(R.string.Dealings_Pay_Amount), false, false, "", HandInputGroup.VALUE_TYPE.DOUBLE));
            }
            holders.add(new HandInputGroup.Holder(this.getString(R.string.Capital_Platform_Payment_Amount), false, false,"/" + retDataBean.getDetailData().getAuditTotalAmount(), HandInputGroup.VALUE_TYPE.DOUBLE));
            holders.add(new HandInputGroup.Holder(this.getString(R.string.Expenditure_Type), true, false, "/" + this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.SELECT));
            holders.add(new HandInputGroup.Holder(this.getString(R.string.Cashier), true, false, "/" + this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.SELECT));
        }

        if (glAccount) {
            holders.add(new HandInputGroup.Holder(this.getString(R.string.Cash_Pay_Amount), true, false, retDataBean.getDetailData().getCashPayAmount(), HandInputGroup.VALUE_TYPE.TEXT));
            if (retDataBean.getDetailData().isIsOffsetLoan()){
                holders.add(new HandInputGroup.Holder(this.getString(R.string.Dealings_Pay_Amount), true, false, retDataBean.getDetailData().getDealingsPayAmount(), HandInputGroup.VALUE_TYPE.TEXT));
            }
            holders.add(new HandInputGroup.Holder(this.getString(R.string.Capital_Platform_Payment_Amount), true, false, retDataBean.getDetailData().getPlatPayAmount(), HandInputGroup.VALUE_TYPE.TEXT));
            holders.add(new HandInputGroup.Holder(this.getString(R.string.Expenditure_Type), true, false, retDataBean.getDetailData().getExpenditureType(), HandInputGroup.VALUE_TYPE.TEXT));
            holders.add(new HandInputGroup.Holder(this.getString(R.string.Cashier), true, false, retDataBean.getDetailData().getCashierName(), HandInputGroup.VALUE_TYPE.TEXT));
            if (retDataBean.getDetailData().isIsGenerateAccountingVouchers()){
                holders.add(new HandInputGroup.Holder(this.getString(R.string.Voucher_Number), true, false, retDataBean.getDetailData().getVoucherNumber(), HandInputGroup.VALUE_TYPE.TEXT));
            }
            if (parseDouble(retDataBean.getDetailData().getPlatPayAmount().isEmpty()?"0":retDataBean.getDetailData().getPlatPayAmount()) != 0){//Double.parseDouble(retDataBean.getDetailData().getPlatPayAmount())!=0
                holders.add(new HandInputGroup.Holder(this.getString(R.string.Pay_Status), true, false, retDataBean.getDetailData().getPayStatusName(), HandInputGroup.VALUE_TYPE.TEXT));
                if (retDataBean.getDetailData().getPayStatus().equals("1")){
                    holders.add(new HandInputGroup.Holder(this.getString(R.string.Paying_Bank_Account), true, false, retDataBean.getDetailData().getFundReturnBankAccount(), HandInputGroup.VALUE_TYPE.TEXT));
                    holders.add(new HandInputGroup.Holder(this.getString(R.string.Paying_Bank_Name), true, false, retDataBean.getDetailData().getFundReturnBankName(), HandInputGroup.VALUE_TYPE.TEXT));
                    holders.add(new HandInputGroup.Holder(this.getString(R.string.Payment_Description), true, false, retDataBean.getDetailData().getFundReturnNote(), HandInputGroup.VALUE_TYPE.TEXT));
                }
            }
            //根据资金平台付款状态改按钮
            if (retDataBean.getDetailData().getPlatPayAmount().isEmpty() || parseDouble(retDataBean.getDetailData().getPlatPayAmount()) == 0){
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
    public void onHolderTextChanged(int main, final int index, HandInputGroup.Holder holder) {
        if (holder.getKey().equals(this.getString(R.string.Audit_Amount)) && holder.getType() == HandInputGroup.VALUE_TYPE.DOUBLE){
            if (!getGroup().get(main).getHolders().get(index + 2).getRealValue().isEmpty() && !getGroup().get(main).getHolders().get(index + 2).getRealValue().equals("0%")){
                final Group ownGroup = getGroup().get(main);
                final double v = parseDouble(holder.getRealValue().isEmpty()?"0":holder.getRealValue());
                final double v1 = parseDouble(ownGroup.getHolders().get(index + 2).getRealValue().replace("%", ""));
                final DecimalFormat df = new DecimalFormat("#.00");
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ownGroup.getHolders().get(index - 2).setDispayValue(df.format(v - v1*v/(100+v1)));
                        ownGroup.getHolders().get(index - 1).setDispayValue(df.format(v1*v/(100+v1)));
                    }
                });

            }
            List<Group> groupsByTitle = getGroupsByTitle("详细信息-" + this.getString(R.string.Details_Information));
            double value = 0;
            for (Group group : groupsByTitle) {
                double v = Double.parseDouble(group.getHolders().get(4).getRealValue().isEmpty()?"0":group.getHolders().get(4).getRealValue());
                value += v;
            }
            getGroup().get(0).getHolders().get(5).setDispayValue(df.format(value));
            getGroupsByTitle("详细信息-" + this.getString(R.string.Total)).get(0).setGroupTopRightTitle(df.format(value));
            notifyGroupChanged(getGroup().size()-1-retDataBean.getAttchList().size(),getGroup().size()-1-retDataBean.getAttchList().size());
            notifyGroupChanged(0,1);
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
        if (title.equals("同意") && over != null){
            ToastUtil.showToast(getContext(),(this.getString(R.string.Please_Fill) + over));
            setButtonllEnable(true);
            return;
        }else {
            final Map<String, Object> param = CommonValues.getCommonParams(getActivity());
            CostDetailBean.RetDataBean.DetailDataBean mainData = getEntity().getDetailData();
            mainData.setUpdateBy(GeelyApp.getLoginEntity().getUserId());
            mainData.setUpdateTime(DataUtil.parseDateNormalString(new Date()));
            if (paperReceive) {
                List<CostDetailBean.RetDataBean.DetailDataBean.ExpenseDetailBean> detailData = getEntity().getDetailData().getExpenseDetail();
                String detail = GsonUtil.getJson(detailData);
                mainData.setBillPaperReceiveDate(getDisplayValueByKey(this.getString(R.string.Received_Date)).getDispayValue());
                String main = GsonUtil.getJson(mainData);
                param.put("mainData", main);
                param.put("detailData", detail);
                param.put("SN", getArguments().getString("SN"));
                applyApprove(CommonValues.REQ_EXPENSE_DETAIL, param, title);
            } else if (finance) {
                mainData.setIsGenerateAccountingVouchers(getDisplayValueByKey(this.getString(R.string.Is_Render_Accountant)).getRealValue().equals(this.getString(R.string.Yes)));
                if (getDisplayValueByKey(this.getString(R.string.Is_Render_Accountant)).getRealValue().equals(this.getString(R.string.Yes))) {
                    mainData.setGenerateAccountingVouchersType(DataUtil.getDicIdByDescr(draftGenerateAccountingVouchersTypes, getDisplayValueByKey(this.getString(R.string.Generate_Accounting_Vouchers_Type)).getRealValue()) + "");
                } else {
                    mainData.setGenerateAccountingVouchersType("");
                }
                mainData.setExpenseType(getDisplayValueByKey(this.getString(R.string.ExpenseType)).getRealValue());
                if (Double.parseDouble(getDisplayValueByKey(this.getString(R.string.Audit_Total_Amount)).getRealValue().isEmpty()?"0":getDisplayValueByKey(this.getString(R.string.Audit_Total_Amount)).getRealValue()) > parseDouble(retDataBean.getDetailData().getTotalAmount().isEmpty()?"0":retDataBean.getDetailData().getTotalAmount())){
                    ToastUtil.showToast(getContext(),"实报金额不能大于申请金额");
                    setButtonllEnable(true);
                    return;
                }
                mainData.setAuditTotalAmount(getDisplayValueByKey(this.getString(R.string.Audit_Total_Amount)).getRealValue());
                List<Object> details = new ArrayList<Object>();
                for (int i = 3; i < groups.size() - 1 - retDataBean.getAttchList().size(); i++) {
                    Map<String, Object> ddata = new HashMap<String, Object>();
                    ddata.put("WorkflowIdentifier", mainData.getIdentifier());
                    ddata.put("ExpenseType", groups.get(i).getHolders().get(0).getRealValue());
                    ddata.put("ExpenseCost", groups.get(i).getHolders().get(1).getRealValue());
                    ddata.put("AmountWithoutTax", groups.get(i).getHolders().get(2).getRealValue());
                    ddata.put("AmountWithTax", groups.get(i).getHolders().get(3).getRealValue());
                    ddata.put("AuditAmount", groups.get(i).getHolders().get(4).getRealValue());
                    ddata.put("IsOffset", groups.get(i).getHolders().get(5).getRealValue().equals(this.getString(R.string.Yes)));
                    ddata.put("TaxRateId", DataUtil.getDicIdByDescr(TaxRateList, groups.get(i).getHolders().get(6).getRealValue()));
                    details.add(ddata);
                }
                String detail = GsonUtil.getJson(details);
                String main = GsonUtil.toJson(mainData, CostDetailBean.RetDataBean.DetailDataBean.class);
                param.put("mainData", main);
                param.put("detailData", detail);
                param.put("SN", getArguments().getString("SN"));
                Log.e("param", param.toString());
                applyApprove(CommonValues.REQ_EXPENSE_DETAIL, param, title);
            } else if (cashier) {
                List<CostDetailBean.RetDataBean.DetailDataBean.ExpenseDetailBean> detailData = getEntity().getDetailData().getExpenseDetail();
                String detail = GsonUtil.getJson(detailData);
                double CashPayAmount = parseDouble(getDisplayValueByKey(this.getString(R.string.Cash_Pay_Amount)).getRealValue().isEmpty() ? "0" : getDisplayValueByKey(this.getString(R.string.Cash_Pay_Amount)).getRealValue());
                double PlatPayAmount = parseDouble(getDisplayValueByKey(this.getString(R.string.Capital_Platform_Payment_Amount)).getRealValue().isEmpty() ? "0" : getDisplayValueByKey(this.getString(R.string.Capital_Platform_Payment_Amount)).getRealValue());
                if (retDataBean.getDetailData().isIsOffsetLoan()) {
                    double DealingsPayAmount = parseDouble(getDisplayValueByKey(this.getString(R.string.Dealings_Pay_Amount)).getRealValue().isEmpty() ? "0" : getDisplayValueByKey(this.getString(R.string.Dealings_Pay_Amount)).getRealValue());
                    if ((CashPayAmount + PlatPayAmount + DealingsPayAmount != parseDouble(retDataBean.getDetailData().getAuditTotalAmount())) && !title.equals(this.getString(R.string.Reject))) {
                        ToastUtil.showToast(getContext(), this.getString(R.string.Cash_Pay_Amount) + " + " + this.getString(R.string.Capital_Platform_Payment_Amount) + " + " + this.getString(R.string.Dealings_Pay_Amount) + "  必须等于实报金额");
                        setButtonllEnable(true);
                    } else {
                        mainData.setCashPayAmount(getDisplayValueByKey(this.getString(R.string.Cash_Pay_Amount)).getRealValue().isEmpty()?"0":getDisplayValueByKey(this.getString(R.string.Cash_Pay_Amount)).getRealValue());
                        mainData.setDealingsPayAmount(getDisplayValueByKey(this.getString(R.string.Dealings_Pay_Amount)).getRealValue().isEmpty()?"0":getDisplayValueByKey(this.getString(R.string.Dealings_Pay_Amount)).getRealValue());
                        mainData.setPlatPayAmount(getDisplayValueByKey(this.getString(R.string.Capital_Platform_Payment_Amount)).getRealValue().isEmpty()?"0":getDisplayValueByKey(this.getString(R.string.Capital_Platform_Payment_Amount)).getRealValue());
                        mainData.setExpenditureType(getDisplayValueByKey(this.getString(R.string.Expenditure_Type)).getRealValue());
                        mainData.setCashierName(getDisplayValueByKey(this.getString(R.string.Cashier)).getRealValue());
                        mainData.setCashier(getDicodeByDescr(dicTaller, getDisplayValueByKey(this.getString(R.string.Cashier)).getRealValue()));
                        String main = GsonUtil.toJson(mainData, CostDetailBean.RetDataBean.DetailDataBean.class);
                        param.put("mainData", main);
                        param.put("detailData", detail);
                        param.put("SN", getArguments().getString("SN"));
                        applyApprove(CommonValues.REQ_EXPENSE_DETAIL, param, title);
                    }
                } else {
                    if ((CashPayAmount + PlatPayAmount != parseDouble(retDataBean.getDetailData().getAuditTotalAmount())) && !title.equals(this.getString(R.string.Reject))) {
                        ToastUtil.showToast(getContext(), this.getString(R.string.Cash_Pay_Amount) + " + " + this.getString(R.string.Capital_Platform_Payment_Amount) + "  必须等于实报金额");
                        setButtonllEnable(true);
                    } else {
                        mainData.setCashPayAmount(getDisplayValueByKey(this.getString(R.string.Cash_Pay_Amount)).getRealValue());
                        mainData.setDealingsPayAmount("0");
                        mainData.setPlatPayAmount(getDisplayValueByKey(this.getString(R.string.Capital_Platform_Payment_Amount)).getRealValue());
                        mainData.setExpenditureType(getDisplayValueByKey(this.getString(R.string.Expenditure_Type)).getRealValue());
                        mainData.setCashierName(getDisplayValueByKey(this.getString(R.string.Cashier)).getRealValue());
                        mainData.setCashier(getDicodeByDescr(dicTaller, getDisplayValueByKey(this.getString(R.string.Cashier)).getRealValue()));
                        String main = GsonUtil.toJson(mainData, CostDetailBean.RetDataBean.DetailDataBean.class);
                        param.put("mainData", main);
                        param.put("detailData", detail);
                        param.put("SN", getArguments().getString("SN"));
                        applyApprove(CommonValues.REQ_EXPENSE_DETAIL, param, title);
                    }
                }
            } else {
                List<CostDetailBean.RetDataBean.DetailDataBean.ExpenseDetailBean> detailData = getEntity().getDetailData().getExpenseDetail();
                String detail = GsonUtil.getJson(detailData);
                String main = GsonUtil.toJson(mainData, CostDetailBean.RetDataBean.DetailDataBean.class);
                param.put("mainData", main);
                param.put("detailData", detail);
                param.put("SN", getArguments().getString("SN"));
                applyApprove(CommonValues.REQ_EXPENSE_DETAIL, param, title);
            }
        }
    }

    @Override
    public void onOneItemBottomDrawableResClick(int index) {
        List<HandInputGroup.Holder> holders = new ArrayList<>();
        holders.add(new HandInputGroup.Holder(this.getString(R.string.Expense_Type), true, false, "/" + this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.SELECT));
        holders.add(new HandInputGroup.Holder(this.getString(R.string.Expense_Cost), false, false, "", HandInputGroup.VALUE_TYPE.DOUBLE).setEditable(false));
        holders.add(new HandInputGroup.Holder(this.getString(R.string.Amount_Without_Tax), false, false, "", HandInputGroup.VALUE_TYPE.TEXT).setColor(Color.rgb(214,16,24)));
        holders.add(new HandInputGroup.Holder(this.getString(R.string.Amount_of_Tax), false, false, "", HandInputGroup.VALUE_TYPE.TEXT).setColor(Color.rgb(214,16,24)));
        holders.add(new HandInputGroup.Holder(this.getString(R.string.Audit_Amount), true, false, "", HandInputGroup.VALUE_TYPE.DOUBLE).setColor(Color.rgb(54,106,160)));
        holders.add(new HandInputGroup.Holder(this.getString(R.string.Deductible_Or_Not), false, false, this.getString(R.string.No), HandInputGroup.VALUE_TYPE.BUTTONS));
        holders.add(new HandInputGroup.Holder(this.getString(R.string.Tax_Rate), false, false, "0%", HandInputGroup.VALUE_TYPE.SELECT));
        addGroup(index+1,new Group("详细信息-" + this.getString(R.string.Details_Information), R.mipmap.add_detail3x, true, null, holders).sethasDelete(true));
        getGroup().get(index).setDrawableRes(null);
        notifyDataSetChanged();
    }

    private void loadFinance() {
        HttpManager manager = HttpManager.getInstance();
        Map<String, Object> params = CommonValues.getCommonParams(getActivity());

        // 凭证生成类型
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

        //费用类别
        params.put("code",2144);
        manager.requestResultForm(CommonValues.GET_DICTIONARY_LIST, params, DictionaryEntity.class, new HttpManager.ResultCallback<DictionaryEntity>() {
            @Override
            public void onSuccess(String content, DictionaryEntity expenseTypeEntity) {
                draftExpense = expenseTypeEntity.getRetData().get(0).getDataList();
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
        //税率
        params.put("code",2158);
        manager.requestResultForm(CommonValues.GET_DICTIONARY_LIST, params, DictionaryEntity.class, new HttpManager.ResultCallback<DictionaryEntity>() {
            @Override
            public void onSuccess(String content, DictionaryEntity expenseTypeEntity) {
                TaxRateList = expenseTypeEntity.getRetData().get(0).getDataList();
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
        manager.requestResultForm( CommonValues.DICTIONARY_TALLER, params, ExpenseDraftListEntity.class, new HttpManager.ResultCallback<ExpenseDraftListEntity>() {
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
        if (key.equals(this.getString(R.string.Is_Render_Accountant))) {
            checkedButton(holder, new OnSelectedResultCallback() {
                @Override
                public void onSelected(Group ownGroup, HandInputGroup.Holder holder, int mainIndex, int itemIndex) {
                    HandInputGroup.Holder holder1 = getDisplayValueByKey(CostApproveFragment.this.getString(R.string.Generate_Accounting_Vouchers_Type));
                    if (holder.getRealValue().equals(CostApproveFragment.this.getString(R.string.Yes))){
                        holder1.setType(HandInputGroup.VALUE_TYPE.SELECT);
                        holder1.setHasIndicator(true);
                    }else {
                        holder1.setDispayValue("").setType(HandInputGroup.VALUE_TYPE.TEXTFILED);
                        holder1.setHasIndicator(false);
                        holder1.setEditable(false);
                    }
                    notifyDataSetChanged();
                }
            });
        } else if (key.equals(this.getString(R.string.Generate_Accounting_Vouchers_Type))) {
            showSelector(holder, DataUtil.getDescr(draftGenerateAccountingVouchersTypes));
        } else if (key.equals(this.getString(R.string.ExpenseType))) {
            showSelector(holder, DataUtil.getDescr(draftExpenseType));
        }else if(key.equals(this.getString(R.string.Deductible_Or_Not))){
            checkedButton(holder , new OnSelectedResultCallback() {
                @Override
                public void onSelected(Group ownGroup, HandInputGroup.Holder holder, int mainIndex, int itemIndex) {
                    if (holder.getDispayValue().equals(CostApproveFragment.this.getString(R.string.Yes))) {
                        ownGroup.getHolders().get(itemIndex + 1).setType(HandInputGroup.VALUE_TYPE.SELECT);

                    }else if (holder.getDispayValue().equals(CostApproveFragment.this.getString(R.string.No))) {
                        HandInputGroup.Holder holder1 = ownGroup.getHolders().get(itemIndex + 1);
                        holder1.setDispayValue("0%").setType(HandInputGroup.VALUE_TYPE.TEXTFILED);
                        holder1.setEditable(false);
                        ownGroup.getHolders().get(itemIndex - 3).setDispayValue(ownGroup.getHolders().get(itemIndex - 4).getRealValue());
                        ownGroup.getHolders().get(itemIndex - 2).setDispayValue("0.00");
                    }
                    notifyDataSetChanged();
                }
            });
        }else if(key.equals(this.getString(R.string.Tax_Rate))){
            showSelector(holder, DataUtil.getDescr(TaxRateList), new OnSelectedResultCallback() {
                @Override
                public void onSelected(Group ownGroup, HandInputGroup.Holder holder, int mainIndex, int itemIndex) {
                    String realValue = ownGroup.getHolders().get(itemIndex - 2).getRealValue();
                    if (realValue.isEmpty()){
                        ToastUtil.showToast(getContext(),CostApproveFragment.this.getString(R.string.Please_Fill) + CostApproveFragment.this.getString(R.string.Audit_Amount));
                        holder.setDispayValue("0%");
                    }else {
                        double v = parseDouble(ownGroup.getHolders().get(itemIndex - 2).getRealValue());
                        double v1 = parseDouble(holder.getRealValue().replace("%", ""));
                        DecimalFormat df = new DecimalFormat("#.00");
                        ownGroup.getHolders().get(itemIndex - 4).setDispayValue(df.format(v - v1*v/(100+v1)));
                        ownGroup.getHolders().get(itemIndex - 3).setDispayValue(df.format(v1*v/(100+v1)));
                    }
                }
            });

        }else if(holder.getType() == HandInputGroup.VALUE_TYPE.DATE){
            showDateTimePicker(holder,false);
        }else if (key.equals(this.getString(R.string.Expenditure_Type))) {
            showSelector(holder, DataUtil.getDescr(dicExpenditureType));
        } else if (key.equals(this.getString(R.string.Cashier))) {
            showSelector(holder, DataUtil.getDescr(dicTaller));
        }else if(key.equals(this.getString(R.string.Expense_Type))){
            showSelector(holder, getDescr(draftExpense));
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CommonValues.CODE_OA_REQUEST) {
            if (data != null) {
                Log.e("BPM-COST","keyOfHolder----" + data.getStringExtra("keyOfHolder") + ",empId----" + data.getStringExtra("empid"));
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
                                        bottomString[i] = CostApproveFragment.this.getString(R.string.Reject);
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
