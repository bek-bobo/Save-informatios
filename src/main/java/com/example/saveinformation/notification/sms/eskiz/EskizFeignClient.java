package com.example.saveinformation.notification.sms.eskiz;

import com.example.saveinformation.notification.sms.eskiz.dto.SendSmsRequestDto;
import com.example.saveinformation.notification.sms.eskiz.dto.TokenRefreshResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "eskizFeignClient", url = "https://notify.eskiz.uz/api")
public interface EskizFeignClient {


    @PatchMapping("/auth/refresh")
    TokenRefreshResponseDto refresh(@RequestHeader(HttpHeaders.AUTHORIZATION) String bearer);

    @PostMapping("/message/sms/send")
    void smsSend(@RequestHeader(HttpHeaders.AUTHORIZATION) String bearer
            , @RequestBody SendSmsRequestDto sendSmsRequestDto);



}
