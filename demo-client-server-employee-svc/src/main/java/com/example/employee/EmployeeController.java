package com.example.employee;


import com.example.employee.models.Employee;
import com.example.employee.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService service;
    private final WebClient webClient;

    @GetMapping
    public List<Employee> getAll() {
        return service.findAll();
    }

    @PostMapping
    public Employee save(@Valid Employee employee) {
        return service.save(employee);
    }

}
