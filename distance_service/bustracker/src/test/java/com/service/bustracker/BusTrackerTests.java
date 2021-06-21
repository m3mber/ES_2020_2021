package com.service.bustracker;

import com.service.bustracker.KafkaConsumer.KafkaConsumerTest;
import com.service.bustracker.listener.KafkaConsumer;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.messages.internal.com.google.gson.Gson;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
@CucumberOptions(features = "src/test/resources/",
        plugin = {"pretty", "html:target/cucumber/bagbasics"},
        extraGlue = "io.tpd.springbootcucumber.bagcommons")
@RunWith(Cucumber.class)
public class BusTrackerTests extends BustrackerApplicationTests{

    private boolean distAlarmFlag = false;
    private String actualAnswer;

    @Autowired
    private KafkaConsumerTest KafkaConsumerTest;
    @Autowired
    private KafkaConsumer kafkaConsumer;


    
    
    /*
     * Scenario 1 Get alarm when distance from location is less than 3000
     */
    @Given("Received kafka topic with id = \"ESP13_bus_dataTest\"")
    public void receivedKafkaTopic() throws InterruptedException {
        KafkaConsumerTest.getLatch().await(10000, TimeUnit.MILLISECONDS);
        actualAnswer = KafkaConsumerTest.getPayload();
    }

    @When("Distance is less than 3000 ")
    public void distance(String lon, String lat) {
        kafkaConsumer = new KafkaConsumer();
        if(kafkaConsumer.calculateDistance(Double.parseDouble(lat), Double.parseDouble(lon)))
            distAlarmFlag = true;
    }

    @Then("Alarm is produced")
    public void produceAlarm(int id) {
        if(distAlarmFlag)
            System.out.println("Producing message to Alarm topic ESP13_distance_alarm");
    }
}
