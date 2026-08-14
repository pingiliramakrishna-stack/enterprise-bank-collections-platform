package com.bank.collections.notification.service;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bank.collections.notification.entity.DocumentControlEntity;
import com.bank.collections.notification.event.DocumentGeneratedEvent;
import com.bank.collections.notification.repository.DocumentControlRepository;

@Service
public class DocumentPersistenceService {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(DocumentPersistenceService.class);

    private final DocumentControlRepository repository;

    public DocumentPersistenceService(
            DocumentControlRepository repository) {

        this.repository = repository;
    }

    @Transactional
    public void saveDocument(DocumentGeneratedEvent event) {

        LOGGER.info(
                "Persisting document. WorkflowId={}, DocumentId={}",
                event.getWorkflowId(),
                event.getDocumentId());

        /*
         * Idempotency check.
         *
         * If the same SQS message is delivered again,
         * we don't want to create duplicate document records.
         */
        if (repository.findByWorkflowId(event.getWorkflowId())
                .isPresent()) {

            LOGGER.info(
                    "Document already exists for WorkflowId={}. "
                    + "Skipping duplicate.",
                    event.getWorkflowId());

            return;
        }

        LocalDateTime now = LocalDateTime.now();

        DocumentControlEntity entity =
                new DocumentControlEntity();

        entity.setWorkflowId(event.getWorkflowId());
        entity.setDocumentId(event.getDocumentId());
        entity.setS3Key(event.getS3Key());
        entity.setStatus(event.getStatus());
        entity.setCreatedDate(now);
        entity.setUpdatedDate(now);

        repository.save(entity);

        LOGGER.info(
                "Document persisted successfully. "
                + "WorkflowId={}, DocumentId={}, S3Key={}",
                event.getWorkflowId(),
                event.getDocumentId(),
                event.getS3Key());
    }
}