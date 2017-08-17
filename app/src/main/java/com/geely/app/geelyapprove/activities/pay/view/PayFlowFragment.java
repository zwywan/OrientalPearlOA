package com.geely.app.geelyapprove.activities.pay.view;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.geely.app.geelyapprove.activities.HistoryActivity;
import com.geely.app.geelyapprove.activities.login.entity.LoginEntity;
import com.geely.app.geelyapprove.activities.pay.bean.PayFlowDetailBean;
import com.geely.app.geelyapprove.common.application.GeelyApp;
import com.geely.app.geelyapprove.common.entity.AddressEntity;
import com.geely.app.geelyapprove.common.entity.AttachDeleteEntity;
import com.geely.app.geelyapprove.common.entity.AttachmentListEntity;
import com.geely.app.geelyapprove.common.entity.AttachmentSingleEntity;
import com.geely.app.geelyapprove.common.entity.CommonDataEntity;
import com.geely.app.geelyapprove.common.entity.CompanyCBPEntity;
import com.geely.app.geelyapprove.common.entity.CompanyEntity;
import com.geely.app.geelyapprove.common.entity.DataListEntity;
import com.geely.app.geelyapprove.common.entity.DictionaryEntity;
import com.geely.app.geelyapprove.common.entity.PCBBranchBanEntity;
import com.geely.app.geelyapprove.common.entity.PayeeEntity;
import com.geely.app.geelyapprove.common.fragment.CommonFragment;
import com.geely.app.geelyapprove.common.utils.CommonValues;
import com.geely.app.geelyapprove.common.utils.DataUtil;
import com.geely.app.geelyapprove.common.utils.FileUtils;
import com.geely.app.geelyapprove.common.utils.GsonUtil;
import com.geely.app.geelyapprove.common.utils.ToastUtil;
import com.geely.app.geelyapprove.common.utils.Utils;
import com.geely.app.geelyapprove.common.view.AddressDialog;
import com.geely.app.geelyapprove.common.view.FileChooserLayout;
import com.geely.app.geelyapprove.common.view.HandInputGroup;
import com.geely.app.geelyapprove.common.view.HandToolbar;
import com.geely.app.geelyapprove.datamanage.CacheManger;
import com.geely.app.geelyapprove.datamanage.HttpManager;
import com.geely.app.orientalpearl.R;

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
import static com.geely.app.geelyapprove.common.utils.DataUtil.getDicIdByDescr;
import static com.geely.app.geelyapprove.common.utils.DataUtil.getDicodeByDescr;

/**
 * Created by guiluXu on 2016/9/27.
 */
public class PayFlowFragment extends CommonFragment implements FileChooserLayout.DataChangeListener{

    private PayFlowDetailBean bean;
    private String CostCentercode;
    private String uuid;
    private String payeecode;
    private Map<String, HashSet<Uri>> paths;
    private String currentRequestorId = GeelyApp.getLoginEntity().getRetData().getUserInfo().getUserId();
    private String companycode;
    private String provinceid;
    private String cityid;
    private boolean draftFailed = false;
    private List<DataListEntity> draftPaymentType, draftBankBranch, draftCostCenter, draftPayeeBankList, companyEntities;
    private List<DataListEntity> draftProvince, PaymentAttachmentTypes, citylist;
    private LoginEntity.RetDataBean.UserInfoBean userInfo;
    private List<AttachmentListEntity> attachList;
    private DecimalFormat df;

    public PayFlowFragment(){}

    public static PayFlowFragment newInstance(){
        PayFlowFragment fragment = new PayFlowFragment();
        return fragment;
    }

    public static PayFlowFragment newInstance(Bundle bundle){
        PayFlowFragment fragment = new PayFlowFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        df = new DecimalFormat("######0.00");
        loadData();
    }

    public void setBean(PayFlowDetailBean bean) {
        this.bean = bean;
    }

