package edu.nc.travelplanner.security.auth;

import edu.nc.travelplanner.table.Client;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

public final class JwtUserFactory {

    private JwtUserFactory() {}

    public static JwtUser create(Client client) {
        return new JwtUser(
                client.getClientId(),
                client.getLogin(),
                client.getEmail(),
                client.getPassword(),
                mapToGrantedAuthorities(client.getRole())
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(String role) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));
        return authorities;
    }

}
