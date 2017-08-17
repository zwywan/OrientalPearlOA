package com.geely.app.geelyapprove.common.entity;


/**
 * Created by Oliver on 2016/10/18.
 */

public class DataListEntity {
    private String DicId;
    private String DicCode;
    private String DicDesc;

    public DataListEntity(String dicId, String dicCode, String dicDesc) {
        DicId = dicId;
        DicCode = dicCode;
        DicDesc = dicDesc;
    }

    @Override
    public String toString() {
        return  DicDesc ;
    }

    public String getDicId() {
        return DicId;
    }

    public void setDicId(String DicId) {
        this.DicId = DicId;
    }

    public String getDicCode() {
        return DicCode;
    }

    public void setDicCode(String DicCode) {
        this.DicCode = DicCode;
    }

    public String getDicDesc() {
        return DicDesc;
    }

    public void setDicDesc(String DicDesc) {
        this.DicDesc = DicDesc;
    }

}
