package com.example.employee.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    private Long id;

    @NotNull
    private String street;

    @NotNull
    private String state;

    @NotNull
    private String zipCode;

    @NotNull
    private String country;

    @NotNull
    private Long personId;
}
