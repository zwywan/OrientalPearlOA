package com.geely.app.geelyapprove.activities.home.view;

import android.app.Activity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.geely.app.geelyapprove.activities.ItemActivity;
import com.geely.app.geelyapprove.activities.PageConfig;
import com.geely.app.geelyapprove.activities.home.entity.MyCommissionListEntity;
import com.geely.app.geelyapprove.common.adapter.ListAdapter;
import com.geely.app.geelyapprove.common.application.GeelyApp;
import com.geely.app.geelyapprove.common.utils.CommonValues;
import com.geely.app.geelyapprove.common.utils.DataUtil;
import com.geely.app.geelyapprove.common.view.SimpleListView;
import com.geely.app.geelyapprove.datamanage.HttpManager;
import com.geely.app.orientalpearl.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Oliver on 2016/9/23.
 */

public class MyCommissionFragment extends Fragment implements AdapterView.OnItemClickListener, SimpleListView.OnRefreshListener {

    private int tabIndex;
    private int index = 1;
    private boolean hasMore = true;
    private ImageView ivEmpty;
    private ProgressBar pb;

    public MyCommissionFragment() {

    }

    public static MyCommissionFragment newInstance(int tabIndex) {
        Bundle args = new Bundle();
        MyCommissionFragment fragment = new MyCommissionFragment();
        args.putInt(DetailFragment.ARG_TAB, tabIndex);
        fragment.setArguments(args);
        return fragment;
    }

    private List<MyCommissionListEntity.RetDataBean> entityList = new ArrayList<>();
    private List<MyCommissionListEntity.RetDataBean> baseEntityList;

