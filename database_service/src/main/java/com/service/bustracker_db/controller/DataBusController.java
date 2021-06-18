package com.service.bustracker_db.controller;

import com.service.bustracker_db.model.DataBusInfo;
import com.service.bustracker_db.repository.DataBusRepository;
import com.service.bustracker_db.service.DataBusService;
import org.apache.kafka.clients.producer.Producer;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bus")
public class DataBusController
{
    @Autowired
    private DataBusService dataBusService;

    private final Logger logger = LoggerFactory.getLogger(Producer.class);


    /**
     * Consume data from kafka Topic
     */
    @KafkaListener(topics = "ESP13_bus_data", groupId = "group_2")
    public void consume(String message)
    {
        saveMessage(message);
    }


    /**
     * Request Service to save the message into database
     */
    public void saveMessage(String message)
    {
        /* JSON Format */
        String json_message = "{'data_bus' : " + message + "}";
        System.out.println(message);
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
        logger.info(String.format("#### -> Getting bus data"));
        /* todo: change speed */
        dataBusService.addBusData(id,node_id,location_id,head,lon,lat,0,ts,write_time);
        //getLatitudeAndLongitude("00000000-0000-0000-0000-000000002481");
        //getAllBusIds();
    }

    /* -------------------------------------------------------------------------------------------------------*/
    /* todo: Change here to communicate with react */

    /** This function retrives a List with all previous coords of that bus id or null if no record found
    *
    * List returns   [ lat, lon ]
    * for example -> [41.166058, -8.58294, 41.17144, -8.594005, 41.172817, -8.607225] */

    @GetMapping("/locations")
    @CrossOrigin
    public List<String> getLatitudeAndLongitude(@RequestParam String busId)
    {
        System.out.println(">>>>" + busId);
        List<String> latAndLon = dataBusService.getLatitudeAndLongitude(busId);
        System.out.println(latAndLon.toString());
        return latAndLon;

    }


    /** This function returns all bus ids to present on the dropdown menu
     *
     * */
    @GetMapping("/ids")
    @CrossOrigin
    public List<String> getAllBusIds()
    {
        List<String> busIds = dataBusService.getAllBusIds();
        //System.out.println(busIds.toString());
        logger.info(String.format("#### -> Returning all ids, success"));
        return busIds;

    }
}
