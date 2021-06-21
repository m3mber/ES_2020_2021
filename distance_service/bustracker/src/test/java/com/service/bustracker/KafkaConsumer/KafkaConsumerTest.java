package com.service.bustracker.KafkaConsumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class KafkaConsumerTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerTest.class);

    private CountDownLatch latch = new CountDownLatch(1);
    private String payload = null;

    @KafkaListener(topics = "ESP13_bus_distTest", groupId="groupTest")
    public void receive(String consumerRecord) {
        LOGGER.info("received payload='{}'", consumerRecord.toString());
        setPayload(consumerRecord.toString());
        latch.countDown();
    }

    private void setPayload(String toString) {
        this.payload=payload;
    }

    public CountDownLatch getLatch() {
        return latch;
    }

    public String getPayload() {
        return payload;
    }
}