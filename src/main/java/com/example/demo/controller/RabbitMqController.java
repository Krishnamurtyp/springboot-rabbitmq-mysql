package com.example.demo.controller;

import com.example.demo.dto.MessageProducerDto;
import com.example.demo.service.RabbitMqService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/v1")
@Api(value = "RabbitMqController Controller")
public class RabbitMqController {

    private final RabbitMqService rabbitMqService;

    public RabbitMqController(RabbitMqService rabbitMqService) {
        this.rabbitMqService = rabbitMqService;
    }

    @PostMapping(value = "/produce", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Produce", response = ResponseEntity.class, httpMethod = "POST")
    @ApiResponses({
            @ApiResponse(code = 201, reference = "success", message = "message sent to queue"),
            @ApiResponse(code = 500, reference = "error", message = "Interval Server Error")
    })
    public ResponseEntity<Void> produce(@RequestBody @Valid MessageProducerDto messageProducerDto) {
        rabbitMqService.send(messageProducerDto);
        return ResponseEntity.ok().body(null);
    }
}
