package com.example.address;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Data
@Entity(name = "ADDRESS")
public class Address {

    @Id
    @GeneratedValue(generator = "address_sequence")
    private Long id;

    @NotNull
    private String street;
    private String state;
    private String zipCode;
    private String country;

    @NotNull(message = "Person Id for the Address is required")
    private Long personId;
}
