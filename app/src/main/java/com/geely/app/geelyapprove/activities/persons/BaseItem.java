package com.geely.app.geelyapprove.activities.persons;

/**
 * Created by zhy on 2017/6/6.
 */

public class BaseItem {
    private int item_type = 0;

    public BaseItem(int item_type) {
        this.item_type = item_type;
    }

    public int getItem_type() {
        return item_type;
    }

    public void setItem_type(int item_type) {
        this.item_type = item_type;
    }

}
