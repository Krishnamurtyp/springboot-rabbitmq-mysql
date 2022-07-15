package com.example.demo.controller;

import com.example.demo.dto.AssignmentDto;
import com.example.demo.dto.TechnicianDto;
import com.example.demo.entities.Assignment;
import com.example.demo.entities.Technician;
import com.example.demo.repository.AssignmentRepository;
import com.example.demo.repository.TechnicianRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import gherkin.deps.com.google.gson.Gson;
import gherkin.deps.com.google.gson.JsonElement;
import gherkin.deps.com.google.gson.JsonObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AssignmentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private AssignmentRepository assignmentRepository;
    @Autowired
    private TechnicianRepository technicianRepository;

    @Test
    public void testAssignmentShouldBeCreated() throws Exception {
        Technician technician = createTechnician();
        AssignmentDto assignmentDto = new AssignmentDto(technician.getId(), LocalDateTime.now(), LocalDateTime.now());
        mockMvc.perform(post("/v1/assignment").contentType("application/json").content(objectMapper.writeValueAsString(assignmentDto))).andExpect(status().isCreated());
        Assignment assignment = assignmentRepository.findAssignmentByTechnician(technician);
        assertThat(assignment.getTechnician().getId()).isEqualTo(technician.getId());
        MvcResult mvcResult = mockMvc.perform(get("/v1/assignment/{id}", assignment.getId()).contentType("application/json").content(objectMapper.writeValueAsString(null))).andExpect(status().isOk()).andReturn();
        assertNotEquals("", mvcResult.getResponse().getContentAsString());
        this.deleteTechnician(technician.getId());
    }

    @Test
    public void testAssignmentHaveNullTechnicianAfterTechnicianIsDeleted() throws Exception {
        Technician technician = createTechnician();
        AssignmentDto assignmentDto = new AssignmentDto(technician.getId(), LocalDateTime.now(), LocalDateTime.now());
        MvcResult mvcResult = mockMvc.perform(post("/v1/assignment").contentType("application/json").content(objectMapper.writeValueAsString(assignmentDto))).andExpect(status().isCreated()).andReturn();
        JsonObject convertedObject = new Gson().fromJson(mvcResult.getResponse().getContentAsString(), JsonElement.class).getAsJsonObject();
        int assignmentId = convertedObject.get("id").getAsInt();
        mockMvc.perform(delete("/v1/technician/{id}", technician.getId()).contentType("application/json").content(objectMapper.writeValueAsString(null))).andExpect(status().isOk());
        mvcResult = mockMvc.perform(get("/v1/assignment/{id}", assignmentId).contentType("application/json").content(objectMapper.writeValueAsString(null))).andExpect(status().isOk()).andReturn();
        convertedObject = new Gson().fromJson(mvcResult.getResponse().getContentAsString(), JsonElement.class).getAsJsonObject();
        assertTrue(convertedObject.get("technician").isJsonNull());
    }

    private Technician createTechnician() throws Exception {
        String uniqueId = LocalDateTime.now().toString();
        TechnicianDto technicianDto = new TechnicianDto("TestCaseTechnician" + uniqueId, "TestCaseTechnician" + uniqueId, "test@test.net");
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/v1/technician").contentType("application/json").content(objectMapper.writeValueAsString(technicianDto))).andExpect(status().isCreated());
        return technicianRepository.findByFirstName("TestCaseTechnician" + uniqueId);
    }

    private void deleteTechnician(int id) throws Exception {
        mockMvc.perform(delete("/v1/technician/{id}", id).contentType("application/json").content(objectMapper.writeValueAsString(null))).andExpect(status().isOk());
    }
}
