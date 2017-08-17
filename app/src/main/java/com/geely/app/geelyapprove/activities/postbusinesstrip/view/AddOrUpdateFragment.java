package com.geely.app.geelyapprove.activities.postbusinesstrip.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.geely.app.geelyapprove.activities.HistoryActivity;
import com.geely.app.geelyapprove.activities.login.entity.LoginEntity;
import com.geely.app.geelyapprove.activities.postbusinesstrip.bean.PostBusinessTripEntity;
import com.geely.app.geelyapprove.common.application.GeelyApp;
import com.geely.app.geelyapprove.common.entity.DataListEntity;
import com.geely.app.geelyapprove.common.entity.DictionaryEntity;
import com.geely.app.geelyapprove.common.fragment.CommonFragment;
import com.geely.app.geelyapprove.common.utils.CommonValues;
import com.geely.app.geelyapprove.common.utils.ToastUtil;
import com.geely.app.geelyapprove.common.view.HandInputGroup;
import com.geely.app.geelyapprove.common.view.HandToolbar;
import com.geely.app.geelyapprove.datamanage.HttpManager;
import com.geely.app.orientalpearl.R;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.geely.app.geelyapprove.common.utils.DataUtil.getDescr;
import static com.geely.app.geelyapprove.common.utils.DataUtil.getDicIdByDescr;


/**  新建公出申請
 * Created by zhy on 2016/9/22.
 */
public class AddOrUpdateFragment extends CommonFragment{

    private String mCompNameCN;
    private String mDeptNameCN;
    private String mUserId;
    private String mNameCN;
    private String mPositionNameCN;
    private LoginEntity loginEntity = GeelyApp.getLoginEntity();
    private boolean mIsProxy;
    private boolean mAllowance;
    private String uuid;
    private String mPlanLeaceDate;
    private String mPlanReturnDate;
    private HandInputGroup.Holder mholder;
    private List<DataListEntity> transportation,BusinessTripType;
    private PostBusinessTripEntity bean;
    private String mEid;
    private String barcode;
    private DecimalFormat df;

    public AddOrUpdateFragment() {

    }

