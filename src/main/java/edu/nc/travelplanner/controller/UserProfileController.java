package edu.nc.travelplanner.controller;

import edu.nc.travelplanner.config.AuthenticationFacade;
import edu.nc.travelplanner.dto.profile.UserProfileDto;
import edu.nc.travelplanner.service.user.UserProfileService;
import edu.nc.travelplanner.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserProfileController {

    @Autowired
    UserProfileService userProfileService;

    @Autowired
    UserService userService;

    @Autowired
    AuthenticationFacade authenticationFacade;

    @GetMapping(path = "/api/client/profile/{username}")
    public UserProfileDto getUserProfileDtoById(@PathVariable String username){

        return userProfileService.getUserProfileByName(username);
    }

    @GetMapping(path = "/api/client/profile")
    public UserProfileDto getCurrentUserProfileDto(){

        return getUserProfileDtoById(authenticationFacade.getAuthentication().getName());
    }

    @PostMapping(path = "/api/client/profile")
    public UserProfileDto updateUserProfileDto(@RequestBody UserProfileDto userProfileDto){
        if (authenticationFacade.getAuthentication().getName().equals(userProfileDto.getLogin()))
            return userProfileService.updateUserProfile(userProfileDto);
        return new UserProfileDto();
    }
}
