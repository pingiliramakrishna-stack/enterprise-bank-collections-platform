package com.bank.collections.persistence.worker.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bank.collections.persistence.entity.RequestControlEntity;
import com.bank.collections.persistence.enums.RequestStatus;
import com.bank.collections.persistence.repository.RequestControlRepository;

@Service
public class RequestCompletionService {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(RequestCompletionService.class);

    private final RequestControlRepository repository;

    public RequestCompletionService(RequestControlRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void markCompleted(RequestControlEntity request) {

        request.setStatus(RequestStatus.COMPLETED);

        repository.save(request);

        LOGGER.info("Workflow {} completed successfully.",
                request.getWorkflowId());

    }

}