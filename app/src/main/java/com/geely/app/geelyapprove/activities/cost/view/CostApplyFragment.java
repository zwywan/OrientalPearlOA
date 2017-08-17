package com.geely.app.geelyapprove.activities.cost.view;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.geely.app.geelyapprove.activities.HistoryActivity;
import com.geely.app.geelyapprove.activities.ItemActivity;
import com.geely.app.geelyapprove.activities.PageConfig;
import com.geely.app.geelyapprove.activities.cost.bean.CostDetailBean;
import com.geely.app.geelyapprove.activities.enterexpense.Entity.EnterExpenseOrdersEntity;
import com.geely.app.geelyapprove.activities.login.entity.LoginEntity;
import com.geely.app.geelyapprove.common.application.GeelyApp;
import com.geely.app.geelyapprove.common.entity.AttachDeleteEntity;
import com.geely.app.geelyapprove.common.entity.AttachmentListEntity;
import com.geely.app.geelyapprove.common.entity.AttachmentSingleEntity;
import com.geely.app.geelyapprove.common.entity.CommonDataEntity;
import com.geely.app.geelyapprove.common.entity.CompanyCBPEntity;
import com.geely.app.geelyapprove.common.entity.CompanyEntity;
import com.geely.app.geelyapprove.common.entity.DataListEntity;
import com.geely.app.geelyapprove.common.entity.DictionaryEntity;
import com.geely.app.geelyapprove.common.entity.PersonalAccountEntity;
import com.geely.app.geelyapprove.common.fragment.CommonFragment;
import com.geely.app.geelyapprove.common.utils.CommonValues;
import com.geely.app.geelyapprove.common.utils.DataUtil;
import com.geely.app.geelyapprove.common.utils.FileUtils;
import com.geely.app.geelyapprove.common.utils.GsonUtil;
import com.geely.app.geelyapprove.common.utils.ToastUtil;
import com.geely.app.geelyapprove.common.view.ActionSheetUnionOrdersActivity;
import com.geely.app.geelyapprove.common.view.FileChooserLayout;
import com.geely.app.geelyapprove.common.view.HandInputGroup;
import com.geely.app.geelyapprove.common.view.HandToolbar;
import com.geely.app.geelyapprove.common.view.SubListLayout;
import com.geely.app.geelyapprove.datamanage.CacheManger;
import com.geely.app.geelyapprove.datamanage.HttpManager;
import com.geely.app.orientalpearl.R;
import com.google.gson.Gson;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.geely.app.geelyapprove.common.utils.DataUtil.getDescr;
import static com.geely.app.geelyapprove.common.utils.DataUtil.getDicodeByDescr;


/**zhy 费用申请
 * Created by guiluXu on 2016/9/28.
 */
public class CostApplyFragment extends CommonFragment implements FileChooserLayout.DataChangeListener{
    private String mUserId;
    private String mNameCN;
    private String mPositionNameCN;
    private String mEid;
    private String uuid;
    private LoginEntity loginEntity = GeelyApp.getLoginEntity();
    private String Grade;
    private CostDetailBean bean;
    private HandInputGroup.Holder mholder;
    private List<DataListEntity> draftExpenseType,companyEntities,expenseAttachmentTypes;
    private List<DataListEntity> costcenterList;
    private String barcode;
    private String companycode;//公司代码
    private String CostCentercode;//成本中心代码
    private String payeecode;//收款银行代码
    private String mId;
    private String provinceid;//省id
    private String cityid;//市id
    private Map<String, HashSet<Uri>> paths;
    private ArrayMap<String,Object> enterExpUnion = new ArrayMap<>();
    private List<AttachmentListEntity> attachList;
    private DecimalFormat df;

