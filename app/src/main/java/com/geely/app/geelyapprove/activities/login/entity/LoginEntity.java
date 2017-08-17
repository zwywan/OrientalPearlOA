package com.geely.app.geelyapprove.activities.login.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Oliver on 2016/9/13.
 */
public class LoginEntity implements Serializable {

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

        private UserInfoBean UserInfo;
        private List<UserAccountBean> UserAccount;

        public UserInfoBean getUserInfo() {
            return UserInfo;
        }

        public void setUserInfo(UserInfoBean UserInfo) {
            this.UserInfo = UserInfo;
        }

        public List<UserAccountBean> getUserAccount() {
            return UserAccount;
        }

        public void setUserAccount(List<UserAccountBean> UserAccount) {
            this.UserAccount = UserAccount;
        }

        public static class UserInfoBean {

            private String Id;
            private String UserId;
            private String Eid;
            private String NameEN;
            private String NameCN;
            private String SEX;
            private String DeptCode;
            private String DeptNameEN;
            private String DeptNameCN;
            private String ReportTo;
            private String Email;
            private String CompCode;
            private String CompNameEN;
            private String CompNameCN;
            private String OrgCode;
            private String BU;
            private String Password;
            private String UpdateTime;
            private String CreateTime;
            private boolean OOF;
            private boolean Active;
            private String PositionNameCN;
            private String PositionNameEN;
            private String Grade;
            private String TelePhoneNBR;
            private String MobilePhoneNBR;

            public String getId() {
                return Id;
            }

            public void setId(String Id) {
                this.Id = Id;
            }

            public String getUserId() {
                return UserId;
            }

            public void setUserId(String UserId) {
                this.UserId = UserId;
            }

            public String getEid() {
                return Eid;
            }

            public void setEid(String Eid) {
                this.Eid = Eid;
            }

            public String getNameEN() {
                return NameEN;
            }

            public void setNameEN(String NameEN) {
                this.NameEN = NameEN;
            }

            public String getNameCN() {
                return NameCN;
            }

            public void setNameCN(String NameCN) {
                this.NameCN = NameCN;
            }

            public String getSEX() {
                return SEX;
            }

            public void setSEX(String SEX) {
                this.SEX = SEX;
            }

            public String getDeptCode() {
                return DeptCode;
            }

            public void setDeptCode(String DeptCode) {
                this.DeptCode = DeptCode;
            }

            public String getDeptNameEN() {
                return DeptNameEN;
            }

            public void setDeptNameEN(String DeptNameEN) {
                this.DeptNameEN = DeptNameEN;
            }

            public String getDeptNameCN() {
                return DeptNameCN;
            }

            public void setDeptNameCN(String DeptNameCN) {
                this.DeptNameCN = DeptNameCN;
            }

            public String getReportTo() {
                return ReportTo;
            }

            public void setReportTo(String ReportTo) {
                this.ReportTo = ReportTo;
            }

            public String getEmail() {
                return Email;
            }

            public void setEmail(String Email) {
                this.Email = Email;
            }

            public String getCompCode() {
                return CompCode;
            }

            public void setCompCode(String CompCode) {
                this.CompCode = CompCode;
            }

            public String getCompNameEN() {
                return CompNameEN;
            }

            public void setCompNameEN(String CompNameEN) {
                this.CompNameEN = CompNameEN;
            }

            public String getCompNameCN() {
                return CompNameCN;
            }

            public void setCompNameCN(String CompNameCN) {
                this.CompNameCN = CompNameCN;
            }

            public String getOrgCode() {
                return OrgCode;
            }

            public void setOrgCode(String OrgCode) {
                this.OrgCode = OrgCode;
            }

            public String getBU() {
                return BU;
            }

            public void setBU(String BU) {
                this.BU = BU;
            }

            public String getPassword() {
                return Password;
            }

            public void setPassword(String Password) {
                this.Password = Password;
            }

            public String getUpdateTime() {
                return UpdateTime;
            }

