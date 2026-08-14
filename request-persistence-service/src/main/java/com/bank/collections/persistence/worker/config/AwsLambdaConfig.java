package com.bank.collections.persistence.worker.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.lambda.LambdaClient;

@Configuration
@Profile("worker")
public class AwsLambdaConfig {

    @Value("${aws.region}")
    private String awsRegion;

    @Bean
    public LambdaClient lambdaClient() {

        return LambdaClient.builder()
                .region(Region.of(awsRegion))
                .build();
    }
}