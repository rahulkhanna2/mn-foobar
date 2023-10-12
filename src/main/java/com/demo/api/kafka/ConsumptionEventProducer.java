package com.demo.api.kafka;

import com.demo.api.model.ConsumptionEvent;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.aop.Introduction;
import io.micronaut.context.annotation.Value;
import jakarta.inject.Singleton;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

@Singleton
@Introduction
public class ConsumptionEventProducer {

    private final ConsumptionEventKafkaClient consumptionEventKafkaClient;

    @Value("${kafka.producers.headers.type}")
    private String headersType;


    public ConsumptionEventProducer(ConsumptionEventKafkaClient consumptionEventKafkaClient) {
        this.consumptionEventKafkaClient = consumptionEventKafkaClient;
    }

    public boolean sendKafkaEvent(String requestId) {
        List<RecordHeader> messageHeaders = List.of(new RecordHeader("type", headersType.getBytes(UTF_8)));
        try {
            var objMapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.ALWAYS);
            ConsumptionEvent consumptionEvent = new ConsumptionEvent("dummy-name");
            String event = objMapper.writeValueAsString(consumptionEvent);
            consumptionEventKafkaClient.sendEvent(null, event, messageHeaders);
            return true;
        } catch (JsonProcessingException ex) {
            System.out.println("Error pushing message to Kafka" + ex);
            return false;
        }
    }
}