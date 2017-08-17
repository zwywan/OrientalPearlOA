package com.geely.app.geelyapprove.activities.traveloffer.bean;

import com.geely.app.geelyapprove.common.entity.AttachmentListEntity;
import com.geely.app.geelyapprove.common.entity.EmpDataEntity;
import com.geely.app.geelyapprove.common.entity.HisDataBean;

import java.util.List;

/**
 * Created by guiluXu on 2016/11/8.
 */
public class TravelOfferDetailBean {

    private String code;
    private String msg;
    private String apiName;
    private String deviceId;
    private String userId;
    private String sign;
    private RetDataBean retData;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public RetDataBean getRetData() {
        return retData;
    }

    public void setRetData(RetDataBean retdata) {
        retData = retdata;
    }

    public static class RetDataBean {

        private DetailDataBean detailData;
        private EmpDataEntity empData;
        private HisDataBean hisData;
        private IsKeyAuditNodeBean IsKeyAuditNodeForApprove;
        private IsKeyAuditNodeBean IsKeyAuditNodeForDisplay;
        private List<AttachmentListEntity> attchList;

        public DetailDataBean getDetailData() {
            return detailData;
        }

        public void setDetailData(DetailDataBean detailData) {
            this.detailData = detailData;
        }

        public EmpDataEntity getEmpData() {
            return empData;
        }

        public void setEmpData(EmpDataEntity empData) {
            this.empData = empData;
        }

        public HisDataBean getHisData() {
            return hisData;
        }

        public void setHisData(HisDataBean hisData) {
            this.hisData = hisData;
        }

        public IsKeyAuditNodeBean getIsKeyAuditNodeForApprove() {
            return IsKeyAuditNodeForApprove;
        }

        public void setIsKeyAuditNodeForApprove(IsKeyAuditNodeBean isKeyAuditNodeForApprove) {
            IsKeyAuditNodeForApprove = isKeyAuditNodeForApprove;
        }

        public IsKeyAuditNodeBean getIsKeyAuditNodeForDisplay() {
            return IsKeyAuditNodeForDisplay;
        }

        public void setIsKeyAuditNodeForDisplay(IsKeyAuditNodeBean isKeyAuditNodeForDisplay) {
            IsKeyAuditNodeForDisplay = isKeyAuditNodeForDisplay;
        }

        public List<AttachmentListEntity> getAttchList() {
            return attchList;
        }

        public void setAttchList(List<AttachmentListEntity> attchList) {
            this.attchList = attchList;
        }

        public static class DetailDataBean {
            private String CompanyName;
            private String PayeeBankName;
            private String PayeeBankProvinceName;
            private String PayeeBankCityName;
            private String CurrencyName;
            private String CostCenterName;
            private String CashierName;
            private String ProjectName;
            private String Id;
            private String SubmitByBase64;
            private String BarCode;
            private String Identifier;
            private String Company;
            private String CostCenter;
            private String Applicant;
            private String PayeeEID;
            private String PayeeName;
            private String PayeeBank;
            private String PayeeBankAccount;
            private String PayeeBankProvince;
            private String PayeeBankCity;
            private String PayeeBranchBank;
            private boolean IsDomestic;
            private boolean HasAccommodation;
            private boolean HasBorrow;
            private boolean IsProject;
            private String ProjectCode;
            private boolean IsCompliance;
            private String RelatedBusinessTripOrders;
            private String RelateHospitalitOrder;
            private String Reason;
            private String TotalAmount;
            private String AuditTotalAmount;
            private String ReceivedDate;
            private String DealingsPayAmount;
            private String CashPayAmount;
            private String PlatPayAmount;
            private String PayStatus;
            private String SubmitBy;
            private String CreateTime;
            private String UpdateBy;
            private String UpdateTime;
            private String Summary;
            private boolean IsRenderAccountant;
            private String GenerateAccountingVouchersType;
            private String GenerateAccountingVouchersTypeName;
            private String ExpenseType;
            private String Currency;
            private String ExpenditureType;
            private String Cashier;
            private String VoucherNumber;
            private String FundReturnBankAccount;
            private String FundReturnBankName;
            private boolean IsGenerateVoucherSuccess;
            private String FundReturnNote;
            private String FundReturnAmount;
            private List<TravelExpenseDetailBean> TravelExpenseDetail;
            private List<TravelAuditDetailBean> TravelAuditDetail;
            private List<ApproverDetailBean> ApproverDetail;
            private String IsComplianceForApp;

