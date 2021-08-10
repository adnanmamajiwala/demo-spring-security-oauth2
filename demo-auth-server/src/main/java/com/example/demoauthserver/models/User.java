package com.example.demoauthserver.models;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;

import static org.springframework.security.core.authority.AuthorityUtils.createAuthorityList;

@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "USER_INFO")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    protected Long id;

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private boolean deleted;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return createAuthorityList(role.authority());
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !this.deleted;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.deleted;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !this.deleted;
    }

    @Override
    public boolean isEnabled() {
        return !this.deleted;
    }
}
