package com.example.saveinformation.common.Exeptions;


import com.example.saveinformation.common.response.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class SaveInformationExceptionHandler {

        @ExceptionHandler(OtpException.class)
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        public CommonResponse handleEarlyResentException(OtpException e){
            return new CommonResponse(e.getMessage(),  LocalDateTime.now(), HttpStatus.BAD_REQUEST.value());
        }

        @ExceptionHandler(OtpException.OtpVerification.class)
        @ResponseStatus(HttpStatus.UNAUTHORIZED)
        public CommonResponse verification(OtpException.OtpVerification ev){
        return new CommonResponse(ev.getMessage(),  LocalDateTime.now(), HttpStatus.UNAUTHORIZED.value());
    }


}
