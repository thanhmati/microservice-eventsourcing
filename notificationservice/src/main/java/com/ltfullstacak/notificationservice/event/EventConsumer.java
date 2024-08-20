package com.ltfullstacak.notificationservice.event;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.RetriableException;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EventConsumer {

    @RetryableTopic(
            attempts = "4",
            backoff = @Backoff(delay = 1000, multiplier = 2),
            autoCreateTopics = "true",
            include = {RetriableException.class,RuntimeException.class}
    )
    @KafkaListener(topics = "test",containerFactory = "kafkaListenerContainerFactory")
    public void listen(String message){
        log.info("Received message: " +message);

        throw new RuntimeException("Error test");
    }

    @RetryableTopic(
            attempts = "2",
            backoff = @Backoff(delay = 1000, multiplier = 2),
            autoCreateTopics = "true",
            include = {RetriableException.class,RuntimeException.class}
    )
    @KafkaListener(topics = "test-1",containerFactory = "kafkaListenerContainerFactory")
    public void listenTest1(String message){
        log.info("Received message: " +message);

        throw new RuntimeException("Error test 1");
    }

    @DltHandler
    void listenDltMessage(@Payload String message){
        log.info("DLT receive message: "+message);
    }
}
