package com.geely.app.geelyapprove.common.entity;

import android.net.Uri;

/**
 * Created by Oliver on 2016/11/17.
 */

public class AttachmentListEntity {

    private String Id;
    private String WorkflowIdentifier;
    private String WorkFlowType;
    private String FileGroupName;
    private String FileGroupValue;
    private String FileName;
    private String FileExtension;
    private String FileUrl;
    private boolean Active;
    private String CreateBy;
    private String CreateTime;
    private Uri LocalFileUri;

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getWorkflowIdentifier() {
        return WorkflowIdentifier;
    }

    public void setWorkflowIdentifier(String WorkflowIdentifier) {
        this.WorkflowIdentifier = WorkflowIdentifier;
    }

    public Uri getLocalFileUri() {
        return LocalFileUri;
    }

    public void setLocalFileUri(Uri localFileUri) {
        LocalFileUri = localFileUri;
    }

    public String getWorkFlowType() {
        return WorkFlowType;
    }

    public void setWorkFlowType(String WorkFlowType) {
        this.WorkFlowType = WorkFlowType;
    }

    public String getFileGroupName() {
        return FileGroupName;
    }

    public void setFileGroupName(String FileGroupName) {
        this.FileGroupName = FileGroupName;
    }

    public String getFileGroupValue() {
        return FileGroupValue;
    }

    public void setFileGroupValue(String FileGroupValue) {
        this.FileGroupValue = FileGroupValue;
    }

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String FileName) {
        this.FileName = FileName;
    }

    public String getFileExtension() {
        return FileExtension;
    }

    public void setFileExtension(String FileExtension) {
        this.FileExtension = FileExtension;
    }

    public String getFileUrl() {
        return FileUrl;
    }

    public void setFileUrl(String FileUrl) {
        this.FileUrl = FileUrl;
    }

    public boolean isActive() {
        return Active;
    }

    public void setActive(boolean Active) {
        this.Active = Active;
    }

    public String getCreateBy() {
        return CreateBy;
    }

    public void setCreateBy(String CreateBy) {
        this.CreateBy = CreateBy;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }


    @Override
    public String toString() {
        return "AttachmentListEntity{" +
                "Id='" + Id + '\'' +
                ", WorkflowIdentifier='" + WorkflowIdentifier + '\'' +
                ", WorkFlowType='" + WorkFlowType + '\'' +
                ", FileGroupName='" + FileGroupName + '\'' +
                ", FileGroupValue='" + FileGroupValue + '\'' +
                ", FileName='" + FileName + '\'' +
                ", FileExtension='" + FileExtension + '\'' +
                ", FileUrl='" + FileUrl + '\'' +
                ", Active=" + Active +
                ", CreateBy='" + CreateBy + '\'' +
                ", CreateTime='" + CreateTime + '\'' +
                '}';
    }
}
