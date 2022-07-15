package com.example.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageProducerDto implements Serializable {
    @NotEmpty(message = "producer action cannot be empty")
    @Schema(type = "string", allowableValues = { "create", "update" })
    private String action = "create";
    private int assignmentId = 0;
    private String endTime;
    private String startTime;
    private int technicianId;
}
