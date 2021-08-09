package com.example.demoauthserver;

import com.example.demoauthserver.models.*;
import com.example.demoauthserver.repositories.ClientRepository;
import com.example.demoauthserver.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Order(2)
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;
    private final ObjectMapper mapper;

    @Override
    public void run(String... args) throws Exception {
        createUsers();
        createClients();
    }

    private void createUsers() throws Exception {
        List<User> users = Arrays.asList(getUsers());
        for (User user : users) {
            String rawPassword = user.getPassword();
            user.setPassword(passwordEncoder.encode(rawPassword));
            userRepository.save(user);
        }

        log.debug(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(users));
        log.debug("Creating default admin user.");
    }

    private User[] getUsers() throws Exception {
        InputStream inputStream = new ClassPathResource("users.json").getInputStream();
        return new ObjectMapper().readValue(inputStream, User[].class);
    }

    private void createClients() throws Exception {
        List<Client> clients = Arrays.asList(getClients());
        for (Client client : clients) {
            String rawSecret = client.getClientSecret();
            client.setClientSecret(passwordEncoder.encode(rawSecret));
            clientRepository.save(client);
        }
        log.debug(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(clients));
        log.debug("Created default clients.");
    }

    private Client[] getClients() throws Exception {
        InputStream inputStream = new ClassPathResource("clients.json").getInputStream();
        return new ObjectMapper().readValue(inputStream, Client[].class);
    }

}