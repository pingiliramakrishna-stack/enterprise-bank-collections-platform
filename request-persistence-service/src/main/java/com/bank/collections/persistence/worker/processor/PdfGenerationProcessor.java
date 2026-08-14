package com.bank.collections.persistence.worker.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.bank.collections.persistence.entity.RequestControlEntity;
import com.bank.collections.persistence.worker.service.LambdaInvoker;

@Component
@Profile("worker")
public class PdfGenerationProcessor implements RequestProcessor {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(PdfGenerationProcessor.class);

    private final LambdaInvoker lambdaInvoker;

    public PdfGenerationProcessor(LambdaInvoker lambdaInvoker) {
        this.lambdaInvoker = lambdaInvoker;
    }

    @Override
    public void process(RequestControlEntity request) throws Exception {

        LOGGER.info(
                "Starting PDF generation for Workflow Id : {}",
                request.getWorkflowId());

        lambdaInvoker.invoke(request);

        LOGGER.info(
                "PDF generation Lambda completed for Workflow Id : {}",
                request.getWorkflowId());
    }
}