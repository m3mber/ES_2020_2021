package com.realtimeservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@RestController
public class SSEController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SSEController.class);
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    private static String lat;
    private static String lon;
    private static String bus_id;
    private static String alarm = null;

    /* Static bus id */
    /* todo: Dynamic bus id button */
    private String static_bus_id = "00000000-0000-0000-0000-000000002481";

    /*
     * __________________________________________________________________________________________
     */
    public SSEController() {
    }

    public void setBusId(String id) {
        this.bus_id = id;
    }

    public void setLatitude(String lat) {
        this.lat = lat;
    }

    public void setLongitude(String lon) {
        this.lon = lon;
    }

    public void setAlarm(String alarm) {
        this.alarm = alarm;
    }

    /*
     * __________________________________________________________________________________________
     */
    @PostConstruct
    public void init() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            executor.shutdown();
            try {
                executor.awaitTermination(1, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                LOGGER.error(e.toString());
            }
        }));
    }

    @GetMapping("/location")
    @CrossOrigin
    public SseEmitter streamRealTimeLocation(@RequestParam String id) {

        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);

        sseEmitter.onCompletion(() -> LOGGER.info("SseEmitter is completed"));

        sseEmitter.onTimeout(() -> LOGGER.info("SseEmitter is timed out"));

        sseEmitter.onError((ex) -> {
            LOGGER.info("SseEmitter got error:", ex);
            sseEmitter.complete();
        });
        executor.execute(() -> {
            while (true) {

                try {
                    if (this.lat != null && this.lon != null && this.bus_id.equals(static_bus_id)) {
                        sseEmitter.send("Bus id: " + this.bus_id + " Lat: " + this.lat + " Lon: " + this.lon);
                    }

                    sleep(1, sseEmitter);

                } catch (IOException e) {
                    e.printStackTrace();
                    sseEmitter.completeWithError(e);
                }
            }
        });

        LOGGER.info("Controller exits");
        return sseEmitter;
    }

    @GetMapping("/alarm")
    @CrossOrigin
    public SseEmitter pushAlarm() {

        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);

        sseEmitter.onCompletion(() -> LOGGER.info("SseEmitter is completed"));

        sseEmitter.onTimeout(() -> LOGGER.info("SseEmitter is timed out"));

        sseEmitter.onError((ex) -> {
            LOGGER.info("SseEmitter got error:", ex);
            sseEmitter.complete();

        });
        executor.execute(() -> {
            while (true) {
                try {
                    System.out.println(">>> " + this.alarm);
                    if (this.alarm != null) {
                        System.out.println(">>> Sending alarm");
                        sseEmitter.send("Alarm: " + this.alarm);
                        break;
                    }
                    // sleep(1, sseEmitter);

                } catch (IOException e) {
                    e.printStackTrace();
                    sseEmitter.completeWithError(e);
                }
            }
            sseEmitter.complete();

        });

        LOGGER.info("Controller exits");
        return sseEmitter;
    }

    private void sleep(int seconds, SseEmitter sseEmitter) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            sseEmitter.completeWithError(e);
        }
    }
}