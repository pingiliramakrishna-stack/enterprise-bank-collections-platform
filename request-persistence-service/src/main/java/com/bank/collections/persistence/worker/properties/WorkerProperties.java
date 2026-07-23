package com.bank.collections.persistence.worker.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("worker")
@ConfigurationProperties(prefix = "worker")
public class WorkerProperties {

    private long pollingIntervalMs = 5000;
    private int maxRetryCount = 3;

    public int getMaxRetryCount() {
		return maxRetryCount;
	}

	public void setMaxRetryCount(int maxRetryCount) {
		this.maxRetryCount = maxRetryCount;
	}

	public long getPollingIntervalMs() {
        return pollingIntervalMs;
    }

    public void setPollingIntervalMs(long pollingIntervalMs) {
        this.pollingIntervalMs = pollingIntervalMs;
    }
}