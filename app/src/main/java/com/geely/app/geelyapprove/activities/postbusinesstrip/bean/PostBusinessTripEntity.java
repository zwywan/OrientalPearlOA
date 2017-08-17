package com.geely.app.geelyapprove.activities.postbusinesstrip.bean;

import com.geely.app.geelyapprove.common.entity.AttachmentListEntity;
import com.geely.app.geelyapprove.common.entity.EmpDataEntity;
import com.geely.app.geelyapprove.common.entity.HisDataBean;

import java.util.List;

/**
 * Created by Oliver on 2016/9/27.
 */

public class PostBusinessTripEntity {
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

        public void setIsKeyAuditNodeForApprove(IsKeyAuditNodeBean IsKeyAuditNode) {
            this.IsKeyAuditNodeForApprove = IsKeyAuditNode;
        }

        public IsKeyAuditNodeBean getIsKeyAuditNodeForDisplay() {
            return IsKeyAuditNodeForDisplay;
        }

        public void setIsKeyAuditNodeForDisplay(IsKeyAuditNodeBean IsKeyAuditNode) {
            this.IsKeyAuditNodeForDisplay = IsKeyAuditNode;
        }

        public List<AttachmentListEntity> getAttchList() {
            return attchList;
        }

        public void setAttchList(List<AttachmentListEntity> attchList) {
            this.attchList = attchList;
        }

        public static class DetailDataBean {
            private String LeaveTypeName;
            private String Id;
            private String Identifier;
            private String BarCode;
            private String SubmitBy;
            private String CreateTime;
            private String UpdateTime;
            private boolean Allowance;
            private String Budget;
            private boolean IsProxy;
            private String TripDay;
            private String Summary;
            private String Descr;
            private String PlanLeaveDate;
            private String PlanReturnDate;
            private String LeaveType;
            private String ActualLeaveDate;
            private String ActualReturnDate;
            private String ActualLeaveDay;
            private boolean IsPostToPs;
            private List<BusinessTripDetailBean> BusinessTripDetail;
            private List<ApproverDetailBean> ApproverDetail;
            private String SubmitByBase64;

            public String getSubmitByBase64(){
                return SubmitByBase64;
            }

            public void setSubmitByBase64(String submitByBase64){
                SubmitByBase64 = submitByBase64;
            }

            public String getLeaveTypeName() {
                return LeaveTypeName;
            }

