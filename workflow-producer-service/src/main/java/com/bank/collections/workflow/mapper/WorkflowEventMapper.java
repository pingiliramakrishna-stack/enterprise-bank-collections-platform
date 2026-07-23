package com.bank.collections.workflow.mapper;

import java.time.LocalDateTime;

import com.bank.collections.workflow.entity.WorkflowEntity;
import com.bank.collections.workflow.event.WorkflowCreatedEvent;

public final class WorkflowEventMapper {

    private WorkflowEventMapper() {
    }

    public static WorkflowCreatedEvent toEvent(
            WorkflowEntity entity) {

        WorkflowCreatedEvent event = new WorkflowCreatedEvent();

        event.setWorkflowId(entity.getWorkflowId());
        event.setCaseNumber(entity.getCaseNumber());
        event.setCustomerId(entity.getCustomerId());
        event.setAccountNumber(entity.getAccountNumber());
        event.setStatus(entity.getStatus());
        event.setCreatedDate(LocalDateTime.now());

        return event;
    }
}