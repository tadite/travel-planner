package edu.nc.travelplanner.security.auth;

import javax.persistence.Column;
import java.io.Serializable;

public class JwtAuthenticationRequest implements Serializable {

    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;

    public JwtAuthenticationRequest() {
        super();
    }

    public JwtAuthenticationRequest(String username, String email, String password) {
        this.username=username;
        this.email=email;
        this.password=password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
