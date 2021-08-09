package com.example.person;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Data
@Entity(name = "PERSON")
public class Person {

    @Id
    @GeneratedValue(generator = "person_sequence")
    private Long id;

    @NotNull
    private String firstName;

    @NotNull
    private String LastName;

}
