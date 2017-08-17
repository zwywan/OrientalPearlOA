package com.geely.app.geelyapprove.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.geely.app.geelyapprove.activities.cost.view.CostApplyFragment;
import com.geely.app.geelyapprove.activities.cost.view.CostApproveFragment;
import com.geely.app.geelyapprove.activities.cost.view.CostDetailFragment;
import com.geely.app.geelyapprove.activities.enterexpense.view.EnterExpenseApplyFragment;
import com.geely.app.geelyapprove.activities.enterexpense.view.EnterExpenseApproveFragment;
import com.geely.app.geelyapprove.activities.enterexpense.view.EnterExpenseDetailFragment;
import com.geely.app.geelyapprove.activities.extrawork.views.ExtraWorkApplyFragment;
import com.geely.app.geelyapprove.activities.extrawork.views.ExtraWorkApproveFragment;
import com.geely.app.geelyapprove.activities.extrawork.views.ExtraWorkDetailFragment;
import com.geely.app.geelyapprove.activities.pay.view.PayFlowApproveFragment;
import com.geely.app.geelyapprove.activities.pay.view.PayFlowDetailFragment;
import com.geely.app.geelyapprove.activities.pay.view.PayFlowFragment;
import com.geely.app.geelyapprove.activities.postbusinesstrip.view.AddOrUpdateFragment;
import com.geely.app.geelyapprove.activities.postbusinesstrip.view.BTripApproveFragment;
import com.geely.app.geelyapprove.activities.postbusinesstrip.view.DisplayFragment;
import com.geely.app.geelyapprove.activities.postfile.view.PostFileApplyFragment;
import com.geely.app.geelyapprove.activities.postfile.view.PostFileApproverFragment;
import com.geely.app.geelyapprove.activities.postfile.view.PostFileDetailFagment;
import com.geely.app.geelyapprove.activities.rest.view.RestApplyFragment;
import com.geely.app.geelyapprove.activities.rest.view.RestApproveFragment;
import com.geely.app.geelyapprove.activities.rest.view.RestDetailFragment;
import com.geely.app.geelyapprove.activities.traveloffer.view.TravelApplyFragment;
import com.geely.app.geelyapprove.activities.traveloffer.view.TravelApproveFragment;
import com.geely.app.geelyapprove.activities.traveloffer.view.TravelOfferDetailFragment;
import com.geely.app.geelyapprove.activities.unified.view.ApproveUnified;
import com.geely.app.geelyapprove.activities.unified.view.DisplayUnified;
import com.geely.app.geelyapprove.common.view.FileChooserLayout;
import com.geely.app.orientalpearl.R;

import java.util.List;

import static com.geely.app.geelyapprove.activities.PageConfig.PAGE_APPLY_BLEAVE;
import static com.geely.app.geelyapprove.activities.PageConfig.PAGE_APPLY_ENTERTAINMENT_EXPENSE;
import static com.geely.app.geelyapprove.activities.PageConfig.PAGE_APPLY_EXPENSE_OFFER;
import static com.geely.app.geelyapprove.activities.PageConfig.PAGE_APPLY_EXTRAWORK;
import static com.geely.app.geelyapprove.activities.PageConfig.PAGE_APPLY_PAYMENT_FLOW;
import static com.geely.app.geelyapprove.activities.PageConfig.PAGE_APPLY_POST_FILE;
import static com.geely.app.geelyapprove.activities.PageConfig.PAGE_APPLY_REST;
import static com.geely.app.geelyapprove.activities.PageConfig.PAGE_APPLY_TRAVEL_OFFER;

/**
 *
 * Created by zhy on 2016/9/14.
 */
public class ItemActivity extends AppCompatActivity {


