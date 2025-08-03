package io.github.lawens.kafka_event_processor.config;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KafkaListners {
  @KafkaListener(topics = "test-topic", groupId = "${spring.kafka.consumer.group-id}")
  public void consume(ConsumerRecord<String, String> record, @Header(KafkaHeaders.OFFSET) Long offset, Acknowledgment ack, Consumer<?, ?> consumer) {
    try {
      log.info("Consumer Data = {}, Offset = {}, Header Offset = {}, Partition = {}", 
               record.value(), offset, record.offset(), record.partition());
      
      // 메시지 처리 로직
      System.out.println("Received message: " + record.value());
      System.out.println("Offset: " + offset);
      System.out.println("Partition: " + record.partition());
      System.out.println("Topic: " + record.topic());
      
      // 비동기 커밋
      consumer.commitAsync();
      
    } catch(Exception e) {
      log.error("Consumer Error = {}", e.getMessage(), e);
    } finally {
      // ACK 확인
      ack.acknowledge();
    }
  }
}
