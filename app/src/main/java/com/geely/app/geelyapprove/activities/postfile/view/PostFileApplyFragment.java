package com.geely.app.geelyapprove.activities.postfile.view;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.geely.app.geelyapprove.activities.HistoryActivity;
import com.geely.app.geelyapprove.activities.login.entity.LoginEntity;
import com.geely.app.geelyapprove.activities.postfile.entity.ComlianceAdministratorBean;
import com.geely.app.geelyapprove.activities.postfile.entity.PostFileEntity;
import com.geely.app.geelyapprove.common.application.GeelyApp;
import com.geely.app.geelyapprove.common.entity.AttachmentListEntity;
import com.geely.app.geelyapprove.common.entity.DataListEntity;
import com.geely.app.geelyapprove.common.entity.DictionaryEntity;
import com.geely.app.geelyapprove.common.fragment.CommonFragment;
import com.geely.app.geelyapprove.common.utils.CommonValues;
import com.geely.app.geelyapprove.common.utils.DataUtil;
import com.geely.app.geelyapprove.common.utils.GsonUtil;
import com.geely.app.geelyapprove.common.utils.ToastUtil;
import com.geely.app.geelyapprove.common.view.HandInputGroup;
import com.geely.app.geelyapprove.common.view.HandToolbar;
import com.geely.app.geelyapprove.datamanage.HttpManager;
import com.geely.app.orientalpearl.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.geely.app.geelyapprove.common.utils.DataUtil.getDicIdByDescr;

/**
 * Created by zhy on 2016/11/25.
 */

public class PostFileApplyFragment extends CommonFragment {

    private HashSet<Uri> paths;
    private PostFileEntity bean;
    private List<DataListEntity> ReleaseModeList, FileTypeList, DenseList;
    private String uuid;
    private String barcode;
    private String id;
    private LoginEntity.RetDataBean.UserInfoBean userInfo;
    private ComlianceAdministratorBean.RetDataBean comliance;
    private List<AttachmentListEntity> attachList;

    public PostFileApplyFragment(){}
    public static PostFileApplyFragment newInstance(){
        PostFileApplyFragment fragment = new PostFileApplyFragment();
        return fragment;
    }
    public static PostFileApplyFragment newInstance(Bundle bundle){
        PostFileApplyFragment fragment = new PostFileApplyFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadData();
    }

    public void setBean(PostFileEntity bean) {
        this.bean = bean;
    }

