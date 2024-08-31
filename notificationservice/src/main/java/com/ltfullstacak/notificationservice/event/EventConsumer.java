package com.ltfullstacak.notificationservice.event;

import com.ltfullstack.commonservice.services.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.RetriableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.DltStrategy;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class EventConsumer {

    @Autowired
    private EmailService emailService;

    @RetryableTopic(
            attempts = "4", // 3 topic retry + 1 topic DLQ
            backoff = @Backoff(delay = 1000,multiplier = 2),
            autoCreateTopics = "true",
            dltStrategy = DltStrategy.FAIL_ON_ERROR,
            include = {RetriableException.class,RuntimeException.class}
    )
    @KafkaListener(topics = "test",containerFactory = "kafkaListenerContainerFactory")
    public void listen(String message){
        log.info("Received message: " +message);
        // processing message
        throw new RuntimeException("Error test");
    }

    @DltHandler
    void processDltMessage(@Payload String messsage){
        log.info("DLT receive message: "+messsage);
    }

    @KafkaListener(topics = "testEmail",containerFactory = "kafkaListenerContainerFactory")
    public void testEmail(String message){
        log.info("Received message: " +message);

        String template = "<div>\n" +
                "    <h1>Welcome, %s!</h1>\n" +
                "    <p>Thank you for joining us. We're excited to have you on board.</p>\n" +
                "    <p>Your username is: <strong>%s</strong></p>\n" +
                "</div>";
        String filledTemplate = String.format(template,"Thanh Do",message);

        emailService.sendEmail(message,"Thanks for buy my course",filledTemplate,true,null);
    }

    @KafkaListener(topics = "emailTemplate",containerFactory = "kafkaListenerContainerFactory")
    public void emailTemplate(String message){
        log.info("Received message: " +message);

        Map<String,Object> placeholders = new HashMap<>();
        placeholders.put("name","Lap trinh FullStack");

        emailService.sendEmailWithTemplate(message,"Welcome to Christmas","emailTemplate.ftl",placeholders,null);
    }
}
