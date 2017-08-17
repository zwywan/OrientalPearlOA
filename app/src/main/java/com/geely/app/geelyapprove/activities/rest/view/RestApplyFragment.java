package com.geely.app.geelyapprove.activities.rest.view;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.geely.app.geelyapprove.activities.HistoryActivity;
import com.geely.app.geelyapprove.activities.login.entity.LoginEntity;
import com.geely.app.geelyapprove.activities.rest.bean.LeaveDaysOrHoursBean;
import com.geely.app.geelyapprove.activities.rest.bean.RestDetailBean;
import com.geely.app.geelyapprove.common.application.GeelyApp;
import com.geely.app.geelyapprove.common.entity.AttachDeleteEntity;
import com.geely.app.geelyapprove.common.entity.AttachmentListEntity;
import com.geely.app.geelyapprove.common.entity.AttachmentSingleEntity;
import com.geely.app.geelyapprove.common.entity.CommonDataEntity;
import com.geely.app.geelyapprove.common.entity.DataListEntity;
import com.geely.app.geelyapprove.common.entity.DictionaryEntity;
import com.geely.app.geelyapprove.common.fragment.CommonFragment;
import com.geely.app.geelyapprove.common.utils.CommonValues;
import com.geely.app.geelyapprove.common.utils.DataUtil;
import com.geely.app.geelyapprove.common.utils.DescripUtil;
import com.geely.app.geelyapprove.common.utils.FileUtils;
import com.geely.app.geelyapprove.common.utils.ToastUtil;
import com.geely.app.geelyapprove.common.view.FileChooserLayout;
import com.geely.app.geelyapprove.common.view.HandInputGroup;
import com.geely.app.geelyapprove.common.view.HandToolbar;
import com.geely.app.geelyapprove.datamanage.HttpManager;
import com.geely.app.orientalpearl.R;
import com.google.gson.Gson;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static android.view.View.VISIBLE;

/**
 * Created by guiluXu on 2016/10/12.
 */
public class RestApplyFragment extends CommonFragment implements FileChooserLayout.DataChangeListener {
    private String uuid;
    private RestDetailBean bean;
    public LeaveDaysOrHoursBean leaveDaysBean;
    private String barCode;
    private List<DataListEntity> draftRestType, draftRestDayCount, draftAttachmentType;
    private Map<String, HashSet<Uri>> fileUri;
    private String mCompNameCN, mDeptNameCN, mNameCN, mPositionNameCN, mEid;
    private String id;
    private String date;
    private List<AttachmentListEntity> attachList;

    public RestApplyFragment() {
    }

    public static RestApplyFragment newInstance() {
        RestApplyFragment restApplyFragment = new RestApplyFragment();
        return restApplyFragment;
    }

    public static RestApplyFragment newInstance(Bundle bundle) {
        RestApplyFragment fragment = new RestApplyFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadData();
        getLeaveDays();
        loadDraftData();
    }

