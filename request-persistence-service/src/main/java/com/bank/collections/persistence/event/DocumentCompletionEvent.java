package com.bank.collections.persistence.event;

public class DocumentCompletionEvent {

    private String workflowId;

    private String documentId;

    private String s3Key;

    private String status;

    public DocumentCompletionEvent() {
    }

    public DocumentCompletionEvent(
            String workflowId,
            String documentId,
            String s3Key,
            String status) {

        this.workflowId = workflowId;
        this.documentId = documentId;
        this.s3Key = s3Key;
        this.status = status;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getS3Key() {
        return s3Key;
    }

    public void setS3Key(String s3Key) {
        this.s3Key = s3Key;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {

        return "DocumentCompletionEvent{" +
                "workflowId='" + workflowId + '\'' +
                ", documentId='" + documentId + '\'' +
                ", s3Key='" + s3Key + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}