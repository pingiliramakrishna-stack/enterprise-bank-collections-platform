package com.bank.collections.persistence.consumer;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bank.collections.persistence.entity.RequestControlEntity;
import com.bank.collections.persistence.enums.RequestStatus;
import com.bank.collections.persistence.event.DocumentCompletionEvent;
import com.bank.collections.persistence.repository.RequestControlRepository;

@Component
public class DocumentCompletionConsumer {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(DocumentCompletionConsumer.class);

    private final RequestControlRepository repository;

    public DocumentCompletionConsumer(
            RequestControlRepository repository) {

        this.repository = repository;
    }

    @KafkaListener(
            topics = "document-completion",
            groupId = "request-completion-group",
            containerFactory = "documentCompletionKafkaListenerContainerFactory")
    @Transactional
    public void consume(DocumentCompletionEvent event) {

        LOGGER.info(
                "Received DocumentCompletionEvent. WorkflowId={}, "
                        + "DocumentId={}, Status={}",
                event.getWorkflowId(),
                event.getDocumentId(),
                event.getStatus());

        RequestControlEntity entity =
                repository.findByWorkflowId(
                        event.getWorkflowId())
                        .orElseThrow(() ->
                                new IllegalStateException(
                                        "REQUEST_CONTROL record not found "
                                                + "for workflowId="
                                                + event.getWorkflowId()));

        LOGGER.info(
                "Current status for WorkflowId={} is {}",
                entity.getWorkflowId(),
                entity.getStatus());

        entity.setStatus(RequestStatus.COMPLETED);
        entity.setUpdatedDate(LocalDateTime.now());

        repository.save(entity);

        LOGGER.info(
                "REQUEST_CONTROL updated successfully. "
                        + "WorkflowId={}, Status={}",
                entity.getWorkflowId(),
                entity.getStatus());
    }
}