    public CostApplyFragment(){}
    public static CostApplyFragment newInstance(Bundle bundle){
        CostApplyFragment fragment = new CostApplyFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    public static CostApplyFragment newInstance(){
        CostApplyFragment fragment = new CostApplyFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        df = new DecimalFormat("######0.00");
        getData();
        loadDraftListData();
    }

    public void getData() {
        if (getArguments() != null) {
            Map<String, Object> params = CommonValues.getCommonParams(getActivity());
            params.put("userId",getArguments().getString("userId"));
            params.put("barCode", getArguments().getString("barCode"));
            params.put("workflowType", "ExpenseRequest");
            HttpManager.getInstance().requestResultForm(CommonValues.REQ_EXPENSE_APPROVE, params, CostDetailBean.class, new HttpManager.ResultCallback<CostDetailBean>() {
                @Override
                public void onSuccess(String json,final CostDetailBean entity) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if(entity!=null&&entity.getCode().equals("100")){
                                bean = entity;
                                if (getArguments().getBoolean("Remak")){
                                    settoolbar();
                                    setButtonsTitles(new String[]{"重新提交"});
                                }
                                setGroup(getGroupList());
                                setPb(false);
                                setButtonllEnable(true);
                                notifyDataSetChanged();
                            }else {
                                Toast.makeText(getActivity(), entity.getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

                @Override
                public void onFailure(final String msg) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "请检查网络", Toast.LENGTH_LONG).show();
                            getActivity().finish();
                        }
                    });

                }
            });
        }

    }

    private void settoolbar() {
        HandToolbar toolbar = getToolbar();
        final View his = toolbar.findViewById(R.id.tv_history);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                his.setVisibility(View.VISIBLE);
                his.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HistoryActivity.startActivity(getActivity(),bean.getRetData().getHisData());
                    }
                });
            }
        });

    }

    @Override
    public List<Group> getGroupList() {
        double sum = 0;
        //获取员工编号
        mUserId = loginEntity.getUserId();
        //获取Eid
        mEid = loginEntity.getRetData().getUserInfo().getEid();
        //获取姓名
        mNameCN = loginEntity.getRetData().getUserInfo().getNameCN();
        //获取岗位
        mPositionNameCN = loginEntity.getRetData().getUserInfo().getPositionNameCN();
        //岗级
        Grade = loginEntity.getRetData().getUserInfo().getGrade().trim();
        List<LoginEntity.RetDataBean.UserAccountBean> userAccount = loginEntity.getRetData().getUserAccount();
        List<Group> groups = new ArrayList<>();
        if(bean == null){
            uuid = UUID.randomUUID().toString();
            if (userAccount.size() == 0){
                List<HandInputGroup.Holder> subHolder = new ArrayList<>();
                subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Company_Name), true, false, "/" + this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.SELECT));
                subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Cost_Center), true, false, "/" + this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.SELECT));
                subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Reimbursement_Person), false, false, mNameCN, HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
                subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Employee_ID), false, true, mEid, HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
                subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Grade), false, true, Grade, HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
                subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Position), false, true, mPositionNameCN, HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
                subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Employee_ID), true, false, "/" + this.getString(R.string.Please_Fill), HandInputGroup.VALUE_TYPE.TEXTFILED));
                subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Name), true, false,"", HandInputGroup.VALUE_TYPE.TEXTFILED));
                subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Bank), true, false, "", HandInputGroup.VALUE_TYPE.SELECT).setEditable(false));
                subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Bank_Account), true, false, "", HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
                subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Bank_Adress), true, false, "", HandInputGroup.VALUE_TYPE.SELECT).setEditable(false));
                subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Branch_Bank), true, true, "", HandInputGroup.VALUE_TYPE.SELECT).setEditable(false));
                subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Has_Borrow), true, false, this.getString(R.string.No), HandInputGroup.VALUE_TYPE.BUTTONS));
                subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Has_Buget), true, false, this.getString(R.string.No), HandInputGroup.VALUE_TYPE.BUTTONS));
                subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Relate_Hospitalit_Order), false, false, "", HandInputGroup.VALUE_TYPE.SUB_LIST).setColor(Color.BLUE).setValue(enterExpUnion.keySet()));
                subHolder.add(new HandInputGroup.Holder("报销说明", false, false, "", HandInputGroup.VALUE_TYPE.TEXTFILED));
                groups.add(new Group(this.getString(R.string.Basic_Information), null, true, null, subHolder));
                List<HandInputGroup.Holder> subDetail = new ArrayList<>();
                subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Expense_Type), true, false,"/" + this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.SELECT));
                subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Expense_Cost), true, false,"/" + this.getString(R.string.Please_Fill), HandInputGroup.VALUE_TYPE.DOUBLE));
                groups.add(new Group(this.getString(R.string.Details_Information), R.mipmap.add_detail3x, true, null, subDetail).sethasDelete(true));
                List<HandInputGroup.Holder> subDetailTotal = new ArrayList<>();
                groups.add(new Group(this.getString(R.string.Total), null, true, sum +"", subDetailTotal));
                List<HandInputGroup.Holder> holderList4 = new ArrayList<>();
                holderList4.add(new HandInputGroup.Holder(this.getString(R.string.Attachment_Type), false, false, "/" + this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.SELECT));
                if (paths == null) {
                    paths = new HashMap<>();
                    paths.put("attach",new HashSet<Uri>());
                }
                holderList4.add(new HandInputGroup.Holder(this.getString(R.string.Select_Attachments), false, false, "/请上传", HandInputGroup.VALUE_TYPE.FILES_UPLOAD).setValue(paths.get("attach")));
                groups.add(new Group(this.getString(R.string.Attachment_Info), null, false, null, holderList4));
            }else {
                LoginEntity.RetDataBean.UserAccountBean userAccountBean = userAccount.get(0);
                payeecode = userAccountBean.getPayeeBank();
                provinceid = userAccountBean.getPayeeBankProvince();
                cityid = userAccountBean.getPayeeBankCity();
                List<HandInputGroup.Holder> subHolder = new ArrayList<>();
                subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Company_Name), true, false, "/" + this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.SELECT));
                subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Cost_Center), true, false, "/" + this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.SELECT));
                subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Reimbursement_Person), false, true, mNameCN, HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
                subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Employee_ID), false, true, mEid, HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
                subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Grade), false, true, Grade, HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
                subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Position), false, true, mPositionNameCN, HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
                subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Employee_ID), true, false, mEid, HandInputGroup.VALUE_TYPE.TEXTFILED));
                subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Name), true, false,userAccountBean.getAccountName(), HandInputGroup.VALUE_TYPE.TEXTFILED));
                subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Bank), true, false, userAccountBean.getPayeeBankName(), HandInputGroup.VALUE_TYPE.SELECT).setEditable(false));
                subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Bank_Account), true, false, userAccountBean.getAccountNumber(), HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
                subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Bank_Adress), true, false, userAccountBean.getProvinceName() + " " + userAccountBean.getCityName(), HandInputGroup.VALUE_TYPE.SELECT).setEditable(false));
                subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Branch_Bank), true, true, userAccountBean.getPayeeBranchBank(), HandInputGroup.VALUE_TYPE.SELECT).setEditable(false));
                subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Has_Borrow), true, false, this.getString(R.string.No), HandInputGroup.VALUE_TYPE.BUTTONS));
                subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Has_Buget), true, false, this.getString(R.string.No), HandInputGroup.VALUE_TYPE.BUTTONS));
                subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Relate_Hospitalit_Order), false, false, "", HandInputGroup.VALUE_TYPE.SUB_LIST).setColor(Color.BLUE).setValue(enterExpUnion.keySet()));
                subHolder.add(new HandInputGroup.Holder("报销说明", false, false, "", HandInputGroup.VALUE_TYPE.TEXTFILED));
                groups.add(new Group(this.getString(R.string.Basic_Information), null, true, null, subHolder));
                List<HandInputGroup.Holder> subDetail = new ArrayList<>();
                subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Expense_Type), true, false,"/" + this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.SELECT));
                subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Expense_Cost), true, false,"/" + this.getString(R.string.Please_Fill), HandInputGroup.VALUE_TYPE.DOUBLE));
                groups.add(new Group(this.getString(R.string.Details_Information), R.mipmap.add_detail3x, true, null, subDetail).sethasDelete(true));
                List<HandInputGroup.Holder> subDetailTotal = new ArrayList<>();
                groups.add(new Group(this.getString(R.string.Total), null, true, sum +"", subDetailTotal));
                List<HandInputGroup.Holder> holderList4 = new ArrayList<>();
                holderList4.add(new HandInputGroup.Holder(this.getString(R.string.Attachment_Type), false, false, "/" + this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.SELECT));
                if (paths == null) {
                    paths = new HashMap<>();
                    paths.put("attach",new HashSet<Uri>());
                }
                holderList4.add(new HandInputGroup.Holder(this.getString(R.string.Select_Attachments), false, false, "/请上传", HandInputGroup.VALUE_TYPE.FILES_UPLOAD).setValue(paths.get("attach")));
                groups.add(new Group(this.getString(R.string.Attachment_Info), null, false, null, holderList4));
            }

        }else {
            uuid = bean.getRetData().getDetailData().getIdentifier();
            companycode = bean.getRetData().getDetailData().getCompany();
            loadById(companycode);
            CostCentercode = bean.getRetData().getDetailData().getCostCenter();
            payeecode = bean.getRetData().getDetailData().getPayeeBank();
            provinceid = bean.getRetData().getDetailData().getPayeeBankProvince();
            cityid = bean.getRetData().getDetailData().getPayeeBankCity();
            CostDetailBean.RetDataBean.DetailDataBean detailData = bean.getRetData().getDetailData();
            List<CostDetailBean.RetDataBean.DetailDataBean.ExpenseDetailBean> expenseDetail = detailData.getExpenseDetail();
            List<HandInputGroup.Holder> subHolder = new ArrayList<>();
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Company_Name), true, false, detailData.getCompanyName(), HandInputGroup.VALUE_TYPE.SELECT));
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Cost_Center), true, false, detailData.getCostCenterName(), HandInputGroup.VALUE_TYPE.SELECT));
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Reimbursement_Person), false, false, mNameCN, HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Employee_ID), false, false, mEid, HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Grade), false, true, Grade, HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Position), false, false, mPositionNameCN, HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Employee_ID), true, false, detailData.getPayeeId(), HandInputGroup.VALUE_TYPE.TEXTFILED));
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Name), true, false, detailData.getPayeeName(), HandInputGroup.VALUE_TYPE.TEXTFILED));
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Bank), true, false, detailData.getPayeeBankName(), HandInputGroup.VALUE_TYPE.SELECT).setEditable(false));
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Bank_Account), true, false, detailData.getPayeeBankAccount(), HandInputGroup.VALUE_TYPE.DOUBLE).setEditable(false));
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Bank_Adress), true, false, detailData.getPayeeBankProvinceName() + " " + detailData.getPayeeBankCityName(), HandInputGroup.VALUE_TYPE.SELECT).setEditable(false));
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Branch_Bank), true, true, detailData.getPayeeBranchBank(), HandInputGroup.VALUE_TYPE.SELECT).setEditable(false));
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Has_Borrow), true, false, detailData.isIsOffsetLoan()?this.getString(R.string.Yes):this.getString(R.string.No), HandInputGroup.VALUE_TYPE.BUTTONS));
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Has_Buget), true, false, detailData.isHasBuget()?this.getString(R.string.Yes):this.getString(R.string.No), HandInputGroup.VALUE_TYPE.BUTTONS));
            String relateHospitalitOrder = detailData.getRelatedEntertainmentOrders();
            if (!relateHospitalitOrder.isEmpty()) {
                String[] split = relateHospitalitOrder.split(";");
                if (split.length > 1) {
                    for (int i = 0; i < split.length; i++) {
                        for (CostDetailBean.RetDataBean.DetailDataBean.ExpenseDetailBean travelExpenseDetailBean : expenseDetail) {
                            if (travelExpenseDetailBean.getEntertainmentBarCode().equals(split[i])) {
                                EnterExpenseOrdersEntity.RetDataBean.EntertainmentExpenseOrdersBean ent = new EnterExpenseOrdersEntity.RetDataBean.EntertainmentExpenseOrdersBean();
                                ent.setBarCode(travelExpenseDetailBean.getEntertainmentBarCode());
                                ent.setAmount(travelExpenseDetailBean.getExpenseCost());
                                enterExpUnion.put(split[i], ent);
                            }
                        }

                    }
                } else if (split.length == 1) {
                    for (CostDetailBean.RetDataBean.DetailDataBean.ExpenseDetailBean travelExpenseDetailBean : expenseDetail) {
                        if (travelExpenseDetailBean.getEntertainmentBarCode().equals(split[0])) {
                            EnterExpenseOrdersEntity.RetDataBean.EntertainmentExpenseOrdersBean ent = new EnterExpenseOrdersEntity.RetDataBean.EntertainmentExpenseOrdersBean();
                            ent.setBarCode(travelExpenseDetailBean.getEntertainmentBarCode());
                            ent.setAmount(travelExpenseDetailBean.getExpenseCost());
                            enterExpUnion.put(split[0], ent);
                        }
                    }
                }
            }
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Relate_Hospitalit_Order), false, false, "", HandInputGroup.VALUE_TYPE.SUB_LIST).setValue(enterExpUnion.keySet()));
            subHolder.add(new HandInputGroup.Holder("报销说明", false, false, detailData.getRemark().isEmpty()?"/" + this.getString(R.string.Please_Fill):detailData.getRemark(), HandInputGroup.VALUE_TYPE.TEXTFILED));

            groups.add(new Group(this.getString(R.string.Basic_Information), null, true, null, subHolder));
            for (int i = 0; i < expenseDetail.size(); i++) {
                List<HandInputGroup.Holder> subDetail = new ArrayList<>();
                subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Expense_Type), true, false,expenseDetail.get(i).getExpenseTypeName(), HandInputGroup.VALUE_TYPE.SELECT));
                subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Expense_Cost), true, false,expenseDetail.get(i).getExpenseCost(), HandInputGroup.VALUE_TYPE.DOUBLE));
                double v = Double.parseDouble(expenseDetail.get(i).getExpenseCost().isEmpty()?"0":expenseDetail.get(i).getExpenseCost());
                sum +=v;
                if (i==expenseDetail.size()-1){
                    if (!expenseDetail.get(i).getEntertainmentBarCode().isEmpty()){
                        groups.add(new Group(this.getString(R.string.Details_Information), R.mipmap.add_detail3x, true, null, subDetail).setoldBarcode(expenseDetail.get(i).getEntertainmentBarCode()).sethasDelete(false));
                    }else {
                        groups.add(new Group(this.getString(R.string.Details_Information), R.mipmap.add_detail3x, true, null, subDetail).sethasDelete(true));
                    }
                }else {
                    if (!expenseDetail.get(i).getEntertainmentBarCode().isEmpty()){
                        groups.add(new Group(this.getString(R.string.Details_Information), null, true,null, subDetail).setoldBarcode(expenseDetail.get(i).getEntertainmentBarCode()).sethasDelete(false));
                    }else {
                        groups.add(new Group(this.getString(R.string.Details_Information), null, true, null, subDetail).sethasDelete(true));
                    }
                }

            }
            List<HandInputGroup.Holder> subDetailTotal = new ArrayList<>();
            groups.add(new Group(this.getString(R.string.Total), null, true, sum +"", subDetailTotal));
            attachList = bean.getRetData().getAttchList();
            if (attachList != null && attachList.size() > 0) {
                if (paths == null) {
                    paths = new HashMap<>();
                }
                List<HandInputGroup.Holder> subAddNoNull = new ArrayList<>();
                for (AttachmentListEntity entity : attachList) {
                    paths.put("attach", new HashSet<Uri>());
                    loadRemoteFiles(entity);
                }
                subAddNoNull.add(new HandInputGroup.Holder(this.getString(R.string.Attachment_Type), false, false, attachList.get(0).getFileGroupName(), HandInputGroup.VALUE_TYPE.SELECT));
                subAddNoNull.add(new HandInputGroup.Holder<HashSet<Uri>>(this.getString(R.string.Select_Attachments), false, false,"", HandInputGroup.VALUE_TYPE.FILES_UPLOAD)
                        .setColor(Color.BLUE).setDrawableRight(-1).setValue(paths.get("attach")).setTag(this));
                groups.add(new Group(this.getString(R.string.Attachment_Info), null, false, null, subAddNoNull));
            } else {
                List<HandInputGroup.Holder> subAddNull = new ArrayList<>();
                subAddNull.add(new HandInputGroup.Holder(this.getString(R.string.Attachment_Type), false, false, "/" + this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.SELECT));
                if (paths == null) {
                    paths = new HashMap<>();
                    paths.put("attach", new HashSet<Uri>());
                }
                subAddNull.add(new HandInputGroup.Holder(this.getString(R.string.Select_Attachments), false, false, "/" + this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.FILES_UPLOAD).setValue(paths.get("attach")));
                groups.add(new Group(this.getString(R.string.Attachment_Info), null, false, null, subAddNull));
            }
        }
        return groups;
    }

    private void loadRemoteFiles(final AttachmentListEntity entity) {
        final Map<String, Object> param = CommonValues.getCommonParams(getActivity());
        param.put("attachmentId", entity.getId());
        HttpManager.getInstance().requestResultForm(CommonValues.CLICK_LOOK_DATA, param, AttachmentSingleEntity.class, new HttpManager.ResultCallback<AttachmentSingleEntity>() {
            @Override
            public void onSuccess(final String content, final AttachmentSingleEntity attachmentListEntity) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (attachmentListEntity != null && attachmentListEntity.getRetData() != null) {
                            if (attachmentListEntity.getCode().equals("100")) {
                                String attachment = attachmentListEntity.getRetData().getAttachment();
                                String attachType = attachmentListEntity.getRetData().getType();
                                String substring = null;
                                substring = DataUtil.getExtensionFromIME(attachType);
                                if (substring == null) {
                                    Toast.makeText(getContext(), "未知的文件类型！", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                File file = DataUtil.base64ToFileWithName(attachment, substring, entity.getFileName());
                                Uri uri = Uri.fromFile(file);
                                paths.get("attach").add(uri);
                                entity.setLocalFileUri(uri);
                                notifyDataSetChanged();
                            }
                        }
                    }
                });
            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

    @Override
    public void onOneItemBottomDrawableResClick(int index) {
        List<HandInputGroup.Holder> subDetail = new ArrayList<>();
        subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Expense_Type), true, false,"/" + this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.SELECT));
        subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Expense_Cost), true, false,"/" + this.getString(R.string.Please_Fill), HandInputGroup.VALUE_TYPE.DOUBLE));
        addGroup(index+1,new Group(this.getString(R.string.Details_Information), R.mipmap.add_detail3x, true, null, subDetail).sethasDelete(true));
        getGroup().get(index).sethasDelete(true).setDrawableRes(null);
        notifyDataSetChanged();
    }

    @Override
    public void setToolbar(HandToolbar toolbar) {
        toolbar.setTitle("费用申请");
        toolbar.setDisplayHomeAsUpEnabled(true, getActivity());
    }

    @Override
    public String[] getBottomButtonsTitles() {
        return new String[]{"提交","保存"};
    }

    @Override
    public void onClickItemContentSetter(final HandInputGroup.Holder holder) {
        mholder = holder;
        if (mholder.getType() == HandInputGroup.VALUE_TYPE.DATE) {
            showDateTimePicker(holder,true);
        }
        else if (TextUtils.equals(mholder.getKey(),this.getString(R.string.Has_Borrow))||TextUtils.equals(mholder.getKey(),this.getString(R.string.Has_Buget))) {
            checkedButton(holder);
        }else if(mholder.getKey().equals(this.getString(R.string.Company_Name))){
            showSelector(holder, getDescr(companyEntities), new OnSelectedResultCallback() {
                @Override
                public void onSelected(Group ownGroup, HandInputGroup.Holder holder, int mainIndex, int itemIndex) {
                    getDisplayValueByKey(CostApplyFragment.this.getString(R.string.Cost_Center)).setDispayValue("/" + CostApplyFragment.this.getString(R.string.Please_Select));
                    loadById(DataUtil.getDicodeByDescr(companyEntities,holder.getRealValue()));
                }
            });
        }else if(mholder.getKey().equals(this.getString(R.string.Cost_Center))){
            if(DataUtil.getDicodeByDescr(companyEntities,getDisplayValueByKey(this.getString(R.string.Company_Name)).getRealValue())==null){
                ToastUtil.showToast(getContext(),this.getString(R.string.Please_Select) + "公司");
            }else{
                if(costcenterList==null||costcenterList.size()==0){
                    ToastUtil.showToast(getContext(),"该公司暂无数据");
                }else {
                    showSelector(holder, getDescr(costcenterList), new OnSelectedResultCallback() {
                        @Override
                        public void onSelected(Group ownGroup, HandInputGroup.Holder holder, int mainIndex, int itemIndex) {
                            String newCostCentercode = getDicodeByDescr(costcenterList,holder.getDispayValue());
                            if (!newCostCentercode.isEmpty() && !newCostCentercode.equals(CostCentercode)){
                                CostCentercode = new String(newCostCentercode);
                            }
                        }
                    });
                }
            }
        }else if(mholder.getKey().equals(this.getString(R.string.Expense_Type))){
            showSelector(holder, getDescr(draftExpenseType));
        }else if(mholder.getKey().equals(this.getString(R.string.Attachment_Type))){
            showSelector(holder,getDescr(expenseAttachmentTypes));
        }else if (holder.getKey().equals(this.getString(R.string.Select_Attachments))) {
            AttachmentListEntity entity = (AttachmentListEntity) holder.getValue();
            lookAttachmentData(entity);
        }
    }

    @Override
    public void FocusChange(HandInputGroup.Holder item, boolean hasFocus) {
        super.FocusChange(item, hasFocus);
        HandInputGroup.Holder byKey = getDisplayValueByKey(this.getString(R.string.Payee_Name));
        if (item.getKey().equals(this.getString(R.string.Payee_Name)) && hasFocus){
            byKey.setEditable(false);
        }
    }

    @Override
    public void onHolderChangedOver(int main, int index,HandInputGroup.Holder holder) {
        if (holder.getKey().equals(this.getString(R.string.Payee_Employee_ID))){
            if (holder.getRealValue().isEmpty()){

            }else {
                getPersonalAccountInfo(holder.getRealValue());
            }
        }
    }

    private void getPersonalAccountInfo(String realValue) {
        Map<String, Object> params = CommonValues.getCommonParams(getActivity());
        params.put("targetEid",realValue);
        params.put("targetUserid","");
        params.put("useEid","true");
        params.put("getAll","false");
        HttpManager.getInstance().requestResultForm(CommonValues.GET_PERSONAL_ACCOUNT_INFO, params, PersonalAccountEntity.class, new HttpManager.ResultCallback<PersonalAccountEntity>() {
            @Override
            public void onSuccess(String json, final PersonalAccountEntity personalAccountEntity) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (personalAccountEntity.getCode().equals("100")){
                            if (!personalAccountEntity.getRetData().isEmpty() && personalAccountEntity.getRetData() !=null) {
                                PersonalAccountEntity.RetDataBean retDataBean = personalAccountEntity.getRetData().get(0);
                                getDisplayValueByKey(CostApplyFragment.this.getString(R.string.Payee_Name)).setDispayValue(retDataBean.getAccountName());
                                getDisplayValueByKey(CostApplyFragment.this.getString(R.string.Payee_Bank)).setDispayValue(retDataBean.getPayeeBankName());
                                getDisplayValueByKey(CostApplyFragment.this.getString(R.string.Payee_Bank_Account)).setDispayValue(retDataBean.getAccountNumber());
                                getDisplayValueByKey(CostApplyFragment.this.getString(R.string.Payee_Bank_Adress)).setDispayValue(retDataBean.getProvinceName() + " " + retDataBean.getCityName());
                                getDisplayValueByKey(CostApplyFragment.this.getString(R.string.Payee_Branch_Bank)).setDispayValue(retDataBean.getPayeeBranchBank());
                                provinceid = personalAccountEntity.getRetData().get(0).getPayeeBankProvince();
                                cityid = personalAccountEntity.getRetData().get(0).getPayeeBankCity();
                                payeecode = personalAccountEntity.getRetData().get(0).getPayeeBank();
                                notifyDataSetChanged();
                            }
                        } else {
                            ToastUtil.showToast(getContext(),personalAccountEntity.getMsg());
                            getDisplayValueByKey(CostApplyFragment.this.getString(R.string.Payee_Name)).setDispayValue("");
                            getDisplayValueByKey(CostApplyFragment.this.getString(R.string.Payee_Bank)).setDispayValue("");
                            getDisplayValueByKey(CostApplyFragment.this.getString(R.string.Payee_Bank_Account)).setDispayValue("");
                            getDisplayValueByKey(CostApplyFragment.this.getString(R.string.Payee_Bank_Adress)).setDispayValue("");
                            getDisplayValueByKey(CostApplyFragment.this.getString(R.string.Payee_Branch_Bank)).setDispayValue("");
                            notifyDataSetChanged();
                        }
                    }
                });
            }
            @Override
            public void onFailure(String msg) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast(getContext(),"未找到该员工编号");
                        getDisplayValueByKey(CostApplyFragment.this.getString(R.string.Payee_Name)).setDispayValue("");
                        getDisplayValueByKey(CostApplyFragment.this.getString(R.string.Payee_Bank)).setDispayValue("");
                        getDisplayValueByKey(CostApplyFragment.this.getString(R.string.Payee_Bank_Account)).setDispayValue("");
                        getDisplayValueByKey(CostApplyFragment.this.getString(R.string.Payee_Bank_Adress)).setDispayValue("");
                        getDisplayValueByKey(CostApplyFragment.this.getString(R.string.Payee_Branch_Bank)).setDispayValue("");
                        notifyDataSetChanged();
                    }
                });
            }
        });
    }

    @Override
    public void onBarCodeChanged(int main, int index, HandInputGroup.Holder holder, String oldBarcode, SubListLayout.ActionType type, int id) {
        if(type == SubListLayout.ActionType.ADD){
            int size = enterExpUnion.size();
            String ss = "";
            if (size > 0){
                ss = enterExpUnion.keyAt(0);
                for (int i = 1; i < size; i++) {
                    ss +=";" + enterExpUnion.keyAt(i);
                }
            }
            ActionSheetUnionOrdersActivity.openActionSheet(getActivity(), CommonValues.WORKFLOW_ENTER_EXPENSE, ss, enterExpUnion, new ActionSheetUnionOrdersActivity.Result() {
                @Override
                public void onResult(ArrayMap<String, Object> data) {
                    enterExpUnion = data;
                    for(Object o:data.values()){
                        addBTripUnionDetail(o);
                    }
                    notifyDataSetChanged();
                }
            });
        }else if(type == SubListLayout.ActionType.DELETE){
            enterExpUnion.remove(oldBarcode);
            notifyGroupChanged(0,1);
            List<Group> groupList = getGroup();
            for (int i = 0; i < groupList.size(); i++) {
                if (groupList.get(i).getOldBarcode()!=null){
                    if (groupList.get(i).getOldBarcode().equals(oldBarcode)){
                        groupList.remove(i);
                        notifyGroupChanged(i,3);
                        i--;
                    }
                }
            }
        }else if(type == SubListLayout.ActionType.CLICK){
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

    private void addBTripUnionDetail(Object obj){
        EnterExpenseOrdersEntity.RetDataBean.EntertainmentExpenseOrdersBean beanServe = (EnterExpenseOrdersEntity.RetDataBean.EntertainmentExpenseOrdersBean) obj;
        if (beanServe != null) {
            List<HandInputGroup.Holder> subDetail = new ArrayList<>();
            subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Expense_Type), true, false,"/" + this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.SELECT));
            subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Expense_Cost), true, false,beanServe.getAmount(), HandInputGroup.VALUE_TYPE.DOUBLE));
            getGroup().add(1, new Group(this.getString(R.string.Details_Information), null, true,null, subDetail).setoldBarcode(beanServe.getBarCode()));
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBottomButtonsClick(final String title, final List<Group> groups) {
        setButtonllEnable(false);
        String over = isOver(groups);
        if (!title.equals("保存") && over != null){
            ToastUtil.showToast(getContext(),this.getString(R.string.Please_Fill) + over);
            setButtonllEnable(true);
        }else {
            if (bean==null){
            }else {
                barcode = bean.getRetData().getDetailData().getBarCode();
                mId = bean.getRetData().getDetailData().getId();
            }
            Map<String, Object> body = CommonValues.getCommonParams(getActivity());
            body.remove("auditNode");
            Map<String, Object> mainData = new HashMap<String, Object>();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date curDate = new Date(System.currentTimeMillis());//获取当前时间
            String str = formatter.format(curDate);
            if(bean == null){
                mainData.put("CreateTime",str);
                mainData.put("Remark", "");
                body.put("SN", "");
            }else{
                mainData.put("CreateTime",bean.getRetData().getDetailData().getCreateTime());
                mainData.put("BarCode",barcode);
                mainData.put("Id", mId);
                body.put("SN", getArguments().getString("SN"));
            }
            mainData.put("Remark", getDisplayValueByKey("报销说明").getRealValue());
            mainData.put("UpdateTime",str);
            mainData.put("Identifier",uuid);
            mainData.put("RequestorId",mEid);//发起人id
            mainData.put("SubmitBy",mUserId);//提交人
            mainData.put("UpdateBy",mUserId);
            mainData.put("Company",DataUtil.getDicodeByDescr(companyEntities,getDisplayValueByKey(this.getString(R.string.Company_Name)).getRealValue()));//公司代码
            mainData.put("CostCenter",DataUtil.getDicodeByDescr(costcenterList,getDisplayValueByKey(this.getString(R.string.Cost_Center)).getRealValue()));// 成本中心码
            mainData.put("PayeeId",getDisplayValueByKey(this.getString(R.string.Payee_Employee_ID)).getRealValue());
            mainData.put("PayeeName",getDisplayValueByKey(this.getString(R.string.Payee_Name)).getRealValue());
            mainData.put("PayeeBank",payeecode);//收款银行
            mainData.put("PayeeBankName",getDisplayValueByKey(this.getString(R.string.Payee_Bank)).getRealValue());
            mainData.put("PayeeBankAccount",getDisplayValueByKey(this.getString(R.string.Payee_Bank_Account)).getRealValue());//收款账号
            mainData.put("PayeeBankProvinceName", getDisplayValueByKey(this.getString(R.string.Payee_Bank_Adress)).getRealValue().split(" ")[0]);//收款银行省
            mainData.put("PayeeBankProvince", provinceid);//收款银行省id
            mainData.put("PayeeBankCityName",getDisplayValueByKey(this.getString(R.string.Payee_Bank_Adress)).getRealValue().split(" ")[0]);//收款银行市
            mainData.put("PayeeBankCity", cityid);//收款银行市id
            mainData.put("PayeeBranchBank",getDisplayValueByKey(this.getString(R.string.Payee_Branch_Bank)).getRealValue());//收款支行
            mainData.put("IsOffsetLoan",getDisplayValueByKey(this.getString(R.string.Has_Borrow)).getRealValue().equals(this.getString(R.string.Yes)));//是否冲借款
            mainData.put("HasBuget",getDisplayValueByKey(this.getString(R.string.Has_Buget)).getRealValue().equals(this.getString(R.string.Yes)));//是否有预算
            mainData.put("TotalAmount",getGroupsByTitle(this.getString(R.string.Total)).get(0).getGroupTopRightTitle());
            mainData.put("AuditTotalAmount",getGroupsByTitle(this.getString(R.string.Total)).get(0).getGroupTopRightTitle());
            mainData.put("RelatedEntertainmentOrders","");
            mainData.put("IsGenerateVoucherSuccess",false);
            mainData.put("IsGenerateAccountingVouchers",false);
            int size = enterExpUnion.size();
            if (size > 0){
                String ss = enterExpUnion.keyAt(0);
                for (int i = 1; i < size; i++) {
                    ss +=";" + enterExpUnion.keyAt(i);
                }
                mainData.put("RelatedEntertainmentOrders",ss);
            }
            mainData.put("ExpenseDetail", null);
            mainData.put("IsPostToPS",false);
            String s = str.split(" ")[0];
            mainData.put("Summary", s.split("-")[0] + s.split("-")[1] + s.split("-")[2] + "-" + GeelyApp.getLoginEntity().getRetData().getUserInfo().getDeptNameCN() + GeelyApp.getLoginEntity().getRetData().getUserInfo().getNameCN()
                    + "-" + getGroupsByTitle(this.getString(R.string.Total)).get(0).getGroupTopRightTitle() + "RMB费用");
            String main = new Gson().toJson(mainData);
            body.put("mainData",main);
            List<Object> details = new ArrayList<Object>();
            for (int i = 1; i < getGroup().size() - 2; i++) {
                Map<String, Object> detailData = new HashMap<String, Object>();
                detailData.put("Id",0);
                detailData.put("WorkflowIdentifier", uuid);
                detailData.put("IsOffset",false);
                detailData.put("IsFinance",false);
                detailData.put("ExpenseType",groups.get(i).getHolders().get(0).getRealValue().isEmpty()?"":groups.get(i).getHolders().get(0).getRealValue());//费用类型
                detailData.put("ExpenseCost", groups.get(i).getHolders().get(1).getRealValue().isEmpty() ? "0.00" : groups.get(i).getHolders().get(1).getRealValue());//费用
                if (groups.get(i).getOldBarcode() != null){
                    detailData.put("EntertainmentBarCode",groups.get(i).getOldBarcode());
                }
                details.add(detailData);
            }
            String detail = new Gson().toJson(details);
            body.put("detailData",detail);
            applySaveOrStart(CommonValues.REQ_EXPENSE_APPLY, body, title);
        }

    }

    @Override
    public void onHolderTextChanged(int main, int index, HandInputGroup.Holder holder) {
        if (holder.getKey().equals(this.getString(R.string.Expense_Cost))){
            List<Group> detals = getGroupsByTitle(this.getString(R.string.Details_Information));
            double sum =0;
            for (Group detal : detals) {
                double TicketCost = Double.parseDouble(detal.getHolders().get(1).getRealValue().isEmpty()?"0":detal.getHolders().get(1).getRealValue());
                sum += TicketCost;
            }
            final double finalSum = sum;
            getGroupsByTitle(this.getString(R.string.Total)).get(0).setGroupTopRightTitle(df.format(finalSum));
            notifyGroupChanged(getGroup().size()-2, 1);

        }else if (holder.getKey().equals(this.getString(R.string.Payee_Employee_ID))){
            getDisplayValueByKey(this.getString(R.string.Payee_Name)).setEditable(true);
        }
    }

    private void loadDraftListData() {
        HttpManager manager = HttpManager.getInstance();
        Map<String, Object> params = CommonValues.getCommonParams(getActivity());

        //公司集合
        String company = CacheManger.getInstance().getData(CommonValues.GET_COMPANY_LIST);
        companyEntities = GsonUtil.parseJsonToBean(company,CompanyEntity.class).getRetData();

        //费用类别
        params.put("code",2144);
        manager.requestResultForm(CommonValues.GET_DICTIONARY_LIST, params, DictionaryEntity.class, new HttpManager.ResultCallback<DictionaryEntity>() {
            @Override
            public void onSuccess(String content, DictionaryEntity expenseTypeEntity) {
                draftExpenseType = expenseTypeEntity.getRetData().get(0).getDataList();
            }

            @Override
            public void onFailure(String content) {
            }
        });

        params.put("code", 2156); //附件类别
        manager.requestResultForm(CommonValues.GET_DICTIONARY_LIST, params, DictionaryEntity.class, new HttpManager.ResultCallback<DictionaryEntity>() {
            @Override
            public void onSuccess(String content, final DictionaryEntity expenseTypeEntity) {
                expenseAttachmentTypes = expenseTypeEntity.getRetData().get(0).getDataList();
            }

            @Override
            public void onFailure(String content) {
            }
        });


    }

    private void loadById(String code) {
        HttpManager manager = HttpManager.getInstance();
        Map<String, Object> commonParams = CommonValues.getCommonParams(getActivity());
        commonParams.put("companyCode", code);
        manager.requestResultForm(CommonValues.GET_COMPANYCB_LIST, commonParams, CompanyCBPEntity.class, new HttpManager.ResultCallback<CompanyCBPEntity>() {
            @Override
            public void onSuccess(String content, CompanyCBPEntity companyCBPEntity) {
                //成本中心
                costcenterList = companyCBPEntity.getRetData().getCostcenterList();
            }

            @Override
            public void onFailure(String content) {
            }
        });
    }

    @Override
    public void onDeleteRemoteFile(Uri uri) {
        for (final AttachmentListEntity entity : attachList){
            if(uri == entity.getLocalFileUri()){
                final String entityId = entity.getId();
                Map<String, Object> param = CommonValues.getCommonParams(getActivity());
                param.put("attachmentId", entityId);
                param.put("workflowType", getArguments().getString("workflowType"));
                HttpManager.getInstance().requestResultForm(CommonValues.DELETE_ATTACHMENT, param, AttachDeleteEntity.class, new HttpManager.ResultCallback<AttachDeleteEntity>() {
                    @Override
                    public void onSuccess(String content, final AttachDeleteEntity attachmentListEntity) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (attachmentListEntity.getCode().equals("100")){
                                    ToastUtil.showToast(getContext(),"删除成功！");
                                }else {
                                    ToastUtil.showToast(getContext(),"删除失败！");
                                }
                            }
                        });
                    }

                    @Override
                    public void onFailure(String msg) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtil.showToast(getContext(),"请检查网络");
                            }
                        });
                    }
                });

            }
        }
    }

    public void AddUri(Uri uri) {
        String extension = null;
        String path = null;
        if("file".equals(uri.getScheme())){
            path = uri.getPath();
            extension = path.substring(path.lastIndexOf("."));
        }else {
            path = HttpManager.getRealPathFromUri(uri, getContext());
            if(path!=null){
                uri = Uri.parse(path);
            }
            uri = Uri.fromFile(new File(uri.toString()));
            extension = path.substring(path.lastIndexOf("."));
        }
        if (extension != null){
            extension = extension.toLowerCase();
            if(extension.endsWith(".jpg")||extension.endsWith(".jpeg")|| extension.endsWith(".png")||
                    extension.endsWith(".bmp")|| extension.endsWith(".gif")||extension.endsWith(".doc")||
                    extension.endsWith(".docx")||extension.endsWith(".xls")||extension.endsWith(".xlsx")||
                    extension.endsWith(".rar")){
                Map<String, Object> map = CommonValues.getCommonParams(getActivity());
                String groupName = getDisplayValueByKey(this.getString(R.string.Attachment_Type)).getRealValue();
                map.put("fileGroupName", groupName);
                map.put("workflowIdentifier", uuid);
                map.put("workflowType", CommonValues.WORKFLOW_COST);
                map.put("createBy", GeelyApp.getLoginEntity().getUserId());
                map.put("fileGroupValue", DataUtil.getDicIdByDescr(expenseAttachmentTypes, groupName));
                path = FileUtils.getPath(getActivity(), uri);
                String name = path.substring(path.lastIndexOf("/") + 1);
                map.put("orignFileName", name);
                map.put("fileName", name.substring(0, name.lastIndexOf(".")));
                map.put("picLocalId", 0);
                String fileBase64 = HttpManager.uri2Base64(uri, getActivity());
                if (fileBase64 == null) {
                }else {
                    map.put("fileBase64", fileBase64);
                    setButtonllEnable(false);
                    showDialog("附件上传中......",0);
                    HttpManager.getInstance().requestResultForm(CommonValues.REQ_ADD_ATTACHMENT, map, CommonDataEntity.class, new HttpManager.ResultCallback<CommonDataEntity>() {
                        @Override
                        public void onSuccess(String json, CommonDataEntity commonDataEntity) throws InterruptedException {
                            if (commonDataEntity.getCode().equals("100")){
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        hideDialog();
                                        ToastUtil.showToast(getContext(),"上传成功！");
                                        setButtonllEnable(true);
                                    }
                                });
                            }
                        }

                        @Override
                        public void onFailure(String msg) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    hideDialog();
                                    ToastUtil.showToast(getContext(),"上传失败！");
                                    setButtonllEnable(true);
                                }
                            });
                        }
                    });
                }
            }
        }
    }
}
