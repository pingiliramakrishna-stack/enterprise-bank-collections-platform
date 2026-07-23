package com.bank.collections.workflow.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic workflowCreatedTopic() {

        return new NewTopic(
                "workflow-created",
                3,          // partitions
                (short)1    // replication factor
        );

    }
}