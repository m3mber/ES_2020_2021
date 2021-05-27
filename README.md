# ES_2020_2021

# Running the Data Simulator
Step 1: To run data simulator its required that you have docker installed on your local machine. Dockerfile is provided in the venv directory

Step2: Important: Csv file its to large to push into git repository so you have to download it from elearning, paste it on data_simulator/venv directory and change its name to " node_data.csv "

Step3: Build the docker image

Step4: Run the docker image " docker run -t [image id] "

If everthing works fine you should see: " Producing message " on console.

To consume message you need to connect to the Runtime VM and consume from topic "ESP13_bus_data"
