package com.example.bankcards.service;

import com.example.bankcards.dto.UserDto;
import com.example.bankcards.entity.User;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.util.UserMapper;

public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public void createUser(UserDto userDto){
        User user = userMapper.toEntity(userDto);
        userRepository.save(user);
    }

    public void userDelete(Long id){
        userRepository.findById(id).ifPresent(userRepository::delete);
    }
}