            public void setUpdateTime(String UpdateTime) {
                this.UpdateTime = UpdateTime;
            }

            public String getCreateTime() {
                return CreateTime;
            }

            public void setCreateTime(String CreateTime) {
                this.CreateTime = CreateTime;
            }

            public boolean isOOF() {
                return OOF;
            }

            public void setOOF(boolean OOF) {
                this.OOF = OOF;
            }

            public boolean isActive() {
                return Active;
            }

            public void setActive(boolean Active) {
                this.Active = Active;
            }

            public String getPositionNameCN() {
                return PositionNameCN;
            }

            public void setPositionNameCN(String PositionNameCN) {
                this.PositionNameCN = PositionNameCN;
            }

            public String getPositionNameEN() {
                return PositionNameEN;
            }

            public void setPositionNameEN(String PositionNameEN) {
                this.PositionNameEN = PositionNameEN;
            }

            public String getGrade() {
                return Grade;
            }

            public void setGrade(String Grade) {
                this.Grade = Grade;
            }

            public String getTelePhoneNBR() {
                return TelePhoneNBR;
            }

            public void setTelePhoneNBR(String TelePhoneNBR) {
                this.TelePhoneNBR = TelePhoneNBR;
            }

            public String getMobilePhoneNBR() {
                return MobilePhoneNBR;
            }

            public void setMobilePhoneNBR(String MobilePhoneNBR) {
                this.MobilePhoneNBR = MobilePhoneNBR;
            }
        }

        public static class UserAccountBean {

            private String Id;
            private String PayeeBankProvince;
            private String ProvinceName;
            private String PayeeBankCity;
            private String CityName;
            private String PayeeBank;
            private String PayeeBankName;
            private String PayeeBranchBank;
            private String AccountName;
            private String AccountNumber;
            private String UserID;
            private String DefaultTag;
            private boolean FromSystem;

            public String getId() {
                return Id;
            }

            public void setId(String Id) {
                this.Id = Id;
            }

            public String getPayeeBankProvince() {
                return PayeeBankProvince;
            }

            public void setPayeeBankProvince(String PayeeBankProvince) {
                this.PayeeBankProvince = PayeeBankProvince;
            }

            public String getProvinceName() {
                return ProvinceName;
            }

            public void setProvinceName(String ProvinceName) {
                this.ProvinceName = ProvinceName;
            }

            public String getPayeeBankCity() {
                return PayeeBankCity;
            }

            public void setPayeeBankCity(String PayeeBankCity) {
                this.PayeeBankCity = PayeeBankCity;
            }

            public String getCityName() {
                return CityName;
            }

            public void setCityName(String CityName) {
                this.CityName = CityName;
            }

            public String getPayeeBank() {
                return PayeeBank;
            }

            public void setPayeeBank(String PayeeBank) {
                this.PayeeBank = PayeeBank;
            }

            public String getPayeeBankName() {
                return PayeeBankName;
            }

            public void setPayeeBankName(String PayeeBankName) {
                this.PayeeBankName = PayeeBankName;
            }

            public String getPayeeBranchBank() {
                return PayeeBranchBank;
            }

            public void setPayeeBranchBank(String PayeeBranchBank) {
                this.PayeeBranchBank = PayeeBranchBank;
            }

            public String getAccountName() {
                return AccountName;
            }

            public void setAccountName(String AccountName) {
                this.AccountName = AccountName;
            }

            public String getAccountNumber() {
                return AccountNumber;
            }

            public void setAccountNumber(String AccountNumber) {
                this.AccountNumber = AccountNumber;
            }

            public String getUserID() {
                return UserID;
            }

            public void setUserID(String UserID) {
                this.UserID = UserID;
            }

            public String getDefaultTag() {
                return DefaultTag;
            }

            public void setDefaultTag(String DefaultTag) {
                this.DefaultTag = DefaultTag;
            }

            public boolean isFromSystem() {
                return FromSystem;
            }

            public void setFromSystem(boolean FromSystem) {
                this.FromSystem = FromSystem;
            }
        }
    }
}
