package com.demo.api.kafka;

import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.Topic;
import io.micronaut.http.annotation.Header;
import org.apache.kafka.common.header.internals.RecordHeader;

import java.util.List;

@KafkaClient
public interface ConsumptionEventKafkaClient {
    @Topic("dummy-project")
    void sendEvent(@KafkaKey String key, String value, @Header List<RecordHeader> headers);
}
