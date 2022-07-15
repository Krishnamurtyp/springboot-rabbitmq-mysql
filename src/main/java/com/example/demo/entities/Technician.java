package com.example.demo.entities;


import com.example.demo.dto.TechnicianDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "technician", indexes = {
        @Index(name = "search_index", columnList = "first_name,last_name")
})
@ToString
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Technician implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "email", nullable = false)
    private String email;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "technician")
    @JsonBackReference("technician-assignment")
    private Assignment assignment;

    public Technician(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public Technician update(TechnicianDto technicianDto) {
        this.firstName = technicianDto.getFirstName();
        this.lastName = technicianDto.getLastName();
        this.email = technicianDto.getEmail();
        return this;
    }
}
