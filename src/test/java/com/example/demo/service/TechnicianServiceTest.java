package com.example.demo.service;

import com.example.demo.dto.TechnicianDto;
import com.example.demo.entities.Technician;
import com.example.demo.repository.TechnicianRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TechnicianServiceTest {

    @Mock
    private TechnicianRepository technicianRepository;
    @InjectMocks
    private TechnicianService technicianService;

    private Technician technicianMock(int id) {
        Technician technician = new Technician("Test", "Test", "test@gmail.com");
        if (id != 0) technician.setId(id);
        return technician;
    }

    @Test
    public void testCreateTechnicianSuccess() {
        int technicianId = 1;
        when(technicianRepository.save(any(Technician.class))).thenReturn(technicianMock(technicianId));
        TechnicianDto technicianDto = technicianService.create(new TechnicianDto("Test", "Test", "test@gmail.com"));
        assertTrue("Technician Created successfully", technicianDto.getId() == technicianId);
    }

}