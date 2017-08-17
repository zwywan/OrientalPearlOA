package com.geely.app.geelyapprove.common.entity;

import java.util.List;

/**下拉框返回
 * Created by zhy on 2016/10/24.
 */
public class DictionaryEntity {

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

    public void setRetData(List<RetDataBean> retData) {
        this.retData = retData;
    }

    public static class RetDataBean {
        private String name;
        private List<DataListEntity> dataList;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<DataListEntity> getDataList() {
            return dataList;
        }

        public void setDataList(List<DataListEntity> dataList) {
            this.dataList = dataList;
        }

    }
}
