package com.bank.collections.workflow.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bank.collections.workflow.controler.WorkflowController;
import com.bank.collections.workflow.dto.WorkflowDetailsResponse;
import com.bank.collections.workflow.dto.WorkflowRequest;
import com.bank.collections.workflow.dto.WorkflowResponse;
import com.bank.collections.workflow.repository.WorkflowAuditRepository;
import com.bank.collections.workflow.repository.WorkflowRepository;
import com.bank.collections.workflow.service.WorkflowService;
import com.bank.collections.workflow.entity.WorkflowAuditEntity;
import com.bank.collections.workflow.entity.WorkflowEntity;
import com.bank.collections.workflow.event.WorkflowCreatedEvent;
import com.bank.collections.workflow.exception.WorkflowNotFoundException;
import com.bank.collections.workflow.kafka.producer.WorkflowPublisher;
import com.bank.collections.workflow.mapper.WorkflowEventMapper;
import com.bank.collections.workflow.mapper.WorkflowMapper;
import com.bank.collections.workflow.model.WorkflowStatus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;

@Service
public class WorkflowServiceImpl implements WorkflowService {
	private static final Logger logger = LoggerFactory.getLogger(WorkflowServiceImpl.class);
	private final WorkflowRepository workflowRepository;

	private final WorkflowAuditRepository auditRepository;
	private final WorkflowPublisher workflowPublisher;

	public WorkflowServiceImpl(
	        WorkflowRepository workflowRepository,
	        WorkflowAuditRepository auditRepository,
	        WorkflowPublisher workflowPublisher) {

	    this.workflowRepository = workflowRepository;
	    this.auditRepository = auditRepository;
	    this.workflowPublisher =workflowPublisher;
	}
    
    @Transactional
    @Override
    public WorkflowResponse createWorkflow(WorkflowRequest request) {

    	 logger.info("Creating workflow for customer {}",
                 request.getCustomerId());

        WorkflowResponse response = new WorkflowResponse();

        response.setWorkflowId(UUID.randomUUID().toString());
        response.setStatus(WorkflowStatus.RECEIVED);
        response.setMessage("Workflow Created Successfully");
        
        WorkflowEntity entity = WorkflowMapper.toEntity(request, response);
        
        
        workflowRepository.save(entity);
        response.setCaseNumber(entity.getCaseNumber());
        
        WorkflowAuditEntity audit = new WorkflowAuditEntity();

        audit.setWorkflowId(entity.getWorkflowId());
        audit.setEvent("Workflow Created");

        auditRepository.save(audit);
        logger.info("Workflow {} created successfully",
                response.getWorkflowId());
        WorkflowCreatedEvent event =
                WorkflowEventMapper.toEvent(entity);
        workflowPublisher.publish(event); 

        logger.info("Workflow  successfully published to the kafka topic.");
        


        return response;
    }
    
    @Override
    public WorkflowDetailsResponse getWorkflow(String workflowId) {

        logger.info("Fetching workflow {}", workflowId);

        WorkflowEntity entity = workflowRepository.findById(workflowId)
                .orElseThrow(() -> new WorkflowNotFoundException(workflowId));
 
        
        WorkflowDetailsResponse response = WorkflowMapper.toResponse(entity);
        logger.info("Workflow {} retrived successfully",
                response.getWorkflowId());

        return response;
    }
    @Override
    public Page<WorkflowDetailsResponse> getAllWorkflows(int page,
                                                         int size) {

    	Pageable pageable =
    	        PageRequest.of(
    	                page,
    	                size,
    	                Sort.by("customerId").ascending());

        Page<WorkflowEntity> entityPage =
                workflowRepository.findAll(pageable);

        return entityPage.map(WorkflowMapper::toResponse);
    }
}