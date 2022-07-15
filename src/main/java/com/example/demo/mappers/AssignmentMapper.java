package com.example.demo.mappers;

import com.example.demo.dto.AssignmentDto;
import com.example.demo.entities.Assignment;
import org.springframework.stereotype.Component;

@Component
public class AssignmentMapper {

    public AssignmentDto fromAssignment(Assignment assignment) throws Exception {
        AssignmentDto assignmentDto = new AssignmentDto(assignment.getTechnician().getId(), assignment.getStartTime(), assignment.getEndTime());
        assignmentDto.setId(assignment.getId());
        return assignmentDto;
    }
}
