package com.example.demo.controller;

import com.example.demo.dto.AssignmentDto;
import com.example.demo.entities.Assignment;
import com.example.demo.service.AssignmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/v1")
@Api(value = "Assignment Controller")
public class AssignmentController {

    private final AssignmentService assignmentService;

    public AssignmentController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @PostMapping(value = "/assignment", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create assignment", response = ResponseEntity.class, httpMethod = "POST")
    @ApiResponses({
            @ApiResponse(code = 201, reference = "success", message = "assignment record created"),
            @ApiResponse(code = 500, reference = "error", message = "Interval Server Error")
    })
    public ResponseEntity<AssignmentDto> create(@RequestBody @Valid AssignmentDto assignmentDto) throws Exception {
        assignmentDto = assignmentService.create(assignmentDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(assignmentDto);
    }

    @GetMapping(value = "/assignment/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get Assignment", response = ResponseEntity.class, httpMethod = "GET")
    @ApiResponses({
            @ApiResponse(code = 200, reference = "success", message = "Assignment details"),
            @ApiResponse(code = 500, reference = "error", message = "Interval Server Error")
    })
    public ResponseEntity<Assignment> get(@PathVariable(name = "id") int id) throws NotFoundException {
        Assignment assignment = assignmentService.get(id);
        return ResponseEntity.ok().body(assignment);
    }
}
