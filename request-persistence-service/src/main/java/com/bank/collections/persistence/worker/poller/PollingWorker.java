package com.bank.collections.persistence.worker.poller;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

import jakarta.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.bank.collections.persistence.entity.RequestControlEntity;
import com.bank.collections.persistence.worker.processor.RequestProcessor;
import com.bank.collections.persistence.worker.properties.WorkerProperties;
import com.bank.collections.persistence.worker.service.LambdaInvoker;
import com.bank.collections.persistence.worker.service.RequestClaimService;
import com.bank.collections.persistence.worker.service.RequestCompletionService;
import com.bank.collections.persistence.worker.service.RetryService;

@Component
@Profile("worker")
public class PollingWorker implements Runnable {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(PollingWorker.class);

    private final AtomicBoolean running = new AtomicBoolean(true);

    private final RequestClaimService requestClaimService;

    private final RequestProcessor requestProcessor;

    private final WorkerProperties workerProperties;
    private final RequestCompletionService completionService;

    private final RetryService retryService;

    public PollingWorker(
            RequestClaimService requestClaimService,
            RequestProcessor requestProcessor,
            RequestCompletionService completionService,
            RetryService retryService,
            WorkerProperties workerProperties) {

        this.requestClaimService = requestClaimService;
        this.requestProcessor = requestProcessor;
        this.completionService = completionService;
        this.retryService = retryService;
        this.workerProperties = workerProperties;
    }

    @Override
    public void run() {

        LOGGER.info("Polling Worker Started.");

        while (running.get()) {

            try {

                Optional<RequestControlEntity> request =
                        requestClaimService.claimNextRequest();

                if (request.isEmpty()) {

                    LOGGER.info("No pending requests found.");

                    Thread.sleep(workerProperties.getPollingIntervalMs());

                    continue;
                }

                RequestControlEntity requestEntity = request.get();

                LOGGER.info("Processing Workflow {}",
                        requestEntity.getWorkflowId());

                try {

                	 requestProcessor.process(requestEntity);

                     completionService.markCompleted(requestEntity);

                }
                catch(Exception ex) {

                    retryService.processFailure(requestEntity, ex);

                }

            } catch (InterruptedException ex) {

                LOGGER.warn("Worker interrupted.");

                Thread.currentThread().interrupt();

                break;

            } catch (Exception ex) {

                LOGGER.error("Unexpected error.", ex);

            }

        }

        LOGGER.info("Polling Worker Stopped.");

    }

    @PreDestroy
    public void shutdown() {

        LOGGER.info("Stopping Polling Worker.");

        running.set(false);

    }

}