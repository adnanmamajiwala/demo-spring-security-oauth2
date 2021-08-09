package com.example.address;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static java.lang.String.format;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/addresses")
public class AddressController {

    private final AddressRepository repository;

    @GetMapping
    public List<Address> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{addressId}")
    public Address getAddressById(@PathVariable("addressId") Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new AddressNotFoundException(format("Could not find Address by id [%d]", id)));
    }

    @GetMapping("/search")
    public Address getAddressByPersonId(@RequestParam("personId") Long id) {
        return repository.findByPersonId(id)
                .orElseThrow(() -> new AddressNotFoundException(format("Could not find Address by person id [%d]", id)));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Address save(@Valid Address address) {
        return repository.save(address);
    }


    @ExceptionHandler(value = AddressNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handle(AddressNotFoundException ex) {
        return ErrorResponse.builder()
                .message(ex.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .build();
    }

}
