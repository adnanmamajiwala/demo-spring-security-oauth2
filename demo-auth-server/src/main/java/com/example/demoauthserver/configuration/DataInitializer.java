package com.example.demoauthserver.configuration;

import com.example.demoauthserver.models.*;
import com.example.demoauthserver.repositories.ClientRepository;
import com.example.demoauthserver.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;

import static java.util.Objects.isNull;

@Slf4j
@Order(2)
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        createAdminUser();
        createClientUser();
    }

    private void createAdminUser() {
        User systemUser = User.builder()
                .role(Role.ADMIN)
                .username("admin")
                .password(passwordEncoder.encode("nimda"))
                .build();

        if (isNull(userRepository.findByUsername(systemUser.getUsername()))) {
            log.debug("Creating default admin user.");
            userRepository.save(systemUser);
        }
    }

    private void createClientUser() {
        Client client = Client.builder()
                .clientId("employee-client")
                .clientSecret(passwordEncoder.encode("secret"))
                .grantTypes(new HashSet<>() {{
                    add(GrantType.AUTHORIZATION_CODE.getCode());
                    add(GrantType.CLIENT_CREDENTIALS.getCode());
                    add(GrantType.REFRESH_TOKEN.getCode());
                }})
                .scopes(new HashSet<>() {{
                    add(ScopeType.OPEN_ID.getCode());
                }})
                .registeredRedirectUri(new HashSet<>() {{
                    add("http://localhost:8080/authorized");
                }})
                .accessTokenValiditySeconds(15)
                .refreshTokenValiditySeconds(30)
                .build();

        if (isNull(clientRepository.findByClientId(client.getClientId()))) {
            log.debug("Creating default client.");
            clientRepository.save(client);
        }

    }

}