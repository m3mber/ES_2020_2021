package com.service.bustracker_db.service;

import com.service.bustracker_db.model.DataBusInfo;
import com.service.bustracker_db.model.DataBusNodeId;
import com.service.bustracker_db.repository.DataBusNodeIdRepository;
import com.service.bustracker_db.repository.DataBusRepository;
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

    private List<DataBusInfo> dataBusInfoList;

    private List<DataBusNodeId> dataBusNodeIds;


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
        //System.out.println("Added on db");

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

        return busIds;
    }


    /* Just made to Add ids to database*/
/*  public void addIds(List<String> list)
    {
        for (String s : list) {
            DataBusNodeId busIds = new DataBusNodeId();
            busIds.setNodeId(s);
            dataBusNodeIdRepository.save(busIds);
            System.out.println("Saving..." + s);
        }
    } */
}
