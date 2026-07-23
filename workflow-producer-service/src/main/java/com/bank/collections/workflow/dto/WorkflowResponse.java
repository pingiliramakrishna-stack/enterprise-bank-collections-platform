package com.bank.collections.workflow.dto;

import com.bank.collections.workflow.model.WorkflowStatus;

public class WorkflowResponse {

    private String workflowId;
    private WorkflowStatus status;
    public WorkflowStatus getStatus() {
		return status;
	}

	public void setStatus(WorkflowStatus status) {
		this.status = status;
	}

	private String message;
    private String caseNumber;

    public String getCaseNumber() {
		return caseNumber;
	}

	public void setCaseNumber(String caseNumber) {
		this.caseNumber = caseNumber;
	}

	public String getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

  
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}