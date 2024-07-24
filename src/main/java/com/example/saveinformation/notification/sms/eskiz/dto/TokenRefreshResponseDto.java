package com.example.saveinformation.notification.sms.eskiz.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class TokenRefreshResponseDto {
    private String message;
    @JsonProperty("token_type")
    private String tokeType;
    private Map<String, String> data;
}
