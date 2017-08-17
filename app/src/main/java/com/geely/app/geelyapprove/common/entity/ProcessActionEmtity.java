package com.geely.app.geelyapprove.common.entity;

import java.util.List;

/**
 * Created by zhy on 2017/2/22.
 */

public class ProcessActionEmtity {

    private String code;
    private String msg;
    private String apiName;
    private String deviceId;
    private String userId;
    private String sign;
    private List<String> retData;

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

    public List<String> getRetData() {
        return retData;
    }

    public void setRetData(List<String> retData) {
        this.retData = retData;
    }
}
