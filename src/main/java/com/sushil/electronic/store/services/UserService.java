package com.sushil.electronic.store.services;

import com.sushil.electronic.store.dtod.PageableResponse;
import com.sushil.electronic.store.dtod.UserDto;
import com.sushil.electronic.store.entities.User;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto user);

    UserDto updateUser(UserDto user, String userId);

    void deleteUser(String userId);

    PageableResponse<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir);

    UserDto getUserBy(String userId);

    UserDto getUserByEmail(String userEmail);

    PageableResponse<UserDto> searchUser(String keyword, int pageNumber, int pageSize, String sortBy, String sortDir);


}
