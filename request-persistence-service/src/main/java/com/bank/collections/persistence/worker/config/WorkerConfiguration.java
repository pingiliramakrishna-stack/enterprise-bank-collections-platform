package com.bank.collections.persistence.worker.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@Profile("worker")
public class WorkerConfiguration {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(WorkerConfiguration.class);

    @Bean
    public TaskExecutor workerExecutor() {

        LOGGER.info("Creating Worker Executor.");

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        executor.setCorePoolSize(1);
        executor.setMaxPoolSize(1);
        executor.setQueueCapacity(0);
        executor.setThreadNamePrefix("worker-");

        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(30);

        executor.initialize();

        return executor;
    }

}