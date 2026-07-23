package com.bank.collections.persistence.worker.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.bank.collections.persistence.entity.RequestControlEntity;

@Service
@Profile("worker")
public class LambdaInvoker {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(LambdaInvoker.class);

    public void invoke(RequestControlEntity request) {

        LOGGER.info("Invoking Lambda for Workflow Id : {}",
                request.getWorkflowId());

        try {

            Thread.sleep(2000);

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

        }

        LOGGER.info("Lambda completed successfully.");
    }

}