    protected void loadData() {
        Map<String, Object> param = CommonValues.getCommonParams(getActivity());
        if (getArguments() == null) return;
        param.put("userId", getArguments().getString("userId"));
        param.put("barCode", getArguments().getString("barCode"));
        param.put("workflowType", getArguments().getString("workflowType"));
        HttpManager.getInstance().requestResultForm(CommonValues.REQ_POSTFILE_DETAIL, param, PostFileEntity.class, new HttpManager.ResultCallback<PostFileEntity>() {
            @Override
            public void onSuccess(String content, final PostFileEntity entity) {
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
                                notifyDataSetChanged();
                                return;
                            }
                        }
                        Toast.makeText(getActivity(), "数据错误", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(String content) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "拉取数据失败", Toast.LENGTH_SHORT).show();
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
        if(bean == null){
            subHolder.add(new HandInputGroup.Holder("秘密等级", true, false, "/" + this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.SELECT));
            subHolder.add(new HandInputGroup.Holder("公司类型", false, false, userInfo.getCompCode().endsWith("000")?"总公司":"子公司", HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
            subHolder.add(new HandInputGroup.Holder("文件名称", true, false, "/请填写", HandInputGroup.VALUE_TYPE.TEXTFILED));
            subHolder.add(new HandInputGroup.Holder("文件类别", true, false, "/" + this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.SELECT));
            subHolder.add(new HandInputGroup.Holder("发布方式", true, false, "/" + this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.SELECT));
            subHolder.add(new HandInputGroup.Holder("发布范围", true, false, "/请填写", HandInputGroup.VALUE_TYPE.TEXTFILED));
            subHolder.add(new HandInputGroup.Holder("部门", false, false, userInfo.getDeptNameCN(), HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
            subHolder.add(new HandInputGroup.Holder("提交人", false, true, userInfo.getNameCN(), HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
            subHolder.add(new HandInputGroup.Holder("发布目标", true, false, userInfo.getNameCN(), HandInputGroup.VALUE_TYPE.SELECT));
            groups.add(new Group("基本信息", null, true, null, subHolder));
            List<HandInputGroup.Holder> subHolder1 = new ArrayList<>();
            if (paths == null) {
                paths = new HashSet<>();
            }
            subHolder1.add(new HandInputGroup.Holder("附件分组", false, false, "/" + this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.SELECT));
            subHolder1.add(new HandInputGroup.Holder("上传附件", false, false, "/请上传附件", HandInputGroup.VALUE_TYPE.FILES_UPLOAD).setValue(paths));
            groups.add(new Group("附件信息", null, true, null, subHolder1));

        }else{
            PostFileEntity.RetDataBean.DetailDataBean detailData = bean.getRetData().getDetailData();
            subHolder.add(new HandInputGroup.Holder("秘密等级", true, false, detailData.getDenseName(), HandInputGroup.VALUE_TYPE.SELECT));
            subHolder.add(new HandInputGroup.Holder("公司类型", false, false, userInfo.getCompCode().endsWith("000")?"总公司":"子公司", HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
            subHolder.add(new HandInputGroup.Holder("文件名称", true, false, detailData.getFileName(), HandInputGroup.VALUE_TYPE.TEXTFILED));
            subHolder.add(new HandInputGroup.Holder("文件类别", true, false, detailData.getFileTypeName(), HandInputGroup.VALUE_TYPE.SELECT));
            subHolder.add(new HandInputGroup.Holder("发布方式", true, false, detailData.getReleaseModeName(), HandInputGroup.VALUE_TYPE.SELECT));
            subHolder.add(new HandInputGroup.Holder("发布范围", true, false, detailData.getReleaseRange(), HandInputGroup.VALUE_TYPE.TEXTFILED));
            subHolder.add(new HandInputGroup.Holder("部门", false, false, userInfo.getDeptNameCN(), HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
            subHolder.add(new HandInputGroup.Holder("提交人", false, true, userInfo.getNameCN(), HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));
            if(detailData.getReleaseModeName().equals("邮件发布")){
                subHolder.add(new HandInputGroup.Holder("是否总裁签发", true, false, detailData.isPresidentIssued()?"是":"否", HandInputGroup.VALUE_TYPE.SELECT));
            }
            if(detailData.getReleaseModeName().equals("红头文件")){
                subHolder.add(new HandInputGroup.Holder("是否组织架构调整", true, false, detailData.isOrganize()?"是":"否", HandInputGroup.VALUE_TYPE.SELECT));
            }
            subHolder.add(new HandInputGroup.Holder("发布目标", true, false, detailData.getReleaseTargetName(), HandInputGroup.VALUE_TYPE.SELECT));
            groups.add(new Group("基本信息", null, true, null, subHolder));
            attachList = bean.getRetData().getAttchList();
            if (attachList != null && attachList.size() > 0) {
                for (AttachmentListEntity entity : attachList) {
                    List<HandInputGroup.Holder> temp = new ArrayList<>();
                    temp.add(new HandInputGroup.Holder("附件分组", false, false, entity.getFileGroupName(), HandInputGroup.VALUE_TYPE.TEXTFILED).setEditable(false));

                    temp.add(new HandInputGroup.Holder<HashSet<Uri>>("上传附件", false, false, entity.getFileName() + entity.getFileExtension(), HandInputGroup.VALUE_TYPE.SELECT)
                            .setColor(Color.BLUE).setDrawableRight(-1).setValue(entity));
                    groups.add(new Group("附件信息", null, false, null, temp));
                }
            }
        }

        return groups;
    }

    public void setToolbar(HandToolbar toolbar) {
        toolbar.setDisplayHomeAsUpEnabled(true, getActivity());
        toolbar.setTitle("发文流程");
        loadDraftListData();
    }

    private void loadDraftListData() {
        HttpManager manager = HttpManager.getInstance();
        Map<String, Object> params = CommonValues.getCommonParams(getActivity());
        //合规管理员
        manager.requestResultForm(CommonValues.GET_GET_COMLIANCE_LIST, params, ComlianceAdministratorBean.class, new HttpManager.ResultCallback<ComlianceAdministratorBean>() {
            @Override
            public void onSuccess(String content, ComlianceAdministratorBean companyEntity) {
                comliance = companyEntity.getRetData().get(0);
            }

            @Override
            public void onFailure(String content) {

            }
        });

        //发布方式
        params.put("code",2179);
        manager.requestResultForm(CommonValues.GET_DICTIONARY_LIST, params, DictionaryEntity.class, new HttpManager.ResultCallback<DictionaryEntity>() {
            @Override
            public void onSuccess(String content, DictionaryEntity companyEntity) {
                ReleaseModeList = companyEntity.getRetData().get(0).getDataList();
            }

            @Override
            public void onFailure(String content) {

            }
        });

        //文件类别
        params.put("code",2178);
        manager.requestResultForm(CommonValues.GET_DICTIONARY_LIST, params, DictionaryEntity.class, new HttpManager.ResultCallback<DictionaryEntity>() {
            @Override
            public void onSuccess(String content, DictionaryEntity companyEntity) {
                FileTypeList = companyEntity.getRetData().get(0).getDataList();
            }

            @Override
            public void onFailure(String content) {

            }
        });

        //秘密等级
        params.put("code",2174);
        manager.requestResultForm(CommonValues.GET_DICTIONARY_LIST, params, DictionaryEntity.class, new HttpManager.ResultCallback<DictionaryEntity>() {
            @Override
            public void onSuccess(String content, DictionaryEntity companyEntity) {
                DenseList = companyEntity.getRetData().get(0).getDataList();
            }

            @Override
            public void onFailure(String content) {

            }
        });
    }

    @Override
    public void onClickItemContentSetter(HandInputGroup.Holder holder) {
        if (holder.getKey().equals("发布方式")){
            showSelector(holder, DataUtil.getDescr(ReleaseModeList), new OnSelectedResultCallback() {
                @Override
                public void onSelected(Group ownGroup, HandInputGroup.Holder holder, int mainIndex, int itemIndex) {
                    if (holder.getRealValue().equals("邮件发布")){
                        if(ownGroup.getHolders().size()==10){
                            ownGroup.getHolders().add(9,new HandInputGroup.Holder("是否总裁签发", true, false, "/" + PostFileApplyFragment.this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.SELECT));
                        }else {
                            ownGroup.getHolders().get(9).setDispayValue("/" + PostFileApplyFragment.this.getString(R.string.Please_Select)).setKey("是否总裁签发");
                        }
                    }else if(holder.getRealValue().equals("一般文件挂OA")){
                        if (ownGroup.getHolders().size() > 10){
                            ownGroup.getHolders().remove(9);
                        }
                    }else if(holder.getRealValue().equals("红头文件")){
                        if(ownGroup.getHolders().size()==10){
                            ownGroup.getHolders().add(9,new HandInputGroup.Holder("是否组织架构调整", true, false, "/" + PostFileApplyFragment.this.getString(R.string.Please_Select), HandInputGroup.VALUE_TYPE.SELECT));
                        }else {
                            ownGroup.getHolders().get(9).setDispayValue("/" + PostFileApplyFragment.this.getString(R.string.Please_Select)).setKey("是否组织架构调整");
                        }
                    }
                }
            });
        }else if(holder.getKey().equals("文件类别")){
            showSelector(holder, DataUtil.getDescr(FileTypeList));
        }else if(holder.getKey().equals("秘密等级")){
            showSelector(holder, DataUtil.getDescr(DenseList));
        }else if (holder.getKey().equals("是否总裁签发")||holder.getKey().equals("是否组织架构调整")){
            showSelector(holder,new String[]{"是","否"});
        }else if (holder.getKey().equals("附件分组")){
            showSelector(holder, DataUtil.getDescr(FileTypeList));
        }else if (holder.getKey().equals("上传附件")){
            AttachmentListEntity entity = (AttachmentListEntity) holder.getValue();
            lookAttachmentData(entity);
        }
    }

    @Override
    public String[] getBottomButtonsTitles() {
        return new String[]{"提交","保存"};
    }

    @Override
    public void onBottomButtonsClick(String title, List<Group> groups) {
        setButtonllEnable(false);
        String over = isOver(groups);
        if(over != null && !title.equals("保存")){
            ToastUtil.showToast(getContext(),"请填写" + over);
            setButtonllEnable(true);
        }else {
            Map<String, Object> params = CommonValues.getCommonParams(getActivity());
            params.put("SN", "");
            Map<String, Object> mainDataMap = new HashMap<>();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date curDate = new Date(System.currentTimeMillis());//获取当前时间
            String str = formatter.format(curDate);
            if (bean == null) {
                mainDataMap.put("CreateTime", str);
                uuid = UUID.randomUUID().toString();
            } else {
                mainDataMap.put("CreateTime", bean.getRetData().getDetailData().getCreateTime());
                uuid = bean.getRetData().getDetailData().getIdentifier();
                barcode = getArguments().getString("barCode");
                id = bean.getRetData().getDetailData().getId();
                mainDataMap.put("Id", id);
                mainDataMap.put("BarCode", barcode);
            }

            mainDataMap.put("UpdateTime", str);
            mainDataMap.put("SubmitBy",userInfo.getUserId());
            mainDataMap.put("Identifier", uuid);
            mainDataMap.put("Dense", getDicIdByDescr(DenseList,getDisplayValueByKey("秘密等级").getRealValue()));
            mainDataMap.put("CompanyType", getDisplayValueByKey("公司类型").getRealValue());
            mainDataMap.put("FileName", getDisplayValueByKey("文件名称").getRealValue());
            mainDataMap.put("FileType", DataUtil.getDicodeByDescr(FileTypeList, getDisplayValueByKey("文件类别").getRealValue()));
            mainDataMap.put("ReleaseMode", DataUtil.getDicodeByDescr(ReleaseModeList,getDisplayValueByKey("发布方式").getRealValue()));
            mainDataMap.put("ReleaseRange", getDisplayValueByKey("发布范围").getRealValue());
            mainDataMap.put("Branch", getDisplayValueByKey("部门").getRealValue());
            if (getDisplayValueByKey("发布方式").getRealValue().equals("邮件发布")){
                mainDataMap.put("PresidentIssued", getDisplayValueByKey("是否总裁签发").getRealValue().equals("是"));
                mainDataMap.put("Organize", false);
            }else if(getDisplayValueByKey("发布方式").getRealValue().equals("红头文件")) {
                mainDataMap.put("Organize", getDisplayValueByKey("是否组织架构调整").getRealValue().equals("是"));
                mainDataMap.put("PresidentIssued", false);
            }else {
                mainDataMap.put("Organize", false);
                mainDataMap.put("PresidentIssued", false);
            }
            mainDataMap.put("ReleaseTarget", userInfo.getUserId());
            mainDataMap.put("Summary","");
            String mainData = GsonUtil.parseMapToJson(mainDataMap);
            params.put("mainData", mainData);
            params.put("detailData", "[]");
        }

    }
}
