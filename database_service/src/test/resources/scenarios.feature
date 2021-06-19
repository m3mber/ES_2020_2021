Feature: Bus

    Scenario: Bus with id = 24586269 is inserted to table
        Given Received kafka topic with id = "ESP13_bus_dataTest"
        When Bus with info id 24583598, node_id "00000000-0000-0000-0000-000000002518", location_id 225, head 306.71, lon "-8.623562", lat "41.15869" , speed 46, ts "2018-10-08 00:09:00.001", write_time "write_time": "2018-10-08 00:09:00.994401"
        Then Data from busID = 24583598 should be on table

    Scenario: The bus data has no latitude or longitude
        Given Received kafka topic with id = "ESP13_bus_dataTest"
        When Trying to add Bus with info id 24583599, node_id "00000000-0000-0000-0000-000000002518", location_id 225, head 306.71, lon "", lat "41.15869" , speed 46, ts "2018-10-08 00:09:00.001", write_time "write_time": "2018-10-08 00:09:00.994401"
        Then Bus with ID = 24586269 should not be added

    Scenario: Getting Latitude and longitude values from DB
        Given Trying to get longitude and latitude from bus ID = 24583598
        When Searching for values in database
        Then It should return list with values
