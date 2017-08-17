package com.geely.app.geelyapprove.activities.persons;

/**
 * Created by zhy on 2017/6/6.
 */

public class ItemBean2 extends BaseItem{

    private boolean Checked;
    private String name = null;
    private String Zw = null;
    private String UsetId;
    private String str;


    public ItemBean2(int item_type, boolean checked, String name, String imagePath, String userId , String string) {
        super(item_type);
        this.Checked = checked;
        this.name = name;
        this.Zw = imagePath;
        this.UsetId = userId;
        this.str = string;
    }

    public boolean getChecked(){
        return Checked;
    }

    public void setChecked(boolean checked){
        Checked = checked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZw() {
        return Zw;
    }

    public void setZw(String imagePath) {
        this.Zw = imagePath;
    }

    public int getItemType(){
        return super.getItem_type();
    }

    public void setItemType(int itemType){
        super.setItem_type(itemType);
    }

    public String getUsetId() {
        return UsetId;
    }

    public void setUsetId(String usetId) {
        UsetId = usetId;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }
}