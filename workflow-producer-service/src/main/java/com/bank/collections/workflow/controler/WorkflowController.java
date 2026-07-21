package com.bank.collections.workflow.controler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.bank.collections.workflow.dto.WorkflowRequest;
import com.bank.collections.workflow.dto.WorkflowResponse;
import com.bank.collections.workflow.service.WorkflowService;

@RestController
@RequestMapping("/api/v1/workflows")
public class WorkflowController {

    private final WorkflowService workflowService;

    public WorkflowController(WorkflowService workflowService) {
        this.workflowService = workflowService;
    }

    @PostMapping
    public ResponseEntity<WorkflowResponse> createWorkflow(
            @Validated @RequestBody WorkflowRequest request) {

        WorkflowResponse response = workflowService.createWorkflow(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }
}