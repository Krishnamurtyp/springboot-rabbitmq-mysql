package com.example.demo.service;

import com.example.demo.dto.TechnicianDto;
import com.example.demo.entities.Assignment;
import com.example.demo.entities.Technician;
import com.example.demo.mappers.TechnicianMapper;
import com.example.demo.repository.AssignmentRepository;
import com.example.demo.repository.TechnicianRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TechnicianService {

    private final TechnicianRepository technicianRepository;
    private final AssignmentRepository assignmentRepository;
    public TechnicianService(TechnicianRepository technicianRepository, AssignmentRepository assignmentRepository) {
        this.technicianRepository = technicianRepository;
        this.assignmentRepository = assignmentRepository;
    }

    public TechnicianDto create(TechnicianDto technicianDto) {
        Technician technician = new Technician(
                technicianDto.getFirstName(),
                technicianDto.getLastName(),
                technicianDto.getEmail()
        );
        technician = technicianRepository.save(technician);
        return this.getDto(technician);
    }

    public void delete(int technician_id) throws NotFoundException {
        Optional<Technician> technicianOptional = technicianRepository.findById(technician_id);
        if (technicianOptional.isPresent()) {
            Assignment assignment = technicianOptional.get().getAssignment();
            assignment.setTechnician(null);
            assignmentRepository.save(assignment);
            technicianRepository.delete(technicianOptional.get());
        } else {
            throw new NotFoundException(String.format("Technician with id %d not found", technician_id));
        }
    }

    public Technician get(int technician_id) throws NotFoundException {
        Optional<Technician> technicianOptional = technicianRepository.findById(technician_id);
        if (technicianOptional.isPresent()) {
            return technicianOptional.get();
        } else {
            throw new NotFoundException(String.format("Technician with id %d not found", technician_id));
        }
    }

    public TechnicianDto update(int technician_id, TechnicianDto technicianDto) throws Exception {
        Technician technician = this.get(technician_id);
        technician = technician.update(technicianDto);
        technicianRepository.save(technician);
        return this.getDto(technician);
    }

    public TechnicianDto getDto(Technician technician) {
        TechnicianMapper technicianMapper = new TechnicianMapper();
        return technicianMapper.fromTechnician(technician);
    }
}
