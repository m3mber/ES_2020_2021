package com.service.bustracker_db.repository;

import com.service.bustracker_db.model.DataBusNodeId;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DataBusNodeIdRepository extends CrudRepository<DataBusNodeId, String>
{
    List<DataBusNodeId> findAll();
}
