package com.bus.busTracker.csv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.bus.busTracker.model.busModel;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

public class CSVHelper {
    public static String TYPE = "text/csv";
    static String[] HEADERs = { "Id", "node_id", "head", "longitude","latitude","speed","timestamp","write_time" };

    public static boolean hasCSVFormat(MultipartFile file) {

        if (!TYPE.equals(file.getContentType())) {
            return false;
        }

        return true;
    }

    public static List<busModel> csvToBus(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

            List<busModel> tutorials = new ArrayList<busModel>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                busModel bus = new busModel(
                        Long.parseLong(csvRecord.get("Id")),
                        csvRecord.get("node_id"),
                        Integer.parseInt(csvRecord.get("location_id")),
                        Float.parseFloat(csvRecord.get("head")),
                        Float.parseFloat(csvRecord.get("longitude")),
                        Float.parseFloat(csvRecord.get("latitude")),
                        Float.parseFloat(csvRecord.get("speed")),
                        csvRecord.get("timestamp"),
                        csvRecord.get("write_time")
                );

                tutorials.add(bus);
            }

            return tutorials;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

}