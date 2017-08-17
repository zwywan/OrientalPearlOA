package com.geely.app.geelyapprove.activities.rest.bean;

/**
 * Created by zhy on 2016/12/2.
 */

public class LeaveDaysOrHoursBean {
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

        private String Eid;
        private String AnnualDaysLeft;
        private String OffHoursLeft;
        private String TimesOfAntenatalTotal;
        private String DayOfLeaveTotal;

        public String getEid() {
            return Eid;
        }

        public void setEid(String Eid) {
            this.Eid = Eid;
        }

        public String getAnnualDaysLeft() {
            return AnnualDaysLeft;
        }

        public void setAnnualDaysLeft(String AnnualDaysLeft) {
            this.AnnualDaysLeft = AnnualDaysLeft;
        }

        public String getOffHoursLeft() {
            return OffHoursLeft;
        }

        public void setOffHoursLeft(String OffHoursLeft) {
            this.OffHoursLeft = OffHoursLeft;
        }

        public String getTimesOfAntenatalTotal() {
            return TimesOfAntenatalTotal;
        }

        public void setTimesOfAntenatalTotal(String TimesOfAntenatalTotal) {
            this.TimesOfAntenatalTotal = TimesOfAntenatalTotal;
        }

        public String getDayOfLeaveTotal() {
            return DayOfLeaveTotal;
        }

        public void setDayOfLeaveTotal(String DayOfLeaveTotal) {
            this.DayOfLeaveTotal = DayOfLeaveTotal;
        }
    }
}