            public String getIsComplianceForApp(){
                return IsComplianceForApp;
            }

            public void setIsComplianceForApp(String isComplianceForApp){
                IsComplianceForApp = isComplianceForApp;
            }

            public String getCompanyName() {
                return CompanyName;
            }

            public void setCompanyName(String CompanyName) {
                this.CompanyName = CompanyName;
            }

            public String getPayeeBankName() {
                return PayeeBankName;
            }

            public void setPayeeBankName(String PayeeBankName) {
                this.PayeeBankName = PayeeBankName;
            }

            public String getSubmitByBase64() {
                return SubmitByBase64;
            }

            public void setSubmitByBase64(String SubmitByBase64) {
                this.SubmitByBase64 = SubmitByBase64;
            }

            public String getPayeeBankProvinceName() {
                return PayeeBankProvinceName;
            }

            public void setPayeeBankProvinceName(String PayeeBankProvinceName) {
                this.PayeeBankProvinceName = PayeeBankProvinceName;
            }

            public String getPayeeBankCityName() {
                return PayeeBankCityName;
            }

            public void setPayeeBankCityName(String PayeeBankCityName) {
                this.PayeeBankCityName = PayeeBankCityName;
            }

            public String getCurrencyName() {
                return CurrencyName;
            }

            public void setCurrencyName(String CurrencyName) {
                this.CurrencyName = CurrencyName;
            }

            public String getCostCenterName() {
                return CostCenterName;
            }

            public void setCostCenterName(String CostCenterName) {
                this.CostCenterName = CostCenterName;
            }

            public String getCashierName() {
                return CashierName;
            }

            public void setCashierName(String CashierName) {
                this.CashierName = CashierName;
            }

            public String getProjectName() {
                return ProjectName;
            }

            public void setProjectName(String ProjectName) {
                this.ProjectName = ProjectName;
            }

            public String getId() {
                return Id;
            }

            public void setId(String Id) {
                this.Id = Id;
            }

            public String getBarCode() {
                return BarCode;
            }

            public void setBarCode(String BarCode) {
                this.BarCode = BarCode;
            }

            public String getIdentifier() {
                return Identifier;
            }

            public void setIdentifier(String Identifier) {
                this.Identifier = Identifier;
            }

            public String getCompany() {
                return Company;
            }

            public void setCompany(String Company) {
                this.Company = Company;
            }

            public String getCostCenter() {
                return CostCenter;
            }

            public void setCostCenter(String CostCenter) {
                this.CostCenter = CostCenter;
            }

            public String getApplicant() {
                return Applicant;
            }

            public void setApplicant(String Applicant) {
                this.Applicant = Applicant;
            }

            public String getPayeeEID() {
                return PayeeEID;
            }

            public void setPayeeEID(String PayeeEID) {
                this.PayeeEID = PayeeEID;
            }

            public String getPayeeName() {
                return PayeeName;
            }

            public void setPayeeName(String PayeeName) {
                this.PayeeName = PayeeName;
            }

            public String getPayeeBank() {
                return PayeeBank;
            }

            public void setPayeeBank(String PayeeBank) {
                this.PayeeBank = PayeeBank;
            }

            public String getPayeeBankAccount() {
                return PayeeBankAccount;
            }

            public void setPayeeBankAccount(String PayeeBankAccount) {
                this.PayeeBankAccount = PayeeBankAccount;
            }

            public String getPayeeBankProvince() {
                return PayeeBankProvince;
            }

            public void setPayeeBankProvince(String PayeeBankProvince) {
                this.PayeeBankProvince = PayeeBankProvince;
            }

            public String getPayeeBankCity() {
                return PayeeBankCity;
            }

            public void setPayeeBankCity(String PayeeBankCity) {
                this.PayeeBankCity = PayeeBankCity;
            }

            public String getPayeeBranchBank() {
                return PayeeBranchBank;
            }

