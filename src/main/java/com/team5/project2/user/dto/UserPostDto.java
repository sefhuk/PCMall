package com.team5.project2.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserPostDto {
    private String name;
    private String email;
    private String password;
    private String phone_number;
}