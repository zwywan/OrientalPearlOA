package com.geely.app.geelyapprove.activities.cost.bean;

import com.geely.app.geelyapprove.common.entity.ApproverDetailEntity;
import com.geely.app.geelyapprove.common.entity.AttachmentListEntity;
import com.geely.app.geelyapprove.common.entity.EmpDataEntity;
import com.geely.app.geelyapprove.common.entity.HisDataBean;

import java.util.List;

/**
 * Created by guiluXu on 2016/10/13.
 */
public class CostDetailBean {
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

    public void setRetData(RetDataBean retData) {
        this.retData = retData;
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
            private String RequestorName;
            private String RequestorPosition;
            private String RequestorLevel;
            private String CompanyName;
            private String PayStatusName;
            private String PayeeBankName;
            private String PayeeBankProvinceName;
            private String PayeeBankCityName;
            private String CurrencyName;
            private String CostCenterName;
            private String CashierName;
            private String Id;
            private String BarCode;
            private String Identifier;
            private String Company;
            private String CostCenter;
            private String RequestorId;
            private String PayeeId;
            private String PayeeName;
            private boolean IsOffsetLoan;
            private String PayeeBank;
            private String PayeeBankAccount;
            private String PayeeBankProvince;
            private String PayeeBankCity;
            private String PayeeBranchBank;
            private boolean IsProject;
            private String ProjectCode;
            private String ProjectName;
            private String Remark;
            private String BillPaperReceiveDate;
            private String AuditTotalAmount;
            private String TotalAmount;
            private String DealingsPayAmount;
            private String CashPayAmount;
            private String PlatPayAmount;
            private String PayStatus;
            private String Summary;
            private String SubmitBy;
            private String CreateTime;
            private String UpdateBy;
            private String UpdateTime;
            private boolean HasBuget;
            private String RelatedEntertainmentOrders;
            private boolean IsGenerateAccountingVouchers;
            private String GenerateAccountingVouchersType;
            private String GenerateAccountingVouchersTypeName;
            private String Currency;
            private String ExpenditureType;
            private String ExpenseType;
            private boolean IsGenerateVoucherSuccess;
            private String Cashier;
            private String VoucherNumber;
            private String FundReturnBankAccount;
            private String FundReturnBankName;
            private String FundReturnNote;
            private String FundReturnAmount;
            private String SubmitByBase64;
            private List<ExpenseDetailBean> ExpenseDetail;
            private List<ApproverDetailEntity> ApproverDetail;

            public String getSubmitByBase64(){
                return SubmitByBase64;
            }

            private void setSubmitByBase64(String submitByBase64){
                SubmitByBase64 = submitByBase64;
            }

            public String getRequestorName() {
                return RequestorName;
            }

            public void setRequestorName(String RequestorName) {
                this.RequestorName = RequestorName;
            }

            public String getRequestorPosition() {
                return RequestorPosition;
            }

            public void setRequestorPosition(String RequestorPosition) {
                this.RequestorPosition = RequestorPosition;
            }

            public String getRequestorLevel() {
                return RequestorLevel;
            }

            public void setRequestorLevel(String RequestorLevel) {
                this.RequestorLevel = RequestorLevel;
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

            public String getPayStatusName() {
                return PayStatusName;
            }

            public void setPayStatusName(String PayStatusName) {
                this.PayStatusName = PayStatusName;
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

            public String getRequestorId() {
                return RequestorId;
            }

            public void setRequestorId(String RequestorId) {
                this.RequestorId = RequestorId;
            }

            public String getPayeeId() {
                return PayeeId;
            }

            public void setPayeeId(String PayeeId) {
                this.PayeeId = PayeeId;
            }

            public String getPayeeName() {
                return PayeeName;
            }

            public void setPayeeName(String PayeeName) {
                this.PayeeName = PayeeName;
            }

            public boolean isIsOffsetLoan() {
                return IsOffsetLoan;
            }

            public void setIsOffsetLoan(boolean IsOffsetLoan) {
                this.IsOffsetLoan = IsOffsetLoan;
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

            public String getProjectName() {
                return ProjectName;
            }

            public void setProjectName(String ProjectName) {
                this.ProjectName = ProjectName;
            }

            public String getRemark() {
                return Remark;
            }

            public void setRemark(String Remark) {
                this.Remark = Remark;
            }

            public String getBillPaperReceiveDate() {
                return BillPaperReceiveDate;
            }

            public void setBillPaperReceiveDate(String BillPaperReceiveDate) {
                this.BillPaperReceiveDate = BillPaperReceiveDate;
            }

            public String getAuditTotalAmount() {
                return AuditTotalAmount;
            }

            public void setAuditTotalAmount(String AuditTotalAmount) {
                this.AuditTotalAmount = AuditTotalAmount;
            }

            public String getTotalAmount() {
                return TotalAmount;
            }

            public void setTotalAmount(String TotalAmount) {
                this.TotalAmount = TotalAmount;
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

            public String getSummary() {
                return Summary;
            }

            public void setSummary(String Summary) {
                this.Summary = Summary;
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

            public boolean isHasBuget() {
                return HasBuget;
            }

            public void setHasBuget(boolean HasBuget) {
                this.HasBuget = HasBuget;
            }

            public String getRelatedEntertainmentOrders() {
                return RelatedEntertainmentOrders;
            }

            public void setRelatedEntertainmentOrders(String RelatedEntertainmentOrders) {
                this.RelatedEntertainmentOrders = RelatedEntertainmentOrders;
            }

            public boolean isIsGenerateAccountingVouchers() {
                return IsGenerateAccountingVouchers;
            }

            public void setIsGenerateAccountingVouchers(boolean IsGenerateAccountingVouchers) {
                this.IsGenerateAccountingVouchers = IsGenerateAccountingVouchers;
            }

            public String getGenerateAccountingVouchersTypeName() {
                return GenerateAccountingVouchersTypeName;
            }

            public void setGenerateAccountingVouchersTypeName(String GenerateAccountingVouchersTypeName) {
                this.GenerateAccountingVouchersTypeName = GenerateAccountingVouchersTypeName;
            }

            public String getGenerateAccountingVouchersType() {
                return GenerateAccountingVouchersType;
            }

            public void setGenerateAccountingVouchersType(String GenerateAccountingVouchersType) {
                this.GenerateAccountingVouchersType = GenerateAccountingVouchersType;
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

            public String getExpenseType() {
                return ExpenseType;
            }

            public void setExpenseType(String ExpenseType) {
                this.ExpenseType = ExpenseType;
            }

            public boolean isIsGenerateVoucherSuccess() {
                return IsGenerateVoucherSuccess;
            }

            public void setIsGenerateVoucherSuccess(boolean IsGenerateVoucherSuccess) {
                this.IsGenerateVoucherSuccess = IsGenerateVoucherSuccess;
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

            public List<ExpenseDetailBean> getExpenseDetail() {
                return ExpenseDetail;
            }

            public void setExpenseDetail(List<ExpenseDetailBean> ExpenseDetail) {
                this.ExpenseDetail = ExpenseDetail;
            }

            public List<ApproverDetailEntity> getApproverDetail() {
                return ApproverDetail;
            }

            public void setApproverDetail(List<ApproverDetailEntity> ApproverDetail) {
                this.ApproverDetail = ApproverDetail;
            }

            public static class ExpenseDetailBean {
                private String ExpenseTypeName;
                private String TaxRateIdName;
                private String Id;
                private String WorkflowIdentifier;
                private String ExpenseType;
                private String ExpenseCost;
                private String AuditAmount;
                private boolean IsOffset;
                private String TaxRateId;
                private String AmountWithoutTax;
                private String AmountWithTax;
                private boolean IsFinance;
                private String EntertainmentBarCode;

                public String getExpenseTypeName() {
                    return ExpenseTypeName;
                }

                public void setExpenseTypeName(String ExpenseTypeName) {
                    this.ExpenseTypeName = ExpenseTypeName;
                }

                public String getTaxRateIdName() {
                    return TaxRateIdName;
                }

                public void setTaxRateIdName(String TaxRateIdName) {
                    this.TaxRateIdName = TaxRateIdName;
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

                public String getExpenseType() {
                    return ExpenseType;
                }

                public void setExpenseType(String ExpenseType) {
                    this.ExpenseType = ExpenseType;
                }

                public String getExpenseCost() {
                    return ExpenseCost;
                }

                public void setExpenseCost(String ExpenseCost) {
                    this.ExpenseCost = ExpenseCost;
                }

                public String getAuditAmount() {
                    return AuditAmount;
                }

                public void setAuditAmount(String AuditAmount) {
                    this.AuditAmount = AuditAmount;
                }

                public boolean isIsOffset() {
                    return IsOffset;
                }

                public void setIsOffset(boolean IsOffset) {
                    this.IsOffset = IsOffset;
                }

                public String getTaxRateId() {
                    return TaxRateId;
                }

                public void setTaxRateId(String TaxRateId) {
                    this.TaxRateId = TaxRateId;
                }

                public String getAmountWithoutTax() {
                    return AmountWithoutTax;
                }

                public void setAmountWithoutTax(String AmountWithoutTax) {
                    this.AmountWithoutTax = AmountWithoutTax;
                }

                public String getAmountWithTax() {
                    return AmountWithTax;
                }

                public void setAmountWithTax(String AmountWithTax) {
                    this.AmountWithTax = AmountWithTax;
                }

                public boolean isIsFinance() {
                    return IsFinance;
                }

                public void setIsFinance(boolean IsFinance) {
                    this.IsFinance = IsFinance;
                }

                public String getEntertainmentBarCode() {
                    return EntertainmentBarCode;
                }

                public void setEntertainmentBarCode(String EntertainmentBarCode) {
                    this.EntertainmentBarCode = EntertainmentBarCode;
                }
            }

        }


        public static class IsKeyAuditNodeBean {
            private boolean PaperReceive;//单据接收
            private boolean Finance;        //财务审核
            private boolean Cashier;        //出纳审核
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

        public static class IsKeyAuditNodeForDisplayBean {
            private boolean Finance;
            private boolean Cashier;
            private boolean PaperReceive;

            public boolean isFinance() {
                return Finance;
            }

            public void setFinance(boolean finance) {
                Finance = finance;
            }

            public boolean isCashier() {
                return Cashier;
            }

            public void setCashier(boolean cashier) {
                Cashier = cashier;
            }

            public boolean isPaperReceive() {
                return PaperReceive;
            }

            public void setPaperReceive(boolean paperReceive) {
                PaperReceive = paperReceive;
            }
        }

    }
}
