package com.example.saveinformation.notification.sms.eskiz.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SendSmsRequestDto {
    @JsonProperty("mobile_phone")
    private long mobilePhone;
    private String message;
    private final int from = 4546;

}
