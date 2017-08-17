package com.geely.app.geelyapprove.common.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.geely.app.geelyapprove.activities.ItemActivity;
import com.geely.app.geelyapprove.activities.PageConfig;
import com.geely.app.geelyapprove.activities.enterexpense.Entity.EnterExpenseOrdersEntity;
import com.geely.app.geelyapprove.activities.postbusinesstrip.bean.BusinessTripOrdersEntity;
import com.geely.app.geelyapprove.common.adapter.ListAdapter;
import com.geely.app.geelyapprove.common.application.GeelyApp;
import com.geely.app.geelyapprove.common.utils.CommonValues;
import com.geely.app.geelyapprove.common.utils.DataUtil;
import com.geely.app.geelyapprove.datamanage.HttpManager;
import com.geely.app.orientalpearl.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Oliver on 2016/11/25.
 */

public class ActionSheetUnionOrdersActivity<T> extends Activity {

    private  static final String BARCODE ="BARCODE";
    private  static final String WORK_FLOW_TYPE ="COMMON_MAP";
    private  static final String DATA ="DATA";
    private static Result callback;
    private ArrayList<BusinessTripOrdersEntity.RetDataBean.BusinessTripOrdersBean> listBTrip;
    private ArrayList<EnterExpenseOrdersEntity.RetDataBean.EntertainmentExpenseOrdersBean>  list;
    private ListAdapter adapter;
    private BusinessTripOrdersEntity bTripEntity;
    private EnterExpenseOrdersEntity EnterExpenseOrdersEntity;
    private String workflowType,refBarcode;
    private SimpleListView listView;
    private static ArrayMap<String,Object> data;
    private int pageIndex = 0;

    public interface Result{
        void onResult(ArrayMap<String,Object> data);
    }

