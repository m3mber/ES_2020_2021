package db_service.database_service;

import java.util.List;

import org.springframework.data.repository.CrudRepository;


public interface DataBusRepository extends CrudRepository<DataBusInfo, Long> {
    List<DataBusInfo> findAll();
    // List<CityValue> findCity(String name);
    // CityValue findById(long id);
}
