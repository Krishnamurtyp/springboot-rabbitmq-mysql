package com.example.demo.rabbitmq;

import com.example.demo.dto.MessageProducerDto;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class Sender {
    private final AmqpTemplate rabbitTemplate;

    @Value("${demo.rabbitmq.exchange}")
    private String exchange;

    @Value("${demo.rabbitmq.routingkey}")
    private String routingkey;

    public Sender(AmqpTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send(MessageProducerDto messageProducerDto) {
        rabbitTemplate.convertAndSend(exchange, routingkey, messageProducerDto);
        System.out.println("Send msg = " + messageProducerDto);
    }
}
