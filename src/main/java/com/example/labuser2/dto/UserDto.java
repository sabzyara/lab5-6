package com.example.labuser2.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {
    private long userId;
    private String username;
    private String password;
    private String email;
    private Date createdAt;


    public UserDto(long userId, String username, String password,  String email , Date createdAt) {
    }
    public UserDto(long userId, String username, String password) {

    }

    public UserDto(String username, String password) {
    }
}

