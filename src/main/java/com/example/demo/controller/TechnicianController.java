package com.example.demo.controller;

import com.example.demo.dto.TechnicianDto;
import com.example.demo.service.TechnicianService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/v1")
@Api(value = "Technician Controller")
public class TechnicianController {
    @Autowired
    private TechnicianService technicianService;

    @PostMapping(value = "/technician", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create technician", response = ResponseEntity.class, httpMethod = "POST")
    @ApiResponses({
            @ApiResponse(code = 201, reference = "success", message = "Technician record created"),
            @ApiResponse(code = 500, reference = "error", message = "Interval Server Error")
    })
    public ResponseEntity<TechnicianDto> create(@RequestBody @Valid TechnicianDto technicianDto) throws Exception {
        technicianDto = technicianService.create(technicianDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(technicianDto);
    }

    @DeleteMapping(value = "/technician/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Delete technician", response = ResponseEntity.class, httpMethod = "DELETE")
    @ApiResponses({
            @ApiResponse(code = 200, reference = "success", message = "Technician record deleted"),
            @ApiResponse(code = 500, reference = "error", message = "Interval Server Error")
    })
    public ResponseEntity<?> delete(@PathVariable(name = "id") int id) throws NotFoundException {
        technicianService.delete(id);
        return ResponseEntity.ok().body(null);
    }

    @PutMapping(value = "/technician/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update technician", response = ResponseEntity.class, httpMethod = "PUT")
    @ApiResponses({
            @ApiResponse(code = 200, reference = "success", message = "Technician record updated"),
            @ApiResponse(code = 500, reference = "error", message = "Interval Server Error")
    })
    public ResponseEntity<TechnicianDto> update(@PathVariable(name = "id") int id, @RequestBody @Valid TechnicianDto technicianDto) throws Exception {
        technicianDto = technicianService.update(id, technicianDto);
        return ResponseEntity.ok().body(technicianDto);
    }
}
