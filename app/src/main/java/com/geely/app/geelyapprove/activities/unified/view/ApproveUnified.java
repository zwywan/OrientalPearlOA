package com.geely.app.geelyapprove.activities.unified.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.geely.app.geelyapprove.activities.persons.PersonListActivity;
import com.geely.app.geelyapprove.activities.unified.bean.UnifiedEntity;
import com.geely.app.geelyapprove.common.application.GeelyApp;
import com.geely.app.geelyapprove.common.entity.AttachmentListEntity;
import com.geely.app.geelyapprove.common.entity.MethodNameEntity;
import com.geely.app.geelyapprove.common.fragment.CommonFragment;
import com.geely.app.geelyapprove.common.utils.CommonValues;
import com.geely.app.geelyapprove.common.utils.DataUtil;
import com.geely.app.geelyapprove.common.utils.GsonUtil;
import com.geely.app.geelyapprove.common.utils.ToastUtil;
import com.geely.app.geelyapprove.common.view.HandInputGroup;
import com.geely.app.geelyapprove.common.view.widget.CustomDatePicker;
import com.geely.app.orientalpearl.R;
import com.google.gson.reflect.TypeToken;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by zhy on 2017/2/6.
 */

public class ApproveUnified extends DisplayUnified {

    private List<MethodNameEntity> methodName;
    private UnifiedEntity.RetDataBean bean;
    private UnifiedEntity.RetDataBean.ListValueBean listValueBean;
    private Map<String, Object> param;
    private Map<String, Object> mainData =  new HashMap<String, Object>();
    private List<UnifiedEntity.RetDataBean.ListValueBean> listValue;
    private CustomDatePicker customDatePicker;

    public static Fragment newInstance(Bundle bundle) {
        ApproveUnified fragment = new ApproveUnified();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getBottomTitles();

    }

    @Override
    public void onHolderTextChanged(int main, int index, HandInputGroup.Holder holder) {
        super.onHolderTextChanged(main, index, holder);
        bean = getEntity();
        listValue = bean.getListValue();
        for (UnifiedEntity.RetDataBean.ListValueBean valueBean : listValue) {
            if (valueBean.getFieldCN().equals(holder.getKey())){
                mainData.put(valueBean.getFieldEN(),holder.getRealValue());
            }
        }
    }

    @Override
    public void onClickItemContentSetter(final HandInputGroup.Holder holder) {
        bean = getEntity();
        if (holder.getType() == HandInputGroup.VALUE_TYPE.DATE ) {
            showDateTimePicker(holder, false);
        }else if (holder.getType() == HandInputGroup.VALUE_TYPE.PICK){
            Intent intent = new Intent();
            intent.setClass(getContext(), PersonListActivity.class);
            intent.putExtra("key",holder.getKey());
            startActivityForResult(intent,23333);
        }else if (holder.getType() == HandInputGroup.VALUE_TYPE.SELECT){
            if (holder.getKey().equals(this.getString(R.string.Select_Attachments))) {
                AttachmentListEntity entity = (AttachmentListEntity) holder.getValue();
                lookAttachmentData(entity);
            }else {

                listValue = bean.getListValue();
                for (int i = 0; i < listValue.size(); i++) {
                    if (listValue.get(i).getFieldCN().equals(holder.getKey())){
                        listValueBean = listValue.get(i);
                        String methodName = (String) listValueBean.getMethodName();
                        if (methodName.length()>2){
                            this.methodName = (List<MethodNameEntity>) GsonUtil.parseJsonToList((String) listValueBean.getMethodName(),new TypeToken<List<MethodNameEntity>>(){}.getType());
                        }
                    }
                }
                if (methodName != null && methodName.size() != 0){
                    String[] str = new String[methodName.size()];
                    for (int i = 0; i < methodName.size(); i++) {
                        str[i] = methodName.get(i).getDicDesc();
                    }

                    showSelector(holder, str, new OnSelectedResultCallback() {
                        @Override
                        public void onSelected(Group ownGroup, HandInputGroup.Holder holder, int mainIndex, int itemIndex) {
                            for (MethodNameEntity methodNameEntity : methodName) {
                                if (methodNameEntity.getDicDesc().equals(holder.getRealValue())){
                                    mainData.put(listValueBean.getFieldEN(),methodNameEntity.getDicId());
                                }
                            }
                            methodName.clear();
                        }
                    });
                }
//                else {
//                    requestForPerson(holder.getKey());
//                }

            }
        }
    }

