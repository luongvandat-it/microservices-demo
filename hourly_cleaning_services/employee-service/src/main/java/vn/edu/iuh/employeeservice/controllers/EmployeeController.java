package vn.edu.iuh.employeeservice.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.employeeservice.models.Employee;
import vn.edu.iuh.employeeservice.services.EmployeeService;

import java.util.Optional;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(final EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * POST: /api/employee/create
     * create employee
     * @param employee
     * @return return employee after save to database
     */
    @PostMapping("/create")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        try{
            Employee _employee = employeeService.save(employee);
            return new ResponseEntity<>(_employee, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * GET: /api/employee/get?id=
     * get employee by id
     * @param id id of employee
     * @return object employee or null
     */
    @GetMapping("/get")
    public ResponseEntity<Employee> findEmployeeById(@RequestParam long id) {
        try {
            Optional<Employee> employeeOptional = employeeService.findById(id);
            if (employeeOptional.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            Employee employee = employeeOptional.get();
            return new ResponseEntity<>(employee, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * GET: /api/employee/getAll?page=&size=
     * @param page page number
     * @param size size of page
     * @return return list employee or null
     */
    @GetMapping("/getAll")
    public ResponseEntity<Page<Employee>> getAllEmployeePagination(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        try {
            Sort sort = Sort.by(Sort.Direction.ASC, "lastName");
            Pageable pageable = PageRequest.of(page, size, sort);
            Page<Employee> employees = employeeService.findAll(pageable);
            return new ResponseEntity<>(employees, HttpStatus.OK);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * PUT: /api/employee/update?id=
     * @param id
     * @param employee
     * @return return employee after updated
     */
    @PutMapping("/update")
    public ResponseEntity<Employee> updateEmployee(@RequestParam Long id, @RequestBody Employee employee) {
        try {
            Optional<Employee> employeeOptional = employeeService.findById(id);
            if (employeeOptional.isEmpty()) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            Employee _employee = employeeOptional.get();
            _employee.setEmail(employee.getEmail());
            _employee.setPhone(employee.getPhone());
            _employee.setFirstName(employee.getLastName());
            _employee.setRating(employee.getRating());
            return new ResponseEntity<>(employeeService.save(_employee), HttpStatus.OK);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
