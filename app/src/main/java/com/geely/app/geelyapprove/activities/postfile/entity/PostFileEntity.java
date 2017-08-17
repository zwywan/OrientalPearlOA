package com.geely.app.geelyapprove.activities.postfile.entity;

import com.geely.app.geelyapprove.common.entity.AttachmentListEntity;
import com.geely.app.geelyapprove.common.entity.HisDataBean;

import java.util.List;

/**
 * Created by zhy on 2016/11/25.
 */

public class PostFileEntity {

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
        private EmpDataBean empData;
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

        public EmpDataBean getEmpData() {
            return empData;
        }

        public void setEmpData(EmpDataBean empData) {
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

            private String DenseName;
            private String ReleaseModeName;
            private String FileTypeName;
            private String ReleaseTargetName;
            private String Id;
            private String BarCode;
            private String Identifier;
            private String Dense;
            private String CompanyType;
            private String FileName;
            private String FileType;
            private String ReleaseMode;
            private String ReleaseRange;
            private String Branch;
            private boolean PresidentIssued;
            private boolean Organize;
            private String ReleaseTarget;
            private String SubmitBy;
            private String CreateTime;
            private String UpdateBy;
            private String UpdateTime;
            private String Summary;
            private List<ApproverDetailBean> ApproverDetail;
            private String SubmitByBase64;

            public String getSubmitByBase64(){
                return SubmitByBase64;
            }

            public void setSubmitByBase64(String submitByBase64){
                SubmitByBase64 = submitByBase64;
            }

            public String getDenseName() {
                return DenseName;
            }

            public void setDenseName(String DenseName) {
                this.DenseName = DenseName;
            }

            public String getReleaseModeName() {
                return ReleaseModeName;
            }

            public void setReleaseModeName(String ReleaseModeName) {
                this.ReleaseModeName = ReleaseModeName;
            }

            public String getFileTypeName() {
                return FileTypeName;
            }

            public void setFileTypeName(String FileTypeName) {
                this.FileTypeName = FileTypeName;
            }

            public String getReleaseTargetName() {
                return ReleaseTargetName;
            }

            public void setReleaseTargetName(String ReleaseTargetName) {
                this.ReleaseTargetName = ReleaseTargetName;
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

            public String getDense() {
                return Dense;
            }

            public void setDense(String Dense) {
                this.Dense = Dense;
            }

            public String getCompanyType() {
                return CompanyType;
            }

            public void setCompanyType(String CompanyType) {
                this.CompanyType = CompanyType;
            }

            public String getFileName() {
                return FileName;
            }

            public void setFileName(String FileName) {
                this.FileName = FileName;
            }

            public String getFileType() {
                return FileType;
            }

            public void setFileType(String FileType) {
                this.FileType = FileType;
            }

            public String getReleaseMode() {
                return ReleaseMode;
            }

            public void setReleaseMode(String ReleaseMode) {
                this.ReleaseMode = ReleaseMode;
            }

            public String getReleaseRange() {
                return ReleaseRange;
            }

            public void setReleaseRange(String ReleaseRange) {
                this.ReleaseRange = ReleaseRange;
            }

            public String getBranch() {
                return Branch;
            }

            public void setBranch(String Branch) {
                this.Branch = Branch;
            }

            public boolean isPresidentIssued() {
                return PresidentIssued;
            }

            public void setPresidentIssued(boolean PresidentIssued) {
                this.PresidentIssued = PresidentIssued;
            }

            public boolean isOrganize() {
                return Organize;
            }

            public void setOrganize(boolean Organize) {
                this.Organize = Organize;
            }

            public String getReleaseTarget() {
                return ReleaseTarget;
            }

            public void setReleaseTarget(String ReleaseTarget) {
                this.ReleaseTarget = ReleaseTarget;
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

        public static class EmpDataBean {

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

        public static class IsKeyAuditNodeForApproveBean {
        }

        public static class IsKeyAuditNodeForDisplayBean {
        }
    }
}
