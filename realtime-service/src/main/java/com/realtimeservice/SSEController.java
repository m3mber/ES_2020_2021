package com.realtimeservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private Consumer kafkaConsumer = new Consumer();

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
                    if (kafkaConsumer.dataBusInfo != null && kafkaConsumer.dataBusInfo.getNode_id().equals(id)) {
                        String latLong = kafkaConsumer.dataBusInfo.getLat() + "," + kafkaConsumer.dataBusInfo.getLon();
                        sseEmitter.send(latLong);
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

    private void sleep(int seconds, SseEmitter sseEmitter) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            sseEmitter.completeWithError(e);
        }
    }
}