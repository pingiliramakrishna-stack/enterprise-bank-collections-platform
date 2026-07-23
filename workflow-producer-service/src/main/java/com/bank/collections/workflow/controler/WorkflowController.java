package com.bank.collections.workflow.controler;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.bank.collections.workflow.dto.WorkflowDetailsResponse;
import com.bank.collections.workflow.dto.WorkflowRequest;
import com.bank.collections.workflow.dto.WorkflowResponse;
import com.bank.collections.workflow.service.WorkflowService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/workflows")
public class WorkflowController {

    private final WorkflowService workflowService;
    private static final Logger logger = LoggerFactory.getLogger(WorkflowController.class);

    public WorkflowController(WorkflowService workflowService) {
        this.workflowService = workflowService;
    }

    @Operation(
    	    summary = "Create Workflow",
    	    description = "Creates a new banking collection workflow request"
    	)
    	@PostMapping
    	public ResponseEntity<WorkflowResponse> createWorkflow(
    	        @Valid @RequestBody WorkflowRequest request)  {
    	logger.info("Received workflow request: {}", request);

        WorkflowResponse response = workflowService.createWorkflow(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }
    
    @GetMapping("/{workflowId}")
    public ResponseEntity<WorkflowDetailsResponse> getWorkflow(
            @PathVariable String workflowId) {

        return ResponseEntity.ok(
                workflowService.getWorkflow(workflowId));
    }
    
    @GetMapping
    public ResponseEntity<Page<WorkflowDetailsResponse>> getAllWorkflows(

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(
                workflowService.getAllWorkflows(page, size));
    }
}