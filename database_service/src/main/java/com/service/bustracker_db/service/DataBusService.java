package com.service.bustracker_db.service;

import com.service.bustracker_db.model.DataBusInfo;
import com.service.bustracker_db.model.DataBusNodeId;
import com.service.bustracker_db.model.DataBusRoute;
import com.service.bustracker_db.repository.DataBusNodeIdRepository;
import com.service.bustracker_db.repository.DataBusRepository;
import com.service.bustracker_db.repository.DataBusRouteRepository;
import org.apache.kafka.clients.producer.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class DataBusService
{
    @Autowired
    private DataBusRepository dataBusRepository;


    @Autowired
    private DataBusNodeIdRepository dataBusNodeIdRepository;

    @Autowired
    private DataBusRouteRepository dataBusRouteRepository;


    private List<DataBusInfo> dataBusInfoList;

    private List<DataBusNodeId> dataBusNodeIds;

    private List<DataBusRoute> dataBusRoutes;

    private final Logger logger = LoggerFactory.getLogger(Producer.class);

    public void addBusData(Integer id, String node_id, int location_id, double head, String lon, String lat, int speed,
                           String ts, String write_time )
    {
        DataBusInfo dataBusInfo = new DataBusInfo();

        dataBusInfo.setId(id);
        dataBusInfo.setNodeId(node_id);
        dataBusInfo.setLocationId(location_id);
        dataBusInfo.setHead(head);
        dataBusInfo.setLon(lon);
        dataBusInfo.setLat(lat);
        dataBusInfo.setSpeed(speed);
        dataBusInfo.setTs(ts);
        dataBusInfo.setWriteTime(write_time);

        /* Used to save on database, comment if you dont want to save */

        //dataBusRepository.save(dataBusInfo);
        logger.info(String.format("#### -> Bus data saved on database"));

    }

    public List<String> getLatitudeAndLongitude(String nodeId)
    {
        dataBusInfoList = dataBusRepository.findDataBusInfoByNodeId(nodeId);
        List<String> dataBusLatAndLon = new ArrayList<>();

        for (DataBusInfo d : dataBusInfoList)
        {
            dataBusLatAndLon.add(d.getLat());
            dataBusLatAndLon.add(d.getLon());
        }
        logger.info(String.format("#### -> Getting latitude and longitude"));
        return dataBusLatAndLon;
    }

    public List<String> getAllBusIds()
    {
        dataBusNodeIds = dataBusNodeIdRepository.findAll();
        List<String> busIds = new ArrayList<>();

        for (DataBusNodeId d : dataBusNodeIds)
        {
            busIds.add(d.getNodeId());
        }
        logger.info(String.format("#### -> Getting all bus ids"));
        return busIds;
    }

    public List<String> getRoutes(String nodeId)
    {
        dataBusRoutes = dataBusRouteRepository.findDataBusRouteByNodeId(nodeId);
        List<String> busRoute = new ArrayList<>();

        for (DataBusRoute d : dataBusRoutes)
        {
            busRoute.add(d.getLat());
            busRoute.add(d.getLon());
        }
        logger.info(String.format("#### -> Getting all routes for specific bus id"));
        return busRoute;
    }


    /* Just made to Add routes on database*/
    public void addRoutes(Integer id, String node_id, int location_id, double head, String lon, String lat )
    {
        DataBusRoute busRoute = new DataBusRoute();
        busRoute.setId(id);
        busRoute.setNodeId(node_id);
        busRoute.setLocationId(location_id);
        busRoute.setHead(head);
        busRoute.setLon(lon);
        busRoute.setLat(lat);

        logger.info(String.format("#### -> Saving routes on db..."));
        dataBusRouteRepository.save(busRoute);
    }
}
