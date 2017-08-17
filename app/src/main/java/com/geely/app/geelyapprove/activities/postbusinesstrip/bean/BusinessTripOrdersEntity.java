package com.geely.app.geelyapprove.activities.postbusinesstrip.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Oliver on 2016/11/25.
 */

public class BusinessTripOrdersEntity implements Serializable{

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

        private int count;
        private List<BusinessTripOrdersBean> BusinessTripOrders;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public List<BusinessTripOrdersBean> getBusinessTripOrders() {
            return BusinessTripOrders;
        }

        public void setBusinessTripOrders(List<BusinessTripOrdersBean> BusinessTripOrders) {
            this.BusinessTripOrders = BusinessTripOrders;
        }

        public static class BusinessTripOrdersBean {

            private String BarCode;
            private String Budget;
            private String CreateTime;
            private List<BusinessTripDetailBean> BusinessTripDetail;

            public String getBarCode() {
                return BarCode;
            }

            public void setBarCode(String BarCode) {
                this.BarCode = BarCode;
            }

            public String getBudget() {
                return Budget;
            }

            public void setBudget(String Budget) {
                this.Budget = Budget;
            }

            public String getCreateTime() {
                return CreateTime;
            }

            public void setCreateTime(String CreateTime) {
                this.CreateTime = CreateTime;
            }

            public List<BusinessTripDetailBean> getBusinessTripDetail() {
                return BusinessTripDetail;
            }

            public void setBusinessTripDetail(List<BusinessTripDetailBean> BusinessTripDetail) {
                this.BusinessTripDetail = BusinessTripDetail;
            }

            public static class BusinessTripDetailBean {

                private String TransportName;
                private String Id;
                private String WorkflowIdentifier;
                private String From;
                private String To;
                private String BarCode;
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
        }
    }
}