    public static AddOrUpdateFragment newInstance(Bundle bundle) {
        AddOrUpdateFragment fragment = new AddOrUpdateFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public static AddOrUpdateFragment newInstance() {
        AddOrUpdateFragment fragment = new AddOrUpdateFragment();

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
            HttpManager.getInstance().requestResultForm(CommonValues.REQ_BLEAVE_DETAIL_DISPLAY, params, PostBusinessTripEntity.class, new HttpManager.ResultCallback<PostBusinessTripEntity>() {
                @Override
                public void onSuccess(String json, final PostBusinessTripEntity entity) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(entity!=null&&entity.getCode().equals("100")){
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
                public void onFailure(final String msg) {
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
        //获取公司名称
        mCompNameCN = loginEntity.getRetData().getUserInfo().getCompNameCN();
        //获取部门/车间/班组
        mDeptNameCN = loginEntity.getRetData().getUserInfo().getDeptNameCN();
        //获取员工编号
        mUserId = loginEntity.getRetData().getUserInfo().getUserId();
        //获取Eid
        mEid = loginEntity.getRetData().getUserInfo().getEid();
        //获取姓名
        mNameCN = loginEntity.getRetData().getUserInfo().getNameCN();
        //获取岗位
        mPositionNameCN = loginEntity.getRetData().getUserInfo().getPositionNameCN();
        //设置界面条目数量及名称、显示文本或图片
        List<Group> groups = new ArrayList<>();
        if (bean == null) {
            List<HandInputGroup.Holder> holderList1 = new ArrayList<>();
            holderList1.add(new HandInputGroup.Holder(this.getString(R.string.Company_Name), false, false, mCompNameCN, HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
            holderList1.add(new HandInputGroup.Holder(this.getString(R.string.Department), false, false, mDeptNameCN, HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
            holderList1.add(new HandInputGroup.Holder(this.getString(R.string.Employee_ID), false, false, mEid, HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
            holderList1.add(new HandInputGroup.Holder(this.getString(R.string.Name), false, false, mNameCN, HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
            holderList1.add(new HandInputGroup.Holder(this.getString(R.string.Position), false, false, mPositionNameCN, HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
            holderList1.add(new HandInputGroup.Holder(this.getString(R.string.Reimbursement_or_not), true, false, this.getString(R.string.No), HandInputGroup.VALUE_TYPE.BUTTONS));
            holderList1.add(new HandInputGroup.Holder(this.getString(R.string.Leave_Budget), false, false, "", HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
            holderList1.add(new HandInputGroup.Holder(this.getString(R.string.Leave_Type), true, false, "/" + this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.SELECT));
            holderList1.add(new HandInputGroup.Holder(this.getString(R.string.Expected_Leave_Days), true, false, "/0", HandInputGroup.VALUE_TYPE.DOUBLE).setColor(Color.rgb(229,0,17)));
            holderList1.add(new HandInputGroup.Holder(this.getString(R.string.Expected_Leave_Date), true, false, "/填写计划离开时间", HandInputGroup.VALUE_TYPE.DATE));
            holderList1.add(new HandInputGroup.Holder(this.getString(R.string.Expected_Return_Date), true, false, "/填写计划回来时间", HandInputGroup.VALUE_TYPE.DATE));
            holderList1.add(new HandInputGroup.Holder(this.getString(R.string.BusinessTrip_Reasons), true, false, "/" + this.getString(R.string.Please_Fill), HandInputGroup.VALUE_TYPE.TEXTFILED));
            groups.add(new Group(this.getString(R.string.Basic_Information), null, true, null, holderList1));
            List<HandInputGroup.Holder> holderList2 = new ArrayList<>();
            holderList2.add(new HandInputGroup.Holder(this.getString(R.string.Leave_Date),true, false, "/填写出发日期", HandInputGroup.VALUE_TYPE.DATE));
            holderList2.add(new HandInputGroup.Holder(this.getString(R.string.Return_Date),true, false, "/填写到达日期", HandInputGroup.VALUE_TYPE.DATE));
            holderList2.add(new HandInputGroup.Holder(this.getString(R.string.From), true, false, "/" + this.getString(R.string.Please_Fill), HandInputGroup.VALUE_TYPE.TEXTFILED));
            holderList2.add(new HandInputGroup.Holder(this.getString(R.string.To), true, false, "/" + this.getString(R.string.Please_Fill), HandInputGroup.VALUE_TYPE.TEXTFILED));
            holderList2.add(new HandInputGroup.Holder(this.getString(R.string.Transport), true, false, "/" + this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.SELECT));
            holderList2.add(new HandInputGroup.Holder(this.getString(R.string.Air_Ticket), false, false, "/" + this.getString(R.string.Please_Fill), HandInputGroup.VALUE_TYPE.DOUBLE));
            holderList2.add(new HandInputGroup.Holder(this.getString(R.string.Transport_Cost), false, false, "/" + this.getString(R.string.Please_Fill), HandInputGroup.VALUE_TYPE.DOUBLE));
            holderList2.add(new HandInputGroup.Holder(this.getString(R.string.Hotel_Cost), false, false, "/" + this.getString(R.string.Please_Fill), HandInputGroup.VALUE_TYPE.DOUBLE));
            holderList2.add(new HandInputGroup.Holder(this.getString(R.string.Guests_Cost), false, false, "/" + this.getString(R.string.Please_Fill), HandInputGroup.VALUE_TYPE.DOUBLE));
            holderList2.add(new HandInputGroup.Holder(this.getString(R.string.Other_Cost), false, false, "/" + this.getString(R.string.Please_Fill), HandInputGroup.VALUE_TYPE.DOUBLE));
            holderList2.add(new HandInputGroup.Holder(this.getString(R.string.Allowance), false, false, "/" + this.getString(R.string.Please_Fill), HandInputGroup.VALUE_TYPE.DOUBLE));
            groups.add(new Group(this.getString(R.string.Details_Information), R.mipmap.add_detail3x, true, null, holderList2).sethasDelete(true));
            List<HandInputGroup.Holder> holderList3 = new ArrayList<>();
            groups.add(new Group(this.getString(R.string.Total), null, true,"0", holderList3));
        } else {
            PostBusinessTripEntity.RetDataBean.DetailDataBean dataBean = bean.getRetData().getDetailData();
            List<HandInputGroup.Holder> holderList1 = new ArrayList<>();
            holderList1.add(new HandInputGroup.Holder(this.getString(R.string.Company_Name), false, false, mCompNameCN, HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
            holderList1.add(new HandInputGroup.Holder(this.getString(R.string.Department), false, false, mDeptNameCN, HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
            holderList1.add(new HandInputGroup.Holder(this.getString(R.string.Employee_ID), false, false, mEid, HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
            holderList1.add(new HandInputGroup.Holder(this.getString(R.string.Name), false, false, mNameCN, HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
            holderList1.add(new HandInputGroup.Holder(this.getString(R.string.Position), false, false, mPositionNameCN, HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
            if (dataBean != null) {
                mIsProxy = dataBean.isIsProxy();
                holderList1.add(new HandInputGroup.Holder(this.getString(R.string.Reimbursement_or_not), true, false, dataBean.isAllowance() ? this.getString(R.string.Yes) : this.getString(R.string.No), HandInputGroup.VALUE_TYPE.BUTTONS));
                holderList1.add(new HandInputGroup.Holder(this.getString(R.string.Leave_Budget), false, false, dataBean.getBudget() + "", HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
                holderList1.add(new HandInputGroup.Holder(this.getString(R.string.Leave_Type), true, false, dataBean.getLeaveTypeName(), HandInputGroup.VALUE_TYPE.SELECT));
                holderList1.add(new HandInputGroup.Holder(this.getString(R.string.Expected_Leave_Days), true, false, TextUtils.isEmpty(dataBean.getTripDay()) ? "/0" : dataBean.getTripDay() + "", HandInputGroup.VALUE_TYPE.DOUBLE).setColor(Color.rgb(229,0,17)));
                holderList1.add(new HandInputGroup.Holder(this.getString(R.string.Expected_Leave_Date), true, false, dataBean.getPlanLeaveDate().isEmpty() ? "/填写计划离开时间" : dataBean.getPlanLeaveDate(), HandInputGroup.VALUE_TYPE.DATE));
                holderList1.add(new HandInputGroup.Holder(this.getString(R.string.Expected_Return_Date), true, false, dataBean.getPlanReturnDate().isEmpty() ? "/填写计划回来时间" : dataBean.getPlanReturnDate(), HandInputGroup.VALUE_TYPE.DATE));
                holderList1.add(new HandInputGroup.Holder(this.getString(R.string.BusinessTrip_Reasons), true, false, dataBean.getDescr(), HandInputGroup.VALUE_TYPE.TEXTFILED));
                groups.add(new Group(this.getString(R.string.Basic_Information), null, true, null, holderList1));

                for (int i = 0; i < dataBean.getBusinessTripDetail().size(); i++) {
                    List<HandInputGroup.Holder> holderList = new ArrayList<>();
                    holderList.add(new HandInputGroup.Holder(this.getString(R.string.Leave_Date), true, false, dataBean.getBusinessTripDetail().get(i).getLeaveDate().isEmpty() ? "/填写出发日期" : dataBean.getBusinessTripDetail().get(i).getLeaveDate(), HandInputGroup.VALUE_TYPE.DATE));
                    holderList.add(new HandInputGroup.Holder(this.getString(R.string.Return_Date), true, false, dataBean.getBusinessTripDetail().get(i).getReturnDate().isEmpty() ? "/填写到达日期" : dataBean.getBusinessTripDetail().get(i).getReturnDate(), HandInputGroup.VALUE_TYPE.DATE));
                    holderList.add(new HandInputGroup.Holder(this.getString(R.string.From), true, false, dataBean.getBusinessTripDetail().get(i).getFrom().isEmpty() ? "/" + this.getString(R.string.Please_Fill) : dataBean.getBusinessTripDetail().get(i).getFrom(), HandInputGroup.VALUE_TYPE.TEXTFILED));
                    holderList.add(new HandInputGroup.Holder(this.getString(R.string.To), true, false, dataBean.getBusinessTripDetail().get(i).getTo().isEmpty()? "/" + this.getString(R.string.Please_Fill) : dataBean.getBusinessTripDetail().get(i).getTo(), HandInputGroup.VALUE_TYPE.TEXTFILED));
                    holderList.add(new HandInputGroup.Holder(this.getString(R.string.Transport), true, false, dataBean.getBusinessTripDetail().get(i).getTransportName(), HandInputGroup.VALUE_TYPE.SELECT));
                    holderList.add(new HandInputGroup.Holder(this.getString(R.string.Air_Ticket), false, false, (dataBean.getBusinessTripDetail().get(i).getTicketCost().isEmpty()||dataBean.getBusinessTripDetail().get(i).getTicketCost().isEmpty())? "/" + this.getString(R.string.Please_Fill) : dataBean.getBusinessTripDetail().get(i).getTicketCost(), HandInputGroup.VALUE_TYPE.DOUBLE));
                    holderList.add(new HandInputGroup.Holder(this.getString(R.string.Transport_Cost), false, false, (dataBean.getBusinessTripDetail().get(i).getTransportCost().isEmpty()||dataBean.getBusinessTripDetail().get(i).getTransportCost().isEmpty()) ? "/" + this.getString(R.string.Please_Fill) : dataBean.getBusinessTripDetail().get(i).getTransportCost(), HandInputGroup.VALUE_TYPE.DOUBLE));
                    holderList.add(new HandInputGroup.Holder(this.getString(R.string.Hotel_Cost), false, false, (dataBean.getBusinessTripDetail().get(i).getHotelCost().isEmpty()||dataBean.getBusinessTripDetail().get(i).getHotelCost().isEmpty() )? "/" + this.getString(R.string.Please_Fill) : dataBean.getBusinessTripDetail().get(i).getHotelCost(), HandInputGroup.VALUE_TYPE.DOUBLE));
                    holderList.add(new HandInputGroup.Holder(this.getString(R.string.Guests_Cost), false, false, (dataBean.getBusinessTripDetail().get(i).getGuestsCost().isEmpty()||dataBean.getBusinessTripDetail().get(i).getGuestsCost().isEmpty() )? "/" + this.getString(R.string.Please_Fill) : dataBean.getBusinessTripDetail().get(i).getGuestsCost(), HandInputGroup.VALUE_TYPE.DOUBLE));
                    holderList.add(new HandInputGroup.Holder(this.getString(R.string.Other_Cost), false, false, (dataBean.getBusinessTripDetail().get(i).getOtherCost().isEmpty()||dataBean.getBusinessTripDetail().get(i).getOtherCost().isEmpty() )? "/" + this.getString(R.string.Please_Fill) : dataBean.getBusinessTripDetail().get(i).getOtherCost(), HandInputGroup.VALUE_TYPE.DOUBLE));
                    holderList.add(new HandInputGroup.Holder(this.getString(R.string.Allowance), false, false, (dataBean.getBusinessTripDetail().get(i).getAllowance().isEmpty()||dataBean.getBusinessTripDetail().get(i).getAllowance().isEmpty() )? "/" + this.getString(R.string.Please_Fill) : dataBean.getBusinessTripDetail().get(i).getAllowance(), HandInputGroup.VALUE_TYPE.DOUBLE));
                    if (i == dataBean.getBusinessTripDetail().size()-1){
                        groups.add(new Group(this.getString(R.string.Details_Information), R.mipmap.add_detail3x, true, null, holderList).sethasDelete(true));
                    }else {
                        groups.add(new Group(this.getString(R.string.Details_Information), null, true, null, holderList).sethasDelete(true));
                    }
                }
                List<HandInputGroup.Holder> holderList3 = new ArrayList<>();
                groups.add(new Group(this.getString(R.string.Total), null, true, dataBean.getBudget(), holderList3));
            }
        }

        return groups;
    }


    @Override
    public void setToolbar(HandToolbar toolbar) {
        //设置页面名称
        toolbar.setTitle("国内公出");
        toolbar.setDisplayHomeAsUpEnabled(true, getActivity());
        loadDraftListData();
    }

    @Override
    public String[] getBottomButtonsTitles() {
        //设置底部按钮个数及名称
        return new String[]{"提交", "保存"};
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBottomButtonsClick(final String title, final List<Group> groups) {
        setButtonllEnable(false);
        if (title.equals("保存")){
            apply(title);
        }else {
            String over = isOver(groups);
            if (over == null){
                apply(title);
            }else {
                ToastUtil.showToast(getContext(),this.getString(R.string.Please_Fill) + over);
                setButtonllEnable(true);
            }
        }
    }

    private void apply(String actionType) {
        setButtonllEnable(false);
        if (bean == null) {
            uuid = UUID.randomUUID().toString();
        } else {
            uuid = bean.getRetData().getDetailData().getIdentifier();
            barcode = getArguments().getString("barCode");
        }
        Map<String, Object> body = CommonValues.getCommonParams(getActivity());
        body.remove("auditNode");
        Map<String, Object> mainData = new HashMap<String, Object>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date();//获取当前时间
        String str = formatter.format(curDate);
        if (bean == null) {
            mainData.put("CreateTime", str);
            body.put("SN", "");
        } else {
            mainData.put("CreateTime", bean.getRetData().getDetailData().getCreateTime());
            mainData.put("Id", bean.getRetData().getDetailData().getId());
            mainData.put("BarCode", barcode);
            body.put("SN", getArguments().getString("SN"));
        }
        mainData.put("UpdateTime", str);
        mainData.put("SubmitBy", mUserId);
        mainData.put("UpdateBy",mUserId);
        mainData.put("IsProxy", mIsProxy);
        String s = str.split(" ")[0];
        mainData.put("Summary", s.split("-")[0] + s.split("-")[1] + s.split("-")[2] + "-" + GeelyApp.getLoginEntity().getRetData().getUserInfo().getDeptNameCN() + GeelyApp.getLoginEntity().getRetData().getUserInfo().getNameCN()
                + getGroup().get(1).getHolders().get(3).getRealValue()+ "公出" + getDisplayValueByKey(this.getString(R.string.Expected_Leave_Days)).getRealValue() + "天-" + (getDisplayValueByKey(this.getString(R.string.Leave_Budget)).getRealValue().isEmpty()?"0":getDisplayValueByKey(this.getString(R.string.Leave_Budget)).getRealValue()) + "RMB");
        String Allowance = getDisplayValueByKey(this.getString(R.string.Reimbursement_or_not)).getRealValue();
        mAllowance = Allowance.equals(this.getString(R.string.Yes));
        mainData.put("Allowance", mAllowance);
        String budget = getDisplayValueByKey(this.getString(R.string.Leave_Budget)).getRealValue();
        mainData.put("Budget", budget.equals("") ? "0" : budget);
        mainData.put("Descr", getDisplayValueByKey(this.getString(R.string.BusinessTrip_Reasons)).getRealValue());
        mainData.put("LeaveType", getDicIdByDescr(BusinessTripType, getDisplayValueByKey(this.getString(R.string.Leave_Type)).getRealValue()));
        mainData.put("LeaveTypeName", getDisplayValueByKey(this.getString(R.string.Leave_Type)).getRealValue());
        String tripDay = getDisplayValueByKey(this.getString(R.string.Expected_Leave_Days)).getRealValue();
        if (!actionType.equals("保存") && Double.parseDouble(tripDay.isEmpty()?"0":tripDay) * 4 % 2 != 0){
            ToastUtil.showToast(getContext(),"预计离开时间必须是0.5的倍数");
            setButtonllEnable(true);
            return;
        }
        if (!actionType.equals("保存") && (getday(getDisplayValueByKey(this.getString(R.string.Expected_Leave_Date)).getRealValue().isEmpty()?"0000-00-00":getDisplayValueByKey(this.getString(R.string.Expected_Leave_Date)).getRealValue() + " 00:00", getDisplayValueByKey(this.getString(R.string.Expected_Return_Date)).getRealValue().isEmpty()?"0000-00-00":getDisplayValueByKey(this.getString(R.string.Expected_Return_Date)).getRealValue() + " 00:00") + 24 < Double.parseDouble(tripDay.isEmpty()?"0":tripDay)*24)){
            ToastUtil.showToast(getContext(),"预计公出时间不能超过所选时间段");
            setButtonllEnable(true);
            return;
        }
        if (TextUtils.isEmpty(tripDay)) {
            mainData.put("TripDay", 0);
        } else {
            mainData.put("TripDay", tripDay);
        }
        mPlanLeaceDate = getDisplayValueByKey(this.getString(R.string.Expected_Leave_Date)).getRealValue();
        mainData.put("PlanLeaveDate", mPlanLeaceDate);
        mPlanReturnDate = getDisplayValueByKey(this.getString(R.string.Expected_Return_Date)).getRealValue();
        mainData.put("PlanReturnDate", mPlanReturnDate);
        mainData.put("Identifier", uuid);
        mainData.put("IsPostToPs", false);
        String main = new Gson().toJson(mainData);
        body.put("mainData", main);
        List<Object> details = new ArrayList<Object>();
        if (mIsProxy) {
            details = null;
        } else {
            List<Group> groups = getGroup();
            for (int i = 1; i < getGroup().size() - 1; i++) {
                Map<String, Object> detailData = new HashMap<String, Object>();
                detailData.put("Id",0);
                detailData.put("WorkflowIdentifier", uuid);
                detailData.put("ReturnDate", getGroup().get(i).getHolders().get(0).getRealValue());
                detailData.put("LeaveDate", groups.get(i).getHolders().get(1).getRealValue());
                detailData.put("From", groups.get(i).getHolders().get(2).getRealValue());
                detailData.put("To", groups.get(i).getHolders().get(3).getRealValue());
                detailData.put("Transport", getDicIdByDescr(transportation, groups.get(i).getHolders().get(4).getRealValue()));
                detailData.put("TicketCost", groups.get(i).getHolders().get(5).getRealValue().equals("") ? "0" : groups.get(i).getHolders().get(5).getRealValue());
                detailData.put("TransportCost", groups.get(i).getHolders().get(6).getRealValue().equals("") ? "0" : groups.get(i).getHolders().get(6).getRealValue());
                detailData.put("HotelCost", groups.get(i).getHolders().get(7).getRealValue().equals("") ? "0" : groups.get(i).getHolders().get(7).getRealValue());
                detailData.put("GuestsCost", groups.get(i).getHolders().get(8).getRealValue().equals("") ? "0" : groups.get(i).getHolders().get(8).getRealValue());
                detailData.put("OtherCost", groups.get(i).getHolders().get(9).getRealValue().equals("") ? "0" : groups.get(i).getHolders().get(9).getRealValue());
                detailData.put("Allowance", groups.get(i).getHolders().get(10).getRealValue().equals("") ? "0" : groups.get(i).getHolders().get(10).getRealValue());
                if (actionType.equals("提交")){
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
                    }
                }

                details.add(detailData);
            }
        }
        String detail = new Gson().toJson(details);
        body.put("detailData", detail);
        applySaveOrStart(CommonValues.REQ_BLEAVE_APPLY_POST, body, actionType);

    }

    @Override
    public void onDataChanged(HandInputGroup.Holder holder){
        Group group = getGroup().get(getitemnum(holder));
        if (holder.getKey().equals(this.getString(R.string.Expected_Return_Date))){
            if(!getDisplayValueByKey(this.getString(R.string.Expected_Leave_Date)).getRealValue().isEmpty()) {
                String returnt = holder.getRealValue();
                String leave = getDisplayValueByKey(this.getString(R.string.Expected_Leave_Date)).getRealValue();
                int getday = getday(leave + " 00:00", returnt + " 00:00");
                if (getday == -1) {
                    holder.setDispayValue("/填写计划回来时间");
                    getDisplayValueByKey(this.getString(R.string.Expected_Leave_Days)).setDispayValue("/0");
                    ToastUtil.showToast(getContext(),"请正确选择预计回来时间");
                }
            }
        }else if (holder.getKey().equals(this.getString(R.string.Expected_Leave_Date))){
            if (!getDisplayValueByKey(this.getString(R.string.Expected_Return_Date)).getRealValue().isEmpty()){
                String leave = holder.getRealValue();
                String returnt = getDisplayValueByKey(this.getString(R.string.Expected_Return_Date)).getRealValue();
                int getday = getday(leave + " 00:00", returnt + " 00:00");
                if (getday == -1) {
                    holder.setDispayValue("/填写计划离开时间");
                    ToastUtil.showToast(getContext(),"请正确选择预计回来时间");
                }
            }
        }else if (holder.getKey().equals(this.getString(R.string.Leave_Date))){
            HandInputGroup.Holder holder1 = group.getHolders().get(1);
            if (!holder1.getRealValue().isEmpty()){
                String leave = holder.getRealValue();
                String returnt = holder1.getRealValue();
                int getday = getday(leave + " 00:00", returnt + " 00:00");
                if (getday == -1) {
                    holder.setDispayValue("/填写出发日期");
                    ToastUtil.showToast(getContext(),"请正确选择\n第" + getitemnum(holder) + "条明细信息中的出发时间");
                }
            }
        }else if (holder.getKey().equals(this.getString(R.string.Return_Date))){
            HandInputGroup.Holder holder1 = group.getHolders().get(0);
            if (!holder1.getRealValue().isEmpty()){
                String returnt = holder.getRealValue();
                String leave = holder1.getRealValue();
                int getday = getday(leave + " 00:00", returnt + " 00:00");
                if (getday == -1) {
                    holder.setDispayValue("/填写到达日期");
                    ToastUtil.showToast(getContext(),"请正确选择\n第" + getitemnum(holder) + "条明细信息中的到达时间");
                }
            }
        }else if (holder.getKey().equals(this.getString(R.string.Expected_Leave_Days))){
            double v = Double.parseDouble(holder.getRealValue().isEmpty() ? "0" : holder.getRealValue());
            if (v%0.5 == 0){
                holder.setDispayValue(holder.getRealValue());
            }
        }
    }

    @Override
    public void onHolderTextChanged(int main, int index, HandInputGroup.Holder holder) {
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
            getDisplayValueByKey(this.getString(R.string.Leave_Budget)).setDispayValue(df.format(finalSum));
            notifyGroupChanged(0, 1);
            notifyGroupChanged(getGroup().size()-1, 1);
        }
    }

    @Override
    public void onClickItemContentSetter(final HandInputGroup.Holder holder) {
        mholder = holder;
        if (mholder.getType() == HandInputGroup.VALUE_TYPE.DATE) {
            if (holder.getKey().equals(this.getString(R.string.Expected_Return_Date)) || holder.getKey().equals(this.getString(R.string.Expected_Leave_Date))){
                showDateTimePicker(holder, true);
            }else {
                showDateTimePicker(holder, false);
            }
        }
        else if (mholder.getKey().equals(this.getString(R.string.Reimbursement_or_not))) {
            checkedButton(holder);
        }else if (mholder.getKey().equals(this.getString(R.string.Leave_Type))) {
            showSelector(holder,getDescr(BusinessTripType));
        }else if (mholder.getKey().equals(this.getString(R.string.Transport))) {
            showSelector(holder,getDescr(transportation));
        }
    }

    @Override
    public void onOneItemBottomDrawableResClick(int index) {
        List<HandInputGroup.Holder> holderList2 = new ArrayList<>();
        holderList2.add(new HandInputGroup.Holder(this.getString(R.string.Leave_Date), false, false, "/" + this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.DATE));
        holderList2.add(new HandInputGroup.Holder(this.getString(R.string.Return_Date), false, false, "/" + this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.DATE));
        holderList2.add(new HandInputGroup.Holder(this.getString(R.string.From), true, false, "/" + this.getString(R.string.Please_Fill), HandInputGroup.VALUE_TYPE.TEXTFILED));
        holderList2.add(new HandInputGroup.Holder(this.getString(R.string.To), true, false, "/" + this.getString(R.string.Please_Fill), HandInputGroup.VALUE_TYPE.TEXTFILED));
        holderList2.add(new HandInputGroup.Holder(this.getString(R.string.Transport), true, false, "/" + this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.SELECT));
        holderList2.add(new HandInputGroup.Holder(this.getString(R.string.Air_Ticket), false, false, "/" + this.getString(R.string.Please_Fill), HandInputGroup.VALUE_TYPE.DOUBLE));
        holderList2.add(new HandInputGroup.Holder(this.getString(R.string.Transport_Cost), false, false, "/" + this.getString(R.string.Please_Fill), HandInputGroup.VALUE_TYPE.DOUBLE));
        holderList2.add(new HandInputGroup.Holder(this.getString(R.string.Hotel_Cost), false, false, "/" + this.getString(R.string.Please_Fill), HandInputGroup.VALUE_TYPE.DOUBLE));
        holderList2.add(new HandInputGroup.Holder(this.getString(R.string.Guests_Cost), false, false, "/" + this.getString(R.string.Please_Fill), HandInputGroup.VALUE_TYPE.DOUBLE));
        holderList2.add(new HandInputGroup.Holder(this.getString(R.string.Other_Cost), false, false, "/" + this.getString(R.string.Please_Fill), HandInputGroup.VALUE_TYPE.DOUBLE));
        holderList2.add(new HandInputGroup.Holder(this.getString(R.string.Allowance), false, false, "/" + this.getString(R.string.Please_Fill), HandInputGroup.VALUE_TYPE.DOUBLE));
        addGroup(index + 1, new Group(this.getString(R.string.Details_Information), R.mipmap.add_detail3x, true, null, holderList2).sethasDelete(true));
        getGroup().get(index).sethasDelete(true).setDrawableRes(null);
        notifyDataSetChanged(index+1);
    }

    private void loadDraftListData() {
        HttpManager manager = HttpManager.getInstance();
        Map<String, Object> params = CommonValues.getCommonParams(getActivity());

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
        //公出类型
        params.put("code",2133);
        manager.requestResultForm(CommonValues.GET_DICTIONARY_LIST, params, DictionaryEntity.class, new HttpManager.ResultCallback<DictionaryEntity>() {
            @Override
            public void onSuccess(String content, DictionaryEntity expenseTypeEntity) {
                BusinessTripType = expenseTypeEntity.getRetData().get(0).getDataList();
            }

            @Override
            public void onFailure(String content) {

            }
        });

    }
}
