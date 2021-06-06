package com.service.bustracker.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LocationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocationController.class);



    @GetMapping("/current-location")
    @CrossOrigin
    public String setAlarmParams(@RequestParam String id, @RequestParam String latLong) {
        try {
            System.out.println(latLong);
        } catch (Error e) {
            LOGGER.error("Error getting bus data ");
            return null;
        }

        return latLong;
    }

}