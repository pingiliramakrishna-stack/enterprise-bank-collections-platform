package com.bank.collections.notification.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.collections.notification.entity.DocumentControlEntity;

public interface DocumentControlRepository
        extends JpaRepository<DocumentControlEntity, Long> {

    Optional<DocumentControlEntity> findByWorkflowId(
            String workflowId);
}