package com.bank.collections.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.collections.persistence.entity.RequestControlEntity;

public interface RequestControlRepository
        extends JpaRepository<RequestControlEntity, Long>,
                RequestControlRepositoryCustom {

    Optional<RequestControlEntity> findByWorkflowId(String workflowId);

}