package com.geely.app.geelyapprove.activities.enterexpense.view;

import android.content.DialogInterface;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.geely.app.geelyapprove.activities.HistoryActivity;
import com.geely.app.geelyapprove.activities.enterexpense.Entity.EnterExpenseDetailEntity;
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
import com.geely.app.geelyapprove.common.view.FileChooserLayout;
import com.geely.app.geelyapprove.common.view.HandInputGroup;
import com.geely.app.geelyapprove.common.view.HandToolbar;
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

/**zhy
 * Created by Oliver on 2016/10/19.
 */
public class EnterExpenseApplyFragment extends CommonFragment implements FileChooserLayout.DataChangeListener{
    private boolean mIsDirectExpense;
    private String uuid;
    private List<DataListEntity> companyEntities,costcenterList,draftAttachmentType;
    private EnterExpenseDetailEntity bean;
    private HandInputGroup.Holder mholder;
    private String companycode;
    private String CostCentercode;
    private String payeecode;
    private LoginEntity loginEntity = GeelyApp.getLoginEntity();
    private Map<String, HashSet<Uri>> paths;
    private String provinceid;
    private String cityid;
    private LoginEntity.RetDataBean.UserAccountBean userAccountBean;
    private List<AttachmentListEntity> attachList;
    private List<LoginEntity.RetDataBean.UserAccountBean> userAccount;
    private DecimalFormat df;

    public EnterExpenseApplyFragment(){}

    public static EnterExpenseApplyFragment newInstance(Bundle bundle){
        EnterExpenseApplyFragment fragment = new EnterExpenseApplyFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public static EnterExpenseApplyFragment newInstance() {
        EnterExpenseApplyFragment fragment = new EnterExpenseApplyFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        df = new DecimalFormat("######0.00");
        loadData();
        loadDraftListData();
    }

    private void show() {
        new AlertDialog.Builder(getActivity())
                .setTitle("提示")
                .setMessage("差旅期间发生的招待费请在差旅报销流程中报销。")
                .setNegativeButton(
                        "",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(
                                    DialogInterface dialog,
                                    int which) {
                            }
                        })
                .show();
    }

