package com.example.demo.controller;

import com.example.demo.dto.TechnicianDto;
import com.example.demo.entities.Technician;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TechnicianControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TechnicianRepository technicianRepository;

    @Test
    public void testTechnicianShouldBeCreated() throws Exception {
        TechnicianDto technicianDto = new TechnicianDto("TestCaseTechnician", "TestCaseTechnician", "test@test.net");
        ObjectMapper objectMapper = new ObjectMapper();
        MvcResult mvcResult = mockMvc.perform(post("/v1/technician")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(technicianDto)))
                .andExpect(status().isCreated()).andReturn();
        JsonObject convertedObject = new Gson().fromJson(mvcResult.getResponse().getContentAsString(), JsonElement.class).getAsJsonObject();
        int techId = convertedObject.get("id").getAsInt();
        Technician technician = technicianRepository.findByFirstName("TestCaseTechnician");
        assertThat(technician.getEmail()).isEqualTo("test@test.net");
    }

    @Test
    public void testTechnicianShouldBeFailedForInvalidInput() throws Exception {
        TechnicianDto technicianDto = new TechnicianDto("TestCaseTechnician", "TestCaseTechnician", "test@");
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/v1/technician")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(technicianDto)))
                .andExpect(status().isBadRequest());
        technicianDto = new TechnicianDto("TestCaseTechnician", "", "test@test.net");
        mockMvc.perform(post("/v1/technician")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(technicianDto)))
                .andExpect(status().isBadRequest());
    }
}
