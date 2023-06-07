package com.sushil.electronic.store.controllers;

import com.sushil.electronic.store.dtod.ApiResponseMessage;
import com.sushil.electronic.store.dtod.UserDto;
import com.sushil.electronic.store.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity createUser(@RequestBody UserDto userDto) {
        UserDto user = userService.createUser(userDto);
        return new ResponseEntity(user, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity updateUser(@PathVariable String userId, @RequestBody UserDto userDto) {
        UserDto user = userService.updateUser(userDto, userId);
        return new ResponseEntity(user, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
       ApiResponseMessage message= ApiResponseMessage.builder().message("user is deleted successfully ").success(true).status(HttpStatus.OK).build();
        return new ResponseEntity( message,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getAllUser() {
        return new ResponseEntity(userService.getAllUser(), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity getUserById(@PathVariable String userId) {
        UserDto user = userService.getUserBy(userId);
        return new ResponseEntity(user, HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity getUserByEmail(@PathVariable String email) {
        UserDto user = userService.getUserByEmail(email);
        return new ResponseEntity(user, HttpStatus.OK);
    }
    @GetMapping("/search/{keyword}")
    public ResponseEntity getUserByNAme(@PathVariable String keyword) {
        List<UserDto> users = userService.searchUser(keyword);
        return new ResponseEntity(users, HttpStatus.OK);
    }
}
