package com.example;

import com.example.person.Person;
import com.example.person.PersonRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.util.Arrays;

@Configuration
@Slf4j
public class DataInitializer {

    @Bean
    public CommandLineRunner run(PersonRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                log.debug("No records found. Now inserting from data.json");
                repository.saveAll(Arrays.asList(getPeople()));
            } else {
                log.debug("Records are found.");
            }
        };
    }

    private Person[] getPeople() throws Exception {
        return new ObjectMapper().readValue(new ClassPathResource("data.json").getInputStream(), Person[].class);
    }
}
