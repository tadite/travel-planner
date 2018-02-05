package edu.nc.travelplanner.security.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import groovy.transform.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Objects;

public class JwtUser implements UserDetails {

    private final Long id;
    private final String name;
    private final String password;
    private final String email;
    private final Collection<? extends GrantedAuthority> authorities;

    public JwtUser(Long id, String name, String email, String password,
                   Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return this.name;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public String getEmail() {
        return email;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "JwtUser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", authorities=" + authorities +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JwtUser jwtUser = (JwtUser) o;
        return Objects.equals(id, jwtUser.id) &&
                Objects.equals(name, jwtUser.name) &&
                Objects.equals(password, jwtUser.password) &&
                Objects.equals(email, jwtUser.email) &&
                Objects.equals(authorities, jwtUser.authorities);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, password, email, authorities);
    }
}
