package com.geely.app.geelyapprove.common.entity;

/**
 * Created by Oliver on 2016/11/21.
 */

public class RequestorIdInfoEntity {

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
    }
}
