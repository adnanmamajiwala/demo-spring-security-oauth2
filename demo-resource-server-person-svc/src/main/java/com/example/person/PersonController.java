package com.example.person;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static java.lang.String.format;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/people")
public class PersonController {

    private final PersonRepository repository;

    @GetMapping
    public List<Person> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{personId}")
    public Person getAddressById(@PathVariable("personId") Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(format("Could not find Person by id [%d]", id)));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Person save(@Valid Person person) {
        return repository.save(person);
    }


    @ExceptionHandler(value = PersonNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handle(PersonNotFoundException ex) {
        return ErrorResponse.builder()
                .message(ex.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .build();
    }

}
