package com.elanvelazquez.cognizant.challenge.employee.controllers;

import com.elanvelazquez.cognizant.challenge.employee.domain.Employee;
import com.elanvelazquez.cognizant.challenge.employee.dtos.EmployeeDTO;
import com.elanvelazquez.cognizant.challenge.employee.dtos.LoanDTO;
import com.elanvelazquez.cognizant.challenge.employee.services.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("/employees")
public class EmployeeController {

    private final ModelMapper MODEL_MAPPER= new ModelMapper();
    @Autowired
    private RestTemplate restTemplate;

    private EmployeeService employeeService;
    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @PostMapping
    public ResponseEntity createEmployee(@RequestBody Employee employeeDTO){
        Employee createdEmployee = employeeService.createEmployee(employeeDTO);
        MODEL_MAPPER.map(createdEmployee,employeeDTO);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{lastName}")
                .buildAndExpand(employeeDTO.getLastName())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{lastName}")
    public List<EmployeeDTO> getEmployeeByLastName(@PathVariable String lastName){
        List<Employee> employees = employeeService.findByLastName(lastName).get();
        List<EmployeeDTO> employeesDTOList = employees.stream()
                .map(employee -> MODEL_MAPPER.map(employee,EmployeeDTO.class))
                .collect(Collectors.toList());

        return employeesDTOList;
    }

    @GetMapping("/find/{id}")
    public EmployeeDTO getEmployeeById(@PathVariable int id){
        Optional<Employee> employee= employeeService.findEmployeeById(id);
        EmployeeDTO  employeeDTO = new EmployeeDTO();
        MODEL_MAPPER.map(employee.get(),employeeDTO);
        return employeeDTO;
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity softDeleteEmployee(@PathVariable int employeeId) {
        ResponseEntity<LoanDTO[]> forEntity = restTemplate.getForEntity(String.format("http://localhost:9090/loans/employee/%s", employeeId), LoanDTO[].class);
        List<LoanDTO> loanDTOS = Arrays.asList(forEntity.getBody());

        Employee softDeletedEmployee =  employeeService.softDelete(employeeId, Arrays.asList(forEntity.getBody()));
        return ResponseEntity.ok().build();
    }


}
