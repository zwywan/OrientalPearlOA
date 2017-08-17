package com.geely.app.geelyapprove.common.entity;

import java.util.List;

/**
 * Created by Oliver on 2016/10/18.
 */

public class AddressEntity {

    private String code;
    private String msg;
    private String apiName;
    private String deviceId;
    private String userId;
    private String sign;

    private List<DataListEntity> retData;

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

    public List<DataListEntity> getRetData() {
        return retData;
    }

    public void setRetData(List<DataListEntity> retData) {
        this.retData = retData;
    }

}
