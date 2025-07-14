package com.example.bankcards.service;

import com.example.bankcards.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    void createUser(UserDto userDto);
    void userDelete(Long id);
}
