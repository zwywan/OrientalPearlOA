package com.geely.app.geelyapprove.activities.traveloffer.view;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.widget.Toast;

import com.geely.app.geelyapprove.activities.ItemActivity;
import com.geely.app.geelyapprove.activities.PageConfig;
import com.geely.app.geelyapprove.activities.enterexpense.Entity.EnterExpenseOrdersEntity;
import com.geely.app.geelyapprove.activities.postbusinesstrip.bean.BusinessTripOrdersEntity;
import com.geely.app.geelyapprove.activities.traveloffer.bean.TravelOfferDetailBean;
import com.geely.app.geelyapprove.common.application.GeelyApp;
import com.geely.app.geelyapprove.common.entity.AttachmentListEntity;
import com.geely.app.geelyapprove.common.entity.EmpDataEntity;
import com.geely.app.geelyapprove.common.entity.UserPhotoEntity;
import com.geely.app.geelyapprove.common.fragment.CommonFragment;
import com.geely.app.geelyapprove.common.utils.CommonValues;
import com.geely.app.geelyapprove.common.view.HandInputGroup;
import com.geely.app.geelyapprove.common.view.HandToolbar;
import com.geely.app.geelyapprove.common.view.SubListLayout;
import com.geely.app.geelyapprove.datamanage.HttpManager;
import com.geely.app.orientalpearl.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Created by xgl on 2016/10/12.
 */

public class TravelOfferDetailFragment extends CommonFragment {

    private TravelOfferDetailBean.RetDataBean entity;
    private TravelOfferDetailBean.RetDataBean.DetailDataBean entityDetail;
    private EmpDataEntity entityEmp;
    private boolean isDomestic;
    private ArrayMap<String, Object> bTripUnion = new ArrayMap<>();
    private ArrayMap<String,Object> enterExpUnion = new ArrayMap<>();
    private String photo;

    public TravelOfferDetailFragment(){
    }

