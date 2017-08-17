package com.geely.app.geelyapprove.activities.enterexpense.Entity;

import java.util.List;

/**
 * Created by Oliver on 2016/11/25.
 */

public class EnterExpenseOrdersEntity {

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
        private String count;
        private List<EntertainmentExpenseOrdersBean> EntertainmentExpenseOrders;

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public List<EntertainmentExpenseOrdersBean> getEntertainmentExpenseOrders() {
            return EntertainmentExpenseOrders;
        }

        public void setEntertainmentExpenseOrders(List<EntertainmentExpenseOrdersBean> EntertainmentExpenseOrders) {
            this.EntertainmentExpenseOrders = EntertainmentExpenseOrders;
        }

        public static class EntertainmentExpenseOrdersBean {

            private String BarCode;
            private String Amount;
            private String CreateTime;

            public String getBarCode(){
                return BarCode;
            }

            public void setBarCode(String barCode){
                BarCode = barCode;
            }

            public String getAmount() {
                return Amount;
            }

            public void setAmount(String Amount) {
                this.Amount = Amount;
            }

            public String getCreateTime() {
                return CreateTime;
            }

            public void setCreateTime(String CreateTime) {
                this.CreateTime = CreateTime;
            }
        }
    }
}
