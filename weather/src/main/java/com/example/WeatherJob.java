/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;


import java.time.LocalDateTime;
import java.util.Properties;
import java.time.Duration;

import org.apache.flink.api.common.functions.*;

/**
 * Skeleton code for the datastream walkthrough
 */
public class WeatherJob {
	static String brokers = "localhost:9092";
    static String groupId = "test-group";
    static String topic = "Temperatura";
	static String topicAlert = "Alarm";
	public static void main(String[] args) throws Exception {
		StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

		Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", brokers);
        properties.setProperty("group.id", groupId);

        FlinkKafkaConsumer<Temperature> consumer = new FlinkKafkaConsumer<>(
			topic,
            new TemperatureDeserializationSchema(),
            properties);

        DataStream<Temperature> temperatureStream = env.addSource(consumer)
            .assignTimestampsAndWatermarks(
                WatermarkStrategy.<Temperature>forBoundedOutOfOrderness(Duration.ofSeconds(5))
                    .withTimestampAssigner((event, timestamp) -> System.currentTimeMillis())
            ).name("temperature-kafka-consumer");
		
		FlinkKafkaProducer<Alert> flinkKafkaProducer = new FlinkKafkaProducer<Alert>(
            brokers,
            topicAlert,
            new AlertSerializationSchema()
        );

		DataStream<Alert> alerts = temperatureStream
			.keyBy(temperature -> temperature.getTemperature())
			.filter(new FilterFunction<Temperature>(){
				@Override
				public boolean filter(Temperature value) throws Exception{
					return value.getTemperature() < 0;
				}

			})
			.map(new MapFunction<Temperature, Alert>(){
				public Alert map(Temperature temperature) throws Exception{
					String timesamp = LocalDateTime.now().toString();
					return new Alert(temperature.getTemperature(), timesamp);
				}
			})
			.name("temperature");

		alerts
			.addSink(flinkKafkaProducer)
			.name("send-alerts");

		env.execute("Weather-kafka-lab");
	}
}
