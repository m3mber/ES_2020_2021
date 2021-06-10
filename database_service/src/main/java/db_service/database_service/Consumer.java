package db_service.database_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.kafka.clients.producer.Producer;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



@Service
public class Consumer {

    private final Logger logger = LoggerFactory.getLogger(Producer.class);

    @Autowired
	private DataBusRepository dataBusRepository;

    public DataBusInfo dataBusInfo = new DataBusInfo();

    public BusController busController = new BusController();

    public void Consumer() {
    }
    
    @KafkaListener(topics = "ESP13_bus_data", groupId = "group_id")
    public void consumeMessage(String message) {
         /* JSON Format */
         String json_message = "{'data_bus' : " + message + "}";

         /* Set the string message to object */
         JSONObject root = new JSONObject(json_message);
 
         /* Getting JSON array */
         JSONArray dataBusArr = root.getJSONArray("data_bus");
 
         for (int i = 0; i < dataBusArr.length(); i++) {
             JSONObject jsonDataBus = dataBusArr.getJSONObject(i);
 
             String lon = jsonDataBus.getString("lon");
             String lat = jsonDataBus.getString("lat");
             String node_id = jsonDataBus.getString("node_id");

             logger.info(String.format("######### -> TESTEEEEE -> %s",lon));

 
             /*
             logger.info(String.format("#### -> Sending latitude and longitude for bus -> %s", node_id));
             BusController.setLatitude(lat);
             BusController.setLongitude(lon);
             BusController.setBusId(node_id);
             */

             addNewBusData(lon, lat, node_id);
             
 
         }
         //logger.info(String.format("#### -> Consumed message -> %s", message));
    }

    /**
	 * Mysql persistence
	 */
     public @ResponseBody String addNewBusData (@RequestParam String lon, @RequestParam String lat, @RequestParam String node_id) {

		DataBusInfo b = new DataBusInfo();
		b.setLongitude(lon);
        b.setLatitude(lat);
        b.setNode_id(node_id);

        logger.info(String.format("####### -> DATABASEEEE -> %s ", dataBusRepository.findAll()));
		
		return "Databus Saved";
	}
    
}
