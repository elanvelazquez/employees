package com.elanvelazquez.cognizant.challenge.employee.controllers;

import com.elanvelazquez.cognizant.challenge.employee.domain.Employee;
import com.elanvelazquez.cognizant.challenge.employee.dtos.EmployeeDTO;
import com.elanvelazquez.cognizant.challenge.employee.dtos.LoanDTO;
import com.elanvelazquez.cognizant.challenge.employee.services.EmployeeService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Errors to catch for challenge
 *  If we want to delete an employee which is having an active loan it must throw an error.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
class EmployeeControllerTest {
    private  static  final String HOST_FOR_EMPLOYEES ="http://localhost:8080/employees";
    private  static  final String HOST_FOR_LOANS ="http://localhost:9090/loans";

    @Autowired
    EmployeeController employeeController = mock(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService ;

    @Autowired
    private RestTemplate restTemplate;


    @Test
    void crateEmployeeFromController_success(){
        Employee employee = new Employee();
        employee.setFirstName("Elan");
        employee.setLastName("Velazquez");
        ResponseEntity response = restTemplate.exchange(HOST_FOR_EMPLOYEES,HttpMethod.POST,new HttpEntity<>(employee),ResponseEntity.class);
        //CREATED
        Assert.assertEquals(response.getStatusCode(),HttpStatus.CREATED);
    }

    @Test
    void findByLastName_success() {
        Employee employee = new Employee();
        employee.setFirstName("Elan Find By");
        employee.setLastName("Velazquez");

        ResponseEntity response = restTemplate.exchange(HOST_FOR_EMPLOYEES,HttpMethod.POST,new HttpEntity<>(employee),ResponseEntity.class);
        //get Employees by last Name, we already add in the last test.
        ResponseEntity<EmployeeDTO[]> employeeDTOS = restTemplate.getForEntity(String.format("%s/%s",HOST_FOR_EMPLOYEES,employee.getLastName()),
                EmployeeDTO[].class);

        List<EmployeeDTO> employeesFound = Arrays.asList(employeeDTOS.getBody());
        Assert.assertTrue(employeesFound.size()>0);

    }

    @Test
    void softDelete_success() {
        Employee employee = new Employee();
        employee.setFirstName("Elan To Delete");
        employee.setLastName("Vejar");
        ResponseEntity responseAddEmployee = restTemplate.exchange(HOST_FOR_EMPLOYEES,HttpMethod.POST,new HttpEntity<>(employee),ResponseEntity.class);

        //get Employees by last Name, we already add in the last test.
        ResponseEntity<EmployeeDTO[]> employeeDTOS = restTemplate.getForEntity(String.format("%s/%s",HOST_FOR_EMPLOYEES,employee.getLastName()),
                EmployeeDTO[].class);
        List<EmployeeDTO> employeesFound = Arrays.asList(employeeDTOS.getBody());

        ResponseEntity responseDeleteEmployee = restTemplate.exchange(String.format("%s/%s", HOST_FOR_EMPLOYEES, employeesFound.get(0).getId()),
                HttpMethod.DELETE,
                null,
                ResponseEntity.class);
        Assert.assertEquals(responseDeleteEmployee.getStatusCode(),HttpStatus.OK);

    }

    /**
     * If we want to delete an employee which is having an active loan it must throw an error.
     */
    @Test()
    void softDelete_errorHaveAnActiveLoan_errorActiveLoan() {
        Employee employee = new Employee();
        employee.setFirstName("Jorge");
        employee.setLastName("Lopez");
        //create Employee
        restTemplate.postForObject(HOST_FOR_EMPLOYEES,
                employee,
                ResponseEntity.class);
        //Find Employee
        ResponseEntity<EmployeeDTO[]> employeeDTOS = restTemplate.getForEntity(String.format("%s/%s",HOST_FOR_EMPLOYEES,employee.getLastName()),
                EmployeeDTO[].class);
        List<EmployeeDTO> employeesFound = Arrays.asList(employeeDTOS.getBody());
        EmployeeDTO employeeDTO = employeesFound.get(0);

        LoanDTO loanDTO = new LoanDTO();
        loanDTO.setAmount(2000);
        //Post Loan for employee id
        restTemplate.postForObject(String.format("%s/%s",HOST_FOR_LOANS ,employeeDTO.getId()),
                loanDTO,
                ResponseEntity.class);

        try {
            //soft Delete.
            restTemplate.exchange(String.format("%s/%sId",HOST_FOR_EMPLOYEES,employeeDTO.getId()),
                    HttpMethod.DELETE,
                    null,
                    ResponseEntity.class);
        }catch (HttpClientErrorException exception){
            //If we want to delete an employee which is having an active loan it must throw an error.
            Assert.assertEquals(exception.getStatusCode(),HttpStatus.BAD_REQUEST);
        }
    }
}