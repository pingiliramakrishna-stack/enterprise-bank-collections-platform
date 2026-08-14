package com.bank.collections.notification.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "DOCUMENT_CONTROL")
public class DocumentControlEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "WORKFLOW_ID", nullable = false, unique = true)
    private String workflowId;

    @Column(name = "DOCUMENT_ID", nullable = false)
    private String documentId;

    @Column(name = "S3_KEY", nullable = false)
    private String s3Key;

    @Column(name = "STATUS", nullable = false)
    private String status;

    @Column(name = "CREATED_DATE", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "UPDATED_DATE", nullable = false)
    private LocalDateTime updatedDate;

    public Long getId() {
        return id;
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

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }
}