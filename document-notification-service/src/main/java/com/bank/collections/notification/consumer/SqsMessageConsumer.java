package com.bank.collections.notification.consumer;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.bank.collections.notification.event.DocumentCompletionEvent;
import com.bank.collections.notification.event.DocumentGeneratedEvent;
import com.bank.collections.notification.publisher.DocumentCompletionPublisher;
import com.bank.collections.notification.service.DocumentPersistenceService;
import com.fasterxml.jackson.databind.ObjectMapper;

import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageResponse;

@Component
public class SqsMessageConsumer implements Runnable {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(SqsMessageConsumer.class);

    private final SqsClient sqsClient;

    private final ObjectMapper objectMapper;

    private final String queueName;

    private final DocumentPersistenceService persistenceService;

    private final DocumentCompletionPublisher completionPublisher;

    private volatile boolean running = true;

    public SqsMessageConsumer(
            SqsClient sqsClient,
            ObjectMapper objectMapper,
            @Value("${aws.sqs.queue-name}") String queueName,
            DocumentPersistenceService persistenceService,
            DocumentCompletionPublisher completionPublisher) {

        this.sqsClient = sqsClient;
        this.objectMapper = objectMapper;
        this.queueName = queueName;
        this.persistenceService = persistenceService;
        this.completionPublisher = completionPublisher;
    }

    @Override
    public void run() {

        LOGGER.info(
                "SQS Consumer started. Queue={}",
                queueName);

        String queueUrl =
                sqsClient.getQueueUrl(builder ->
                        builder.queueName(queueName))
                        .queueUrl();

        LOGGER.info(
                "SQS Queue URL resolved: {}",
                queueUrl);

        while (running) {

            try {

                ReceiveMessageRequest request =
                        ReceiveMessageRequest.builder()
                                .queueUrl(queueUrl)
                                .maxNumberOfMessages(1)
                                .waitTimeSeconds(10)
                                .visibilityTimeout(30)
                                .build();

                ReceiveMessageResponse response =
                        sqsClient.receiveMessage(request);

                for (Message message : response.messages()) {

                    processMessage(
                            message,
                            queueUrl);
                }

            } catch (Exception ex) {

                LOGGER.error(
                        "Error while polling SQS.",
                        ex);

                try {

                    Thread.sleep(5000);

                } catch (InterruptedException interruptedException) {

                    Thread.currentThread().interrupt();

                    break;
                }
            }
        }

        LOGGER.info("SQS Consumer stopped.");
    }

    private void processMessage(
            Message message,
            String queueUrl) {

        try {

            LOGGER.info(
                    "Received SQS message. MessageId={}",
                    message.messageId());

            LOGGER.info(
                    "Raw SQS message body: {}",
                    message.body());

            /*
             * SNS -> SQS sends an SNS envelope.
             *
             * The actual business JSON is inside
             * the "Message" field.
             */

            Map<?, ?> snsEnvelope =
                    objectMapper.readValue(
                            message.body(),
                            Map.class);

            String businessMessage =
                    String.valueOf(
                            snsEnvelope.get("Message"));

            LOGGER.info(
                    "Extracted SNS business message: {}",
                    businessMessage);

            DocumentGeneratedEvent event =
                    objectMapper.readValue(
                            businessMessage,
                            DocumentGeneratedEvent.class);

            LOGGER.info(
                    "Document Generated Event received: {}",
                    event);

            /*
             * STEP 1:
             * Persist document metadata.
             */
            persistenceService.saveDocument(event);

            LOGGER.info(
                    "Document persistence completed successfully. "
                            + "WorkflowId={}",
                    event.getWorkflowId());

            /*
             * STEP 2:
             * Create completion event.
             */
            DocumentCompletionEvent completionEvent =
                    new DocumentCompletionEvent(
                            event.getWorkflowId(),
                            event.getDocumentId(),
                            event.getS3Key(),
                            "COMPLETED");

            /*
             * STEP 3:
             * Publish completion event to Kafka.
             */
            completionPublisher.publish(completionEvent);

            LOGGER.info(
                    "Document completion event published. "
                            + "WorkflowId={}",
                    event.getWorkflowId());

            /*
             * STEP 4:
             * Delete SQS message only after the processing
             * above has completed successfully.
             */
            deleteMessage(
                    queueUrl,
                    message);

        } catch (Exception ex) {

            LOGGER.error(
                    "Unable to process SQS message. "
                            + "Message will remain available for retry.",
                    ex);
        }
    }

    private void deleteMessage(
            String queueUrl,
            Message message) {

        DeleteMessageRequest deleteRequest =
                DeleteMessageRequest.builder()
                        .queueUrl(queueUrl)
                        .receiptHandle(
                                message.receiptHandle())
                        .build();

        sqsClient.deleteMessage(deleteRequest);

        LOGGER.info(
                "SQS message deleted successfully. MessageId={}",
                message.messageId());
    }
}