    private void doData(boolean isDirect) {
        if (isDirect) {
            if (userAccount.size() == 0){
                List<HandInputGroup.Holder> subHolder1 = new ArrayList<>();
                subHolder1.add(new HandInputGroup.Holder(this.getString(R.string.Company_Name), true, false, "/" + this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.SELECT));
                subHolder1.add(new HandInputGroup.Holder(this.getString(R.string.Cost_Center), true, false, "/" + this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.SELECT));
                subHolder1.add(new HandInputGroup.Holder(this.getString(R.string.Reimbursement_Employee_ID), false, false, GeelyApp.getLoginEntity().getRetData().getUserInfo().getEid(), HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
                subHolder1.add(new HandInputGroup.Holder(this.getString(R.string.Reimbursement_Person), false, false, GeelyApp.getLoginEntity().getRetData().getUserInfo().getNameCN(), HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
                subHolder1.add(new HandInputGroup.Holder(this.getString(R.string.Grade), false, false, GeelyApp.getLoginEntity().getRetData().getUserInfo().getGrade() + "", HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
                subHolder1.add(new HandInputGroup.Holder(this.getString(R.string.Position), false, false, GeelyApp.getLoginEntity().getRetData().getUserInfo().getPositionNameCN(), HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
                subHolder1.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Employee_ID), true, false, "/" + this.getString(R.string.Please_Fill), HandInputGroup.VALUE_TYPE.TEXTFILED));
                subHolder1.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Name), true, false, "", HandInputGroup.VALUE_TYPE.TEXTFILED));
                subHolder1.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Bank), true, false, "", HandInputGroup.VALUE_TYPE.SELECT).setEditable(false));
                subHolder1.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Bank_Account), true, false, "", HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
                subHolder1.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Bank_Adress), true, false, "", HandInputGroup.VALUE_TYPE.SELECT).setEditable(false));
                subHolder1.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Branch_Bank), true, true, "", HandInputGroup.VALUE_TYPE.SELECT).setEditable(false));
                subHolder1.add(new HandInputGroup.Holder(this.getString(R.string.Has_Borrow), true, false, this.getString(R.string.No), HandInputGroup.VALUE_TYPE.BUTTONS));
                getGroup().add(new CommonFragment.Group(this.getString(R.string.Reimbursement_Info), null, true, null, subHolder1));
                List<HandInputGroup.Holder> subHolder2 = new ArrayList<>();
                subHolder2.add(new HandInputGroup.Holder(this.getString(R.string.Attachment_Type), false, false, "/" + this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.SELECT));
                if (paths == null) {
                    paths = new HashMap<>();
                    paths.put("attach", new HashSet<Uri>());
                }
                subHolder2.add(new HandInputGroup.Holder(this.getString(R.string.Select_Attachments), false, false, "/请上传附件", HandInputGroup.VALUE_TYPE.FILES_UPLOAD).setValue(paths.get("attach")));
                getGroup().add(new CommonFragment.Group(this.getString(R.string.Attachment_Info), null, true, null, subHolder2));
            }else {
                userAccountBean = userAccount.get(0);
                payeecode = userAccountBean.getPayeeBank();
                provinceid = userAccountBean.getPayeeBankProvince();
                cityid = userAccountBean.getPayeeBankCity();
                List<HandInputGroup.Holder> subHolder1 = new ArrayList<>();
                subHolder1.add(new HandInputGroup.Holder(this.getString(R.string.Reimbursement_Employee_ID), false, false, GeelyApp.getLoginEntity().getRetData().getUserInfo().getEid(), HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
                subHolder1.add(new HandInputGroup.Holder(this.getString(R.string.Reimbursement_Person), false, false, GeelyApp.getLoginEntity().getRetData().getUserInfo().getNameCN(), HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
                subHolder1.add(new HandInputGroup.Holder(this.getString(R.string.Grade), false, false, GeelyApp.getLoginEntity().getRetData().getUserInfo().getGrade() + "", HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
                subHolder1.add(new HandInputGroup.Holder(this.getString(R.string.Position), false, false, GeelyApp.getLoginEntity().getRetData().getUserInfo().getPositionNameCN(), HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
                subHolder1.add(new HandInputGroup.Holder(this.getString(R.string.Company_Name), true, false, "/" + this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.SELECT));
                subHolder1.add(new HandInputGroup.Holder(this.getString(R.string.Cost_Center), true, false, "/" + this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.SELECT));
                subHolder1.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Employee_ID), true, false, loginEntity.getRetData().getUserInfo().getEid(), HandInputGroup.VALUE_TYPE.TEXTFILED));
                subHolder1.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Name), true, false, userAccountBean.getAccountName(), HandInputGroup.VALUE_TYPE.TEXTFILED));
                subHolder1.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Bank), true, false, userAccountBean.getPayeeBankName(), HandInputGroup.VALUE_TYPE.SELECT).setEditable(false));
                subHolder1.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Bank_Account), true, false, userAccountBean.getAccountNumber(), HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
                subHolder1.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Bank_Adress), true, false, userAccountBean.getProvinceName() + " " + userAccountBean.getCityName(), HandInputGroup.VALUE_TYPE.SELECT).setEditable(false));
                subHolder1.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Branch_Bank), true, true, userAccountBean.getPayeeBranchBank(), HandInputGroup.VALUE_TYPE.SELECT).setEditable(false));
                subHolder1.add(new HandInputGroup.Holder(this.getString(R.string.Has_Borrow), true, false, this.getString(R.string.No), HandInputGroup.VALUE_TYPE.BUTTONS));
                getGroup().add(new CommonFragment.Group(this.getString(R.string.Reimbursement_Info), null, true, null, subHolder1));
                List<HandInputGroup.Holder> subHolder2 = new ArrayList<>();
                subHolder2.add(new HandInputGroup.Holder(this.getString(R.string.Attachment_Type), false, false, "/" + this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.SELECT));
                if (paths == null) {
                    paths = new HashMap<>();
                    paths.put("attach", new HashSet<Uri>());
                }
                subHolder2.add(new HandInputGroup.Holder(this.getString(R.string.Select_Attachments), false, false, "/请上传附件", HandInputGroup.VALUE_TYPE.FILES_UPLOAD).setValue(paths.get("attach")));
                getGroup().add(new CommonFragment.Group(this.getString(R.string.Attachment_Info), null, true, null, subHolder2));
            }
        } else {
            getGroup().remove(2);
            getGroup().remove(1);

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

    @Override
    public void onHolderTextChanged(int main, int index, HandInputGroup.Holder holder) {
        if (holder.getKey().equals(this.getString(R.string.Payee_Employee_ID))){
            getDisplayValueByKey(this.getString(R.string.Payee_Name)).setEditable(true);
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
                                getDisplayValueByKey(EnterExpenseApplyFragment.this.getString(R.string.Payee_Name)).setDispayValue(retDataBean.getAccountName());
                                getDisplayValueByKey(EnterExpenseApplyFragment.this.getString(R.string.Payee_Bank)).setDispayValue(retDataBean.getPayeeBankName());
                                getDisplayValueByKey(EnterExpenseApplyFragment.this.getString(R.string.Payee_Bank_Account)).setDispayValue(retDataBean.getAccountNumber());
                                getDisplayValueByKey(EnterExpenseApplyFragment.this.getString(R.string.Payee_Bank_Adress)).setDispayValue(retDataBean.getProvinceName() + " " + retDataBean.getCityName());
                                getDisplayValueByKey(EnterExpenseApplyFragment.this.getString(R.string.Payee_Branch_Bank)).setDispayValue(retDataBean.getPayeeBranchBank());
                                cityid = personalAccountEntity.getRetData().get(0).getPayeeBankCity();
                                payeecode = personalAccountEntity.getRetData().get(0).getPayeeBank();
                                notifyDataSetChanged();
                            }
                        } else {
                            ToastUtil.showToast(getContext(),personalAccountEntity.getMsg());
                            getDisplayValueByKey(EnterExpenseApplyFragment.this.getString(R.string.Payee_Name)).setDispayValue("");
                            getDisplayValueByKey(EnterExpenseApplyFragment.this.getString(R.string.Payee_Bank)).setDispayValue("");
                            getDisplayValueByKey(EnterExpenseApplyFragment.this.getString(R.string.Payee_Bank_Account)).setDispayValue("");
                            getDisplayValueByKey(EnterExpenseApplyFragment.this.getString(R.string.Payee_Bank_Adress)).setDispayValue("");
                            getDisplayValueByKey(EnterExpenseApplyFragment.this.getString(R.string.Payee_Branch_Bank)).setDispayValue("");
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
                        getDisplayValueByKey(EnterExpenseApplyFragment.this.getString(R.string.Payee_Name)).setDispayValue("");
                        getDisplayValueByKey(EnterExpenseApplyFragment.this.getString(R.string.Payee_Bank)).setDispayValue("");
                        getDisplayValueByKey(EnterExpenseApplyFragment.this.getString(R.string.Payee_Bank_Account)).setDispayValue("");
                        getDisplayValueByKey(EnterExpenseApplyFragment.this.getString(R.string.Payee_Bank_Adress)).setDispayValue("");
                        getDisplayValueByKey(EnterExpenseApplyFragment.this.getString(R.string.Payee_Branch_Bank)).setDispayValue("");
                        notifyDataSetChanged();
                    }
                });
            }
        });
    }

    @Override
    public List<Group> getGroupList() {
        List<Group> groups = new ArrayList<>();
        userAccount = loginEntity.getRetData().getUserAccount();
        if(bean == null){
            uuid = UUID.randomUUID().toString();
            List<HandInputGroup.Holder> subHolder = new ArrayList<>();
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Submitter), false, false, GeelyApp.getLoginEntity().getRetData().getUserInfo().getNameCN(), HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Number_of_Guests), true, false, "/" + this.getString(R.string.Please_Fill), HandInputGroup.VALUE_TYPE.DOUBLE));
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Hosting_expense), true, false, "/" + this.getString(R.string.Please_Fill), HandInputGroup.VALUE_TYPE.DOUBLE));
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Direct_Reimbursement), true, false, this.getString(R.string.No), HandInputGroup.VALUE_TYPE.BUTTONS));
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Has_Buget), true, false, this.getString(R.string.No), HandInputGroup.VALUE_TYPE.BUTTONS));
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Place_Of_Entertainment), true, false, "/" + this.getString(R.string.Please_Fill), HandInputGroup.VALUE_TYPE.TEXTFILED));
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Our_Participant_hosts), true, false, "/" + this.getString(R.string.Please_Fill), HandInputGroup.VALUE_TYPE.TEXTFILED));
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Entertainment_Content), true, false, "/" + this.getString(R.string.Please_Fill), HandInputGroup.VALUE_TYPE.TEXTFILED));
            groups.add(new CommonFragment.Group(this.getString(R.string.Basic_Information), null, true, null, subHolder));
        }else {
            uuid = bean.getRetData().getDetailData().getIdentifier();
            companycode = bean.getRetData().getDetailData().getCompany();
            loadById(companycode);
            CostCentercode = bean.getRetData().getDetailData().getCostCenter();
            payeecode = bean.getRetData().getDetailData().getPayeeBank();
            provinceid = bean.getRetData().getDetailData().getPayeeBankProvince();
            cityid = bean.getRetData().getDetailData().getPayeeBankCity();
            mIsDirectExpense = bean.getRetData().getDetailData().isIsDirectExpense();
            List<HandInputGroup.Holder> subHolder = new ArrayList<>();
            EnterExpenseDetailEntity.RetDataBean.DetailDataBean detailData = bean.getRetData().getDetailData();
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Submitter), false, false, GeelyApp.getLoginEntity().getRetData().getUserInfo().getNameCN(), HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Number_of_Guests), true, false, detailData.getPeopleNumber(), HandInputGroup.VALUE_TYPE.DOUBLE));
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Hosting_expense), true, false, detailData.getAmount(), HandInputGroup.VALUE_TYPE.DOUBLE));
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Direct_Reimbursement), true, false, detailData.isIsDirectExpense()?this.getString(R.string.Yes):this.getString(R.string.No), HandInputGroup.VALUE_TYPE.BUTTONS));
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Has_Buget), true, false, detailData.isHasBuget()?this.getString(R.string.Yes):this.getString(R.string.No), HandInputGroup.VALUE_TYPE.BUTTONS));
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Place_Of_Entertainment), true, false, detailData.getPlaceOfEntertainment(), HandInputGroup.VALUE_TYPE.TEXTFILED));
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Our_Participant_hosts), true, false, detailData.getOurReceptionPeople(), HandInputGroup.VALUE_TYPE.TEXTFILED));
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Entertainment_Content), true, false, detailData.getRemark().isEmpty()?("/" + this.getString(R.string.Please_Fill)):detailData.getRemark(), HandInputGroup.VALUE_TYPE.TEXTFILED));
            groups.add(new CommonFragment.Group(this.getString(R.string.Basic_Information), null, true, null, subHolder));
            if(detailData.isIsDirectExpense()){
                List<HandInputGroup.Holder> subHolder1 = new ArrayList<>();
                subHolder1.add(new HandInputGroup.Holder(this.getString(R.string.Company_Name), true, false, detailData.getCompanyName(), HandInputGroup.VALUE_TYPE.SELECT));
                subHolder1.add(new HandInputGroup.Holder(this.getString(R.string.Cost_Center), true, false, detailData.getCostCenterName(), HandInputGroup.VALUE_TYPE.SELECT));
                subHolder1.add(new HandInputGroup.Holder(this.getString(R.string.Reimbursement_Employee_ID), false, false, GeelyApp.getLoginEntity().getRetData().getUserInfo().getEid(), HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
                subHolder1.add(new HandInputGroup.Holder(this.getString(R.string.Reimbursement_Person), false, false, GeelyApp.getLoginEntity().getRetData().getUserInfo().getNameCN(), HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
                subHolder1.add(new HandInputGroup.Holder(this.getString(R.string.Grade), false, false, GeelyApp.getLoginEntity().getRetData().getUserInfo().getGrade() + "", HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
                subHolder1.add(new HandInputGroup.Holder(this.getString(R.string.Position), false, false, GeelyApp.getLoginEntity().getRetData().getUserInfo().getPositionNameCN(), HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
                subHolder1.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Employee_ID), false, false, detailData.getPayeeId(), HandInputGroup.VALUE_TYPE.TEXTFILED));
                subHolder1.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Name), false, false,detailData.getPayeeName(), HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
                subHolder1.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Bank), true, false, detailData.getPayeeBankName(), HandInputGroup.VALUE_TYPE.SELECT).setEditable(false));
                subHolder1.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Bank_Account), true, false, detailData.getPayeeBankAccount(), HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
                subHolder1.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Bank_Adress), true, false, detailData.getPayeeBankProvinceName() + " " + detailData.getPayeeBankCityName(), HandInputGroup.VALUE_TYPE.SELECT).setEditable(false));
                subHolder1.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Branch_Bank), true, true, detailData.getPayeeBranchBank(), HandInputGroup.VALUE_TYPE.SELECT).setEditable(false));
                subHolder1.add(new HandInputGroup.Holder(this.getString(R.string.Has_Borrow), true, false, detailData.isIsOffsetLoan()?this.getString(R.string.Yes):this.getString(R.string.No), HandInputGroup.VALUE_TYPE.BUTTONS));
                groups.add(1, new CommonFragment.Group(this.getString(R.string.Reimbursement_Info), null, true, null, subHolder1));
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
        toolbar.setTitle("招待费用");
        toolbar.setDisplayHomeAsUpEnabled(true, getActivity());
    }
    @Override
    public String[] getBottomButtonsTitles() {
        return new String[]{"提交", "保存"};
    }
    private void loadDraftListData() {
        HttpManager manager = HttpManager.getInstance();
        Map<String, Object> params = CommonValues.getCommonParams(getActivity());

        //公司集合
        String company = CacheManger.getInstance().getData(CommonValues.GET_COMPANY_LIST);
        companyEntities = GsonUtil.parseJsonToBean(company,CompanyEntity.class).getRetData();

        params.put("code", 3237); //附件类别
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

    private void loadData() {
        if (getArguments() != null) {
            Map<String, Object> params = CommonValues.getCommonParams(getActivity());
            params.put("userId",getArguments().getString("userId"));
            params.put("barCode", getArguments().getString("barCode"));
            params.put("workflowType", getArguments().getString("workflowType"));
            HttpManager.getInstance().requestResultForm(CommonValues.REQ_EXPENSEREQUEST_DETAIL, params, EnterExpenseDetailEntity.class, new HttpManager.ResultCallback<EnterExpenseDetailEntity>() {
                @Override
                public void onSuccess(String json, final EnterExpenseDetailEntity enterExpenseDetailEntity) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(enterExpenseDetailEntity!=null&&enterExpenseDetailEntity.getCode().equals("100")){
                                bean = enterExpenseDetailEntity;
                                if (getArguments().getBoolean("Remak")){
                                    settoolbar();
                                    setButtonsTitles(new String[]{"重新提交"});
                                }else {
                                    show();
                                }
                                setGroup(getGroupList());
                                setPb(false);
                                setButtonllEnable(true);
                                notifyDataSetChanged();
                            } else {
                                ToastUtil.showToast(getContext(),enterExpenseDetailEntity.getMsg());
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
        }else {
            show();
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

    public void onBottomButtonsClick(final String title, final List<Group> groups) {
        setButtonllEnable(false);
        String over = isOver(groups);
        if (!title.equals("保存") && over != null){
            ToastUtil.showToast(getContext(),this.getString(R.string.Please_Fill) + over);
            setButtonllEnable(true);
        }else {
            Map<String, Object> body = CommonValues.getCommonParams(getActivity());
            Map<String, Object> mainData = new HashMap<String, Object>();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date curDate = new Date(System.currentTimeMillis());//获取当前时间
            String str = formatter.format(curDate);
            if (bean == null) {
                body.put("SN", "");
            } else {
                mainData.put("Id", bean.getRetData().getDetailData().getId());
                mainData.put("BarCode", bean.getRetData().getDetailData().getBarCode());
                mainData.put("CreateTime", bean.getRetData().getDetailData().getCreateTime());
                body.put("SN", getArguments().getString("SN"));
            }
            mainData.put("Identifier", uuid);
            mainData.put("PayeeName", getDisplayValueByKey(this.getString(R.string.Submitter)).getRealValue());
            mainData.put("PeopleNumber", getDisplayValueByKey(this.getString(R.string.Number_of_Guests)).getRealValue());
            mainData.put("Amount", getDisplayValueByKey(this.getString(R.string.Hosting_expense)).getRealValue());
            mainData.put("AuditTotalAmount",getDisplayValueByKey(this.getString(R.string.Hosting_expense)).getRealValue());
            mainData.put("IsDirectExpense", getDisplayValueByKey(this.getString(R.string.Direct_Reimbursement)).getRealValue().equals(this.getString(R.string.Yes)));
            mainData.put("HasBuget", getDisplayValueByKey(this.getString(R.string.Has_Buget)).getRealValue().equals(this.getString(R.string.Yes)));
            mainData.put("PlaceOfEntertainment", getDisplayValueByKey(this.getString(R.string.Place_Of_Entertainment)).getRealValue());
            mainData.put("OurReceptionPeople", getDisplayValueByKey(this.getString(R.string.Our_Participant_hosts)).getRealValue());
            mainData.put("Remark", getDisplayValueByKey(this.getString(R.string.Entertainment_Content)).getRealValue());
            mainData.put("UpdateTime", str);
            mainData.put("PayeeId", GeelyApp.getLoginEntity().getRetData().getUserInfo().getEid());
            mainData.put("PayeeName", GeelyApp.getLoginEntity().getRetData().getUserInfo().getNameCN());
            mainData.put("SubmitBy", loginEntity.getRetData().getUserInfo().getUserId());
            mainData.put("UpdateBy", loginEntity.getRetData().getUserInfo().getUserId());
            String s = str.split(" ")[0];
            mainData.put("Summary", s.split("-")[0] + s.split("-")[1] + s.split("-")[2] + "-" + GeelyApp.getLoginEntity().getRetData().getUserInfo().getDeptNameCN() + GeelyApp.getLoginEntity().getRetData().getUserInfo().getNameCN()
                    + "-" + (getDisplayValueByKey(this.getString(R.string.Hosting_expense)).getRealValue().isEmpty() ? "0" : getDisplayValueByKey(this.getString(R.string.Hosting_expense)).getRealValue()) + "RMB招待");
            if (mIsDirectExpense) {
                mainData.put("Company", companycode);
                mainData.put("CostCenter", CostCentercode);
                mainData.put("PayeeId", getDisplayValueByKey(this.getString(R.string.Payee_Employee_ID)).getRealValue());
                mainData.put("PayeeName", getDisplayValueByKey(this.getString(R.string.Payee_Name)).getRealValue());
                mainData.put("PayeeBank", payeecode);//收款银行
                mainData.put("PayeeBankAccount", getDisplayValueByKey(this.getString(R.string.Payee_Bank_Account)).getRealValue());//收款账号
                if (getDisplayValueByKey(this.getString(R.string.Payee_Bank_Adress)).getRealValue().isEmpty()){
                    mainData.put("PayeeBankProvinceName", "");//收款银行省
                    mainData.put("PayeeBankCityName", "");//收款银行市
                }else {
                    mainData.put("PayeeBankProvinceName", getDisplayValueByKey(this.getString(R.string.Payee_Bank_Adress)).getRealValue().split(" ")[0]);//收款银行省
                    mainData.put("PayeeBankCityName", getDisplayValueByKey(this.getString(R.string.Payee_Bank_Adress)).getRealValue().split(" ")[1]);//收款银行市
                }
                mainData.put("PayeeBankProvince", provinceid);//收款银行省id
                mainData.put("PayeeBankCity", cityid);//收款银行市id
                mainData.put("PayeeBranchBank", getDisplayValueByKey(this.getString(R.string.Payee_Branch_Bank)).getRealValue());//收款支行
                mainData.put("IsOffsetLoan", getDisplayValueByKey(this.getString(R.string.Has_Borrow)).getRealValue().equals(this.getString(R.string.Yes)));//是否冲借款
                mainData.put("IsGenerateAccountingVouchers", false);
                mainData.put("IsGenerateVoucherSuccess", false);
                mainData.put("IsCompliance", false);
                mainData.put("ExpenseDetail", null);
                mainData.put("IsDirectExpense", true);
            }
            mainData.put("IsPostToPS", false);
            String main = new Gson().toJson(mainData);
            body.put("detailData", "");
            body.put("mainData", main);
            applySaveOrStart(CommonValues.REQ_EXPENSEREQUEST_APPLY, body, title);
        }
    }

    @Override
    public void onClickItemContentSetter(final HandInputGroup.Holder holder) {
        mholder = holder;
        if (TextUtils.equals(mholder.getKey(),this.getString(R.string.Has_Buget))) {
            checkedButton(holder);
        }else if(mholder.getKey().equals(this.getString(R.string.Company_Name))){
            showSelector(holder, getDescr(companyEntities), new OnSelectedResultCallback() {
                @Override
                public void onSelected(Group ownGroup, HandInputGroup.Holder holder, int mainIndex, int itemIndex) {
                    companycode = getDicodeByDescr(companyEntities,holder.getDispayValue());
                    if (!companycode.equals("")) {
                        loadById(companycode);
                    }
                }
            });
        }else if(mholder.getKey().equals(this.getString(R.string.Cost_Center))){
            if(companycode==null){
                ToastUtil.showToast(getContext(),this.getString(R.string.Please_Select) + "公司");
            }else{
                if(costcenterList==null||costcenterList.size()==0){
                    ToastUtil.showToast(getContext(),"该公司暂无数据");
                }else {
                    showSelector(holder, DataUtil.getDescr(costcenterList), new OnSelectedResultCallback() {
                        @Override
                        public void onSelected(Group ownGroup, HandInputGroup.Holder holder, int mainIndex, int itemIndex) {
                            CostCentercode = DataUtil.getDicodeByDescr(costcenterList,holder.getRealValue());
                        }
                    });
                }
            }
        }else if (mholder.getKey().equals(this.getString(R.string.Direct_Reimbursement))) {
            checkedButton(holder, new OnSelectedResultCallback() {
                @Override
                public void onSelected(Group ownGroup, HandInputGroup.Holder holder, int mainIndex, int itemIndex) {
                    if (holder.getRealValue().equals(EnterExpenseApplyFragment.this.getString(R.string.No))) {
                        doData(false);
                        mIsDirectExpense = false;
                    } else if (holder.getRealValue().equals(EnterExpenseApplyFragment.this.getString(R.string.Yes))){
                        doData(true);
                        mIsDirectExpense = true;
                        loadDraftListData();
                    }
                    notifyDataSetChanged();
                }
            });

        }else if(mholder.getKey().equals(this.getString(R.string.Attachment_Type))){
            showSelector(holder,getDescr(draftAttachmentType));
        }else if(mholder.getKey().equals(this.getString(R.string.Select_Attachments))){
            AttachmentListEntity entity = (AttachmentListEntity) holder.getValue();
            lookAttachmentData(entity);
        }
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
                map.put("workflowType", CommonValues.WORKFLOW_ENTER_EXPENSE);
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
