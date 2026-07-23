package com.bank.collections.persistence.worker.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bank.collections.persistence.entity.RequestControlEntity;
import com.bank.collections.persistence.enums.RequestStatus;
import com.bank.collections.persistence.repository.RequestControlRepository;
import com.bank.collections.persistence.worker.properties.WorkerProperties;

@Service
public class RetryService {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(RetryService.class);

    private final RequestControlRepository repository;
    private final WorkerProperties workerProperties;

    public RetryService(RequestControlRepository repository,
                        WorkerProperties workerProperties) {

        this.repository = repository;
        this.workerProperties = workerProperties;
    }

    @Transactional
    public void processFailure(RequestControlEntity request,
                               Exception ex) {

        LOGGER.error("Workflow {} failed.",
                request.getWorkflowId(), ex);

        int retryCount = request.getRetryCount() + 1;

        request.setRetryCount(retryCount);

        if (retryCount >= workerProperties.getMaxRetryCount()) {

            request.setStatus(RequestStatus.FAILED);

            LOGGER.error("Workflow {} moved to FAILED.",
                    request.getWorkflowId());

        } else {

            request.setStatus(RequestStatus.RECEIVED);

            LOGGER.info("Workflow {} returned to RECEIVED. Retry {}",
                    request.getWorkflowId(),
                    retryCount);

        }

        repository.save(request);

    }

}