FROM python:3.8

ADD data.py .
ADD node_data.csv /etc

RUN pip install kafka-python

CMD ["python3", "./data.py"]
