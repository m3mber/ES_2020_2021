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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
@CucumberOptions(features = "src/test/resources/",
        plugin = {"pretty", "html:target/cucumber/bagbasics"},
        extraGlue = "io.tpd.springbootcucumber.bagcommons")
@RunWith(Cucumber.class)
public class DatabaseStepsTest extends DatabaseServiceApplicationTests {

    private DataBusInfo dataBusInfo;

    private String longTest;
    private String latTest;

    private DataBusRepository dataBusRepository;
    private String actualAnswer;
    private int busIDTest;
    private String auxBusID;
    private List<String> latLongValues = new ArrayList<>();

    @Autowired
    private DataBusService dataBusService;
    @Autowired
    private KafkaConsumer KafkaConsumer;
    @Autowired
    private DataBusController dataBusController;

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
    @Given("Received kafka topic with id = \"ESP13_bus_dataTest\"")
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
     * Scenario 2: The bus data has no latitude or longitude
     */

    @Given("Received kafka topic with id = \"ESP13_bus_dataTest\"")
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

    @Then("Bus with ID = {int} should not be added")
    public void cannotBeInserted(int id) {
        List<DataBusInfo> list = dataBusRepository.findDataBusInfoById(busIDTest);
        busIDTest = id;
        assertTrue(list.size() == 0);

    }
    /*
        Scenario 3: Getting Latitude and longitude values from DB
     */
    @Given("Trying to get longitude {string} and latitude {string} from bus ID = {int}")
    public void receiveBusID(int id){
        busIDTest=id;
    }
    @When("Searching for values in database")
    public List<String> searchDBValues(){
        dataBusController = new DataBusController();
        auxBusID = Integer.toString(busIDTest);
        List<String> latLongValues = dataBusController.getLatitudeAndLongitude(auxBusID);
        return latLongValues;
    }
    @Then("It should return list with values that are expected (Lon = {string} lat = {string}")
    public void checkIfReceived(String lon, String lat){
        longTest = lon;
        latTest = lat;
        assertFalse(latLongValues.isEmpty());
        String aux1 = latLongValues.get(0);
        String aux2 = latLongValues.get(1);
        assertEquals(longTest,aux1 );
        assertEquals(latTest,aux2 );
    }

}
