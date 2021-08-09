package com.example;

import com.example.person.Person;
import com.example.person.PersonRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.util.Arrays;

@Configuration
@Slf4j
public class DataInitializer {

    @Bean
    public CommandLineRunner run(PersonRepository repository) {
        return args -> repository.saveAll(Arrays.asList(getPeople()));
    }

    private Person[] getPeople() throws Exception {
        InputStream inputStream = new ClassPathResource("data.json").getInputStream();
        return new ObjectMapper().readValue(inputStream, Person[].class);
    }
}
