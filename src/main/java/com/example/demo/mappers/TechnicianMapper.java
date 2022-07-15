package com.example.demo.mappers;

import com.example.demo.dto.TechnicianDto;
import com.example.demo.entities.Technician;
import org.springframework.stereotype.Component;

@Component
public class TechnicianMapper {

    public TechnicianDto fromTechnician(Technician technician) {
        TechnicianDto technicianDto = new TechnicianDto(technician.getFirstName(), technician.getLastName(), technician.getEmail());
        technicianDto.setId(technician.getId());
        return technicianDto;
    }
}