    FileChooserLayout.ActivityResultCallback callback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow_detail_item);
        checkFragmentForLoading(getIntent().getIntExtra(PageConfig.PAGE_CODE, -1));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for (Fragment fragment : fragments) {
            fragment.onActivityResult(requestCode,resultCode,data);
        }
        if(callback != null){
            callback.onActivityResult(requestCode, resultCode, data);
        }
    }

    void checkFragmentForLoading(int pageCode) {
        switch (pageCode) {
            case PAGE_APPLY_BLEAVE:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, AddOrUpdateFragment.newInstance(getIntent().getBundleExtra("data"))).commit();
                break;
            case PAGE_APPLY_EXTRAWORK:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, ExtraWorkApplyFragment.newInstance(getIntent().getBundleExtra("data"))).commit();
                break;
            case PAGE_APPLY_REST:
                //请假申请
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, RestApplyFragment.newInstance(getIntent().getBundleExtra("data"))).commit();
                break;
            case PAGE_APPLY_TRAVEL_OFFER:
                //差旅报销
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, TravelApplyFragment.newInstance(getIntent().getBundleExtra("data"))).commit();
                break;
            case PAGE_APPLY_PAYMENT_FLOW:
                //付款流程
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, PayFlowFragment.newInstance(getIntent().getBundleExtra("data"))).commit();
                break;
            case PAGE_APPLY_EXPENSE_OFFER:
                //费用报销发起
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, CostApplyFragment.newInstance(getIntent().getBundleExtra("data"))).commit();
                break;
            case PAGE_APPLY_ENTERTAINMENT_EXPENSE:
                //发起招待费
                checkFragment(EnterExpenseApplyFragment.newInstance(getIntent().getBundleExtra("data")));
                break;
            case PAGE_APPLY_POST_FILE:
                //发文申请
                checkFragment(PostFileApplyFragment.newInstance(getIntent().getBundleExtra("data")));
                break;

            case PageConfig.PAGE_DISPLAY_BLEAVE:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, DisplayFragment.newInstance(getIntent().getBundleExtra("data"))).commit();
                break;
            case PageConfig.PAGE_DISPLAY_OVERTIME:
                checkFragment(ExtraWorkDetailFragment.newInstance(getIntent().getBundleExtra("data")));
                break;
            case PageConfig.PAGE_DISPLAY_REST:
                checkFragment(RestDetailFragment.newInstance(getIntent().getBundleExtra("data")));
                break;
            case PageConfig.PAGE_DISPLAY_EXPENSE:
                //费用明细
                checkFragment(CostDetailFragment.newInstance(getIntent().getBundleExtra("data")));
                break;
            case PageConfig.PAGE_DISPLAY_PAYMENT:
                checkFragment(PayFlowDetailFragment.newInstance(getIntent().getBundleExtra("data")));
                break;
            case PageConfig.PAGE_DISPLAY_TRAVEL:
                checkFragment(TravelOfferDetailFragment.newInstance(getIntent().getBundleExtra("data")));
                break;
            case PageConfig.PAGE_DISPLAY_EXPENSE_OFFER:
                //招待费明细
                checkFragment(EnterExpenseDetailFragment.newInstance(getIntent().getBundleExtra("data")));
                break;
            case PageConfig.PAGE_DISPLAY_POST_FILE:
                //发文明细
                checkFragment(PostFileDetailFagment.newInstance(getIntent().getBundleExtra("data")));
                break;

            case PageConfig.PAGE_APPROVE_EXTRAWORK:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, ExtraWorkApproveFragment.newInstance(getIntent().getBundleExtra("data"))).commit();
                break;
            case PageConfig.PAGE_APPROVE_REST:
                //请假审批
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, RestApproveFragment.newInstance(getIntent().getBundleExtra("data"))).commit();
                break;
            case PageConfig.PAGE_APPROVE_TRAVEL:
                //差旅审批
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, TravelApproveFragment.newInstance(getIntent().getBundleExtra("data"))).commit();
                break;
            case PageConfig.PAGE_APPROVE_EXPENSE_OFFER:
                //费用审批
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, CostApproveFragment.newInstance(getIntent().getBundleExtra("data"))).commit();
                break;
            case PageConfig.PAGE_APPROVE_BLEAVE:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, BTripApproveFragment.newInstance(getIntent().getBundleExtra("data"))).commit();
                //公出审批
                break;
            case PageConfig.PAGE_APPROVE_PAYMENT_FLOW:
                checkFragment(PayFlowApproveFragment.newInstance(getIntent().getBundleExtra("data")));
                break;
            case PageConfig.PAGE_APPROVE_ENTER_EXPENSE:
                //招待费审批
                checkFragment(EnterExpenseApproveFragment.newInstance(getIntent().getBundleExtra("data")));
                break;
            case PageConfig.PAGE_APPROVE_POST_FILE:
                //发文审批
                checkFragment(PostFileApproverFragment.newInstance(getIntent().getBundleExtra("data")));
                break;

            case PageConfig.PAGE_DISPLAY_UNIFIED:
                checkFragment(DisplayUnified.newInstance(getIntent().getBundleExtra("data")));
                break;
            case PageConfig.PAGE_APPROVE_UNIFIED:
                checkFragment(ApproveUnified.newInstance(getIntent().getBundleExtra("data")));
                break;
        }

    }

    private void checkFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }

    public void setCallback(FileChooserLayout.ActivityResultCallback callback) {
        this.callback = callback;
    }

    @Override
    public void onBackPressed() {
        int intExtra = getIntent().getIntExtra(PageConfig.PAGE_CODE, -1);
        if (intExtra == PageConfig.PAGE_APPLY_BLEAVE || intExtra == PageConfig.PAGE_APPLY_EXTRAWORK ||intExtra == PageConfig.PAGE_APPLY_REST ||
                intExtra == PageConfig.PAGE_APPLY_TRAVEL_OFFER ||intExtra == PageConfig.PAGE_APPLY_PAYMENT_FLOW ||intExtra == PageConfig.PAGE_APPLY_EXPENSE_OFFER ||
                intExtra == PageConfig.PAGE_APPLY_ENTERTAINMENT_EXPENSE ||intExtra == PageConfig.PAGE_APPLY_POST_FILE ){
            AlertDialog.Builder builder=new AlertDialog.Builder(this);  //先得到构造器
            builder.setMessage("是否确认退出?"); //设置内容
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() { //设置确定按钮
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss(); //关闭dialog
                    ItemActivity.super.onBackPressed();
                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() { //设置取消按钮
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            //参数都设置完成了，创建并显示出来
            builder.create().show();

        }else {
            super.onBackPressed();
        }
    }



}
