package com.geely.app.geelyapprove.activities.postfile.entity;

import java.util.List;

/**
 * Created by zhy on 2016/11/28.
 */

public class ComlianceAdministratorBean {
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
        /**
         * UserId : liuying
         * Name : 刘莹
         */

        private String UserId;
        private String Name;

        public String getUserId() {
            return UserId;
        }

        public void setUserId(String UserId) {
            this.UserId = UserId;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }
    }
}
