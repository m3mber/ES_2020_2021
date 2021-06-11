package com.service.bustracker_db.controller;

import com.service.bustracker_db.model.DataBusInfo;
import com.service.bustracker_db.repository.DataBusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

@Controller
public class DataBusController
{

    @Autowired
    private DataBusRepository dataBusRepository;

    public void addBusData(Integer id, String node_id, int location_id, double head, String lon, String lat, int speed,
                             String ts, String write_time )
    {
        DataBusInfo dataBusInfo = new DataBusInfo();

        dataBusInfo.setId(id);
        dataBusInfo.setNode_id(node_id);
        dataBusInfo.setLocation_id(location_id);
        dataBusInfo.setHead(head);
        dataBusInfo.setLon(lon);
        dataBusInfo.setLat(lat);
        dataBusInfo.setSpeed(speed);
        dataBusInfo.setTs(ts);
        dataBusInfo.setWrite_time(write_time);

        System.out.println("Save into db");
        dataBusRepository.save(dataBusInfo);
    }
}
