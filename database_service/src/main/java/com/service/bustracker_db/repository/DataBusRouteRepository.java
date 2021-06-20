package com.service.bustracker_db.repository;

import com.service.bustracker_db.model.DataBusInfo;
import com.service.bustracker_db.model.DataBusRoute;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DataBusRouteRepository extends CrudRepository<DataBusRoute, Integer>
{
    List<DataBusRoute> findDataBusRouteByNodeId(String busId);
}
