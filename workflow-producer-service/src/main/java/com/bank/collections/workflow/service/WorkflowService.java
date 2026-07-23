package com.bank.collections.workflow.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.bank.collections.workflow.dto.WorkflowDetailsResponse;
import com.bank.collections.workflow.dto.WorkflowRequest;
import com.bank.collections.workflow.dto.WorkflowResponse;

public interface WorkflowService {

    WorkflowResponse createWorkflow(WorkflowRequest request);
    WorkflowDetailsResponse getWorkflow(String workflowId);
    Page<WorkflowDetailsResponse> getAllWorkflows(int page, int size);

}