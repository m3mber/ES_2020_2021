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
    public DataBusInfo dataBusInfo =new DataBusInfo(
                24582903,"00000000-0000-0000-0000-000000002518",
                        225,24.53,"-8.610583","41.14898",12,
                        "2018-10-08 00:00:00.001","2018-10-08 00:00:01.638819") ;

    @KafkaListener(topics = "ESP13_bus_data", groupId = "group_id")
    public void consume(String message) throws IOException {


        message = message.replaceAll("[\\[\\]\\(\\)]", "");
        setMessage(message);
        System.out.println(message);
        logger.info(String.format("#### -> Consumed message -> %s", message));
    }

    private void setMessage(String message) {
        msg = message;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            DataBusInfo busDataInfo = objectMapper.readValue(msg, DataBusInfo.class);
            System.out.println("Id: " + busDataInfo.getId() + "node: " + busDataInfo.getNode_id());
            this.dataBusInfo=busDataInfo;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}