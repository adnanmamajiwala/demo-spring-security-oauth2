package com.example.demoauthserver.repositories;

import com.example.demoauthserver.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, String> {

    Client findByClientId(String clientId);
}
