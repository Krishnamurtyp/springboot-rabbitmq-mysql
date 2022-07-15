package com.example.demo.entities;


import com.example.demo.dto.AssignmentDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "assignment", indexes = {
        @Index(name = "search_index", columnList = "technician_id")
})
@ToString
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Assignment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "technician_id", referencedColumnName = "id")
    @JsonManagedReference("technician-assignment")
    private Technician technician;
    @Column(name = "start_time", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;
    @Column(name = "end_time", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    public Assignment(Technician technician, LocalDateTime startTime, LocalDateTime endTime) {
        this.technician = technician;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Assignment update(AssignmentDto assignmentDto, Technician technician) {
        this.startTime = assignmentDto.getStartTime();
        this.endTime = assignmentDto.getEndTime();
        this.technician = technician;
        return this;
    }

}
