package com.geely.app.geelyapprove.activities.unified.bean;

import com.geely.app.geelyapprove.common.entity.AttachmentListEntity;
import com.geely.app.geelyapprove.common.entity.EmpDataEntity;
import com.geely.app.geelyapprove.common.entity.HisDataBean;

import java.util.List;

/**
 * Created by zhy on 2017/2/3.
 */

public class UnifiedEntity {

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

        private String SubmitByBase64;
        private String mainData;
        private String detailData;
        private EmpDataEntity empData;
        private HisDataBean hisData;
        private IsKeyAuditNodeForApproveBean IsKeyAuditNodeForApprove;
        private IsKeyAuditNodeForDisplayBean IsKeyAuditNodeForDisplay;
        private List<ListValueBean> listValue;
        private List<ListAbstractBean> listAbstract;
        private List<AttachmentListEntity> attchList;

        public String getSubmitByBase64() {
            return SubmitByBase64;
        }

        public void setSubmitByBase64(String SubmitByBase64) {
            this.SubmitByBase64 = SubmitByBase64;
        }

        public String getMainData() {
            return mainData;
        }

        public void setMainData(String mainData) {
            this.mainData = mainData;
        }

        public String getDetailData() {
            return detailData;
        }

        public void setDetailData(String detailData) {
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

        public List<ListValueBean> getListValue() {
            return listValue;
        }

        public void setListValue(List<ListValueBean> listValue) {
            this.listValue = listValue;
        }

        public List<ListAbstractBean> getListAbstract() {
            return listAbstract;
        }

        public void setListAbstract(List<ListAbstractBean> listAbstract) {
            this.listAbstract = listAbstract;
        }

        public List<AttachmentListEntity> getAttchList() {
            return attchList;
        }

        public void setAttchList(List<AttachmentListEntity> attchList) {
            this.attchList = attchList;
        }

        public static class IsKeyAuditNodeForApproveBean {
        }

        public static class IsKeyAuditNodeForDisplayBean {
        }

        public static class ListValueBean {

            private String Id;
            private String ProcessName;
            private String FieldEN;
            private String FieldCN;
            private String FieldType;
            private String FieldCode;
            private Object MethodName;
            private boolean IsEdit;
            private boolean IsShowForApp;
            private String WhichPageDisplay;
            private boolean IsAbstract;
            private String Order;
            private boolean Required;
            private String ApproverDetail;

            public String getId() {
                return Id;
            }

            public void setId(String Id) {
                this.Id = Id;
            }

            public String getProcessName() {
                return ProcessName;
            }

            public void setProcessName(String ProcessName) {
                this.ProcessName = ProcessName;
            }

            public String getFieldEN() {
                return FieldEN;
            }

            public void setFieldEN(String FieldEN) {
                this.FieldEN = FieldEN;
            }

            public String getFieldCN() {
                return FieldCN;
            }

            public void setFieldCN(String FieldCN) {
                this.FieldCN = FieldCN;
            }

            public String getFieldType() {
                return FieldType;
            }

            public void setFieldType(String FieldType) {
                this.FieldType = FieldType;
            }

            public String getFieldCode() {
                return FieldCode;
            }

            public void setFieldCode(String FieldCode) {
                this.FieldCode = FieldCode;
            }

            public Object getMethodName() {
                return MethodName;
            }

            public void setMethodName(Object MethodName) {
                this.MethodName = MethodName;
            }

            public boolean isIsEdit() {
                return IsEdit;
            }

            public void setIsEdit(boolean IsEdit) {
                this.IsEdit = IsEdit;
            }

            public boolean isIsShowForApp() {
                return IsShowForApp;
            }

            public void setIsShowForApp(boolean IsShowForApp) {
                this.IsShowForApp = IsShowForApp;
            }

            public String getWhichPageDisplay() {
                return WhichPageDisplay;
            }

            public void setWhichPageDisplay(String WhichPageDisplay) {
                this.WhichPageDisplay = WhichPageDisplay;
            }

            public boolean isIsAbstract() {
                return IsAbstract;
            }

            public void setIsAbstract(boolean IsAbstract) {
                this.IsAbstract = IsAbstract;
            }

            public String getOrder() {
                return Order;
            }

            public void setOrder(String Order) {
                this.Order = Order;
            }

            public boolean isRequired() {
                return Required;
            }

            public void setRequired(boolean Required) {
                this.Required = Required;
            }

            public String getApproverDetail() {
                return ApproverDetail;
            }

            public void setApproverDetail(String ApproverDetail) {
                this.ApproverDetail = ApproverDetail;
            }
        }

        public static class ListAbstractBean {

            private String Id;
            private String ProcessName;
            private String FieldEN;
            private String FieldCN;
            private String FieldType;
            private String FieldCode;
            private String MethodName;
            private boolean IsEdit;
            private boolean IsShowForApp;
            private String WhichPageDisplay;
            private boolean IsAbstract;
            private String Order;
            private boolean Required;
            private String ApproverDetail;

            public String getId() {
                return Id;
            }

            public void setId(String Id) {
                this.Id = Id;
            }

            public String getProcessName() {
                return ProcessName;
            }

            public void setProcessName(String ProcessName) {
                this.ProcessName = ProcessName;
            }

            public String getFieldEN() {
                return FieldEN;
            }

            public void setFieldEN(String FieldEN) {
                this.FieldEN = FieldEN;
            }

            public String getFieldCN() {
                return FieldCN;
            }

            public void setFieldCN(String FieldCN) {
                this.FieldCN = FieldCN;
            }

            public String getFieldType() {
                return FieldType;
            }

            public void setFieldType(String FieldType) {
                this.FieldType = FieldType;
            }

            public String getFieldCode() {
                return FieldCode;
            }

            public void setFieldCode(String FieldCode) {
                this.FieldCode = FieldCode;
            }

            public String getMethodName() {
                return MethodName;
            }

            public void setMethodName(String MethodName) {
                this.MethodName = MethodName;
            }

            public boolean isIsEdit() {
                return IsEdit;
            }

            public void setIsEdit(boolean IsEdit) {
                this.IsEdit = IsEdit;
            }

            public boolean isIsShowForApp() {
                return IsShowForApp;
            }

            public void setIsShowForApp(boolean IsShowForApp) {
                this.IsShowForApp = IsShowForApp;
            }

            public String getWhichPageDisplay() {
                return WhichPageDisplay;
            }

            public void setWhichPageDisplay(String WhichPageDisplay) {
                this.WhichPageDisplay = WhichPageDisplay;
            }

            public boolean isIsAbstract() {
                return IsAbstract;
            }

            public void setIsAbstract(boolean IsAbstract) {
                this.IsAbstract = IsAbstract;
            }

            public String getOrder() {
                return Order;
            }

            public void setOrder(String Order) {
                this.Order = Order;
            }

            public boolean isRequired() {
                return Required;
            }

            public void setRequired(boolean Required) {
                this.Required = Required;
            }

            public String getApproverDetail() {
                return ApproverDetail;
            }

            public void setApproverDetail(String ApproverDetail) {
                this.ApproverDetail = ApproverDetail;
            }
        }

    }
}
