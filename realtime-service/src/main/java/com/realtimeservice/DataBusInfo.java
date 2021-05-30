package com.realtimeservice;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DataBusInfo {
    @JsonProperty("id")
    private long id;
    private String node_id;
    private int location_id;
    private double head;
    private String lon;
    private String lat;
    private int speed;
    private String ts;
    private String write_time;

    public DataBusInfo(long id, String node_id, int location_id, double head, String lon, String lat, int speed,
            String ts, String write_time) {
        this.id = id;
        this.node_id = node_id;
        this.location_id = location_id;
        this.head = head;
        this.lon = lon;
        this.lat = lat;
        this.speed = speed;
        this.ts = ts;
        this.write_time = write_time;
    }

    public DataBusInfo() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNode_id() {
        return node_id;
    }

    public void setNode_id(String node_id) {
        this.node_id = node_id;
    }

    public long getLocation_id() {
        return location_id;
    }

    public void setLocation_id(int location_id) {
        this.location_id = location_id;
    }

    public double getHead() {
        return head;
    }

    public void setHead(double head) {
        this.head = head;
    }

    public String getLon() {
        return lon;
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
        return write_time;
    }

    public void setWrite_time(String write_time) {
        this.write_time = write_time;
    }
}