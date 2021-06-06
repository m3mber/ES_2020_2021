package com.realtimeservice;


import org.apache.kafka.clients.producer.Producer;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class Consumer {
    /*public DataBusInfo dataBusInfo = new DataBusInfo(24582903, "00000000-0000-0000-0000-000000002518", 225, 24.53,
            "-8.610583", "41.14898", 12, "2018-10-08 00:00:00.001", "2018-10-08 00:00:01.638819");*/


    private final Logger logger = LoggerFactory.getLogger(Producer.class);

    private static List<DataBusInfo> allBus = new ArrayList<DataBusInfo>();

    public DataBusInfo dataBusInfo = new DataBusInfo();

    public SSEController sseController = new SSEController();
    public void Consumer() {}

    @KafkaListener(topics = "ESP13_bus_data", groupId = "group_id")
    public void consume(String message) throws IOException {

        /* JSON Format */
        String json_message = "{'data_bus' : " + message + "}";

        /* Set the string message to object */
        JSONObject root = new JSONObject(json_message);

        /* Getting JSON array */
        JSONArray dataBusArr = root.getJSONArray("data_bus");

        for (int i = 0; i < dataBusArr.length(); i++)
        {
            JSONObject jsonDataBus = dataBusArr.getJSONObject(i);

            String lon = jsonDataBus.getString("lon");
            String lat = jsonDataBus.getString("lat");
            String node_id = jsonDataBus.getString("node_id");

            logger.info(String.format("#### -> Sending latitude and longitude for bus -> %s", node_id));
            sseController.setLatitude(lat);
            sseController.setLongitude(lon);
            sseController.setBusId(node_id);

            /*___________________________________________________________________________*/
            /*String location_id = jsonDataBus.getString("location_id");
            String id = jsonDataBus.getString("id");
            String head = jsonDataBus.getString("head");
            String speed = jsonDataBus.getString("speed");
            String ts = jsonDataBus.getString("ts");
            String write_time = jsonDataBus.getString("write_time"); */

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
            //this.allBus.add(dataBus);


        }
        logger.info(String.format("#### -> Consumed message -> %s", message));
    }

    public List<DataBusInfo> getDataBusList()
    {
        return this.allBus;
    }

}