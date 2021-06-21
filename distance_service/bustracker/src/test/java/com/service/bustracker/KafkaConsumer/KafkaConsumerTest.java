package com.service.bustracker.KafkaConsumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import com.service.bustracker.model.DataBusInfo;

import java.util.concurrent.CountDownLatch;

@Component
public class KafkaConsumerTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerTest.class);

    private CountDownLatch latch = new CountDownLatch(1);
    private String payload = null;
    
 
   

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