    protected void loadData() {
        Map<String, Object> param = CommonValues.getCommonParams(getActivity());
        if (getArguments() == null) return;
        param.put("userId", getArguments().getString("userId"));
        param.put("barCode", getArguments().getString("barCode"));
        param.put("workflowType", getArguments().getString("workflowType"));
        HttpManager.getInstance().requestResultForm(CommonValues.REQ_PAYMENT_DETAIL, param, PayFlowDetailBean.class, new HttpManager.ResultCallback<PayFlowDetailBean>() {
            @Override
            public void onSuccess(String content, final PayFlowDetailBean entity) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (entity != null && entity.getRetData() != null) {
                            if (entity.getCode().equals("100")) {
                                setBean(entity);
                                if (getArguments().getBoolean("Remak")){
                                    settoolbar();
                                    setButtonsTitles(new String[]{"重新提交"});
                                }
                                setGroup(getGroupList());
                                setPb(false);
                                setButtonllEnable(true);
                                notifyDataSetChanged();
                                return;
                            }
                        }else {

                            Toast.makeText(getActivity(), entity.getMsg(), Toast.LENGTH_SHORT).show();

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
                        getActivity().finish();
                    }
                });

            }
        });
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
        List<Group> groups = new ArrayList<>();
        List<HandInputGroup.Holder> subHolder = new ArrayList<>();
        userInfo = GeelyApp.getLoginEntity().getRetData().getUserInfo();
        userInfo = GeelyApp.getLoginEntity().getRetData().getUserInfo();
        if(bean == null){
            uuid = UUID.randomUUID().toString();
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Company_Name), true, false, "/" + this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.SELECT));
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Cost_Center), true, true, "/" + this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.SELECT));
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Operator), true, false, userInfo.getNameCN(), HandInputGroup.VALUE_TYPE.SELECT).setValue(userInfo.getUserId()));
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Payment_Type), true, false, "/" + this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.SELECT));
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Payment_Content), true, false, "/" + this.getString(R.string.Please_Fill), HandInputGroup.VALUE_TYPE.TEXTFILED));
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Name_of_Payee), true, false, "/" + this.getString(R.string.Please_Fill), HandInputGroup.VALUE_TYPE.TEXTFILED));
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Bank), true, false, "/" + this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.SELECT));
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Bank_Account), true, false, "/" + this.getString(R.string.Please_Fill), HandInputGroup.VALUE_TYPE.DOUBLE));
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Bank_Adress), true, false, "/" + this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.SELECT));
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Branch_Bank), true, true, "/" + this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.SELECT));
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Has_Invoice), true, false, this.getString(R.string.No), HandInputGroup.VALUE_TYPE.BUTTONS));
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Invoice_Number), false, true, "", HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Invoice_Amount), false, true, "", HandInputGroup.VALUE_TYPE.DOUBLE).setEditable(false));
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Payment_Amount), true, false, "/" + this.getString(R.string.Please_Fill), HandInputGroup.VALUE_TYPE.DOUBLE));
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Has_Buget), true, true, this.getString(R.string.No), HandInputGroup.VALUE_TYPE.BUTTONS));
            groups.add(new Group(this.getString(R.string.Basic_Information), null, true, null, subHolder));
            List<HandInputGroup.Holder> subHolder1 = new ArrayList<>();
            if (paths == null) {
                paths = new HashMap<>();
                paths.put("", new HashSet<Uri>());
            }
            subHolder1.add(new HandInputGroup.Holder(this.getString(R.string.Attachment_Type), false, false, "/" + this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.SELECT));
            subHolder1.add(new HandInputGroup.Holder(this.getString(R.string.Select_Attachments), false, false, "/请上传附件", HandInputGroup.VALUE_TYPE.FILES_UPLOAD).setValue(paths.get("")));
            groups.add(new Group(this.getString(R.string.Attachment_Info), null, true, null, subHolder1));

        }else{
            uuid = bean.getRetData().getDetailData().getIdentifier();
            companycode = bean.getRetData().getDetailData().getCompany();
            CostCentercode = bean.getRetData().getDetailData().getCostCenter();
            payeecode = bean.getRetData().getDetailData().getPayeeBank();
            provinceid = bean.getRetData().getDetailData().getPayeeBankProvince();
            cityid = bean.getRetData().getDetailData().getPayeeBankCity();
            loadById(companycode);
            PayFlowDetailBean.RetDataBean.DetailDataBean detailData = bean.getRetData().getDetailData();
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Company_Name), true, false, detailData.getCompanyName(), HandInputGroup.VALUE_TYPE.SELECT));
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Cost_Center), true, true, detailData.getCostCenterName(), HandInputGroup.VALUE_TYPE.SELECT));
            currentRequestorId = detailData.getRequestorId();
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Operator), true, false, detailData.getRequestorName(), HandInputGroup.VALUE_TYPE.SELECT).setValue(currentRequestorId));
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Payment_Type), true, false, detailData.getPaymentTypeName(), HandInputGroup.VALUE_TYPE.SELECT));
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Payment_Content), true, false, detailData.getPaymentContent(), HandInputGroup.VALUE_TYPE.TEXTFILED));
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Name_of_Payee), true, false, detailData.getPayeeId(), HandInputGroup.VALUE_TYPE.TEXTFILED));
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Bank), true, false, detailData.getPayeeBankName(), HandInputGroup.VALUE_TYPE.SELECT));
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Bank_Account), true, false, detailData.getPayeeBankAccount(), HandInputGroup.VALUE_TYPE.DOUBLE));
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Bank_Adress), true, false, detailData.getPayeeBankProvinceName() + " " + detailData.getPayeeBankCityName(), HandInputGroup.VALUE_TYPE.SELECT));
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Branch_Bank), true, true, detailData.getPayeeBranchBank(), HandInputGroup.VALUE_TYPE.SELECT));
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Has_Invoice), true, false, detailData.isIsGenerateAccountingVouchers()?this.getString(R.string.Yes):this.getString(R.string.No), HandInputGroup.VALUE_TYPE.BUTTONS));
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Invoice_Number), false, true, detailData.getInvoiceNumber(), HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(detailData.isIsGenerateAccountingVouchers()));
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Invoice_Amount), false, true, detailData.getInvoiceAmount(), HandInputGroup.VALUE_TYPE.DOUBLE).setEditable(detailData.isIsGenerateAccountingVouchers()));
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Payment_Amount), false, false, detailData.getPaymentAmount(), HandInputGroup.VALUE_TYPE.DOUBLE));
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Has_Buget), true, true, detailData.isHasBuget()?this.getString(R.string.Yes):this.getString(R.string.No), HandInputGroup.VALUE_TYPE.BUTTONS));
            groups.add(new Group(this.getString(R.string.Basic_Information), null, true, null, subHolder));
            attachList = bean.getRetData().getAttchList();
            if (attachList != null && attachList.size() > 0) {
                List<HandInputGroup.Holder> temp = new ArrayList<>();
                if (paths == null) {
                    paths = new HashMap<>();
                    paths.put("attach",new HashSet<Uri>());
                }
                for (AttachmentListEntity entity : attachList) {
                    temp.add(new HandInputGroup.Holder(this.getString(R.string.Attachment_Type), false, false, entity.getFileGroupName(), HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
                    loadRemoteFiles(entity);
                }
                temp.add(new HandInputGroup.Holder<HashSet<Uri>>(this.getString(R.string.Select_Attachments), false, false, "", HandInputGroup.VALUE_TYPE.SELECT)
                        .setColor(Color.BLUE).setDrawableRight(-1).setValue(paths.get("attach")));
                groups.add(new Group(this.getString(R.string.Attachment_Info), null, false, null, temp));
            }else {
                List<HandInputGroup.Holder> holderList4 = new ArrayList<>();
                holderList4.add(new HandInputGroup.Holder(this.getString(R.string.Attachment_Type), false, false, "/" + this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.SELECT));
                if (paths == null) {
                    paths = new HashMap<>();
                    paths.put("attach",new HashSet<Uri>());
                }
                holderList4.add(new HandInputGroup.Holder(this.getString(R.string.Select_Attachments), false, false, "", HandInputGroup.VALUE_TYPE.FILES_UPLOAD).setValue(paths.get("attach")));
                groups.add(new Group(this.getString(R.string.Attachment_Info), null, false, null, holderList4));
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
                                paths.get(entity.getFileGroupName()).add(uri);
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
        toolbar.setDisplayHomeAsUpEnabled(true, getActivity());
        toolbar.setTitle("付款申请");
        loadDraftListData();
    }

    @Override
    public String[] getBottomButtonsTitles() {
        return new String[]{"提交","保存"};
    }

    @Override
    public void onClickItemContentSetter(final HandInputGroup.Holder holder) {
        if (draftFailed) return;
        String key = holder.getKey();
        if (key.equals(this.getString(R.string.Has_Invoice))){
            checkedButton(holder, new OnSelectedResultCallback() {
                @Override
                public void onSelected(Group ownGroup, HandInputGroup.Holder holder, int mainIndex, int itemIndex) {
                    if (holder.getDispayValue().equals(PayFlowFragment.this.getString(R.string.Yes))){
                        getDisplayValueByKey(PayFlowFragment.this.getString(R.string.Invoice_Number)).setEditable(true).setDispayValue("/" + PayFlowFragment.this.getString(R.string.Please_Fill)).setHasIndicator(true);
                        getDisplayValueByKey(PayFlowFragment.this.getString(R.string.Invoice_Amount)).setEditable(true).setDispayValue("/" + PayFlowFragment.this.getString(R.string.Please_Fill)).setHasIndicator(true);
                    }else if(holder.getDispayValue().equals(PayFlowFragment.this.getString(R.string.No))){
                        getDisplayValueByKey(PayFlowFragment.this.getString(R.string.Invoice_Number)).setEditable(false).setDispayValue("");
                        getDisplayValueByKey(PayFlowFragment.this.getString(R.string.Invoice_Amount)).setEditable(false).setDispayValue("");
                    }
                    notifyDataSetChanged();
                }
            });
        }else if (key.equals(this.getString(R.string.Company_Name))) {
            showSelector(holder, getDescr(companyEntities), new OnSelectedResultCallback() {
                @Override
                public void onSelected(Group ownGroup, HandInputGroup.Holder holder, int mainIndex, int itemIndex) {
                    companycode = getDicodeByDescr(companyEntities,holder.getDispayValue());
                    if (!companycode.isEmpty()) {
                        loadById(companycode);
                    }
                }
            });

        } else if (key.equals(this.getString(R.string.Cost_Center))) {
            if(companycode==null){
                ToastUtil.showToast(getContext(),this.getString(R.string.Please_Select) + "公司");
            }else{
                if(draftCostCenter==null||draftCostCenter.size()==0){
                    ToastUtil.showToast(getContext(),"该公司暂无数据");
                }else {
                    showSelector(holder, getDescr(draftCostCenter), new OnSelectedResultCallback() {
                        @Override
                        public void onSelected(Group ownGroup, HandInputGroup.Holder holder, int mainIndex, int itemIndex) {
                            CostCentercode = getDicodeByDescr(draftCostCenter,holder.getDispayValue());
                        }
                    });
                }
            }
        } else if (key.equals(this.getString(R.string.Payment_Type))) {
            showSelector(holder, getDescr(draftPaymentType));
        } else if (key.equals(this.getString(R.string.Payee_Bank_Adress))) {

            AddressDialog addressDialog = new AddressDialog(getContext())
                    .setTitle(holder.getKey())
                    .setClickListener(new AddressDialog.OnClickListener() {
                        @Override
                        public void onMainItemClickListener(final AddressDialog addressDialog, final int index, String value, final boolean hasValue) {
                            Map<String, Object> map = CommonValues.getCommonParams(getActivity());
                            map.put("provinceId",value);
                            HttpManager.getInstance().requestResultForm(CommonValues.GET_CITY_LIST, map, AddressEntity.class, new HttpManager.ResultCallback<AddressEntity>() {
                                @Override
                                public void onSuccess(String content, final AddressEntity addressEntity) {
                                    citylist = addressEntity.getRetData();
                                    Utils.runOnUIThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (citylist.size() != 0){
                                                addressDialog.updateSubAdapter(index, addressEntity.getRetData());
                                            }
                                        }
                                    });
                                }
                                @Override
                                public void onFailure(String content) {

                                }
                            });
                        }

                        @Override
                        public void onSubItemClickListener(int mainIndex, String mainValue, int subIndex, String subValue) {
                        }
                    }).setOnSuccessCallback(new AddressDialog.OnSuccessCallback() {
                        @Override
                        public void onSuccess(String province, String cityName) {
                            holder.setDispayValue(province + " " + cityName);
                            provinceid = getDicIdByDescr(draftProvince, province);
                            cityid = getDicIdByDescr(citylist, cityName);
                            getDisplayValueByKey(PayFlowFragment.this.getString(R.string.Payee_Branch_Bank)).setDispayValue("/" + PayFlowFragment.this.getString(R.string.Please_Select));
                            notifyDataSetChanged();
                        }

                        @Override
                        public void onCancel() {

                        }
                    }).setData(draftProvince,null,null);
            Window dialogWindow = addressDialog.getWindow();
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            int height = getActivity().getWindowManager().getDefaultDisplay().getHeight();
            lp.x = 0; // 新位置X坐标
            lp.y = height - 240; // 新位置Y坐标
            dialogWindow.setAttributes(lp);
            addressDialog.show();
        } else if (key.equals(this.getString(R.string.Payee_Bank))) {
            showSelector(holder, getDescr(draftPayeeBankList), new OnSelectedResultCallback() {
                @Override
                public void onSelected(Group ownGroup, HandInputGroup.Holder holder, int mainIndex, int itemIndex) {
                    payeecode = getDicIdByDescr(draftPayeeBankList,holder.getRealValue());

                }
            });
        } else if (key.equals(this.getString(R.string.Payee_Branch_Bank))) {
            if (getDisplayValueByKey(this.getString(R.string.Payee_Bank)).getRealValue().isEmpty()){
                ToastUtil.showToast(getContext(),this.getString(R.string.Please_Select) + this.getString(R.string.Payee_Bank));
            }else {
                if (getDisplayValueByKey(this.getString(R.string.Payee_Bank_Adress)).getRealValue().isEmpty()){
                    ToastUtil.showToast(getContext(),this.getString(R.string.Please_Select) + this.getString(R.string.Payee_Bank_Adress));
                }else {
                    loadPayeeBankBranch(new HttpManager.ResultCallback<PCBBranchBanEntity>() {
                        @Override
                        public void onSuccess(String content, PCBBranchBanEntity entity) {
                            draftBankBranch = entity.getRetData();
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    showSelector(holder, getDescr(draftBankBranch));
                                }
                            });

                        }

                        @Override
                        public void onFailure(String content) {

                        }
                    });
                }
            }


        } else if (key.equals(this.getString(R.string.Operator))) {
            requestForPerson(userInfo.getEid());
            notifyDataSetChanged();
        }else if (key.equals(this.getString(R.string.Attachment_Type))){
            showSelector(holder, getDescr(PaymentAttachmentTypes));
        }else if(key.equals(this.getString(R.string.Has_Buget))){
            checkedButton(holder);
        }else if (key.equals(this.getString(R.string.Select_Attachments))){
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
                draftCostCenter = companyCBPEntity.getRetData().getCostcenterList();
            }
            @Override
            public void onFailure(String content) {

            }
        });
    }

    /**
     * 加载分行数据
     *
     * @param callback 网络结果回调
     */
    private void loadPayeeBankBranch(HttpManager.ResultCallback<PCBBranchBanEntity> callback) {
        Map<String, Object> map = CommonValues.getCommonParams(getActivity());
        map.put("provienceName", getDisplayValueByKey(this.getString(R.string.Payee_Bank_Adress)).getRealValue().split(" ")[0]);
        map.put("cityName", getDisplayValueByKey(this.getString(R.string.Payee_Bank_Adress)).getRealValue().split(" ")[1]);
        map.put("payeeBankId",payeecode);
        HttpManager.getInstance().requestResultForm(CommonValues.GET_PAYEEBANKBRANCH_LIST, map, PCBBranchBanEntity.class, callback);
    }

    @Override
    public void onOneItemBottomDrawableResClick(int index) {
        ToastUtil.showToast(getContext(),index+"");
    }


    /**
     * 加载通用数据
     */
    private void loadDraftListData() {
        HttpManager manager = HttpManager.getInstance();
        Map<String, Object> params = CommonValues.getCommonParams(getActivity());
        //公司集合
        String company = CacheManger.getInstance().getData(CommonValues.GET_COMPANY_LIST);
        companyEntities = GsonUtil.parseJsonToBean(company,CompanyEntity.class).getRetData();

        //省份集合
        String address = CacheManger.getInstance().getData(CommonValues.GET_PRIVINCE_LIST);
        draftProvince = GsonUtil.parseJsonToBean(address,AddressEntity.class).getRetData();

        //银行集合
        String draft = CacheManger.getInstance().getData(CommonValues.GET_PAYEEBANK_LIST);
        draftPayeeBankList = GsonUtil.parseJsonToBean(draft,PayeeEntity.class).getRetData();

        //费用类别
        params.put("code", 3219); //付款类型
        manager.requestResultForm(CommonValues.GET_DICTIONARY_LIST, params, DictionaryEntity.class, new HttpManager.ResultCallback<DictionaryEntity>() {
            @Override
            public void onSuccess(String content, final DictionaryEntity selectDraftListEntity) {
                draftPaymentType = selectDraftListEntity.getRetData().get(0).getDataList();
                draftFailed = false;
            }

            @Override
            public void onFailure(String content) {
                draftFailed = true;
            }
        });
        //附件类别
        params.put("code", 3234); //附件信息
        manager.requestResultForm(CommonValues.GET_DICTIONARY_LIST, params, DictionaryEntity.class, new HttpManager.ResultCallback<DictionaryEntity>() {
            @Override
            public void onSuccess(String content, final DictionaryEntity selectDraftListEntity) {
                PaymentAttachmentTypes = selectDraftListEntity.getRetData().get(0).getDataList();
            }

            @Override
            public void onFailure(String content) {

            }
        });
        params.put("code", 3231); //凭证生成类型
        manager.requestResultForm(CommonValues.GET_DICTIONARY_LIST, params, DictionaryEntity.class, new HttpManager.ResultCallback<DictionaryEntity>() {
            @Override
            public void onSuccess(String content, final DictionaryEntity selectDraftListEntity) {

            }

            @Override
            public void onFailure(String content) {

            }
        });
    }

    @Override
    public void onBottomButtonsClick(String title, List<Group> groups) {
        setButtonllEnable(false);
        if (title.equals("保存")){
            ButtonClick("保存");
        }else {
            String over = isOver(groups);
            if (over == null){
                ButtonClick(title);
            }else {
                ToastUtil.showToast(getContext(),this.getString(R.string.Please_Fill) + over);
                setButtonllEnable(true);
            }
        }

    }

    private void ButtonClick(String title) {
        Map<String, Object> params = CommonValues.getCommonParams(getActivity());
        Map<String, Object> mainDataMap = new HashMap<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        if (bean == null) {
            mainDataMap.put("CreateTime", str);
            params.put("SN", "");

        } else {
            mainDataMap.put("CreateTime", bean.getRetData().getDetailData().getCreateTime());
            mainDataMap.put("Id", bean.getRetData().getDetailData().getId());
            mainDataMap.put("BarCode", getArguments().getString("barCode"));
            params.put("SN", getArguments().getString("SN"));
        }

        mainDataMap.put("UpdateTime", str);
        mainDataMap.put("Company", companycode);//公司代码
        mainDataMap.put("CostCenter", CostCentercode);// 成本中心码
        if (currentRequestorId == null || TextUtils.isEmpty(currentRequestorId)) {
            Toast.makeText(getActivity(), "经办人信息为空", Toast.LENGTH_SHORT).show();
            setButtonllEnable(true);
            return;
        }
        mainDataMap.put("RequestorId", currentRequestorId);
        mainDataMap.put("RequestorName",getDisplayValueByKey(this.getString(R.string.Operator)).getRealValue());
        mainDataMap.put("PaymentTypeName", getDisplayValueByKey(this.getString(R.string.Payment_Type)).getRealValue());
        mainDataMap.put("PaymentType", DataUtil.getDicodeByDescr(draftPaymentType, getDisplayValueByKey(this.getString(R.string.Payment_Type)).getRealValue()));
        mainDataMap.put("PaymentContent", getDisplayValueByKey(this.getString(R.string.Payment_Content)).getRealValue());
        mainDataMap.put("PayeeId", getDisplayValueByKey(this.getString(R.string.Name_of_Payee)).getRealValue());
        if (getDisplayValueByKey(this.getString(R.string.Payee_Bank)).getRealValue().isEmpty()){
            mainDataMap.put("PayeeBank", "");
        }else {
            mainDataMap.put("PayeeBank", getDicIdByDescr(draftPayeeBankList,getDisplayValueByKey(this.getString(R.string.Payee_Bank)).getRealValue()));
        }
        mainDataMap.put("PayeeBankAccount", getDisplayValueByKey(this.getString(R.string.Payee_Bank_Account)).getRealValue());
        mainDataMap.put("PayeeBankProvince", provinceid);
        mainDataMap.put("PayeeBankCity", cityid);
        if (getDisplayValueByKey(this.getString(R.string.Payee_Bank_Adress)).getRealValue().isEmpty()){
            mainDataMap.put("PayeeBankProvinceName", "");
            mainDataMap.put("PayeeBankCityName", "");
        }else {
            mainDataMap.put("PayeeBankProvinceName", getDisplayValueByKey(this.getString(R.string.Payee_Bank_Adress)).getRealValue().split(" ")[0]);
            mainDataMap.put("PayeeBankCityName", getDisplayValueByKey(this.getString(R.string.Payee_Bank_Adress)).getRealValue().split(" ")[1]);
        }
        mainDataMap.put("PayeeBranchBank", getDisplayValueByKey(this.getString(R.string.Payee_Branch_Bank)).getRealValue());
        String vouchers = getDisplayValueByKey(this.getString(R.string.Has_Invoice)).getDispayValue();
        mainDataMap.put("IsGenerateAccountingVouchers", true);
        if (vouchers.equals(this.getString(R.string.Yes))) {
            mainDataMap.put("InvoiceAmount", getDisplayValueByKey(this.getString(R.string.Invoice_Amount)).getRealValue());
            mainDataMap.put("IsInvoice", true);
            mainDataMap.put("InvoiceNumber", getDisplayValueByKey(this.getString(R.string.Invoice_Number)).getRealValue());
        } else {
            mainDataMap.put("IsInvoice", false);
        }
        mainDataMap.put("PaymentAmount", getDisplayValueByKey(this.getString(R.string.Payment_Amount)).getRealValue());
        mainDataMap.put("AuditPaymentAmount",getDisplayValueByKey(this.getString(R.string.Payment_Amount)).getRealValue());
        mainDataMap.put("CostCenter", DataUtil.getDicodeByDescr(draftCostCenter, getDisplayValueByKey(this.getString(R.string.Cost_Center)).getRealValue()));
        mainDataMap.put("Identifier", uuid);
        mainDataMap.put("SubmitBy", GeelyApp.getLoginEntity().getUserId());
        mainDataMap.put("UpdateBy",GeelyApp.getLoginEntity().getUserId());
        String s = str.split(" ")[0];
        mainDataMap.put("Summary", s.split("-")[0] + s.split("-")[1] + s.split("-")[2] + "-" + GeelyApp.getLoginEntity().getRetData().getUserInfo().getDeptNameCN() + GeelyApp.getLoginEntity().getRetData().getUserInfo().getNameCN()
                + "-" + (getDisplayValueByKey(this.getString(R.string.Payment_Amount)).getRealValue().isEmpty()?"0":getDisplayValueByKey(this.getString(R.string.Payment_Amount)).getRealValue()) + "RMB" + getDisplayValueByKey(this.getString(R.string.Payment_Content)).getRealValue());
        String budget = getDisplayValueByKey(this.getString(R.string.Has_Buget)).getRealValue();
        mainDataMap.put("HasBuget", budget.equals(this.getString(R.string.Yes)));
        String mainData = GsonUtil.parseMapToJson(mainDataMap);
        params.put("mainData", mainData);
        params.put("detailData", "[]");
        applySaveOrStart(CommonValues.REQ_PAYMENT_APPLY, params, title);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CommonValues.CODE_OA_REQUEST) {
            if (data != null) {
                String empAdname = data.getStringExtra("empAdname"); //登录名
                String empCname = data.getStringExtra("empCname");  //姓名
                String empid = data.getStringExtra("empId");//eId
                if (empAdname == null || empAdname.equals("")) {
                    Toast.makeText(getActivity(), "读取经办人信息失败", Toast.LENGTH_SHORT).show();
                    return;
                }
                currentRequestorId = empAdname;
                if (empCname != null) {
                    getDisplayValueByKey(this.getString(R.string.Operator)).setDispayValue(empCname);
                    notifyDataSetChanged();
                }
            } else {
                Toast.makeText(getActivity(), "读取人员信息失败", Toast.LENGTH_SHORT).show();
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
                map.put("fileGroupName", groupName.isEmpty()?PaymentAttachmentTypes.get(0).getDicDesc():groupName);
                map.put("workflowIdentifier", uuid);
                map.put("workflowType", CommonValues.WORKFLOW_PAYMENT);
                map.put("createBy", GeelyApp.getLoginEntity().getUserId());
                map.put("fileGroupValue", DataUtil.getDicIdByDescr(PaymentAttachmentTypes, groupName.isEmpty()?PaymentAttachmentTypes.get(0).getDicDesc():groupName));
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
