package com.example.demo.rabbitmq;

import com.example.demo.dto.AssignmentDto;
import com.example.demo.dto.MessageProducerDto;
import com.example.demo.entities.Assignment;
import com.example.demo.service.AssignmentService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CountDownLatch;


@Component
public class Receiver {
    private final AssignmentService assignmentService;
    private final CountDownLatch latch = new CountDownLatch(1);

    public Receiver(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @RabbitListener(queues = "assignment-queue")
    public void receiveMessage(MessageProducerDto messageProducerDto) {
        System.out.println("Received <" + messageProducerDto.toString() + ">");
        //signal that the message has been received
        latch.countDown();
        String action = messageProducerDto.getAction();
        System.out.println("Action <" + action + ">");
        switch (action.toLowerCase()) {
            case "create":
                this.createAssignment(messageProducerDto);
                break;
            case "update":
                this.updateAssignment(messageProducerDto);
                break;
            default:
                break;
        }
    }

    private void updateAssignment(MessageProducerDto messageProducerDto) {
        if (messageProducerDto.getAssignmentId() != 0) {
            try {
                Assignment assignment = assignmentService.get(messageProducerDto.getAssignmentId());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime startTime = messageProducerDto.getStartTime() != null ?
                        LocalDateTime.parse(messageProducerDto.getStartTime(), formatter) :
                        assignment.getStartTime();
                LocalDateTime endTime = messageProducerDto.getEndTime() != null ?
                        LocalDateTime.parse(messageProducerDto.getEndTime(), formatter) :
                        assignment.getEndTime();
                assignmentService.update(
                        messageProducerDto.getAssignmentId(),
                        new AssignmentDto(
                                messageProducerDto.getTechnicianId(),
                                startTime,
                                endTime
                        )
                );
            } catch (Exception e) {
                // Log it
                System.err.println(e.getMessage());
            }
        }
    }

    private void createAssignment(MessageProducerDto messageProducerDto) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            if (messageProducerDto.getStartTime().trim().equals("") || messageProducerDto.getEndTime().trim().equals("")) {
                throw new Exception("Start time and end time should not be empty");
            }
            assignmentService.create(
                    new AssignmentDto(
                            messageProducerDto.getTechnicianId(),
                            LocalDateTime.parse(messageProducerDto.getStartTime(), formatter),
                            LocalDateTime.parse(messageProducerDto.getEndTime(), formatter)
                    )
            );
        } catch (Exception e) {
            // Log it
            System.err.println(e.getMessage());
        }
    }
}
