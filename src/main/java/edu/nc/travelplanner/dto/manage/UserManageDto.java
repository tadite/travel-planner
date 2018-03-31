package edu.nc.travelplanner.dto.manage;

import edu.nc.travelplanner.table.Client;
import edu.nc.travelplanner.table.Country;

public class UserManageDto {

    private Long clientId;
    private String login;
    private String email;
    private String firstName;
    private String lastName;
    private Boolean isBlocked;
    private String countryName;
    private Integer age;

    public UserManageDto() {
    }

    public UserManageDto(Long clientId, String login, String email, String firstName, String lastName, Boolean isBlocked, String countryName, Integer age) {
        this.clientId = clientId;
        this.login = login;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isBlocked = isBlocked;
        this.countryName = countryName;
        this.age = age;
    }

    public static UserManageDto fromClient(Client client){
        UserManageDto userManageDto = new UserManageDto();
        userManageDto.setLogin(client.getLogin());
        userManageDto.setEmail(client.getEmail());
        userManageDto.setAge(client.getAge());
        userManageDto.setBlocked(client.getIsBlocked());
        userManageDto.setClientId(client.getClientId());
        Country country = client.getCountry();
        if (country!=null)
            userManageDto.setCountryName(country.getName());
        userManageDto.setFirstName(client.getFirstName());
        userManageDto.setLastName(client.getLastName());
        return userManageDto;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(Boolean blocked) {
        isBlocked = blocked;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
