package com.geely.app.geelyapprove.activities.postfile.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.geely.app.geelyapprove.activities.postfile.entity.PostFileEntity;
import com.geely.app.geelyapprove.common.application.GeelyApp;
import com.geely.app.geelyapprove.common.entity.AttachmentListEntity;
import com.geely.app.geelyapprove.common.utils.CommonValues;
import com.geely.app.geelyapprove.common.utils.DataUtil;
import com.geely.app.geelyapprove.common.utils.GsonUtil;
import com.geely.app.geelyapprove.common.utils.ToastUtil;
import com.geely.app.geelyapprove.common.view.HandInputGroup;
import com.geely.app.orientalpearl.R;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by zhy on 2016/11/25.
 */

public class PostFileApproverFragment extends PostFileDetailFagment {

    public PostFileApproverFragment(){}

    public static PostFileApproverFragment newInstance(){
        PostFileApproverFragment fragment = new PostFileApproverFragment();
        return fragment;
    }
    public static PostFileApproverFragment newInstance(Bundle bundle){
        PostFileApproverFragment fragment = new PostFileApproverFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getBottomTitles();
    }

    public void onClickItemContentSetter(final HandInputGroup.Holder holder) {

        if (holder.getType() == HandInputGroup.VALUE_TYPE.SELECT) {
            if (holder.getKey().equals(this.getString(R.string.Select_Attachments))) {
                AttachmentListEntity entity = (AttachmentListEntity) holder.getValue();
                lookAttachmentInDetailFragment(entity);
            }
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
        if (!title.equals("驳回") && over != null){
            ToastUtil.showToast(getContext(),(this.getString(R.string.Please_Fill) + over));
            setButtonllEnable(true);
            return;
        }else {
            final Map<String, Object> param = CommonValues.getCommonParams(getActivity());
            PostFileEntity.RetDataBean.DetailDataBean mainData = getBean().getRetData().getDetailData();
            mainData.setUpdateBy(GeelyApp.getLoginEntity().getUserId());
            mainData.setUpdateTime(DataUtil.parseDateNormalString(new Date()));
            String main = GsonUtil.getJson(mainData);
            param.put("mainData", main);
            param.put("detailData", "[]");
            param.put("sn", getArguments().getString("SN"));
            applyApprove(CommonValues.REQ_POSTFILE_APPROVE, param, title);

        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CommonValues.CODE_OA_REQUEST) {
            if (data != null) {
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
}
