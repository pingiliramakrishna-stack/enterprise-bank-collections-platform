package com.bank.collections.workflow.model;

public enum WorkflowStatus {

    RECEIVED,

    VALIDATED,

    QUEUED,

    DOCUMENT_GENERATION,

    DOCUMENT_GENERATED,

    NOTIFICATION_SENT,

    COMPLETED,

    FAILED
}