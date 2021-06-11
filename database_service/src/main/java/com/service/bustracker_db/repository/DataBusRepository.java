package com.service.bustracker_db.repository;

import com.service.bustracker_db.model.DataBusInfo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface DataBusRepository extends CrudRepository<DataBusInfo, Integer>
{
   // List<DataBusInfo> findAll();
}