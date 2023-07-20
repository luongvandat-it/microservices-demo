package vn.edu.iuh.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import vn.edu.iuh.models.Employee;

import java.util.Optional;

public interface EmployeeService {
    Employee save(Employee employee);
    Optional<Employee> findById(long id);

    Page<Employee> findAll(Pageable pageable);
}
