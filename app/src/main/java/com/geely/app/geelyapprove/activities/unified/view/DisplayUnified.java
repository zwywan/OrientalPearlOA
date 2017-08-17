package com.geely.app.geelyapprove.activities.unified.view;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.geely.app.geelyapprove.activities.unified.bean.UnifiedEntity;
import com.geely.app.geelyapprove.common.application.GeelyApp;
import com.geely.app.geelyapprove.common.entity.AttachmentListEntity;
import com.geely.app.geelyapprove.common.entity.HisDataBean;
import com.geely.app.geelyapprove.common.entity.UserPhotoEntity;
import com.geely.app.geelyapprove.common.fragment.CommonFragment;
import com.geely.app.geelyapprove.common.utils.CommonValues;
import com.geely.app.geelyapprove.common.view.HandInputGroup;
import com.geely.app.geelyapprove.common.view.HandToolbar;
import com.geely.app.geelyapprove.datamanage.HttpManager;
import com.geely.app.orientalpearl.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Created by zhy on 2017/2/3.
 */

public class DisplayUnified extends CommonFragment {

    private String photo;

    public DisplayUnified(){
    }

    private UnifiedEntity.RetDataBean bean;
    private UnifiedEntity.RetDataBean entity;

    public void loadData() {
        if (getArguments() != null){
            Map<String, Object> param = CommonValues.getCommonParams(getActivity());
            param.put("userId",getArguments().getString("userId"));
            param.put("barCode", getArguments().getString("barCode"));
            param.put("workflowType", getArguments().getString("workflowType"));
            HttpManager.getInstance().requestResultForm(CommonValues.UNIFIED_DETATIL, param, UnifiedEntity.class, new HttpManager.ResultCallback<UnifiedEntity>() {
                @Override
                public void onSuccess(String content, final UnifiedEntity entity) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (entity != null && entity.getRetData() != null) {
                                if (entity.getCode().equals("100")) {
                                    bean = entity.getRetData();
                                    setEntity(bean);
                                    setGroup(getGroupList());
                                    setPb(false);
                                    setButtonllEnable(true);
                                    setDisplayTabs(true);
                                    notifyDataSetChanged();
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
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "请检查网络", Toast.LENGTH_SHORT).show();
                            getActivity().finish();
                        }
                    });
                }
            });
            param.put("uid",getArguments().getString("SubmitBy"));
            HttpManager.getInstance().requestResultForm(CommonValues.GET_USER_PHOTO, param, UserPhotoEntity.class, new HttpManager.ResultCallback<UserPhotoEntity>() {
                @Override
                public void onSuccess(String content, final UserPhotoEntity entity) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (entity != null && entity.getRetData() != null) {
                                if (entity.getCode().equals("100")) {
                                    photo = entity.getRetData();
                                    if (getGroup().size() > 0){
                                        getGroup().get(0).setDrawable(photo);
                                        notifyGroupChanged(0,1);
                                    }
                                    return;
                                }
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadData();
    }

    @Override
    public List<Group> getGroupList() {
        if (bean == null) return new ArrayList<>();
        List<Group> groups = new ArrayList<>();
        List<HandInputGroup.Holder> list = new ArrayList<>();
        Group group1 = new Group("流程摘要-摘要内容", null, true, null, list);
        group1.setrl(true).setrl(true).setv1(bean.getEmpData().getNameCN()).setv2(bean.getEmpData().getPositionNameCN() + " " + bean.getEmpData().getEid());
        if (photo != null && !photo.isEmpty()){
            group1.setDrawable(photo);
        }
        List<UnifiedEntity.RetDataBean.ListAbstractBean> listAbstract = bean.getListAbstract();
        if (listAbstract != null && listAbstract.size() != 0){
            for (int i = 0; i < listAbstract.size(); i++) {
                HandInputGroup.Holder holder = new HandInputGroup.Holder(listAbstract.get(i).getFieldCN(), true, false, listAbstract.get(i).getFieldCode(), HandInputGroup.VALUE_TYPE.TEXT);
                if (listAbstract.get(i).getFieldType().equals("文本") && listAbstract.get(i).isIsEdit()){
                    holder.setType(HandInputGroup.VALUE_TYPE.TEXTFILED);
                }
                list.add(holder);
            }
        }
        groups.add(group1);
        groups.add(new Group("流程摘要-摘要",null,true,null,null).setValue(bean.getHisData()).setrl(false));
        List<UnifiedEntity.RetDataBean.ListValueBean> listValue = bean.getListValue();
        bean.getListValue();
        List<HandInputGroup.Holder> holderList = new ArrayList<>();
        if (listValue != null && listValue.size() != 0){
            for (int i = 0; i < listValue.size(); i++) {
                HandInputGroup.Holder holder = new HandInputGroup.Holder(listValue.get(i).getFieldCN(), true, false, listValue.get(i).getFieldCode(), HandInputGroup.VALUE_TYPE.TEXT);
                if (listValue.get(i).getFieldType().equals("文本") && listValue.get(i).isIsEdit()){
                    holder.setType(HandInputGroup.VALUE_TYPE.TEXTFILED);
                    holder.setHasIndicator(listValue.get(i).isRequired());
                }
                if (listValue.get(i).getFieldType().equals("日期") && listValue.get(i).isIsEdit()){
                    holder.setType(HandInputGroup.VALUE_TYPE.DATE);
                    holder.setHasIndicator(listValue.get(i).isRequired());
                }
                if ((listValue.get(i).getFieldType().equals("下拉框")) && listValue.get(i).isIsEdit()){
                    holder.setType(HandInputGroup.VALUE_TYPE.SELECT);
                    holder.setHasIndicator(listValue.get(i).isRequired());
                }
                if (listValue.get(i).getFieldType().equals("选用户控件") && listValue.get(i).isIsEdit()){
                    holder.setType(HandInputGroup.VALUE_TYPE.PICK);
                    holder.setHasIndicator(listValue.get(i).isRequired());
                }
                if (listValue.get(i).getWhichPageDisplay().equals("Start")){
                    holderList.add(holder);
                }else {
                    List<HisDataBean.AuditNodeBean> auditNode = bean.getHisData().getAuditNode();
                    for (HisDataBean.AuditNodeBean auditNodeBean : auditNode) {
                        if (auditNodeBean.getStatus().equals("2") && auditNodeBean.getNameEN().equals(listValue.get(i).getWhichPageDisplay()) && GeelyApp.getLoginEntity().getRetData().getUserInfo().getNameCN().equals(auditNodeBean.getApprovers().split("\\(")[0])){
                            holderList.add(holder);
                            break;
                        }else if (auditNodeBean.getStatus().equals("1") && auditNodeBean.getNameEN().equals(listValue.get(i).getWhichPageDisplay()) && GeelyApp.getLoginEntity().getRetData().getUserInfo().getNameCN().equals(auditNodeBean.getApprovers().split("\\(")[0])){
                            holder.setType(HandInputGroup.VALUE_TYPE.TEXT);
                            holder.setHasIndicator(true);
                            holderList.add(holder);
                            break;
                        }
                    }
                }

            }
        }
        groups.add(new Group("详细信息-" + this.getString(R.string.Basic_Information), null, true, null, holderList).setrl(false));
        List<AttachmentListEntity> attchList = bean.getAttchList();
        if (attchList != null && attchList.size() != 0){
            for (AttachmentListEntity entity : attchList) {
                List<HandInputGroup.Holder> temp = new ArrayList<>();
                temp.add(new HandInputGroup.Holder(this.getString(R.string.Attachment_Type), false, false, entity.getFileGroupName(), HandInputGroup.VALUE_TYPE.TEXT));
                temp.add(new HandInputGroup.Holder<HashSet<Uri>>(this.getString(R.string.Select_Attachments), false, false, entity.getFileName() + entity.getFileExtension(), HandInputGroup.VALUE_TYPE.SELECT)
                        .setColor(Color.BLUE).setDrawableRight(-1).setValue(entity));
                groups.add(new Group("详细信息-" + this.getString(R.string.Attachment_Info), null, false, null, temp).setrl(false));
            }
        }
        return groups;
    }

    @Override
    public void setToolbar(HandToolbar toolbar) {
        toolbar.setDisplayHomeAsUpEnabled(true, getActivity());
        toolbar.setTitle(getArguments().getString("ProcessNameCN"));
    }

    public static Fragment newInstance(Bundle bundle) {
        DisplayUnified fragment = new DisplayUnified();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public String[] getButtonsTitles() {
        return super.getButtonsTitles();
    }

    @Override
    public void onClickItemContentSetter(HandInputGroup.Holder holder) {
        super.onClickItemContentSetter(holder);
        if (holder.getType() == HandInputGroup.VALUE_TYPE.SELECT) {
            if (holder.getKey().equals(this.getString(R.string.Select_Attachments))) {
                AttachmentListEntity entity = (AttachmentListEntity) holder.getValue();
                lookAttachmentData(entity);
            }
        }
    }

    public void setEntity(UnifiedEntity.RetDataBean entity) {
        this.entity = entity;
    }

    public UnifiedEntity.RetDataBean getEntity() {
        return entity;
    }
}
