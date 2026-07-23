package com.bank.collections.persistence.worker.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.bank.collections.persistence.entity.RequestControlEntity;

@Component
@Profile("worker")
public class PdfGenerationProcessor implements RequestProcessor {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(PdfGenerationProcessor.class);

    @Override
    public void process(RequestControlEntity request) throws Exception {

        LOGGER.info("Invoking Lambda for Workflow Id : {}",
                request.getWorkflowId());

        // TODO:
        // AWS Lambda SDK invocation will be added here.

        Thread.sleep(2000);

        LOGGER.info("Lambda execution completed successfully.");

    }

}