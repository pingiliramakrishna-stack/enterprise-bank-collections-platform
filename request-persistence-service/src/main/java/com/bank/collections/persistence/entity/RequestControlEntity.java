package com.bank.collections.persistence.entity;

import java.time.LocalDateTime;

import com.bank.collections.persistence.enums.RequestStatus;

import jakarta.persistence.*;

@Entity
@Table(name = "REQUEST_CONTROL")
public class RequestControlEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String workflowId;
	private String caseNumber;

	private String customerId;

	private String accountNumber;

	@Enumerated(EnumType.STRING)
	private RequestStatus status;

	private Integer retryCount;

	private LocalDateTime requestCreatedDate;

	private LocalDateTime updatedDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public RequestStatus getStatus() {
		return status;
	}

	public void setStatus(RequestStatus status) {
		this.status = status;
	}

	public Integer getRetryCount() {
		return retryCount;
	}

	public void setRetryCount(Integer retryCount) {
		this.retryCount = retryCount;
	}

	public LocalDateTime getRequestCreatedDate() {
		return requestCreatedDate;
	}

	public void setRequestCreatedDate(LocalDateTime requestCreatedDate) {
		this.requestCreatedDate = requestCreatedDate;
	}

	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(LocalDateTime updatedDate) {
		this.updatedDate = updatedDate;
	}

	
}