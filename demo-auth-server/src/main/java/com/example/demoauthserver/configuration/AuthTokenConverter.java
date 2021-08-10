package com.example.demoauthserver.configuration;

import com.example.demoauthserver.models.User;
import com.example.demoauthserver.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;

import java.util.*;

import static java.util.Objects.nonNull;
import static org.springframework.security.core.authority.AuthorityUtils.createAuthorityList;

@RequiredArgsConstructor
public class AuthTokenConverter extends DefaultUserAuthenticationConverter {

    private final UserRepository repository;

    @Override
    public Map<String, ?> convertUserAuthentication(Authentication authentication) {
        Map<String, Object> response = new LinkedHashMap<>();
        User user = (User) authentication.getPrincipal();
        response.put("sub", authentication.getName());
        response.put("firstName", user.getFirstName());
        response.put("lastName", user.getLastName());
        mapAuthorities(authentication, response);
        return response;
    }

    @Override
    public Authentication extractAuthentication(Map<String, ?> map) {
        String username = (String) map.get("sub");
        if (nonNull(username)) {
            User user = repository.findByUsername(username);
            List<String> authorities = (ArrayList<String>) map.get(AUTHORITIES);
            List<GrantedAuthority> authorityList = createAuthorityList(authorities.toArray(new String[0]));
            return new UsernamePasswordAuthenticationToken(user, user.getPassword(), authorityList);
        }
        throw new InvalidTokenException("Invalid Token : Does not contain all the required parameters");
    }

    private void mapAuthorities(Authentication authentication, Map<String, Object> response) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        if (nonNull(authorities) && !authorities.isEmpty()) {
            response.put(AUTHORITIES, AuthorityUtils.authorityListToSet(authorities));
        }
    }

}