    private void loadData() {
        if (getArguments() != null) {
            Map<String, Object> params = CommonValues.getCommonParams(getActivity());
            params.put("userId",getArguments().getString("userId"));
            params.put("barCode", getArguments().getString("barCode"));
            params.put("workflowType", getArguments().getString("workflowType"));
            HttpManager.getInstance().requestResultForm(CommonValues.REQ_REST_DETAIL, params, RestDetailBean.class, new HttpManager.ResultCallback<RestDetailBean>() {
                @Override
                public void onSuccess(String json, final RestDetailBean entity) {

                    if(entity != null && entity.getCode().equals("100")){
                        bean = entity;
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (getArguments().getBoolean("Remak")){
                                    settoolbar();
                                    setButtonsTitles(new String[]{"重新提交"});
                                }
                                setGroup(getGroupList());
                                getLeaveDays();
                                setPb(false);
                                setButtonllEnable(true);
                                notifyDataSetChanged();
                            }
                        });
                    }else {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity(),entity.getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
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
                his.setVisibility(VISIBLE);
                his.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HistoryActivity.startActivity(getActivity(),bean.getRetData().getHisData());
                    }
                });
            }
        });
    }

    private void getLeaveDays() {
        Map<String, Object> params = CommonValues.getCommonParams(getActivity());
        params.put("Eid", GeelyApp.getLoginEntity().getRetData().getUserInfo().getEid());
        if (getArguments() != null){
            params.put("barCode", getArguments().getString("barCode"));
        }else {
            params.put("barCode", "");
        }
        HttpManager.getInstance().requestResultForm(CommonValues.GET_LEAVE_REQUESTLEFTDAYS_ORHOURS_BYCODE, params, LeaveDaysOrHoursBean.class, new HttpManager.ResultCallback<LeaveDaysOrHoursBean>() {
            @Override
            public void onSuccess(String json, final LeaveDaysOrHoursBean leaveDaysOrHoursBean) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(null != leaveDaysOrHoursBean && leaveDaysOrHoursBean.getCode().equals("100")){
                            leaveDaysBean = leaveDaysOrHoursBean;
                            setGroup(getGroupList());
                            notifyDataSetChanged();
                        }else {
                            Toast.makeText(getActivity(), leaveDaysOrHoursBean.getMsg(), Toast.LENGTH_SHORT).show();
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
                    }
                });
            }
        });
    }

    @Override
    public List<Group> getGroupList() {
        mCompNameCN = GeelyApp.getLoginEntity().getRetData().getUserInfo().getCompNameCN();
        mDeptNameCN = GeelyApp.getLoginEntity().getRetData().getUserInfo().getDeptNameCN();
        mNameCN = GeelyApp.getLoginEntity().getRetData().getUserInfo().getNameCN();
        mPositionNameCN = GeelyApp.getLoginEntity().getRetData().getUserInfo().getPositionNameCN();
        mEid = GeelyApp.getLoginEntity().getRetData().getUserInfo().getEid();
        List<Group> groups = new ArrayList<>();
        if (bean == null) {
            uuid = UUID.randomUUID().toString();
            List<HandInputGroup.Holder> subHolder = new ArrayList<>();
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Company_Name), false, false, mCompNameCN, HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Department), false, false, mDeptNameCN, HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Applicant), false, false, mNameCN, HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Position), false, false, mPositionNameCN, HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Employee_ID), false, false, mEid, HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
            if (leaveDaysBean == null){
                subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Leave_Year_Days), false, false, "0", HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
                subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Lave_Hours), false, false, "0", HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
                subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Cumulative_number_of_antenatal_examination), false, false, "0", HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
                subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Total_Days), false, false, "0", HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
            }else {
                subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Leave_Year_Days), false, false, leaveDaysBean.getRetData().getAnnualDaysLeft(), HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
                subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Lave_Hours), false, false, leaveDaysBean.getRetData().getOffHoursLeft(), HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
                subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Cumulative_number_of_antenatal_examination), false, false, leaveDaysBean.getRetData().getTimesOfAntenatalTotal(), HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
                subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Total_Days), false, false, leaveDaysBean.getRetData().getDayOfLeaveTotal(), HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
            }
            groups.add(0, new Group(this.getString(R.string.Basic_Information), null, true, null, subHolder));
            List<HandInputGroup.Holder> subDetail = new ArrayList<>();
            subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Rest_Leave_Type), true, false, "/" + this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.SELECT));
            subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Starting_Time), true, false, "/" + this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.DATE));
            subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Ending_Time), true, false, "/" + this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.DATE));
            subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Leave_Time), true, false, "/0小时", HandInputGroup.VALUE_TYPE.DOUBLE).setColor(Color.rgb(229,0,17)));
            subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Leave_Causes), false, false, "/请填写请假原因", HandInputGroup.VALUE_TYPE.TEXTFILED));
            groups.add(1, new Group(this.getString(R.string.Details_Information), R.mipmap.add_detail3x, true, null, subDetail).sethasDelete(true));
            List<HandInputGroup.Holder> subDetailTotoal = new ArrayList<>();
            groups.add(2, new Group(this.getString(R.string.Total), null, false, "0天", subDetailTotoal));
            List<HandInputGroup.Holder> subAppend = new ArrayList<>();
            HandInputGroup.Holder holder = new HandInputGroup.Holder(this.getString(R.string.Attachment_Type), false, false, "/" + this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.SELECT);
            if (draftAttachmentType != null && draftAttachmentType.size() != 0){
                holder.setDispayValue(draftAttachmentType.get(0).getDicDesc());
            }
            subAppend.add(holder);
            if (fileUri == null) {
                fileUri = new HashMap<>();
                fileUri.put("attach", new HashSet<Uri>());
            }
            subAppend.add(new HandInputGroup.Holder(this.getString(R.string.Select_Attachments), false, false, "", HandInputGroup.VALUE_TYPE.FILES_UPLOAD).setValue(fileUri.get("")));
            groups.add(new Group(this.getString(R.string.Attachment_Info), null, false, null, subAppend));
        } else {
            uuid = bean.getRetData().getDetailData().getIdentifier();
            RestDetailBean.RetDataBean.DetailDataBean dataBean = bean.getRetData().getDetailData();
            List<RestDetailBean.RetDataBean.DetailDataBean.LeaveRequestDetailBean> leaveRequestDetail = dataBean.getLeaveRequestDetail();
            List<HandInputGroup.Holder> subHolder = new ArrayList<>();
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Company_Name), false, false, mCompNameCN, HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Department), false, false, mDeptNameCN, HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Applicant), false, false, mNameCN, HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Position), false, false, mPositionNameCN, HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Employee_ID), false, false, mEid, HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Leave_Year_Days), false, false, TextUtils.isEmpty(dataBean.getLaveYearDays()) ? "0" : dataBean.getLaveYearDays(), HandInputGroup.VALUE_TYPE.DOUBLE));
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Lave_Hours), false, false, TextUtils.isEmpty(dataBean.getLaveHours()) ? "0" : dataBean.getLaveHours(), HandInputGroup.VALUE_TYPE.DOUBLE));
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Cumulative_number_of_antenatal_examination), false, false, TextUtils.isEmpty(dataBean.getTotalNumber()) ? "0" : dataBean.getTotalNumber(), HandInputGroup.VALUE_TYPE.DOUBLE));
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Total_Days), false, false, TextUtils.isEmpty(dataBean.getTotalDays()) ? "0" : dataBean.getTotalDays(), HandInputGroup.VALUE_TYPE.DOUBLE));
            groups.add(0, new Group(this.getString(R.string.Basic_Information), null, true, null, subHolder));
            for (int i = 0; i < leaveRequestDetail.size(); i++) {
                List<HandInputGroup.Holder> subDetail = new ArrayList<>();
                subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Rest_Leave_Type), true, false, leaveRequestDetail.get(i).getLeaveTpyeName(), HandInputGroup.VALUE_TYPE.SELECT));
                subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Starting_Time), true, false, leaveRequestDetail.get(i).getStartTime(), HandInputGroup.VALUE_TYPE.DATE));
                subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Ending_Time), true, false, leaveRequestDetail.get(i).getEndTime(), HandInputGroup.VALUE_TYPE.DATE));
                subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Leave_Time), true, false, leaveRequestDetail.get(i).getLeaveTime() + "", HandInputGroup.VALUE_TYPE.DOUBLE).setColor(Color.rgb(229,0,17)));
                subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Leave_Causes), false, false, leaveRequestDetail.get(i).getLeaveCauses() + leaveRequestDetail.get(i).getRemark(), HandInputGroup.VALUE_TYPE.TEXTFILED));
                if (i == leaveRequestDetail.size() - 1) {
                    groups.add(new Group(this.getString(R.string.Details_Information), R.mipmap.add_detail3x, true, null, subDetail).sethasDelete(true));
                } else {
                    groups.add(new Group(this.getString(R.string.Details_Information), null, true, null, subDetail).sethasDelete(true));
                }
            }
            List<HandInputGroup.Holder> subDetailTotoal = new ArrayList<>();
            groups.add(new Group(this.getString(R.string.Total), null, false, dataBean.getTotalLeaveTime(), subDetailTotoal));
            attachList = bean.getRetData().getAttchList();
            if (attachList != null && attachList.size() > 0) {
                if (fileUri == null) {
                    fileUri = new HashMap<>();
                }
                List<HandInputGroup.Holder> subAddNoNull = new ArrayList<>();
                for (AttachmentListEntity entity : attachList) {
                    fileUri.put("attach", new HashSet<Uri>());
                    loadRemoteFiles(entity);
                }
                subAddNoNull.add(new HandInputGroup.Holder(this.getString(R.string.Attachment_Type), false, false, attachList.get(0).getFileGroupName(), HandInputGroup.VALUE_TYPE.SELECT));
                subAddNoNull.add(new HandInputGroup.Holder<HashSet<Uri>>(this.getString(R.string.Select_Attachments), false, false,"", HandInputGroup.VALUE_TYPE.FILES_UPLOAD)
                        .setColor(Color.BLUE).setDrawableRight(-1).setValue(fileUri.get("attach")).setTag(this));
                groups.add(new Group(this.getString(R.string.Attachment_Info), null, false, null, subAddNoNull));
            } else {
                List<HandInputGroup.Holder> subAddNull = new ArrayList<>();
                subAddNull.add(new HandInputGroup.Holder(this.getString(R.string.Attachment_Type), false, false, "/" + this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.SELECT));
                if (fileUri == null) {
                    fileUri = new HashMap<>();
                    fileUri.put("attach", new HashSet<Uri>());
                }
                subAddNull.add(new HandInputGroup.Holder(this.getString(R.string.Select_Attachments), false, false, "/" + this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.FILES_UPLOAD).setValue(fileUri.get("attach")));
                groups.add(new Group(this.getString(R.string.Attachment_Info), null, false, null, subAddNull));
            }
        }
        return groups;
    }

    private void loadRemoteFiles(final AttachmentListEntity entity) {
        Map<String, Object> param = CommonValues.getCommonParams(getActivity());
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
                                fileUri.get("attach").add(uri);
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


    public void setToolbar(HandToolbar toolbar) {
        toolbar.setTitle("请假调休");
        toolbar.setDisplayHomeAsUpEnabled(true, getActivity());
    }

    @Override
    public String[] getBottomButtonsTitles() {
        return new String[]{"提交", "保存"};
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
            List<Group> groupsByTitle = getGroupsByTitle(this.getString(R.string.Details_Information));
            for (Group group : groupsByTitle) {
                int getday = getday(group.getHolders().get(1).getRealValue().isEmpty() ? "0000-00-00 00:00" : group.getHolders().get(1).getRealValue(), group.getHolders().get(2).getRealValue().isEmpty() ? "0000-00-00 00:00" : group.getHolders().get(2).getRealValue());
                if (group.getHolders().get(0).getRealValue().equals("调休")){
                    if(Double.parseDouble(group.getHolders().get(3).getRealValue().isEmpty()?"0":group.getHolders().get(3).getRealValue())%4 != 0){
                        ToastUtil.showToast(getContext(),"调休时长必须是4的整数倍！");
                        setButtonllEnable(true);
                        return;
                    }
                    if (getday<Double.parseDouble(group.getHolders().get(3).getRealValue().isEmpty()?"0":group.getHolders().get(3).getRealValue())){
                        ToastUtil.showToast(getContext(),"调休时长数不能超过所选时间！");
                        setButtonllEnable(true);
                        return;
                    }
                }else {
                    if(Double.parseDouble(group.getHolders().get(3).getRealValue().isEmpty()?"0":group.getHolders().get(3).getRealValue())*4%2 != 0){
                        ToastUtil.showToast(getContext(),"请假时长必须是0.5的整数倍！");
                        setButtonllEnable(true);
                        return;
                    }
                    if (getday<Double.parseDouble(group.getHolders().get(3).getRealValue().isEmpty()?"0":group.getHolders().get(3).getRealValue())*8){
                        ToastUtil.showToast(getContext(),"请假时长数不能超过所选时间！");
                        setButtonllEnable(true);
                        return;
                    }
                }

            }
            Map<String, Object> body = CommonValues.getCommonParams(getActivity());
            Map<String, Object> mainData = new HashMap<String, Object>();
            ArrayList<Map> details = new ArrayList<>();
            LoginEntity.RetDataBean.UserInfoBean userInfo = GeelyApp.getLoginEntity().getRetData().getUserInfo();
            if (bean == null) {
                barCode = "";
                date = DescripUtil.formatDate(new Date());
                id = "0";
                body.put("SN", "");

            } else {
                barCode = getArguments().getString("barCode");
                id = bean.getRetData().getDetailData().getId();
                date = bean.getRetData().getDetailData().getCreateTime();
                mainData.put("Id",id);//1
                mainData.put("BarCode", barCode);//2
                body.put("SN", getArguments().getString("SN"));
            }

            mainData.put("Identifier", uuid);//3
            mainData.put("Company", userInfo.getCompNameCN());//5
            mainData.put("Position", userInfo.getPositionNameCN());//6
            mainData.put("EmployeeID", userInfo.getEid());//7
            mainData.put("Department", userInfo.getDeptNameCN());//8
            mainData.put("SubmitBy", userInfo.getUserId());//9
            mainData.put("UpdateBy",userInfo.getUserId());
            String rightTitle = getGroupsByTitle(this.getString(R.string.Total)).get(0).getGroupTopRightTitle();
            if (rightTitle.endsWith("小时")){
                mainData.put("TotalLeaveTime",rightTitle.substring(0,rightTitle.length()-2));//10
            }else if (rightTitle.endsWith("天")){
                mainData.put("TotalLeaveTime",rightTitle.substring(0,rightTitle.length()-1));
            }else {
                mainData.put("TotalLeaveTime",rightTitle);//10
            }
            mainData.put("Applicant", userInfo.getNameCN());//11
            mainData.put("UpdateTime", DescripUtil.formatDate(new Date()));//12
            mainData.put("CreateTime",date);//13
            String s = date.split(" ")[0];
            mainData.put("Summary", s.split("-")[0] + s.split("-")[1] + s.split("-")[2] + "-" + GeelyApp.getLoginEntity().getRetData().getUserInfo().getDeptNameCN() + GeelyApp.getLoginEntity().getRetData().getUserInfo().getNameCN()
                    + "-" + rightTitle + "请假/调休");//14
            mainData.put("UpdateBy", userInfo.getUserId());//15
            mainData.put("IsSelf",false);//16
            List<HandInputGroup.Holder> holders = groups.get(0).getHolders();
            if (holders.get(5).getRealValue().isEmpty() || Double.parseDouble(holders.get(5).getRealValue()) == 0){
                mainData.put("laveYearDays","0");
            }else {
                mainData.put("laveYearDays", holders.get(5).getRealValue());//调休剩余小时数//19
            }
            if (holders.get(6).getRealValue().isEmpty() || Double.parseDouble(holders.get(6).getRealValue()) == 0){
                mainData.put("laveHours","0");
            }else {
                mainData.put("laveHours", holders.get(6).getRealValue());
            }
            if (holders.get(7).getRealValue().isEmpty() || Double.parseDouble(holders.get(7).getRealValue()) == 0){
                mainData.put("totalNumber","0");
            }else {
                mainData.put("totalNumber", holders.get(7).getRealValue());//累积产前检查次数//19
            }
            if (holders.get(8).getRealValue().isEmpty() || Double.parseDouble(holders.get(8).getRealValue()) == 0){
                mainData.put("totalDays","0");
            }else {
                mainData.put("totalDays", holders.get(8).getRealValue());//累计事假天数//19
            }
            double num = 0;
            double sum = 0;
            if (bean != null){
                for (int i = 1; i < groups.size() - 2; i++) {
                    Map<String, Object> detail = new HashMap<>();
                    if (groups.get(i).getTitle().equals(this.getString(R.string.Details_Information))){
                        detail.put("LeaveTpyeName",groups.get(i).getHolders().get(0).getRealValue());
                    }
                    if (bean.getRetData().getDetailData().getLeaveRequestDetail().isEmpty()){
                        detail.put("Id","0");
                    }else{
                        if(i < bean.getRetData().getDetailData().getLeaveRequestDetail().size() + 1){
                            detail.put("Id",bean.getRetData().getDetailData().getLeaveRequestDetail().get(i-1).getId());//1
                        }else {
                            detail.put("Id","0");
                        }
                    }
                    detail.put("WorkflowIdentifier", uuid);//2
                    if (groups.get(i).getTitle().endsWith(this.getString(R.string.Details_Information))){
                        String type = groups.get(i).getHolders().get(0).getRealValue();
                        if (type.equals("调休")){
                            sum += Double.parseDouble(groups.get(i).getHolders().get(3).getRealValue().equals("") ? "0" : groups.get(i).getHolders().get(3).getRealValue());
                        }else if (type.equals("年假请假")){
                            num += Double.parseDouble(groups.get(i).getHolders().get(3).getRealValue().equals("") ? "0" : groups.get(i).getHolders().get(3).getRealValue());
                        }
                        detail.put("LeaveType", DataUtil.getDicodeByDescr(draftRestType, type));//请假类别//3
                        String fromDate = groups.get(i).getHolders().get(1).getRealValue();
                        detail.put("StartTime", fromDate);//开始时间//4
                        String toDate = groups.get(i).getHolders().get(2).getRealValue();
                        detail.put("EndTime", toDate);//结束时间//5
                        detail.put("LeaveTime", groups.get(i).getHolders().get(3).getRealValue().equals("") ? "0" : groups.get(i).getHolders().get(3).getRealValue());//请假时数//6
                        detail.put("LeaveCauses", groups.get(i).getHolders().get(4).getRealValue().equals("") ? "" : groups.get(i).getHolders().get(4).getRealValue());//请假原因//7
                    }
                    detail.put("Remark","");//8
                    detail.put("IsPostToPs", false);//9
                    if (sum > Double.parseDouble(getDisplayValueByKey(this.getString(R.string.Lave_Hours)).getRealValue().isEmpty()?"0":getDisplayValueByKey(this.getString(R.string.Lave_Hours)).getRealValue())){
                        ToastUtil.showToast(getContext(),"调休时间不能大于调休剩余小时数");
                        setButtonllEnable(true);
                        return;
                    }
                    if (num > Double.parseDouble(getDisplayValueByKey(this.getString(R.string.Leave_Year_Days)).getRealValue().isEmpty()?"0":getDisplayValueByKey(this.getString(R.string.Leave_Year_Days)).getRealValue())){
                        ToastUtil.showToast(getContext(),"请年假时间不能大于剩余年假天数");
                        setButtonllEnable(true);
                        return;
                    }
                    details.add(detail);
                }
            }else{
                for (int i = 1; i < groups.size() - 2; i++) {
                    Map<String, Object> detail = new HashMap<>();
                    if (groups.get(i).getTitle().equals(this.getString(R.string.Details_Information))){
                        detail.put("LeaveTpyeName",groups.get(i).getHolders().get(0).getRealValue());
                    }
                    detail.put("Id","0");
                    detail.put("WorkflowIdentifier", uuid);//2
                    if (groups.get(i).getTitle().endsWith(this.getString(R.string.Details_Information))){
                        String type = groups.get(i).getHolders().get(0).getRealValue();
                        if (type.equals("调休")){
                            sum += Double.parseDouble(groups.get(i).getHolders().get(3).getRealValue().equals("") ? "0" : groups.get(i).getHolders().get(3).getRealValue());
                        }else if (type.equals("年假请假")){
                            num += Double.parseDouble(groups.get(i).getHolders().get(3).getRealValue().equals("") ? "0" : groups.get(i).getHolders().get(3).getRealValue());
                        }
                        detail.put("LeaveType", DataUtil.getDicodeByDescr(draftRestType, type));//请假类别//3
                        String fromDate = groups.get(i).getHolders().get(1).getRealValue();
                        detail.put("StartTime", fromDate);//开始时间//4
                        String toDate = groups.get(i).getHolders().get(2).getRealValue();
                        detail.put("EndTime", toDate);//结束时间//5
                        detail.put("LeaveTime", groups.get(i).getHolders().get(3).getRealValue().equals("") ? "0" : groups.get(i).getHolders().get(3).getRealValue());//请假时数//6
                        detail.put("LeaveCauses", groups.get(i).getHolders().get(4).getRealValue().equals("") ? "" : groups.get(i).getHolders().get(4).getRealValue());//请假原因//7
                    }
                    detail.put("Remark","");//8
                    detail.put("IsPostToPs", false);//9
                    if (sum > Double.parseDouble(getDisplayValueByKey(this.getString(R.string.Lave_Hours)).getRealValue().isEmpty()?"0":getDisplayValueByKey(this.getString(R.string.Lave_Hours)).getRealValue())){
                        ToastUtil.showToast(getContext(),"调休时间不能大于调休剩余小时数");
                        setButtonllEnable(true);
                        return;
                    }
                    if (num > Double.parseDouble(getDisplayValueByKey(this.getString(R.string.Leave_Year_Days)).getRealValue().isEmpty()?"0":getDisplayValueByKey(this.getString(R.string.Leave_Year_Days)).getRealValue())){
                        ToastUtil.showToast(getContext(),"请年假时间不能大于剩余年假天数");
                        setButtonllEnable(true);
                        return;
                    }
                    details.add(detail);
                }
            }
            body.put("mainData", new Gson().toJson(mainData));
            body.put("detailData", new Gson().toJson(details));
            body.put("transmissionId", "");
            applySaveOrStart(CommonValues.REQ_REST_APPLY, body, title);
        }
    }


    @Override
    public void onClickItemContentSetter(final HandInputGroup.Holder holder) {
        if (holder.getType() == HandInputGroup.VALUE_TYPE.DATE) {
            showDateTimePicker(holder,true);
        } else if (holder.getType() == HandInputGroup.VALUE_TYPE.SELECT) {
            if (holder.getKey().equals(this.getString(R.string.Rest_Leave_Type))) {
                if (draftRestType != null) {
                    showSelector(holder, DataUtil.getDescr(draftRestType), new OnSelectedResultCallback() {
                        @Override
                        public void onSelected(Group ownGroup, HandInputGroup.Holder holder, int mainIndex, int itemIndex) {
                            HandInputGroup.Holder holder1 = ownGroup.getHolders().get(3);
                            if (holder.getRealValue().equals("调休")){
                                holder1.setDispayValue("/0小时");
                            }else {
                                holder1.setDispayValue("/0天");
                            }
                            notifyDataSetChanged();
                        }
                    });
                } else {
                    ToastUtil.showToast(getContext(),"拉取失败");
                }
            } else if (holder.getKey().equals("天数")) {
                if (draftRestDayCount != null){
                    showSelector(holder, DataUtil.getDescr(draftRestDayCount));
                }else{
                    ToastUtil.showToast(getContext(),"拉取失败");
                }
            } else if (holder.getKey().equals(this.getString(R.string.Attachment_Type))) {
                showSelector(holder, DataUtil.getDescr(draftAttachmentType));
            }
        }else if (holder.getKey().equals(this.getString(R.string.Select_Attachments))) {
            String realValue = getDisplayValueByKey(this.getString(R.string.Attachment_Type)).getRealValue();
            if (realValue.isEmpty()){
                ToastUtil.showToast(getContext(),"请选择附件分组");
            }else {
                AttachmentListEntity entity = (AttachmentListEntity) holder.getValue();
                lookAttachmentInDetailFragment(entity);
            }

        }

    }

    @Override
    public void onHolderTextChanged(int main, int index, HandInputGroup.Holder holder) {
        final String key = holder.getKey();
        if (key.equals(this.getString(R.string.Leave_Time))){
            double sum = 0;
            List<Group> groupsByTitle = getGroupsByTitle(this.getString(R.string.Details_Information));
            for (Group group : groupsByTitle) {
                double v = 0;
                if (group.getHolders().get(0).getRealValue().equals("调休")){
                    v = Double.parseDouble(group.getHolders().get(3).getRealValue().isEmpty() ? "0" : group.getHolders().get(3).getRealValue())/8.0;
                }else {
                    v = Double.parseDouble(group.getHolders().get(3).getRealValue().isEmpty() ? "0" : group.getHolders().get(3).getRealValue());
                }
                sum += v;
            }
            getGroupsByTitle(this.getString(R.string.Total)).get(0).setGroupTopRightTitle(sum + "天");
            notifyGroupChanged(getGroup().size()-2, 1);
        }
    }

    @Override
    public void onDataChanged(HandInputGroup.Holder holder) throws ParseException {
        int getitemnum = getitemnum(holder);
        Group group = getGroup().get(getitemnum);
        if (holder.getKey().equals(this.getString(R.string.Starting_Time))){
            HandInputGroup.Holder holder1 = group.getHolders().get(2);
            if (!holder1.getRealValue().isEmpty()){
                String starttime = holder.getRealValue();
                String overtime = holder1.getRealValue();
                int getday = getday(starttime, overtime);
                if (getday == -1){
                    holder.setDispayValue("/" + this.getString(R.string.Please_Select));
                    ToastUtil.showToast(getContext(),"请正确选择第" + getitemnum+ "条明细信息中的开始、结束时间");
                }
            }
        }else if (holder.getKey().equals(this.getString(R.string.Ending_Time))){
            HandInputGroup.Holder holder1 = group.getHolders().get(1);
            if (!holder1.getRealValue().isEmpty()){
                String starttime = holder1.getRealValue();
                String overtime = holder.getRealValue();
                int getday = getday(starttime, overtime);
                if (getday == -1){
                    holder.setDispayValue("/" + this.getString(R.string.Please_Select));
                    ToastUtil.showToast(getContext(),"请正确选择第" + getitemnum+ "条明细信息中的开始、结束时间");
                }
            }
        }
    }


    private void loadDraftData() {
        HttpManager manager = HttpManager.getInstance();
        Map<String, Object> params = CommonValues.getCommonParams(getActivity());
        //请假类别
        params.put("code", "2124");
        manager.requestResultForm(CommonValues.GET_DICTIONARY_LIST, params, DictionaryEntity.class, new HttpManager.ResultCallback<DictionaryEntity>() {
            @Override
            public void onSuccess(String content, final DictionaryEntity selectDraftListEntity) {
                draftRestType = selectDraftListEntity.getRetData().get(0).getDataList();
            }
            @Override
            public void onFailure(String content) {
            }
        });
        //请假天数
        params.put("code", "3176");
        manager.requestResultForm(CommonValues.GET_DICTIONARY_LIST, params, DictionaryEntity.class, new HttpManager.ResultCallback<DictionaryEntity>() {
            @Override
            public void onSuccess(String content, final DictionaryEntity selectDraftListEntity) {
                draftRestDayCount = selectDraftListEntity.getRetData().get(0).getDataList();
            }
            @Override
            public void onFailure(String content) {
            }
        });
        //附件类型
        params.put("code", "2169");
        manager.requestResultForm(CommonValues.GET_DICTIONARY_LIST, params, DictionaryEntity.class, new HttpManager.ResultCallback<DictionaryEntity>() {
            @Override
            public void onSuccess(String content, final DictionaryEntity selectDraftListEntity) {
                draftAttachmentType = selectDraftListEntity.getRetData().get(0).getDataList();
            }
            @Override
            public void onFailure(String content) {
            }
        });
    }

    @Override
    public void onOneItemBottomDrawableResClick(int index) {
        List<HandInputGroup.Holder> subHolder1 = new ArrayList<>();
        subHolder1.add(new HandInputGroup.Holder(this.getString(R.string.Rest_Leave_Type), true, false, "/" + this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.SELECT));
        subHolder1.add(new HandInputGroup.Holder(this.getString(R.string.Starting_Time), true, false, "/" + this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.DATE));
        subHolder1.add(new HandInputGroup.Holder(this.getString(R.string.Ending_Time), true, false, "/" + this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.DATE));
        subHolder1.add(new HandInputGroup.Holder(this.getString(R.string.Leave_Time),true, false, "/天", HandInputGroup.VALUE_TYPE.DOUBLE).setColor(Color.rgb(229,0,17)));
        subHolder1.add(new HandInputGroup.Holder(this.getString(R.string.Leave_Causes), false, false, "/" + this.getString(R.string.Please_Fill), HandInputGroup.VALUE_TYPE.TEXTFILED));
        addGroup(index + 1, new Group(this.getString(R.string.Details_Information), R.mipmap.add_detail3x, true, null, subHolder1).sethasDelete(true));
        getGroup().get(index).setDrawableRes(null);
        notifyDataSetChanged();
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
                map.put("workflowType", CommonValues.WORKFLOW_REST);
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