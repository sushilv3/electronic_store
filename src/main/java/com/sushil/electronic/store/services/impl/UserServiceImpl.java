package com.sushil.electronic.store.services.impl;

import com.sushil.electronic.store.dtod.UserDto;
import com.sushil.electronic.store.entities.User;
import com.sushil.electronic.store.repositories.UserRepository;
import com.sushil.electronic.store.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        String userID = UUID.randomUUID().toString();
        userDto.setUserId(userID);
        User user = modelMapper.map(userDto, User.class);
        User savedUser = userRepository.save(user);
        UserDto savedUserDto = modelMapper.map(savedUser, UserDto.class);
        return savedUserDto;
    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found exception"));
        user.setName(userDto.getName());
//       user.setEmail(userDto.getEmail());
        user.setAbout(userDto.getAbout());
        user.setPassword(userDto.getPassword());
        user.setGender(userDto.getGender());
        user.setImageName(userDto.getImageName());
        User updatedUser = userRepository.save(user);
        UserDto updatedUserDto = modelMapper.map(updatedUser, UserDto.class);
        return updatedUserDto;
    }

    @Override
    public void deleteUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found exception"));
        userRepository.delete(user);
    }

    @Override
    public List<UserDto> getAllUser() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = users.stream().map(user -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public UserDto getUserBy(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found exception"));
        UserDto userDto = modelMapper.map(user, UserDto.class);
        return userDto;
    }

    @Override
    public UserDto getUserByEmail(String userEmail) {
       User user= userRepository.findByEmail(userEmail).orElseThrow(()->new RuntimeException("user not found with email"));
      UserDto userDto= modelMapper.map(user,UserDto.class);
       return userDto;
    }

    @Override
    public List<UserDto> searchUser(String keyword) {
        List<User> users=userRepository.findByNameContaining(keyword);
        List<UserDto> userDtos = users.stream().map(user -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
        return userDtos;
    }
}
