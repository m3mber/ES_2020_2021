package com.service.bustracker_db.consumer;

import com.service.bustracker_db.controller.DataBusController;
import com.service.bustracker_db.model.DataBusInfo;
import com.service.bustracker_db.repository.DataBusRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * This service will be a Distance Service Service will listen the
 * ESP13_service_alarm and send information to client when the bus is a stop
 * away.
 */
@Service
public class Consumer
{
    @Autowired
    private DataBusController dataBusController;

    /**
     * Consume real time data Topic will be changed to ESP13_distance_alarm
     */
    @KafkaListener(topics = "ESP13_bus_data", groupId = "group_2")
    public void consume(String message)
    {
        sendToController(message);
    }

    public void sendToController(String message)
    {
        /* JSON Format */
        String json_message = "{'data_bus' : " + message + "}";

        /* Set the string message to object */
        JSONObject root = new JSONObject(json_message);

        /* Getting JSON array */
        JSONArray dataBusArr = root.getJSONArray("data_bus");
        JSONObject jsonDataBus = dataBusArr.getJSONObject(0);

        Integer id = Integer.parseInt(jsonDataBus.getString("id"));
        String node_id = jsonDataBus.getString("node_id");
        int location_id = Integer.parseInt(jsonDataBus.getString("location_id"));
        double head = Double.parseDouble(jsonDataBus.getString("head"));
        String lon = jsonDataBus.getString("lon");
        String lat = jsonDataBus.getString("lat");
        //int speed = Integer.parseInt(jsonDataBus.getString("speed"));
        String ts = jsonDataBus.getString("ts");
        String write_time = jsonDataBus.getString("write_time");

        /* todo: change speed */
        dataBusController.addBusData(id,node_id,location_id,head,lon,lat,0,ts,write_time );


    }
}
