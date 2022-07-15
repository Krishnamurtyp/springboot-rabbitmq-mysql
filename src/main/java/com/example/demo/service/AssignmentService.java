package com.example.demo.service;

import com.example.demo.dto.AssignmentDto;
import com.example.demo.entities.Assignment;
import com.example.demo.entities.Technician;
import com.example.demo.mappers.AssignmentMapper;
import com.example.demo.repository.AssignmentRepository;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AssignmentService {
    private final AssignmentRepository assignmentRepository;
    private final TechnicianService technicianService;

    public AssignmentService(AssignmentRepository assignmentRepository, TechnicianService technicianService) {
        this.assignmentRepository = assignmentRepository;
        this.technicianService = technicianService;
    }

    public AssignmentDto create(AssignmentDto assignmentDto) throws Exception {
        Technician technician = technicianService.get(assignmentDto.getTechnicianId());
        Assignment assignedToTechnician = assignmentRepository.findAssignmentByTechnician(technician);
        if(assignedToTechnician != null) {
            throw  new Exception("Technician is already assigned to one assignment");
        }
        Assignment assignment = new Assignment(
                technician,
                assignmentDto.getStartTime(),
                assignmentDto.getEndTime()
        );
        assignment = assignmentRepository.save(assignment);
        return this.getDto(assignment);
    }

    public Assignment get(int assignment_id) throws NotFoundException {
        Optional<Assignment> assignmentOptional = assignmentRepository.findById(assignment_id);
        if (assignmentOptional.isPresent()) {
            return assignmentOptional.get();
        } else {
            throw new NotFoundException(String.format("Assignment with id %d not found", assignment_id));
        }
    }

    public AssignmentDto update(int assignment_id, AssignmentDto assignmentDto) throws Exception {
        Technician technician = technicianService.get(assignmentDto.getTechnicianId());
        Assignment assignment = this.get(assignment_id);
        assignment = assignment.update(assignmentDto, technician);

        assignmentRepository.save(assignment);
        return this.getDto(assignment);
    }

    public AssignmentDto getDto(Assignment assignment) throws Exception {
        AssignmentMapper assignmentMapper = new AssignmentMapper();
        return assignmentMapper.fromAssignment(assignment);
    }
}
