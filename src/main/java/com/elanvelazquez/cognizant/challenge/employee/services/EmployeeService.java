package com.elanvelazquez.cognizant.challenge.employee.services;

import com.elanvelazquez.cognizant.challenge.employee.domain.Employee;
import com.elanvelazquez.cognizant.challenge.employee.dtos.LoanDTO;
import com.elanvelazquez.cognizant.challenge.employee.exceptions.GenericRequestException;
import com.elanvelazquez.cognizant.challenge.employee.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {


    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }


    public Employee createEmployee(Employee employee) {
        if(employeeRepository.findByLastNameAndFirstName(employee.getLastName(),employee.getFirstName()).get().stream().count()>0)
        {
            throw new GenericRequestException("User already created, please try again.", HttpStatus.CONFLICT);
        }
        Employee savedEmployee =  employeeRepository.save(employee);
        return savedEmployee;
    }

    public Optional<List<Employee>> findByLastName(String lastName) {
        Optional<List<Employee>> employee = employeeRepository.findByLastName(lastName);
        if(employee.isEmpty()) throw new GenericRequestException("Employee not found, please try again.",HttpStatus.NOT_FOUND);
        return employee;
    }

    public Optional<Employee> findEmployeeById(int id){
        Optional<Employee> employee = employeeRepository.findById(id);
        if(employee.isEmpty()) throw new GenericRequestException("Employee not found, please try again.",HttpStatus.NOT_FOUND);
        return employee;
    }

    public Employee softDelete(int employeeId, List<LoanDTO> loanDTOList){
        Optional<Employee> employeeToDelete = employeeToDelete = employeeRepository.findById(employeeId);

        if(employeeToDelete.isEmpty()) throw new GenericRequestException("Employee not exists, please try again.",HttpStatus.NOT_FOUND);
        if(loanDTOList.stream().count()>0)
            throw new GenericRequestException("Employee can not be deleted because there are active loans.",HttpStatus.BAD_REQUEST);

        employeeToDelete.get().setDeleted(true);
        employeeRepository.save(employeeToDelete.get());

        return employeeToDelete.get();
    }

}

