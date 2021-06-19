package com.service.bustracker_db;

import com.service.bustracker_db.controller.DataBusController;
import com.service.bustracker_db.model.DataBusInfo;
import com.service.bustracker_db.repository.DataBusRepository;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@CucumberContextConfiguration
@ContextConfiguration
@DirtiesContext
class DatabaseServiceApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DataBusRepository dataBusRepository;

    @Autowired
    private DataBusController dataBusController;
    @Test
    void contextLoads() {
        System.out.println("Repository exists");
        assertNotNull(dataBusRepository);
        System.out.println("Controller exists");
        assertNotNull(dataBusController);
    }
    /*
    @Test
    void getStatusDB() throws Exception {
        System.out.println("Checking if DB is up");
        this.mockMvc.perform(get("http://192.168.160.18:3306/es13_db")).andDo(print()).andExpect(status().isOk());

    }
     */
    @Test
    void emptyBusList() throws Exception{
        System.out.println("Check if bus list is empty");
        List<DataBusInfo> list = (List<DataBusInfo>) dataBusRepository.findAll();
        //assert (!list.isEmpty());
        assert (list.size()>0);
    }
}
