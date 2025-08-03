package io.github.lawens.kafka_event_processor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/kafka")
@Slf4j
public class KafkaTestController {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @PostMapping("/publish")
    public String publishMessage(@RequestBody String message) {
        try {
            CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send("test-topic", message);
            
            future.whenComplete((result, ex) -> {
                if (ex == null) {
                    log.info("Message sent successfully: {}", message);
                } else {
                    log.error("Failed to send message: {}", ex.getMessage());
                }
            });
            
            return "Message sent: " + message;
        } catch (Exception e) {
            log.error("Error sending message: {}", e.getMessage());
            return "Error sending message: " + e.getMessage();
        }
    }

    @GetMapping("/health")
    public String health() {
        return "Kafka Test Controller is running!";
    }
} 