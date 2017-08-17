package com.geely.app.geelyapprove.activities.home.entity;

import java.util.List;

/**
 * Created by Oliver on 2016/10/31.
 */

public class MyLaunchListEntity {

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

    public void setRetData(List<RetDataBean> retdata) {
        retData = retdata;
    }

    public static class RetDataBean {
        private int Id;
        private String WorkflowIdentifier;
        private String BarCode;
        private String ProcInstID;
        private String ActInstDestID;
        private String ProcessName;
        private String ProcessNameCN;
        private String ProcessNameEN;
        private String ActivityNameCN;
        private String ActivityNameEN;
        private String Status;
        private String DeptCode;
        private String DeptNameCN;
        private String DeptNameEN;
        private String CompCode;
        private String CompNameCN;
        private String CompNameEN;
        private String UpdateTime;
        private String SubmitBy;
        private String CreateTime;
        private int SrcProcInstID;
        private String SrcBarCode;
        private String Summary;

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public String getWorkflowIdentifier() {
            return WorkflowIdentifier;
        }

        public void setWorkflowIdentifier(String WorkflowIdentifier) {
            this.WorkflowIdentifier = WorkflowIdentifier;
        }

        public String getBarCode() {
            return BarCode;
        }

        public void setBarCode(String BarCode) {
            this.BarCode = BarCode;
        }

        public String getProcInstID() {
            return ProcInstID;
        }

        public void setProcInstID(String ProcInstID) {
            this.ProcInstID = ProcInstID;
        }

        public String getActInstDestID() {
            return ActInstDestID;
        }

        public void setActInstDestID(String actInstDestID) {
            this.ActInstDestID = actInstDestID;
        }

        public String getProcessName() {
            return ProcessName;
        }

        public void setProcessName(String ProcessName) {
            this.ProcessName = ProcessName;
        }

        public String getProcessNameCN() {
            return ProcessNameCN;
        }

        public void setProcessNameCN(String ProcessNameCN) {
            this.ProcessNameCN = ProcessNameCN;
        }

        public String getProcessNameEN() {
            return ProcessNameEN;
        }

        public void setProcessNameEN(String ProcessNameEN) {
            this.ProcessNameEN = ProcessNameEN;
        }

        public String getActivityNameCN() {
            return ActivityNameCN;
        }

        public void setActivityNameCN(String ActivityNameCN) {
            this.ActivityNameCN = ActivityNameCN;
        }

        public String getActivityNameEN() {
            return ActivityNameEN;
        }

        public void setActivityNameEN(String ActivityNameEN) {
            this.ActivityNameEN = ActivityNameEN;
        }

        public String getStatus() {
            return Status;
        }

        public void setStatus(String Status) {
            this.Status = Status;
        }

        public String getDeptCode() {
            return DeptCode;
        }

        public void setDeptCode(String DeptCode) {
            this.DeptCode = DeptCode;
        }

        public String getDeptNameCN() {
            return DeptNameCN;
        }

        public void setDeptNameCN(String DeptNameCN) {
            this.DeptNameCN = DeptNameCN;
        }

        public String getDeptNameEN() {
            return DeptNameEN;
        }

        public void setDeptNameEN(String DeptNameEN) {
            this.DeptNameEN = DeptNameEN;
        }

        public String getCompCode() {
            return CompCode;
        }

        public void setCompCode(String CompCode) {
            this.CompCode = CompCode;
        }

        public String getCompNameCN() {
            return CompNameCN;
        }

        public void setCompNameCN(String CompNameCN) {
            this.CompNameCN = CompNameCN;
        }

        public String getCompNameEN() {
            return CompNameEN;
        }

        public void setCompNameEN(String CompNameEN) {
            this.CompNameEN = CompNameEN;
        }

        public String getUpdateTime() {
            return UpdateTime;
        }

        public void setUpdateTime(String UpdateTime) {
            this.UpdateTime = UpdateTime;
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

        public int getSrcProcInstID() {
            return SrcProcInstID;
        }

        public void setSrcProcInstID(int SrcProcInstID) {
            this.SrcProcInstID = SrcProcInstID;
        }

        public String getSrcBarCode() {
            return SrcBarCode;
        }

        public void setSrcBarCode(String SrcBarCode) {
            this.SrcBarCode = SrcBarCode;
        }

        public String getSummary() {
            return Summary;
        }

        public void setSummary(String Summary) {
            this.Summary = Summary;
        }
    }
}
