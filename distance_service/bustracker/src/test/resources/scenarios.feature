Feature: distanceTest

    Scenario: Get alarm when distance from location is less than 3000
        Given Received kafka topic with id = "ESP13_bus_distTest"
        When Distance is less than 3000
        Then Alarm is produced

    