package com.bank.collections.workflow.exception;

public class WorkflowNotFoundException extends RuntimeException {

    public WorkflowNotFoundException(String workflowId) {
        super("Workflow not found: " + workflowId);
    }
}