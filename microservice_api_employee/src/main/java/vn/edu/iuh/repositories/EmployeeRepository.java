package vn.edu.iuh.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.iuh.models.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
