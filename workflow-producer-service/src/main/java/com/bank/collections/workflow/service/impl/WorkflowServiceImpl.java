package com.bank.collections.workflow.service.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.bank.collections.workflow.dto.WorkflowRequest;
import com.bank.collections.workflow.dto.WorkflowResponse;
import com.bank.collections.workflow.service.WorkflowService;

@Service
public class WorkflowServiceImpl implements WorkflowService {

    @Override
    public WorkflowResponse createWorkflow(WorkflowRequest request) {

        System.out.println("Received Workflow Request");
        System.out.println(request);

        WorkflowResponse response = new WorkflowResponse();

        response.setWorkflowId(UUID.randomUUID().toString());
        response.setStatus("PENDING");
        response.setMessage("Workflow Created Successfully");

        return response;
    }
}