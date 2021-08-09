package com.example.demoauthserver.configuration;

import com.example.demoauthserver.repositories.ClientRepository;
import com.example.demoauthserver.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.security.KeyPair;

@Slf4j
@Configuration
@EnableAuthorizationServer
@RequiredArgsConstructor
public class AuthServerConfiguration extends AuthorizationServerConfigurerAdapter {

    private final AuthenticationManager authenticationManager;
    private final KeyPair keyPair;
    private final OauthExceptionBuilder exceptionBuilder;
    private final ClientRepository clientRepository;
    private final UserRepository userRepository;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientRepository::findByClientId);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        JwtAccessTokenConverter accessTokenConverter = jwtAccessTokenConverter();
        endpoints
                .authenticationManager(this.authenticationManager)
                .userDetailsService(userRepository::findByUsername)
                .accessTokenConverter(accessTokenConverter)
                .tokenStore(new JwtTokenStore(accessTokenConverter))
                .exceptionTranslator(this::oAuth2ExceptionResponseEntity);
    }

    private ResponseEntity<OAuth2Exception> oAuth2ExceptionResponseEntity(Exception e) throws Exception {
        OAuth2Exception oAuth2Exception = exceptionBuilder.build(e);
        return ResponseEntity
                .status(oAuth2Exception.getHttpErrorCode())
                .body(oAuth2Exception);
    }

    private JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setKeyPair(keyPair);

        DefaultAccessTokenConverter accessTokenConverter = new DefaultAccessTokenConverter();
        accessTokenConverter.setUserTokenConverter(new AuthTokenConverter(userRepository));
        converter.setAccessTokenConverter(accessTokenConverter);
        return converter;
    }

}
