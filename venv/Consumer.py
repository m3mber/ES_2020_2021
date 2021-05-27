from kafka import KafkaConsumer
from json import loads
import json


# This is only for test purposes
def consume():
    consumer = KafkaConsumer('ESP13_bus_data', bootstrap_servers='192.168.160.18:19092')

    for message in consumer:
        print(message.value)


if __name__ == "__main__":
    print("Starting consuming...")
    consume()
