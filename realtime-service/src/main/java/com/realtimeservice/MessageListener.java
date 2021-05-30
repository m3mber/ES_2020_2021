package com.realtimeservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageListener {
    @Autowired
    SimpMessagingTemplate template;

    @KafkaListener(topics = "ESP13_bus_data", groupId = "group_id")
    public void listen(DataBusInfo message) {
        System.out.println("sending via kafka listener..");
        template.convertAndSend("/location/ESP13_bus_data", message);
    }
}