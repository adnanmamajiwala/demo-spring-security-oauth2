package com.example.employee.services;

import com.example.employee.models.Address;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class AddressService {

    @Value("${service.address.base-url}")
    private String baseUrl;

    private final WebClient webClient;

    public Address findAddressByPersonId(Long id) {
        return this.webClient.get()
                .uri(baseUrl, uriBuilder -> uriBuilder
                        .path("/addresses/search")
                        .queryParam("personId", id)
                        .build())
                .retrieve()
                .bodyToMono(Address.class)
                .block();
    }

    public Address save(Address address) {
        return this.webClient.post()
                .uri("http://localhost:8090/addresses")
                .body(address, Address.class)
                .retrieve()
                .bodyToMono(Address.class)
                .block();
    }
}
