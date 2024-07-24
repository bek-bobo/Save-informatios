package com.example.saveinformation.user.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserSignInDto {
    private String phoneNumber;
    private String password;
}