    public static void openActionSheet(Context context, String workFlowType, String refBarCode,ArrayMap<String,Object> dat,Result result){
        Intent intent = new Intent(context,ActionSheetUnionOrdersActivity.class);
        intent.putExtra(BARCODE,refBarCode);
        intent.putExtra(WORK_FLOW_TYPE,workFlowType);
        callback = result;
        data = dat;
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_sheet_union_orders);
        if(data==null){
            data = new ArrayMap<>();
        }
        findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onResult(data);
                onBackPressed();
            }
        });
        listView = (SimpleListView) findViewById(R.id.lst_container);
        checkType();


        listView.setAdapter(adapter);
        listView.setOnRefreshListener(new SimpleListView.OnRefreshListener() {
            @Override
            public void onPullRefresh() {
                if(adapter!=null){
                    if(listBTrip!=null&&listBTrip.size()>0){
                        listBTrip.clear();
                    }else if (list!=null&&list.size()>0){
                        list.clear();
                    }
                    loadData(0,10);
                }
            }

            @Override
            public void onLoadingMore() {
                Toast.makeText(ActionSheetUnionOrdersActivity.this, "Load more...", Toast.LENGTH_SHORT).show();
                pageIndex=pageIndex+1;
                loadData(pageIndex,10);
            }

            @Override
            public void onScrollOutside() {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void loadData(int pageIndex, int pageSize){
        Map<String, Object> params = CommonValues.getCommonParams(getApplicationContext());
        params.put("workflowType",workflowType);
        params.put("pageIndex",pageIndex);
        params.put("pageSize",pageSize);
        params.put("refBarCode",  DataUtil.valueNullKeepString(refBarcode));
        if(workflowType.equals("BusinessTripRequest")){
            HttpManager.getInstance().requestResultForm(CommonValues.REQ_UNION_ORDERS, params, BusinessTripOrdersEntity.class, new HttpManager.ResultCallback<BusinessTripOrdersEntity>() {
                @Override
                public void onSuccess(String json, final BusinessTripOrdersEntity businessTripOrdersEntity) {
                    if(bTripEntity==null){
                        bTripEntity = businessTripOrdersEntity;
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(businessTripOrdersEntity!=null&&businessTripOrdersEntity.getCode().equals("100")){
                                List<BusinessTripOrdersEntity.RetDataBean.BusinessTripOrdersBean> businessTripOrders = businessTripOrdersEntity.getRetData().getBusinessTripOrders();
                                if(!listBTrip.removeAll(businessTripOrders)){
                                    listBTrip.addAll(businessTripOrders);
                                    adapter.notifyDataSetChanged();
                                }
                            }
                            if(listView.isPullRefreshed()){
                                listView.completeRefresh();
                            }
                        }
                    });

                }

                @Override
                public void onFailure(String msg) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            listView.completeRefresh();
                            Toast.makeText(ActionSheetUnionOrdersActivity.this, "没有数据哦", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            });

        }else {
            HttpManager.getInstance().requestResultForm(CommonValues.REQ_UNION_ORDERS, params, EnterExpenseOrdersEntity.class, new HttpManager.ResultCallback<EnterExpenseOrdersEntity>() {
                @Override
                public void onSuccess(String json, final EnterExpenseOrdersEntity enter) {
                    if(bTripEntity==null){
                        EnterExpenseOrdersEntity = enter;
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(enter!=null&&enter.getCode().equals("100")){
                                List<EnterExpenseOrdersEntity.RetDataBean.EntertainmentExpenseOrdersBean> entertainmentExpenseOrders = enter.getRetData().getEntertainmentExpenseOrders();
                                if(!list.removeAll(entertainmentExpenseOrders)){
                                    list.addAll(entertainmentExpenseOrders);
                                    adapter.notifyDataSetChanged();
                                }
                            }
                            if(listView.isPullRefreshed()){
                                listView.completeRefresh();
                            }
                        }
                    });

                }

                @Override
                public void onFailure(String msg) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            listView.completeRefresh();
                            Toast.makeText(ActionSheetUnionOrdersActivity.this, "没有数据哦", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            });
        }



    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    private void checkType(){
        workflowType = getIntent().getStringExtra(WORK_FLOW_TYPE);
        refBarcode = getIntent().getStringExtra(BARCODE);
        if(workflowType.equals(CommonValues.WORKFLOW_BLEAVE)){
            listBTrip = new ArrayList<>();
            adapter = new ListAdapter<BusinessTripOrdersEntity.RetDataBean.BusinessTripOrdersBean>(listBTrip,R.layout.list_item_union_orders) {
                @Override
                public void bindView(final ViewHolder holder, final BusinessTripOrdersEntity.RetDataBean.BusinessTripOrdersBean obj) {
                    holder.setText(R.id.tv_title,obj.getBarCode());
                    holder.setText(R.id.tv_time,obj.getCreateTime());
                    holder.setText(R.id.tv_money,obj.getBudget());
                    CheckBox checkBox = holder.getView(R.id.cb_status);
                    if(data.containsKey(obj.getBarCode())){
                        checkBox.setChecked(true);
                    }else {
                        checkBox.setChecked(false);
                    }
                    checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if(isChecked){
                                data.put(obj.getBarCode(), (T) obj);
                            }else {
                                data.remove(obj.getBarCode());
                            }
                        }
                    });
                    holder.getView(R.id.tv_title).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(ActionSheetUnionOrdersActivity.this, ItemActivity.class);
                            intent.putExtra(PageConfig.PAGE_CODE, PageConfig.PAGE_DISPLAY_BLEAVE);
                            Bundle bundle = new Bundle();
                            bundle.putString("userId", GeelyApp.getLoginEntity().getUserId());
                            bundle.putString("barCode", obj.getBarCode());
                            bundle.putString("workflowType", CommonValues.WORKFLOW_BLEAVE);
                            intent.putExtra("data", bundle);
                            startActivity(intent);
                        }
                    });

                }
            };
            loadData(pageIndex,10);
        }else if(workflowType.equals(CommonValues.WORKFLOW_ENTER_EXPENSE)){
            list = new ArrayList<>();
            adapter = new ListAdapter<EnterExpenseOrdersEntity.RetDataBean.EntertainmentExpenseOrdersBean>(list,R.layout.list_item_union_orders) {
                @Override
                public void bindView(final ViewHolder holder, final EnterExpenseOrdersEntity.RetDataBean.EntertainmentExpenseOrdersBean obj) {
                    holder.setText(R.id.tv_title,obj.getBarCode());
                    holder.setText(R.id.tv_time,obj.getCreateTime());
                    holder.setText(R.id.tv_money,obj.getAmount());
                    CheckBox checkBox = holder.getView(R.id.cb_status);
                    if(data.containsKey(obj.getBarCode())){
                        checkBox.setChecked(true);
                    }else {
                        checkBox.setChecked(false);
                    }
                    checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if(isChecked){
                                data.put(obj.getBarCode(), (T) obj);
                            }else {
                                data.remove(obj.getBarCode());
                            }
                        }
                    });
                    holder.getView(R.id.tv_title).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(ActionSheetUnionOrdersActivity.this, ItemActivity.class);
                            intent.putExtra(PageConfig.PAGE_CODE, PageConfig.PAGE_DISPLAY_EXPENSE_OFFER);
                            Bundle bundle = new Bundle();
                            bundle.putString("userId", GeelyApp.getLoginEntity().getUserId());
                            bundle.putString("barCode", obj.getBarCode());
                            bundle.putString("workflowType", CommonValues.WORKFLOW_ENTER_EXPENSE);
                            intent.putExtra("data", bundle);
                            startActivity(intent);
                        }
                    });

                }
            };
            loadData(pageIndex,10);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
