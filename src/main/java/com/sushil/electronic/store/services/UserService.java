package com.sushil.electronic.store.services;

import com.sushil.electronic.store.dtod.UserDto;
import com.sushil.electronic.store.entities.User;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto user);

    UserDto updateUser(UserDto user, String userId);

    void deleteUser(String userId);

    List<UserDto> getAllUser();

    UserDto getUserBy(String userId);

    UserDto getUserByEmail(String userEmail);

    List<UserDto> searchUser(String keyword);


}
