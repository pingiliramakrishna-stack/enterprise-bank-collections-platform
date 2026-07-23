package com.bank.collections.persistence.worker.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bank.collections.persistence.entity.RequestControlEntity;
import com.bank.collections.persistence.enums.RequestStatus;
import com.bank.collections.persistence.repository.RequestControlRepository;

@Service
public class RequestClaimService {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(RequestClaimService.class);

    private final RequestControlRepository repository;

    public RequestClaimService(RequestControlRepository repository) {

        this.repository = repository;
    }

    @Transactional
    public Optional<RequestControlEntity> claimNextRequest() {

        Optional<RequestControlEntity> request =
                repository.claimNextRequest();

        if (request.isEmpty()) {

            return Optional.empty();

        }

        RequestControlEntity entity = request.get();

        LOGGER.info("Claimed Workflow {}",
                entity.getWorkflowId());

        entity.setStatus(RequestStatus.IN_PROGRESS);

        repository.save(entity);

        return Optional.of(entity);

    }

}