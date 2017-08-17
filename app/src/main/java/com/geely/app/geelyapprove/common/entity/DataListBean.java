package com.geely.app.geelyapprove.common.entity;

import java.util.ArrayList;

/**
 * Created by glx on 2017/2/16.
 */
public class DataListBean implements Comparable<DataListBean>{
    private ArrayList<DataListEntity> dataListEntities;
    private String DicId;

    public String getDicId() {
        return DicId;
    }

    public void setDicId(String dicId) {
        DicId = dicId;
    }

    public ArrayList<DataListEntity> getDataListEntities() {
        return dataListEntities;
    }

    public void setDataListEntities(ArrayList<DataListEntity> dataListEntities) {
        this.dataListEntities = dataListEntities;
    }

    @Override
    public int compareTo(DataListBean dataListBean) {
        return this.getDicId().compareTo(dataListBean.getDicId());
    }
}
