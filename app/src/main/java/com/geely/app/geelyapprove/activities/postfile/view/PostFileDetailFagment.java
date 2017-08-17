package com.geely.app.geelyapprove.activities.postfile.view;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.geely.app.geelyapprove.activities.postfile.entity.PostFileEntity;
import com.geely.app.geelyapprove.common.entity.AttachmentListEntity;
import com.geely.app.geelyapprove.common.entity.UserPhotoEntity;
import com.geely.app.geelyapprove.common.fragment.CommonFragment;
import com.geely.app.geelyapprove.common.utils.CommonValues;
import com.geely.app.geelyapprove.common.utils.ToastUtil;
import com.geely.app.geelyapprove.common.view.HandInputGroup;
import com.geely.app.geelyapprove.common.view.HandToolbar;
import com.geely.app.geelyapprove.datamanage.HttpManager;
import com.geely.app.orientalpearl.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Created by zhy on 2016/11/25.
 */

public class PostFileDetailFagment extends CommonFragment {

    private PostFileEntity bean;
    private String photo;

    public PostFileDetailFagment(){
    }

    public static PostFileDetailFagment newInstance(){
        PostFileDetailFagment fragment = new PostFileDetailFagment();
        return fragment;
    }
    public static PostFileDetailFagment newInstance(Bundle bundle){
        PostFileDetailFagment fragment = new PostFileDetailFagment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public void setBean(PostFileEntity bean) {
        this.bean = bean;
    }

    public PostFileEntity getBean(){
        return this.bean;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadData();
    }

    protected void loadData() {
        Map<String, Object> param = CommonValues.getCommonParams(getActivity());
        if (getArguments() == null) return;
        param.put("userId",getArguments().getString("userId"));
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
                                setGroup(getGroupList());
                                setPb(false);
                                setButtonllEnable(true);
                                setDisplayTabs(true);
                                notifyDataSetChanged();
                                return;
                            }else {
                                Toast.makeText(getActivity(), entity.getMsg(), Toast.LENGTH_SHORT).show();
                            }
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
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast(getContext(),"请检查网络");
                        getActivity().finish();
                    }
                });

            }
        });
    }

    @Override
    public List<Group> getGroupList() {
        if (bean == null)return new ArrayList<>();
        List<Group> groups = new ArrayList<>();
        PostFileEntity.RetDataBean.DetailDataBean detailData = bean.getRetData().getDetailData();
        List<HandInputGroup.Holder> list = new ArrayList<>();
        list.add(new HandInputGroup.Holder(this.getString(R.string.Summary), true, false, detailData.getSummary(), HandInputGroup.VALUE_TYPE.TEXT));
        list.add(new HandInputGroup.Holder("流程编号", true, false, getArguments().getString("barCode"), HandInputGroup.VALUE_TYPE.TEXT));
        list.add(new HandInputGroup.Holder(this.getString(R.string.Dense), true, false, detailData.getDenseName(), HandInputGroup.VALUE_TYPE.TEXT));
        list.add(new HandInputGroup.Holder(this.getString(R.string.Company_Type), true, false, detailData.getCompanyType(), HandInputGroup.VALUE_TYPE.TEXT));
        list.add(new HandInputGroup.Holder(this.getString(R.string.File_Name), true, false, detailData.getFileName(), HandInputGroup.VALUE_TYPE.TEXT));
        list.add(new HandInputGroup.Holder(this.getString(R.string.File_Type), true, false, detailData.getFileTypeName(), HandInputGroup.VALUE_TYPE.TEXT));
        list.add(new HandInputGroup.Holder(this.getString(R.string.Release_Mode), true, false, detailData.getReleaseModeName(), HandInputGroup.VALUE_TYPE.TEXT));
        list.add(new HandInputGroup.Holder(this.getString(R.string.Release_Range), true, false, detailData.getReleaseRange(), HandInputGroup.VALUE_TYPE.TEXT));
        Group group = new Group("流程摘要-摘要内容", null, true, null, list).setrl(true).setv1(bean.getRetData().getEmpData().getNameCN() + "(" + bean.getRetData().getEmpData().getNameEN() + ")").setv2(bean.getRetData().getEmpData().getPositionNameCN() + " " + bean.getRetData().getEmpData().getEid());
        if ( photo != null && !photo.isEmpty()) {
            group.setDrawable(photo);
        }
        groups.add(group);
        groups.add(new Group("流程摘要-摘要",null,true,null,null).setValue(bean.getRetData().getHisData()).setrl(false));
        List<HandInputGroup.Holder> subHolder = new ArrayList<>();
        subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Summary), true, false, detailData.getSummary(), HandInputGroup.VALUE_TYPE.TEXT));
        subHolder.add(new HandInputGroup.Holder("流程编号", true, false, getArguments().getString("barCode"), HandInputGroup.VALUE_TYPE.TEXT));
        subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Dense), true, false, detailData.getDenseName(), HandInputGroup.VALUE_TYPE.TEXT));
        subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Company_Type), true, false, detailData.getCompanyType(), HandInputGroup.VALUE_TYPE.TEXT));
        subHolder.add(new HandInputGroup.Holder(this.getString(R.string.File_Name), true, false, detailData.getFileName(), HandInputGroup.VALUE_TYPE.TEXT));
        subHolder.add(new HandInputGroup.Holder(this.getString(R.string.File_Type), true, false, detailData.getFileTypeName(), HandInputGroup.VALUE_TYPE.TEXT));
        subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Release_Mode), true, false, detailData.getReleaseModeName(), HandInputGroup.VALUE_TYPE.TEXT));
        subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Release_Range), true, false, detailData.getReleaseRange(), HandInputGroup.VALUE_TYPE.TEXT));
        subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Branch), false, false, detailData.getBranch(), HandInputGroup.VALUE_TYPE.TEXT));
        subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Submitter), false, true, bean.getRetData().getEmpData().getNameCN(), HandInputGroup.VALUE_TYPE.TEXT));
        if(detailData.getReleaseModeName().equals("邮件发布")){
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.President_Issued), true, false, detailData.isPresidentIssued()?this.getString(R.string.Yes):this.getString(R.string.No), HandInputGroup.VALUE_TYPE.TEXT));
        }
        if(detailData.getReleaseModeName().equals("红头文件")){
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Organize), true, false, detailData.isOrganize()?this.getString(R.string.Yes):this.getString(R.string.No), HandInputGroup.VALUE_TYPE.TEXT));
        }
        subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Release_Target), true, false, detailData.getReleaseTargetName(), HandInputGroup.VALUE_TYPE.TEXT));
        groups.add(new Group("详细信息-" + this.getString(R.string.Basic_Information), null, true, null, subHolder).setrl(false));
        List<AttachmentListEntity> attachList = bean.getRetData().getAttchList();
        if (attachList != null && attachList.size() > 0) {
            for  ( int  i  =   0 ; i  <  attachList.size()  -   1 ; i ++ )   {
                for  ( int  j  =  attachList.size()  -   1 ; j  >  i; j -- )   {
                    if  (attachList.get(j).getFileName().equals(attachList.get(i).getFileName()))   {
                        attachList.remove(j);
                        notifyDataSetChanged();
                    }
                }
            }
            for (AttachmentListEntity entity : attachList) {
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
    public void onClickItemContentSetter(HandInputGroup.Holder holder) {
        if (holder.getType() == HandInputGroup.VALUE_TYPE.SELECT) {
            if (holder.getKey().equals(this.getString(R.string.Select_Attachments))) {
                AttachmentListEntity entity = (AttachmentListEntity) holder.getValue();
                lookAttachmentInDetailFragment(entity);
            }
        }
    }

    public void setToolbar(HandToolbar toolbar) {
        toolbar.setDisplayHomeAsUpEnabled(true, getActivity());
        toolbar.setTitle(getArguments().getString("ProcessNameCN"));
    }

}
