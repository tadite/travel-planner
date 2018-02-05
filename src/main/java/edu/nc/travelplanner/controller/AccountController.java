package edu.nc.travelplanner.controller;

import edu.nc.travelplanner.dto.UserDto;
import edu.nc.travelplanner.exception.InvalidPasswordException;
import edu.nc.travelplanner.exception.UserAlreadyExistsException;
import edu.nc.travelplanner.exception.UserNotFoundException;
import edu.nc.travelplanner.security.auth.JwtAuthenticationRequest;
import edu.nc.travelplanner.security.auth.JwtAuthenticationResponse;
import edu.nc.travelplanner.security.auth.JwtUser;
import edu.nc.travelplanner.security.auth.JwtUtil;
import edu.nc.travelplanner.service.user.UserService;
import edu.nc.travelplanner.table.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;

@RestController
public class AccountController {

    protected final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Value("${auth.header}")
    private String tokenHeader;

    public final static String SIGNUP_URL = "/api/auth/signup";
    public final static String SIGNIN_URL = "/api/auth/signin";
    public final static String REFRESH_TOKEN_URL = "/api/auth/token/refresh";

    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;
    private UserDetailsService userDetailsService;
    private UserService userService;

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    public void setJwtTokenUtil(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Autowired
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Bean
    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @PostMapping(SIGNUP_URL)
    public ResponseEntity createAuthenticationToken(@RequestBody UserDto userDto)
            throws AuthenticationException {

        final String login = userDto.getLogin();
        final String password = userDto.getPassword();
        final String email = userDto.getEmail();
        LOG.info("[POST] CREATING TOKEN FOR User " + login);

        if(this.userService.find(login) != null) {
            throw new UserAlreadyExistsException();
        }

        Client client = new Client();
        client.setLogin(login);
        client.setPassword(password);
        client.setEmail(email);
        client.setIsBlocked(false);
        client.setRole("USER");
        userService.save(client);

        Client userDetails;

        try {
            userDetails = (Client) userDetailsService.loadUserByUsername(login);
        } catch (UsernameNotFoundException ex) {
            LOG.error(ex.getMessage());
            throw new UserNotFoundException();
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(login, password)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
    }

    @PostMapping(SIGNIN_URL)
    public ResponseEntity getAuthenticationToken(@RequestBody UserDto userDto)
            throws AuthenticationException {

        final String name = userDto.getLogin();
        final String password = userDto.getPassword();
        LOG.info("[POST] GETTING TOKEN FOR User " + name);
        Client userDetails;

        try {
            userDetails = (Client) userDetailsService.loadUserByUsername(name);
        } catch (UsernameNotFoundException | NoResultException ex) {
            LOG.error(ex.getMessage());
            throw new UserNotFoundException();
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
            return new ResponseEntity<>(ex, HttpStatus.BAD_REQUEST);
        }

        if(!passwordEncoder().matches(password, userDetails.getPassword())) {
            throw new InvalidPasswordException();
        }

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(name, password)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
    }

    @PostMapping(REFRESH_TOKEN_URL)
    public ResponseEntity refreshAuthenticationToken(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        LOG.info("[POST] REFRESHING TOKEN");
        String refreshedToken = jwtUtil.refreshToken(token);
        return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken));
    }

}
