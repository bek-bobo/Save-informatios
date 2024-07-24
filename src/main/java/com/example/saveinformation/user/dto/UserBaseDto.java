package com.example.saveinformation.user.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserBaseDto {
    private String name;
    private String surname;
    private String phoneNumber;
    private String gmail;
}
