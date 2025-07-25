package com.example.bankcards.util;

import com.example.bankcards.dto.UserDto;
import com.example.bankcards.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserDto userDto);
}
