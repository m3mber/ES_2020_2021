from kafka import KafkaProducer
import csv
import json
import time


# Producer
producer = KafkaProducer(bootstrap_servers=["192.168.160.18:19092"])


def produce_message(data):
    try:
        producer.send("ESP13_bus_data", json.dumps(data["bus_data"]).encode('utf-8'))
    except:
        print("STATUS: An exception occurred while producing data do Kafka")


def convert_csv_json(file_name):
    with open(file_name, "r") as file:
        reader = csv.reader(file)
        next(reader)

        data = {"bus_data": []}

        for row in reader:
            time.sleep(1.5)
            data["bus_data"].append(
                {"id": row[0], "node_id": row[1], "location_id": row[2], "head": row[3], "lon": row[4], "lat": row[5],
                 "speed": row[6], "ts": row[7], "write_time": row[8]})

            # Send data to kafka
            produce_message(data)
            # Clean the data
            data = {"bus_data": []}


if __name__ == "__main__":
    print("Converting CSV to JSON and send it...")
    print("Sending data...")
    convert_csv_json("node_data.csv")
   
