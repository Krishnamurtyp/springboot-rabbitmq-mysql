package com.example.demo.repository;

import com.example.demo.entities.Technician;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TechnicianRepository extends JpaRepository<Technician, Integer> {

    Technician findByFirstName(String firstName);
}
