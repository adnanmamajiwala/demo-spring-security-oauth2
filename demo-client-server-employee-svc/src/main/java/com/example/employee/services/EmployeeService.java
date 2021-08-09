package com.example.employee.services;

import com.example.employee.models.Address;
import com.example.employee.models.Employee;
import com.example.employee.models.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final PersonService personService;
    private final AddressService addressService;

    public List<Employee> findAll() {
        return Arrays.stream(personService.getAllPeople())
                .map(person -> Employee.builder()
                        .id(person.getId())
                        .firstName(person.getFirstName())
                        .lastName(person.getLastName())
                        .address(addressService.findAddressByPersonId(person.getId()))
                        .build())
                .collect(Collectors.toList());
    }

    public Employee save(Employee employee) {
        Person savedPerson = personService.save(Person.builder().firstName(employee.getFirstName()).build());
        Address savedAddress = addressService.save(employee.getAddress());
        return Employee.builder()
                .id(savedPerson.getId())
                .firstName(savedPerson.getFirstName())
                .lastName(savedPerson.getLastName())
                .address(savedAddress)
                .build();
    }
}
