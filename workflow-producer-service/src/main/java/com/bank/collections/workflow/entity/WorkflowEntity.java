package com.bank.collections.workflow.entity;
import java.time.LocalDateTime;

import com.bank.collections.workflow.model.WorkflowStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "WORKFLOW")
public class WorkflowEntity {

    @Id
    @Column(name = "WORKFLOW_ID")
    private String workflowId;

    @Column(name = "CASE_NUMBER", unique = true)
    private String caseNumber;

    public String getCaseNumber() {
		return caseNumber;
	}

	public void setCaseNumber(String caseNumber) {
		this.caseNumber = caseNumber;
	}

	@Column(name = "CUSTOMER_ID")
    private String customerId;

    @Column(name = "ACCOUNT_NUMBER")
    private String accountNumber;

    @Column(name = "DOCUMENT_TYPE")
    private String documentType;

    @Column(name = "CHANNEL")
    private String channel;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private WorkflowStatus status;

    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

    @Column(name = "UPDATED_DATE")
    private LocalDateTime updatedDate;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "UPDATED_BY")
    private String updatedBy;

	public String getWorkflowId() {
		return workflowId;
	}

	public void setWorkflowId(String workflowId) {
		this.workflowId = workflowId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	

	public WorkflowStatus getStatus() {
		return status;
	}

	public void setStatus(WorkflowStatus status) {
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

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	
	@PrePersist
	public void prePersist() {

	    createdDate = LocalDateTime.now();
	    updatedDate = LocalDateTime.now();

	    createdBy = "SYSTEM";
	    updatedBy = "SYSTEM";
	}
	
	@PreUpdate
	public void preUpdate() {

	    updatedDate = LocalDateTime.now();

	    updatedBy = "SYSTEM";
	}
}