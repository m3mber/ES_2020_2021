package com.bus.busTracker.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "buses")
public class busModel {

    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "node_id")
    private String node_id;

    @Column(name = "location_id")
    private int location_id;

    @Column(name = "head")
    private float head;

    @Column(name = "longitude")
    private float longitude;

    @Column(name = "latitude")
    private float latitude;

    @Column(name = "speed")
    private float speed;

    @Column(name = "timestamp")
    private String timestamp;

    @Column(name = "writeTime")
    private String write_time;


    public busModel() {

    }

    public busModel(long id, String node_id, int location_id, float head, float longitude, float latitude, float speed, String timestamp, String write_time) {
        this.id = id;
        this.node_id = node_id;
        this.location_id = location_id;
        this.head = head;
        this.longitude = longitude;
        this.latitude = latitude;
        this.speed = speed;
        this.timestamp =timestamp;
        this.write_time = write_time;
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

    public int getLocation_id() {
        return location_id;
    }

    public void getLocation_id(int location_id) {
        this.location_id = location_id;
    }

    public float isHead() {
        return head;
    }

    public void setHead(float head) {
        this.head = head;
    }
    public float isLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
    public float isLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }
    public float isSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public String isTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    public String isWrite_time() {
        return write_time;
    }

    public void setWrite_time(String write_time) {
        this.write_time = write_time;
    }

    @Override
    public String toString() {
        return "Bus [id=" + id + ", node_id=" + node_id + ", location_id=" + location_id + ", head=" + head  + ", longitude=" + longitude
                + ", latitude=" + latitude + ", speed=" + speed + ", timestamp=" + timestamp + ", write_time=" + write_time +"]";
    }
}