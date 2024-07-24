package com.example.saveinformation.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ValidatePhoneNumberDto {

    @NotBlank
    @Pattern(regexp = "^998[0-9]{9}$")
    private String phoneNumber;


}
