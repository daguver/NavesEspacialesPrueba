package com.prueba.navesespaciales.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@Slf4j
@RabbitListener
public class EventListener {

    @RabbitListener(queues = "your-queue-name")
    public void receiveMessage(String message) {
        log.atInfo().log("Event received: {}",message);
    }
}
