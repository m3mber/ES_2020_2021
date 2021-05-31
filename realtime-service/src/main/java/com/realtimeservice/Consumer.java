package com.realtimeservice;

import org.apache.kafka.clients.producer.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

@Service
public class Consumer {

    private final Logger logger = LoggerFactory.getLogger(Producer.class);
    private String msg;

    @KafkaListener(topics = "ESP13_bus_data", groupId = "group_id")
    public void consume(String message) throws IOException {

        message = message.replaceAll("[\\[\\]\\(\\)]", "");
        //setMessage(message);
        System.out.println(message);
        //logger.info(String.format("#### -> Consumed message -> %s", message));
    }

    private void setMessage(String message) {
        msg = message;

        /*** WORKING ON ****/

        /*ObjectMapper objectMapper = new ObjectMapper();
        try {
            DataBusInfo dataBusInfo = objectMapper.readValue(msg, DataBusInfo.class);
            System.out.println("Id: " + dataBusInfo.getId() + "node: " + dataBusInfo.getNode_id());

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }*/
    }
}