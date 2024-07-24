package com.example.saveinformation.common.response;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CommonResponse {
    private String massage;
    @JsonFormat(pattern = ResponseConstants.RESPONSE_DATE_FORMAT)
    private LocalDateTime dateTime;
    private int statusCode;

}
