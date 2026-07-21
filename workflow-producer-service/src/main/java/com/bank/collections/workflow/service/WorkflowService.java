package com.bank.collections.workflow.service;

import com.bank.collections.workflow.dto.WorkflowRequest;
import com.bank.collections.workflow.dto.WorkflowResponse;

public interface WorkflowService {

    WorkflowResponse createWorkflow(WorkflowRequest request);

}