package com.example.demo.service;

import com.example.demo.dto.AssignmentDto;
import com.example.demo.entities.Assignment;
import com.example.demo.entities.Technician;
import com.example.demo.repository.AssignmentRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AssignmentServiceTest {

    @Mock
    private AssignmentRepository assignmentRepository;
    @Mock
    private TechnicianService technicianService;
    @InjectMocks
    private AssignmentService assignmentService;

    @Before
    public void init() {
    }

    private Assignment assignmentMock(int id, int technicianId) {
        Assignment assignment = new Assignment(
                new Technician("Test", "Test", "test@gmail.com"),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        assignment.setId(id);
        if (technicianId != 0) assignment.setTechnician(technicianMock(technicianId));
        return assignment;
    }

    private Technician technicianMock(int id) {
        Technician technician = new Technician("Test", "Test", "test@gmail.com");
        if (id != 0) technician.setId(id);
        return technician;
    }

    @Test
    public void testCreateAssignmentSuccess() throws Exception {
        int assignmentId = 1;
        int technicianId = 1;
        when(technicianService.get(any(Integer.class))).thenReturn(technicianMock(technicianId));
        when(assignmentRepository.findAssignmentByTechnician(any(Technician.class))).thenReturn(null);
        when(assignmentRepository.save(any(Assignment.class))).thenReturn(assignmentMock(assignmentId, technicianId));
        AssignmentDto assignmentDto = assignmentService.create(new AssignmentDto(technicianId, LocalDateTime.now(), LocalDateTime.now()));
        assertTrue("Assignment Created successfully", assignmentDto.getId() == assignmentId);
    }

    @Test
    public void testCreateAssignmentFailed() throws Exception {
        int assignmentId = 1;
        int technicianId = 2;
        when(technicianService.get(any(Integer.class))).thenReturn(technicianMock(technicianId));
        when(assignmentRepository.findAssignmentByTechnician(any(Technician.class))).thenReturn(assignmentMock(assignmentId, technicianId));
        Exception exception = assertThrows(Exception.class, () -> assignmentService.create(new AssignmentDto(technicianId, LocalDateTime.now(), LocalDateTime.now())));
        assertEquals("Technician is already assigned to one assignment", exception.getMessage());
    }

}