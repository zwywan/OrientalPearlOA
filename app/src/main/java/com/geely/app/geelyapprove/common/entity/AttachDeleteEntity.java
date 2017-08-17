package com.geely.app.geelyapprove.common.entity;

/**
 * Created by glx on 2017/3/3.
 */
public class AttachDeleteEntity {

    /**
     * code : 100
     * msg : OK
     * apiName : DeleteAttachment
     * deviceId : 123
     * userId : zhouly
     * sign : 825846B9C666D2DF7C74CD8D38EA62C7
     * retData :
     */

    private String code;
    private String msg;
    private String apiName;
    private String deviceId;
    private String userId;
    private String sign;
    private String retData;

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

    public String getRetData() {
        return retData;
    }

    public void setRetData(String retData) {
        this.retData = retData;
    }
}
