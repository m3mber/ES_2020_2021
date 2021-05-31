package com.bus.busTracker.service;

import java.io.IOException;
import java.util.List;

import com.bus.busTracker.csv.CSVHelper;
import com.bus.busTracker.model.busModel;
import com.bus.busTracker.repository.BusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class CSVService {
    @Autowired
    BusRepository repository;

    public void save(MultipartFile file) {
        try {
            List<busModel> bus = CSVHelper.csvToBus(file.getInputStream());
            repository.saveAll(bus);
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }

    public List<busModel> getAllBus() {
        return repository.findAll();
    }
}