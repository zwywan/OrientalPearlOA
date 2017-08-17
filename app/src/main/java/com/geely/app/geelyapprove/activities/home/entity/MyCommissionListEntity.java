package com.geely.app.geelyapprove.activities.home.entity;

import java.util.List;

/**
 * Created by Oliver on 2016/10/31.
 */

public class MyCommissionListEntity {


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
        private int ProcInstID;
        private int ActInstDestID;
        private String BarCode;
        private String WorkflowIdentifier;
        private String SrcBarCode;
        private int SrcProcInstID;
        private String ProcessNameCN;
        private String ProcessNameEN;
        private String ProcessName;
        private String ActivityNameCN;
        private String ActivityNameEN;
        private String CreateTime;
        private String UpdateTime;
        private String CompCode;
        private String CompNameCN;
        private String CompNameEN;
        private String DeptCode;
        private String DeptNameCN;
        private String DeptNameEN;
        private String Status;
        private String Destination;
        private String SubmitBy;
        private String ApplicantName;
        private String Summary;

        public int getProcInstID() {
            return ProcInstID;
        }

        public void setProcInstID(int ProcInstID) {
            this.ProcInstID = ProcInstID;
        }

        public int getActInstDestID() {
            return ActInstDestID;
        }

        public void setActInstDestID(int ActInstDestID) {
            this.ActInstDestID = ActInstDestID;
        }

        public String getBarCode() {
            return BarCode;
        }

        public void setBarCode(String BarCode) {
            this.BarCode = BarCode;
        }

        public String getWorkflowIdentifier() {
            return WorkflowIdentifier;
        }

        public void setWorkflowIdentifier(String WorkflowIdentifier) {
            this.WorkflowIdentifier = WorkflowIdentifier;
        }

        public String getSrcBarCode() {
            return SrcBarCode;
        }

        public void setSrcBarCode(String SrcBarCode) {
            this.SrcBarCode = SrcBarCode;
        }

        public int getSrcProcInstID() {
            return SrcProcInstID;
        }

        public void setSrcProcInstID(int SrcProcInstID) {
            this.SrcProcInstID = SrcProcInstID;
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

        public String getProcessName() {
            return ProcessName;
        }

        public void setProcessName(String ProcessName) {
            this.ProcessName = ProcessName;
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

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public String getUpdateTime() {
            return UpdateTime;
        }

        public void setUpdateTime(String UpdateTime) {
            this.UpdateTime = UpdateTime;
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

        public String getStatus() {
            return Status;
        }

        public void setStatus(String Status) {
            this.Status = Status;
        }

        public String getDestination() {
            return Destination;
        }

        public void setDestination(String Destination) {
            this.Destination = Destination;
        }

        public String getSubmitBy() {
            return SubmitBy;
        }

        public void setSubmitBy(String SubmitBy) {
            this.SubmitBy = SubmitBy;
        }

        public String getApplicantName() {
            return ApplicantName;
        }

        public void setApplicantName(String ApplicantName) {
            this.ApplicantName = ApplicantName;
        }

        public String getSummary() {
            return Summary;
        }

        public void setSummary(String Summary) {
            this.Summary = Summary;
        }
    }
}
