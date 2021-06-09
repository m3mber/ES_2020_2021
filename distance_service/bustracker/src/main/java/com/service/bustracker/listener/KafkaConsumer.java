package com.service.bustracker.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.service.bustracker.model.DataBusInfo;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * This service will be a Distance Service Service will listen the
 * ESP13_service_alarm and send information to client when the bus is a stop
 * away.
 */
@Service
public class KafkaConsumer
{

    private List<DataBusInfo> allBus = new ArrayList<DataBusInfo>();

    private boolean distanceFlag = false;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    /**
     * Consume real time data Topic will be changed to ESP13_distance_alarm
     */
    @KafkaListener(topics = "ESP13_bus_data", groupId = "group_1")
    public void consume(String message) {
        /* JSON format */
        String s = "{'data_bus' : " + message + "}";

        /* Set the String message to an Object */
        JSONObject root = new JSONObject(s);

        /* Getting JSON array */
        JSONArray dataBusArr = root.getJSONArray("data_bus");

        for (int i = 0; i < dataBusArr.length(); i++)
        {
            /* Retrieving JSON Data */
            JSONObject jsonDataBus = dataBusArr.getJSONObject(i);
            DataBusInfo dataBus = new DataBusInfo();

            String id = jsonDataBus.getString("id");
            String node_id = jsonDataBus.getString("node_id");
            String location_id = jsonDataBus.getString("location_id");
            String head = jsonDataBus.getString("head");
            String lon = jsonDataBus.getString("lon");
            String lat = jsonDataBus.getString("lat");
            String speed = jsonDataBus.getString("speed");
            String ts = jsonDataBus.getString("ts");
            String write_time = jsonDataBus.getString("write_time");


            /* todo: this bus id needs to be dynamic */
            if (node_id.equals("00000000-0000-0000-0000-000000002481"))
            {
                if (calculateDistance(Double.parseDouble(lat), Double.parseDouble(lon)))
                {
                    System.out.println("Bus is near from user location");
                    /* Sending message to Kafka Alarm topic if bus is near */
                    sendAlarmMessage("Bus is near, Lon: " + lon + " Lat: " + lat);
                }
            }


            /* Populate DataBusInfo
             * dataBus.setId(Long.parseLong(id)); dataBus.setNode_id(node_id);
             * dataBus.setLocation_id(Integer.parseInt(location_id));
             * dataBus.setHead(Double.parseDouble(head)); dataBus.setLon(lon);
             * dataBus.setLat(lat);
             * try { dataBus.setSpeed(Integer.parseInt(speed)); } catch (Exception e) {
             * dataBus.setSpeed(Integer.parseInt("0")); } dataBus.setTs(ts);
             * dataBus.setWrite_time(write_time);
             * allBus.add(dataBus); System.out.println("Adding to dataBus object");
             */
        }
    }

    private boolean calculateDistance(double lat2, double long2)
    {
        /* This is a static value */
        Double lat1 = 41.18038089795866;
        Double long1 = -8.622097932210133;

        double dist = org.apache.lucene.util.SloppyMath.haversinMeters(lat1, long1, lat2, long2);

        System.out.println("Distance " + dist + " meters");

        if (dist < 3000 && distanceFlag == false)
        {
            distanceFlag = true;
            return true;
        }

        if (dist > 1000 && distanceFlag == true) {
            distanceFlag = false;
            return false;
        }

        return false;
    }


    private void sendAlarmMessage(String message)
    {
        System.out.println("Producing message to Alarm topic ESP13_distance_alarm");
        kafkaTemplate.send("ESP13_distance_alarm", message);
    }
}
