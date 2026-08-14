package com.bank.collections.persistence.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.bank.collections.persistence.event.DocumentCompletionEvent;

@Configuration
public class KafkaConsumerConfig {

    @Bean
    public ConsumerFactory<String, DocumentCompletionEvent>
            documentCompletionConsumerFactory() {

        Map<String, Object> properties =
                new HashMap<>();

        properties.put(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "localhost:9092");

        properties.put(
                ConsumerConfig.GROUP_ID_CONFIG,
                "request-completion-group");

        properties.put(
                ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,
                "earliest");

        properties.put(
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class);

        properties.put(
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                JsonDeserializer.class);

        properties.put(
                JsonDeserializer.TRUSTED_PACKAGES,
                "com.bank.collections.persistence.event");

        properties.put(
                JsonDeserializer.VALUE_DEFAULT_TYPE,
                DocumentCompletionEvent.class.getName());

        properties.put(
                JsonDeserializer.USE_TYPE_INFO_HEADERS,
                false);

        return new DefaultKafkaConsumerFactory<>(
                properties);
    }

    @Bean(name = "documentCompletionKafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, DocumentCompletionEvent>
            documentCompletionKafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, DocumentCompletionEvent>
                factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(
                documentCompletionConsumerFactory());

        return factory;
    }
}