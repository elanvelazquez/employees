package com.elanvelazquez.cognizant.challenge.employee.repositories;

import com.elanvelazquez.cognizant.challenge.employee.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    Optional<List<Employee>> findByLastName(String lastName);
    Optional<List<Employee>> findByLastNameAndFirstName(String lastName, String firstName);
}