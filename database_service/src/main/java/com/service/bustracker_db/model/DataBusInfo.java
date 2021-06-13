package com.service.bustracker_db.model;

import javax.persistence.*;

@Entity
public class DataBusInfo
{
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private String nodeId;

    private int locationId;

    private double head;

    private String lon;

    private String lat;

    private int speed;

    private String ts;

    private String writeTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String node_id) {
        this.nodeId = node_id;
    }

    public int getLocation_id() {
        return locationId;
    }

    public void setLocationId(int location_id) {
        this.locationId = location_id;
    }

    public double getHead() {
        return this.head;
    }

    public void setHead(double head) {
        this.head = head;
    }

    public String getLon() {
        return this.lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public String getWrite_time() {
        return writeTime;
    }

    public void setWriteTime(String write_time) {
        this.writeTime = write_time;
    }

    @Override
    public String toString() {
        return "DataBusInfo{" +
                "id=" + id +
                ", nodeId='" + nodeId + '\'' +
                ", locationId=" + locationId +
                ", head=" + head +
                ", lon='" + lon + '\'' +
                ", lat='" + lat + '\'' +
                ", speed=" + speed +
                ", ts='" + ts + '\'' +
                ", writeTime='" + writeTime + '\'' +
                '}';
    }
}
