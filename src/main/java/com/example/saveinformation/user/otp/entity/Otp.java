package com.example.saveinformation.user.otp.entity;


import jakarta.persistence.EntityListeners;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RedisHash(value = "otp", timeToLive = 3600)
@EntityListeners(AuditingEntityListener.class  )
public class Otp {

    @Id
    private String phoneNumber;
    private int code;
    private int sentCount;
    private LocalDateTime createdTime;
    private LocalDateTime lastSentTime;
    private boolean isVerified;

}
