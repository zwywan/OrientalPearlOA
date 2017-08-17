package com.geely.app.geelyapprove.common.entity;

import android.net.Uri;

/**
 * Created by zhy on 2017/4/10.
 */

public class AttachEntity {
    private String GroupName;
    private Uri uri;

    public void setGroupName(String groupName){
        this.GroupName = groupName;
    }

    public String getGroupName(){
        return GroupName;
    }

    public void setUri(Uri uri1){
        this.uri = uri1;
    }

    public Uri getUri(){
        return uri;
    }
}