    ListAdapter<MyCommissionListEntity.RetDataBean> adapter = new ListAdapter<MyCommissionListEntity.RetDataBean>((ArrayList<MyCommissionListEntity.RetDataBean>) entityList, R.layout.layout_my_todo_too) {
        @Override
        public void bindView(ViewHolder holder, MyCommissionListEntity.RetDataBean obj) {
            holder.setText(R.id.tv_name, obj.getApplicantName());
            holder.setText(R.id.tv_date, DataUtil.parseDateByFormat(obj.getUpdateTime(), "yyyy-MM-dd HH:mm"));
            holder.setText(R.id.tv_message, obj.getSummary());
            holder.setText(R.id.tv_type, obj.getProcessNameCN());
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tabIndex = getArguments().getInt(DetailFragment.ARG_TAB);
        loadData(tabIndex, index, 10);
    }

    SimpleListView lv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_viewpage_page_content, null, false);
        lv = (SimpleListView) view.findViewById(R.id.viewpager_listview);
        ivEmpty = (ImageView) view.findViewById(R.id.iv_empty);
        pb = (ProgressBar) view.findViewById(R.id.mycommission_pb);
        lv.setAdapter(adapter);
        adapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                if (adapter.getCount() == 0) {
                    ivEmpty.setVisibility(View.VISIBLE);
                } else {
                    ivEmpty.setVisibility(View.GONE);
                }
                pb.setVisibility(View.GONE);
                super.onChanged();
            }
        });
        lv.setOnRefreshListener(this);
        lv.setOnItemClickListener(this);
        return view;
    }

    void loadData(int tabIndex, final int pageIndex, int pageSize) {
        if (callback != null) {
            callback.onLoadData();
        }
        HashMap<String, Object> map = (HashMap<String, Object>) CommonValues.getCommonParams(getActivity());
        map.put("userId", GeelyApp.getLoginEntity().getUserId());
        map.put("keyWord", "");
        String url = null;
        if (tabIndex == 0) {
            url = CommonValues.REQ_WAITING_TODO_NOT_FINSHED;
        } else if (tabIndex == 1) {
            url = CommonValues.REQ_WAITING_TODO_FINSHED;
        }
        map.put("pageIndex", pageIndex);
        map.put("pageSize", pageSize);
        if (map.get("userId") != null){
            HttpManager.getInstance().requestResultForm(url, map, MyCommissionListEntity.class, new HttpManager.ResultCallback<MyCommissionListEntity>() {
                @Override
                public void onSuccess(String content, final MyCommissionListEntity entity) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (entity != null && entity.getRetData().size() > 0) {
                                if (pageIndex == 1){
                                    entityList.clear();
                                }
                                hasMore = true;
                                entityList.addAll(entity.getRetData());
                                adapter.notifyDataSetChanged();
                            } else {
                                hasMore = false;
                            }
                            pb.setVisibility(View.GONE);
                            lv.completeRefresh();
                        }
                    });


                }

                @Override
                public void onFailure(String content) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            lv.completeRefresh();
                            pb.setVisibility(View.GONE);
                        }
                    });

                }
            });
        }

    }


    private void loadMore() {
        if (hasMore) {
            index += 1;
            loadData(tabIndex, index, 10);
        } else {
            lv.completeRefresh();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (lv.getCurrentState() == 2) return;
        position -= 1;
        MyCommissionListEntity.RetDataBean item = adapter.getItem(position);
        String procName = item.getProcessName();
        String activityNameEN = item.getActivityNameEN();
        boolean isRework = activityNameEN.equals("Rework");
        if (isRework && tabIndex == 0) {
            Toast.makeText(getActivity(), "退回的申请，请重新填写", Toast.LENGTH_LONG).show();
        }
        if (tabIndex == 0) {
//            if (procName.equals("PaymentRequest")) {
//                if (isRework) {
//                    checkDetail(position, PageConfig.PAGE_APPLY_PAYMENT_FLOW, true,tabIndex);
//                } else {
//                    checkDetail(position, PageConfig.PAGE_APPROVE_PAYMENT_FLOW, false, tabIndex);
//                }
//            } else if (procName.equals("ExpenseRequest")) {
//                if (isRework) {
//                    checkDetail(position, PageConfig.PAGE_APPLY_EXPENSE_OFFER, true, tabIndex);
//                } else {
//                    checkDetail(position, PageConfig.PAGE_APPROVE_EXPENSE_OFFER, false, tabIndex);
//                }
//            } else if (procName.equals("BusinessTripRequest")) {
//                if (isRework) {
//                    checkDetail(position, PageConfig.PAGE_APPLY_BLEAVE, true, tabIndex);
//                } else {
//                    checkDetail(position, PageConfig.PAGE_APPROVE_BLEAVE, false, tabIndex);
//                }
//            } else if (procName.equals("EntertainmentExpenseRequest")) {
//                if (isRework) {
//                    checkDetail(position, PageConfig.PAGE_APPLY_ENTERTAINMENT_EXPENSE, true, tabIndex);
//                } else {
//                    checkDetail(position, PageConfig.PAGE_APPROVE_ENTER_EXPENSE, false, tabIndex);
//
//                }
//            } else if (procName.equals("OverTimeRequest")) {
//                if (isRework) {
//                    checkDetail(position, PageConfig.PAGE_APPLY_EXTRAWORK, true, tabIndex);
//                } else {
//                    checkDetail(position, PageConfig.PAGE_APPROVE_EXTRAWORK, false, tabIndex);
//                }
//            } else if (procName.equals("LeaveRequest")) {
//                if (isRework) {
//                    checkDetail(position, PageConfig.PAGE_APPLY_REST, true, tabIndex);
//                } else {
//                    checkDetail(position, PageConfig.PAGE_APPROVE_REST, false, tabIndex);
//                }
//
//            } else if (procName.equals("TravelExpenseRequest")) {
//                if (isRework) {
//                    checkDetail(position, PageConfig.PAGE_APPLY_TRAVEL_OFFER, true, tabIndex);
//                } else {
//                    checkDetail(position, PageConfig.PAGE_APPROVE_TRAVEL, false, tabIndex);
//                }
//
//            }else if (procName.equals("FileRequest")) {
//                if (isRework) {
//                    checkDetail(position, PageConfig.PAGE_DISPLAY_POST_FILE, true, tabIndex);
//                } else {
//                    checkDetail(position, PageConfig.PAGE_APPROVE_POST_FILE, false, tabIndex);
//                }
//
//            }else{
                if (isRework) {
                    checkDetail(position, PageConfig.PAGE_DISPLAY_UNIFIED, true, tabIndex);
                } else {
                    checkDetail(position, PageConfig.PAGE_APPROVE_UNIFIED, false, tabIndex);
//                }
            }

        } else if (tabIndex == 1) {
//            if (procName.equals("BusinessTripRequest")) {
//
//                checkDetail(position, PageConfig.PAGE_DISPLAY_BLEAVE, false, tabIndex);
//
//                //加班明细
//            } else if (procName.equals("OverTimeRequest")) {
//
//                checkDetail(position, PageConfig.PAGE_DISPLAY_OVERTIME, false, tabIndex);
//
//                //请假明细
//            } else if (procName.equals("LeaveRequest")) {
//
//                checkDetail(position, PageConfig.PAGE_DISPLAY_REST, false, tabIndex);
//
//            } else if (procName.equals("ExpenseRequest")) {
//
//                checkDetail(position, PageConfig.PAGE_DISPLAY_EXPENSE, false, tabIndex);
//
//            }//付款明细
//            else if (procName.equals("PaymentRequest")) {
//
//                checkDetail(position, PageConfig.PAGE_DISPLAY_PAYMENT, false, tabIndex);
//
//            }//差旅
//            else if (procName.equals("TravelExpenseRequest")) {
//
//                checkDetail(position, PageConfig.PAGE_DISPLAY_TRAVEL, false, tabIndex);
//            }//招待费
//            else if (procName.equals("EntertainmentExpenseRequest")) {
//
//                checkDetail(position, PageConfig.PAGE_DISPLAY_EXPENSE_OFFER, false, tabIndex);
//
//            }//发文明细
//            else if(procName.equals("FileRequest")){
//                checkDetail(position, PageConfig.PAGE_DISPLAY_POST_FILE,false, tabIndex);
//            }else {
                checkDetail(position, PageConfig.PAGE_DISPLAY_UNIFIED, false, tabIndex);
//            }

        }


    }

    private void checkDetail(int position, int pageCode, boolean remak, int tabIndex) {
        Intent intent = new Intent(getActivity(), ItemActivity.class);
        intent.putExtra(PageConfig.PAGE_CODE, pageCode);
        Bundle bundle = new Bundle();
        bundle.putString("userId", GeelyApp.getLoginEntity().getUserId());
        bundle.putString("barCode", adapter.getItem(position).getBarCode());
        bundle.putString("SubmitBy",adapter.getItem(position).getSubmitBy());
        bundle.putString("workflowType", adapter.getItem(position).getProcessName());
        bundle.putString("ProcessNameCN",adapter.getItem(position).getProcessNameCN());
        bundle.putString("WorkflowIdentifier",adapter.getItem(position).getWorkflowIdentifier());
        bundle.putBoolean("Remak",remak);
        bundle.putInt("item",position);
        bundle.putInt("tabIndex",tabIndex);
        bundle.putString("SN", adapter.getItem(position).getProcInstID() + "_" + adapter.getItem(position).getActInstDestID());
        intent.putExtra("data", bundle);
        startActivityForResult(intent,CommonValues.MYCOMM);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CommonValues.MYCOMM){
            if (resultCode == Activity.RESULT_OK){
                final int item = data.getExtras().getInt("item");
                final int tabIndex = data.getExtras().getInt("tabIndex");
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (tabIndex == 0){
                            entityList.remove(item);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });

            }
        }
    }

    private DetailFragment.DataCallback callback;

    public MyCommissionFragment setCallback(DetailFragment.DataCallback callback) {
        this.callback = callback;
        return this;
    }

    public void filter(String key) {
        if (entityList != null && !key.equals("")) {
            if (baseEntityList == null) {
                baseEntityList = new ArrayList<>();
                baseEntityList.addAll(entityList);
            }
            List<MyCommissionListEntity.RetDataBean> list = new ArrayList<>();
            for (MyCommissionListEntity.RetDataBean bean : baseEntityList) {
                if (bean.getProcessNameCN().replace(" ", "").contains(key)) {
                    list.add(bean);
                }
                if (bean.getSummary().replace(" ", "").contains(key)) {
                    list.add(bean);
                }
                if (DataUtil.parseDateByFormat(bean.getUpdateTime(), "yyyy-MM-dd HH:mm").replace(" ", "").contains(key)) {
                    list.add(bean);
                }
                if (bean.getApplicantName().replace(" ", "").contains(key)) {
                    list.add(bean);
                }
            }
            entityList.clear();
            entityList.addAll(list);
            adapter.notifyDataSetChanged();
        } else if (entityList != null) {
            if (baseEntityList != null) {
                entityList.clear();
                entityList.addAll(baseEntityList);
                adapter.notifyDataSetChanged();
            }

        }
    }

    @Override
    public void onPullRefresh() {
        pb.setVisibility(View.VISIBLE);
        hasMore = true;
        index = 1;
        loadData(tabIndex, index, 10);
    }



    @Override
    public void onLoadingMore() {
        loadMore();
    }

    @Override
    public void onScrollOutside() {

    }

}
