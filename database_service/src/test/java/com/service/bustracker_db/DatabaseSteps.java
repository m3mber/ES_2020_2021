package com.service.bustracker_db;

import com.service.bustracker_db.KafkaConsumer.KafkaConsumer;
import com.service.bustracker_db.controller.DataBusController;
import com.service.bustracker_db.model.DataBusInfo;
import com.service.bustracker_db.repository.DataBusRepository;
import com.service.bustracker_db.service.DataBusService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.messages.internal.com.google.gson.Gson;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
@CucumberOptions(features = "src/test/resources/",plugin = {"pretty"}, publish = true)
@RunWith(Cucumber.class)
public class DatabaseSteps extends DatabaseServiceApplicationTests {

    @Autowired
    private DataBusInfo dataBusInfo;
    private String longTest;
    private String latTest;
    @Autowired
    private DataBusRepository dataBusRepository;
    private String actualAnswer;
    private int busIDTest;

    @Autowired
    private DataBusService dataBusService;
    @Autowired
    private KafkaConsumer KafkaConsumer;


    /*
        @Value("${test.topic}")
        private String topic;

        DataBusInfo dataBusInfo = new DataBusInfo();
        private String actualAnswer;
        @Given("Received the correct topicID = {string}")
        public void dbInsert(){
            String msg = new Gson().toJson("");
            KafkaProducer.sendMessage(topic, msg);
        }

        @When("Kafka topic \"events\" test1")
        public void test1() throws InterruptedException{
            KafkaConsumer.getLatch().await(10000, TimeUnit.MILLISECONDS);
            actualAnswer = KafkaConsumer.getPayload();
        }

        @Then("Received message should be {string}")
        public void answerTest1(String expectedAnswer){

            assertEquals(expectedAnswer, actualAnswer);
        }

    /*
     * Scenario 1 Bus insert with id = should exist in table
     */
    @Given("Received kafka topic with id = \"ESP13_bus_data\"")
    public void receivedKafkaTopic() throws InterruptedException {
        KafkaConsumer.getLatch().await(10000, TimeUnit.MILLISECONDS);
        actualAnswer = KafkaConsumer.getPayload();
    }

    @When("Bus with info id = {int},{int} node_id {string}, location_id {int}, head {double}, lon {string}, lat {string} , speed {double}, ts {string}, write_time {string} ")
    public void insertToTable(int id, String node_id, int location_id, double head, String lon, String lat, int speed, String ts, String write_time) {
        dataBusService = new DataBusService();
        dataBusService.addBusData(id, node_id, location_id, head, lon, lat, speed, ts, write_time);
    }

    @Then("Data from busID = {int} should be on table")
    public void verifyInTable(int id) {
        busIDTest = id;
        List<DataBusInfo> list = (List<DataBusInfo>) dataBusRepository.findDataBusInfoById(busIDTest);
        assertTrue(list.size() != 0);
    }
    /*
     * The bus data has no latitude or longitude
     */
    @Given("Bus with id {int} should exist in table")
    public void receivedKafkaTopic2(int busID) throws InterruptedException {
        KafkaConsumer.getLatch().await(10000, TimeUnit.MILLISECONDS);
        actualAnswer = KafkaConsumer.getPayload();
    }

    @When("Trying to add Bus with info id = {int},{int} node_id {string}, location_id {int}, head {double}, lon {string}, lat {string} , speed {double}, ts {string}, write_time {string} ")
    public void checkIfLongLatPresent(int id, String node_id, int location_id, double head, String lon, String lat, double speed, String ts, String write_time) {
        if(lat.isEmpty() || lon.isEmpty() ){
            System.out.println("Bus trying to get inserted has no latitude, longitude");
        }

    }

    @Then("It should tell me \"Bus is on table\"")
    public void cannotBeInserted(int id) {
        List<DataBusInfo> list = (List<DataBusInfo>) dataBusRepository.findDataBusInfoById(busIDTest);
        busIDTest = id;
        assertFalse(list.size() != 0);

    }

}
