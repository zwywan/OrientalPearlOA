package com.geely.app.geelyapprove.common.entity;

import java.util.List;

/**
 * Created by zhy on 2017/1/20.
 */

public class PersonalAccountEntity {

    private String code;
    private String msg;
    private String apiName;
    private String deviceId;
    private String userId;
    private String sign;
    private List<RetDataBean> retData;

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

    public List<RetDataBean> getRetData() {
        return retData;
    }

    public void setRetData(List<RetDataBean> retData) {
        this.retData = retData;
    }

    public static class RetDataBean {

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
        private String FromSystem;

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

        public String getFromSystem() {
            return FromSystem;
        }

        public void setFromSystem(String FromSystem) {
            this.FromSystem = FromSystem;
        }
    }
}
