Feature: distanceTest

    Scenario: Checking if a certain location ("41.18038089795863" and "-8.622097932210131") is close to the desired one
        Given Location ("41.18038089795863" and "-8.622097932210131")
        When Location is less than 3000
        Then Alarm is produced 

        
    