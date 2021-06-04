package com.service.bustracker.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.service.bustracker.model.DataBusInfo;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/** This service will be a Distance Service
 * Service will listen the ESP13_service_alarm and send information to client
 * when the bus is a stop away.
 */
@Service
public class KafkaConsumer
{
    private String msg;
    private List<DataBusInfo> allBus = new ArrayList<DataBusInfo>();
    /** Consume real time data
     * Topic will be changed to ESP13_distance_alarm
     */
    @KafkaListener(topics="ESP13_bus_data", groupId = "group_1")
    public void consume(String message)
    {
        /* Remove characters [ ] from the receiced message */
        //message = message.replaceAll("[\\[\\]\\(\\)]", "");
        System.out.println("{'data_bus' : " + message + "}");
        String s = "{'data_bus' : " + message + "}";
        /* Set the String message to an Object */

        JSONObject root = new JSONObject(s);

        JSONArray dataBusArr = root.getJSONArray("data_bus");

        for (int i = 0; i < dataBusArr.length(); i++)
        {
            /*JSON Data*/
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

            /* GET USER CURRENT LOCATION  FROM REACT */
            System.out.println("\nLON: " + lon + "\nLAT: " + lat);
            /* Populate DataBusInfo */

            /*dataBus.setId(Long.parseLong(id));
            dataBus.setNode_id(node_id);
            dataBus.setLocation_id(Integer.parseInt(location_id));
            dataBus.setHead(Double.parseDouble(head));
            dataBus.setLon(lon);
            dataBus.setLat(lat);

            try {
                dataBus.setSpeed(Integer.parseInt(speed));
            } catch (Exception e) {
                dataBus.setSpeed(Integer.parseInt("0"));
            }
            dataBus.setTs(ts);
            dataBus.setWrite_time(write_time); */

            /* Add populated bus to our collection */

            /* allBus.add(dataBus);
            System.out.println("Adding to dataBus object"); */
        }
    }
}