            public void setPayeeBranchBank(String PayeeBranchBank) {
                this.PayeeBranchBank = PayeeBranchBank;
            }

            public boolean isIsDomestic() {
                return IsDomestic;
            }

            public void setIsDomestic(boolean IsDomestic) {
                this.IsDomestic = IsDomestic;
            }

            public boolean isHasAccommodation() {
                return HasAccommodation;
            }

            public void setHasAccommodation(boolean HasAccommodation) {
                this.HasAccommodation = HasAccommodation;
            }

            public boolean isHasBorrow() {
                return HasBorrow;
            }

            public void setHasBorrow(boolean HasBorrow) {
                this.HasBorrow = HasBorrow;
            }

            public boolean isIsProject() {
                return IsProject;
            }

            public void setIsProject(boolean IsProject) {
                this.IsProject = IsProject;
            }

            public String getProjectCode() {
                return ProjectCode;
            }

            public void setProjectCode(String ProjectCode) {
                this.ProjectCode = ProjectCode;
            }

            public boolean isIsCompliance() {
                return IsCompliance;
            }

            public void setIsCompliance(boolean IsCompliance) {
                this.IsCompliance = IsCompliance;
            }

            public String getRelatedBusinessTripOrders() {
                return RelatedBusinessTripOrders;
            }

            public void setRelatedBusinessTripOrders(String RelatedBusinessTripOrders) {
                this.RelatedBusinessTripOrders = RelatedBusinessTripOrders;
            }

            public String getRelateHospitalitOrder() {
                return RelateHospitalitOrder;
            }

            public void setRelateHospitalitOrder(String RelateHospitalitOrder) {
                this.RelateHospitalitOrder = RelateHospitalitOrder;
            }

            public String getReason() {
                return Reason;
            }

            public void setReason(String Reason) {
                this.Reason = Reason;
            }

            public String getTotalAmount() {
                return TotalAmount;
            }

            public void setTotalAmount(String TotalAmount) {
                this.TotalAmount = TotalAmount;
            }

            public String getAuditTotalAmount() {
                return AuditTotalAmount;
            }

            public void setAuditTotalAmount(String AuditTotalAmount) {
                this.AuditTotalAmount = AuditTotalAmount;
            }

            public String getReceivedDate() {
                return ReceivedDate;
            }

            public void setReceivedDate(String ReceivedDate) {
                this.ReceivedDate = ReceivedDate;
            }

            public String getDealingsPayAmount() {
                return DealingsPayAmount;
            }

            public void setDealingsPayAmount(String DealingsPayAmount) {
                this.DealingsPayAmount = DealingsPayAmount;
            }

            public String getCashPayAmount() {
                return CashPayAmount;
            }

            public void setCashPayAmount(String CashPayAmount) {
                this.CashPayAmount = CashPayAmount;
            }

            public String getPlatPayAmount() {
                return PlatPayAmount;
            }

            public void setPlatPayAmount(String PlatPayAmount) {
                this.PlatPayAmount = PlatPayAmount;
            }

            public String getPayStatus() {
                return PayStatus;
            }

            public void setPayStatus(String PayStatus) {
                this.PayStatus = PayStatus;
            }

            public String getSubmitBy() {
                return SubmitBy;
            }

            public void setSubmitBy(String SubmitBy) {
                this.SubmitBy = SubmitBy;
            }

            public String getCreateTime() {
                return CreateTime;
            }

            public void setCreateTime(String CreateTime) {
                this.CreateTime = CreateTime;
            }

            public String getUpdateBy() {
                return UpdateBy;
            }

            public void setUpdateBy(String UpdateBy) {
                this.UpdateBy = UpdateBy;
            }

            public String getUpdateTime() {
                return UpdateTime;
            }

            public void setUpdateTime(String UpdateTime) {
                this.UpdateTime = UpdateTime;
            }

            public String getSummary() {
                return Summary;
            }

            public void setSummary(String Summary) {
                this.Summary = Summary;
            }

            public boolean isIsRenderAccountant() {
                return IsRenderAccountant;
            }

            public void setIsRenderAccountant(boolean IsRenderAccountant) {
                this.IsRenderAccountant = IsRenderAccountant;
            }

