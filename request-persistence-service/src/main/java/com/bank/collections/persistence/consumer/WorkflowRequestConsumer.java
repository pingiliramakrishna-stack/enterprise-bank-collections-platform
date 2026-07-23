package com.bank.collections.persistence.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.bank.collections.persistence.event.WorkflowCreatedEvent;
import com.bank.collections.persistence.service.RequestPersistenceService;

@Component
public class WorkflowRequestConsumer {

    private static final Logger logger =
            LoggerFactory.getLogger(WorkflowRequestConsumer.class);

    private final RequestPersistenceService persistenceService;

    public WorkflowRequestConsumer(
            RequestPersistenceService persistenceService) {

        this.persistenceService = persistenceService;
    }

    @KafkaListener(
            topics = "workflow-created-v2",
            groupId = "request-persistence-group")
    public void consume(WorkflowCreatedEvent event) {

        logger.info("Received Workflow Event : {}", event.getWorkflowId());

        try {

            persistenceService.saveRequest(event);

            logger.info("Workflow {} processed successfully.",
                    event.getWorkflowId());

        } catch (Exception ex) {

            logger.error("Unable to process workflow {}",
                    event.getWorkflowId(), ex);

            throw ex;
        }

    }

}