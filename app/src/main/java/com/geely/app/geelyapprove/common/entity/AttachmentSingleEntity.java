package com.geely.app.geelyapprove.common.entity;

/**
 * Created by glx on 2017/3/2.
 */
public class AttachmentSingleEntity {

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

    public void setRetData(RetDataBean retData) {
        this.retData = retData;
    }

    public static class RetDataBean {
        private String attachment;
        private String type;

        public String getAttachment() {
            return attachment;
        }

        public void setAttachment(String attachment) {
            this.attachment = attachment;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
