package com.geely.app.geelyapprove.activities.pay.bean;

import com.geely.app.geelyapprove.common.entity.AttachmentListEntity;
import com.geely.app.geelyapprove.common.entity.EmpDataEntity;
import com.geely.app.geelyapprove.common.entity.HisDataBean;

import java.util.List;

/**
 * Created by guiluXu on 2016/10/17.
 */
public class PayFlowDetailBean {

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
        private IsKeyAuditNodeForApproveBean IsKeyAuditNodeForApprove;
        private IsKeyAuditNodeForDisplayBean IsKeyAuditNodeForDisplay;
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

        public IsKeyAuditNodeForApproveBean getIsKeyAuditNodeForApprove() {
            return IsKeyAuditNodeForApprove;
        }

        public void setIsKeyAuditNodeForApprove(IsKeyAuditNodeForApproveBean IsKeyAuditNodeForApprove) {
            this.IsKeyAuditNodeForApprove = IsKeyAuditNodeForApprove;
        }

        public IsKeyAuditNodeForDisplayBean getIsKeyAuditNodeForDisplay() {
            return IsKeyAuditNodeForDisplay;
        }

        public void setIsKeyAuditNodeForDisplay(IsKeyAuditNodeForDisplayBean IsKeyAuditNodeForDisplay) {
            this.IsKeyAuditNodeForDisplay = IsKeyAuditNodeForDisplay;
        }

        public List<AttachmentListEntity> getAttchList() {
            return attchList;
        }

        public void setAttchList(List<AttachmentListEntity> attchList) {
            this.attchList = attchList;
        }

        public static class DetailDataBean {
            private String CurrencyName;
            private String PaymentTypeName;
            private String CompanyName;
            private String PayeeBankName;
            private String PayeeBankProvinceName;
            private String PayeeBankCityName;
            private String CostCenterName;
            private String CashierName;
            private String GenerateAccountingVouchersTypeName;
            private String PayStatusName;
            private String RequestorName;
            private String Id;
            private String BarCode;
            private String Identifier;
            private String Company;
            private String CostCenter;
            private String RequestorId;
            private String PayeeId;
            private String PaymentType;
            private String PaymentContent;
            private String PayeeBankAccount;
            private String PayeeBranchBank;
            private boolean IsInvoice;
            private String InvoiceAmount;
            private String AuditPaymentAmount;
            private String PaymentAmount;
            private String Currency;
            private String InvoiceNumber;
            private boolean IsProject;
            private String ProjectCode;
            private String ProjectName;
            private boolean HasBuget;
            private boolean IsGenerateAccountingVouchers;
            private String GenerateAccountingVouchersType;
            private String Summary;
            private String SubmitBy;
            private String CreateTime;
            private String UpdateBy;
            private String UpdateTime;
            private String Remark;
            private String BillPaperReceiveDate;
            private String PayeeBank;
            private String PayeeBankProvince;
            private String PayeeBankCity;
            private String PlatPayAmount;
            private String CashPayAmount;
            private String DealingsPayAmount;
            private String ExpenditureType;
            private String Cashier;
            private String PayStatus;
            private String VoucherNumber;
            private String FundReturnBankAccount;
            private String FundReturnBankName;
            private boolean IsGenerateVoucherSuccess;
            private String FundReturnNote;
            private String FundReturnAmount;
            private List<ApproverDetailBean> ApproverDetail;
            private String SubmitByBase64;

            public String getSubmitByBase64(){
                return SubmitByBase64;
            }

            public void setSubmitByBase64(String submitByBase64){
                SubmitByBase64 = submitByBase64;
            }

            public String getCurrencyName() {
                return CurrencyName;
            }

            public void setCurrencyName(String CurrencyName) {
                this.CurrencyName = CurrencyName;
            }

            public String getPaymentTypeName() {
                return PaymentTypeName;
            }

