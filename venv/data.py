#from kafka import KafkaProducer
import csv
import json
import time


# Producer
producer = KafkaProducer(bootstrap_servers=["192.168.160.18:19092"])


def produce_message(data):
    try:
        print("STATUS: Producing message..")
        producer.send("ESP13_bus_data", json.dumps(data["bus_data"]).encode('utf-8'))
    except:
        print("STATUS: An exception occurred while producing data do Kafka")


def convert_csv_json(file_name):
    with open(file_name, "r") as file:
        reader = csv.reader(file)
        next(reader)

        data = {"bus_data": []}

        for row in reader:
            time.sleep(5)
            data["bus_data"].append(
                {"id": row[0], "node_id": row[1], "location_id": row[2], "head": row[3], "lon": row[4], "lat": row[5],
                 "speed": row[6], "ts": row[7], "write_time": row[8]})

            # Send data to kafka
            produce_message(data)
            # Clean the data
            data = {"bus_data": []}


if __name__ == "__main__":
<<<<<<< HEAD
    print("Starting converting CSV to JSON and send it...")
    convert_csv_json("etc/node_data.csv")
=======
    print("Converting CSV to JSON and send it...")
    #convert_csv_json("node_data.csv")
    print("Sending data...")
>>>>>>> bf4e82627d28eb9dde16c2459aaa89157f76c90d
