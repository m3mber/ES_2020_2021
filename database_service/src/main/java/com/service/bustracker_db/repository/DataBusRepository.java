package com.service.bustracker_db.repository;

import com.service.bustracker_db.model.DataBusInfo;
import com.service.bustracker_db.model.DataBusNodeId;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface DataBusRepository extends CrudRepository<DataBusInfo, Integer>
{
    List<DataBusInfo> findAll();

    List<DataBusInfo> findDataBusInfoById(Integer id);

    List<DataBusInfo> findDataBusInfoByLocationId(String id);

    List<DataBusInfo> findDataBusInfoByNodeId(String nodeId);

}
