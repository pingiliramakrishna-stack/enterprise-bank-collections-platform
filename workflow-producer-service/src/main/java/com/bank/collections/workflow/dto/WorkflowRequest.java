package com.bank.collections.workflow.dto;

import jakarta.validation.constraints.NotBlank;

public class WorkflowRequest {

    @NotBlank
    private String customerId;

    @NotBlank
    private String accountNumber;

    @NotBlank
    private String documentType;

    @NotBlank
    private String channel;

    public WorkflowRequest() {
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
}