    public void showDateTimePicker(final HandInputGroup.Holder holder,boolean bolean) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());
        // 回调接口，获得选中的时间
        initDatePicker(holder,bolean);
        if (holder.getRealValue().isEmpty()){
            customDatePicker.show(now);
        }else {
            customDatePicker.show(holder.getRealValue());
        }

    }

    private void initDatePicker(final HandInputGroup.Holder holder, final boolean bolean) {
        customDatePicker = new CustomDatePicker(getActivity(), new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) {
                if (bolean){
                    holder.setDispayValue(time);
                    bean = getEntity();
                    listValue = bean.getListValue();
                    for (UnifiedEntity.RetDataBean.ListValueBean valueBean : listValue) {
                        if (valueBean.getFieldCN().equals(holder.getKey())){
                            mainData.put(valueBean.getFieldEN(),holder.getRealValue());
                        }
                    }
                }else {
                    holder.setDispayValue(time.split(" ")[0]);
                }
                try {
                    onDataChanged(holder);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                notifyDataSetChanged();
            }
        }, "1986-01-01 00:00", "2986-01-01 00:00"); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker.showSpecificTime(bolean); // 是否显示时和分
        customDatePicker.setIsLoop(true); // 是否循环滚动
    }




    @SuppressWarnings("unchecked")
    @Override
    public void onBottomButtonsClick(final String title, final List<CommonFragment.Group> groups) {
        param = CommonValues.getCommonParams(getActivity());
        setButtonllEnable(false);
        if (title.equals("转办")) {
            requestForPerson(title);
            return;
        }
        String over = isOver(groups);
        if (!title.equals("驳回") && over != null) {
            ToastUtil.showToast(getContext(), ("请填写" + over));
            setButtonllEnable(true);
            return;
        } else {
            if (over == null) {
                bean = getEntity();
                param.remove("vision");
                param.put("barCode", getArguments().getString("barCode"));
                param.put("workflowType", getArguments().getString("workflowType"));
                param.put("SN", getArguments().getString("SN"));
                mainData.put("UpdateBy", GeelyApp.getLoginEntity().getUserId());
                mainData.put("UpdateTime", DataUtil.parseDateNormalString(new Date()));
                List<Object> details = new ArrayList<Object>();
                param.put("detailData", GsonUtil.getJson(details));
                String json = GsonUtil.getJson(mainData);
                param.put("mainData", json);
                applyApprove(CommonValues.UNIFIED_APPROVE, param, title);
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CommonValues.CODE_OA_REQUEST) {
            if (data != null) {
                String keyOfHolder = data.getStringExtra("keyOfHolder");
                if (keyOfHolder.equals("转办")){
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
                }else {
                    getDisplayValueByKey(keyOfHolder).setDispayValue(data.getStringExtra("empCname"));
                    notifyDataSetChanged();
                }

            } else {
                Toast.makeText(getActivity(), "读取人员信息失败", Toast.LENGTH_SHORT).show();
            }
        }else if (requestCode == 23333){
            if (resultCode == 23333){
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String key = data.getExtras().getString("key");
                        bean = getEntity();
                        listValue = bean.getListValue();
                        HandInputGroup.Holder holder = getDisplayValueByKey(key);
                        for (UnifiedEntity.RetDataBean.ListValueBean valueBean : listValue) {
                            if (valueBean.getFieldCN().equals(holder.getKey())){
                                mainData.put(valueBean.getFieldEN(),data.getExtras().getString("id"));
                            }
                        }
                        getDisplayValueByKey(key).setDispayValue(data.getExtras().getString("name"));
                        notifyDataSetChanged();
                    }
                });
            }


        }
    }

}
