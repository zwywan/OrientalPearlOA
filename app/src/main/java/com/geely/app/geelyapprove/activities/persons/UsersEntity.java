package com.geely.app.geelyapprove.activities.persons;

import java.util.List;

/**
 * Created by zhy on 2017/6/5.
 */

public class UsersEntity {

    /**
     * code : 100
     * msg : OK
     * apiName : 获取用户控件数据集合
     * deviceId :
     * userId : fan.ruohan
     * sign :
     */

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
        /**
         * Entity : [{"FirstLetter":"A","NameCN":"艾晓明","UserId":"ai.xiaoming","PositionNameCN":"云计算中心CDN技术部测试工程师"}]
         */

        private String Letter;
        private List<EntityBean> Entity;

        public String getLetter() {
            return Letter;
        }

        public void setLetter(String Letter) {
            this.Letter = Letter;
        }

        public List<EntityBean> getEntity() {
            return Entity;
        }

        public void setEntity(List<EntityBean> Entity) {
            this.Entity = Entity;
        }

        public static class EntityBean {
            /**
             * FirstLetter : A
             * NameCN : 艾晓明
             * UserId : ai.xiaoming
             * PositionNameCN : 云计算中心CDN技术部测试工程师
             */

            private String FirstLetter;
            private String NameCN;
            private String UserId;
            private String PositionNameCN;

            public String getFirstLetter() {
                return FirstLetter;
            }

            public void setFirstLetter(String FirstLetter) {
                this.FirstLetter = FirstLetter;
            }

            public String getNameCN() {
                return NameCN;
            }

            public void setNameCN(String NameCN) {
                this.NameCN = NameCN;
            }

            public String getUserId() {
                return UserId;
            }

            public void setUserId(String UserId) {
                this.UserId = UserId;
            }

            public String getPositionNameCN() {
                return PositionNameCN;
            }

            public void setPositionNameCN(String PositionNameCN) {
                this.PositionNameCN = PositionNameCN;
            }
        }
    }
}
