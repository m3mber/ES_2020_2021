package com.service.bustracker;

// import com.service.bustracker.KafkaConsumer.KafkaConsumerTest;
// import com.service.bustracker.listener.KafkaConsumer;

// import io.cucumber.java.en.Given;
// import io.cucumber.java.en.Then;
// import io.cucumber.java.en.When;
// import io.cucumber.junit.Cucumber;
// import io.cucumber.junit.CucumberOptions;
// import io.cucumber.messages.internal.com.google.gson.Gson;

// import org.json.JSONException;
// import org.junit.runner.RunWith;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Value;

// import java.util.ArrayList;
// import java.util.List;
// import java.util.concurrent.TimeUnit;

// import static org.junit.jupiter.api.Assertions.*;

// @CucumberOptions(features = "bustracker/src/test/resources/",
//         plugin = {"pretty", "html:target/cucumber/bagbasics"},
//         extraGlue = "io.tpd.springbootcucumber.bagcommons")
// @RunWith(Cucumber.class)
// public class BusTrackerTests extends BustrackerApplicationTests{
//     private Boolean flag;
//     private String latRec, lonRec;

//     @Autowired
//     private KafkaConsumerTest KafkaConsumerTest;
//     @Autowired
//     private KafkaConsumer kafkaConsumer;


    
    
//     /*
//      * Scenario: Checking if a certain location ("41.18038089795863" and "-8.622097932210131") is close to the desired one
//      */
//     //@Given("Received kafka topic with id = \"ESP13_bus_distTest\"")
//     @Given("Location (lat {string} and lon {string})")
//     public void receivedKafkaTopic(String lat, String lon) {
//         latRec = lat;
//         lonRec = lon;
//     }

//     @When("Location is less than 3000")
//     public void distance() {
//         kafkaConsumer = new KafkaConsumer();
//         flag = kafkaConsumer.calculateDistance(Double.parseDouble(latRec), Double.parseDouble(lonRec));
//     }

//     @Then("Alarm is produced")
//     public void produceAlarm() {
//         if(flag)
//             System.out.println("Producing message to Alarm topic ESP13_distance_alarm");
//     }

// }
