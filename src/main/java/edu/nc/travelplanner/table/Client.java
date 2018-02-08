package edu.nc.travelplanner.table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "client")
public class Client implements UserDetails {

    @Id
    @Column(name = "client_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long clientId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "age")
    private Integer age;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    private Country country;

    @Column(name = "role")
    private String role;

    @Column(name = "is_blocked")
    private Boolean isBlocked;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "client")
    private Set<SocialNetwork> socialNetworks = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "client")
    private Set<TravelForClient> travelForClients = new HashSet<>();

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId  = clientId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName  = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName  = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email  = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age  = age;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login  = login;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));
        authorities.add(new SimpleGrantedAuthority(role));
        return authorities;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !isBlocked;
    }

    public void setPassword(String password) {
        this.password  = password;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city  = city;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry (Country country) {
        this.country  = country;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role  = role;
    }

    public Boolean getIsBlocked() {
        return isBlocked;
    }

    public void setIsBlocked(Boolean isBlocked) {
        this.isBlocked  = isBlocked;
    }

    public void setSocialNetworks(Set<SocialNetwork> socialNetworks) {
        this.socialNetworks = socialNetworks;
    }

    public void setTravelForClients(Set<TravelForClient> travelForClients) {
        this.travelForClients = travelForClients;
    }

    public void addSocialNetwork(SocialNetwork socialNetwork) {
        this.socialNetworks.add(socialNetwork);
    }

    public void removeSocialNetwork(SocialNetwork socialNetwork) {
        socialNetworks.remove(socialNetwork);
    }

    public void addTravelForClient(TravelForClient travelForClient) {
        this.travelForClients.add(travelForClient);
    }

    public void removeTravelForClient(TravelForClient travelForClient) {
        travelForClients.remove(travelForClient);
    }

    public Set<SocialNetwork> getSocialNetworks() {
        return socialNetworks;
    }

    public Boolean getBlocked() {
        return isBlocked;
    }

    public Set<TravelForClient> getTravelForClients() {
        return travelForClients;
    }
}