package edu.nc.travelplanner.dto.profile;

import edu.nc.travelplanner.table.City;
import edu.nc.travelplanner.table.Client;
import edu.nc.travelplanner.table.Country;
public class UserProfileDto {

    private long clientId;
    private String firstName;
    private String lastName;
    private String email;
    private Integer age;
    private String login;
    private String password;
    private String countryName;
    private Integer countryId;
    private String cityName;
    private Long cityId;

    public UserProfileDto() {
    }

    public static UserProfileDto fromClient(Client client){
        UserProfileDto dto = new UserProfileDto();
        dto.setClientId(client.getClientId());
        dto.setFirstName(client.getFirstName());
        dto.setLastName(client.getLastName());
        dto.setEmail(client.getEmail());
        dto.setAge(client.getAge());
        dto.setLogin(client.getLogin());

        Country country = client.getCountry();
        if (country!=null){
            dto.setCountryId(country.getCountryId());
            dto.setCountryName(country.getName());
        }

        City city = client.getCity();
        if (city!=null){
            dto.setCityId(city.getCityId());
            dto.setCityName(city.getName());
        }
        return dto;
    }

    public static Client toClient(UserProfileDto userProfileDto){
        Client client = new Client();
        return toClient(client, userProfileDto);
    }

    public static Client toClient(Client client, UserProfileDto userProfileDto){
        client.setClientId(userProfileDto.getClientId());
        client.setFirstName(userProfileDto.getFirstName());
        client.setLastName(userProfileDto.getLastName());
        client.setEmail(userProfileDto.getEmail());
        client.setAge(userProfileDto.getAge());

        return client;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }
}
