package com.bank.collections.persistence.event;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.bank.collections.persistence.enums.RequestStatus;

public class WorkflowCreatedEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    private String workflowId;

    private String caseNumber;

    private String customerId;

    private String accountNumber;

    private RequestStatus status;

    private LocalDateTime createdDate;

    public WorkflowCreatedEvent() {
    }

    public WorkflowCreatedEvent(String workflowId,
                                String caseNumber,
                                String customerId,
                                String accountNumber,
                                String channel,
                                RequestStatus status,
                                LocalDateTime createdDate) {
        this.workflowId = workflowId;
        this.caseNumber = caseNumber;
        this.customerId = customerId;
        this.accountNumber = accountNumber;
    }

    public RequestStatus getStatus() {
		return status;
	}

	public void setStatus(RequestStatus status) {
		this.status = status;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

    public String getCaseNumber() {
        return caseNumber;
    }

    public void setCaseNumber(String caseNumber) {
        this.caseNumber = caseNumber;
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


    @Override
    public String toString() {
        return "WorkflowCreatedEvent{" +
                "workflowId='" + workflowId + '\'' +
                ", caseNumber='" + caseNumber + '\'' +
                ", customerId='" + customerId + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                '}';
    }
}