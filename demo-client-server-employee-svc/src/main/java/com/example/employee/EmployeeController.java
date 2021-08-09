package com.example.employee;


import com.example.employee.models.Address;
import com.example.employee.models.Employee;
import com.example.employee.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient;

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

    @GetMapping(value = "/address")
    public Address[] getArticles() {
        return this.webClient
                .get()
                .uri("http://localhost:8090/addresses")
                .retrieve()
                .bodyToMono(Address[].class)
                .block();
    }


}
