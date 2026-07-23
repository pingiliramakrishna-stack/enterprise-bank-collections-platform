package com.bank.collections.persistence.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bank.collections.persistence.entity.RequestControlEntity;
import com.bank.collections.persistence.enums.RequestStatus;
import com.bank.collections.persistence.event.WorkflowCreatedEvent;
import com.bank.collections.persistence.repository.RequestControlRepository;

@Service
@Transactional
public class RequestPersistenceService {

    private static final Logger logger =
            LoggerFactory.getLogger(RequestPersistenceService.class);

    private final RequestControlRepository repository;

    public RequestPersistenceService(
            RequestControlRepository repository) {

        this.repository = repository;
    }

    public void saveRequest(WorkflowCreatedEvent event) {

        repository.findByWorkflowId(event.getWorkflowId())
                .ifPresent(existing -> {
                    throw new IllegalStateException(
                            "Duplicate workflow : "
                                    + event.getWorkflowId());
                });

        RequestControlEntity entity = new RequestControlEntity();

        entity.setWorkflowId(event.getWorkflowId());
        entity.setCaseNumber(event.getCaseNumber());
        entity.setCustomerId(event.getCustomerId());
        entity.setAccountNumber(event.getAccountNumber());

        entity.setStatus(RequestStatus.RECEIVED);
        entity.setRetryCount(0);
        entity.setRequestCreatedDate(event.getCreatedDate());

        repository.save(entity);

    }

}