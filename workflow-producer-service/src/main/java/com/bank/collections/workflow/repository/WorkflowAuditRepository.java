package com.bank.collections.workflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.collections.workflow.entity.WorkflowAuditEntity;

public interface WorkflowAuditRepository
extends JpaRepository<WorkflowAuditEntity, Long> {
}
