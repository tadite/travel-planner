package edu.nc.travelplanner.controller;

import edu.nc.travelplanner.dto.manage.UserManageDto;
import edu.nc.travelplanner.service.user.UserManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserManageController {

    @Autowired
    UserManageService userManageService;

    @GetMapping(path = "/api/manage/user/page/{pageNum}")
    public ResponseEntity<List<UserManageDto>> getUsersByPage(@PathVariable(value = "pageNum") int pageNum){
        return new ResponseEntity<>(userManageService.getClientsByPage(pageNum).stream().map(client -> UserManageDto.fromClient(client)).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping(path = "/api/manage/user")
    public ResponseEntity<List<UserManageDto>> getAllUsers(){
        return new ResponseEntity<>(userManageService.getAllClients().stream().map(client -> UserManageDto.fromClient(client)).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping(path = "/api/manage/user/{clientId}")
    public ResponseEntity<UserManageDto> getAllUsers(@PathVariable(value = "clientId") Long clientId){
        return new ResponseEntity<>(UserManageDto.fromClient(userManageService.getClientById(clientId)), HttpStatus.OK);
    }

    @PostMapping(path = "/api/manage/user/block")
    public ResponseEntity<Boolean> blockUserById(@RequestBody Long userId){
        return new ResponseEntity<>(userManageService.blockClientById(userId), HttpStatus.OK);
    }
}
