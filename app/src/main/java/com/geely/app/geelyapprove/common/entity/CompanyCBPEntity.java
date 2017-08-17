package com.geely.app.geelyapprove.common.entity;

import java.util.List;

/**公司成本中心币种联动
 * Created by zhy on 2016/10/20.
 */
public class CompanyCBPEntity {
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
        private List<DataListEntity> costcenterList;
        private List<?> currenctyList;
        private List<?> innerorderList;

        public List<DataListEntity> getCostcenterList() {
            return costcenterList;
        }

        public void setCostcenterList(List<DataListEntity> costcenterList) {
            this.costcenterList = costcenterList;
        }

        public List<?> getCurrenctyList() {
            return currenctyList;
        }

        public void setCurrenctyList(List<?> currenctyList) {
            this.currenctyList = currenctyList;
        }

        public List<?> getInnerorderList() {
            return innerorderList;
        }

        public void setInnerorderList(List<?> innerorderList) {
            this.innerorderList = innerorderList;
        }

    }
}
