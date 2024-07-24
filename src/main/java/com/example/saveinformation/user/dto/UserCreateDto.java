package com.example.saveinformation.user.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
@NoArgsConstructor
public class UserCreateDto extends UserBaseDto{
    private String password;
    private String confirmPassword;
}
