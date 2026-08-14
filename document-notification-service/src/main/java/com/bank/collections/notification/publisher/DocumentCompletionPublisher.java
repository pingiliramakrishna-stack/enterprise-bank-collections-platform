package com.bank.collections.notification.publisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.bank.collections.notification.event.DocumentCompletionEvent;

@Component
public class DocumentCompletionPublisher {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(DocumentCompletionPublisher.class);

    private static final String TOPIC =
            "document-completion";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public DocumentCompletionPublisher(
            KafkaTemplate<String, Object> kafkaTemplate) {

        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(
            DocumentCompletionEvent event) {

        LOGGER.info(
                "Publishing DocumentCompletionEvent. "
                        + "WorkflowId={}, DocumentId={}",
                event.getWorkflowId(),
                event.getDocumentId());

        kafkaTemplate.send(
                TOPIC,
                event.getWorkflowId(),
                event);

        LOGGER.info(
                "DocumentCompletionEvent submitted to Kafka. "
                        + "Topic={}, WorkflowId={}",
                TOPIC,
                event.getWorkflowId());
    }
}