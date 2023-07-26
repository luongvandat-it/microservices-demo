package vn.edu.iuh.employeeservice.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.edu.iuh.employeeservice.models.Employee;

import java.util.Optional;

public interface EmployeeService {
    Employee save(Employee employee);
    Optional<Employee> findById(long id);

    Page<Employee> findAll(Pageable pageable);
}
