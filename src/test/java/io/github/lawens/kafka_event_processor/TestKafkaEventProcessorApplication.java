package io.github.lawens.kafka_event_processor;

import org.springframework.boot.SpringApplication;

public class TestKafkaEventProcessorApplication {

	public static void main(String[] args) {
		SpringApplication.from(KafkaEventProcessorApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
