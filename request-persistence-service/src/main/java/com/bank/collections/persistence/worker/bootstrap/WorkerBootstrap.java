package com.bank.collections.persistence.worker.bootstrap;

import com.bank.collections.persistence.worker.poller.PollingWorker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

@Component
@Profile("worker")
public class WorkerBootstrap {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(WorkerBootstrap.class);

    private final TaskExecutor workerExecutor;

    private final PollingWorker pollingWorker;

    public WorkerBootstrap(TaskExecutor workerExecutor,
                           PollingWorker pollingWorker) {

        this.workerExecutor = workerExecutor;
        this.pollingWorker = pollingWorker;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void startWorker() {

        LOGGER.info("Submitting Polling Worker.");

        workerExecutor.execute(pollingWorker);

    }

}