            public String getGenerateAccountingVouchersType() {
                return GenerateAccountingVouchersType;
            }

            public void setGenerateAccountingVouchersType(String GenerateAccountingVouchersType) {
                this.GenerateAccountingVouchersType = GenerateAccountingVouchersType;
            }

            public String getGenerateAccountingVouchersTypeName() {
                return GenerateAccountingVouchersTypeName;
            }

            public void setGenerateAccountingVouchersTypeName(String GenerateAccountingVouchersTypeName) {
                this.GenerateAccountingVouchersTypeName = GenerateAccountingVouchersTypeName;
            }

            public String getExpenseType() {
                return ExpenseType;
            }

            public void setExpenseType(String ExpenseType) {
                this.ExpenseType = ExpenseType;
            }

            public String getCurrency() {
                return Currency;
            }

            public void setCurrency(String Currency) {
                this.Currency = Currency;
            }

            public String getExpenditureType() {
                return ExpenditureType;
            }

            public void setExpenditureType(String ExpenditureType) {
                this.ExpenditureType = ExpenditureType;
            }

            public String getCashier() {
                return Cashier;
            }

            public void setCashier(String Cashier) {
                this.Cashier = Cashier;
            }

            public String getVoucherNumber() {
                return VoucherNumber;
            }

            public void setVoucherNumber(String VoucherNumber) {
                this.VoucherNumber = VoucherNumber;
            }

            public String getFundReturnBankAccount() {
                return FundReturnBankAccount;
            }

            public void setFundReturnBankAccount(String FundReturnBankAccount) {
                this.FundReturnBankAccount = FundReturnBankAccount;
            }

            public String getFundReturnBankName() {
                return FundReturnBankName;
            }

            public void setFundReturnBankName(String FundReturnBankName) {
                this.FundReturnBankName = FundReturnBankName;
            }

            public boolean isIsGenerateVoucherSuccess() {
                return IsGenerateVoucherSuccess;
            }

            public void setIsGenerateVoucherSuccess(boolean IsGenerateVoucherSuccess) {
                this.IsGenerateVoucherSuccess = IsGenerateVoucherSuccess;
            }

            public String getFundReturnNote() {
                return FundReturnNote;
            }

            public void setFundReturnNote(String FundReturnNote) {
                this.FundReturnNote = FundReturnNote;
            }

            public String getFundReturnAmount() {
                return FundReturnAmount;
            }

            public void setFundReturnAmount(String FundReturnAmount) {
                this.FundReturnAmount = FundReturnAmount;
            }

            public List<TravelExpenseDetailBean> getTravelExpenseDetail() {
                return TravelExpenseDetail;
            }

            public void setTravelExpenseDetail(List<TravelExpenseDetailBean> TravelExpenseDetail) {
                this.TravelExpenseDetail = TravelExpenseDetail;
            }

            public List<TravelAuditDetailBean> getTravelAuditDetail() {
                return TravelAuditDetail;
            }

            public void setTravelAuditDetail(List<TravelAuditDetailBean> TravelAuditDetail) {
                this.TravelAuditDetail = TravelAuditDetail;
            }

            public List<ApproverDetailBean> getApproverDetail() {
                return ApproverDetail;
            }

            public void setApproverDetail(List<ApproverDetailBean> ApproverDetail) {
                this.ApproverDetail = ApproverDetail;
            }

            public static class TravelExpenseDetailBean {
                private String TransportName;
                private String Id;
                private String WorkflowIdentifier;
                private String From;
                private String To;
                private String Transport;
                private String TicketCost;
                private String TransportCost;
                private String TransportCost_3;
                private String TransportCost_5;
                private String TransportCost_NoDeductible;
                private String HotelCost;
                private String HotelCost_3;
                private String HotelCost_6;
                private String HotelCost_NoDeductible;
                private String GuestsCost;
                private String OtherCost;
                private String Allowance;
                private String PlanLeaveDate;
                private String PlanReturnDate;
                private String PlanLeaveDay;
                private String ActualLeaveDate;
                private String ActualReturnDate;
                private String ActualLeaveDay;
                private String LeaveType;
                private String BusinessTripBarCode;
                private String EntertainmentBarCode;

