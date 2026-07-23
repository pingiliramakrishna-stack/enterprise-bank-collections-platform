package com.bank.collections.persistence;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class RequestPersistenceServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RequestPersistenceServiceApplication.class, args);
    }

}