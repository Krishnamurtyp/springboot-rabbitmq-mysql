package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(value = {"id"}, allowGetters = true)
public class AssignmentDto {
    private int id;
    private int technicianId;
    @NotNull(message = "Start time of assignment cannot be empty")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;
    @NotNull(message = "End time of assignment cannot be empty")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    public AssignmentDto(int technicianId, LocalDateTime startTime, LocalDateTime endTime) throws Exception {
        this.technicianId = technicianId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.validate();
    }

    private void validate() throws Exception {
        if (this.technicianId == 0) {
            throw new Exception(String.format("Assignment must be assigned to one technician. [Technician Id: %d is invalid] ", this.technicianId));
        }
        if (this.startTime == null || this.endTime == null) {
            throw new Exception("Assignment must have start and end time");
        }
    }

    public void setId(int id) {
        this.id = id;
    }
}
