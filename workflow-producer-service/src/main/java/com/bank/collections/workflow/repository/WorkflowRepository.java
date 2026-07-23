package com.bank.collections.workflow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bank.collections.workflow.entity.WorkflowEntity;

public interface WorkflowRepository
extends JpaRepository<WorkflowEntity, String> {
	List<WorkflowEntity> findByCustomerId(String customerId);
}