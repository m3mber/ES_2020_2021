package db_service.database_service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BusController {
    @Autowired
	private DataBusRepository dataBusRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(BusController.class);
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
    public BusController() {
    }

    public static void setBusId(String id) {
        bus_id = id;
    }

    public static void setLatitude(String lat) {
        lat = lat;
    }

    public static void setLongitude(String lon) {
        lon = lon;
    }

    public void setAlarm(String alarm)
    {
        this.alarm = alarm;
    }

}