            public void setPaymentTypeName(String PaymentTypeName) {
                this.PaymentTypeName = PaymentTypeName;
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

            public String getGenerateAccountingVouchersTypeName() {
                return GenerateAccountingVouchersTypeName;
            }

            public void setGenerateAccountingVouchersTypeName(String GenerateAccountingVouchersTypeName) {
                this.GenerateAccountingVouchersTypeName = GenerateAccountingVouchersTypeName;
            }

            public String getPayStatusName() {
                return PayStatusName;
            }

            public void setPayStatusName(String PayStatusName) {
                this.PayStatusName = PayStatusName;
            }

            public String getRequestorName() {
                return RequestorName;
            }

            public void setRequestorName(String RequestorName) {
                this.RequestorName = RequestorName;
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

            public String getPaymentType() {
                return PaymentType;
            }

            public void setPaymentType(String PaymentType) {
                this.PaymentType = PaymentType;
            }

            public String getPaymentContent() {
                return PaymentContent;
            }

            public void setPaymentContent(String PaymentContent) {
                this.PaymentContent = PaymentContent;
            }

            public String getPayeeBankAccount() {
                return PayeeBankAccount;
            }

            public void setPayeeBankAccount(String PayeeBankAccount) {
                this.PayeeBankAccount = PayeeBankAccount;
            }

            public String getPayeeBranchBank() {
                return PayeeBranchBank;
            }

            public void setPayeeBranchBank(String PayeeBranchBank) {
                this.PayeeBranchBank = PayeeBranchBank;
            }

            public boolean isIsInvoice() {
                return IsInvoice;
            }

            public void setIsInvoice(boolean IsInvoice) {
                this.IsInvoice = IsInvoice;
            }

            public String getInvoiceAmount() {
                return InvoiceAmount;
            }

            public void setInvoiceAmount(String InvoiceAmount) {
                this.InvoiceAmount = InvoiceAmount;
            }

            public String getAuditPaymentAmount() {
                return AuditPaymentAmount;
            }

            public void setAuditPaymentAmount(String AuditPaymentAmount) {
                this.AuditPaymentAmount = AuditPaymentAmount;
            }

            public String getPaymentAmount() {
                return PaymentAmount;
            }

            public void setPaymentAmount(String PaymentAmount) {
                this.PaymentAmount = PaymentAmount;
            }

            public String getCurrency() {
                return Currency;
            }

            public void setCurrency(String Currency) {
                this.Currency = Currency;
            }

            public String getInvoiceNumber() {
                return InvoiceNumber;
            }

            public void setInvoiceNumber(String InvoiceNumber) {
                this.InvoiceNumber = InvoiceNumber;
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

            public boolean isHasBuget() {
                return HasBuget;
            }

            public void setHasBuget(boolean HasBuget) {
                this.HasBuget = HasBuget;
            }

            public boolean isIsGenerateAccountingVouchers() {
                return IsGenerateAccountingVouchers;
            }

            public void setIsGenerateAccountingVouchers(boolean IsGenerateAccountingVouchers) {
                this.IsGenerateAccountingVouchers = IsGenerateAccountingVouchers;
            }

            public String getGenerateAccountingVouchersType() {
                return GenerateAccountingVouchersType;
            }

            public void setGenerateAccountingVouchersType(String GenerateAccountingVouchersType) {
                this.GenerateAccountingVouchersType = GenerateAccountingVouchersType;
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

            public String getPayeeBank() {
                return PayeeBank;
            }

            public void setPayeeBank(String PayeeBank) {
                this.PayeeBank = PayeeBank;
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

            public String getPlatPayAmount() {
                return PlatPayAmount;
            }

            public void setPlatPayAmount(String PlatPayAmount) {
                this.PlatPayAmount = PlatPayAmount;
            }

            public String getCashPayAmount() {
                return CashPayAmount;
            }

            public void setCashPayAmount(String CashPayAmount) {
                this.CashPayAmount = CashPayAmount;
            }

            public String getDealingsPayAmount() {
                return DealingsPayAmount;
            }

            public void setDealingsPayAmount(String DealingsPayAmount) {
                this.DealingsPayAmount = DealingsPayAmount;
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

            public String getPayStatus() {
                return PayStatus;
            }

            public void setPayStatus(String PayStatus) {
                this.PayStatus = PayStatus;
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

            public List<ApproverDetailBean> getApproverDetail() {
                return ApproverDetail;
            }

            public void setApproverDetail(List<ApproverDetailBean> ApproverDetail) {
                this.ApproverDetail = ApproverDetail;
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

        public static class IsKeyAuditNodeForApproveBean {

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

        public static class IsKeyAuditNodeForDisplayBean {

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
