package com.example;

import com.example.address.Address;
import com.example.address.AddressRepository;
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
    public CommandLineRunner run(AddressRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                log.debug("No records found. Now inserting from data.json");
                repository.saveAll(Arrays.asList(getAddresses()));
            } else {
                log.debug("Records are found.");
            }
        };
    }

    private Address[] getAddresses() throws Exception {
        return new ObjectMapper().readValue(new ClassPathResource("data.json").getInputStream(), Address[].class);
    }
}
