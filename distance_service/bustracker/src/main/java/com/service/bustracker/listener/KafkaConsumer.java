package com.service.bustracker.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.bustracker.model.DataBusInfo;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


/** This service will be a Distance Service
 * Service will listen the ESP13_service_alarm and send information to client
 * when the bus is a stop away.
 */
@Service
public class KafkaConsumer
{
    private String msg;

    /** Consume real time data
     * Topic will be changed to ESP13_distance_alarm
     */
    @KafkaListener(topics="ESP13_bus_data", groupId = "group_id")
    public void consume(String message)
    {
        /* Remove characters [ ] from the receiced message */
        message = message.replaceAll("[\\[\\]\\(\\)]", "");

        /* Set the String message to an Object */
        setMessage(message);
    }

    /* DataBusInfo is an object from the received message */
    private void setMessage(String message)
    {
        msg = message;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            DataBusInfo dataBusInfo = objectMapper.readValue(msg, DataBusInfo.class);
            System.out.println("Id: " + dataBusInfo.getId() + "node: " + dataBusInfo.getNode_id());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
