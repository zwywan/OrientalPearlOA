package com.geely.app.geelyapprove.activities.traveloffer.view;

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
import com.geely.app.geelyapprove.activities.enterexpense.Entity.EnterExpenseOrdersEntity;
import com.geely.app.geelyapprove.activities.login.entity.LoginEntity;
import com.geely.app.geelyapprove.activities.postbusinesstrip.bean.BusinessTripOrdersEntity;
import com.geely.app.geelyapprove.activities.traveloffer.bean.TravelOfferDetailBean;
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
import com.geely.app.geelyapprove.common.utils.DescripUtil;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Created by guiluXu on 2016/10/12.
 */
public class TravelApplyFragment extends CommonFragment implements FileChooserLayout.DataChangeListener{

    private TravelOfferDetailBean bean;
    private String uuid;
    private HandInputGroup.Holder mholder;
    private List<DataListEntity> draftAttachmentType,companyEntities,costcenterList,transportation;
    private String companycode,costcentercode,payeecode;
    private Map<String, HashSet<Uri>> paths;
    private LoginEntity loginEntity = GeelyApp.getLoginEntity();
    private String mEid;
    private String mNameCN;
    private String mGrade;
    private String mPositionNameCN;
    private String mUserId;
    private ArrayMap<String, Object> bTripUnion = new ArrayMap<>();
    private ArrayMap<String,Object> enterExpUnion = new ArrayMap<>();
    private boolean mIsDomestic;
    private String provinceid;
    private String cityid;
    private String str;
    private List<AttachmentListEntity> attachList;
    private DecimalFormat df;

    public TravelApplyFragment() {
    }

    public static TravelApplyFragment newInstance() {
        TravelApplyFragment fragment = new TravelApplyFragment();
        return fragment;
    }

    public static TravelApplyFragment newInstance(Bundle bundle) {
        TravelApplyFragment fragment = new TravelApplyFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        df = new DecimalFormat("######0.00");
        loadData();
    }

