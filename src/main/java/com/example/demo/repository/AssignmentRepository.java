package com.example.demo.repository;

import com.example.demo.entities.Assignment;
import com.example.demo.entities.Technician;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Integer> {

    Assignment findAssignmentByTechnician(Technician technician);
}
