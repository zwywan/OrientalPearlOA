package com.geely.app.geelyapprove.common.entity;

/**
 * Created by Oliver on 2016/11/17.
 */

public class ApproverDetailEntity {

    private String NameCN;
    private String NameEN;
    private String Id;
    private String WorkflowIdentifier;
    private String PName;
    private String PValue;

    public String getNameCN() {
        return NameCN;
    }

    public void setNameCN(String NameCN) {
        this.NameCN = NameCN;
    }

    public String getNameEN() {
        return NameEN;
    }

    public void setNameEN(String NameEN) {
        this.NameEN = NameEN;
    }

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

    public String getPName() {
        return PName;
    }

    public void setPName(String PName) {
        this.PName = PName;
    }

    public String getPValue() {
        return PValue;
    }

    public void setPValue(String PValue) {
        this.PValue = PValue;
    }

}
