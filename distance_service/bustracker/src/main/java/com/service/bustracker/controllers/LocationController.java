package com.service.bustracker.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.annotation.PostConstruct;

import com.service.bustracker.listener.KafkaConsumer;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@RestController
public class LocationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocationController.class);

    private String bus_id;
    private String latLong;

    public void setBusId(String id) {
        this.bus_id = id;
    }

    public void setLatLong(String latLong) {
        this.latLong = latLong;
    }

    public String getId() {
        return this.bus_id;
    }

    public LocationController() {
    }

    @GetMapping("/current-location")
    @CrossOrigin
    public String setAlarmParams(@RequestParam String id, @RequestParam String latLong) {
        try {
            setBusId(id);
            setLatLong(latLong);
            System.out.println(id + " " + latLong);
        } catch (Error e) {
            LOGGER.error("Error getting bus data ");
            return null;
        }

        return latLong;
    }

}