    private void loadData() {
        if (getArguments() != null) {
            Map<String, Object> params = CommonValues.getCommonParams(getActivity());
            params.put("userId",getArguments().getString("userId"));
            params.put("barCode", getArguments().getString("barCode"));
            params.put("workflowType", getArguments().getString("workflowType"));
            HttpManager.getInstance().requestResultForm(CommonValues.REQ_TRAVEL_EXPENSE_DETAIL, params, TravelOfferDetailBean.class, new HttpManager.ResultCallback<TravelOfferDetailBean>() {
                @Override
                public void onSuccess(String json, final TravelOfferDetailBean entity) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(entity != null && entity.getCode().equals("100")){
                                bean = entity;
                                if (getArguments().getBoolean("Remak")){
                                    settoolbar();
                                    setButtonsTitles(new String[]{"重新提交"});
                                }
                                setPb(false);
                                setButtonllEnable(true);
                                setGroup(getGroupList());
                                notifyDataSetChanged();
                            }else {
                                Toast.makeText(getActivity(), entity.getMsg(), Toast.LENGTH_SHORT).show();
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
        mEid = GeelyApp.getLoginEntity().getRetData().getUserInfo().getEid();
        mNameCN = GeelyApp.getLoginEntity().getRetData().getUserInfo().getNameCN();
        mGrade = GeelyApp.getLoginEntity().getRetData().getUserInfo().getGrade().trim();
        mPositionNameCN = GeelyApp.getLoginEntity().getRetData().getUserInfo().getPositionNameCN();
        mUserId = GeelyApp.getLoginEntity().getRetData().getUserInfo().getUserId();
        List<LoginEntity.RetDataBean.UserAccountBean> userAccount = loginEntity.getRetData().getUserAccount();
        List<Group> groups = new ArrayList<>();
        if (bean == null){
            uuid = UUID.randomUUID().toString();
            if (userAccount.size() == 0){
                List<HandInputGroup.Holder> subMain = new ArrayList<>();
                subMain.add(new HandInputGroup.Holder(this.getString(R.string.Company_Name), true, false, "/" + this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.SELECT));
                subMain.add(new HandInputGroup.Holder(this.getString(R.string.Cost_Center), true, false, "/" + this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.SELECT));
                subMain.add(new HandInputGroup.Holder(this.getString(R.string.Employee_ID), false, false, mEid, HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
                subMain.add(new HandInputGroup.Holder(this.getString(R.string.Reimbursement_Person), false, false, mNameCN, HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
                subMain.add(new HandInputGroup.Holder(this.getString(R.string.Grade), false, false, mGrade, HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
                subMain.add(new HandInputGroup.Holder(this.getString(R.string.Position), false, false, mPositionNameCN, HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
                subMain.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Employee_ID), true, false, "/" + this.getString(R.string.Please_Fill), HandInputGroup.VALUE_TYPE.TEXTFILED));
                subMain.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Name), false, false,"", HandInputGroup.VALUE_TYPE.TEXTFILED));
                subMain.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Bank), true, false, "", HandInputGroup.VALUE_TYPE.SELECT).setEditable(false));
                subMain.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Bank_Account), true, false, "", HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
                subMain.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Bank_Adress), true, false, "", HandInputGroup.VALUE_TYPE.SELECT).setEditable(false));
                subMain.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Branch_Bank), true, true, "", HandInputGroup.VALUE_TYPE.SELECT).setEditable(false));
                subMain.add(new HandInputGroup.Holder(this.getString(R.string.IsDomesti),true , false, this.getString(R.string.No), HandInputGroup.VALUE_TYPE.BUTTONS));
                subMain.add(new HandInputGroup.Holder(this.getString(R.string.Has_Accommodation), true, false, this.getString(R.string.No), HandInputGroup.VALUE_TYPE.BUTTONS));
                subMain.add(new HandInputGroup.Holder(this.getString(R.string.Has_Borrow), true, false, this.getString(R.string.No), HandInputGroup.VALUE_TYPE.BUTTONS));
                subMain.add(new HandInputGroup.Holder(this.getString(R.string.Relate_Hospitalit_Order), false, false, "", HandInputGroup.VALUE_TYPE.SUB_LIST).setValue(enterExpUnion.keySet()));
                subMain.add(new HandInputGroup.Holder(this.getString(R.string.Reason), false, false, "/" + this.getString(R.string.Please_Fill), HandInputGroup.VALUE_TYPE.TEXTFILED));
                groups.add(new Group(this.getString(R.string.Basic_Information), null, true, null, subMain));//oa
                List<HandInputGroup.Holder> subDetail = new ArrayList<>();
                subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Leave_Date), true, false, "/填写出发时间", HandInputGroup.VALUE_TYPE.DATE));
                subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Return_Date), true, false, "/填写到达时间", HandInputGroup.VALUE_TYPE.DATE));
                subDetail.add(new HandInputGroup.Holder(this.getString(R.string.From), true, false, "/" + this.getString(R.string.Please_Fill), HandInputGroup.VALUE_TYPE.TEXTFILED));
                subDetail.add(new HandInputGroup.Holder(this.getString(R.string.To), true, false, "/" + this.getString(R.string.Please_Fill), HandInputGroup.VALUE_TYPE.TEXTFILED));
                subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Transport), true, false, "/" + this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.SELECT));
                subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Air_Ticket), false, false, "/" + this.getString(R.string.Please_Fill), HandInputGroup.VALUE_TYPE.DOUBLE));
                subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Transport_Cost), false, false, "/" + this.getString(R.string.Please_Fill), HandInputGroup.VALUE_TYPE.DOUBLE));
                subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Hotel_Cost), false, false, "/" + this.getString(R.string.Please_Fill), HandInputGroup.VALUE_TYPE.DOUBLE));
                subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Guests_Cost), false, false, "/" + this.getString(R.string.Please_Fill), HandInputGroup.VALUE_TYPE.DOUBLE));
                subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Other_Cost), false, false, "/" + this.getString(R.string.Please_Fill), HandInputGroup.VALUE_TYPE.DOUBLE));
                subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Allowance), false, false, "/" + this.getString(R.string.Please_Fill), HandInputGroup.VALUE_TYPE.DOUBLE));
                groups.add(new Group(this.getString(R.string.Details_Information), R.mipmap.add_detail3x,true,null,subDetail).sethasDelete(true));
                List<HandInputGroup.Holder> subDetailTotal = new ArrayList<>();
                groups.add(new Group(this.getString(R.string.Total), null, true, sum + "", subDetailTotal));
                List<HandInputGroup.Holder> subAppend = new ArrayList<>();
                subAppend.add(new HandInputGroup.Holder(this.getString(R.string.Attachment_Type), true, false, "/" + this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.SELECT));
                if (paths == null) {
                    paths = new HashMap<>();
                    paths.put("", new HashSet<Uri>());
                }
                subAppend.add(new HandInputGroup.Holder(this.getString(R.string.Select_Attachments), false, false, "/" + this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.FILES_UPLOAD).setValue(paths.get("")));
                groups.add(new Group(this.getString(R.string.Attachment_Info), null, false, null, subAppend));
            }else {
                LoginEntity.RetDataBean.UserAccountBean userAccountBean = userAccount.get(0);
                payeecode = userAccountBean.getPayeeBank();
                provinceid = userAccountBean.getPayeeBankProvince();
                cityid = userAccountBean.getPayeeBankCity();
                List<HandInputGroup.Holder> subMain = new ArrayList<>();
                subMain.add(new HandInputGroup.Holder(this.getString(R.string.Company_Name), true, false, "/" + this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.SELECT));
                subMain.add(new HandInputGroup.Holder(this.getString(R.string.Cost_Center), true, false, "/" + this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.SELECT));
                subMain.add(new HandInputGroup.Holder(this.getString(R.string.Employee_ID), false, false, mEid, HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
                subMain.add(new HandInputGroup.Holder(this.getString(R.string.Reimbursement_Person), false, false, mNameCN, HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
                subMain.add(new HandInputGroup.Holder(this.getString(R.string.Grade), false, false, mGrade, HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
                subMain.add(new HandInputGroup.Holder(this.getString(R.string.Position), false, false, mPositionNameCN, HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
                subMain.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Employee_ID), true, false, loginEntity.getRetData().getUserInfo().getEid(), HandInputGroup.VALUE_TYPE.TEXTFILED));
                subMain.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Name), false, false,userAccountBean.getAccountName(), HandInputGroup.VALUE_TYPE.TEXTFILED));
                subMain.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Bank), true, false, userAccountBean.getPayeeBankName(), HandInputGroup.VALUE_TYPE.SELECT).setEditable(false));
                subMain.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Bank_Account), true, false, userAccountBean.getAccountNumber(), HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
                subMain.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Bank_Adress), true, false, userAccountBean.getProvinceName() + " " + userAccountBean.getCityName(), HandInputGroup.VALUE_TYPE.SELECT).setEditable(false));
                subMain.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Branch_Bank), true, true, userAccountBean.getPayeeBranchBank(), HandInputGroup.VALUE_TYPE.SELECT).setEditable(false));
                subMain.add(new HandInputGroup.Holder(this.getString(R.string.IsDomesti),true , false, this.getString(R.string.No), HandInputGroup.VALUE_TYPE.BUTTONS));
                subMain.add(new HandInputGroup.Holder(this.getString(R.string.Has_Accommodation), true, false, this.getString(R.string.No), HandInputGroup.VALUE_TYPE.BUTTONS));
                subMain.add(new HandInputGroup.Holder(this.getString(R.string.Has_Borrow), true, false, this.getString(R.string.No), HandInputGroup.VALUE_TYPE.BUTTONS));
                subMain.add(new HandInputGroup.Holder(this.getString(R.string.Relate_Hospitalit_Order), false, false, "", HandInputGroup.VALUE_TYPE.SUB_LIST).setValue(enterExpUnion.keySet()));
                subMain.add(new HandInputGroup.Holder(this.getString(R.string.Reason), false, false, "/" + this.getString(R.string.Please_Fill), HandInputGroup.VALUE_TYPE.TEXTFILED));
                groups.add(new Group(this.getString(R.string.Basic_Information), null, true, null, subMain));//oa
                List<HandInputGroup.Holder> subDetail = new ArrayList<>();
                subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Leave_Date), true, false, "/填写出发时间", HandInputGroup.VALUE_TYPE.DATE));
                subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Return_Date), true, false, "/填写到达时间", HandInputGroup.VALUE_TYPE.DATE));
                subDetail.add(new HandInputGroup.Holder(this.getString(R.string.From), true, false, "/" + this.getString(R.string.Please_Fill), HandInputGroup.VALUE_TYPE.TEXTFILED));
                subDetail.add(new HandInputGroup.Holder(this.getString(R.string.To), true, false, "/" + this.getString(R.string.Please_Fill), HandInputGroup.VALUE_TYPE.TEXTFILED));
                subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Transport), true, false, "/" + this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.SELECT));
                subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Air_Ticket), false, false, "/" + this.getString(R.string.Please_Fill), HandInputGroup.VALUE_TYPE.DOUBLE));
                subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Transport_Cost), false, false, "/" + this.getString(R.string.Please_Fill), HandInputGroup.VALUE_TYPE.DOUBLE));
                subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Hotel_Cost), false, false, "/" + this.getString(R.string.Please_Fill), HandInputGroup.VALUE_TYPE.DOUBLE));
                subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Guests_Cost), false, false, "/" + this.getString(R.string.Please_Fill), HandInputGroup.VALUE_TYPE.DOUBLE));
                subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Other_Cost), false, false, "/" + this.getString(R.string.Please_Fill), HandInputGroup.VALUE_TYPE.DOUBLE));
                subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Allowance), false, false, "/" + this.getString(R.string.Please_Fill), HandInputGroup.VALUE_TYPE.DOUBLE));
                groups.add(new Group(this.getString(R.string.Details_Information), R.mipmap.add_detail3x,true,null,subDetail).sethasDelete(true));
                List<HandInputGroup.Holder> subDetailTotal = new ArrayList<>();
                groups.add(new Group(this.getString(R.string.Total), null, true, sum + "", subDetailTotal));
                List<HandInputGroup.Holder> subAppend = new ArrayList<>();
                subAppend.add(new HandInputGroup.Holder(this.getString(R.string.Attachment_Type), true, false, "/" + this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.SELECT));
                if (paths == null) {
                    paths = new HashMap<>();
                    paths.put("", new HashSet<Uri>());
                }
                subAppend.add(new HandInputGroup.Holder(this.getString(R.string.Select_Attachments), false, false, "/" + this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.FILES_UPLOAD).setValue(paths.get("")));
                groups.add(new Group(this.getString(R.string.Attachment_Info), null, false, null, subAppend));
            }
        }else {
            uuid = bean.getRetData().getDetailData().getIdentifier();
            companycode = bean.getRetData().getDetailData().getCompany();
            loadById(companycode);
            costcentercode = bean.getRetData().getDetailData().getCostCenter();
            payeecode = bean.getRetData().getDetailData().getPayeeBank();
            cityid = bean.getRetData().getDetailData().getPayeeBankCity();
            provinceid = bean.getRetData().getDetailData().getPayeeBankProvince();
            TravelOfferDetailBean.RetDataBean.DetailDataBean detailData = bean.getRetData().getDetailData();
            List<TravelOfferDetailBean.RetDataBean.DetailDataBean.TravelExpenseDetailBean> expenseDetail = detailData.getTravelExpenseDetail();
            List<HandInputGroup.Holder> subMain = new ArrayList<>();
            mIsDomestic = detailData.isIsDomestic();
            subMain.add(new HandInputGroup.Holder(this.getString(R.string.Employee_ID), false, false, mEid, HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
            subMain.add(new HandInputGroup.Holder(this.getString(R.string.Reimbursement_Person), false, false, mNameCN, HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
            subMain.add(new HandInputGroup.Holder(this.getString(R.string.Grade), false, false, mGrade, HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
            subMain.add(new HandInputGroup.Holder(this.getString(R.string.Position), false, false, mPositionNameCN, HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
            subMain.add(new HandInputGroup.Holder(this.getString(R.string.Company_Name), true, false, detailData.getCompanyName(), HandInputGroup.VALUE_TYPE.SELECT));
            subMain.add(new HandInputGroup.Holder(this.getString(R.string.Cost_Center), true, false, detailData.getCostCenterName(), HandInputGroup.VALUE_TYPE.SELECT));
            subMain.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Employee_ID), true, false, detailData.getPayeeEID(), HandInputGroup.VALUE_TYPE.TEXTFILED));
            subMain.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Name), true, false,detailData.getPayeeName(), HandInputGroup.VALUE_TYPE.TEXTFILED));
            subMain.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Bank), true, false, detailData.getPayeeBankName(), HandInputGroup.VALUE_TYPE.SELECT).setEditable(false));
            subMain.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Bank_Account), true, false, detailData.getPayeeBankAccount(), HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
            subMain.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Bank_Adress), true, false, detailData.getPayeeBankProvinceName() + " " + detailData.getPayeeBankCityName(), HandInputGroup.VALUE_TYPE.SELECT).setEditable(false));
            subMain.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Branch_Bank), true, true, detailData.getPayeeBranchBank(), HandInputGroup.VALUE_TYPE.SELECT).setEditable(false));
            subMain.add(new HandInputGroup.Holder(this.getString(R.string.IsDomesti), false, false, mIsDomestic ? this.getString(R.string.Yes):this.getString(R.string.No), HandInputGroup.VALUE_TYPE.BUTTONS));
            subMain.add(new HandInputGroup.Holder(this.getString(R.string.Has_Accommodation), false, false, detailData.isHasAccommodation() ? this.getString(R.string.Yes):this.getString(R.string.No), HandInputGroup.VALUE_TYPE.BUTTONS));
            subMain.add(new HandInputGroup.Holder(this.getString(R.string.Has_Borrow), false, false, detailData.isHasBorrow() ? this.getString(R.string.Yes):this.getString(R.string.No), HandInputGroup.VALUE_TYPE.BUTTONS));
            if (mIsDomestic) {
                String relatedBusinessTripOrders = detailData.getRelatedBusinessTripOrders();
                if (!relatedBusinessTripOrders.isEmpty()){
                    String[] split = relatedBusinessTripOrders.split(";");
                    if (split.length > 1){
                        for (int i = 0; i < split.length; i++) {
                            BusinessTripOrdersEntity.RetDataBean.BusinessTripOrdersBean businessTripOrdersBean = new BusinessTripOrdersEntity.RetDataBean.BusinessTripOrdersBean();
                            businessTripOrdersBean.setBarCode(split[i]);
                            List<BusinessTripOrdersEntity.RetDataBean.BusinessTripOrdersBean.BusinessTripDetailBean> BusinessTripDetaillist = new ArrayList<>();
                            for (TravelOfferDetailBean.RetDataBean.DetailDataBean.TravelExpenseDetailBean travelExpenseDetailBean : expenseDetail) {
                                if (travelExpenseDetailBean.getBusinessTripBarCode().equals(split[i])){
                                    BusinessTripOrdersEntity.RetDataBean.BusinessTripOrdersBean.BusinessTripDetailBean businessTripDetailBean = new BusinessTripOrdersEntity.RetDataBean.BusinessTripOrdersBean.BusinessTripDetailBean();
                                    businessTripDetailBean.setLeaveDate(travelExpenseDetailBean.getPlanLeaveDate());
                                    businessTripDetailBean.setReturnDate(travelExpenseDetailBean.getPlanReturnDate());
                                    businessTripDetailBean.setFrom(travelExpenseDetailBean.getFrom());
                                    businessTripDetailBean.setTo(travelExpenseDetailBean.getTo());
                                    businessTripDetailBean.setTicketCost(travelExpenseDetailBean.getTicketCost());
                                    businessTripDetailBean.setTransportCost(travelExpenseDetailBean.getTransportCost());
                                    businessTripDetailBean.setTransportName(travelExpenseDetailBean.getTransportName());
                                    businessTripDetailBean.setHotelCost(travelExpenseDetailBean.getHotelCost());
                                    businessTripDetailBean.setGuestsCost(travelExpenseDetailBean.getGuestsCost());
                                    businessTripDetailBean.setOtherCost(travelExpenseDetailBean.getOtherCost());
                                    businessTripDetailBean.setAllowance(travelExpenseDetailBean.getAllowance());
                                    BusinessTripDetaillist.add(businessTripDetailBean);
                                }
                            }
                            bTripUnion.put(split[i],businessTripOrdersBean);
                        }

                    }else if (split.length == 1){
                        BusinessTripOrdersEntity.RetDataBean.BusinessTripOrdersBean businessTripOrdersBean = new BusinessTripOrdersEntity.RetDataBean.BusinessTripOrdersBean();
                        ArrayList<BusinessTripOrdersEntity.RetDataBean.BusinessTripOrdersBean.BusinessTripDetailBean> businessTripDetail = new ArrayList<>();
                        businessTripOrdersBean.setBarCode(split[0]);
                        for (TravelOfferDetailBean.RetDataBean.DetailDataBean.TravelExpenseDetailBean travelExpenseDetailBean : expenseDetail) {
                            if (travelExpenseDetailBean.getBusinessTripBarCode().equals(split[0])){
                                BusinessTripOrdersEntity.RetDataBean.BusinessTripOrdersBean.BusinessTripDetailBean businessTripDetailBean = new BusinessTripOrdersEntity.RetDataBean.BusinessTripOrdersBean.BusinessTripDetailBean();
                                businessTripDetailBean.setLeaveDate(travelExpenseDetailBean.getPlanLeaveDate());
                                businessTripDetailBean.setReturnDate(travelExpenseDetailBean.getPlanReturnDate());
                                businessTripDetailBean.setFrom(travelExpenseDetailBean.getFrom());
                                businessTripDetailBean.setTo(travelExpenseDetailBean.getTo());
                                businessTripDetailBean.setTicketCost(travelExpenseDetailBean.getTicketCost());
                                businessTripDetailBean.setTransportCost(travelExpenseDetailBean.getTransportCost());
                                businessTripDetailBean.setTransportName(travelExpenseDetailBean.getTransportName());
                                businessTripDetailBean.setHotelCost(travelExpenseDetailBean.getHotelCost());
                                businessTripDetailBean.setGuestsCost(travelExpenseDetailBean.getGuestsCost());
                                businessTripDetailBean.setOtherCost(travelExpenseDetailBean.getOtherCost());
                                businessTripDetailBean.setAllowance(travelExpenseDetailBean.getAllowance());
                                businessTripDetail.add(businessTripDetailBean);
                                businessTripOrdersBean.setBusinessTripDetail(businessTripDetail);
                            }
                        }
                        bTripUnion.put(split[0],businessTripOrdersBean);
                    }
                }
                subMain.add(new HandInputGroup.Holder(this.getString(R.string.Related_Business_Trip_Orders), false, false, "", HandInputGroup.VALUE_TYPE.SUB_LIST).setValue(bTripUnion.keySet()));

            }
            String relateHospitalitOrder = detailData.getRelateHospitalitOrder();
            if (!relateHospitalitOrder.isEmpty()){
                String[] split = relateHospitalitOrder.split(";");
                if (split.length > 1){
                    for (int i = 0; i < split.length; i++) {
                        for (TravelOfferDetailBean.RetDataBean.DetailDataBean.TravelExpenseDetailBean travelExpenseDetailBean : expenseDetail) {
                            if (travelExpenseDetailBean.getEntertainmentBarCode().equals(split[i])){
                                EnterExpenseOrdersEntity.RetDataBean.EntertainmentExpenseOrdersBean  ent = new EnterExpenseOrdersEntity.RetDataBean.EntertainmentExpenseOrdersBean();
                                ent.setCreateTime(travelExpenseDetailBean.getActualLeaveDate());
                                ent.setBarCode(travelExpenseDetailBean.getEntertainmentBarCode());
                                ent.setAmount(travelExpenseDetailBean.getGuestsCost());
                                enterExpUnion.put(split[i],ent);
                            }
                        }

                    }
                }else if (split.length == 1){
                    for (TravelOfferDetailBean.RetDataBean.DetailDataBean.TravelExpenseDetailBean travelExpenseDetailBean : expenseDetail) {
                        if (travelExpenseDetailBean.getEntertainmentBarCode().equals(split[0])){
                            EnterExpenseOrdersEntity.RetDataBean.EntertainmentExpenseOrdersBean  ent = new EnterExpenseOrdersEntity.RetDataBean.EntertainmentExpenseOrdersBean();
                            ent.setCreateTime(travelExpenseDetailBean.getActualLeaveDate());
                            ent.setBarCode(travelExpenseDetailBean.getEntertainmentBarCode());
                            ent.setAmount(travelExpenseDetailBean.getGuestsCost());
                            enterExpUnion.put(split[0],ent);
                        }
                    }
                }
            }
            subMain.add(new HandInputGroup.Holder(this.getString(R.string.Relate_Hospitalit_Order), false,false, "", HandInputGroup.VALUE_TYPE.SUB_LIST).setValue(enterExpUnion.keySet()).setColor(Color.BLUE));
            subMain.add(new HandInputGroup.Holder(this.getString(R.string.Reason), false, false, detailData.getReason(), HandInputGroup.VALUE_TYPE.TEXTFILED));
            groups.add(new Group(this.getString(R.string.Basic_Information), null, true, null, subMain));
            for(int i = 0; i < expenseDetail.size(); i++) {
                List<HandInputGroup.Holder> subDetail = new ArrayList<>();
                subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Leave_Date), true, false, expenseDetail.get(i).getActualLeaveDate().isEmpty() ? "/填写出发时间" : expenseDetail.get(i).getActualLeaveDate().split(" ")[0], HandInputGroup.VALUE_TYPE.DATE));
                subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Return_Date), true, false, expenseDetail.get(i).getActualReturnDate().isEmpty() ? "/填写到达时间" : expenseDetail.get(i).getActualReturnDate().split(" ")[0], HandInputGroup.VALUE_TYPE.DATE));
                subDetail.add(new HandInputGroup.Holder(this.getString(R.string.From), true, false, expenseDetail.get(i).getFrom().isEmpty() ? "/" + this.getString(R.string.Please_Fill) : expenseDetail.get(i).getFrom(), HandInputGroup.VALUE_TYPE.TEXTFILED));
                subDetail.add(new HandInputGroup.Holder(this.getString(R.string.To), true, false, expenseDetail.get(i).getTo().isEmpty() ? "/" + this.getString(R.string.Please_Fill) : expenseDetail.get(i).getTo(), HandInputGroup.VALUE_TYPE.TEXTFILED));
                subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Transport), true, false, expenseDetail.get(i).getTransportName(), HandInputGroup.VALUE_TYPE.SELECT));
                subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Air_Ticket), false, false, TextUtils.isEmpty(expenseDetail.get(i).getTicketCost()) ? "/" + this.getString(R.string.Please_Fill) : expenseDetail.get(i).getTicketCost(), HandInputGroup.VALUE_TYPE.DOUBLE));
                subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Transport_Cost), false, false, TextUtils.isEmpty(expenseDetail.get(i).getTransportCost()) ? "/" + this.getString(R.string.Please_Fill) : expenseDetail.get(i).getTransportCost(), HandInputGroup.VALUE_TYPE.DOUBLE));
                subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Hotel_Cost), false, false, TextUtils.isEmpty(expenseDetail.get(i).getHotelCost()) ? "/" + this.getString(R.string.Please_Fill) : expenseDetail.get(i).getHotelCost(), HandInputGroup.VALUE_TYPE.DOUBLE));
                subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Guests_Cost), false, false, TextUtils.isEmpty(expenseDetail.get(i).getGuestsCost()) ? "/" + this.getString(R.string.Please_Fill) : expenseDetail.get(i).getGuestsCost(), HandInputGroup.VALUE_TYPE.DOUBLE));
                subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Other_Cost), false, false, TextUtils.isEmpty(expenseDetail.get(i).getOtherCost()) ? "/" + this.getString(R.string.Please_Fill) : expenseDetail.get(i).getOtherCost(), HandInputGroup.VALUE_TYPE.DOUBLE));
                subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Allowance), false, false, TextUtils.isEmpty(expenseDetail.get(i).getAllowance()) ? "/" + this.getString(R.string.Please_Fill) : expenseDetail.get(i).getAllowance(), HandInputGroup.VALUE_TYPE.DOUBLE));
                double temp = Double.parseDouble(expenseDetail.get(i).getTicketCost().isEmpty()?"0":expenseDetail.get(i).getTicketCost());
                double temp1 = Double.parseDouble(expenseDetail.get(i).getTransportCost().isEmpty()?"0":expenseDetail.get(i).getTransportCost());
                double temp2 = Double.parseDouble(expenseDetail.get(i).getHotelCost().isEmpty()?"0":expenseDetail.get(i).getHotelCost());
                double temp3 = Double.parseDouble(expenseDetail.get(i).getGuestsCost().isEmpty()?"0":expenseDetail.get(i).getGuestsCost());
                double temp4 = Double.parseDouble(expenseDetail.get(i).getOtherCost().isEmpty()?"0":expenseDetail.get(i).getOtherCost());
                double temp5 = Double.parseDouble(expenseDetail.get(i).getAllowance().isEmpty()?"0":expenseDetail.get(i).getAllowance());
                double tempTotal = temp + temp1 + temp2 + temp3 + temp4 + temp5;
                sum += tempTotal;
                String oldbarcode = null;
                if (!expenseDetail.get(i).getBusinessTripBarCode().isEmpty()){
                    oldbarcode = expenseDetail.get(i).getBusinessTripBarCode();
                }else if (!expenseDetail.get(i).getEntertainmentBarCode().isEmpty()){
                    oldbarcode = expenseDetail.get(i).getEntertainmentBarCode();
                }
                if (i==expenseDetail.size()-1){
                    groups.add(new Group(this.getString(R.string.Details_Information), R.mipmap.add_detail3x, true, null, subDetail).setoldBarcode(oldbarcode).sethasDelete(oldbarcode == null));
                }else {
                    groups.add(new Group(this.getString(R.string.Details_Information), null, true, null, subDetail).setoldBarcode(oldbarcode).sethasDelete(oldbarcode == null));
                }
            }
            List<HandInputGroup.Holder> subDetailTotal = new ArrayList<>();
            groups.add(new Group(this.getString(R.string.Total), null, true, sum + "", subDetailTotal));
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
    public void setToolbar(HandToolbar toolbar) {
        toolbar.setTitle("差旅报销");
        toolbar.setDisplayHomeAsUpEnabled(true, getActivity());
        loadDraftListData();
    }

    @Override
    public String[] getBottomButtonsTitles() {
        return new String[]{"提交", "保存"};
    }

    @Override
    public void onClickItemContentSetter(final HandInputGroup.Holder holder) {
        mholder = holder;
        HandInputGroup.VALUE_TYPE valueType = mholder.getType();
        String key = mholder.getKey();
        if (valueType == HandInputGroup.VALUE_TYPE.DATE) {
            showDateTimePicker(holder,false);
        }
        if (TextUtils.equals(key,this.getString(R.string.Has_Accommodation))||TextUtils.equals(key,this.getString(R.string.Has_Borrow))) {
            checkedButton(holder);
        }else if (TextUtils.equals(key,this.getString(R.string.IsDomesti))) {
            checkedButton(holder, new OnSelectedResultCallback() {
                @Override
                public void onSelected(Group ownGroup, HandInputGroup.Holder holder, int mainIndex, int itemIndex) {
                    if (holder.getRealValue().equals(TravelApplyFragment.this.getString(R.string.Yes))) {
                        mIsDomestic = true;
                        insertItems(mIsDomestic);
                    } else if (holder.getRealValue().equals(TravelApplyFragment.this.getString(R.string.No))){
                        mIsDomestic = false;
                        insertItems(mIsDomestic);
                    }
                }
            });
        }else if (valueType == HandInputGroup.VALUE_TYPE.SELECT) {
            if (key.equals(this.getString(R.string.Company_Name))){
                showSelector(holder, DataUtil.getDescr(companyEntities), new OnSelectedResultCallback() {
                    @Override
                    public void onSelected(Group ownGroup, HandInputGroup.Holder holder, int mainIndex, int itemIndex) {
                        companycode = DataUtil.getDicodeByDescr(companyEntities,holder.getDispayValue());
                        if (!companycode.equals("")) {
                            loadById(companycode);
                        }
                    }
                });
            }else if(key.equals(this.getString(R.string.Cost_Center))){
                if(companycode == null){
                    ToastUtil.showToast(getContext(),this.getString(R.string.Please_Select) + "公司");
                }else{
                    if(costcenterList == null || costcenterList.size() == 0){
                        ToastUtil.showToast(getContext(),"该公司暂无数据");
                    }else {
                        showSelector(holder, DataUtil.getDescr(costcenterList), new OnSelectedResultCallback() {
                            @Override
                            public void onSelected(Group ownGroup, HandInputGroup.Holder holder, int mainIndex, int itemIndex) {
                                costcentercode = DataUtil.getDicodeByDescr(costcenterList,holder.getRealValue());
                            }
                        });
                    }
                }
            }else if(TextUtils.equals(key,this.getString(R.string.Transport))){
                showSelector(holder,DataUtil.getDescr(transportation));
            }else if(TextUtils.equals(key,this.getString(R.string.Attachment_Type))){
                showSelector(holder,DataUtil.getDescr(draftAttachmentType));//
            }else if (TextUtils.equals(key,this.getString(R.string.Select_Attachments))){
                AttachmentListEntity entity = (AttachmentListEntity) holder.getValue();
                lookAttachmentData(entity);
            }
        }
    }

    private void insertItems(boolean mIsDomestic) {
        List<HandInputGroup.Holder> holders = getGroup().get(0).getHolders();
        if (mIsDomestic) {
            if (holders.size()==17){
                holders.add(15,new HandInputGroup.Holder(this.getString(R.string.Related_Business_Trip_Orders), false, false, "添加+", HandInputGroup.VALUE_TYPE.SUB_LIST).setColor(Color.BLUE).setValue(bTripUnion.keySet()));
            }
        } else {
            if (holders.size() == 18){
                if (holders.get(15).getValue() != null){
                    Set<String> strings = bTripUnion.keySet();
                    int size = strings.size();
                    for (int i = 0; i < size; i++) {
                        List<Group> group = getGroup();
                        for (int j = 0; j < group.size(); j++) {
                            if (group.get(j).getOldBarcode() != null && group.get(j).getOldBarcode().equals(bTripUnion.keyAt(i))) {
                                group.remove(j);
                                j--;
                            }

                        }
                    }
                    if (bean != null){

                    }
                }
                bTripUnion.clear();
                holders.remove(15);
                onHolderTextChanged(1,6,getGroup().get(1).getHolders().get(6));
            }

        }
        notifyDataSetChanged();
    }

    private void addBTripUnionDetail(boolean zorg,Object obj){
        if (zorg) {
            EnterExpenseOrdersEntity.RetDataBean.EntertainmentExpenseOrdersBean beanServe = (EnterExpenseOrdersEntity.RetDataBean.EntertainmentExpenseOrdersBean) obj;
            if (beanServe != null) {
                List<HandInputGroup.Holder> subDetail = new ArrayList<>();
                subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Leave_Date), true, false, "/" + this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.DATE));
                subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Return_Date), true, false, "/" + this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.DATE));
                subDetail.add(new HandInputGroup.Holder(this.getString(R.string.From), true, false, "/" + this.getString(R.string.Please_Fill), HandInputGroup.VALUE_TYPE.TEXTFILED));
                subDetail.add(new HandInputGroup.Holder(this.getString(R.string.To), true, false, "/" + this.getString(R.string.Please_Fill), HandInputGroup.VALUE_TYPE.TEXTFILED));
                subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Transport), true, false, "/" + this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.SELECT));
                subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Air_Ticket), false, false, "", HandInputGroup.VALUE_TYPE.DOUBLE));
                subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Transport_Cost), false, false, "", HandInputGroup.VALUE_TYPE.DOUBLE));
                subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Hotel_Cost), false, false, "", HandInputGroup.VALUE_TYPE.DOUBLE));
                subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Guests_Cost), false, false, beanServe.getAmount().isEmpty()?"0":beanServe.getAmount(), HandInputGroup.VALUE_TYPE.DOUBLE));
                subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Other_Cost), false, false, "", HandInputGroup.VALUE_TYPE.DOUBLE));
                subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Allowance), false, false, "", HandInputGroup.VALUE_TYPE.DOUBLE));
                Group group = new Group(this.getString(R.string.Details_Information), null, true, null, subDetail);
                if(getGroup().get(0).getDrawableRes() != null){
                    getGroup().get(0).setDrawableRes(null);
                    group.setDrawableRes(R.mipmap.add_detail3x);
                }
                group.sethasDelete(false);
                group.setoldBarcode(beanServe.getBarCode());
                getGroup().add(1, group);
            }
        } else {
            BusinessTripOrdersEntity.RetDataBean.BusinessTripOrdersBean orderBean = (BusinessTripOrdersEntity.RetDataBean.BusinessTripOrdersBean) obj;
            List<BusinessTripOrdersEntity.RetDataBean.BusinessTripOrdersBean.BusinessTripDetailBean> beans = orderBean.getBusinessTripDetail();
            if (beans != null) {
                for (int i = 0; i < beans.size(); i++) {
                    List<HandInputGroup.Holder> subDetail = new ArrayList<>();
                    subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Leave_Date), true, false, beans.get(i).getLeaveDate().split(" ")[0], HandInputGroup.VALUE_TYPE.DATE));
                    subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Return_Date), true, false, beans.get(i).getReturnDate().split(" ")[0], HandInputGroup.VALUE_TYPE.DATE));
                    subDetail.add(new HandInputGroup.Holder(this.getString(R.string.From), true, false, beans.get(i).getFrom(), HandInputGroup.VALUE_TYPE.TEXTFILED));
                    subDetail.add(new HandInputGroup.Holder(this.getString(R.string.To), true, false, beans.get(i).getTo(), HandInputGroup.VALUE_TYPE.TEXTFILED));
                    subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Transport), true, false, beans.get(i).getTransportName(), HandInputGroup.VALUE_TYPE.SELECT));
                    subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Air_Ticket), false, false, TextUtils.isEmpty(beans.get(i).getTicketCost()) ? "0" : beans.get(i).getTicketCost(), HandInputGroup.VALUE_TYPE.DOUBLE));
                    subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Transport_Cost), false, false, TextUtils.isEmpty(beans.get(i).getTransportCost()) ? "0" : beans.get(i).getTransportCost(), HandInputGroup.VALUE_TYPE.DOUBLE));
                    subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Hotel_Cost), false, false, TextUtils.isEmpty(beans.get(i).getHotelCost()) ? "0" : beans.get(i).getHotelCost(), HandInputGroup.VALUE_TYPE.DOUBLE));
                    subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Guests_Cost), false, false, TextUtils.isEmpty(beans.get(i).getGuestsCost()) ? "0" : beans.get(i).getGuestsCost(), HandInputGroup.VALUE_TYPE.DOUBLE));
                    subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Other_Cost), false, false, TextUtils.isEmpty(beans.get(i).getOtherCost()) ? "0" : beans.get(i).getOtherCost(), HandInputGroup.VALUE_TYPE.DOUBLE));
                    subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Allowance), false, false, TextUtils.isEmpty(beans.get(i).getAllowance()) ? "0" : beans.get(i).getAllowance(), HandInputGroup.VALUE_TYPE.DOUBLE));
                    Group group = new Group(this.getString(R.string.Details_Information), null, true, null, subDetail);
                    if (getGroup().get(0).getDrawableRes() != null){
                        getGroup().get(0).setDrawableRes(null);
                        if (i == 0){
                            group.setDrawableRes(R.mipmap.add_detail3x);
                        }
                    }
                    group.sethasDelete(false);
                    group.setoldBarcode(orderBean.getBarCode());
                    getGroup().add(1, group);
                }
            }
        }

    }

    @Override
    public void onBarCodeChanged(int main, int index, HandInputGroup.Holder holder, String oldBarcode, SubListLayout.ActionType type, int id) {
        if(type == SubListLayout.ActionType.ADD){
            if(holder.getKey().equals(this.getString(R.string.Related_Business_Trip_Orders))){
                int size = bTripUnion.size();
                String s = "";
                if (size > 0){
                    s = bTripUnion.keyAt(0);
                    for (int i = 1; i < size; i++) {
                        s +=";" + bTripUnion.keyAt(i);
                    }
                }

                ActionSheetUnionOrdersActivity.openActionSheet(getActivity(), CommonValues.WORKFLOW_BLEAVE, s, bTripUnion, new ActionSheetUnionOrdersActivity.Result() {
                    @Override
                    public void onResult(ArrayMap<String, Object> data) {
                        bTripUnion = data;
                        for(Object o:data.values()){
                            addBTripUnionDetail(false,o);
                        }
                        notifyDataSetChanged();
                    }
                });

            }else if(holder.getKey().equals(this.getString(R.string.Relate_Hospitalit_Order))) {
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
                            addBTripUnionDetail(true,o);
                        }
                        notifyDataSetChanged();
                    }
                });
            }
        }else if(type == SubListLayout.ActionType.DELETE){
            bTripUnion.remove(oldBarcode);
            enterExpUnion.remove(oldBarcode);
            notifyGroupChanged(0,1);
            List<Group> groupList = getGroup();
            for (int i = 0; i < groupList.size(); i++) {
                if (groupList.get(i).getOldBarcode()!=null){
                    if (groupList.get(i).getOldBarcode().equals(oldBarcode)){
                        if (groupList.get(i).getDrawableRes() != null){
                            groupList.get(i-1).setDrawableRes(R.mipmap.add_detail3x);
                        }
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
        intent.putExtra(PageConfig.PAGE_CODE, barcode.startsWith("HER")?PageConfig.PAGE_DISPLAY_EXPENSE_OFFER:PageConfig.PAGE_DISPLAY_BLEAVE);
        Bundle bundle = new Bundle();
        bundle.putString("userId", GeelyApp.getLoginEntity().getUserId());
        bundle.putString("barCode", barcode);
        bundle.putString("workflowType", barcode.startsWith("HER")?"EntertainmentExpenseRequest":"BusinessTripRequest");
        intent.putExtra("data", bundle);
        startActivity(intent);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBottomButtonsClick(final String title, final List<Group> groups) {
        setButtonllEnable(false);
        if (title.equals("保存")){
            ButtonClick(title,groups);
        }else {
            String over = isOver(groups);
            if (over != null){
                ToastUtil.showToast(getContext(),this.getString(R.string.Please_Fill) + over);
                setButtonllEnable(true);
            }else {
                ButtonClick(title,groups);
            }
        }
    }

    private void ButtonClick(String title, List<Group> groups) {
        Map<String, Object> body = CommonValues.getCommonParams(getActivity());
        Map<String, Object> mainData = new HashMap<String, Object>();
        if (bean == null) {
            str = DescripUtil.formatDate(new Date());
            mainData.put("Identifier", uuid);//3
            body.put("SN", "");

        } else {
            str = bean.getRetData().getDetailData().getCreateTime();
            mainData.put("Id",bean.getRetData().getDetailData().getId());
            mainData.put("BarCode", bean.getRetData().getDetailData().getBarCode());//2
            mainData.put("Identifier", uuid);//3
            body.put("SN", getArguments().getString("SN"));
        }
        mainData.put("Company", companycode);//4
        mainData.put("CostCenter", costcentercode);//5
        mainData.put("Applicant", mUserId);//6
        mainData.put("PayeeEID", getDisplayValueByKey(this.getString(R.string.Payee_Employee_ID)).getRealValue());//7
        mainData.put("PayeeName", getDisplayValueByKey(this.getString(R.string.Payee_Name)).getRealValue());//8
        mainData.put("PayeeBank", payeecode);//9
        String payeeBankAccount = getDisplayValueByKey(this.getString(R.string.Payee_Bank_Account)).getRealValue();
        mainData.put("PayeeBankAccount", payeeBankAccount);//10
        mainData.put("PayeeBankProvince", provinceid);//11
        mainData.put("PayeeBankCity", cityid);//12
        mainData.put("PayeeBranchBank", getDisplayValueByKey(this.getString(R.string.Payee_Branch_Bank)).getRealValue());//13
        mainData.put("IsCompliance", false);//14
        mainData.put("HasAccommodation", getDisplayValueByKey(this.getString(R.string.Has_Accommodation)).getRealValue().equals(this.getString(R.string.Yes)));//15
        mainData.put("HasBorrow", getDisplayValueByKey(this.getString(R.string.Has_Borrow)).getRealValue().equals(this.getString(R.string.Yes)));//16
        mainData.put("Reason", getDisplayValueByKey(this.getString(R.string.Reason)).getRealValue());//22
        mainData.put("SubmitBy", mUserId);//30
        mainData.put("UpdateBy",mUserId);
        mainData.put("CreateTime", str);
        mainData.put("UpdateTime", DescripUtil.formatDate(new Date()));
        mainData.put("IsRenderAccountant", false);//35
        mainData.put("Currency", "");//36
        mainData.put("PayeeBankCityName", getDisplayValueByKey(this.getString(R.string.Payee_Bank_Adress)).getRealValue().split(" ")[1]);
        String isDomestic = getDisplayValueByKey(this.getString(R.string.IsDomesti)).getRealValue();
        if (isDomestic.equals(this.getString(R.string.Yes))) {
            mainData.put("IsDomestic", true);
            int size = bTripUnion.size();
            if (size > 0){
                String s = bTripUnion.keyAt(0);
                for (int i = 1; i < size; i++) {
                    s +=";" + bTripUnion.keyAt(i);
                }
                mainData.put("RelatedBusinessTripOrders",s);
            }
        } else {
            mainData.put("IsDomestic", false);
        }
        int size = enterExpUnion.size();
        if (size > 0){
            String ss = enterExpUnion.keyAt(0);
            for (int i = 1; i < size; i++) {
                ss +=";" + enterExpUnion.keyAt(i);
            }
            mainData.put("RelateHospitalitOrder",ss);
        }

        mainData.put("TravelAuditDetail", null);
        mainData.put("PayeeBankProvinceName", getDisplayValueByKey(this.getString(R.string.Payee_Bank_Adress)).getRealValue().split(" ")[0]);
        mainData.put("TravelExpenseDetail", null);
        String topRightTitle = getGroupsByTitle(this.getString(R.string.Total)).get(0).getGroupTopRightTitle();
        String s = str.split(" ")[0];
        if(topRightTitle != null){
            mainData.put("TotalAmount",topRightTitle);
            mainData.put("AuditTotalAmount",topRightTitle);
            mainData.put("Summary", s.split("-")[0] + s.split("-")[1] + s.split("-")[2] + "-" + GeelyApp.getLoginEntity().getRetData().getUserInfo().getDeptNameCN() + GeelyApp.getLoginEntity().getRetData().getUserInfo().getNameCN()
                    + "-" + topRightTitle + "RMB差旅");
        }else{
            mainData.put("TotalAmount",0);
            mainData.put("AuditTotalAmount",0);
            mainData.put("Summary", s.split("-")[0] + s.split("-")[1] + s.split("-")[2] + "-" + GeelyApp.getLoginEntity().getRetData().getUserInfo().getDeptNameCN() + GeelyApp.getLoginEntity().getRetData().getUserInfo().getNameCN()
                    + "-" + 0 + "RMB差旅");
        }
        String md = new Gson().toJson(mainData);
        body.put("mainData", md);
        List<Object> details = new ArrayList<Object>();
        for (int i = 1; i < groups.size() - 2; i++) {
            Map<String, Object> detailData = new HashMap<String, Object>();
            if (bean == null){
                detailData.put("Id",0);
            }else {
                if (i < bean.getRetData().getDetailData().getTravelExpenseDetail().size() + 1){
                    detailData.put("Id",bean.getRetData().getDetailData().getTravelExpenseDetail().get(i-1).getId());
                }else {
                    detailData.put("Id",0);
                }
            }
            detailData.put("ActualLeaveDate", groups.get(i).getHolders().get(0).getRealValue().equals("")?"":groups.get(i).getHolders().get(0).getRealValue());
            detailData.put("ActualReturnDate", groups.get(i).getHolders().get(1).getRealValue().equals("")?"":groups.get(i).getHolders().get(1).getRealValue());
            detailData.put("WorkflowIdentifier", uuid);
            String from = groups.get(i).getHolders().get(2).getRealValue();
            String to = groups.get(i).getHolders().get(3).getRealValue();
            detailData.put("From", from);
            detailData.put("To", to);
            detailData.put("Transport", DataUtil.getDicIdByDescr(transportation, groups.get(i).getHolders().get(4).getRealValue()));
            detailData.put("TicketCost", groups.get(i).getHolders().get(5).getRealValue().equals("") ? "0" : groups.get(i).getHolders().get(5).getRealValue());
            detailData.put("TransportCost", groups.get(i).getHolders().get(6).getRealValue().equals("") ? "0" : groups.get(i).getHolders().get(6).getRealValue());
            detailData.put("HotelCost", groups.get(i).getHolders().get(7).getRealValue().equals("") ? "0" : groups.get(i).getHolders().get(7).getRealValue());
            detailData.put("GuestsCost", groups.get(i).getHolders().get(8).getRealValue().equals("") ? "0" : groups.get(i).getHolders().get(8).getRealValue());
            detailData.put("OtherCost", groups.get(i).getHolders().get(9).getRealValue().equals("") ? "0" : groups.get(i).getHolders().get(9).getRealValue());
            detailData.put("Allowance", groups.get(i).getHolders().get(10).getRealValue().equals("") ? "0" : groups.get(i).getHolders().get(10).getRealValue());
            if (title.equals("提交")){
                double v = Double.parseDouble(groups.get(i).getHolders().get(5).getRealValue().isEmpty() ? "0" : groups.get(i).getHolders().get(5).getRealValue()) +
                        Double.parseDouble(groups.get(i).getHolders().get(6).getRealValue().isEmpty() ? "0" : groups.get(i).getHolders().get(6).getRealValue()) +
                        Double.parseDouble(groups.get(i).getHolders().get(7).getRealValue().isEmpty() ? "0" : groups.get(i).getHolders().get(7).getRealValue()) +
                        Double.parseDouble(groups.get(i).getHolders().get(8).getRealValue().isEmpty() ? "0" : groups.get(i).getHolders().get(8).getRealValue()) +
                        Double.parseDouble(groups.get(i).getHolders().get(9).getRealValue().isEmpty() ? "0" : groups.get(i).getHolders().get(9).getRealValue()) +
                        Double.parseDouble(groups.get(i).getHolders().get(10).getRealValue().isEmpty() ? "0" : groups.get(i).getHolders().get(10).getRealValue());
                if (v == 0){
                    ToastUtil.showToast(getContext(),"第" + i + "条数据中至少应有一项报销金额大于0");
                    setButtonllEnable(true);
                    return;
                }}
            if (groups.get(i).getOldBarcode()!=null){
                if (groups.get(i).getOldBarcode().startsWith("BTR")){
                    detailData.put("BusinessTripBarCode",groups.get(i).getOldBarcode());
                }else if(groups.get(i).getOldBarcode().startsWith("HER")){
                    detailData.put("EntertainmentBarCode",groups.get(i).getOldBarcode());
                }
            }
            details.add(detailData);
        }
        String detail = new Gson().toJson(details);
        body.put("detailData", detail);
        String groupName = getDisplayValueByKey(this.getString(R.string.Attachment_Type)).getDispayValue();
        if (!title.equals("保存") && (groupName.isEmpty() || paths.get("attach").size() == 0)){
            ToastUtil.showToast(getContext(),this.getString(R.string.Please_Select) + "附件");
            setButtonllEnable(true);
            return;
        }
        applySaveOrStart(CommonValues.REQ_TRAVEL_EXPENSE_APPLY, body, title);
    }

    @Override
    public void onHolderTextChanged(int main, int index, final HandInputGroup.Holder holder) {
        final String key = holder.getKey();
        if (key.equals(this.getString(R.string.Air_Ticket))||key.equals(this.getString(R.string.Transport_Cost))||key.equals(this.getString(R.string.Hotel_Cost))||key.equals(this.getString(R.string.Guests_Cost))||key.equals(this.getString(R.string.Other_Cost))||key.equals(this.getString(R.string.Allowance))){
            List<Group> detals = getGroupsByTitle(this.getString(R.string.Details_Information));
            double sum =0;
            for (Group detal : detals) {
                double TicketCost = Double.parseDouble(detal.getHolders().get(5).getRealValue().isEmpty()?"0":detal.getHolders().get(5).getRealValue());
                double TransportCost = Double.parseDouble(detal.getHolders().get(6).getRealValue().isEmpty()?"0":detal.getHolders().get(6).getRealValue());
                double HotelCost = Double.parseDouble(detal.getHolders().get(7).getRealValue().isEmpty()?"0":detal.getHolders().get(7).getRealValue());
                double GuestsCost = Double.parseDouble(detal.getHolders().get(8).getRealValue().isEmpty()?"0":detal.getHolders().get(8).getRealValue());
                double OtherCost = Double.parseDouble(detal.getHolders().get(9).getRealValue().isEmpty()?"0":detal.getHolders().get(9).getRealValue());
                double Allowance = Double.parseDouble(detal.getHolders().get(10).getRealValue().isEmpty()?"0":detal.getHolders().get(10).getRealValue());
                double num =TicketCost + TransportCost + HotelCost + GuestsCost + OtherCost + Allowance;
                sum += num;
            }
            final double finalSum = sum;
            getGroupsByTitle(this.getString(R.string.Total)).get(0).setGroupTopRightTitle(df.format(finalSum));
            notifyGroupChanged(getGroup().size()-2, 1);
        }else if (key.equals(this.getString(R.string.Payee_Employee_ID))){
            getDisplayValueByKey(this.getString(R.string.Payee_Name)).setEditable(true);
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
                                getDisplayValueByKey(TravelApplyFragment.this.getString(R.string.Payee_Name)).setDispayValue(retDataBean.getAccountName());
                                getDisplayValueByKey(TravelApplyFragment.this.getString(R.string.Payee_Bank)).setDispayValue(retDataBean.getPayeeBankName());
                                getDisplayValueByKey(TravelApplyFragment.this.getString(R.string.Payee_Bank_Account)).setDispayValue(retDataBean.getAccountNumber());
                                getDisplayValueByKey(TravelApplyFragment.this.getString(R.string.Payee_Bank_Adress)).setDispayValue(retDataBean.getProvinceName() + " " + retDataBean.getCityName());
                                getDisplayValueByKey(TravelApplyFragment.this.getString(R.string.Payee_Branch_Bank)).setDispayValue(retDataBean.getPayeeBranchBank());
                                cityid = personalAccountEntity.getRetData().get(0).getPayeeBankCity();
                                notifyDataSetChanged();
                            }
                        } else {
                            ToastUtil.showToast(getContext(),personalAccountEntity.getMsg());
                            getDisplayValueByKey(TravelApplyFragment.this.getString(R.string.Payee_Name)).setDispayValue("");
                            getDisplayValueByKey(TravelApplyFragment.this.getString(R.string.Payee_Bank)).setDispayValue("");
                            getDisplayValueByKey(TravelApplyFragment.this.getString(R.string.Payee_Bank_Account)).setDispayValue("");
                            getDisplayValueByKey(TravelApplyFragment.this.getString(R.string.Payee_Bank_Adress)).setDispayValue("");
                            getDisplayValueByKey(TravelApplyFragment.this.getString(R.string.Payee_Branch_Bank)).setDispayValue("");
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
                        getDisplayValueByKey(TravelApplyFragment.this.getString(R.string.Payee_Name)).setDispayValue("");
                        getDisplayValueByKey(TravelApplyFragment.this.getString(R.string.Payee_Bank)).setDispayValue("");
                        getDisplayValueByKey(TravelApplyFragment.this.getString(R.string.Payee_Bank_Account)).setDispayValue("");
                        getDisplayValueByKey(TravelApplyFragment.this.getString(R.string.Payee_Bank_Adress)).setDispayValue("");
                        getDisplayValueByKey(TravelApplyFragment.this.getString(R.string.Payee_Branch_Bank)).setDispayValue("");
                        notifyDataSetChanged();
                    }
                });
            }
        });
    }

    private void loadDraftListData() {
        HttpManager manager = HttpManager.getInstance();
        Map<String, Object> params = CommonValues.getCommonParams(getActivity());
        //公司集合
        String company = CacheManger.getInstance().getData(CommonValues.GET_COMPANY_LIST);
        companyEntities = GsonUtil.parseJsonToBean(company,CompanyEntity.class).getRetData();

        //交通工具
        params.put("code",3172);
        manager.requestResultForm(CommonValues.GET_DICTIONARY_LIST, params, DictionaryEntity.class, new HttpManager.ResultCallback<DictionaryEntity>() {
            @Override
            public void onSuccess(String content, DictionaryEntity expenseTypeEntity) {
                transportation = expenseTypeEntity.getRetData().get(0).getDataList();
            }

            @Override
            public void onFailure(String content) {
            }
        });

        params.put("code", 2153); //附件类别
        manager.requestResultForm(CommonValues.GET_DICTIONARY_LIST, params, DictionaryEntity.class, new HttpManager.ResultCallback<DictionaryEntity>() {
            @Override
            public void onSuccess(String content, final DictionaryEntity expenseTypeEntity) {
                draftAttachmentType = expenseTypeEntity.getRetData().get(0).getDataList();
            }

            @Override
            public void onFailure(String content) {

            }
        });

    }

    private void loadById(String code) {
        Map<String, Object> commonParams = CommonValues.getCommonParams(getActivity());
        commonParams.put("companyCode", code);
        HttpManager.getInstance().requestResultForm(CommonValues.GET_COMPANYCB_LIST, commonParams, CompanyCBPEntity.class, new HttpManager.ResultCallback<CompanyCBPEntity>() {
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
    public void onOneItemBottomDrawableResClick(int index) {
        List<HandInputGroup.Holder> subDetail = new ArrayList<>();
        subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Leave_Date), true, false, "/" + this.getString(R.string.Please_Fill), HandInputGroup.VALUE_TYPE.DATE));
        subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Return_Date), true, false, "/" + this.getString(R.string.Please_Fill), HandInputGroup.VALUE_TYPE.DATE));
        subDetail.add(new HandInputGroup.Holder(this.getString(R.string.From), true, false, "/" + this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.TEXTFILED));
        subDetail.add(new HandInputGroup.Holder(this.getString(R.string.To), true, false, "/" + this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.TEXTFILED));
        subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Transport), true, false, "/" + this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.SELECT));
        subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Air_Ticket), false, false, "/" + this.getString(R.string.Please_Fill), HandInputGroup.VALUE_TYPE.DOUBLE));
        subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Transport_Cost), false, false, "/" + this.getString(R.string.Please_Fill), HandInputGroup.VALUE_TYPE.DOUBLE));
        subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Hotel_Cost), false, false, "/" + this.getString(R.string.Please_Fill), HandInputGroup.VALUE_TYPE.DOUBLE));
        subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Guests_Cost), false, false, "/" + this.getString(R.string.Please_Fill), HandInputGroup.VALUE_TYPE.DOUBLE));
        subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Other_Cost), false, false, "/" + this.getString(R.string.Please_Fill), HandInputGroup.VALUE_TYPE.DOUBLE));
        subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Allowance), false, false, "/" + this.getString(R.string.Please_Fill), HandInputGroup.VALUE_TYPE.DOUBLE));
        addGroup(index + 1, new Group(this.getString(R.string.Details_Information), R.mipmap.add_detail3x, true, null, subDetail).sethasDelete(true));
        getGroup().get(index).setDrawableRes(null);
        notifyDataSetChanged();
    }

    @Override
    public void onDataChanged(HandInputGroup.Holder holder){
        Group group = getGroup().get(getitemnum(holder));
        if (holder.getKey().equals(this.getString(R.string.Leave_Date))){
            HandInputGroup.Holder holder1 = group.getHolders().get(1);
            if (!holder1.getRealValue().isEmpty()){
                String leave = holder.getRealValue();
                String returnt = holder1.getRealValue();
                int getday = getday(leave, returnt);
                if (getday == -1) {
                    holder.setDispayValue("/填写出发时间");
                    ToastUtil.showToast(getContext(),"请正确选择第" + getitemnum(holder) + "条明细信息中的出发、到达时间");
                }
            }
        }else if (holder.getKey().equals(this.getString(R.string.Return_Date))){
            HandInputGroup.Holder holder1 = group.getHolders().get(0);
            if (!holder1.getRealValue().isEmpty()){
                String returnt = holder.getRealValue();
                String leave = holder1.getRealValue();
                int getday = getday(leave, returnt);
                if (getday == -1) {
                    holder.setDispayValue("/填写到达时间");
                    ToastUtil.showToast(getContext(),"请正确选择第" + getitemnum(holder) + "条明细信息中的出发、到达时间");
                }
            }
        }
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

        FileChooserLayout.FileType file = null;
        if (extension != null){
            extension = extension.toLowerCase();
            if(extension.endsWith(".jpg")||extension.endsWith(".jpeg")|| extension.endsWith(".png")||
                    extension.endsWith(".bmp")|| extension.endsWith(".gif")||extension.endsWith(".doc")||
                    extension.endsWith(".docx")||extension.endsWith(".xls")||extension.endsWith(".xlsx")||
                    extension.endsWith(".rar")){
                Map<String, Object> map = CommonValues.getCommonParams(getActivity());
                String groupName = getDisplayValueByKey(this.getString(R.string.Attachment_Type)).getRealValue();
                map.put("fileGroupName", groupName.isEmpty()?draftAttachmentType.get(0).getDicDesc():groupName);
                map.put("workflowIdentifier", uuid);
                map.put("workflowType", CommonValues.WORKFLOW_TRAVEL_OFFER);
                map.put("createBy", GeelyApp.getLoginEntity().getUserId());
                map.put("fileGroupValue", DataUtil.getDicIdByDescr(draftAttachmentType, groupName.isEmpty()?draftAttachmentType.get(0).getDicDesc():groupName));
                path = FileUtils.getPath(getActivity(), uri);
                String name = path.substring(path.lastIndexOf("/") + 1);
                map.put("orignFileName", name);
                map.put("fileName", name.substring(0, name.lastIndexOf(".")));
                map.put("picLocalId", 0);
                String fileBase64 = HttpManager.uri2Base64(uri, getActivity());
                if (fileBase64 == null) {
                }else {
                    map.put("fileBase64", fileBase64);
                    showDialog("附件上传中......",0);
                    setButtonllEnable(false);
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