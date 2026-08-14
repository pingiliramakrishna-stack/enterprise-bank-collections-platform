package com.bank.collections.notification.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bank.collections.notification.consumer.SqsMessageConsumer;

@Configuration
public class SqsConsumerStartup {

    @Bean
    public Thread sqsConsumerThread(
            SqsMessageConsumer consumer) {

        Thread thread =
                new Thread(
                        consumer,
                        "sqs-consumer");

        thread.setDaemon(true);
        thread.start();

        return thread;
    }
}