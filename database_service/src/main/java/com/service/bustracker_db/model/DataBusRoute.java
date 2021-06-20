package com.service.bustracker_db.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class DataBusRoute
{
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String nodeId;

    private int locationId;

    private double head;

    private String lon;

    private String lat;

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

    @Override
    public String toString() {
        return "DataBusInfo{" +
                "id=" + id +
                ", nodeId='" + nodeId + '\'' +
                ", locationId=" + locationId +
                ", head=" + head +
                ", lon='" + lon + '\'' +
                ", lat='" + lat + '\'' +
                '}';
    }
}
