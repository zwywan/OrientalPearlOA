package com.geely.app.geelyapprove.activities.persons;

/**
 * Created by zhy on 2017/6/6.
 */

public class ItemBean1 extends BaseItem{

    public String name = null;


    public ItemBean1(int item_type, String name) {
        super(item_type);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getItemType(){
        return super.getItem_type();
    }

    public void setItemType(int itemType){
        super.setItem_type(itemType);
    }
}