    public static TravelOfferDetailFragment newInstance(Bundle bundle) {
        TravelOfferDetailFragment fragment = new TravelOfferDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public List<Group> getGroupList() {
        if (entity == null) return new ArrayList<>();
        boolean paperReceive = entity.getIsKeyAuditNodeForDisplay().isPaperReceive();//单据接收
        boolean finance = entity.getIsKeyAuditNodeForDisplay().isFinance();//财务审核
        boolean glAccount = entity.getIsKeyAuditNodeForDisplay().isGLAccount();//总账
        boolean cashier = entity.getIsKeyAuditNodeForDisplay().isCashier();//出纳
        entityDetail = entity.getDetailData();
        entityEmp = entity.getEmpData();
        isDomestic = entityDetail.isIsDomestic();
        List<Group> groups = new ArrayList<>();
        List<HandInputGroup.Holder> list = new ArrayList<>();
        list.add(new HandInputGroup.Holder(this.getString(R.string.Summary), true, false, entity.getDetailData().getSummary(), HandInputGroup.VALUE_TYPE.TEXT));
        list.add(new HandInputGroup.Holder("流程编号", true, false, getArguments().getString("barCode"), HandInputGroup.VALUE_TYPE.TEXT));
        list.add(new HandInputGroup.Holder(this.getString(R.string.Company_Name), true, false, entityEmp.getCompNameCN(), HandInputGroup.VALUE_TYPE.TEXT));
        list.add(new HandInputGroup.Holder(this.getString(R.string.Cost_Center), true, false, entityDetail.getCostCenterName(), HandInputGroup.VALUE_TYPE.TEXT));
        for (TravelOfferDetailBean.RetDataBean.DetailDataBean.TravelExpenseDetailBean bean : entity.getDetailData().getTravelExpenseDetail()) {
            list.add(new HandInputGroup.Holder(this.getString(R.string.From) + "/" + this.getString(R.string.To), true, false, bean.getActualLeaveDate().split(" ")[0] + " " + bean.getFrom() + "\n"
                    + bean.getActualReturnDate().split(" ")[0] + " " + bean.getTo(), HandInputGroup.VALUE_TYPE.TEXT));
        }
        list.add(new HandInputGroup.Holder(this.getString(R.string.Total), true, false, entity.getDetailData().getTotalAmount(), HandInputGroup.VALUE_TYPE.TEXT));
        if(paperReceive && !entity.getIsKeyAuditNodeForApprove().isPaperReceive()){
            list.add(new HandInputGroup.Holder(this.getString(R.string.Received_Date), true, false, entity.getDetailData().getReceivedDate().split(" ")[0], HandInputGroup.VALUE_TYPE.TEXT));
        }
        if (finance && !entity.getIsKeyAuditNodeForApprove().isFinance()){
            list.add(new HandInputGroup.Holder(this.getString(R.string.Audit_Total_Amount), true, false, entityDetail.getAuditTotalAmount(), HandInputGroup.VALUE_TYPE.TEXT));
            list.add(new HandInputGroup.Holder(this.getString(R.string.Is_Render_Accountant), true, false, entityDetail.isIsRenderAccountant()?this.getString(R.string.Yes):this.getString(R.string.No), HandInputGroup.VALUE_TYPE.TEXT).setEditable(false));
            list.add(new HandInputGroup.Holder(this.getString(R.string.Generate_Accounting_Vouchers_Type), true, false, entityDetail.getGenerateAccountingVouchersTypeName(), HandInputGroup.VALUE_TYPE.TEXT));
            list.add(new HandInputGroup.Holder(this.getString(R.string.Is_Compliance), true, false, entityDetail.isIsCompliance()?this.getString(R.string.Yes):this.getString(R.string.No), HandInputGroup.VALUE_TYPE.TEXT).setEditable(false));
            list.add(new HandInputGroup.Holder(this.getString(R.string.ExpenseType), true, false, entityDetail.getExpenseType(), HandInputGroup.VALUE_TYPE.TEXT));
        }
        if (cashier && !entity.getIsKeyAuditNodeForApprove().isCashier()){
            list.add(new HandInputGroup.Holder(this.getString(R.string.Audit_Total_Amount), true, false, entityDetail.getAuditTotalAmount(), HandInputGroup.VALUE_TYPE.TEXT));
            list.add(new HandInputGroup.Holder(this.getString(R.string.Cash_Pay_Amount), false, false, entity.getDetailData().getCashPayAmount().isEmpty()?"0":entity.getDetailData().getCashPayAmount(), HandInputGroup.VALUE_TYPE.TEXT));
            if (entity.getDetailData().isHasBorrow()){
                list.add(new HandInputGroup.Holder(this.getString(R.string.Dealings_Pay_Amount), false, false, entity.getDetailData().getDealingsPayAmount().isEmpty()?"0":entity.getDetailData().getDealingsPayAmount(), HandInputGroup.VALUE_TYPE.TEXT));
            }
            list.add(new HandInputGroup.Holder(this.getString(R.string.Capital_Platform_Payment_Amount), false, false, entity.getDetailData().getPlatPayAmount().isEmpty()?"0":entity.getDetailData().getPlatPayAmount(), HandInputGroup.VALUE_TYPE.TEXT));
            list.add(new HandInputGroup.Holder(this.getString(R.string.Expenditure_Type), true, false, entity.getDetailData().getExpenditureType(), HandInputGroup.VALUE_TYPE.TEXT));
            list.add(new HandInputGroup.Holder(this.getString(R.string.Cashier), true, false, entity.getDetailData().getCashierName(), HandInputGroup.VALUE_TYPE.TEXT));
            if (entity.getDetailData().isIsRenderAccountant()){
                list.add(new HandInputGroup.Holder(this.getString(R.string.Voucher_Number), true, false, entity.getDetailData().getVoucherNumber(), HandInputGroup.VALUE_TYPE.TEXT));
            }
            if (Double.parseDouble(entity.getDetailData().getPlatPayAmount().isEmpty()?"0":entity.getDetailData().getPlatPayAmount()) != 0){
                list.add(new HandInputGroup.Holder(this.getString(R.string.Pay_Status), false, false, entity.getDetailData().getPayStatus(), HandInputGroup.VALUE_TYPE.TEXT));
                if (entity.getDetailData().getPayStatus().equals("1")) {
                    list.add(new HandInputGroup.Holder(this.getString(R.string.Paying_Bank_Account), false, false, entity.getDetailData().getFundReturnBankAccount(), HandInputGroup.VALUE_TYPE.TEXT));
                    list.add(new HandInputGroup.Holder(this.getString(R.string.Paying_Bank_Name), false, false, entity.getDetailData().getFundReturnBankName(), HandInputGroup.VALUE_TYPE.TEXT));
                    list.add(new HandInputGroup.Holder(this.getString(R.string.Payment_Description), false, false, entity.getDetailData().getFundReturnNote(), HandInputGroup.VALUE_TYPE.TEXT));
                }
            }
        }
        if (glAccount){
            list.add(new HandInputGroup.Holder(this.getString(R.string.Audit_Total_Amount), true, false, entityDetail.getAuditTotalAmount(), HandInputGroup.VALUE_TYPE.TEXT));
            list.add(new HandInputGroup.Holder(this.getString(R.string.Cash_Pay_Amount), false, false, entity.getDetailData().getCashPayAmount().isEmpty()?"0":entity.getDetailData().getCashPayAmount(), HandInputGroup.VALUE_TYPE.TEXT));
            if (entity.getDetailData().isHasBorrow()){
                list.add(new HandInputGroup.Holder(this.getString(R.string.Dealings_Pay_Amount), false, false, entity.getDetailData().getDealingsPayAmount().isEmpty()?"0":entity.getDetailData().getDealingsPayAmount(), HandInputGroup.VALUE_TYPE.TEXT));
            }
            list.add(new HandInputGroup.Holder(this.getString(R.string.Capital_Platform_Payment_Amount), false, false, entity.getDetailData().getPlatPayAmount().isEmpty()?"0":entity.getDetailData().getPlatPayAmount(), HandInputGroup.VALUE_TYPE.TEXT));
            list.add(new HandInputGroup.Holder(this.getString(R.string.Expenditure_Type), true, false, entity.getDetailData().getExpenditureType(), HandInputGroup.VALUE_TYPE.TEXT));
            list.add(new HandInputGroup.Holder(this.getString(R.string.Cashier), true, false, entity.getDetailData().getCashierName(), HandInputGroup.VALUE_TYPE.TEXT));
            if (entity.getDetailData().isIsRenderAccountant()){
                list.add(new HandInputGroup.Holder(this.getString(R.string.Voucher_Number), true, false, entity.getDetailData().getVoucherNumber(), HandInputGroup.VALUE_TYPE.TEXT));
            }
            if (Double.parseDouble(entity.getDetailData().getPlatPayAmount().isEmpty()?"0":entity.getDetailData().getPlatPayAmount()) != 0){
                list.add(new HandInputGroup.Holder(this.getString(R.string.Pay_Status), false, false, entity.getDetailData().getPayStatus(), HandInputGroup.VALUE_TYPE.TEXT));
                if (entity.getDetailData().getPayStatus().equals("1")){
                    list.add(new HandInputGroup.Holder(this.getString(R.string.Paying_Bank_Account), false, false, entity.getDetailData().getFundReturnBankAccount(), HandInputGroup.VALUE_TYPE.TEXT));
                    list.add(new HandInputGroup.Holder(this.getString(R.string.Paying_Bank_Name), false, false, entity.getDetailData().getFundReturnBankName(), HandInputGroup.VALUE_TYPE.TEXT));
                    list.add(new HandInputGroup.Holder(this.getString(R.string.Payment_Description), false, false, entity.getDetailData().getFundReturnNote(), HandInputGroup.VALUE_TYPE.TEXT));
                }
            }
        }

        Group group1 = new Group("流程摘要-摘要内容", null, true, null, list);
        group1.setrl(true).setrl(true).setv1(entity.getEmpData().getNameCN() + "(" + entity.getEmpData().getNameEN() +")").setv2(entity.getEmpData().getPositionNameCN() + " " + entity.getEmpData().getEid());
        if (photo != null && !photo.isEmpty()){
            group1.setDrawable(photo);
        }
        groups.add(group1);
        groups.add(new Group("流程摘要-摘要",null,true,null,null).setValue(entity.getHisData()).setrl(false));
        List<HandInputGroup.Holder> subHolder = new ArrayList<>();
        subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Summary), true, false, entity.getDetailData().getSummary(), HandInputGroup.VALUE_TYPE.TEXT));
        subHolder.add(new HandInputGroup.Holder("流程编号", true, false, getArguments().getString("barCode"), HandInputGroup.VALUE_TYPE.TEXT));
        subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Company_Name), true, false, entityEmp.getCompNameCN(), HandInputGroup.VALUE_TYPE.TEXT));
        subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Cost_Center), true, false, entityDetail.getCostCenterName(), HandInputGroup.VALUE_TYPE.TEXT));
        subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Reimbursement_Person), true, false, entityEmp.getNameCN(), HandInputGroup.VALUE_TYPE.TEXT));
        subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Employee_ID), true, true, entityEmp.getEid(), HandInputGroup.VALUE_TYPE.TEXT));
        subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Grade), true, false, entityEmp.getGrade(), HandInputGroup.VALUE_TYPE.TEXT));
        subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Position), true, false, entityEmp.getPositionNameCN(), HandInputGroup.VALUE_TYPE.TEXT));
        subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Employee_ID), true, false, entityDetail.getPayeeEID(), HandInputGroup.VALUE_TYPE.TEXT));
        subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Name), true, false, entityDetail.getPayeeName(), HandInputGroup.VALUE_TYPE.TEXT));
        subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Bank), true, false, entityDetail.getPayeeBankName(), HandInputGroup.VALUE_TYPE.TEXT));
        if (entity.getIsKeyAuditNodeForApprove().isCashier() || entity.getIsKeyAuditNodeForApprove().isFinance() || finance || cashier || entity.getEmpData().getUserId().equals(GeelyApp.getLoginEntity().getRetData().getUserInfo().getUserId())){
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Bank_Account), false, false, entityDetail.getPayeeBankAccount(), HandInputGroup.VALUE_TYPE.TEXT));
        }else {
            if (entityDetail.getPayeeBankAccount().length() > 15){
                subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Bank_Account), false, false, "********" + entityDetail.getPayeeBankAccount().substring(entityDetail.getPayeeBankAccount().length() - 4,entityDetail.getPayeeBankAccount().length()), HandInputGroup.VALUE_TYPE.TEXT));
            }else{
                subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Bank_Account), false, false, entityDetail.getPayeeBankAccount(), HandInputGroup.VALUE_TYPE.TEXT));
            }
        }
        subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Bank_Adress), true, false, entityDetail.getPayeeBankProvinceName()+entity.getDetailData().getPayeeBankCityName(), HandInputGroup.VALUE_TYPE.TEXT));
        subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Payee_Branch_Bank), true, true, entityDetail.getPayeeBranchBank(), HandInputGroup.VALUE_TYPE.TEXT));
        subHolder.add(new HandInputGroup.Holder(this.getString(R.string.IsDomesti), true, false, isDomestic ? this.getString(R.string.Yes) : this.getString(R.string.No), HandInputGroup.VALUE_TYPE.TEXT));
        subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Has_Accommodation), true, false, entityDetail.isHasAccommodation() ? this.getString(R.string.Yes) : this.getString(R.string.No), HandInputGroup.VALUE_TYPE.TEXT));
        subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Has_Borrow), true, true, entityDetail.isHasBorrow() ? this.getString(R.string.Yes) : this.getString(R.string.No), HandInputGroup.VALUE_TYPE.TEXT));
        boolean mIsDomestic = entityDetail.isIsDomestic();
        if (mIsDomestic) {
            String relatedBusinessTripOrders = entityDetail.getRelatedBusinessTripOrders();
            if (!relatedBusinessTripOrders.isEmpty()){
                String[] split = relatedBusinessTripOrders.split(";");
                if (split.length > 1){
                    for (int i = 0; i < split.length; i++) {
                        BusinessTripOrdersEntity.RetDataBean.BusinessTripOrdersBean businessTripOrdersBean = new BusinessTripOrdersEntity.RetDataBean.BusinessTripOrdersBean();
                        businessTripOrdersBean.setBarCode(split[i]);
                        List<BusinessTripOrdersEntity.RetDataBean.BusinessTripOrdersBean.BusinessTripDetailBean> BusinessTripDetaillist = new ArrayList<>();
                        for (TravelOfferDetailBean.RetDataBean.DetailDataBean.TravelExpenseDetailBean travelExpenseDetailBean : entityDetail.getTravelExpenseDetail()) {
                            if (travelExpenseDetailBean.getBusinessTripBarCode().equals(split[i])){
                                BusinessTripOrdersEntity.RetDataBean.BusinessTripOrdersBean.BusinessTripDetailBean businessTripDetailBean = new BusinessTripOrdersEntity.RetDataBean.BusinessTripOrdersBean.BusinessTripDetailBean();
                                businessTripDetailBean.setLeaveDate(travelExpenseDetailBean.getPlanLeaveDate());
                                businessTripDetailBean.setReturnDate(travelExpenseDetailBean.getPlanReturnDate());
                                businessTripDetailBean.setFrom(travelExpenseDetailBean.getFrom());
                                businessTripDetailBean.setTo(travelExpenseDetailBean.getTo());
                                businessTripDetailBean.setTicketCost(travelExpenseDetailBean.getTicketCost());
                                businessTripDetailBean.setTransportCost(travelExpenseDetailBean.getTransportCost());
                                businessTripDetailBean.setTransportName(travelExpenseDetailBean.getTransportName());
                                businessTripDetailBean.setHotelCost(travelExpenseDetailBean.getHotelCost());
                                businessTripDetailBean.setGuestsCost(travelExpenseDetailBean.getGuestsCost());
                                businessTripDetailBean.setOtherCost(travelExpenseDetailBean.getOtherCost());
                                businessTripDetailBean.setAllowance(travelExpenseDetailBean.getAllowance());
                                BusinessTripDetaillist.add(businessTripDetailBean);
                            }
                        }
                        bTripUnion.put(split[i],businessTripOrdersBean);
                    }

                }else if (split.length == 1){
                    BusinessTripOrdersEntity.RetDataBean.BusinessTripOrdersBean businessTripOrdersBean = new BusinessTripOrdersEntity.RetDataBean.BusinessTripOrdersBean();
                    ArrayList<BusinessTripOrdersEntity.RetDataBean.BusinessTripOrdersBean.BusinessTripDetailBean> businessTripDetailBeen = new ArrayList<>();
                    businessTripOrdersBean.setBarCode(split[0]);
                    for (TravelOfferDetailBean.RetDataBean.DetailDataBean.TravelExpenseDetailBean travelExpenseDetailBean : entityDetail.getTravelExpenseDetail()) {
                        if (travelExpenseDetailBean.getBusinessTripBarCode().equals(split[0])){
                            BusinessTripOrdersEntity.RetDataBean.BusinessTripOrdersBean.BusinessTripDetailBean businessTripDetailBean = new BusinessTripOrdersEntity.RetDataBean.BusinessTripOrdersBean.BusinessTripDetailBean();
                            businessTripDetailBean.setLeaveDate(travelExpenseDetailBean.getPlanLeaveDate());
                            businessTripDetailBean.setReturnDate(travelExpenseDetailBean.getPlanReturnDate());
                            businessTripDetailBean.setFrom(travelExpenseDetailBean.getFrom());
                            businessTripDetailBean.setTo(travelExpenseDetailBean.getTo());
                            businessTripDetailBean.setTicketCost(travelExpenseDetailBean.getTicketCost());
                            businessTripDetailBean.setTransportCost(travelExpenseDetailBean.getTransportCost());
                            businessTripDetailBean.setTransportName(travelExpenseDetailBean.getTransportName());
                            businessTripDetailBean.setHotelCost(travelExpenseDetailBean.getHotelCost());
                            businessTripDetailBean.setGuestsCost(travelExpenseDetailBean.getGuestsCost());
                            businessTripDetailBean.setOtherCost(travelExpenseDetailBean.getOtherCost());
                            businessTripDetailBean.setAllowance(travelExpenseDetailBean.getAllowance());
                            businessTripDetailBeen.add(businessTripDetailBean);
                        }
                    }
                    businessTripOrdersBean.setBusinessTripDetail(businessTripDetailBeen);
                    bTripUnion.put(split[0],businessTripOrdersBean);
                }
            }
            subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Related_Business_Trip_Orders), false, true, "", HandInputGroup.VALUE_TYPE.SUB_LIST).setValue(bTripUnion.keySet()).setEditable(false));

        }
        String relateHospitalitOrder = entityDetail.getRelateHospitalitOrder();
        if (!relateHospitalitOrder.isEmpty()){
            String[] split = relateHospitalitOrder.split(";");
            if (split.length > 1){
                for (int i = 0; i < split.length; i++) {
                    for (TravelOfferDetailBean.RetDataBean.DetailDataBean.TravelExpenseDetailBean travelExpenseDetailBean : entityDetail.getTravelExpenseDetail()) {
                        if (travelExpenseDetailBean.getEntertainmentBarCode().equals(split[i])){
                            EnterExpenseOrdersEntity.RetDataBean.EntertainmentExpenseOrdersBean  ent = new EnterExpenseOrdersEntity.RetDataBean.EntertainmentExpenseOrdersBean();
                            ent.setCreateTime(travelExpenseDetailBean.getActualLeaveDate());
                            ent.setBarCode(travelExpenseDetailBean.getEntertainmentBarCode());
                            ent.setAmount(travelExpenseDetailBean.getGuestsCost());
                            enterExpUnion.put(split[i],ent);
                        }
                    }

                }
            }else if (split.length == 1){
                for (TravelOfferDetailBean.RetDataBean.DetailDataBean.TravelExpenseDetailBean travelExpenseDetailBean : entityDetail.getTravelExpenseDetail()) {
                    if (travelExpenseDetailBean.getEntertainmentBarCode().equals(split[0])){
                        EnterExpenseOrdersEntity.RetDataBean.EntertainmentExpenseOrdersBean  ent = new EnterExpenseOrdersEntity.RetDataBean.EntertainmentExpenseOrdersBean();
                        ent.setCreateTime(travelExpenseDetailBean.getActualLeaveDate());
                        ent.setBarCode(travelExpenseDetailBean.getEntertainmentBarCode());
                        ent.setAmount(travelExpenseDetailBean.getGuestsCost());
                        enterExpUnion.put(split[0],ent);
                    }
                }
            }
        }
        subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Relate_Hospitalit_Order), false,true, "", HandInputGroup.VALUE_TYPE.SUB_LIST).setValue(enterExpUnion.keySet()).setEditable(false));
        subHolder.add(new HandInputGroup.Holder(this.getString(R.string.Reason), true, false, entityDetail.getReason(), HandInputGroup.VALUE_TYPE.TEXT));
        groups.add(new Group("详细信息-" + this.getString(R.string.Basic_Information), null, true, null, subHolder).setrl(false));
        for (int i = 0; i < entity.getDetailData().getTravelExpenseDetail().size(); i++) {
            TravelOfferDetailBean.RetDataBean.DetailDataBean.TravelExpenseDetailBean bean = entity.getDetailData().getTravelExpenseDetail().get(i);
            List<HandInputGroup.Holder> subHolder3 = new ArrayList<>();
            subHolder3.add(new HandInputGroup.Holder(this.getString(R.string.Leave_Date), false, false, bean.getActualLeaveDate().split(" ")[0], HandInputGroup.VALUE_TYPE.TEXT));
            subHolder3.add(new HandInputGroup.Holder(this.getString(R.string.Return_Date), false, false, bean.getActualReturnDate().split(" ")[0], HandInputGroup.VALUE_TYPE.TEXT));
            subHolder3.add(new HandInputGroup.Holder(this.getString(R.string.From), false, false, bean.getFrom(), HandInputGroup.VALUE_TYPE.TEXT));
            subHolder3.add(new HandInputGroup.Holder(this.getString(R.string.To), false, false, bean.getTo(), HandInputGroup.VALUE_TYPE.TEXT));
            subHolder3.add(new HandInputGroup.Holder(this.getString(R.string.Transport), false, false, bean.getTransportName(), HandInputGroup.VALUE_TYPE.TEXT));
            subHolder3.add(new HandInputGroup.Holder(this.getString(R.string.Air_Ticket), false, false, bean.getTicketCost().isEmpty()?"0":bean.getTicketCost(), HandInputGroup.VALUE_TYPE.TEXT));
            subHolder3.add(new HandInputGroup.Holder(this.getString(R.string.Transport_Cost), false, false, bean.getTransportCost().isEmpty()?"0":bean.getTransportCost(), HandInputGroup.VALUE_TYPE.TEXT));
            subHolder3.add(new HandInputGroup.Holder(this.getString(R.string.Hotel_Cost), false, false, bean.getHotelCost().isEmpty()?"0":bean.getHotelCost(), HandInputGroup.VALUE_TYPE.TEXT));
            subHolder3.add(new HandInputGroup.Holder(this.getString(R.string.Cost_Center), false, false, bean.getGuestsCost().isEmpty()?"0":bean.getGuestsCost(), HandInputGroup.VALUE_TYPE.TEXT));
            subHolder3.add(new HandInputGroup.Holder(this.getString(R.string.Other_Cost), false, false, bean.getOtherCost().isEmpty()?"0":bean.getOtherCost(), HandInputGroup.VALUE_TYPE.TEXT));
            subHolder3.add(new HandInputGroup.Holder(this.getString(R.string.Allowance), false, false, bean.getAllowance().isEmpty()?"0":bean.getAllowance(), HandInputGroup.VALUE_TYPE.TEXT));
            groups.add(new Group("详细信息-" + this.getString(R.string.Details_Information), null, true, null, subHolder3).setrl(false));
        }

        List<HandInputGroup.Holder> subDetail = new ArrayList<>();
        if(finance){
            double ticketCost = 0;
            double transportCost = 0;
            double hotelCost = 0;
            double guestsCost = 0;
            double otherCost = 0;
            double allowance = 0;
            List<TravelOfferDetailBean.RetDataBean.DetailDataBean.TravelExpenseDetailBean> travelExpenseDetail = entityDetail.getTravelExpenseDetail();
            for (TravelOfferDetailBean.RetDataBean.DetailDataBean.TravelExpenseDetailBean travelExpenseDetailBean : travelExpenseDetail) {
                ticketCost += Double.parseDouble(travelExpenseDetailBean.getTicketCost().isEmpty()?"0":travelExpenseDetailBean.getTicketCost());
                transportCost += Double.parseDouble(travelExpenseDetailBean.getTransportCost().isEmpty()?"0":travelExpenseDetailBean.getTransportCost());
                hotelCost += Double.parseDouble(travelExpenseDetailBean.getHotelCost().isEmpty()?"0":travelExpenseDetailBean.getHotelCost());
                guestsCost += Double.parseDouble(travelExpenseDetailBean.getGuestsCost().isEmpty()?"0":travelExpenseDetailBean.getGuestsCost());
                otherCost += Double.parseDouble(travelExpenseDetailBean.getOtherCost().isEmpty()?"0":travelExpenseDetailBean.getOtherCost());
                allowance += Double.parseDouble(travelExpenseDetailBean.getAllowance().isEmpty()?"0":travelExpenseDetailBean.getAllowance());
            }
            subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Air_Ticket), false, false, entityDetail.getTravelAuditDetail().get(0).getAuditAmount(), HandInputGroup.VALUE_TYPE.EDIT_DOUBLE).setNumber("" + ticketCost));
            subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Transport_Cost), false, false, entityDetail.getTravelAuditDetail().get(1).getAuditAmount(), HandInputGroup.VALUE_TYPE.EDIT_DOUBLE).setNumber("" + transportCost));
            if (transportCost!=0) {
                subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Transport_Cost_Rate), false, false, entityDetail.getTravelAuditDetail().get(2).getAuditAmount(), HandInputGroup.VALUE_TYPE.EDIT_DOUBLE).setNumber("3%"));
                subDetail.add(new HandInputGroup.Holder(" ", false, false, entityDetail.getTravelAuditDetail().get(3).getAuditAmount(), HandInputGroup.VALUE_TYPE.EDIT_DOUBLE).setNumber("5%"));
                subDetail.add(new HandInputGroup.Holder(" ", false, false, entityDetail.getTravelAuditDetail().get(4).getAuditAmount(), HandInputGroup.VALUE_TYPE.EDIT_DOUBLE).setNumber("不可抵扣"));
            }
            subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Hotel_Cost), false, false, entityDetail.getTravelAuditDetail().get(5).getAuditAmount(), HandInputGroup.VALUE_TYPE.EDIT_DOUBLE).setNumber("" + hotelCost));
            if ( hotelCost !=0) {
                subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Hotel_Cost_Rate), false, false,entityDetail.getTravelAuditDetail().get(6).getAuditAmount(), HandInputGroup.VALUE_TYPE.EDIT_DOUBLE).setNumber("3%"));
                subDetail.add(new HandInputGroup.Holder(" ", false, false, entityDetail.getTravelAuditDetail().get(7).getAuditAmount(), HandInputGroup.VALUE_TYPE.EDIT_DOUBLE).setNumber("5%"));
                subDetail.add(new HandInputGroup.Holder(" ", false, false, entityDetail.getTravelAuditDetail().get(8).getAuditAmount(), HandInputGroup.VALUE_TYPE.EDIT_DOUBLE).setNumber("不可抵扣"));
            }
            subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Cost_Center), false, false, entityDetail.getTravelAuditDetail().get(9).getAuditAmount(), HandInputGroup.VALUE_TYPE.EDIT_DOUBLE).setNumber("" + guestsCost));
            subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Other_Cost), false, false, entityDetail.getTravelAuditDetail().get(10).getAuditAmount(), HandInputGroup.VALUE_TYPE.EDIT_DOUBLE).setNumber("" + otherCost));
            subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Allowance), false, false, entityDetail.getTravelAuditDetail().get(11).getAuditAmount(), HandInputGroup.VALUE_TYPE.EDIT_DOUBLE).setNumber("" + allowance));
            subDetail.add(new HandInputGroup.Holder(this.getString(R.string.Audit_Amount), false, false, "", HandInputGroup.VALUE_TYPE.EDIT_DOUBLE).setNumber(entityDetail.getAuditTotalAmount().isEmpty() ? "0" : entityDetail.getAuditTotalAmount()));
        }

        groups.add(new Group("详细信息-" + this.getString(R.string.Total), null, true, entity.getDetailData().getTotalAmount(), subDetail).setrl(false));
        List<AttachmentListEntity> attachList = entity.getAttchList();
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
        toolbar.setTitle(getArguments().getString("ProcessNameCN"));
        toolbar.setDisplayHomeAsUpEnabled(true, getActivity());
    }

    public void loadData() {
        if (getArguments() != null){
            Map<String, Object> param = CommonValues.getCommonParams(getActivity());
            param.put("userId", getArguments().getString("userId"));
            param.put("barCode", getArguments().getString("barCode"));
            param.put("workflowType", getArguments().getString("workflowType"));
            HttpManager.getInstance().requestResultForm(CommonValues.REQ_TRAVEL_EXPENSE_DETAIL, param, TravelOfferDetailBean.class, new HttpManager.ResultCallback<TravelOfferDetailBean>() {
                @Override
                public void onSuccess(String content, final TravelOfferDetailBean entity) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (entity != null && entity.getRetData() != null) {
                                if (entity.getCode().equals("100")) {
                                    setEntity(entity.getRetData());
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

    public TravelOfferDetailBean.RetDataBean getEntity() {
        return entity;
    }

    public void setEntity(TravelOfferDetailBean.RetDataBean entity) {
        this.entity = entity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadData();
    }

    @Override
    public void onBarCodeChanged(int main, int index, HandInputGroup.Holder holder, String oldBarcode, SubListLayout.ActionType type, int id) {
        if(type == SubListLayout.ActionType.CLICK){
            DisplayItem(oldBarcode);
        }
    }

    private void DisplayItem(String barcode) {
        Intent intent = new Intent(getActivity(), ItemActivity.class);
        intent.putExtra(PageConfig.PAGE_CODE, barcode.startsWith("HER")?PageConfig.PAGE_DISPLAY_EXPENSE_OFFER:PageConfig.PAGE_DISPLAY_BLEAVE);
        Bundle bundle = new Bundle();
        bundle.putString("userId", GeelyApp.getLoginEntity().getUserId());
        bundle.putString("barCode", barcode);
        bundle.putString("workflowType", barcode.startsWith("HER")?"EntertainmentExpenseRequest":"BusinessTripRequest");
        intent.putExtra("data", bundle);
        startActivity(intent);
    }
}

