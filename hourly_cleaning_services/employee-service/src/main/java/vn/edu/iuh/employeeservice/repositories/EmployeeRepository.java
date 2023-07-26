package vn.edu.iuh.employeeservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.iuh.employeeservice.models.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
