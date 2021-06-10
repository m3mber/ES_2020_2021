package db_service.database_service;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

public class DataBusInfo {
    @org.codehaus.jackson.annotate.JsonProperty("id")
    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "node_id")
    private String node_id;

    @Column(name = "location_id")
    private int location_id;

    @Column(name = "head")
    private double head;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "speed")
    private int speed;

    @Column(name = "timestamp")
    private String timestamp;

    @Column(name = "writeTime")
    private String write_time;


    public DataBusInfo() {

    }

    public DataBusInfo(long id, String node_id, int location_id, double head, String longitude, String latitude, int speed, String timestamp, String write_time) {
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

    public double getHead() {
        return head;
    }

    public void setHead(float head) {
        this.head = head;
    }
    public String isLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    public String getWrite_time() {
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