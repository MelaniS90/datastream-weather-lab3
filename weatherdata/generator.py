import json
import random
import time
from datetime import datetime
from kafka import KafkaProducer


def generate_temperature(termometerId):
    temperature = round(random.uniform(-180, 180), 6)  
    measureTime = time.time()
    return {
        "termometerId": termometerId,
        "measureTime": measureTime,
        "temperature": temperature
    }

def simulate_temperatures(num_temperature):
    temperatures = []
     # Each card maps to one of 3000 users
    for i in range(num_temperature):
        temperature = generate_temperature(i)
        temperatures.append(temperature)
    return temperatures

def serializer(message):
    return json.dumps(message).encode('utf-8')

# Kafka Producer
producer = KafkaProducer(
    bootstrap_servers=['localhost:9092'],
    value_serializer=serializer
)

if __name__ == '__main__':
    num_temperatures= 10000
    temperatures = simulate_temperatures(num_temperatures)

    # Infinite loop - runs until you kill the program
    for temperature in temperatures:
        # Send it to our 'transactions' topic
        print(f'Producing temperatures @ {datetime.now()} | Temperature = {temperature}')
        producer.send('Temperatura', temperature)
        # Sleep for a random number of seconds
        time_to_sleep = random.randint(1, 11)
        time.sleep(time_to_sleep)

    print("temperatures generated and sent to Kafka topic 'Temperatura'")