                public String getTransportName() {
                    return TransportName;
                }

                public void setTransportName(String TransportName) {
                    this.TransportName = TransportName;
                }

                public String getId() {
                    return Id;
                }

                public void setId(String Id) {
                    this.Id = Id;
                }

                public String getWorkflowIdentifier() {
                    return WorkflowIdentifier;
                }

                public void setWorkflowIdentifier(String WorkflowIdentifier) {
                    this.WorkflowIdentifier = WorkflowIdentifier;
                }

                public String getFrom() {
                    return From;
                }

                public void setFrom(String From) {
                    this.From = From;
                }

                public String getTo() {
                    return To;
                }

                public void setTo(String To) {
                    this.To = To;
                }

                public String getTransport() {
                    return Transport;
                }

                public void setTransport(String Transport) {
                    this.Transport = Transport;
                }

                public String getTicketCost() {
                    return TicketCost;
                }

                public void setTicketCost(String TicketCost) {
                    this.TicketCost = TicketCost;
                }

                public String getTransportCost() {
                    return TransportCost;
                }

                public void setTransportCost(String TransportCost) {
                    this.TransportCost = TransportCost;
                }

                public String getTransportCost_3() {
                    return TransportCost_3;
                }

                public void setTransportCost_3(String TransportCost_3) {
                    this.TransportCost_3 = TransportCost_3;
                }

                public String getTransportCost_5() {
                    return TransportCost_5;
                }

                public void setTransportCost_5(String TransportCost_5) {
                    this.TransportCost_5 = TransportCost_5;
                }

                public String getTransportCost_NoDeductible() {
                    return TransportCost_NoDeductible;
                }

                public void setTransportCost_NoDeductible(String TransportCost_NoDeductible) {
                    this.TransportCost_NoDeductible = TransportCost_NoDeductible;
                }

                public String getHotelCost() {
                    return HotelCost;
                }

                public void setHotelCost(String HotelCost) {
                    this.HotelCost = HotelCost;
                }

                public String getHotelCost_3() {
                    return HotelCost_3;
                }

                public void setHotelCost_3(String HotelCost_3) {
                    this.HotelCost_3 = HotelCost_3;
                }

                public String getHotelCost_6() {
                    return HotelCost_6;
                }

                public void setHotelCost_6(String HotelCost_6) {
                    this.HotelCost_6 = HotelCost_6;
                }

                public String getHotelCost_NoDeductible() {
                    return HotelCost_NoDeductible;
                }

                public void setHotelCost_NoDeductible(String HotelCost_NoDeductible) {
                    this.HotelCost_NoDeductible = HotelCost_NoDeductible;
                }

                public String getGuestsCost() {
                    return GuestsCost;
                }

                public void setGuestsCost(String GuestsCost) {
                    this.GuestsCost = GuestsCost;
                }

                public String getOtherCost() {
                    return OtherCost;
                }

                public void setOtherCost(String OtherCost) {
                    this.OtherCost = OtherCost;
                }

                public String getAllowance() {
                    return Allowance;
                }

                public void setAllowance(String Allowance) {
                    this.Allowance = Allowance;
                }

                public String getPlanLeaveDate() {
                    return PlanLeaveDate;
                }

                public void setPlanLeaveDate(String PlanLeaveDate) {
                    this.PlanLeaveDate = PlanLeaveDate;
                }

                public String getPlanReturnDate() {
                    return PlanReturnDate;
                }

                public void setPlanReturnDate(String PlanReturnDate) {
                    this.PlanReturnDate = PlanReturnDate;
                }

                public String getPlanLeaveDay() {
                    return PlanLeaveDay;
                }

                public void setPlanLeaveDay(String PlanLeaveDay) {
                    this.PlanLeaveDay = PlanLeaveDay;
                }

                public String getActualLeaveDate() {
                    return ActualLeaveDate;
                }

                public void setActualLeaveDate(String ActualLeaveDate) {
                    this.ActualLeaveDate = ActualLeaveDate;
                }

                public String getActualReturnDate() {
                    return ActualReturnDate;
                }