            public void setLeaveTypeName(String LeaveTypeName) {
                this.LeaveTypeName = LeaveTypeName;
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

            public String getUpdateTime() {
                return UpdateTime;
            }

            public void setUpdateTime(String UpdateTime) {
                this.UpdateTime = UpdateTime;
            }

            public boolean isAllowance() {
                return Allowance;
            }

            public void setAllowance(boolean Allowance) {
                this.Allowance = Allowance;
            }

            public String getBudget() {
                return Budget;
            }

            public void setBudget(String Budget) {
                this.Budget = Budget;
            }

            public boolean isIsProxy() {
                return IsProxy;
            }

            public void setIsProxy(boolean IsProxy) {
                this.IsProxy = IsProxy;
            }

            public String getTripDay() {
                return TripDay;
            }

            public void setTripDay(String TripDay) {
                this.TripDay = TripDay;
            }

            public String getSummary() {
                return Summary;
            }

            public void setSummary(String Summary) {
                this.Summary = Summary;
            }

            public String getDescr() {
                return Descr;
            }

            public void setDescr(String Descr) {
                this.Descr = Descr;
            }

            public String getPlanLeaveDate() {
                return PlanLeaveDate;
            }

            public void setPlanLeaveDate(String PlanLeaveDate) {
                this.PlanLeaveDate = PlanLeaveDate;
            }

            public String getPlanReturnDate() {
                return PlanReturnDate;
            }

            public void setPlanReturnDate(String PlanReturnDate) {
                this.PlanReturnDate = PlanReturnDate;
            }

            public String getLeaveType() {
                return LeaveType;
            }

            public void setLeaveType(String LeaveType) {
                this.LeaveType = LeaveType;
            }

            public String getActualLeaveDate() {
                return ActualLeaveDate;
            }

            public void setActualLeaveDate(String ActualLeaveDate) {
                this.ActualLeaveDate = ActualLeaveDate;
            }

            public String getActualReturnDate() {
                return ActualReturnDate;
            }

            public void setActualReturnDate(String ActualReturnDate) {
                this.ActualReturnDate = ActualReturnDate;
            }

            public String getActualLeaveDay() {
                return ActualLeaveDay;
            }

            public void setActualLeaveDay(String ActualLeaveDay) {
                this.ActualLeaveDay = ActualLeaveDay;
            }

            public boolean isIsPostToPs() {
                return IsPostToPs;
            }

            public void setIsPostToPs(boolean IsPostToPs) {
                this.IsPostToPs = IsPostToPs;
            }

            public List<BusinessTripDetailBean> getBusinessTripDetail() {
                return BusinessTripDetail;
            }

            public void setBusinessTripDetail(List<BusinessTripDetailBean> BusinessTripDetail) {
                this.BusinessTripDetail = BusinessTripDetail;
            }

            public List<ApproverDetailBean> getApproverDetail() {
                return ApproverDetail;
            }

            public void setApproverDetail(List<ApproverDetailBean> ApproverDetail) {
                this.ApproverDetail = ApproverDetail;
            }

            public static class BusinessTripDetailBean {
                private String TransportName;
                private String Id;
                private String WorkflowIdentifier;
                private String From;
                private String To;
                private String Transport;
                private String TicketCost;
                private String TransportCost;
                private String HotelCost;
                private String GuestsCost;
                private String OtherCost;
                private String Allowance;
                private String LeaveDate;
                private String ReturnDate;

                public String getTransportName() {
                    return TransportName;
                }

                public void setTransportName(String TransportName) {
                    this.TransportName = TransportName;
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

                public String getFrom() {
                    return From;
                }

                public void setFrom(String From) {
                    this.From = From;
                }

                public String getTo() {
                    return To;
                }

                public void setTo(String To) {
                    this.To = To;
                }

                public String getTransport() {
                    return Transport;
                }

                public void setTransport(String Transport) {
                    this.Transport = Transport;
                }

                public String getTicketCost() {
                    return TicketCost;
                }

                public void setTicketCost(String TicketCost) {
                    this.TicketCost = TicketCost;
                }

                public String getTransportCost() {
                    return TransportCost;
                }

                public void setTransportCost(String TransportCost) {
                    this.TransportCost = TransportCost;
                }

                public String getHotelCost() {
                    return HotelCost;
                }

                public void setHotelCost(String HotelCost) {
                    this.HotelCost = HotelCost;
                }

                public String getGuestsCost() {
                    return GuestsCost;
                }

                public void setGuestsCost(String GuestsCost) {
                    this.GuestsCost = GuestsCost;
                }

                public String getOtherCost() {
                    return OtherCost;
                }

                public void setOtherCost(String OtherCost) {
                    this.OtherCost = OtherCost;
                }

                public String getAllowance() {
                    return Allowance;
                }

                public void setAllowance(String Allowance) {
                    this.Allowance = Allowance;
                }

                public String getLeaveDate() {
                    return LeaveDate;
                }

                public void setLeaveDate(String LeaveDate) {
                    this.LeaveDate = LeaveDate;
                }

                public String getReturnDate() {
                    return ReturnDate;
                }

                public void setReturnDate(String ReturnDate) {
                    this.ReturnDate = ReturnDate;
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
            private boolean Active;
            private String PositionNameCN;
            private String PositionNameEN;
            private String Grade;
            private String NAME1_AC;
            private String G_PY_NAME;
            private String G_PY_SURNAME;

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

            public String getNAME1_AC() {
                return NAME1_AC;
            }

            public void setNAME1_AC(String NAME1_AC) {
                this.NAME1_AC = NAME1_AC;
            }

            public String getG_PY_NAME() {
                return G_PY_NAME;
            }

            public void setG_PY_NAME(String G_PY_NAME) {
                this.G_PY_NAME = G_PY_NAME;
            }

            public String getG_PY_SURNAME() {
                return G_PY_SURNAME;
            }

            public void setG_PY_SURNAME(String G_PY_SURNAME) {
                this.G_PY_SURNAME = G_PY_SURNAME;
            }
        }

        public static class IsKeyAuditNodeBean {
            private boolean Attendance;

            public boolean isAttendance() {
                return Attendance;
            }

            public void setAttendance(boolean Attendance) {
                this.Attendance = Attendance;
            }
        }
    }
}