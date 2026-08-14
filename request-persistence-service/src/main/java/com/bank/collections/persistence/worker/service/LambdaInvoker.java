package com.bank.collections.persistence.worker.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.bank.collections.persistence.entity.RequestControlEntity;

import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.lambda.model.InvokeRequest;
import software.amazon.awssdk.services.lambda.model.InvokeResponse;

@Service
@Profile("worker")
public class LambdaInvoker {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(LambdaInvoker.class);

    private final LambdaClient lambdaClient;

    private final String functionName;

    public LambdaInvoker(
            LambdaClient lambdaClient,
            @Value("${aws.lambda.pdf-function-name}") String functionName) {

        this.lambdaClient = lambdaClient;
        this.functionName = functionName;
    }

    public void invoke(RequestControlEntity request) {

        LOGGER.info(
                "Invoking Lambda '{}' for Workflow Id : {}",
                functionName,
                request.getWorkflowId());

        String payload = String.format("""
                {
                    "workflowId": "%s",
                    "caseNumber": "%s",
                    "customerId": "%s",
                    "accountNumber": "%s"
                }
                """,
                request.getWorkflowId(),
                request.getCaseNumber(),
                request.getCustomerId(),
                request.getAccountNumber());

        InvokeRequest invokeRequest = InvokeRequest.builder()
                .functionName(functionName)
                .payload(SdkBytes.fromUtf8String(payload))
                .build();

        InvokeResponse response =
                lambdaClient.invoke(invokeRequest);

        LOGGER.info(
                "Lambda invocation completed. Workflow Id={}, StatusCode={}",
                request.getWorkflowId(),
                response.statusCode());

        if (response.functionError() != null) {

            throw new RuntimeException(
                    "Lambda execution failed: "
                            + response.functionError());
        }
    }
}