                public void setActualReturnDate(String ActualReturnDate) {
                    this.ActualReturnDate = ActualReturnDate;
                }

                public String getActualLeaveDay() {
                    return ActualLeaveDay;
                }

                public void setActualLeaveDay(String ActualLeaveDay) {
                    this.ActualLeaveDay = ActualLeaveDay;
                }

                public String getLeaveType() {
                    return LeaveType;
                }

                public void setLeaveType(String LeaveType) {
                    this.LeaveType = LeaveType;
                }

                public String getBusinessTripBarCode() {
                    return BusinessTripBarCode;
                }

                public void setBusinessTripBarCode(String BusinessTripBarCode) {
                    this.BusinessTripBarCode = BusinessTripBarCode;
                }

                public String getEntertainmentBarCode() {
                    return EntertainmentBarCode;
                }

                public void setEntertainmentBarCode(String EntertainmentBarCode) {
                    this.EntertainmentBarCode = EntertainmentBarCode;
                }
            }

            public static class TravelAuditDetailBean {
                private String AuditExpenseTypeName;
                private String Id;
                private String Identifier;
                private String ExpenseType;
                private String AuditAmount;
                private String AuditExpenseType;

                public String getAuditExpenseTypeName() {
                    return AuditExpenseTypeName;
                }

                public void setAuditExpenseTypeName(String AuditExpenseTypeName) {
                    this.AuditExpenseTypeName = AuditExpenseTypeName;
                }

                public String getId() {
                    return Id;
                }

                public void setId(String Id) {
                    this.Id = Id;
                }

                public String getIdentifier() {
                    return Identifier;
                }

                public void setIdentifier(String Identifier) {
                    this.Identifier = Identifier;
                }

                public String getExpenseType() {
                    return ExpenseType;
                }

                public void setExpenseType(String ExpenseType) {
                    this.ExpenseType = ExpenseType;
                }

                public String getAuditAmount() {
                    return AuditAmount;
                }

                public void setAuditAmount(String AuditAmount) {
                    this.AuditAmount = AuditAmount;
                }

                public String getAuditExpenseType() {
                    return AuditExpenseType;
                }

                public void setAuditExpenseType(String AuditExpenseType) {
                    this.AuditExpenseType = AuditExpenseType;
                }
            }

            public static class ApproverDetailBean {
                private String NameCN;
                private String NameEN;
                private String Id;
                private String WorkflowIdentifier;
                private String PName;
                private String PValue;

                public String getNameCN() {
                    return NameCN;
                }

                public void setNameCN(String NameCN) {
                    this.NameCN = NameCN;
                }

                public String getNameEN() {
                    return NameEN;
                }

                public void setNameEN(String NameEN) {
                    this.NameEN = NameEN;
                }

                public String getId() {
                    return Id;
                }

                public void setId(String Id) {
                    this.Id = Id;
                }

                public String getWorkflowIdentifier() {
                    return WorkflowIdentifier;
                }

                public void setWorkflowIdentifier(String WorkflowIdentifier) {
                    this.WorkflowIdentifier = WorkflowIdentifier;
                }

                public String getPName() {
                    return PName;
                }

                public void setPName(String PName) {
                    this.PName = PName;
                }

                public String getPValue() {
                    return PValue;
                }

                public void setPValue(String PValue) {
                    this.PValue = PValue;
                }
            }
        }

        public static class IsKeyAuditNodeBean {

            private boolean Finance;
            private boolean Cashier;
            private boolean PaperReceive;
            private boolean GLAccount;      //总账

            public boolean isGLAccount(){
                return GLAccount;
            }

            public void setGLAccount(boolean glAccount){
                GLAccount = glAccount;
            }

            public boolean isFinance() {
                return Finance;
            }

            public void setFinance(boolean Finance) {
                this.Finance = Finance;
            }

            public boolean isCashier() {
                return Cashier;
            }

            public void setCashier(boolean Cashier) {
                this.Cashier = Cashier;
            }

            public boolean isPaperReceive() {
                return PaperReceive;
            }

            public void setPaperReceive(boolean PaperReceive) {
                this.PaperReceive = PaperReceive;
            }
        }
    }
}
