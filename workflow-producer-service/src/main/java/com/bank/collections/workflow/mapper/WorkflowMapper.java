package com.bank.collections.workflow.mapper;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.bank.collections.workflow.dto.WorkflowDetailsResponse;
import com.bank.collections.workflow.dto.WorkflowRequest;
import com.bank.collections.workflow.dto.WorkflowResponse;
import com.bank.collections.workflow.entity.WorkflowEntity;
import com.bank.collections.workflow.model.WorkflowStatus;

public class WorkflowMapper {

    public static WorkflowEntity toEntity(WorkflowRequest request,
                                          WorkflowResponse response) {

        WorkflowEntity entity = new WorkflowEntity();

        entity.setWorkflowId(response.getWorkflowId());
        entity.setCustomerId(request.getCustomerId());
        entity.setAccountNumber(request.getAccountNumber());
        entity.setDocumentType(request.getDocumentType());
        entity.setChannel(request.getChannel());
        entity.setStatus(WorkflowStatus.RECEIVED);
        String caseNumber =
                "COL-" +
                LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE)
                + "-" +
                System.currentTimeMillis();
        entity.setCaseNumber(caseNumber);

        return entity;
    }
    
    public static WorkflowDetailsResponse toResponse(
            WorkflowEntity entity) {

        WorkflowDetailsResponse response =
                new WorkflowDetailsResponse();

        response.setWorkflowId(entity.getWorkflowId());
        response.setCustomerId(entity.getCustomerId());
        response.setAccountNumber(entity.getAccountNumber());
        response.setDocumentType(entity.getDocumentType());
        response.setChannel(entity.getChannel());
        response.setStatus(entity.getStatus().toString());
        response.setCaseNumber(entity.getCaseNumber());

        return response;
    }
}