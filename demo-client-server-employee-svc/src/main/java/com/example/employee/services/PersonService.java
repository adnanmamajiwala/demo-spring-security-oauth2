package com.example.employee.services;

import com.example.employee.models.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class PersonService {

    @Value("${service.person.base-url}")
    private String baseUrl;

    private final WebClient webClient;

    public Person[] getAllPeople() {
        String url = baseUrl + "/people";
        return this.webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(Person[].class)
                .block();
    }

    public Person save(Person person) {
        return this.webClient.post()
                .uri("http://localhost:8070/people")
                .body(person, Person.class)
                .retrieve()
                .bodyToMono(Person.class)
                .block();
    }
}
