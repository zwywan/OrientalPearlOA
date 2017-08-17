package com.geely.app.geelyapprove.common.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Oliver on 2016/10/8.
 */

public class HisDataBean implements Serializable {
    private List<AuditHisBean> auditHis;
    private List<AuditNodeBean> auditNode;

    public List<AuditHisBean> getAuditHis() {
        return auditHis;
    }

    public void setAuditHis(List<AuditHisBean> auditHis) {
        this.auditHis = auditHis;
    }

    public List<AuditNodeBean> getAuditNode() {
        return auditNode;
    }

    public void setAuditNode(List<AuditNodeBean> auditNode) {
        this.auditNode = auditNode;
    }

    public static class AuditHisBean implements Serializable {
        private String Id;
        private String WorkflowIdentifier;
        private String SerialNumber;
        private String ActivityNameCN;
        private String ActivityNameEN;
        private String Action;
        private String Comment;
        private String Operator;
        private String Delegator;
        private String CreateTime;
        private String DelegationType;
        private String OperatorName;

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

        public String getSerialNumber() {
            return SerialNumber;
        }

        public void setSerialNumber(String SerialNumber) {
            this.SerialNumber = SerialNumber;
        }

        public String getActivityNameCN() {
            return ActivityNameCN;
        }

        public void setActivityNameCN(String ActivityNameCN) {
            this.ActivityNameCN = ActivityNameCN;
        }

        public String getActivityNameEN() {
            return ActivityNameEN;
        }

        public void setActivityNameEN(String ActivityNameEN) {
            this.ActivityNameEN = ActivityNameEN;
        }

        public String getAction() {
            return Action;
        }

        public void setAction(String Action) {
            this.Action = Action;
        }

        public String getComment() {
            return Comment;
        }

        public void setComment(String Comment) {
            this.Comment = Comment;
        }

        public String getOperator() {
            return Operator;
        }

        public void setOperator(String Operator) {
            this.Operator = Operator;
        }

        public String getDelegator() {
            return Delegator;
        }

        public void setDelegator(String Delegator) {
            this.Delegator = Delegator;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public String getDelegationType() {
            return DelegationType;
        }

        public void setDelegationType(String DelegationType) {
            this.DelegationType = DelegationType;
        }

        public String getOperatorName() {
            return OperatorName;
        }

        public void setOperatorName(String OperatorName) {
            this.OperatorName = OperatorName;
        }
    }

    public static class AuditNodeBean implements Serializable{
        private String NameCN;
        private String NameEN;
        private String Action;
        private String Approvers;
        private String Status;
        private String Comment;
        private String CreateTime;

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

        public String getAction() {
            return Action;
        }

        public void setAction(String Action) {
            this.Action = Action;
        }

        public String getApprovers() {
            return Approvers;
        }

        public void setApprovers(String Approvers) {
            this.Approvers = Approvers;
        }

        public String getStatus() {
            return Status;
        }

        public void setStatus(String Status) {
            this.Status = Status;
        }

        public String getComment() {
            return Comment;
        }

        public void setComment(String Comment) {
            this.Comment = Comment;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }
    }
}