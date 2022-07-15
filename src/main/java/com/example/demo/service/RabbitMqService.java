package com.example.demo.service;

import com.example.demo.dto.MessageProducerDto;
import com.example.demo.rabbitmq.Sender;
import org.springframework.stereotype.Service;

@Service
public class RabbitMqService {

    private final Sender sender;

    public RabbitMqService(Sender sender) {
        this.sender = sender;
    }

    public void send(MessageProducerDto messageProducerDto) {
        sender.send(messageProducerDto);
    }
}
