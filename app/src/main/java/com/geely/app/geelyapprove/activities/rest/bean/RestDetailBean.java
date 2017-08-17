package com.geely.app.geelyapprove.activities.rest.bean;

import com.geely.app.geelyapprove.common.entity.AttachmentListEntity;
import com.geely.app.geelyapprove.common.entity.EmpDataEntity;
import com.geely.app.geelyapprove.common.entity.HisDataBean;

import java.util.List;

/**
 * Created by guiluXu on 2016/11/8.
 */
public class RestDetailBean {
    @Override
    public String toString() {
        return "RestDetailBean{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", apiName='" + apiName + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", userId='" + userId + '\'' +
                ", sign='" + sign + '\'' +
                ", retData=" + retData +
                '}';
    }

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
        private IsKeyAuditNodeBean IsKeyAuditNode;
        private List<AttachmentListEntity> attchList;

        @Override
        public String toString() {
            return "RetDataBean{" +
                    "detailData=" + detailData +
                    ", empData=" + empData +
                    ", hisData=" + hisData +
                    ", IsKeyAuditNode=" + IsKeyAuditNode +
                    ", attchList=" + attchList +
                    '}';
        }

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

        public IsKeyAuditNodeBean getIsKeyAuditNode() {
            return IsKeyAuditNode;
        }

        public void setIsKeyAuditNode(IsKeyAuditNodeBean IsKeyAuditNode) {
            this.IsKeyAuditNode = IsKeyAuditNode;
        }

        public List<AttachmentListEntity> getAttchList() {
            return attchList;
        }

        public void setAttchList(List<AttachmentListEntity> attchList) {
            this.attchList = attchList;
        }

        public static class DetailDataBean {
            private String Id;
            private String Identifier;
            private String BarCode;
            private String ProjectName;
            private String Company;
            private String Position;
            private String EmployeeID;
            private String Department;
            private String SubmitBy;
            private String TotalLeaveTime;
            private String Applicant;
            private String UpdateTime;
            private String CreateTime;
            private String Summary;
            private String UpdateBy;
            private boolean IsSelf;
            private String LeaveDays;
            private String laveYearDays;
            private String laveHours;
            private String totalNumber;
            private String totalDays;

            private List<LeaveRequestDetailBean> LeaveRequestDetail;

            private List<ApproverDetailBean> ApproverDetail;
            private String SubmitByBase64;

            public String getSubmitByBase64(){
                return SubmitByBase64;
            }

            public void setSubmitByBase64(String submitByBase64){
                SubmitByBase64 = submitByBase64;
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

            public String getBarCode() {
                return BarCode;
            }

            public void setBarCode(String BarCode) {
                this.BarCode = BarCode;
            }

            public String getProjectName() {
                return ProjectName;
            }

            public void setProjectName(String ProjectName) {
                this.ProjectName = ProjectName;
            }

            public String getCompany() {
                return Company;
            }

            public void setCompany(String Company) {
                this.Company = Company;
            }

            public String getPosition() {
                return Position;
            }

            public void setPosition(String Position) {
                this.Position = Position;
            }

            public String getEmployeeID() {
                return EmployeeID;
            }

            public void setEmployeeID(String EmployeeID) {
                this.EmployeeID = EmployeeID;
            }

            public String getDepartment() {
                return Department;
            }

            public void setDepartment(String Department) {
                this.Department = Department;
            }

            public String getSubmitBy() {
                return SubmitBy;
            }

            public void setSubmitBy(String SubmitBy) {
                this.SubmitBy = SubmitBy;
            }

            public String getTotalLeaveTime() {
                return TotalLeaveTime;
            }

            public void setTotalLeaveTime(String TotalLeaveTime) {
                this.TotalLeaveTime = TotalLeaveTime;
            }

            public String getApplicant() {
                return Applicant;
            }

            public void setApplicant(String Applicant) {
                this.Applicant = Applicant;
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

            public String getSummary() {
                return Summary;
            }

            public void setSummary(String Summary) {
                this.Summary = Summary;
            }

            public String getUpdateBy() {
                return UpdateBy;
            }

            public void setUpdateBy(String UpdateBy) {
                this.UpdateBy = UpdateBy;
            }

            public boolean isIsSelf() {
                return IsSelf;
            }

            public void setIsSelf(boolean IsSelf) {
                this.IsSelf = IsSelf;
            }

            public String getLeaveDays() {
                return LeaveDays;
            }

            public void setLeaveDays(String LeaveDays) {
                this.LeaveDays = LeaveDays;
            }

            public String getLaveYearDays() {
                return laveYearDays;
            }

            public void setLaveYearDays(String laveYearDays) {
                this.laveYearDays = laveYearDays;
            }

            public String getLaveHours() {
                return laveHours;
            }

            public void setLaveHours(String laveHours) {
                this.laveHours = laveHours;
            }

            public String getTotalNumber() {
                return totalNumber;
            }

            public void setTotalNumber(String totalNumber) {
                this.totalNumber = totalNumber;
            }

            public String getTotalDays() {
                return totalDays;
            }

            public void setTotalDays(String totalDays) {
                this.totalDays = totalDays;
            }

            public List<LeaveRequestDetailBean> getLeaveRequestDetail() {
                return LeaveRequestDetail;
            }

            public void setLeaveRequestDetail(List<LeaveRequestDetailBean> LeaveRequestDetail) {
                this.LeaveRequestDetail = LeaveRequestDetail;
            }

            public List<ApproverDetailBean> getApproverDetail() {
                return ApproverDetail;
            }

            public void setApproverDetail(List<ApproverDetailBean> ApproverDetail) {
                this.ApproverDetail = ApproverDetail;
            }

            public static class LeaveRequestDetailBean {
                private String LeaveTpyeName;
                private String Id;
                private String WorkflowIdentifier;
                private String LeaveType;
                private String StartTime;
                private String EndTime;
                private String LeaveTime;
                private String LeaveCauses;
                private String Remark;
                private boolean IsPostToPs;

                public String getLeaveTpyeName() {
                    return LeaveTpyeName;
                }

                public void setLeaveTpyeName(String LeaveTpyeName) {
                    this.LeaveTpyeName = LeaveTpyeName;
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

                public String getLeaveType() {
                    return LeaveType;
                }

                public void setLeaveType(String LeaveType) {
                    this.LeaveType = LeaveType;
                }

                public String getStartTime() {
                    return StartTime;
                }

                public void setStartTime(String StartTime) {
                    this.StartTime = StartTime;
                }

                public String getEndTime() {
                    return EndTime;
                }

                public void setEndTime(String EndTime) {
                    this.EndTime = EndTime;
                }

                public String getLeaveTime() {
                    return LeaveTime;
                }

                public void setLeaveTime(String LeaveTime) {
                    this.LeaveTime = LeaveTime;
                }

                public String getLeaveCauses() {
                    return LeaveCauses;
                }

                public void setLeaveCauses(String LeaveCauses) {
                    this.LeaveCauses = LeaveCauses;
                }

                public String getRemark() {
                    return Remark;
                }

                public void setRemark(String Remark) {
                    this.Remark = Remark;
                }

                public boolean isIsPostToPs() {
                    return IsPostToPs;
                }

                public void setIsPostToPs(boolean IsPostToPs) {
                    this.IsPostToPs = IsPostToPs;
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
            private boolean Activie;
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

            public boolean isActivie() {
                return Activie;
            }

            public void setActivie(boolean Activie) {
                this.Activie = Activie;
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

        public static class IsKeyAuditNodeBean {
        }
    }
}
