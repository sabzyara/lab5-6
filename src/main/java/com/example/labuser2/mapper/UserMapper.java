package com.example.labuser2.mapper;

import com.example.labuser2.dto.UserDto;
import com.example.labuser2.entity.User;

public class UserMapper {

    public static UserDto toDto(User user) {
        return new UserDto(
                user.getUserId(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getCreatedAt()
        );
    }

    public static User toEntity(UserDto userDto) {
        User user = new User();
        user.setUserId(userDto.getUserId());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());


        if (userDto.getCreatedAt() != null) {
            user.setCreatedAt(userDto.getCreatedAt());
        }

        return user;
    }
}


