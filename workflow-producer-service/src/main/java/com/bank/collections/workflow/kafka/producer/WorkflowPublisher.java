package com.bank.collections.workflow.kafka.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.bank.collections.workflow.event.WorkflowCreatedEvent;

@Service
public class WorkflowPublisher {
	private static final Logger logger =
	        LoggerFactory.getLogger(WorkflowPublisher.class);

    private static final String TOPIC =  "workflow-created-v2";

    private final KafkaTemplate<String, WorkflowCreatedEvent> kafkaTemplate;

    public WorkflowPublisher(
            KafkaTemplate<String, WorkflowCreatedEvent> kafkaTemplate) {

        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(WorkflowCreatedEvent event) {

        logger.info("Publishing WorkflowCreatedEvent : {}", event.getWorkflowId());

        kafkaTemplate.send(TOPIC, event.getWorkflowId(), event)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        logger.info(
                                "Message published successfully. Topic={}, Partition={}, Offset={}",
                                result.getRecordMetadata().topic(),
                                result.getRecordMetadata().partition(),
                                result.getRecordMetadata().offset());
                    } else {
                        logger.error("Failed to publish WorkflowCreatedEvent", ex);
                    }
                });
    }
}